package com.pascloud.module.config.init;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pascloud.constant.Constants;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.module.redis.service.RedisService;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.PasCloudUtils;
import com.pascloud.utils.db.DataSourceUtils;
import com.pascloud.utils.redis.JedisPoolUtils;
import com.pascloud.vo.database.DBInfo;
import com.pascloud.vo.pass.RedisVo;
import com.pascloud.vo.redis.RedisInfo;
import com.thoughtworks.xstream.XStream;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SuppressWarnings("rawtypes")
@Repository
public class PasCloudInitConfig implements BeanFactoryAware, ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger log = LoggerFactory.getLogger(PasCloudInitConfig.class);

	@Autowired
	private PasCloudConfig m_config;
	
	@Autowired
	private RedisService   m_redisService;
	
	private DefaultListableBeanFactory beanFactory;
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		if(true){
			//initDataSource();
			//initRedisPool();
			//m_redisService.initRedisServer();
			registerDataSource("dataSource3","jdbc:mysql://127.0.0.1:3306/dn3?useUnicode=true&amp;characterEncoding=UTF-8&amp;autoReconnect=true&amp;useSSL=false",
					"root","root","com.mysql.jdbc.Driver");
			try {
				registerSqlSessionFactoryBean("sqlSessionFactory3","dataSource3");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			registerMapperScannerConfigurer("msc3","sqlSessionFactory3");
			registerDataSourceTransactionManager("transactionManager3","dataSource3");
		}
		
	}
	
	private void registerDataSource(String name,String dbUrl,String dbUsername,String dbPassword,
			String driveClass){
		BeanDefinitionBuilder datasourceBeanDefinitionBuilder = BeanDefinitionBuilder  
                .genericBeanDefinition(BasicDataSource.class);
		datasourceBeanDefinitionBuilder.addPropertyValue("driverClassName", driveClass);  
		datasourceBeanDefinitionBuilder.addPropertyValue("url", dbUrl);  
		datasourceBeanDefinitionBuilder.addPropertyValue("username", dbUsername);  
		datasourceBeanDefinitionBuilder.addPropertyValue("password", dbPassword);  
		datasourceBeanDefinitionBuilder.addPropertyValue("maxActive", 20);  
		datasourceBeanDefinitionBuilder.addPropertyValue("maxIdle", 1); 
		datasourceBeanDefinitionBuilder.addPropertyValue("maxWait", 600);  
		datasourceBeanDefinitionBuilder.addPropertyValue("defaultAutoCommit", true); 
		datasourceBeanDefinitionBuilder.addPropertyValue("removeAbandoned", true);  
		datasourceBeanDefinitionBuilder.addPropertyValue("removeAbandonedTimeout", 60); 
		datasourceBeanDefinitionBuilder.addPropertyValue("logAbandoned", true); 
		datasourceBeanDefinitionBuilder.setDestroyMethodName("close");
        beanFactory.registerBeanDefinition(name,  
        		datasourceBeanDefinitionBuilder.getRawBeanDefinition());  
	}
	
	private void registerSqlSessionFactoryBean(String beanName,String dataSourceName) throws IOException{
		BeanDefinitionBuilder clientBeanDefinitionBuilder = BeanDefinitionBuilder  
                .genericBeanDefinition(SqlSessionFactoryBean.class);
		
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] rs = resolver.getResources("classpath*:com/pascloud/mapping/**/*Mapper.xml");

		clientBeanDefinitionBuilder.addPropertyValue("mapperLocations", rs);
		
		clientBeanDefinitionBuilder.addPropertyReference("dataSource", dataSourceName);
		
		
		PageHelper page = new PageHelper();
		Properties p = new Properties();
		p.setProperty("dialect", "mysql");
		p.setProperty("reasonable","true");
		page.setProperties(p);
		
		Interceptor[] interceptors = new Interceptor[1];
		interceptors[0] = page;
		
		clientBeanDefinitionBuilder.addPropertyValue("plugins", interceptors);
		
		beanFactory.registerBeanDefinition(beanName,  
				clientBeanDefinitionBuilder.getRawBeanDefinition()); 
		
	}
	
	public void registerMapperScannerConfigurer(String beanName,String sqlSessionFactoryBeanName){
		BeanDefinitionBuilder clientBeanDefinitionBuilder = BeanDefinitionBuilder  
                .genericBeanDefinition(MapperScannerConfigurer.class);
		clientBeanDefinitionBuilder.addPropertyValue("basePackage", "com.pascloud.mapper.**");
		

		clientBeanDefinitionBuilder.addPropertyValue("sqlSessionFactoryBeanName", sqlSessionFactoryBeanName);
	
		beanFactory.registerBeanDefinition(beanName,  
				clientBeanDefinitionBuilder.getRawBeanDefinition()); 
	}
	
	public void registerDataSourceTransactionManager(String beanName,String dataSourceName){
		BeanDefinitionBuilder clientBeanDefinitionBuilder = BeanDefinitionBuilder  
                .genericBeanDefinition(MapperScannerConfigurer.class);
		clientBeanDefinitionBuilder.addPropertyReference("dataSource", dataSourceName);
		beanFactory.registerBeanDefinition(beanName,  
				clientBeanDefinitionBuilder.getRawBeanDefinition()); 
	}


	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		this.beanFactory = (DefaultListableBeanFactory) beanFactory; 
	}
	
