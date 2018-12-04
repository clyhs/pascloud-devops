package com.pascloud.module.kettle.env;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.vfs2.FileUtil;
import org.pentaho.di.core.JndiUtil;
import org.pentaho.di.core.KettleClientEnvironment;
import org.pentaho.di.core.KettleVariablesList;
import org.pentaho.di.core.auth.AuthenticationConsumerPluginType;
import org.pentaho.di.core.auth.AuthenticationProviderPluginType;
import org.pentaho.di.core.compress.CompressionPluginType;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.lifecycle.KettleLifecycleSupport;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LogTablePluginType;
import org.pentaho.di.core.plugins.CartePluginType;
import org.pentaho.di.core.plugins.EnginePluginType;
import org.pentaho.di.core.plugins.ImportRulePluginType;
import org.pentaho.di.core.plugins.JobEntryDialogFragmentType;
import org.pentaho.di.core.plugins.JobEntryPluginType;
import org.pentaho.di.core.plugins.KettleLifecyclePluginType;
import org.pentaho.di.core.plugins.LifecyclePluginType;
import org.pentaho.di.core.plugins.PartitionerPluginType;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.PluginTypeInterface;
import org.pentaho.di.core.plugins.RepositoryPluginType;
import org.pentaho.di.core.plugins.StepDialogFragmentType;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.core.variables.Variables;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.step.RowDistributionPluginType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.constant.Constants;
import com.pascloud.module.kettle.constant.KettleConstant;
import com.pascloud.utils.FileUtils;

public class KettleEnvironment {
	
	private static Logger log = LoggerFactory.getLogger(KettleEnvironmentLinstener.class);
	
	private static Class<?> PKG = KettleConstant.class;

	private static Boolean initialized;
	
	public  static Properties props;
	
	public  static void init(boolean simpleJndi) throws KettleException {
		
		//log.info(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT));
		log.info("****************开始初始化kettle的环境变量****************");
		
		if (initialized == null) {
			//配置环境变量
			environmentInit();
			//创建kettle home目录
			KettleClientEnvironment.init();
			log.info("kettle home:"+KettleConstant.getKettleDirectory());
			//初始化jndi目录
			if (simpleJndi) {
				initJndi();
		    }
			//设置环境变量
			setSystemProperty();
			//日志初始化
			KettleLogStore.init();
			//初始化plugins
			initPlugins();
			
			PluginRegistry.init();
			// 初始化读取的变量列表。
	        KettleVariablesList.init();
	        // 初始化生命周期监听器
	        initLifecycleListeners();
	        
	        initialized = true;
			
		}
		
		log.info("****************结束初始化kettle的环境变量****************");
	}
	
	public static void createKettleHome() {
		
		// 尝试创建目录
		String directory = KettleConstant.getKettleDirectory();
		//log.info("kettle directory:"+directory);
		File dir = new File(directory);
		try {
			dir.mkdirs();
			// 创建属性文件 kettle.properties
			createDefaultKettleProperties(directory);
			
			setSystemProperty();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	private static void initPlugins(){
		List<PluginTypeInterface> pluginClasses = Arrays.asList(
			      RowDistributionPluginType.getInstance(),
			      StepPluginType.getInstance(),
			      StepDialogFragmentType.getInstance(),
			      PartitionerPluginType.getInstance(),
			      JobEntryPluginType.getInstance(),
			      JobEntryDialogFragmentType.getInstance(),
			      LogTablePluginType.getInstance(),
			      RepositoryPluginType.getInstance(),
			      LifecyclePluginType.getInstance(),
			      KettleLifecyclePluginType.getInstance(),
			      ImportRulePluginType.getInstance(),
			      CartePluginType.getInstance(),
			      CompressionPluginType.getInstance(),
			      AuthenticationProviderPluginType.getInstance(),
			      AuthenticationConsumerPluginType.getInstance(),
			      EnginePluginType.getInstance()
			    );
		pluginClasses.forEach( PluginRegistry::addPluginType );
	}
	
	private static void initJndi(){
		String jndiPath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+
				KettleConstant.KETTLE_JNDI;
		
		try {
			KettleConstant.JNDI_DIRECTORY = jndiPath;
			FileUtils.createOrExistsDir(jndiPath);
			JndiUtil.initJNDI();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	private static void initLifecycleListeners() throws KettleException {
		final KettleLifecycleSupport s = new KettleLifecycleSupport();
		s.onEnvironmentInit();
		// 注册关闭hook监听的调用OnExit()方法
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					s.onEnvironmentShutdown();
				} catch (Throwable t) {
					log.error(BaseMessages
							.getString(PKG,
									"LifecycleSupport.ErrorInvokingKettleEnvironmentShutdownListeners"));
					log.error(t.getMessage());
				}
			};
		});

	}
	
