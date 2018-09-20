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
import com.pascloud.utils.FileUtils;
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
	
    @SuppressWarnings("unchecked")
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
        if(null!=master.getIp()){
        	url ="http://"+ master.getIp()+":"+"2375";
        }
        
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
    
    public synchronized Boolean addServer(ServerVo vo){
    	Boolean flag = false;
    	String serverPath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVER_DIR()+this.m_server_file;
		
    	List<ServerVo> lists= getServers();
    	
    	lists.add(vo);
    	String header =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
    	
    	File file = new File(serverPath);
        XStream xstream = new XStream(); 
        xstream.alias("server", ServerVo.class);
        String xml = xstream.toXML(lists);
        xml = header+xml;
        log.info("写入xml");
        if(!file.exists()){
        	FileUtils.createOrExistsFile(file);
        }
        FileUtils.writeFileFromString(file, xml, false);
        flag = true;
    	return flag;
    	
    }
    
    public synchronized Boolean delServer(String ip){
    	Boolean flag = false;
    	String serverPath =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_SERVER_DIR()+this.m_server_file;
		
    	List<ServerVo> lists= getServers();
    	List<ServerVo> result= new ArrayList<>();
    	
    	if(lists.size()>0){
    		for(ServerVo vo:lists){
    			if(vo.getIp().equals(ip)){
    				
    			}else{
    				result.add(vo);
    			}
    		}
    	}
    	
    	String header =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
    	
    	File file = new File(serverPath);
        XStream xstream = new XStream(); 
        xstream.alias("server", ServerVo.class);
        String xml = xstream.toXML(result);
        xml = header+xml;
        log.info("写入XML文件");
        if(!file.exists()){
        	FileUtils.createOrExistsFile(file);
        }
        FileUtils.writeFileFromString(file, xml, false);
        flag = true;
    	return flag;
    }
    
    public Boolean checkServerIsExist(String ip){
    	Boolean flag = false;
    	List<ServerVo> lists= getServers();
    	if(lists.size()>0){
    		for(ServerVo vo:lists){
    			if(vo.getIp().equals(ip)){
    				flag = true;
    			}
    		}
    	}
    	return flag;
    }
    
    public Boolean checkServerIsExist(String ip,String type){
    	Boolean flag = false;
    	List<ServerVo> lists= getServers();
    	if(lists.size()>0){
    		for(ServerVo vo:lists){
    			if(vo.getIp().equals(ip) && vo.getType().equals(type)){
    				flag = true;
    			}
    		}
    	}
    	return flag;
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
				conn = null;
				log.info("验证失败");
			}
		}catch(Exception e){
			conn = null;
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
    			if(null!=br){
    				br.close();
    			}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
    		try {
    			if(null!=stdout){
    				stdout.close();
    			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
    		try{
    			if(null!=session){
    				session.close();
    			}
    			if(null!=conn){
    				conn.close();
    			}
        		
    		}catch(Exception e){
    			
    		}
    		
    	}
    	
    	
    	return version;
    }

}
