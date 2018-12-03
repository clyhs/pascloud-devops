package com.pascloud.module.kettle.service;
import java.util.ArrayList;
import java.util.Properties;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogChannel;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.RepositoryPluginType;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.repository.RepositoriesMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryMeta;
import org.pentaho.di.repository.filerep.KettleFileRepository;
import org.pentaho.di.repository.filerep.KettleFileRepositoryMeta;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.www.JobMap;
import org.pentaho.di.www.TransformationMap;

import com.pascloud.module.kettle.bean.RepositoryTree;
import com.pascloud.utils.JsonUtil;


/**
 * 
 * @author chenly 
 *
 * @version createtime:2016-6-24 下午12:08:20
 */
public class KettleUtils {
	
	private  static JobMap jobMap = new JobMap();
	private  static TransformationMap transMap = new TransformationMap();
	private  static LogChannel logChannel ;
	
	private  static DatabaseMeta databaseMeta;
	private  static Properties   databaseProperties;
	
	{
		try {
			KettleRepositoryService kps = new KettleRepositoryService();
			//KettleEnvironment.init();
			logChannel = new LogChannel("spoon");
			//getVariables();
			//getRepositories();
			//getRepository("eee","admin","admin");
			//kps.createKettleFileRepository("test","test");
			//createKettleDatabaseRepository("kettle02");
			databaseMeta = kps.initDatabase();
			databaseProperties = kps.initDatabaseProperties();
			//System.out.println(checkDatabaseRepository("localhost"));
			//checkDatabaseConnection(databaseMeta);
			
			//kps.createDatabaseRepository(databaseMeta,databaseProperties);
			
			//kps.createKettleDatabaseRepository("ddrr",databaseMeta,databaseProperties);
			
			Repository r =kps.getRepository("mydatarep","admin","admin");
			RepositoryTree rt = kps.getfilefromRepository(r);
			
			
			
			String json =JsonUtil.objectToJson(rt);
			System.out.println(json);
			KettleJobService kjs = new KettleJobService(kps);
			KettleTransService kts = new KettleTransService(kps);
			//kjs.execute("mydatarep","admin","admin", "/", "job01");
			kts.execute("mydatarep","admin","admin", "/", "tran01");
			
			//kjs.suspendJob("job01");
			//Thread.sleep(5000);
			//kjs.resumeJob("job01");
			kts.stopTrans("tran01");
			//kts.pauseTrans("tran01");
			
			//Thread.sleep(5000);
			//kts.resumeTrans("tran01");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	

	
	
	
	
	public static void main(String[] args){
		KettleUtils ku = new KettleUtils();
	}

}