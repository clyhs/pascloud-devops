package com.pascloud.module.database.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.pascloud.constant.Constants;
import com.pascloud.module.common.service.AbstractBaseService;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.server.service.ServerService;
import com.pascloud.utils.DBUtils;
import com.pascloud.utils.FileUtils;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.server.ServerVo;
import com.pascloud.vo.tenant.KhdxHyVo;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

@Service
public class DBServerService extends AbstractBaseService {

	@Autowired
	private ServerService  m_serverService;
	@Autowired
	private PasCloudConfig m_config;

	private static Logger log = LoggerFactory.getLogger(DBServerService.class);

	public List<DBInfo> getOracleSidWithServer(ServerVo server) {
		List<DBInfo> result = new ArrayList<>();
		List<String> lists = getOracleSid(server.getIp());
		if (null != lists && lists.size() > 0) {
			for (String s : lists) {
				DBInfo db = new DBInfo();
				if (s.contains("ora_pmon")) {
					s = s.replaceAll("ora_pmon_", "");
					db.setId(s);
					db.setName(s);
					db.setUrl(DBUtils.getUrlByParams("oracle", server.getIp(), s, 1521));
					result.add(db);
				}
			}
		}
		return result;
	}

	public List<String> getOracleSid(String ip) {
		Connection conn = null;
		List<String> lists = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		InputStream stdout = null;
		StringTokenizer tokenStat = null;
		Session session = null;
		BufferedReader br = null;
		int i = 0;
		try {
			ServerVo vo = m_serverService.getByIP(ip);
			// SCPClient scpClient = conn.createSCPClient();
			if (null != vo) {
				conn = getScpClientConn(vo.getIp(), vo.getUsername(), vo.getPassword());
				session = conn.openSession();
				session.execCommand("ps -ef | grep ora_pmon");
				stdout = new StreamGobbler(session.getStdout());
				br = new BufferedReader(new InputStreamReader(stdout));
				while (true) {
					String line = br.readLine();
					if (line == null)
						break;
					if (line.contains("oracle")) {
						tokenStat = new StringTokenizer(line);
						for (i = 0; i < 7; i++) {
							tokenStat.nextToken();
						}
						lists.add(tokenStat.nextToken());
					} else {
						continue;
					}
					System.out.println(line);
					sb.append(line);
				}
			}

		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			// session.close();
			try {
				stdout.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				log.error(e1.getMessage());
			}
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}
			session.close();
			conn.close();
		}