//	private void initDataSource(){
//		String database_dir =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_DATABASE_DIR();
//		if(null!=database_dir){
//			log.info("初始化数据库--开始--");
//			XStream xstream = new  XStream();
//			xstream.alias("dbinfo", DBInfo.class);
//			File database_file= new File(database_dir);
//			if(FileUtils.createOrExistsDir(database_file)){
//				List<File> files = FileUtils.listFilesInDirWithFilter(database_file, "xml");
//				if(files.size()>0){
//					for(File file : files){
//						try{
//							ComboPooledDataSource dataSource = new ComboPooledDataSource();
//							DBInfo info = (DBInfo) xstream.fromXML(file);
//							log.info(info.getUrl().trim()+info.getDbType());
//							dataSource = new ComboPooledDataSource();  
//					        dataSource.setUser(info.getUsername().trim());  
//					        dataSource.setDataSourceName(info.getName());
//					        dataSource.setPassword(info.getPassword().trim());  
//					        dataSource.setJdbcUrl(info.getUrl().trim());  
//					        dataSource.setDriverClass(info.getDriverClassName().trim());  
//					        dataSource.setInitialPoolSize(5);  
//					        dataSource.setMinPoolSize(5);  
//					        dataSource.setMaxPoolSize(10);  
//					        dataSource.setMaxStatements(0);  
//					        dataSource.setMaxIdleTime(60);     
//					        dataSource.getConnection();
//					        DataSourceUtils.addDataSource(info.getId(), dataSource);
//						} catch (Exception e) {  
//					        e.printStackTrace();  
//					    } 
//						
//					}
//				}
//			}
//			
//			log.info("初始化数据库--结束--");
//		}
//	} 
//	
//	
//	
//	private void initRedisPool(){
//		String redis_dir =System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+ m_config.getPASCLOUD_REDIS_DIR();
//		
//		if(null!=redis_dir){
//			log.info("初始化redis--开始--");
//			XStream xstream = new  XStream();
//			xstream.alias("redisInfo", RedisInfo.class);
//			File redis_file= new File(redis_dir);
//			if(FileUtils.createOrExistsDir(redis_file)){
//				List<File> files = FileUtils.listFilesInDirWithFilter(redis_file, "xml");
//				if(files.size()>0){
//					for(File file : files){
//						try{
//							RedisInfo info = (RedisInfo) xstream.fromXML(file);
//							log.info(info.getAddr().trim()+":"+info.getPort());
//							
//							JedisPoolConfig config = new JedisPoolConfig();
//					        config.setMaxTotal(10);
//					        config.setMaxIdle(5);
//					        config.setMaxWaitMillis(15000);
//					        config.setTestOnBorrow(true);
//					        String id = info.getAddr().trim()+":"+ info.getPort();
//					        JedisPool jedisPool = new JedisPool(config, info.getAddr().trim(), info.getPort());
//					        JedisPoolUtils.addJedisPool(id, jedisPool);
//					        
//						} catch (Exception e) {  
//					        e.printStackTrace();  
//					    } 
//						
//					}
//				}
//			}
//			
//			log.info("初始化redis--结束--");
//		}
//	}

	

}
