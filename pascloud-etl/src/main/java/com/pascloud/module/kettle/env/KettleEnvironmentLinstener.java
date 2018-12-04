package com.pascloud.module.kettle.env;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.pentaho.di.core.exception.KettleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.pascloud.constant.Constants;


public class KettleEnvironmentLinstener implements ServletContextListener  {
	
	private Logger log = LoggerFactory.getLogger(KettleEnvironmentLinstener.class);
	
	private WebApplicationContext springContext;  
	
	

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		try {
			KettleEnvironment.init(true);
			org.pentaho.di.core.KettleEnvironment.init(false);
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
}