		return lists;
	}

	public Boolean createOracleWithSid(String ip, String sid, String tnsnamePath) {
		Boolean flag = false;
		ServerVo vo = m_serverService.getByIP(ip);
		log.info("ip:" + ip + ",sid:" + sid);
		flag = createOracleBySID(sid, ip, tnsnamePath);

		return flag;
	}

	private Boolean createOracleBySID(String sid, String ip, String tnsnamePath) {
		Boolean flag = false;
		StringBuffer sb = new StringBuffer();
		InputStream stdout = null;
		Session session = null;
		BufferedReader br = null;
		int i = 0;
		Connection conn = null;
		Connection conn2 = null;
		try {
			if (null != ip) {
				ServerVo vo = m_serverService.getByIP(ip);
				conn2 = getScpClientConn(ip, "oracle", "oracle");
				conn = getScpClientConn(ip, vo.getUsername(), vo.getPassword());
				session = conn2.openSession();
				session.execCommand("/home/oracle/script/create_database.sh" + " " + sid);
				stdout = new StreamGobbler(session.getStdout());
				br = new BufferedReader(new InputStreamReader(stdout));
				while (true) {
					String line = br.readLine();
					if (line == null) {
						flag = true;
						break;
					} else {
						log.info(line);
					}
					sb.append(line);
				}
				System.out.println(flag);

				if (flag) {

					if (changeScriptOwn(conn)) {
						flag = changeScriptMod(conn);
					} else {
						flag = false;
					}

					if (flag) {
						if (addSidWithTnsname(sid, conn, ip, tnsnamePath)) {
							// flag =
							// impDmpFileWithSid(conn2,sid);createTableSpaceWithSid
							if(addSidWithListener(sid,conn,ip)){
								if (createTableSpaceWithSid(conn, sid)) {
									if (grantWithSid(conn, sid)) {
										flag = restartListenerWithSid(conn, sid);
									}
								}
							}
						}
						
					}
				}
			} else {
				return flag;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		} finally {
			// session.close();
			try {
				stdout.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				log.error(e1.getMessage());
			}
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}
			session.close();
			conn2.close();
			conn.close();
		}

		return flag;
	}

	private Boolean createTableSpaceWithSid(Connection conn, String sid) {
		Boolean flag = false;
		Session session = null;
		InputStream stdout = null;
		BufferedReader br = null;
		try {
			log.info("新建表空间");
			session = conn.openSession();
			session.execCommand("/home/oracle/script/create_tablespace.sh" + " " + sid);
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					log.info("新建表空间成功");
					flag = true;
					break;
				} else {
					log.info(line);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.error("新建表空间异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			session.close();
		}
		return flag;
	}

	private Boolean grantWithSid(Connection conn, String sid) {
		Boolean flag = false;
		Session session = null;
		InputStream stdout = null;
		BufferedReader br = null;
		try {
			log.info("新建用户pas");
			session = conn.openSession();
			session.execCommand("/home/oracle/script/grant_sid.sh" + " " + sid);
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					log.info("新建用户pas成功");
					flag = true;
					break;
				} else {
					log.info(line);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.error("新建用户pas异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			session.close();
		}
		return flag;
	}

	private Boolean restartListenerWithSid(Connection conn, String sid) {
		Boolean flag = false;
		Session session = null;
		InputStream stdout = null;
		BufferedReader br = null;
		try {
			log.info("新建用户pas");
			session = conn.openSession();
			session.execCommand("/home/oracle/script/restart_listener.sh" + " " + sid);
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					log.info("新建用户pas成功");
					flag = true;
					break;
				} else {
					log.info(line);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.error("新建用户pas异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			session.close();
		}
		return flag;
	}

	public Boolean deleteOracleWithSid(String ip, String sid, String oratabfilePath, String tnsnamePath) {

		Boolean flag = false;
		Connection conn = null;
		try {
			log.info("删除数据库开始" + sid);
			ServerVo vo = m_serverService.getByIP(ip);
			if (null != vo) {
				conn = getScpClientConn(vo.getIp(), vo.getUsername(), vo.getPassword());
				if (shutDownOracleWithSid(conn, sid)) {
					if (deleteOracleWithSid(conn, sid)) {
						if (delSIDWithOratab(conn, sid, oratabfilePath)) {
							if(delSidWithListener(sid,conn,ip)){
								flag = delSidWithTnsname(sid, conn, ip, tnsnamePath);
								flag = restartListenerWithSid(conn, sid);
							}
							
						}
					}
				}
			}
		} catch (Exception e) {
			log.info("删除数据库开始" + sid + "异常");
		} finally {
			conn.close();
		}
		return flag;
	}

	public Boolean startOracleWithSid(String sid) {
		Boolean flag = false;
		try {

		} catch (Exception e) {

		}

		return flag;
	}

	public Boolean importDmpfileWithSid(String ip, String sid) {
		Boolean flag = false;
		ServerVo vo = m_serverService.getByIP(ip);
		log.info("ip:" + ip + ",sid:" + sid);
		Connection conn = null;
		if (null != vo) {
			conn = getScpClientConn(vo.getIp(), vo.getUsername(), vo.getPassword());
			flag = impDmpFileWithSid(conn, sid);
		}

		return flag;
	}

	private Boolean impDmpFileWithSid(Connection conn, String sid) {
		Boolean flag = false;
		Session session = null;
		InputStream stdout = null;
		BufferedReader br = null;
		try {
			log.info("导入DMP文件");
			session = conn.openSession();
			session.execCommand("/home/oracle/script/imp_dmp.sh" + " " + sid);
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					log.info("导入DMP文件成功");
					flag = true;
					break;
				} else {
					log.info(line);
				}
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.error("导入DMP文件异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			session.close();
		}
		return flag;
	}

	private Boolean shutDownOracleWithSid(Connection conn, String sid) {
		Boolean flag = false;
		Session session = null;
		InputStream stdout = null;
		BufferedReader br = null;
		try {
			log.info("关闭数据库");
			session = conn.openSession();
			session.execCommand("/home/oracle/script/shutdown.sh" + " " + sid);
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					log.info("关闭数据库成功");
					flag = true;
					break;
				} else {
					log.info(line);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.error("关闭数据库异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			session.close();
		}
		return flag;
	}

	private Boolean deleteOracleWithSid(Connection conn, String sid) {
		Boolean flag = false;
		Session session = null;
		InputStream stdout = null;
		BufferedReader br = null;
		try {
			log.info("删除数据库文件");
			session = conn.openSession();
			session.execCommand("/home/oracle/script/delete_database.sh" + " " + sid + " " + getSidEx(sid));
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					log.info("删除数据库文件成功");
					flag = true;
					break;
				} else {
					log.info(line);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.info("删除数据库文件异常");
			log.error(e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			session.close();
		}
		return flag;
	}

	private Boolean delSIDWithOratab(Connection conn, String sid, String oratabfilePath) {
		StringBuffer sb = new StringBuffer();
		Boolean flag = false;
		Session session = null;
		InputStream stdout = null;
		BufferedReader br = null;
		try {
			log.info("删除oratab文件里面的SID信息");
			session = conn.openSession();
			session.execCommand("cat " + "/etc/oratab");
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					flag = true;
					break;
				}
				if (!line.startsWith("#")) {
					if (line.contains(sid)) {
					} else {
						sb.append(line).append("\n");
					}
				}
			}
			if (flag) {
				File file = new File(oratabfilePath);
				if (!file.exists()) {
					FileUtils.createOrExistsFile(file);
				}
				FileUtils.writeFileFromString(file, sb.toString(), false);
				uploadOratabToServer(conn, oratabfilePath);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.info("删除oratab文件里面的SID信息异常");
			log.error(e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			session.close();
		}
		return flag;
	}

	private String getSidEx(String sid) {
		String ex = Constants.ORACLE_SID_EX_PREEFIX;
		String sidNum = sid.replace(Constants.ORACLE_SID_PREEFIX, "");
		ex = ex + sidNum + "*";
		log.info("正则表达式：" + ex);
		return ex;
	}

	private Boolean uploadOratabToServer(Connection conn, String oratabfilePath) {
		Boolean flag = false;
		SCPClient scpClient = null;
		try {
			log.info("上传" + oratabfilePath + "文件");
			scpClient = conn.createSCPClient();
			scpClient.put(oratabfilePath, "/etc/");
			flag = true;
			log.info("上传" + oratabfilePath + "文件成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.info("上传" + oratabfilePath + "文件异常");
		} finally {

		}
		return flag;
	}

	private Boolean uploadTnsnameToServer(Connection conn, String tnsnamePath) {
		Boolean flag = false;
		SCPClient scpClient = null;
		try {
			log.info("上传" + tnsnamePath + "文件");
			scpClient = conn.createSCPClient();
			scpClient.put(tnsnamePath, "/u01/app/oracle/product/11.2.0/dbhome_1/network/admin/");
			flag = true;
			log.info("上传" + tnsnamePath + "文件成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.info("上传" + tnsnamePath + "文件异常");
		} finally {

		}
		return flag;
	}

	private Boolean addSidWithTnsname(String sid, Connection conn, String ip, String tnsnamePath) {
		Boolean flag = false;
		InputStream stdout = null;
		BufferedReader br = null;
		Session session = null;
		StringBuffer sb = new StringBuffer();
		StringBuffer sidSb = new StringBuffer();
		Boolean isExist = false;
		try {
			log.info("读取tnsnames.ora文件");
			session = conn.openSession();
			session.execCommand("cat " + "/u01/app/oracle/product/11.2.0/dbhome_1/network/admin/tnsnames.ora");
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			int i = 0;
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				if (line.startsWith(sid.toUpperCase())) {
					isExist = true;
				}
				sb.append(line).append("\n");
				i++;
			}

			if (!isExist) {
				log.info("编写" + sid + "内容");
				sidSb.append(sid.toUpperCase() + " =").append("\n").append("  (DESCRIPTION =").append("\n")
						.append("    (ADDRESS = (PROTOCOL = TCP)(HOST = " + ip + ")(PORT = 1521))").append("\n")
						.append("    (CONNECT_DATA =").append("\n").append("      (SERVER = DEDICATED)").append("\n")
						.append("      (SERVICE_NAME = " + sid.toLowerCase() + ")").append("\n").append("    )")
						.append("\n").append("  )").append("\n").append("").append("\n");
				if (!FileUtils.isFileExists(tnsnamePath)) {
					FileUtils.createOrExistsFile(tnsnamePath);
				}
				sb.append("").append("\n").append(sidSb.toString());
				log.info("向tnsnames.ora写入" + sid + "的配置内容");
				log.info(sb.toString());
				if (FileUtils.writeFileFromString(new File(tnsnamePath), sb.toString(), false)) {
					flag = uploadTnsnameToServer(conn, tnsnamePath);
				}

			} else {
				flag = true;
				log.info("tnsnames.ora文件已经配置了" + sid);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.info("发生异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			session.close();
		}

		return flag;
	}

	private Boolean delSidWithTnsname(String sid, Connection conn, String ip, String tnsnamePath) {
		Boolean flag = false;
		InputStream stdout = null;
		BufferedReader br = null;
		Session session = null;
		StringBuffer sb = new StringBuffer();
		Boolean isExist = false;
		try {
			log.info("读取tnsnames.ora文件");
			session = conn.openSession();
			session.execCommand("cat " + "/u01/app/oracle/product/11.2.0/dbhome_1/network/admin/tnsnames.ora");
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			int i = 0;
			int index = 0;
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				if (line.startsWith(sid.toUpperCase())) {
					index = i;
					isExist = true;
				}
				if (index > 0 && i <= index + 7) {

				} else {
					sb.append(line).append("\n");
				}
				i++;
			}

			if (isExist) {

				if (!FileUtils.isFileExists(tnsnamePath)) {
					FileUtils.createOrExistsFile(tnsnamePath);
				}
				log.info("向tnsnames.ora写入" + sid + "的配置内容");
				FileUtils.writeFileFromString(new File(tnsnamePath), sb.toString(), false);
				flag = uploadTnsnameToServer(conn, tnsnamePath);
			} else {
				flag = true;
				log.info("tnsnames.ora文件没有配置" + sid);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.info("发生异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			session.close();
		}

		return flag;
	}

	/**
	 * listener.ora
	 * 
	 * @param sid
	 * @param conn
	 * @param ip
	 * @param listenerPath
	 * @return
	 */
	private Boolean addSidWithListener(String sid, Connection conn, String ip) {
		Boolean flag = false;
		InputStream stdout = null;
		BufferedReader br = null;
		Session session = null;
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		StringBuffer sidSb = new StringBuffer();
		Boolean isExist = false;
		String listenerPath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SCRIPT_ORACLE_DIR()+
				"/conf/listener.ora";
		try {
			log.info("读取listener.ora文件");
			session = conn.openSession();
			session.execCommand("cat " + "/u01/app/oracle/product/11.2.0/dbhome_1/network/admin/listener.ora");
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			int index = 0;
			int listEndIndex = 0;
			int i = 0;
			sidSb.append("  (SID_DESC =").append("\n").append("  (GLOBAL_DBNAME = " + sid + ")").append("\n")
					.append("  (SID_NAME = " + sid + ")").append("\n").append("  )").append("\n");
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				sb.append(line).append("\n");
				if (line.contains("SID_LIST_LISTENER =")) {
					index = i;
				}
				if (line.contains("GLOBAL_DBNAME")) {
					String[] str = line.split("=");
					//System.out.println(str[1].toLowerCase().trim().toString());
					if (str.length == 2) {
						String name = str[1].toLowerCase().trim().toString();
						name = name.replace(")", "");
						if (sid.equals(name)) {
							System.out.println("sid已经存在");
							isExist = true;
						} 
					}
				}
				if(line.contains("  )") && index>0 && !isExist){
					listEndIndex = i;
				}
				i++;
			}
			if(!isExist && listEndIndex>0 && index>0){
				InputStream in = new ByteArrayInputStream(sb.toString().getBytes());
				BufferedReader br2 = new BufferedReader(new InputStreamReader(in));
				int j=0;
				while (true) {
					String line = br2.readLine();
					if (line == null) {
						break;
					}
					sb2.append(line).append("\n");
					if(line.contains("  )") && j==listEndIndex){
						sb2.append(sidSb.toString());
					}
					j++;
				}
				br2.close();
				in.close();
			}else if(index == 0 && !isExist){
				sb3.append("SID_LIST_LISTENER =").append("\n")
				   .append("(SID_LIST =").append("\n")
				   .append(sidSb.toString())
				   .append(")");
				sb2.append(sb.toString()).append(sb3.toString());
			}else if(isExist){
				sb2.append(sb.toString());
			}
			if(!isExist){
				if (!FileUtils.isFileExists(listenerPath)) {
					FileUtils.createOrExistsFile(listenerPath);
				}
				log.info("向listener.ora写入" + sid + "的配置内容");
				FileUtils.writeFileFromString(new File(listenerPath), sb2.toString(), false);
				flag = uploadTnsnameToServer(conn, listenerPath);
			}else{
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.info("发生异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			session.close();
		}

		return flag;
	}
	
	private Boolean delSidWithListener(String sid, Connection conn, String ip) {
		Boolean flag = false;
		InputStream stdout = null;
		BufferedReader br = null;
		Session session = null;
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		StringBuffer sidSb = new StringBuffer();
		Boolean isExist = false;
		String listenerPath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SCRIPT_ORACLE_DIR()+
				"/conf/listener.ora";
		try {
			log.info("读取listener.ora文件");
			session = conn.openSession();
			session.execCommand("cat " + "/u01/app/oracle/product/11.2.0/dbhome_1/network/admin/listener.ora");
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			int index = 0;
			int listStartIndex = 0;
			int listEndIndex = 0;
			int i = 0;

			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}

				sb.append(line).append("\n");
				if (line.contains("SID_LIST_LISTENER =")) {
					index = i;
				}
				if (line.contains("GLOBAL_DBNAME")) {
					String[] str = line.split("=");
					//System.out.println(str[1].toLowerCase().trim().toString());
					if (str.length == 2) {
						String name = str[1].toLowerCase().trim().toString();
						name = name.replace(")", "");
						if (sid.equals(name)) {
							System.out.println("sid已经存在");
							isExist = true;
							listStartIndex = i;
						} 
					}
				}
				i++;
			}
			if(isExist && listStartIndex>0 && index>0){
				InputStream in = new ByteArrayInputStream(sb.toString().getBytes());
				BufferedReader br2 = new BufferedReader(new InputStreamReader(in));
				int j=0;
				while (true) {
					String line = br2.readLine();
					if (line == null) {
						break;
					}
					if(j>=listStartIndex-1 && j<=listStartIndex+2){
						
					}else{
						sb2.append(line).append("\n");
					}
					j++;
				}
				
				br2.close();
				in.close();
				if (!FileUtils.isFileExists(listenerPath)) {
					FileUtils.createOrExistsFile(listenerPath);
				}
				log.info("向listener.ora写入" + sid + "的配置内容");
				FileUtils.writeFileFromString(new File(listenerPath), sb2.toString(), false);
				flag = uploadTnsnameToServer(conn, listenerPath);
			}else{
				flag = true;
			}
			System.out.println(sb2.toString());
			
			
		} catch (Exception e) {
			log.error(e.getMessage());
			log.info("发生异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			session.close();
		}

		return flag;
	}

	public Boolean checkLsnrctlStatus(String ip, String sid) {
		Boolean flag = false;
		ServerVo vo = m_serverService.getByIP(ip);
		log.info("ip:" + ip + ",sid:" + sid);
		Connection conn = null;
		if (null != vo) {
			conn = getScpClientConn(vo.getIp(), vo.getUsername(), vo.getPassword());
			flag = checkLsnrctlStatus(conn, sid);
		}
		log.info("lsnrctl status:" + flag);
		return flag;
	}

	private Boolean checkLsnrctlStatus(Connection conn, String sid) {
		Boolean flag = false;
		Session session = null;
		InputStream stdout = null;
		BufferedReader br = null;
		try {
			log.info("检查" + sid + "监听是否启动");
			session = conn.openSession();
			session.execCommand("/home/oracle/script/lsnrctl_status.sh" + " " + sid);
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				if (line.contains("Service \"" + sid + "\" has 1 instance(s)")) {
					flag = true;
				}
				log.info(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.info("检查" + sid + "监听异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			}
			session.close();
			conn.close();
		}
		return flag;
	}

	public Integer updateKhdxHy(String dbType, String url, String username, String password, String hyprefix) {

		String driverClass = DBUtils.getDirverClassName(dbType);
		DBUtils db = new DBUtils(driverClass, url, username, password);
		java.sql.Connection sourceConn = null;
		Integer result = 0;
		QueryRunner qRunner = new QueryRunner();
		log.info("driverClass=" + driverClass + ",url=" + url + ",username=" + username + ",password=" + password);
		// qRunner.insertBatch(conn, sql, ArrayHandle<KhdxHyVo>, params);
		try {
			sourceConn = db.getConnection();
			if (null != sourceConn) {
				String sql = "update khdx_hy set dlmc=replace(dlmc,substr(dlmc, 0, 2),?);";
				Object[] params = new Object[] { hyprefix };
				result = qRunner.update(sourceConn, sql, params);
			} else {

			}
			log.info("影响了" + result + "行");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			try {
				sourceConn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;

	}

	private Boolean changeScriptOwn(Connection conn) {
		Boolean flag = false;
		Session session = null;
		InputStream stdout = null;
		BufferedReader br = null;
		try {
			log.info("更改/home/oracle/script目录的用户权限");
			session = conn.openSession();
			session.execCommand("chown -R oracle:oinstall /home/oracle/script");
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
			}
			flag = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("更改/home/oracle/script目录的用户权限异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
			}
			session.close();
		}
		return flag;
	}

	private Boolean changeScriptMod(Connection conn) {
		Boolean flag = false;
		Session session = null;
		InputStream stdout = null;
		BufferedReader br = null;
		try {
			log.info("更改/home/oracle/script目录的读写权限");
			session = conn.openSession();
			session.execCommand("chmod -R 755 /home/oracle/script");
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
			}
			flag = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("更改/home/oracle/script目录的读写权限异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
			}
			try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
			}
			session.close();
		}
		return flag;
	}

	public static void main(String[] args) throws Exception {

		String sid = "cloudpas";

		DBServerService s = new DBServerService();
		InputStream stdout = null;
		BufferedReader br = null;
		Session session = null;
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		StringBuffer sidSb = new StringBuffer();
		Boolean isExist = false;
		Connection conn = null;
		try {
			log.info("读取listener.ora文件");
			conn = s.getScpClientConn("192.168.0.101", "root", "tccp@2012");
			session = conn.openSession();
			session.execCommand("cat " + "/u01/app/oracle/product/11.2.0/dbhome_1/network/admin/listener.ora");
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			int index = 0;
			int listStartIndex = 0;
			int listEndIndex = 0;
			int i = 0;

			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}

				sb.append(line).append("\n");
				if (line.contains("SID_LIST_LISTENER =")) {
					index = i;
				}
				if (line.contains("GLOBAL_DBNAME")) {
					String[] str = line.split("=");
					//System.out.println(str[1].toLowerCase().trim().toString());
					if (str.length == 2) {
						String name = str[1].toLowerCase().trim().toString();
						name = name.replace(")", "");
						if (sid.equals(name)) {
							System.out.println("sid已经存在");
							isExist = true;
							listStartIndex = i;
						} 
					}
				}
				i++;
			}
			if(isExist && listStartIndex>0 && index>0){
				InputStream in = new ByteArrayInputStream(sb.toString().getBytes());
				BufferedReader br2 = new BufferedReader(new InputStreamReader(in));
				int j=0;
				while (true) {
					String line = br2.readLine();
					if (line == null) {
						break;
					}
					if(j>=listStartIndex-1 && j<=listStartIndex+2){
						
					}else{
						sb2.append(line).append("\n");
					}
					j++;
				}
			}
			System.out.println(sb2.toString());
			
		} catch (Exception e) {
			log.error(e.getMessage());
			log.info("发生异常");
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
			try {
				stdout.close();
			} catch (IOException e) {
			}
			session.close();
		}

	}

}
