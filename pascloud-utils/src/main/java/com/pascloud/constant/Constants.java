package com.pascloud.constant;

import java.util.Map;

public class Constants {
	
	public static final String CHARSET = "UTF-8";
	
	public static String DB_MYSQL_DIRVERCLASS  = "com.mysql.jdbc.Driver";
	public static String DB_DB2L_DIRVERCLASS   = "com.ibm.db2.jcc.DB";
	public static String DB_ORACLE_DIRVERCLASS = "oracle.jdbc.driver.OracleDriver";
	
	public static String PASCLOUD_HOME = "/home/pascloud/";
	
	
	public static String PASCLOUD_SERVICE_DEMO = "pas-cloud-service-demo";
	public static String PASCLOUD_SERVICE_PASPM= "pas-cloud-service-paspm";
	
	public static String SHIPYARD_PROXY        = "shipyard-proxy";
	public static String PASCLOUD_SERVICE_DEMO_CONTAINER = "pascloud_service_demo";
	public static String PASCLOUD_SERVICE_PASPM_CONTAINER = "pascloud_service_paspm";
	
	public static String MYCAT_SCHEMA = "schema.xml";
	public static String MYCAT_SERVER = "server.xml";
	
	public static String WEB_APP_ROOT_DEFAULT = "webapp.root";
	
	public static String PASCLOUD_PUBLIC_DB = "dn0";
	
	public static String PASCLOUD_DEV_DEFAULT = "pasdev";
	
	public static String LINUX_ORACLE_HOME = "/home/oracle";
	public static String LINUX_ORACLE_INST = "/u01/app/oracle";
	public static String LINUX_ORACLE_VERSION = "11.2.0";
	
	public static String ORACLE_SID_PREEFIX="cpas";
	public static String ORACLE_SID_EX_PREEFIX="*[Cc][Pp][Aa][Ss]";
	
	public static String PS_REDIS_PORT="6379";
	public static String PS_MQ_PORT="61616";
	public static String PS_ZOOKEEPER_PORT="2181";
	public static String PS_MYSQL_PORT="3306";
	public static String PS_MYCAT_PORT="8066";
	
	public static final String MYCAT_EYE="/mycat-eye";
	
	public  static final String MYCATS = MYCAT_EYE+"/mycat";
    public  static final String MYCAT_JMX = MYCAT_EYE+"/mycat_jmx";
    public  static final String MYCAT_MYSQL = MYCAT_EYE+"/mysql";
    public  static final String MYCAT_SNMP = MYCAT_EYE+"/mycat_snmp";
    public  static final String MYCAT_PROCESSOR = MYCAT_EYE+"/mycat_processor";	
	
	
}
