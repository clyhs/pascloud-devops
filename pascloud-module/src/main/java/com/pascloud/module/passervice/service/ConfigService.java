package com.pascloud.module.passervice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.constant.Constants;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.utils.DBUtils;
import com.pascloud.utils.PropertiesUtil;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.pass.RedisVo;
import com.pascloud.vo.pass.ZookeeperVo;

@Service
public class ConfigService {
	
	private static Logger log = LoggerFactory.getLogger(ConfigService.class);
	
	@Autowired
	private PasCloudConfig m_config;
	
	private String m_config_file = "/config.properties";
	
	private String m_dubbo_file = "/dubbo.properties";
	
	private String m_db_file = "/db.properties";
	
	private String m_redis_file = "/redis.properties";
	
	private String m_zk_file = "/zk.properties";
	
	private String m_tomcat_config_file="/config.properties";
	
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
			String dbType,String dnName,String database,String en,String cn){
		PropertiesUtil p =new PropertiesUtil();
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_db_file);
		log.info("添加到服务的config.properties");
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
		setEnAndCn(dnName,p,en,cn);
		
	}
	
    public void setEnAndCn(String dnName,PropertiesUtil p,String en,String cn){
		p.setValueByKey(dnName+".en", en, "");
		p.setValueByKey(dnName+".cn", cn, "");
	}
    
    public String getEnByDnname(String dnName){
    	PropertiesUtil p =new PropertiesUtil();
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_db_file);
		
		return p.getValueByKey(dnName+".en");
    }
    
    public String getCnByDnname(String dnName){
    	PropertiesUtil p =new PropertiesUtil();
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_db_file);
		
		return p.getValueByKey(dnName+".cn");
    }
	
    public Boolean checkEnAndCn(String dnName,String en,String cn){
    	PropertiesUtil p =new PropertiesUtil();
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_db_file);
    	Boolean flag = false;
    	Map map = p.getByFuzzyKey("dn");
    	List<DBInfo> list = getDBFromConfig();
    	if(null!=list){
    		for(DBInfo d:list){
    			if(!d.getId().equals(dnName)){
    				if(d.getCn().equals(cn) || d.getEn().equals(en)){
    					flag = true;
    				}
    			}
    		}
    	}
    	return flag;
    }
    
	public List<DBInfo> getDBFromConfig(){
		log.info("从本地的db.properties查询的所有的数据库");
		List<DBInfo> dbs = new ArrayList<DBInfo>();
		PropertiesUtil p =new PropertiesUtil();
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_db_file);
		
		//System.out.println(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_config_file);
		
		Map map = p.getByFuzzyKey("dn");
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> obj = it.next();
			if(obj.getKey().contains("driverClass")){
			    //System.out.println(obj.getKey()+"="+p.getValueByKey(obj.getKey()));
			    String id = obj.getKey().split("\\.")[0];
			    DBInfo vo = new DBInfo();
				vo.setId(id);
				vo.setName(id);
				vo.setDriverClassName(p.getValueByKey(obj.getKey()));
				vo.setUrl(p.getValueByKey(id+".url"));
				vo.setPassword(p.getValueByKey(id+".username"));
				vo.setUsername(p.getValueByKey(id+".password"));
				vo.setEn(p.getValueByKey(id+".en"));
				vo.setCn(p.getValueByKey(id+".cn"));
				if(p.getValueByKey(id+".type").equals("ora")){
					vo.setDbType("oracle");
				}else{
					vo.setDbType(p.getValueByKey(id+".type"));
				}
				if(id.equals("dn0")){
					vo.setDesc("公共数据库实例");
				}else{
					vo.setDesc("租户数据库实例");
				}
				dbs.add(vo);
			}
			
		}
		Collections.sort(dbs, new Comparator<DBInfo>() {  
            @Override  
            public int compare(DBInfo o1, DBInfo o2) { 
                int i =getValue(o1)-getValue(o2);
                return i;  
            }
            private Integer getValue(DBInfo o){
            	return Integer.valueOf(o.getId().replace("dn", ""));
            }
        });  
		
		return dbs;
	}
	
	public DBInfo getDBByName(String sname){
		DBInfo vo = null;
		PropertiesUtil p =new PropertiesUtil();
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_db_file);
		
		Map map = p.getByFuzzyKey("dn");
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> obj = it.next();
			if(obj.getKey().contains("driverClass")){
			    System.out.println(obj.getKey()+"="+p.getValueByKey(obj.getKey()));
			    String id = obj.getKey().split("\\.")[0];
			    if(id.equals(sname)){
			    	vo = new DBInfo();
					vo.setId(id);
					vo.setName(id);
					vo.setDriverClassName(p.getValueByKey(obj.getKey()));
					vo.setUrl(p.getValueByKey(id+".url"));
					vo.setPassword(p.getValueByKey(id+".username"));
					vo.setUsername(p.getValueByKey(id+".password"));
					if(p.getValueByKey(id+".type").equals("ora")){
						vo.setDbType("oracle");
					}else{
						vo.setDbType(p.getValueByKey(id+".type"));
					}
					if(id.equals("dn0")){
						vo.setDesc("公共数据库实例");
					}else{
						vo.setDesc("租户数据库实例");
					}
			    }
			    
			}
			
		}
		
		
		return vo;
	}
	/**
	 * 删除DB配置
	 * @param dnName
	 */
	public Boolean delDBConfig(String dnName){
		Boolean flag =false;
		log.info("删除租户的数据节点");
		PropertiesUtil p =new PropertiesUtil();
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_db_file);
		String dbName = dnName+".";
		Map map = p.getByFuzzyKey(dbName);
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> obj = it.next();
			// System.out.println(obj.getKey()+p.getValueByKey(obj.getKey()));
			p.removeByKey(obj.getKey());
		}
		delMycatKey(dnName,p);
		log.info("删除租户的数据节点成功");
		flag = true;
		return flag;
	}
	
	/**
	 * dn0.driverClass=com.mysql.jdbc.Driver
     * dn0.url=jdbc\:mysql\://192.168.0.7\:3306/pascloud
     * dn0.username=root
     * dn0.password=root
	 * @param ip
	 * @param port
	 * @param user
	 * @param password
	 */
	public void setMysqlConfig(String ip,Integer port){
		PropertiesUtil p =new PropertiesUtil();
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_db_file);
		String url = "jdbc:mysql://"+ip+":"+port+"/pascloud";
		p.setValueByKey("dn0.url", url, "");
	}
	
	public void setRedisConfig(String ip,Integer port,String user,String password){
		PropertiesUtil p =new PropertiesUtil();
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_redis_file);
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
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_db_file);
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
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_config_file);
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
		log.info("set zk addr ");
		PropertiesUtil p =new PropertiesUtil();
		log.info(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_zk_file);
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_zk_file);
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
			if(dn.equals(dnName)){
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
			if(dn.equals(dnName)){
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
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_dubbo_file);
		p.setValueByKey("dubbo.application.name", appName, "");
	
	}
	
	public void setHomePath(String projectPath){
		PropertiesUtil p =new PropertiesUtil();
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_config_file);
		p.setValueByKey("pascloud.home", projectPath, "");
	}
	
	public void setDev(String flag){
		PropertiesUtil p =new PropertiesUtil();
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_SERVICE_DIR()+this.m_config_file);
		p.setValueByKey("pascloud.dev", flag, "");
	}
	
	public void setTomcatConfig(RedisVo redis,ZookeeperVo vo){
		PropertiesUtil p =new PropertiesUtil();
		p.load(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_TOMCAT()+this.m_tomcat_config_file);
		p.setValueByKey("redis.host", redis.getIp(), "");
		p.setValueByKey("redis.port", redis.getPort()+"", "");
		
		StringBuffer sb = new StringBuffer();
		sb.append("zookeeper://").append(vo.getIp());
		p.setValueByKey("zookeeper.host", sb.toString(), "");
		p.setValueByKey("zookeeper.port", vo.getPort()+"", "");
	}
	
	

}
