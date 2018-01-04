package com.pascloud.module.config.init;

import java.io.File;
import java.util.List;

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
import com.pascloud.utils.db.DataSourceUtils;
import com.pascloud.vo.database.DBInfo;
import com.thoughtworks.xstream.XStream;

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
			initDataSource();
		}
		
	}
	
	private void initDataSource(){
		String database_dir = m_config.getPASCLOUD_DATABASE_DIR();
		if(null!=database_dir){
			log.info("初始化数据库--开始1--");
			log.info("初始化数据库--开始2--");
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
							log.info(info.getUrl().trim());
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

}
