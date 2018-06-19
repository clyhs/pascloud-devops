package com.pascloud.module.server.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.constant.Constants;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.vo.server.ServerVo;
import com.thoughtworks.xstream.XStream;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * 服务器接口
 * @author chenly
 *
 */
@Service
public class ServerService  {
	
    private static Logger log = LoggerFactory.getLogger(ServerService.class);
    
    private String m_server_file = "/server.xml";
	
	@Autowired
	private PasCloudConfig m_config;
	
	/**
	 * 获得全部服务器
	 * @return
	 */
	public List<ServerVo> getServers(){
		List<ServerVo> servers = new ArrayList<ServerVo>();
		String serverPath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVER_DIR()+this.m_server_file;
		File file = new File(serverPath);
        XStream xstream = new XStream(); 
        xstream.alias("server", ServerVo.class);
        servers =  (List<ServerVo>) xstream.fromXML(file);
        
        if(servers.size() > 0){
        	for(ServerVo vo:servers){
        		vo.setDockerVersion(getDockerVersion(vo));
        	}
        }
        
		return servers;
	}
	
    public String getMasterDockerUrl(){
		
    	String url="";
		List<ServerVo> servers = new ArrayList<ServerVo>();
		String serverPath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVER_DIR()+this.m_server_file;
		File file = new File(serverPath);
        XStream xstream = new XStream(); 
        xstream.alias("server", ServerVo.class);
        servers =  (List<ServerVo>) xstream.fromXML(file);
        ServerVo master = new ServerVo();
        if(servers.size()>0){
        	
        	for(ServerVo vo:servers){
        		if("master".equals(vo.getType())){
        			master=vo;
        		}
        	}
        }
        url ="http://"+ master.getIp()+":"+"2375";
		
		return url;
	}
    
    public ServerVo getByIP(String ip){
    	ServerVo vo = null;
    	List<ServerVo> servers = new ArrayList<ServerVo>();
		String serverPath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVER_DIR()+this.m_server_file;
		File file = new File(serverPath);
        XStream xstream = new XStream(); 
        xstream.alias("server", ServerVo.class);
        servers =  (List<ServerVo>) xstream.fromXML(file);
        if(servers.size()>0){
        	for(ServerVo v:servers){
        		if(v.getIp().equals(ip)){
        			vo = v;
        		}
        	}
        }
        return vo;
        
    }
    
    public List<ServerVo> getDataBaseServers(){
    	List<ServerVo> servers = new ArrayList<>();
    	List<ServerVo> result = new ArrayList<>();
    	String serverPath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVER_DIR()+this.m_server_file;
		File file = new File(serverPath);
        XStream xstream = new XStream(); 
        xstream.alias("server", ServerVo.class);
        servers =  (List<ServerVo>) xstream.fromXML(file);
        
        if(servers.size()>0){
        	for(ServerVo vo:servers){
        		if(vo.getTypeEnum() == 2){
        			result.add(vo);
        		}
        	}
        }
        
    	return result;
    }
    
    public List<ServerVo> getAppServers(){
    	List<ServerVo> servers = new ArrayList<>();
    	List<ServerVo> result = new ArrayList<>();
    	String serverPath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVER_DIR()+this.m_server_file;
		File file = new File(serverPath);
        XStream xstream = new XStream(); 
        xstream.alias("server", ServerVo.class);
        servers =  (List<ServerVo>) xstream.fromXML(file);
        
        if(servers.size()>0){
        	for(ServerVo vo:servers){
        		if(vo.getTypeEnum() == 1){
        			result.add(vo);
        		}
        	}
        }
        
    	return result;
    }
    
    private Connection getScpClientConn(String ip,String user,String password){
		Connection conn = null;
		ConnectionInfo info = null;
		Boolean flag = false;
		try{
			conn = new Connection(ip);
			if(null!=conn){
				info = conn.connect();
				if(null!=info){
					log.info("连接成功");
					flag = conn.authenticateWithPassword(user, password);
				}
			}else{
				log.info("连接失败");
			}
			if(flag){
				log.info("验证成功");
				return conn;
			}else{
				log.info("验证失败");
			}
		}catch(Exception e){
			log.info("连接异常");
			log.error(e.getMessage());
		}
		return null;
	} 
    
    private String getDockerVersion(ServerVo vo){
    	String version ="";
    	Connection conn = null;
    	InputStream stdout = null;
		BufferedReader br = null;
		Session session = null;
		StringBuffer sb = new StringBuffer();
    	try{
    		conn = getScpClientConn(vo.getIp(),vo.getUsername(),vo.getPassword());
    		if(null!=conn){
    			session = conn.openSession();
    			session.execCommand("docker -v");
    			stdout = new StreamGobbler(session.getStdout());
    			br = new BufferedReader(new InputStreamReader(stdout));
    			
    			while (true) {
    				String line = br.readLine();
    				if (line == null) {
    					break;
    				}
    				sb.append(line);
    			}
    			version = sb.toString();
    		}
    	}catch(Exception e){
    		log.error(e.getMessage());
    	}finally{
    		try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		try {
				stdout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		session.close();
    		conn.close();
    	}
    	
    	
    	return version;
    }

}
