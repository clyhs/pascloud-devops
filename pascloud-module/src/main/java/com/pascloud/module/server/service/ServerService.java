package com.pascloud.module.server.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.bean.server.ServerVo;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.passervice.service.ConfigService;
import com.thoughtworks.xstream.XStream;

@Service
public class ServerService {
	
    private static Logger log = LoggerFactory.getLogger(ServerService.class);
    
    private String m_server_file = "/server.xml";
	
	@Autowired
	private PasCloudConfig m_config;
	
	public List<ServerVo> getServers(){
		
		List<ServerVo> servers = new ArrayList<ServerVo>();
		
		String serverPath = m_config.getPASCLOUD_SERVER_DIR()+this.m_server_file;
		
		File file = new File(serverPath);
        XStream xstream = new XStream(); 
        xstream.alias("server", ServerVo.class);
        servers =  (List<ServerVo>) xstream.fromXML(file);
		
		return servers;
	}

}