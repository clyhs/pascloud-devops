package com.pascloud.module.kettle.constant;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.vfs2.FileUtil;
import org.eclipse.jetty.util.log.Log;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.constant.Constants;
import com.pascloud.utils.FileUtils;

public class KettleConstant extends Const {
	
	public static Logger log = LoggerFactory.getLogger(KettleConstant.class);
	
	public static final String VERSION = "8.1.0.0";
	
	public static final String KETTLE_HOME = "/static/resources/kettle";
	
	public static final String KETTLE_PROP_FILE = "kettle.properties";
	
	public static String KETTLE_JNDI;
	
	
	public static String KETTLE_PLUGIN;
	public static String KETTLE_SCRIPT;
	public static LogLevel KETTLE_LOGLEVEL;
	
	static {
		KETTLE_PLUGIN = KETTLE_HOME+File.separator+"plugins";
		KETTLE_SCRIPT = KETTLE_HOME+File.separator+"script";
		KETTLE_JNDI =  KETTLE_HOME+File.separator+"simple-jndi";
		KETTLE_LOGLEVEL = logger("debug");
	}
	

	public static LogLevel logger(String level) {
		LogLevel logLevel = null;
		if ("basic".equals(level)) {
			logLevel = LogLevel.BASIC;
		} else if ("detail".equals(level)) {
			logLevel = LogLevel.DETAILED;
		} else if ("error".equals(level)) {
			logLevel = LogLevel.ERROR;
		} else if ("debug".equals(level)) {
			logLevel = LogLevel.DEBUG;
		} else if ("minimal".equals(level)) {
			logLevel = LogLevel.MINIMAL;
		} else if ("rowlevel".equals(level)) {
			logLevel = LogLevel.ROWLEVEL;
		} else {
			logLevel = KETTLE_LOGLEVEL;
		}
		return logLevel;
	}
	

}
