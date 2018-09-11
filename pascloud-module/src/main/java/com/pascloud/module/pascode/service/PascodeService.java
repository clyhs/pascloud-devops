package com.pascloud.module.pascode.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pascloud.constant.Constants;
import com.pascloud.module.common.service.AbstractBaseService;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.database.service.DataBaseService;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.module.server.service.ServerService;
import com.pascloud.utils.DBUtils;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.mversion.XtcdVo;
import com.pascloud.vo.pascode.PascodeEnum;
import com.pascloud.vo.pascode.PascodeVo;
import com.pascloud.vo.pasdev.PasfileVo;
import com.pascloud.vo.pass.MysqlVo;
import com.pascloud.vo.pass.PasTypeEnum;
import com.pascloud.vo.result.ResultCommon;
import com.pascloud.vo.result.ResultPageVo;
import com.pascloud.vo.script.ScriptEnum;
import com.pascloud.vo.server.ServerVo;

import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

@Service
public class PascodeService extends AbstractBaseService {
	
	@Autowired
	private DataBaseService    m_databaseService;
	
	@Autowired
	private PasService         m_pasService;
	
	@Autowired
	private PasCloudConfig     m_config;
	
	@Autowired
	private ServerService      m_serverService;
	
	
	public Connection getConnectionPublic(){
		Connection conn = null;
		List<MysqlVo> vos = m_pasService.getMysqlServer();
		if(null!=vos && vos.size()>0){
			MysqlVo vo = vos.get(0);
			String driverClass = DBUtils.getDirverClassName(ScriptEnum.MYSQL.getValue());
			String url = DBUtils.getUrlByParams(ScriptEnum.MYSQL.getValue(), vo.getIp(), "pascloud", vo.getPort());
			DBUtils dbUtils = new DBUtils(driverClass, url, vo.getUsername(), vo.getPassword());
			conn = dbUtils.getConnection();
		}else{
			log.info("db为空");
		}
		return conn;
	}
	
	
	
