package com.pascloud.module.kettle.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.RepositoryPluginType;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoriesMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectory;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositoryElementMetaInterface;
import org.pentaho.di.repository.RepositoryMeta;
import org.pentaho.di.repository.filerep.KettleFileRepositoryMeta;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import com.pascloud.module.kettle.bean.RepositoryTree;



/**
 * 
 * @author chenly 
 *
 * @version createtime:2016-6-27 上午10:05:12
 */
public class KettleRepositoryService {
	
	{
		try {
			KettleEnvironment.init();
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public DatabaseMeta initDatabase(){
		DatabaseMeta dm = new DatabaseMeta();
		dm.setDBName("kettle");
		dm.setDBPort("3306");
		dm.setHostname("localhost");
		dm.setServername("localhost");
		dm.setDatabaseType("MYSQL");
		dm.setName("localhost");
		dm.setUsername("root");
		dm.setPassword("123456");
		dm.setStreamingResults(true);
		dm.setAccessType(0);
		dm.setDataTablespace("");
		dm.setIndexTablespace("");
		return dm;
	} 
	
	public Properties initDatabaseProperties(){
		Properties p = new Properties();
		p.setProperty("EXTRA_OPTION_MYSQL.defaultFetchSize", "500");
		p.setProperty("EXTRA_OPTION_MYSQL.useCursorFetch", "true");
		p.setProperty("FORCE_IDENTIFIERS_TO_LOWERCASE", "N");
		p.setProperty("FORCE_IDENTIFIERS_TO_UPPERCASE", "N");
		p.setProperty("IS_CLUSTERED", "N");
		p.setProperty("PORT_NUMBER", "3306");
		p.setProperty("PRESERVE_RESERVED_WORD_CASE", "Y");
		p.setProperty("QUOTE_ALL_FIELDS", "N");
		p.setProperty("STREAM_RESULTS", "Y");
		p.setProperty("SUPPORTS_BOOLEAN_DATA_TYPE", "Y");
		p.setProperty("SUPPORTS_TIMESTAMP_DATA_TYPE", "Y");
		p.setProperty("USE_POOLING", "N");
		return p;
	}
	
	public void createKettleFileRepository(String repName,String desc,String baseDirectory){
		RepositoriesMeta rsMeta = new RepositoriesMeta();
		KettleFileRepositoryMeta kfm = null;
		//KettleFileRepository rep = new KettleFileRepository();  
		System.out.println("*****repository create start*****");
		int i = 0;
		try {
			rsMeta.readData();
			kfm = new KettleFileRepositoryMeta();		
			kfm.setId("KettleFileRepository");
			kfm.setName(repName);
			kfm.setDescription(desc);
			kfm.setBaseDirectory(baseDirectory);
			
			
			rsMeta.addRepository(kfm);
			if(!checkFileRepository(repName)){
				System.out.println("*****repository is creating*****");
				rsMeta.writeData();
			}else{
				System.out.println("*****repository had created*****");
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("*****repository create end*****");
	}
	
	public void createDatabaseRepository(DatabaseMeta dm,Properties p){
		RepositoriesMeta rsMeta = new RepositoriesMeta();
		System.out.println("*****data repository create start*****");
		try {
			//如果不先读出数据，后面写入会覆盖掉
			rsMeta.readData();
			dm.setAttributes(p);
			rsMeta.addDatabase(dm);
			if(!checkDatabaseRepository(dm.getName())){
				rsMeta.writeData();
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("*****data repository create end*****");
	}
	
	public void createKettleDatabaseRepository(String repName,DatabaseMeta dm,Properties p){
		KettleDatabaseRepositoryMeta kdpm = new KettleDatabaseRepositoryMeta();
		RepositoriesMeta rsMeta = new RepositoriesMeta();
		System.out.println("*****data file repository create start*****");
		try {
			//如果不先读出数据，后面写入会覆盖掉
			rsMeta.readData();
			dm.setAttributes(p);
			kdpm.setConnection(dm);
			kdpm.setId("KettleDatabaseRepository");
			kdpm.setName(repName);
			rsMeta.addRepository(kdpm);
			
			if(!checkFileRepository(repName)){
				System.out.println("*****data file repository is creating*****");
				rsMeta.writeData();
			}else{
				System.out.println("*****data file repository had created*****");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("*****data file repository create end*****");
	}
	
	public void getRepositories(){
		ArrayList list = new ArrayList();
		RepositoriesMeta rms = new RepositoriesMeta();
		int i = 0;
		System.out.println("*****reps start*****");
		try {
			rms.readData();
		    i= rms.nrRepositories();
			
			for (int j = 0; j < i; j++){
				RepositoryMeta rm =  rms.getRepository(j);
				String name = rm.getName();
				String id = rm.getId();
				System.out.println(id);
				System.out.println(name);
				list.add(name);
			}
		} catch (Exception e) {
		}
		System.out.println("*****reps end*****");
	}
	
	public Repository getRepository(String repName,String username,String password){
		RepositoriesMeta rsMeta = new RepositoriesMeta();
		RepositoryMeta   repMeta  = null;
		Repository       rep  = null;
		PluginRegistry   pluginRep = null;   
		RepositoryPluginType rpy = RepositoryPluginType.getInstance();
		System.out.println("*****repository start*****");
		try {
			rsMeta.readData();
			repMeta = rsMeta.findRepository(repName);
			pluginRep = PluginRegistry.getInstance();
			
			rep =  pluginRep.loadClass(RepositoryPluginType.class, repMeta, Repository.class); 
			rep.init(repMeta);
			rep.connect(username, password);
			System.out.println("*****repository connected:"+rep.isConnected()+"*****");
			//rep.isConnected();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("*****repository end*****");
		return rep;
		
	}
	
	public void getVariables(){
		System.out.println("*****var start*****");
		for(String s:EnvUtil.getEnvironmentVariablesForRuntimeExec()){
			
			String[] vs = s.split("=");
			
			System.out.println(s);
		}
		System.out.println("*****var end*****");
	}
	
	private static boolean checkDatabaseRepository(String dataName){
		boolean flag = false;
		RepositoriesMeta rsMeta = new RepositoriesMeta();
		DatabaseMeta dm = new DatabaseMeta();
		System.out.println("*****check data repository start*****");
		try {
			rsMeta.readData();
			dm = rsMeta.searchDatabase(dataName);
			if(null==dm){
				
			}else{
				flag = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("*****check data repository end*****");
		return flag;
	}
	
	private static boolean checkDatabaseConnection(DatabaseMeta dm){
		boolean flag = false;
		try{
			String result = dm.testConnection();
			//System.out.println(result);
			if(result.startsWith("错误连接数据库")){
				System.out.println("测试连接失败");
			}
			if(result.startsWith("正确连接到数据库")){
				flag = true;
				System.out.println("测试连接成功");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
		
	}
	
	public static boolean checkFileRepository(String repName){
		boolean flag = false;
		RepositoriesMeta rsMeta = new RepositoriesMeta();
		RepositoryMeta   repMeta  = null;
		System.out.println("*****check repository start*****");
		try {
			rsMeta.readData();
			repMeta = rsMeta.findRepository(repName);
			if(null == repMeta){
				
			}else{
				flag = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("*****check repository end*****");
		return flag;
	}
	
	public RepositoryTree getfilefromRepository(Repository repository){
		RepositoryDirectoryInterface rdi = null;
		RepositoryTree rt = new RepositoryTree();
		try {
			rdi = repository.loadRepositoryDirectoryTree();
			rt = buildTree(rdi,repository);
			
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rt;
	}
	
	private RepositoryTree buildTree(RepositoryDirectoryInterface rdi,Repository rep){
		RepositoryTree rt = new RepositoryTree();
		List<String> filenames = new ArrayList<String>();
		//rt.setObjId(rdi.getObjectId());
		rt.setName(rdi.getName());
		//System.out.println(rt.getName());
		String spath = rdi.getPath();
		rt.setPath(spath);
		//rt.setRdi(rdi);
		//System.out.println(spath);
		
		try {
			for(String jobname:rep.getJobNames(rdi.getObjectId(), true)){
				filenames.add(jobname+".ktj");
				//System.out.println("filename:"+jobname);
			}
			for(String tranname:rep.getTransformationNames(rdi.getObjectId(), true)){
				//System.out.println("filename:"+tranname);
				filenames.add(tranname+".ktr");
			}
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		rt.setFilenames(filenames);
		
		if(rdi.getChildren().size()>0){
			List<RepositoryTree> childrens = new ArrayList<RepositoryTree>();
			for(RepositoryDirectoryInterface r:rdi.getChildren()){
				RepositoryTree child = new RepositoryTree();
				child = buildTree(r,rep);
				childrens.add(child);
			}
			rt.setChildrens(childrens);
		}
		return rt;
	}
	
	public RepositoryDirectoryInterface getDirectory(Repository rep, String filePath){
		RepositoryDirectoryInterface tree = null;
		try{
			tree = rep.loadRepositoryDirectoryTree();
			for(int i=0;i<tree.getNrSubdirectories();i++){
				RepositoryDirectory subdir = tree.getSubdirectory(i); 
	            //System.out.println(" id " + subdir.getObjectId() + " name " + subdir.getName()); 			
			}
			if ((filePath != null) && (!"/".equals(filePath)) && (!"".equals(filePath))){
				tree = rep.findDirectory(filePath);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return tree;
    }

}