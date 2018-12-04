package com.pascloud.module.kettle.serv;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.RepositoryPluginType;
import org.pentaho.di.repository.RepositoriesMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectory;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositoryMeta;
import org.pentaho.di.repository.filerep.KettleFileRepositoryMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pascloud.module.kettle.bean.RepositoryTree;
import com.pascloud.module.kettle.constant.KettleConstant;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.vo.result.ResultCommon;

/**
 * 资源仓库接口
 * @author chenly
 *
 */
@Service
public class KettleRepoService {
	
	private static Logger log = LoggerFactory.getLogger(KettleRepoService.class);
	
	/**
	 * 创建资源文件仓库
	 * @param id
	 * @param name
	 * @param desc
	 * @param baseDirectory
	 * @param isDefault
	 * @return
	 */
	public ResultCommon createKettleFileRepository(String id,String name,String desc,String baseDirectory,
			Boolean isDefault){
		ResultCommon result =new ResultCommon(PasCloudCode.SUCCESS);
		RepositoriesMeta rsMeta = new RepositoriesMeta();
		KettleFileRepositoryMeta kfm = null;
		try {
			rsMeta.readData();
			kfm = new KettleFileRepositoryMeta();		
			kfm.setId(id);
			kfm.setName(name);
			kfm.setDescription(desc);
			kfm.setBaseDirectory(baseDirectory);
			kfm.setDefault(isDefault);
			rsMeta.addRepository(kfm);
			if(!checkFileRepository(name)){
				rsMeta.writeData();
			}
			
		}catch (Exception e) {
			log.error(e.getMessage());
			result =new ResultCommon(PasCloudCode.EXCEPTION.getCode(),e.getMessage());
		}finally{
		}
		return result;
	}
	
	public boolean checkRepository(String id){
		boolean flag = false;
		RepositoriesMeta rsMeta = new RepositoriesMeta();
		RepositoryMeta   repMeta  = null;
		try {
			log.info(KettleConstant.getKettleUserRepositoriesFile());
			//rsMeta.readDataFromInputStream(new FileInputStream(new File(KettleConstant.getKettleUserRepositoriesFile())));
			rsMeta.readData();
			log.info(rsMeta.nrRepositories()+"");
			
			repMeta = rsMeta.findRepositoryById(id);
			
			if(null == repMeta){
			}else{
				flag = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return flag;
	}
	
	/**
	 * 查一下是否已经存在资源文件仓库
	 * @param name
	 * @return
	 */
	public boolean checkFileRepository(String name){
		boolean flag = false;
		RepositoriesMeta rsMeta = new RepositoriesMeta();
		RepositoryMeta   repMeta  = null;
		try {
			rsMeta.readData();
			repMeta = rsMeta.findRepository(name);
			
			if(null == repMeta){
			}else{
				flag = true;
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}
	
	/**
	 * 查一下是否已经存在数据库的资源仓库
	 * @param name
	 * @return
	 */
	private boolean checkDatabaseRepository(String name){
		boolean flag = false;
		RepositoriesMeta rsMeta = new RepositoriesMeta();
		DatabaseMeta dm = new DatabaseMeta();
		try {
			rsMeta.readData();
			dm = rsMeta.searchDatabase(name);
			if(null==dm){
				
			}else{
				flag = true;
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}
	
	public boolean checkDatabaseConnection(DatabaseMeta dm){
		boolean flag = false;
		try{
			String result = dm.testConnection();
			//System.out.println(result);
			if(result.startsWith("错误连接数据库")){
				log.info("连接失败");
			}
			if(result.startsWith("正确连接到数据库")){
				flag = true;
				log.info("连接成功");
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return flag;
		
	}
	
	/**
	 * 获取所有的资源仓库
	 * @return
	 */
	public List<RepositoryMeta> getRepository(){
		List<RepositoryMeta> repos = new ArrayList<>();
		RepositoriesMeta rms = new RepositoriesMeta();
		
		System.out.println("*****reps start*****");
		try {
			rms.readData();
			
			for (int j = 0; j < rms.nrRepositories(); j++){
				RepositoryMeta rm =  rms.getRepository(j);
				repos.add(rm);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return repos;
	}
	
	/**
	 * 连接资源仓库
	 * @param repName
	 * @param username
	 * @param password
	 * @return
	 */
	public Repository connectRepository(String repName,String username,String password){
		RepositoriesMeta rsMeta = new RepositoriesMeta();
		RepositoryMeta   repMeta  = null;
		Repository       rep  = null;
		PluginRegistry   pluginRep = null;   
		RepositoryPluginType rpy = RepositoryPluginType.getInstance();
		
		try {
			rsMeta.readData();
			repMeta = rsMeta.findRepository(repName);
			pluginRep = PluginRegistry.getInstance();
			
			rep =  pluginRep.loadClass(RepositoryPluginType.class, repMeta, Repository.class); 
			rep.init(repMeta);
			rep.connect(username, password);
			if(rep.isConnected()){
				return rep;
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
		
	}
	
	/**
	 * 获取RepositoryDirectoryInterface
	 * @param rep
	 * @param filePath
	 * @return
	 */
	private RepositoryDirectoryInterface getRepositoryDirectoryInterface(Repository rep, String filePath){
		RepositoryDirectoryInterface tree = null;
		try{
			tree = rep.loadRepositoryDirectoryTree();
			for(int i=0;i<tree.getNrSubdirectories();i++){
				RepositoryDirectory subdir = tree.getSubdirectory(i); 		
			}
			if ((filePath != null) && (!"/".equals(filePath)) && (!"".equals(filePath))){
				tree = rep.findDirectory(filePath);
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return tree;
    }
	
	/**
	 * 获取资源树
	 * @param repository
	 * @return
	 */
	public RepositoryTree getfilefromRepository(Repository repository){
		RepositoryDirectoryInterface rdi = null;
		RepositoryTree rt = new RepositoryTree();
		try {
			rdi = repository.loadRepositoryDirectoryTree();
			rt = buildTree(rdi,repository);
			
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		return rt;
	}
	
	
	private RepositoryTree buildTree(RepositoryDirectoryInterface rdi,Repository rep){
		RepositoryTree rt = new RepositoryTree();
		List<String> filenames = new ArrayList<String>();
		rt.setName(rdi.getName());
		String spath = rdi.getPath();
		rt.setPath(spath);
		try {
			for(String jobname:rep.getJobNames(rdi.getObjectId(), true)){
				filenames.add(jobname+".ktj");
			}
			for(String tranname:rep.getTransformationNames(rdi.getObjectId(), true)){
				filenames.add(tranname+".ktr");
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
		} catch (KettleException e) {
			log.error(e.getMessage());
		}
		
		return rt;
	}
	
	

}