	public ResultPageVo<PascodeVo> getPageData(Integer pageNo,Integer pageSize){
		ResultPageVo<PascodeVo> result = null;
		List<PascodeVo> list = new ArrayList<>();
		Connection conn = null;
		Integer start = 0;
		Integer totals = 0;
		try{
			conn = getConnectionPublic();
			QueryRunner qRunner = new QueryRunner(); 
			start = (pageNo - 1) * pageSize;
			String sql = "select * from xtb_pascode order by id desc limit ?,?";
			String tSql= "select count(1) from xtb_pascode ";
			
			Object[] tparams = {};
			Object[] params = {start,pageSize};
			
			Number num =  (Number)qRunner.query(conn,tSql, new ScalarHandler(),tparams);
			if(null!=num){
				totals = num.intValue();
				if(totals>0){
					list =  qRunner.query(conn,sql, new BeanListHandler<PascodeVo>(PascodeVo.class),params);
				}
			}
			result = new ResultPageVo<PascodeVo>(PasCloudCode.SUCCESS);
			result.setTotal(totals);
			result.setRows(list);
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			result = new ResultPageVo<PascodeVo>(PasCloudCode.EXCEPTION);
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return result;
	}
	
	public ResultPageVo<PascodeVo> getPageData(String name,Integer pageNo,Integer pageSize){
		ResultPageVo<PascodeVo> result = null;
		List<PascodeVo> list = new ArrayList<>();
		Connection conn = null;
		Integer start = 0;
		Integer totals = 0;
		try{
			conn = getConnectionPublic();
			QueryRunner qRunner = new QueryRunner(); 
			start = (pageNo - 1) * pageSize;
			String sql = "select * from xtb_pascode where name like ? order by id desc limit ?,?";
			String tSql= "select count(1) from xtb_pascode where name like ?  ";
			
			Object[] tparams = {"%"+name+"%"};
			Object[] params = {"%"+name+"%",start,pageSize};
			
			Number num =  (Number)qRunner.query(conn,tSql, new ScalarHandler(),tparams);
			if(null!=num){
				totals = num.intValue();
				if(totals>0){
					list =  qRunner.query(conn,sql, new BeanListHandler<PascodeVo>(PascodeVo.class),params);
				}
			}
			result = new ResultPageVo<PascodeVo>(PasCloudCode.SUCCESS);
			result.setTotal(totals);
			result.setRows(list);
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			result = new ResultPageVo<PascodeVo>(PasCloudCode.EXCEPTION);
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return result;
	}
	
	public ResultCommon uploadPascode(CommonsMultipartFile file){
        ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		String filepath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_PASCODE();
  
		FileUtils.createOrExistsDir(filepath);
		PascodeVo vo = null;
		String filename = "";
		String fname =  "";
		String newfilename = "";
		String newfname = "";
		String suffix = "";
		Integer type = 0;
		if (file != null && !file.isEmpty()) {
			log.info(file.getOriginalFilename());
			InputStream input = null;
			BufferedOutputStream bos = null;
			long size = 0;
			try {
				
				//pas-cloud-service-demo-1.0.1-SNAPSHOT-assembly.tar.gz
				//root.war
				filename = file.getOriginalFilename();
				//testhydr
				size = file.getSize();
				
				if(filename.contains(PascodeEnum.TGZ.getValue())){
					suffix = PascodeEnum.TGZ.getValue();
				}else if(filename.contains(PascodeEnum.WAR.getValue())){
					suffix = PascodeEnum.WAR.getValue();
				}
				
				if(filename.contains(Constants.PASCLOUD_SERVICE_DEMO)){
					type = PasTypeEnum.DEMO.getIndex();
				}else if(filename.contains(Constants.PASCLOUD_SERVICE_PASPM)){
					type = PasTypeEnum.PASPM.getIndex();
				}else if(filename.contains(Constants.PASCLOUD_SERVICE_MYCAT)){
					type = PasTypeEnum.MYCAT.getIndex();
				}else if(filename.contains(Constants.PASCLOUD_SERVICE_TOMCAT)){
					type = PasTypeEnum.TOMCAT.getIndex();
				}else{
					if(suffix.equals(PascodeEnum.WAR.getValue())){
						type = PasTypeEnum.TOMCAT.getIndex();
					}
				}
				
				fname = filename.replace(suffix, "");
				Date now = new Date();
				log.info(fname);
				//xxx/upload/pasfile/dn0/testhydr.zip
				newfname = fname + "-" + getRandomFileName(now);
				newfilename = newfname + suffix;
				//filepath = destpath+"/"+filename;
				filepath = filepath+"/"+newfilename;
				log.info(filepath);
				
				File destfile = new File(filepath);
				file.transferTo(destfile);
				
				vo = new PascodeVo();
				vo.setName(newfilename);
				vo.setCreateTime(now);
				vo.setType(type);
				vo.setSize(size);
				result = addPascode(vo);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				log.error(e.getMessage());
				result = new ResultCommon(PasCloudCode.EXCEPTION);
			}finally{
			}
		}
		return result;
	}
	
	public ResultCommon addPascode(PascodeVo vo){
		ResultCommon result = null;
		Connection conn = null;
		String sql = "";
		Integer row = 0;
		try {
			conn = getConnectionPublic();
			QueryRunner qRunner = new QueryRunner(); 
			
			sql = "INSERT INTO xtb_pascode(name,type,createTime,size)"
						+ "VALUES(?,?,?,?)";
			Object[] params = {vo.getName(),vo.getType(),vo.getCreateTime(),vo.getSize()};
			row = qRunner.update(conn, sql, params);
			
			log.info(sql);

			if(row>0){
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			log.error(e.getMessage());
			//e.printStackTrace();
			result = new ResultCommon(PasCloudCode.EXCEPTION.getCode(),e.getMessage());
		}finally{
			try {
				if(null!=conn){
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		return result;
	}

	
	public ResultCommon deletePascode(PascodeVo vo){
		ResultCommon result = null;
		Connection conn = null;
		String sql = "";
		Integer row = 0;
		String filepath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_PASCODE();
		try {
			conn = getConnectionPublic();
			QueryRunner qRunner = new QueryRunner(); 
			
			sql = "delete from xtb_pascode where "
						+ "id=? ";
			Object[] params = {vo.getId()};
			row = qRunner.update(conn, sql, params);
			
			log.info(sql);

			if(row>0){
				
				filepath = filepath + "/" +vo.getName();
				FileUtils.deleteFile(filepath);
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			log.error(e.getMessage());
			//e.printStackTrace();
			result = new ResultCommon(PasCloudCode.EXCEPTION.getCode(),e.getMessage());
		}finally{
			try {
				if(null!=conn){
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public ResultCommon uploadPascodeAndRestart(PascodeVo vo){
		ResultCommon result = null;
		result = uploadServiceToServer(vo);
		return result;
	}
	
	private ResultCommon uploadServiceToServer(PascodeVo vo){
        ResultCommon result = null;
        List<ServerVo> servers = new ArrayList<>();
        log.info("源码部署开始");
        log.info("查询应用服务器");
        servers = m_serverService.getAppServers();
        if(servers.size()<=0){
        	result = new ResultCommon(PasCloudCode.ERROR);
        	return result;
        }
        Date now = new Date();
        int num = 0;
        for(ServerVo s:servers){
        	
        	if(vo.getType().equals(PasTypeEnum.TOMCAT.getIndex())){
        		
        		if(vo.getName().contains(Constants.PASCLOUD_SERVICE_TOMCAT)){
        			result = uploadTomcatToServer(vo,s,now);
                	if(result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
                		num ++;
                	}
        		}else{
        			result = uploadWarToServer(vo,s,now);
                	if(result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
                		num ++;
                	}
        		}
        	}
        	else if(vo.getType().equals(PasTypeEnum.MYCAT.getIndex())){
        		result = uploadMycatToServer(vo,s,now);
            	if(result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
            		num ++;
            	}
        	}
        	else{
        		result = uploadServiceToServer(vo,s,now);
            	if(result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
            		num ++;
            	}
        	}
        	
        }
        
        if(vo.getType().equals(PasTypeEnum.TOMCAT.getIndex())){
        	result = new ResultCommon(PasCloudCode.SUCCESS.getCode(),
        			"成功部署出了"+num+"台应用服务器,请去服务管理进行重启前端服务");
    	}else if(vo.getType().equals(PasTypeEnum.MYCAT.getIndex())){
    		result = new ResultCommon(PasCloudCode.SUCCESS.getCode(),
        			"成功部署出了"+num+"台应用服务器,请去服务管理进行重启MYCAT服务");
    	}
    	else{
    		if(num>0){
            	result = copyPascodeToContainers(vo,now);
            	result = new ResultCommon(PasCloudCode.SUCCESS.getCode(),
            			"成功部署出了"+num+"台应用服务器,并替换了"+result.getDesc()+"个服务的源码，请去服务管理进行重启服务");
            }else{
            	result = new ResultCommon(PasCloudCode.ERROR.getCode(),"部署"+num+"台应用服务器都失败");
            }
    	}
        
        
        
        log.info("源码部署结束");
		return result;
	}
	
	public ResultCommon updatePascodeState(PascodeVo vo){
		ResultCommon result = null;
		log.info("更新源码的部署状态");
		Connection conn = null;
	
		Integer row = 0;
		try{
			String preffix_name  = vo.getName().substring(0, vo.getName().lastIndexOf("-"));
			
			QueryRunner qRunner = new QueryRunner(); 
			if(null!=preffix_name){
				
				conn = getConnectionPublic();
				String sql = " update xtb_pascode set selected=0 where name like ? ";
				log.info(sql);
				Object[] params = {"%"+preffix_name+"%"};
				qRunner.update(conn, sql, params);
				
				String sql2  = " update xtb_pascode set selected=1 where name=? ";
				log.info(sql2);
				Object[] params2 = {vo.getName()};
				row = qRunner.update(conn, sql2, params2);
			}
			if(row>0){
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}else{
				result = new ResultCommon(PasCloudCode.ERROR);
			}
		}catch(SQLException e){
			log.error(e.getMessage());
			result = new ResultCommon(PasCloudCode.EXCEPTION.getCode(),e.getMessage());
		}finally{
			if(null!=conn){
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
			}
		}
		return result;
	}
	
	private ResultCommon uploadServiceToServer(PascodeVo vo ,ServerVo s,Date now){
		ResultCommon result = null;
		String codepath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_PASCODE();
		String defaultPath = Constants.PASCLOUD_HOME;
        
        if(vo.getType().equals(PasTypeEnum.DEMO.getIndex())){
        	defaultPath =defaultPath+ Constants.PASCLOUD_SERVICE_DEMO;
        }else if(vo.getType().equals(PasTypeEnum.PASPM.getIndex())){
        	defaultPath =defaultPath+ Constants.PASCLOUD_SERVICE_PASPM;
        }else{
        	result = new ResultCommon(PasCloudCode.ERROR);
        	return result;
        }
        String backname = getRandomFileName(now);
        log.info("服务根目录路径："+defaultPath);
        String backupPath = defaultPath + "-" + backname;
        log.info("服务备份目录路径："+backupPath);
        String backupCmd = "mv "+defaultPath+" "+backupPath;
        codepath = codepath +"/"+ vo.getName();
        log.info("服务升级包路径："+codepath);
		ch.ethz.ssh2.Connection conn = null;
		conn = getScpClientConn(s.getIp(), s.getUsername(), s.getPassword());
    	if(null !=conn){
    		log.info("执行："+s.getIp()+"的命令"+backupCmd);
    		if(execCommand(conn,backupCmd)){
    			if(putFileToServer(conn,codepath,Constants.PASCLOUD_HOME)){
    				//tar -zxvf /home/pascloud/pas-cloud-service-demo-1.0.1-SNAPSHOT-assembly-2018080215195284198.tar.gz -C /home/pascloud/
    				//String cmd="tar -zvxf "+serverGZpath;
    				String uploadPath = Constants.PASCLOUD_HOME+vo.getName();
    				String tarname = execTarCommand(conn,uploadPath,Constants.PASCLOUD_HOME);
    				if(null!=tarname){
    					String tarpath = Constants.PASCLOUD_HOME+tarname;
    					String mvCmd = "mv "+tarpath+" "+defaultPath;
    					if(execCommand(conn,mvCmd)){
    						result = new ResultCommon(PasCloudCode.SUCCESS);
    					}else{
    						result = new ResultCommon(PasCloudCode.ERROR.getCode(),mvCmd+"失败");
    					}
    				}else{
    					result = new ResultCommon(PasCloudCode.ERROR.getCode(),tarname+"失败");
    				}
    			}else{
    				result = new ResultCommon(PasCloudCode.ERROR.getCode(),"上传失败");
    			}
    		}else{
    			result = new ResultCommon(PasCloudCode.ERROR.getCode(),backupCmd+"失败");
    		}
    		
        	conn.close();
        	conn = null;
        }else{
        	result = new ResultCommon(PasCloudCode.ERROR.getCode(),s.getIp()+"连接失败");
        }
    	
		return result;
	}
	
	/**
	 * 把上传的源码复制到各容器的映射地址
	 * @param vo
	 * @param now
	 * @return
	 */
	private ResultCommon copyPascodeToContainers(PascodeVo vo,Date now){
		ResultCommon result = null;
		String defaultPath = Constants.PASCLOUD_HOME;
		List<ContainerVo> containters = new ArrayList<>();
		if(vo.getType().equals(PasTypeEnum.DEMO.getIndex())){
			containters = m_pasService.getContainerWithMainService();
			defaultPath =defaultPath+ Constants.PASCLOUD_SERVICE_DEMO;
		}else if(vo.getType().equals(PasTypeEnum.PASPM.getIndex())){
			containters = m_pasService.getContainerWithPaspmService();
			defaultPath =defaultPath+ Constants.PASCLOUD_SERVICE_PASPM;
		}
		ch.ethz.ssh2.Connection conn = null;
		int num = 0;
		if(containters.size()>0){
			for(ContainerVo cvo : containters){
				String cpath = m_pasService.getServicePathWithContainerName(cvo.getName());
				log.info("服务的路径："+cpath);
				String backpath = cpath+"-"+getRandomFileName(now);
				String backupCmd = "mv "+cpath+" "+backpath;
				ServerVo s = m_serverService.getByIP(cvo.getIp());
				if(null!=s){
					conn = getScpClientConn(s.getIp(), s.getUsername(), s.getPassword());
					if(null!=conn){
						if(execCommand(conn,backupCmd)){
							String cpCmd = "cp -r "+defaultPath+" "+cpath;
							if(execCommand(conn,cpCmd)){
								num++;
								result = new ResultCommon(PasCloudCode.SUCCESS.getCode(),num+"");
							}else{
								result = new ResultCommon(PasCloudCode.ERROR.getCode(),cpCmd+"失败");
							}
						}else{
							result = new ResultCommon(PasCloudCode.ERROR.getCode(),backupCmd+"失败");
						}
						conn.close();
			        	conn = null;
					}else{
						result = new ResultCommon(PasCloudCode.ERROR.getCode(),s.getIp()+"连接失败");
					}
				}else{
					result = new ResultCommon(PasCloudCode.ERROR.getCode(),"找不到应用服务器");
				}
			}
		}else{
			result = new ResultCommon(PasCloudCode.ERROR.getCode(),"找不到服务");
		}
		
		return result;
	}
	
	private ResultCommon uploadWarToServer(PascodeVo vo ,ServerVo s,Date now){
        ResultCommon result = null;
        
        log.info("上传到/home/pascloud/tomcat");
        
        String codepath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_PASCODE();
		String defaultPath = Constants.PASCLOUD_HOME;
		String webappPath = defaultPath+"tomcat/webapps";
		String warpath    = webappPath+"/ROOT.war";
		String webpath    = webappPath+"/ROOT";

		String backname = getRandomFileName(now);
		String web_backup_path = defaultPath+"ROOT"+"-"+backname;
		
		codepath = codepath +"/"+ vo.getName();
		
		
		String backupCmd = "mv "+webpath+" "+web_backup_path;
		String rmCmd     = "rm -rf "+warpath;
		//unzip /home/pascloud/ROOT.war -d /home/pascloud/tomcat/webapps/ROOT 
		
		String servercodepath = defaultPath+vo.getName();
		String unzipCmd  = "unzip "+servercodepath+" -d "+webpath;
		
		ch.ethz.ssh2.Connection conn = null;
		conn = getScpClientConn(s.getIp(), s.getUsername(), s.getPassword());
    	if(null !=conn){
    		log.info("执行："+s.getIp()+"的命令"+backupCmd);
    		if(execCommand(conn,backupCmd)){
    			log.info("执行："+s.getIp()+"的命令"+rmCmd);
    			if(execCommand(conn,rmCmd)){
    				log.info("上传源码包："+s.getIp()+" "+codepath);
    				if(putFileToServer(conn,codepath,Constants.PASCLOUD_HOME)){
    					log.info("执行："+s.getIp()+"的命令"+unzipCmd);
    					if(execCommand(conn,unzipCmd)){
    						result = new ResultCommon(PasCloudCode.SUCCESS);
    					}else{
    						result = new ResultCommon(PasCloudCode.ERROR.getCode(),unzipCmd+"失败");
    					}
    				}else{
    					result = new ResultCommon(PasCloudCode.ERROR.getCode(),"上传失败");
    				}
    			}else{
    				result = new ResultCommon(PasCloudCode.ERROR.getCode(),rmCmd+"失败");
    			}
    		}else{
    			result = new ResultCommon(PasCloudCode.ERROR.getCode(),backupCmd+"失败");
    		}
    		conn.close();
    		conn = null;
    	}else{
    		result = new ResultCommon(PasCloudCode.ERROR.getCode(),s.getIp()+"连接失败");
    	}
		
		return result;
	}
	
	
	private ResultCommon uploadMycatToServer(PascodeVo vo ,ServerVo s,Date now){
		ResultCommon result = null;
		
        log.info("上传到/home/pascloud/mycat");
        
        String codepath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_PASCODE();
		String defaultPath = Constants.PASCLOUD_HOME;
		String mycatHome = defaultPath+"mycat";
		//本地MYCAT的源码地址
		codepath = codepath +"/"+ vo.getName();
		//服务器的MYCAT地址
		String servercodepath = defaultPath+vo.getName();
		
		ch.ethz.ssh2.Connection conn = null;
		conn = getScpClientConn(s.getIp(), s.getUsername(), s.getPassword());
    	if(null !=conn){
    		if(!checkDirIsExist(conn, mycatHome)){
    			log.info("上传源码包："+s.getIp()+" "+codepath);
    			//home/pascloud/mycat-xxxx.tar.gz
				if(putFileToServer(conn,codepath,Constants.PASCLOUD_HOME)){
					
					String uploadPath = Constants.PASCLOUD_HOME+vo.getName();
					//home/pascloud/mycat
    				String tarname = execTarCommand(conn,uploadPath,Constants.PASCLOUD_HOME);
    				
    				if(null!=tarname){
    					String tarpath = Constants.PASCLOUD_HOME+tarname;
    					//mv /home/pascloud/mycat /home/pascloud/
    					result = new ResultCommon(PasCloudCode.SUCCESS);
    				}else{
    					result = new ResultCommon(PasCloudCode.ERROR.getCode(),"解压失败");
    				}
    				
				}else{
					result = new ResultCommon(PasCloudCode.ERROR.getCode(),"上传失败");
				}
			}else{
				log.info(mycatHome+"已经存在！");
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}
    	}
		
		return result;
	}
	
	
	private ResultCommon uploadTomcatToServer(PascodeVo vo ,ServerVo s,Date now){
		ResultCommon result = null;
		
        log.info("上传到/home/pascloud/tomcat");
        
        String codepath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_PASCODE();
		String defaultPath = Constants.PASCLOUD_HOME;
		String tomcatHome = defaultPath+"tomcat";
		//本地MYCAT的源码地址
		codepath = codepath +"/"+ vo.getName();
		//服务器的MYCAT地址
		String servercodepath = defaultPath+vo.getName();
		
		ch.ethz.ssh2.Connection conn = null;
		conn = getScpClientConn(s.getIp(), s.getUsername(), s.getPassword());
    	if(null !=conn){
    		if(!checkDirIsExist(conn, tomcatHome)){
    			log.info("上传源码包："+s.getIp()+" "+codepath);
    			//home/pascloud/tomcat-xxxx.tar.gz
				if(putFileToServer(conn,codepath,Constants.PASCLOUD_HOME)){
					
					String uploadPath = Constants.PASCLOUD_HOME+vo.getName();
					//home/pascloud/tomcat
    				String tarname = execTarCommand(conn,uploadPath,Constants.PASCLOUD_HOME);
    				
    				if(null!=tarname){
    					String tarpath = Constants.PASCLOUD_HOME+tarname;
    					//mv /home/pascloud/tomcat /home/pascloud/
    					result = new ResultCommon(PasCloudCode.SUCCESS);
    				}else{
    					result = new ResultCommon(PasCloudCode.ERROR.getCode(),"解压失败");
    				}
    				
				}else{
					result = new ResultCommon(PasCloudCode.ERROR.getCode(),"上传失败");
				}
			}else{
				log.info(tomcatHome+"已经存在！");
				result = new ResultCommon(PasCloudCode.SUCCESS);
			}
    	}
		
		return result;
	}
	
	public Boolean checkDirIsExist(ch.ethz.ssh2.Connection conn,String dirPath){
		Boolean flag = false;
		Session session = null;
		InputStream stdout = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			log.info("检查目录");
			session = conn.openSession();
			session.execCommand(" [ -s "+dirPath+" ] && echo \"true\" || echo \"false\"");
			stdout = new StreamGobbler(session.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				} else {
					log.info(line);
					sb.append(line);
				}
			}
			if("true".equals(sb.toString())){
				flag = true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			log.error("检查目录异常");
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
	
	
	public String execTarCommand(ch.ethz.ssh2.Connection conn,String filepath,String outpath) {
		//Boolean flag = false;
		String result = "";
		StringBuffer sb = new StringBuffer();
		String cmd = "tar -zxvf "+filepath+" -C"+" "+outpath;
		if (null!=conn) {
			Session session = null;
			InputStream stdout = null;
			BufferedReader br = null;
			try {
				session = conn.openSession();
				log.info("执行命令" + cmd);
				session.execCommand(cmd);
				stdout = new StreamGobbler(session.getStdout());
				br = new BufferedReader(new InputStreamReader(stdout));
				String line;
				String firstLine="";
				int i=0;
				while ((line = br.readLine()) != null) {
					//System.out.println(line);
					sb.append(line);
					if(i==0){
						firstLine = line;
					}
					i++;
				}
				Thread.sleep(1000*1);
				//flag = true;
				if(firstLine.length()>0){
					result = firstLine.substring(0, firstLine.indexOf('/'));
				}
				log.info("执行命令完毕");
				//System.out.println(sb.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.info("执行命令异常");
				log.error(e.getMessage());
			} finally {
				session.close();
			}
		}
		return result;
	}
	
	
	
	private String getTarname(String name){
        name = name.replace(".tar.gz", "");
		name = name.substring(0, name.lastIndexOf('-'));
		name = name.substring(0, name.lastIndexOf('-'));
		System.out.println(name);
		return name;
	}
	
	public static void main(String[] args){
		//String name = "pas-cloud-service-demo-1.0.1-SNAPSHOT-assembly-2018080215195284198.tar.gz";
		//String path = "D:/eclipse64/devops/pascloud-devops-parent/pascloud-webapps/src/main/webapp/static/resources/pascode";
		//path = path + "/" + name;
		
		//ScpClientUtils scpClient = new ScpClientUtils("192.168.0.16","root","tccp@2012");
		
		//long startTime = System.currentTimeMillis();
		
		///scpClient.putFileToServer(path, "/home/pascloud");
		//String cmd = "tar -zxvf "+"/home/pascloud/"+name+" -C"+" "+Constants.PASCLOUD_HOME;
		
		//scpClient.execCommand(cmd);
		//String f = "pas-cloud-service-demo-1.0.1-SNAPSHOT/data/pasplus/config/dn8/xttzdjckgd.para";
		//System.out.println(f.substring(0, f.indexOf('/')));
		
		
		//long endTime   = System.currentTimeMillis();
		
		//long time = endTime - startTime;
		
		//System.out.println(time/1000);
		
		//name = name.replace(".tar.gz", "");
		
		//String[] ns = name.split("\\-");
		
		//name = name.substring(0, name.lastIndexOf('-'));
		//name = name.substring(0, name.lastIndexOf('-'));
		//System.out.println(name);
		
		String name = "pas-cloud-service-demo-1.0.1-SNAPSHOT-assembly-2018080215195284198.tar.gz";
		
		name = name.substring(0, name.lastIndexOf("-"));
		System.out.println(name);
	}
}
