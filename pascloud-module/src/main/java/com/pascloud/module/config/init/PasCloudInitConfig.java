package com.pascloud.module.config.init;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.PasCloudUtils;
import com.pascloud.utils.db.DataSourceUtils;
import com.pascloud.utils.redis.JedisPoolUtils;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.redis.RedisInfo;
import com.thoughtworks.xstream.XStream;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SuppressWarnings("rawtypes")
@Repository
public class PasCloudInitConfig implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger log = LoggerFactory.getLogger(PasCloudInitConfig.class);

	@Autowired
	private PasCloudConfig m_config;
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		if(event.getApplicationContext().getParent() == null){
			//initDataSource();
			//initRedisPool();
			
		}
		
	}
	
	private void initDataSource(){
		String database_dir = m_config.getPASCLOUD_DATABASE_DIR();
		if(null!=database_dir){
			log.info("初始化数据库--开始--");
			XStream xstream = new  XStream();
			xstream.alias("dbinfo", DBInfo.class);
			File database_file= new File(database_dir);
			if(FileUtils.createOrExistsDir(database_file)){
				List<File> files = FileUtils.listFilesInDirWithFilter(database_file, "xml");
				if(files.size()>0){
					for(File file : files){
						try{
							ComboPooledDataSource dataSource = new ComboPooledDataSource();
							DBInfo info = (DBInfo) xstream.fromXML(file);
							log.info(info.getUrl().trim()+info.getDbType());
							dataSource = new ComboPooledDataSource();  
					        dataSource.setUser(info.getUsername().trim());  
					        dataSource.setDataSourceName(info.getName());
					        dataSource.setPassword(info.getPassword().trim());  
					        dataSource.setJdbcUrl(info.getUrl().trim());  
					        dataSource.setDriverClass(info.getDriverClassName().trim());  
					        dataSource.setInitialPoolSize(5);  
					        dataSource.setMinPoolSize(5);  
					        dataSource.setMaxPoolSize(10);  
					        dataSource.setMaxStatements(0);  
					        dataSource.setMaxIdleTime(60);     
					        dataSource.getConnection();
					        DataSourceUtils.addDataSource(info.getId(), dataSource);
						} catch (Exception e) {  
					        e.printStackTrace();  
					    } 
						
					}
				}
			}
			
			log.info("初始化数据库--结束--");
		}
	} 
	
	private void initRedisPool(){
		String redis_dir = m_config.getPASCLOUD_REDIS_DIR();
		
		if(null!=redis_dir){
			log.info("初始化redis--开始--");
			XStream xstream = new  XStream();
			xstream.alias("redisInfo", RedisInfo.class);
			File redis_file= new File(redis_dir);
			if(FileUtils.createOrExistsDir(redis_file)){
				List<File> files = FileUtils.listFilesInDirWithFilter(redis_file, "xml");
				if(files.size()>0){
					for(File file : files){
						try{
							RedisInfo info = (RedisInfo) xstream.fromXML(file);
							log.info(info.getAddr().trim()+":"+info.getPort());
							
							JedisPoolConfig config = new JedisPoolConfig();
					        config.setMaxTotal(10);
					        config.setMaxIdle(5);
					        config.setMaxWaitMillis(15000);
					        config.setTestOnBorrow(true);
					        String id = info.getAddr().trim()+":"+ info.getPort();
					        JedisPool jedisPool = new JedisPool(config, info.getAddr().trim(), info.getPort());
					        JedisPoolUtils.addJedisPool(id, jedisPool);
					        
						} catch (Exception e) {  
					        e.printStackTrace();  
					    } 
						
					}
				}
			}
			
			log.info("初始化redis--结束--");
		}
	}

	

}
