package com.pascloud.module.passervice.service;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.utils.DBUtils;
import com.pascloud.utils.PropertiesUtil;

@Service
public class ConfigService {
	
	private static Logger log = LoggerFactory.getLogger(ConfigService.class);
	
	@Autowired
	private PasCloudConfig m_config;
	
	private String m_config_file = "/config.properties";
	
	private String m_dubbo_file = "/dubbo.properties";
	
	
	/**
	 *添加DB配置（static/resources/service/conf/config.properties) 
	 *dn17.driverClass=oracle.jdbc.driver.OracleDriver
     *dn17.url=jdbc\:oracle\:thin\:@192.168.0.82\:1521\:cpas12
     *dn17.username=pas
     *dn17.password=pas
     *dn17.type=ora
	 * 
	 * @param ip
	 * @param port
	 * @param user
	 * @param password
	 * @param dbType
	 * @param dnName
	 * @param database
	 */
	public void addDBConfig(String ip,Integer port,String user,String password,
			String dbType,String dnName,String database){
		PropertiesUtil p =new PropertiesUtil();
		p.load(m_config.getPASCLOUD_SERVICE_DIR()+this.m_config_file);
		String url = DBUtils.getUrlByParams(dbType, ip, database, port);
		String driverClass = DBUtils.getDirverClassName(dbType);
		p.setValueByKey(dnName+".driverClass", driverClass, "");
		p.setValueByKey(dnName+".url", url, "");
		p.setValueByKey(dnName+".username", user, "");
		p.setValueByKey(dnName+".password", password, "");
		//p.setValueByKey(dnName+".type", value, note);
		if(dbType.equals("oracle")){
			p.setValueByKey(dnName+".type", "ora", "");
		}else{
			p.setValueByKey(dnName+".type", dbType, "");
		}
		addMycatKey(dnName,p);
		
	}
	/**
	 * 删除DB配置
	 * @param dnName
	 */
	public void delDBConfig(String dnName){
		PropertiesUtil p =new PropertiesUtil();
		p.load(m_config.getPASCLOUD_SERVICE_DIR()+this.m_config_file);
		Map map = p.getByFuzzyKey(dnName);
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> obj = it.next();
			// System.out.println(obj.getKey()+p.getValueByKey(obj.getKey()));
			p.removeByKey(obj.getKey());
		}
		delMycatKey(dnName,p);
	}
	
	public void updateRedisConfig(String ip,Integer port,String user,String password){
		PropertiesUtil p =new PropertiesUtil();
		p.load(m_config.getPASCLOUD_SERVICE_DIR()+this.m_config_file);
		p.setValueByKey("redis.host", ip, "");
		p.setValueByKey("redis.port", port+"", "");
		p.setValueByKey("redis.password", password, "");
		p.setValueByKey("redis.user", user, "");
	}
	
	/**
	 * db.mcat.driverClass=com.mysql.jdbc.Driver
	 * db.mcat.url=jdbc\:mysql\://192.168.0.7\:8066/alldb?autoReconnect\=true
	 * db.mcat.username=root
	 * db.mcat.password=root
	 * db.mcat.initialSize=5
	 * db.mcat.maxActive=500
	 * db.mcat.minIdle=50
	 * db.mcat.maxWait=60000
	 * db.mcat.removeAbandonedTimeout=180
	 * mycat.datanode=dn0,dn1,dn2,dn3,dn4,dn5,dn12,dn13,dn14,dn15,dn16,dn17
	 */
	public void setMycatConfig(String ip,String user,String password){
		PropertiesUtil p =new PropertiesUtil();
		p.load(m_config.getPASCLOUD_SERVICE_DIR()+this.m_config_file);
		String url = DBUtils.getUrlByParams("mysql", ip, "alldb", 8066);
		url = url + "?autoReconnect=true";
		p.setValueByKey("db.mcat.url", url, "");
		p.setValueByKey("db.mcat.username", user, "");
		p.setValueByKey("db.mcat.password", password, "");
	}
	
	/**
	 * mq.brokerUrl=tcp\://192.168.0.7\:61616
	 * @param ip
	 * @param port
	 */
	public void setMQConfig(String ip,Integer port){
		PropertiesUtil p =new PropertiesUtil();
		p.load(m_config.getPASCLOUD_SERVICE_DIR()+this.m_config_file);
		StringBuffer sb = new StringBuffer();
		sb.append("tcp://").append(ip).append(":").append(port);
		p.setValueByKey("mq.brokerUrl", sb.toString(), "");
	}
	
	/**
	 * zookeeper.host=zookeeper\://192.168.0.7
	 * zookeeper.port=2181
	 * @param ip
	 * @param port
	 */
	public void setZookeeperConfig(String ip,Integer port){
		PropertiesUtil p =new PropertiesUtil();
		p.load(m_config.getPASCLOUD_SERVICE_DIR()+this.m_config_file);
		StringBuffer sb = new StringBuffer();
		sb.append("zookeeper://").append(ip);
		p.setValueByKey("zookeeper.host", sb.toString(), "");
		p.setValueByKey("zookeeper.port", port+"", "");
	}
	
	private void addMycatKey(String dnName,PropertiesUtil p){
		String value = p.getValueByKey("mycat.datanode");
		String[] dnNames = value.split(",");
		Boolean isExist = false;
		for(String dn:dnNames){
			if(dn.equals(dnNames)){
				isExist = true;
			}
		}
		if(!isExist){
			value = value+","+dnName;
		}
		p.setValueByKey("mycat.datanode", value, "");
	}
	
	private void delMycatKey(String dnName,PropertiesUtil p){
		String value = p.getValueByKey("mycat.datanode");
		String[] dnNames = value.split(",");
		StringBuffer sb = new StringBuffer();
		for(String dn:dnNames){
			if(dn.equals(dnNames)){
			}else{
				sb.append(dn).append(",");
			}
		}
		String str = sb.toString();
		value = str.substring(0, str.length()-1);
		
		p.setValueByKey("mycat.datanode", value, "");
	}
	
	public void setApplicationName(String appName){
		log.info("setApplicationName "+appName);
		PropertiesUtil p =new PropertiesUtil();
		p.load(m_config.getPASCLOUD_SERVICE_DIR()+this.m_dubbo_file);
		p.setValueByKey("dubbo.application.name", appName, "");
	
	}
	
	public void setHomePath(String projectPath){
		PropertiesUtil p =new PropertiesUtil();
		p.load(m_config.getPASCLOUD_SERVICE_DIR()+this.m_config_file);
		p.setValueByKey("pascloud.home", projectPath, "");
	}

}
