package com.pascloud.listen;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.pascloud.constant.Constants;

public class PasCloudListener implements ServletContextListener {
	
	//private final String WEB_APP_ROOT_DEFAULT = "webapp.root";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		String prefix = sce.getServletContext().getRealPath("/");
		if (prefix.endsWith(File.separator)) {
			prefix = prefix.substring(0, prefix.length() - 1);
			prefix = prefix.replaceAll("\\\\", "/");
		}
		System.out.println(prefix);
		System.setProperty(Constants.WEB_APP_ROOT_DEFAULT, prefix);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