	private static void createDefaultKettleProperties(String directory) {

		String kpFile = directory + KettleConstant.FILE_SEPARATOR + KettleConstant.KETTLE_PROP_FILE;
		File file = new File(kpFile);
		if (!file.exists()) {
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(file);
				out.write(KettleConstant.getKettlePropertiesFileHeader().getBytes());
			} catch (IOException e) {
				log.error(BaseMessages
								.getString(
										PKG,
										"Props.Log.Error.UnableToCreateDefaultKettleProperties.Message",
										KettleConstant.KETTLE_PROP_FILE, kpFile));
				log.error(e.getMessage());
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						log.info(BaseMessages
										.getString(
												PKG,
												"Props.Log.Error.UnableToCreateDefaultKettleProperties.Message",
												KettleConstant.KETTLE_PROP_FILE, kpFile));
						log.error(e.getMessage());
					}
				}
			}
		}
	}
	
	/**
	 * 初始化kettle的环境变量
	 * @throws KettleException
	 */
	public static void environmentInit() throws KettleException {
		
		System.getProperties().put("KETTLE_HOME", System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)
				+KettleConstant.KETTLE_HOME);
		System.getProperties().put("KETTLE_PLUGIN_BASE_FOLDERS",
				System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)
				+KettleConstant.KETTLE_PLUGIN);
		System.getProperties().put("KETTLE_JS_HOME", System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)
				+KettleConstant.KETTLE_SCRIPT);
//		System.getProperties().put("KETTLE_JNDI_ROOT", System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)
//				+KettleConstant.KETTLE_JNDI);
		
		System.getProperties().put("KETTLE_JNDI_ROOT", System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+
				KettleConstant.KETTLE_JNDI);
		
		System.getProperties()
				.put(KettleConstant.INTERNAL_VARIABLE_CLUSTER_SIZE, "1");
		System.getProperties().put(
				KettleConstant.INTERNAL_VARIABLE_SLAVE_SERVER_NUMBER, "0");
		System.getProperties().put(
				KettleConstant.INTERNAL_VARIABLE_SLAVE_SERVER_NAME,
				"slave-trans-name");
		System.getProperties().put(KettleConstant.INTERNAL_VARIABLE_STEP_COPYNR, "0");
		System.getProperties().put(KettleConstant.INTERNAL_VARIABLE_STEP_NAME,
				"step-name");
		System.getProperties().put(
				KettleConstant.INTERNAL_VARIABLE_STEP_PARTITION_ID, "partition-id");
		System.getProperties().put(
				KettleConstant.INTERNAL_VARIABLE_STEP_PARTITION_NR, "0");
		System.getProperties().put(
				KettleConstant.INTERNAL_VARIABLE_STEP_UNIQUE_COUNT, "1");
		System.getProperties().put(
				KettleConstant.INTERNAL_VARIABLE_STEP_UNIQUE_NUMBER, "0");
		
	}
	
	private static void setSystemProperty(){
		if (Thread.currentThread().getContextClassLoader() == null) {
			Thread.currentThread().setContextClassLoader(
					ClassLoader.getSystemClassLoader());
		}
		Map<?, ?> prop = readProperties();
		Variables variables = new Variables();
		for (Object key : prop.keySet()) {
			String variable = (String) key;
			String value = variables.environmentSubstitute((String) prop
					.get(key));
			variables.setVariable(variable, value);
		}
		for (String variable : variables.listVariables()) {
			System.setProperty(variable, variables.getVariable(variable));
		}
	}
	/**
	 * 读取.kettle/kettle.properties
	 * @return
	 */
	public static Properties readProperties() {
		Properties p = new Properties();
		
		try {
			String directory = KettleConstant.getKettleDirectory();
			String kpFile = directory + KettleConstant.FILE_SEPARATOR + KettleConstant.KETTLE_PROP_FILE;
			p.load(new FileInputStream(kpFile));
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return p;
	}
	
	public static String get(String key) {
		return props.getProperty(key);
	}

	public static void set(Properties p) {
		props = p;
	}
	

}
