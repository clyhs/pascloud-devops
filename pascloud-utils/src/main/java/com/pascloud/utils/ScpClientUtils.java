package com.pascloud.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.vo.server.SysServerInfo;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class ScpClientUtils {

	private static final Logger log = LoggerFactory.getLogger(ScpClientUtils.class);

	private Connection conn;

	private String addr;

	private String username;

	private String password;

	private Integer port;

	private ScpClientUtils() {
	}

	public ScpClientUtils(String addr, String username, String password) {
		this.addr = addr;
		this.username = username;
		this.password = password;
	}

	public ScpClientUtils(String addr, String username, String password, Integer port) {
		this.addr = addr;
		this.username = username;
		this.password = password;
		this.port = port;
	}

	private Connection getConn() {
		conn = new Connection(addr);
		return conn;
	}

	private Boolean isAuth() {
		Boolean flag = false;
		try {
			getConn();
			conn.connect();
			flag = conn.authenticateWithPassword(username, password);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.error("isauth", e.getMessage());
		}
		return flag;
	}

	public void copyFolder(String sourceFolder, String targetFolder) {
		if (isAuth()) {
			Session session = null;
			InputStream stdout = null;
			BufferedReader br = null;
			try {
				SCPClient scpClient = conn.createSCPClient();
				session = conn.openSession();
				session.execCommand("cp -r " + sourceFolder + " " + targetFolder);
				stdout = new StreamGobbler(session.getStdout());
				br = new BufferedReader(new InputStreamReader(stdout));

				while (true) {
					String line = br.readLine();
					if (line == null)
						break;
					System.out.println(line);
				}
				System.out.println("ExitCode: " + session.getExitStatus());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			} finally {
				try {
					stdout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				session.close();
			}
		} else {
			log.info("ssh登录失败");
		}
	}

	public void close() {
		conn.close();
	}

	public Boolean execCommand(String cmd) {
		Boolean flag = false;
		StringBuffer sb = new StringBuffer();
		if (isAuth()) {
			Session session = null;
			InputStream stdout = null;
			BufferedReader br = null;
			try {
				SCPClient scpClient = conn.createSCPClient();
				session = conn.openSession();
				log.info("执行命令" + cmd);
				session.execCommand(cmd);
				stdout = new StreamGobbler(session.getStdout());
				br = new BufferedReader(new InputStreamReader(stdout));
				String line;
				while ((line = br.readLine()) != null) {

					sb.append(line);
				}
				log.info("执行命令完毕");
				flag = true;
				// System.out.println(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.info("执行命令异常");
				log.error(e.getMessage());
			} finally {
				session.close();
				conn.close();
			}
		}
		return flag;
	}

	public String catFileToString(String path) {
		StringBuffer sb = new StringBuffer();
		if (isAuth()) {
			Session session = null;
			InputStream stdout = null;
			BufferedReader br = null;
			try {
				SCPClient scpClient = conn.createSCPClient();
				session = conn.openSession();
				session.execCommand("cat " + path);
				stdout = new StreamGobbler(session.getStdout());
				br = new BufferedReader(new InputStreamReader(stdout));
				System.out.println(conn.getHostname());
				while (true) {
					String line = br.readLine();
					if (line == null)
						break;
					sb.append(line);
				}
				System.out.println(sb.toString());
				System.out.println("ExitCode: " + session.getExitStatus());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.error(e.getMessage());
			} finally {
				try {
					stdout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				session.close();
				conn.close();

			}
		}
		return sb.toString();
	}

	public Boolean putFileToServer(String local, String server) {
		Boolean flag = false;
		Session session = null;
		InputStream stdout = null;
		BufferedReader br = null;
		if (isAuth()) {

			try {
				SCPClient scpClient = conn.createSCPClient();
				log.info("文件正在上传");
				scpClient.put(local, server);
				flag = true;
				log.info("文件上传完毕");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	public SysServerInfo getServerInfo() {
		SysServerInfo info = new SysServerInfo();

		if (isAuth()) {

			Session session = null;
			try {
				// SCPClient scpClient = conn.createSCPClient();
				session = conn.openSession();
				int cpuidle = cpuIdle(session);
				info.setCpu_idle(cpuidle + "%");
				info.setCpu_used((100 - cpuidle) + "%");
				session.close();
				session = null;
				session = conn.openSession();
				String memoryStr = memory(session);
				String memoryArr[] = memoryStr.split(",");

				int memoryTotal = new Integer(memoryArr[0]).intValue();
				int memoryFree = new Integer(memoryArr[1]).intValue();
				int used = memoryTotal - memoryFree;
				info.setMemory_total(memoryTotal + "m");
				info.setMemory_free(memoryFree + "m");
				NumberFormat numberFormat = NumberFormat.getInstance();
				// 设置精确到小数点后2位
				numberFormat.setMaximumFractionDigits(2);

				String result = numberFormat.format((float) used / (float) memoryTotal * 100);
				info.setMemory_used(result + "%");
				session.close();
				session = null;
				session = conn.openSession();
				info.setOs(os(session));
				session.close();
				session = null;
				session = conn.openSession();
				info.setUname(uname(session));
				session.close();
				session = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} finally {
				// session.close();
				conn.close();

			}
		}
		return info;
	}

	private int cpuIdle(Session session) {
		InputStream is = null;
		BufferedReader brStat = null;
		StringTokenizer tokenStat = null;
		Session sess = null;
		String str = "";
		int i = 0, j = 0, cpuidle = 0;
		try {
			sess = session;
			sess.execCommand("vmstat 1 2");
			is = new StreamGobbler(sess.getStdout());
			brStat = new BufferedReader(new InputStreamReader(is));
			brStat.readLine();
			brStat.readLine();
			brStat.readLine();

			str = brStat.readLine();
			if (str == null) {

			} else {
				tokenStat = new StringTokenizer(str);
				for (i = 0; i < 14; i++) {
					tokenStat.nextToken();
				}
				cpuidle = cpuidle + new Integer(tokenStat.nextToken()).intValue();
			}
			cpuidle = new Double(cpuidle).intValue();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return cpuidle;
	}

	private String memory(Session session) {
		String str = "";
		InputStream is = null;
		BufferedReader brStat = null;
		StringTokenizer tokenStat = null;
		Session sess = null;
		int i = 0, j = 0, free = 0, buff = 0, cache = 0, totalmemory = 0, memidle = 0;
		double memused = 0;
		try {
			sess = session;
			sess.execCommand("free -m");
			is = new StreamGobbler(sess.getStdout());
			brStat = new BufferedReader(new InputStreamReader(is));
			brStat.readLine();

			str = brStat.readLine();
			if (str == null) {

			} else {
				tokenStat = new StringTokenizer(str);
				for (i = 0; i < 1; i++) {
					tokenStat.nextToken();
				}

				totalmemory = totalmemory + new Integer(tokenStat.nextToken()).intValue();

				tokenStat.nextToken();

				free = free + new Integer(tokenStat.nextToken()).intValue();

				tokenStat.nextToken();
				// SysLogger.log("free is :"+free);
				/* 读取buff的信息 */
				buff = buff + new Integer(tokenStat.nextToken()).intValue();
				// SysLogger.log("buff is :"+buff);
				/* 读取cache的信息 */
				cache = cache + new Integer(tokenStat.nextToken()).intValue();
				// SysLogger.log("cache is :"+cache);
				memidle = (free + buff + cache);

				// memused = ((totalmemory - memidle) * 100) / totalmemory;
			}
			str = totalmemory + "," + memidle;

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return str;
	}

	public List<String> getOracleSid() {
		String msg = "";
		List<String> lists = new ArrayList<>();
		if (isAuth()) {
			StringBuffer sb = new StringBuffer();
			InputStream stdout = null;
			BufferedReader brStat = null;
			StringTokenizer tokenStat = null;
			Session session = null;
			BufferedReader br = null;
			int i = 0;
			try {
				// SCPClient scpClient = conn.createSCPClient();
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
				// System.out.println(sb.toString());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} finally {
				// session.close();
				try {
					stdout.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				session.close();
				conn.close();
			}
		}
		return lists;
	}

	

	public Boolean createOracleBySID(String sid) {
		Boolean flag = false;
		if (isAuth()) {
			StringBuffer sb = new StringBuffer();
			InputStream stdout = null;
			StringTokenizer tokenStat = null;
			Session session = null;
			BufferedReader br = null;
			int i = 0;
			try {
				session = conn.openSession();
				session.execCommand("~/script/create_database.sh" + " " + sid);
				stdout = new StreamGobbler(session.getStdout());
				br = new BufferedReader(new InputStreamReader(stdout));
				while (true) {
					String line = br.readLine();
					if (line == null){
						flag = true;
						break;
					}else{
						log.info(line);
					}
					sb.append(line);
				}
				
				System.out.println(flag);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// session.close();
				try {
					stdout.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				session.close();
				conn.close();
			}
		}
		return flag;
	}

	

	private String os(Session session) {
		String str = "";
		InputStream is = null;
		BufferedReader brStat = null;
		Session sess = null;
		try {
			sess = session;
			sess.execCommand("rpm -q centos-release");
			is = new StreamGobbler(sess.getStdout());
			brStat = new BufferedReader(new InputStreamReader(is));
			str = brStat.readLine();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return str;
	}

	private String uname(Session session) {
		String str = "";
		InputStream is = null;
		BufferedReader brStat = null;
		Session sess = null;
		try {
			sess = session;
			sess.execCommand("uname -a");
			is = new StreamGobbler(sess.getStdout());
			brStat = new BufferedReader(new InputStreamReader(is));
			str = brStat.readLine();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return str;
	}

	public static void main(String[] args) {
		// System.out.println("host");
		// String sourceFolder = "/home/webapps";
		// String targetFolder = "/home/paspb_liao";
		// ScpClientUtils client = ScpClientUtils.getInstance("192.168.0.16",
		// "root", "tccp@2012");
		// client.copyFolder(sourceFolder, targetFolder);
		// client.close();
		SysServerInfo info = new SysServerInfo();
		ScpClientUtils client = new ScpClientUtils("192.168.1.234", "oracle", "oracle");
		// client.getOracleSid();
		//client.processOracleBySID("cpas03");
		client.createOracleBySID("cpas04");
		//client.processOracleBySID2("cpas03");

	}

}
