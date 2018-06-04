package com.pascloud.module.server.service;

import java.io.File;
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

/**
 * 服务器接口
 * @author chenly
 *
 */
@Service
public class ServerService {
	
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
        		if(vo.getDesc().equals("数据库服务器")){
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
        		if(vo.getDesc().equals("应用服务器")){
        			result.add(vo);
        		}
        	}
        }
        
    	return result;
    }

}
