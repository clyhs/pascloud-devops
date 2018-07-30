package com.pascloud.module.pasdev.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pas.cloud.studio.parameters.ImportParameters;
import com.pas.cloud.studio.parameters.ManageParameters;
import com.pas.cloud.studio.parameters.Parameter;
import com.pas.cloud.studio.parameters.Parameters;
import com.pas.cloud.studio.parameters.YjgxParameters;
import com.pascloud.constant.Constants;
import com.pascloud.module.common.service.AbstractBaseService;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.database.service.DataBaseService;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.module.redis.service.RedisService;
import com.pascloud.module.server.service.ServerService;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.ZipUtils;
import com.pascloud.utils.gzip.GZipUtils;
import com.pascloud.utils.gzip.TarUtils;
import com.pascloud.utils.redis.SerializeUtils;
import com.pascloud.utils.xml.XmlParser;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.mversion.XtcdVo;
import com.pascloud.vo.pasdev.PasfileVo;
import com.pascloud.vo.pass.RedisVo;
import com.pascloud.vo.result.ResultCommon;
import com.pascloud.vo.result.ResultPageVo;
import com.pascloud.vo.server.ServerVo;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * pas+版本管理服务
 * @author chenly
 *
 */
@Service
public class PasdevService extends AbstractBaseService {
	
	private static Logger log = LoggerFactory.getLogger(PasdevService.class);
	
	@Autowired
	private PasCloudConfig     m_config;
	
	@Autowired
	private PasService         m_pasService;
	
	@Autowired
	private ServerService      m_serverService;
	
	
	@Autowired
	private DataBaseService    m_databaseService;
	
	@Autowired
	private RedisService       m_redisService;
	
	/**
	 * 根据目录ID(分行代号)进行遍历目录
	 * @param dirId
	 * @return
	 */
	public List<PasfileVo> getPasdevFiles(String dirId){
		List<PasfileVo> result = new ArrayList<>();
		List<File> files = new ArrayList<File>();
		files = FileUtils.listFilesInDirWithFilter(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()+"/"+dirId, ".xml", false);
		//System.out.println(m_config.getPASCLOUD_DEV_DIR());
		if(files.size()>0){
			Iterator<File> it = files.iterator();
			while(it.hasNext()){
				File f = it.next();
				PasfileVo vo = new PasfileVo();
				vo.setFilename(f.getName());
				String funId = FileUtils.getFileNameNoExtension(f);
				vo.setFunId(funId);
				vo.setSuffix(FileUtils.getFileExtension(f));
				vo.setFilepara(funId+".para");
				vo.setFilepath(f.getAbsolutePath());
				vo.setFhdh(dirId);
				parserPasfile(f.getAbsolutePath(),vo);
				result.add(vo);
			}
		}
		
		return result;
	}
	
	public List<PasfileVo> getPasdevFiles(List<File> files,String dirId){
		List<PasfileVo> result = new ArrayList<>();
		//System.out.println(m_config.getPASCLOUD_DEV_DIR());
		if(files.size()>0){
			Iterator<File> it = files.iterator();
			while(it.hasNext()){
				File f = it.next();
				PasfileVo vo = new PasfileVo();
				vo.setFilename(f.getName());
				String funId = FileUtils.getFileNameNoExtension(f);
				vo.setFunId(funId);
				vo.setSuffix(FileUtils.getFileExtension(f));
				vo.setFilepara(funId+".para");
				vo.setFilepath(f.getAbsolutePath());
				vo.setFhdh(dirId);
				parserPasfile(f.getAbsolutePath(),vo);
				result.add(vo);
			}
		}
		
		return result;
	}
	
	public List<PasfileVo> getPasdevFiles(List<File> files){
		List<PasfileVo> result = new ArrayList<>();
		//System.out.println(m_config.getPASCLOUD_DEV_DIR());
		if(files.size()>0){
			Iterator<File> it = files.iterator();
			while(it.hasNext()){
				File f = it.next();
				PasfileVo vo = new PasfileVo();
				vo.setFilename(f.getName());
				String funId = FileUtils.getFileNameNoExtension(f);
				vo.setFunId(funId);
				vo.setSuffix(FileUtils.getFileExtension(f));
				vo.setFilepara(funId+".para");
				vo.setFilepath(f.getAbsolutePath());
				parserPasfile(f.getAbsolutePath(),vo);
				String dId = parserPasfile(f.getAbsolutePath());
				vo.setFhdh(dId);
				result.add(vo);
			}
		}
		
		return result;
	}
	
	public ResultPageVo<PasfileVo> getPageData(String dirId,Integer pageNo,Integer pageSize){
		ResultPageVo<PasfileVo> result = null;
		List<PasfileVo> list = new ArrayList<>();
		java.sql.Connection conn = null;
		Integer start = 0;
		Integer totals = 0;
		try{
			conn = m_databaseService.getConnectionById(Constants.PASCLOUD_PUBLIC_DB);
			QueryRunner qRunner = new QueryRunner(); 
			start = (pageNo - 1) * pageSize;
			String sql = "select * from xtb_pasfile where fhdh=? order by id desc limit ?,?";
			String tSql= "select count(1) from xtb_pasfile where fhdh=?";
			
			Object[] tparams = {dirId};
			Object[] params = {dirId,start,pageSize};
			
			Number num =  (Number)qRunner.query(conn,tSql, new ScalarHandler(),tparams);
			if(null!=num){
				totals = num.intValue();
				if(totals>0){
					list =  qRunner.query(conn,sql, new BeanListHandler<PasfileVo>(PasfileVo.class),params);
				}
			}
			result = new ResultPageVo<PasfileVo>(PasCloudCode.SUCCESS);
			result.setTotal(totals);
			result.setRows(list);
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			result = new ResultPageVo<PasfileVo>(PasCloudCode.EXCEPTION);
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return result;
	}
	
	public ResultPageVo<PasfileVo> getAllData(String dirId){
		ResultPageVo<PasfileVo> result = null;
		List<PasfileVo> list = new ArrayList<>();
		java.sql.Connection conn = null;
		
		Integer totals = 0;
		try{
			conn = m_databaseService.getConnectionById(Constants.PASCLOUD_PUBLIC_DB);
			QueryRunner qRunner = new QueryRunner(); 
			
			String sql = "select * from xtb_pasfile where fhdh=?";
			String tSql= "select count(1) from xtb_pasfile where fhdh=?";
			
			Object[] params = {dirId};
			
			Number num =  (Number)qRunner.query(conn,tSql, new ScalarHandler(),params);
			if(null!=num){
				totals = num.intValue();
				if(totals>0){
					list =  qRunner.query(conn,sql, new BeanListHandler<PasfileVo>(PasfileVo.class),params);
				}
			}
			result = new ResultPageVo<PasfileVo>(PasCloudCode.SUCCESS);
			result.setTotal(totals);
			result.setRows(list);
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			result = new ResultPageVo<PasfileVo>(PasCloudCode.EXCEPTION);
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return result;
	}
	
	public ResultCommon delPasfileFromDB(String dirId){
		ResultCommon result = null;
		java.sql.Connection conn = null;
		try{
			conn = m_databaseService.getConnectionById(Constants.PASCLOUD_PUBLIC_DB);
			QueryRunner qRunner = new QueryRunner(); 
		
			String delSql = "delete from xtb_pasfile where fhdh=?";
			Object[] delparams = {dirId};
			qRunner.update(conn, delSql, delparams);
			result = new ResultCommon(PasCloudCode.SUCCESS);
		} catch (SQLException e) {
			log.error(e.getMessage());
			result = new ResultCommon(PasCloudCode.EXCEPTION);
		}finally{
			try {
				if(null!=conn){
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return result;
	}
	
	public ResultCommon sysPasfileToDB(String dirId){
		ResultCommon result = null;
		java.sql.Connection conn = null;
		List<PasfileVo> pfs = new ArrayList<>();
		
		try{
			conn = m_databaseService.getConnectionById(Constants.PASCLOUD_PUBLIC_DB);
			QueryRunner qRunner = new QueryRunner(); 
			String sql = "insert into xtb_pasfile(`funId`,`title`,`type`,`version`,`desc`,`pid`,`createTime`,`fhdh`)"
					+ "values(?,?,?,?,?,?,?,?)";
			String delSql = "delete from xtb_pasfile where fhdh=?";
			
			pfs = getPasdevFiles(dirId);
			
			
			
			if(pfs.size()>0){
				Object[] delparams = {dirId};
				log.info("先"+dirId+"清空xtb_pasfile表");
				qRunner.update(conn, delSql, delparams);
				Object[][] params = new Object[pfs.size()][];
				for(int i=0;i<pfs.size();i++){
					PasfileVo vo = pfs.get(i);
					params[i] = new Object[]{vo.getFunId(),vo.getTitle(),vo.getType(),
							vo.getVersion(),vo.getDesc(),vo.getPid(),new Date(),vo.getFhdh()};
				}
				qRunner.batch(conn, sql, params);
				
				sysPasfileToRedis(dirId,pfs);
			}
			
			result = new ResultCommon(PasCloudCode.SUCCESS);
		} catch (SQLException e) {
			log.error(e.getMessage());
			result = new ResultCommon(PasCloudCode.EXCEPTION);
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return result;
	}
	
	
	public ResultCommon sysPasfileToDB(List<PasfileVo> pfs){
		ResultCommon result = null;
		java.sql.Connection conn = null;
		//List<PasfileVo> pfs = new ArrayList<>();
		String dId = "";
		try{
			conn = m_databaseService.getConnectionById(Constants.PASCLOUD_PUBLIC_DB);
			QueryRunner qRunner = new QueryRunner(); 
			String sql = "insert into xtb_pasfile(`funId`,`title`,`type`,`version`,`desc`,`pid`,`createTime`,`fhdh`)"
					+ "values(?,?,?,?,?,?,?,?)";
			String delSql = "delete from xtb_pasfile where fhdh=? and funId=?";
			
			//pfs = getPasdevFiles(files,dirId);
			
			if(pfs.size()>0){
				Object[][] delparams = new Object[pfs.size()][];
				
				//qRunner.update(conn, delSql, delparams);

				Object[][] params = new Object[pfs.size()][];
				for(int i=0;i<pfs.size();i++){
					PasfileVo vo = pfs.get(i);
					
					delparams[i] = new Object[]{vo.getFhdh(),vo.getFunId()};
					params[i] = new Object[]{vo.getFunId(),vo.getTitle(),vo.getType(),
							vo.getVersion(),vo.getDesc(),vo.getPid(),new Date(),vo.getFhdh()};
					dId = vo.getFhdh();
					
				}
				
				log.info("清空xtb_pasfile表");
				qRunner.batch(conn, delSql, delparams);
				log.info("插入xtb_pasfile表");
				qRunner.batch(conn, sql, params);
				
			}
			
			result = new ResultCommon(PasCloudCode.SUCCESS);
			result.setDesc(dId);
		} catch (SQLException e) {
			log.error(e.getMessage());
			result = new ResultCommon(PasCloudCode.EXCEPTION);
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return result;
	}
	
	
	private void parserPasfile(String filepath,PasfileVo vo){
		log.info(filepath);
		Document doc = XmlParser.getDocument(filepath);
		Element root = doc.getRootElement();
		vo.setTitle(root.attributeValue("title"));
		vo.setType(root.attributeValue("type"));
		if(null == root.attributeValue("version")){
			
		}else{
			vo.setVersion(root.attributeValue("version"));
		}
		vo.setPid(root.attributeValue("pid"));
		vo.setDesc(root.attributeValue("desc"));
	}
	
	private String parserPasfile(String filepath){
		log.info(filepath);
		Document doc = XmlParser.getDocument(filepath);
		Element root = doc.getRootElement();
		String version = "";
		version = root.attributeValue("version");
		
		String[] vers = version.split("\\.");
		
		Integer  len = vers.length;
		
		String   dbNum = vers[2];
		String   dirId = "dn"+dbNum;
		log.info("文件的租户号为："+dirId);
		return dirId;
		
	}
	
	public synchronized Integer modifyPasdevFilesWidthID(String dbSchema){
        Integer totals = 0;
		List<File> files = new ArrayList<File>();
		files = FileUtils.listFilesInDirWithFilter(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()
		+"/"+Constants.PASCLOUD_PUBLIC_DB, ".xml", false);
		//System.out.println(m_config.getPASCLOUD_DEV_DIR());
		if(files.size()>0){
			Iterator<File> it = files.iterator();
			while(it.hasNext()){
				File f = it.next();
				//parserPasfileForID(f.getAbsolutePath());
				totals+=modifyPasfileForID(f.getAbsolutePath(),dbSchema);
			}
		}
		return totals;
		
	}
	
	
	
	
	
	public synchronized Integer copyPasfileWidthID(String dbSchema){
		Integer num = 0;
		
		String defaultpath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()
		+"/"+Constants.PASCLOUD_PUBLIC_DB;
		String newpath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()
		+"/"+dbSchema;
		if(FileUtils.isFileExists(newpath)){
			
		}else{
			if(FileUtils.createOrExistsDir(newpath)){
				log.info("复制PAS+文件到"+dbSchema);
				if(FileUtils.copyDir(defaultpath, newpath)){
					num = copyPasdevFilesWidthID(dbSchema);
				}
				log.info("复制PAS+文件到"+dbSchema+"完成");
			}
		}
		
		if(num>0){
			log.info("同步PAS+文件到公共库");
			sysPasfileToDB(dbSchema);
		}
		
		return num;
	}
	/**
	 * 根据目录进行删除
	 * @param dbSchema
	 * @return
	 */
	public synchronized Integer delPasfileWidthID(String dbSchema){
		Integer result = 0;
		String path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()
		+"/"+dbSchema;
		String gzPath = path+".tar.gz";
		if(dbSchema.equals(Constants.PASCLOUD_PUBLIC_DB)){
			result = -1;
		}else{
			if(FileUtils.isFileExists(path)){
				log.info("删除目录文件："+path);
				if(FileUtils.deleteDir(path)){
					
					if(FileUtils.isFileExists(gzPath)){
						log.info("删除压缩包文件："+gzPath);
						FileUtils.deleteFile(gzPath);
					}
					log.info("删除数据库数据："+dbSchema);
					delPasfileFromDB(dbSchema);
					result = 1;
				}else{
					result = -1;
				}
				
			}else{
				result =-1;
			}
		}
		return result;
	}
	
	private synchronized Integer copyPasdevFilesWidthID(String dbSchema){
        Integer totals = 0;
		List<File> files = new ArrayList<File>();
		files = FileUtils.listFilesInDirWithFilter(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()
		+"/"+dbSchema, ".xml", false);
		//System.out.println(m_config.getPASCLOUD_DEV_DIR());
		if(files.size()>0){
			Iterator<File> it = files.iterator();
			while(it.hasNext()){
				File f = it.next();
				//parserPasfileForID(f.getAbsolutePath());
				totals+=modifyPasfileForID(f.getAbsolutePath(),dbSchema);
			}
		}
		return totals;
		
	}
	
	private synchronized Integer modifyPasfileForID(String filepath,String dbSchema){
		Integer num = 0;
		Document doc = XmlParser.getDocument(filepath);
		Element root = doc.getRootElement();
		Element sqlmap = (Element) root.selectNodes("sqlMap").get(0);
		//替换SELECT的ID
		List<Element> selectNodes = sqlmap.selectNodes("select");
		for(Element node:selectNodes){
			//System.out.println(node.attributeValue("id"));
			String id = node.attributeValue("id").replaceAll("^dn[0-9]{1,}", dbSchema);
			//System.out.println(id);
			node.addAttribute("id", id);
			num++;
		}
		
		List<Element> insertNodes = sqlmap.selectNodes("insert");
		for(Element node:insertNodes){
			//System.out.println(node.attributeValue("id"));
			String id = node.attributeValue("id").replaceAll("^dn[0-9]{1,}", dbSchema);
			node.addAttribute("id", id);
			num++;
		}
		
		List<Element> updateNodes = sqlmap.selectNodes("update");
		for(Element node:updateNodes){
			//System.out.println(node.attributeValue("id"));
			String id = node.attributeValue("id").replaceAll("^dn[0-9]{1,}", dbSchema);
			node.addAttribute("id", id);
			num++;
		}
		
		List<Element> deleteNodes = sqlmap.selectNodes("delete");
		for(Element node:deleteNodes){
			//System.out.println(node.attributeValue("id"));
			String id = node.attributeValue("id").replaceAll("^dn[0-9]{1,}", dbSchema);
			node.addAttribute("id", id);
			num++;
		}
		saveDocument(filepath,doc);
		return num;
		
	}
	//保存文件
	private synchronized void saveDocument(String schemaPath,Document doc){
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileOutputStream(schemaPath),format);
			writer.write(doc);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
	
	
	public List<String> getPasfileDir(){
		List<String> lists = new ArrayList<>();
		List<File> files = new ArrayList<File>();
		files = FileUtils.listFilesInDir(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR(), false);
		if(files.size()>0){
			for(File f:files){
				if(!f.getName().endsWith(".tar.gz")){
					lists.add(f.getName());
				}
				
			}
		}
		return lists;
	}
	
	/**
	 * 根据分行代号进行压缩打包，并上传到服务，再进行解压
	 * @param dbSchema
	 * @return
	 */
	public Boolean uploadPasfileWithID(String dbSchema){
		Boolean flag = false;
		String path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()
		+"/"+dbSchema;
		String tarpath = path+".tar";
		String gzpath = path+".tar.gz";
		String gzname = dbSchema+".tar.gz";
		try{
			log.info("判断目录下文件是否不为空");
			List<File> files = new ArrayList<File>();
			if(!FileUtils.isDir(path)){
				return flag;
			}
			files = FileUtils.listFilesInDir(path);
			if(files.size()>=0 && !FileUtils.isFileExists(gzpath)){
				if(!FileUtils.isFileExists(tarpath)){
					log.info("对目录进行tar压缩");
					TarUtils.archive(path,tarpath);
				}
				if(FileUtils.isFileExists(tarpath)){
					log.info("对目录进行gz压缩");
					GZipUtils.compress(tarpath);
				}
			}
			if(FileUtils.isFileExists(gzpath)){
				flag = uploadGZIPToServer(gzpath,gzname,dbSchema);
				
			}
			log.info("完成压缩");
		}catch(Exception e){
		    log.error(e.getMessage());
		    e.printStackTrace();
		}
		return flag;
	}
	
	public Boolean uploadPasfileWithID(String dbSchema,String ip){
		Boolean flag = false;
		String path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()
		+"/"+dbSchema;
		String tarpath = path+".tar";
		String gzpath = path+".tar.gz";
		String gzname = dbSchema+".tar.gz";
		try{
			log.info("判断目录下文件是否不为空");
			List<File> files = new ArrayList<File>();
			if(!FileUtils.isDir(path)){
				return flag;
			}
			files = FileUtils.listFilesInDir(path);
			if(files.size()>=0 && !FileUtils.isFileExists(gzpath)){
				if(!FileUtils.isFileExists(tarpath)){
					log.info("对目录进行tar压缩");
					TarUtils.archive(path,tarpath);
				}
				if(FileUtils.isFileExists(tarpath)){
					log.info("对目录进行gz压缩");
					GZipUtils.compress(tarpath);
				}
			}
			if(FileUtils.isFileExists(gzpath)){
				flag = uploadGZIPToServer(gzpath,gzname,dbSchema,ip);
				
			}
			log.info("完成压缩");
		}catch(Exception e){
		    log.error(e.getMessage());
		    e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 上传到服务器并解压
	 * @param gzpath
	 * @param gzname
	 */
	private Boolean uploadGZIPToServer(String gzpath,String gzname,String dbSchema){
		String path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR();
		
		Connection conn = null;
		Boolean flag = true;
		//String serverPath = Constants.PASCLOUD_HOME;
		List<ContainerVo> containers = m_pasService.getContainerWithMainService();
		log.info("获取服务的容器");
		if(null!=containers && containers.size()>0){
			for(ContainerVo vo:containers){
				log.info("获取容器的在的服务器");
				ServerVo server = m_serverService.getByIP(vo.getIp());
				log.info("获取容器所在服务器的PAS+目录");
				String serverPath = m_pasService.getServicePasdevPathWithContainerName(vo.getName());
				
				String serverGZpath = serverPath+gzname;
				if(null!=server){
					conn = getScpClientConn(server.getIp(), server.getUsername(), server.getPassword());
					//scpClient = m_scpClientService.buildScpClient(server.getIp(), server.getUsername(), server.getPassword());
					log.info("创建SSH连接工具类");
					if(null !=conn){
						log.info("上传");
						if(uploadGZToServer(conn,gzpath,serverPath)){
							String cmd="tar -zvxf "+serverGZpath;
							if(execCommand(conn,cmd)){
								String cpcmd = "cp -R ~/"+dbSchema+" "+serverPath;
								if(execCommand(conn,cpcmd)){
									flag = true;
								}
							}
						}
						//String cmd="tar -zvxf "+serverGZpath+" -C "+serverPath;这种方式太慢
						conn.close();
					}
					log.info("创建SSH连接工具类完成");
				}
				log.info("获取容器所在服务器的PAS+目录完成");
				log.info("获取容器的在的服务器完成");
			}
		}
		log.info("获取服务的容器完成");
		return flag;
	}
	
	private Boolean uploadGZIPToServer(String gzpath,String gzname,String dbSchema,
			String ip){
		String path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR();
		
		Connection conn = null;
		Boolean flag = true;
		//String serverPath = Constants.PASCLOUD_HOME;
		//List<ContainerVo> containers = m_pasService.getContainerWithMainService();
		log.info("获取服务的容器");
		if(null!=ip){
			ServerVo server = m_serverService.getByIP(ip);
			//for(ContainerVo vo:containers){
				log.info("获取容器的在的服务器");
				//ServerVo server = m_serverService.getByIP(vo.getIp());
				log.info("获取容器所在服务器的PAS+目录");
				//String serverPath = m_pasService.getServicePasdevPathWithContainerName(vo.getName());
				String serverPath = Constants.PASCLOUD_HOME+Constants.PASCLOUD_SERVICE_DEMO+"/data/pasplus/config/";
				String serverGZpath = serverPath+gzname;
				if(null!=server){
					conn = getScpClientConn(server.getIp(), server.getUsername(), server.getPassword());
					//scpClient = m_scpClientService.buildScpClient(server.getIp(), server.getUsername(), server.getPassword());
					log.info("创建SSH连接工具类");
					if(null !=conn){
						log.info("上传");
						if(uploadGZToServer(conn,gzpath,serverPath)){
							String cmd="tar -zvxf "+serverGZpath;
							if(execCommand(conn,cmd)){
								String cpcmd = "cp -R ~/"+dbSchema+" "+serverPath;
								if(execCommand(conn,cpcmd)){
									flag = true;
								}
							}
						}
						//String cmd="tar -zvxf "+serverGZpath+" -C "+serverPath;这种方式太慢
						conn.close();
					}
					log.info("创建SSH连接工具类完成");
				}
				log.info("获取容器所在服务器的PAS+目录完成");
				log.info("获取容器的在的服务器完成");
			//}
		}
		log.info("获取服务的容器完成");
		return flag;
	}
	
	private Boolean uploadGZToServer(Connection conn,String gzpath,String serverPath){
		Boolean flag = false; 
		if (null!=conn) {

			try {
				SCPClient scpClient = conn.createSCPClient();
				log.info("文件正在上传");
				scpClient.put(gzpath, serverPath);
				Thread.sleep(1000*10);
				flag = true;
				log.info("文件上传完毕");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return flag;
	}
	
	public Boolean execCommand(Connection conn,String cmd) {
		Boolean flag = false;
		StringBuffer sb = new StringBuffer();
		if (null!=conn) {
			Session session = null;
			InputStream stdout = null;
			BufferedReader br = null;
			try {
				session = conn.openSession();
				log.info("执行命令" + cmd);
				session.execCommand(cmd);
				stdout = new StreamGobbler(session.getStdout());
				br = new BufferedReader(new InputStreamReader(stdout));
				/*
				while (true) {
					String line = br.readLine();
					if (line == null){
						flag = true;
						break;
					}
					sb.append(line);
				}*/
				Thread.sleep(1000*5);
				flag = true;
				log.info("执行命令完毕");
				
				// System.out.println(sb.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.info("执行命令异常");
				log.error(e.getMessage());
			} finally {
				session.close();
			}
		}
		return flag;
	}
	
	
	public ResultCommon uploadPasfile(CommonsMultipartFile file,String dirId){
        ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		String filepath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_UPLOAD()+"/pasfile";
        
		//String filepath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR();
		
        //String filepath =
		//xxx/upload/pasfile/dn0
		String destpath = filepath+"/"+dirId;
		//String destpath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()+"/"+dirId;
		
		if(FileUtils.createOrExistsDir(filepath));
		
		String filename = "";
		String fname =  "";
		String newfilename = "";
		String newfname = "";
		String suffix = "";
		if (file != null && !file.isEmpty()) {
			log.info(file.getOriginalFilename());
			InputStream input = null;
			BufferedOutputStream bos = null;
			try {
				
				//testhydr.zip
				filename = file.getOriginalFilename();
				//testhydr
				fname = filename.substring(0, filename.lastIndexOf('.'));
				suffix = filename.substring(filename.lastIndexOf('.'));
				log.info(fname);
				//xxx/upload/pasfile/dn0/testhydr.zip
				newfname = fname + "-" + getRandomFileName();
				newfilename = newfname + suffix;
				//filepath = destpath+"/"+filename;
				filepath = destpath+"/"+newfilename;
				log.info(filepath);
				
				File destfile = new File(filepath);
				//file trans to xxx/upload/pasfile/dn0/testhydr.zip
				file.transferTo(destfile);

				
				String dest = destpath+"/"+newfname; 
				result = unzipAndSysToDB(filepath,dest,dirId);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				log.error(e.getMessage());
				result = new ResultCommon(PasCloudCode.EXCEPTION);
			}finally{
			}
		}
		return result;
	}
	
	private String getRandomFileName() {
 
		SimpleDateFormat simpleDateFormat;
 
		simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
 
		Date date = new Date();
 
		String str = simpleDateFormat.format(date);
 
		Random random = new Random();
 
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
 
		return  str +rannum ;// 当前时间
	}

	
	private ResultCommon unzipAndSysToDB(String filepath,String destpath,String dirId){
		ResultCommon result = null;
		String destDirPath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()+"/";
		
		String dId="";
		List<PasfileVo> vos = new ArrayList<>();
		if(FileUtils.isFileExists(filepath)){
			try {
				log.info(filepath);
				log.info(destpath);
				//filepath.replaceAll(regex, replacement)
				log.info(FileUtils.getFileByPath(filepath).length()+"");
				
				List<File> files = ZipUtils.unzipFile(filepath, destpath);
				
				if(files.size()>0){
					List<File> fs = FileUtils.listFilesInDirWithFilter(destpath, ".xml");
					//List<File> fs = filterWithSuffix(files,".xml");
					if(fs.size()>0){
						
						vos = getPasdevFiles(fs);
						result = sysPasfileToDB(vos);
						if(result.getCode().equals(PasCloudCode.SUCCESS.getCode())){
							dId = result.getDesc();
							destDirPath = destDirPath+dId;
							log.info(destDirPath);
							
							for(int i=0;i<vos.size();i++){
								PasfileVo vo = vos.get(i);
								String filexml = destDirPath+"/"+vo.getFunId()+".xml";
								String filepara = destDirPath+"/"+vo.getFunId()+".para";
								FileUtils.deleteFile(filexml);
								FileUtils.deleteFile(filepara);
							}
							
							Boolean flag = FileUtils.copyDir(destpath, destDirPath);

							if(null!=flag && flag){
								log.info("复制文件成功");
								result = sysPasfileToRedis(dId,vos);
							}else{
								log.info("复制文件失败");
								result = new ResultCommon(PasCloudCode.ERROR.getCode(),"复制到租户目录"+dId+"失败");
							}
							
						}else{
							result = new ResultCommon(PasCloudCode.ERROR.getCode(),"同步到数据库失败");
						}
					}else{
						result = new ResultCommon(PasCloudCode.ERROR.getCode(),"解压后文件为空");
					}
					
				}else{
					result = new ResultCommon(PasCloudCode.ERROR);
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage());
				result = new ResultCommon(PasCloudCode.EXCEPTION);
			}
			return result;
		}else{
			result = new ResultCommon(PasCloudCode.ERROR);
		}
		
		return result;
	}
	
	
	
	
	
	
	private List<File> filterWithSuffix(List<File> files , String suffix){
		List<File> fs = new ArrayList<>();
		
		if(files.size()>0){
			for(int i=0;i<files.size();i++){
				File f= files.get(i);
				String name = f.getName();
				log.info(name);
				int lastIndex = name.lastIndexOf('.');
                
                // get extension
                String str = name.substring(lastIndex);
                if(str.equals(suffix)){
                	fs.add(f);
                }
				
			}
		}
         
        return fs;
	}

	
	
	
	
	public ResultCommon deletePasfile(String funId,String dirId){
		ResultCommon result = null;
		
		String destDirPath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()+"/"+dirId;
		
		java.sql.Connection conn = null;
		int row = 0;
		try{
			conn = m_databaseService.getConnectionById(Constants.PASCLOUD_PUBLIC_DB);
			QueryRunner qRunner = new QueryRunner(); 
			
			String delSql = "delete from xtb_pasfile where fhdh=? and funId=?";
			
			Object[] delparams ={dirId,funId};
			row = qRunner.update(conn, delSql, delparams);
			if(row>0){
				String filexml = destDirPath+"/"+funId+".xml";
				String filepara = destDirPath+"/"+funId+".para";
				FileUtils.deleteFile(filexml);
				FileUtils.deleteFile(filepara);
			}
			
			result = new ResultCommon(PasCloudCode.SUCCESS);
		} catch (SQLException e) {
			log.error(e.getMessage());
			result = new ResultCommon(PasCloudCode.EXCEPTION);
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return result;
	}
	
	
	public ResultCommon sysPasfileToRedis(String dirId,List<PasfileVo> vos){
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		List<RedisVo> rediss = m_pasService.getRedisServer();
		JedisPool jedisPool = null;
		Jedis jedis = null;
		if(rediss.size()>0){
			RedisVo vo = rediss.get(0);
			JedisPoolConfig config = new JedisPoolConfig();
	        config.setMaxTotal(10);
	        config.setMaxIdle(5);
	        config.setMaxWaitMillis(15000);
	        config.setTestOnBorrow(true);
	        jedisPool = new JedisPool(config, vo.getIp(), vo.getPort());
	        jedis = jedisPool.getResource();
	        
		}
		
		if(null!=jedis && vos.size()>0){
			Map<byte[],byte[]> map = new HashMap<>();
			for(int i=0;i<vos.size();i++){
				PasfileVo vo = vos.get(i);
				m_redisService.setCacheForPasfile(jedis, vo.getFunId(), dirId);
				m_redisService.setCacheFunList(jedis, vo.getFunId(), dirId,vo.getType());
				String key = dirId+"."+vo.getFunId();
				map.put(key.getBytes(), SerializeUtils.serialize(vo.getTitle()));
				
				if(vo.getPid().equals("")){
					m_redisService.setCacheFunVersMap(jedis, vo.getFunId(), vo.getVersion(), dirId);
				}else{
					m_redisService.setCacheFunVersMap(jedis, vo.getPid(),vo.getFunId(), vo.getVersion(), dirId);
				}
			}
			m_redisService.setCacheFunMapAll(jedis, map, dirId);
			
		}
		
		jedis.close();
		jedisPool.close();
		
		return result;
	}
	
	private Integer sysPasfileVoToRedis(String dirId,PasfileVo vo){
		Integer res = 0;
		
		List<RedisVo> rediss = m_pasService.getRedisServer();
		JedisPool jedisPool = null;
		Jedis jedis = null;
		if(rediss.size()>0){
			RedisVo r = rediss.get(0);
			JedisPoolConfig config = new JedisPoolConfig();
	        config.setMaxTotal(10);
	        config.setMaxIdle(5);
	        config.setMaxWaitMillis(15000);
	        config.setTestOnBorrow(true);
	        jedisPool = new JedisPool(config, r.getIp(), r.getPort());
	        jedis = jedisPool.getResource();
	        
		}
		
		m_redisService.setCacheForPasfile(jedis, vo.getFunId(), dirId);
		m_redisService.setCacheFunList(jedis, vo.getFunId(), dirId,vo.getType());
		
		if(vo.getPid().equals("")){
			m_redisService.setCacheFunVersMap(jedis, vo.getFunId(), vo.getVersion(), dirId);
		}else{
			m_redisService.setCacheFunVersMap(jedis, vo.getPid(),vo.getFunId(), vo.getVersion(), dirId);
		}
		m_redisService.setCacheFunMap(jedis, vo.getFunId(), vo.getTitle(), dirId);
		
		
		jedis.close();
		jedisPool.close();
		
		return res;
	}
	
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		/*
		String path = "D:/eclipse64/devops/pascloud-devops-parent/pascloud-webapps/src/main/webapp/static/resources/pascloud/pasdev";
		
		List<File> files = new ArrayList<File>();
		files = FileUtils.listFilesInDirWithFilter(path, ".para", false);
		//System.out.println(m_config.getPASCLOUD_DEV_DIR());
		if(files.size()>0){
			Iterator<File> it = files.iterator();
			while(it.hasNext()){
				File f = it.next();
				System.out.println(f.getName());
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Parameter p = (Parameter) ois.readObject();
				System.out.println(p.getFunName());
				System.out.println(p.getFunType());
				
				System.out.println(p.getVersion()+p.getDesc());
				
				if(p.getFunType().equals("query")){
					Parameters para = (Parameters) p;
					
					para.setPid("");
					para.setVersion("1.0.0.0");
					para.setDesc("标准版");
					OutputStream pos = new FileOutputStream(f);
					ObjectOutputStream oos = new ObjectOutputStream(pos);
					oos.writeObject(para);
				}else if(p.getFunType().equals("manage")){
					
					ManageParameters para = (ManageParameters) p;
					
					para.setPid("");
					para.setVersion("1.0.0.0");
					para.setDesc("标准版");
					OutputStream pos = new FileOutputStream(f);
					ObjectOutputStream oos = new ObjectOutputStream(pos);
					oos.writeObject(para);
				}else if(p.getFunType().equals("import")){
					ImportParameters para = (ImportParameters) p;
					
					para.setPid("");
					para.setVersion("1.0.0.0");
					para.setDesc("标准版");
					OutputStream pos = new FileOutputStream(f);
					ObjectOutputStream oos = new ObjectOutputStream(pos);
					oos.writeObject(para);
				}else if(p.getFunType().equals("yjgx")){
					YjgxParameters para = (YjgxParameters) p;
					
					para.setPid("");
					para.setVersion("1.0.0.0");
					para.setDesc("标准版");
					OutputStream pos = new FileOutputStream(f);
					ObjectOutputStream oos = new ObjectOutputStream(pos);
					oos.writeObject(para);
				}
				
			}
		}
		
        String path = "D:/eclipse64/devops/pascloud-devops-parent/pascloud-webapps/src/main/webapp/static/resources/pascloud/pasdev";
		
		List<File> files = new ArrayList<File>();
		files = FileUtils.listFilesInDirWithFilter(path, ".xml", false);
		
		if(files.size()>0){
			Iterator<File> it = files.iterator();
			while(it.hasNext()){
				File f = it.next();
				//parserPasfileForID(f.getAbsolutePath());
				Document doc = XmlParser.getDocument(f.getAbsolutePath());
				Element root = doc.getRootElement();
				String title= root.attributeValue("title");
				//vo.setType(root.attributeValue("type"));
				root.addAttribute("version", "1.0.0.0");
				root.addAttribute("pid", "");
				root.addAttribute("desc", "标准版");
				
				System.out.println(title);
				
				OutputFormat format = OutputFormat.createPrettyPrint();
				
				XMLWriter writer =writer = new XMLWriter(new FileOutputStream(f.getAbsolutePath()),format);
				writer.write(doc);
			}
		}*/
		
		String dest = "D:/eclipse64/devops/pascloud-devops-parent/pascloud-webapps/src/main/webapp/static/resources/upload/pasfile/dn0/testhydr/testhydr.xml";

		String destp = "D:/eclipse64/devops/pascloud-devops-parent/pascloud-webapps/src/main/webapp/static/resources/upload/pasfile/dn0/testhydr/testhydr.para";

		
		Document doc = XmlParser.getDocument(dest);
		
		String contentxml = doc.asXML();
		
		System.out.println(contentxml);
		
		JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(15000);
        config.setTestOnBorrow(true);
        String id = "192.168.0.7"+":"+ "6379";
        JedisPool jedisPool = new JedisPool(config, "192.168.0.7", 6379);
        Jedis j = jedisPool.getResource();
        
        //byte[] o = j.get("dn0.testhydr.para".getBytes());
        byte[] contento = SerializeUtils.serialize(contentxml);
        j.set("dn1.testhydr".getBytes(), contento);
        
        
        
        byte[] content = j.get("dn1.testhydr".getBytes());
        
        
        //InputStream is = new ByteArrayInputStream(o);
		//ObjectInputStream ois = new ObjectInputStream(is);
        //Parameter p = (Parameter) ois.readObject();
        
        InputStream nis = new ByteArrayInputStream(content);
		ObjectInputStream nois = new ObjectInputStream(nis);
        
        //System.out.println(p.getFunId());
        System.out.println("------------");
        //System.out.println(content);
        
        String contentstr = (String) nois.readObject();
        System.out.println(contentstr);
        Document doc2 =XmlParser.getDocumentFromString(contentstr);
        Element root =doc2.getRootElement();
        System.out.println(root.attributeValue("id"));
        
        File file = new File(destp);
        
        FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Parameter p = null;
		byte[] parab = null;
		p = (Parameter) ois.readObject();
		if(p.getFunType().equals("query")){
			Parameters para = (Parameters) p;
			parab = SerializeUtils.serialize(para);
		}else if(p.getFunType().equals("manage")){
			ManageParameters para = (ManageParameters) p;
			parab = SerializeUtils.serialize(para);
		}else if(p.getFunType().equals("import")){
			ImportParameters para = (ImportParameters) p;
			parab = SerializeUtils.serialize(para);
			parab = SerializeUtils.serialize(para);
		}else if(p.getFunType().equals("yjgx")){
			YjgxParameters para = (YjgxParameters) p;
			parab = SerializeUtils.serialize(para);
		}
		
		
		j.set("dn1.testhydr.para".getBytes(), parab);
		
		byte[] o = j.get("dn1.testhydr.para".getBytes());
		
		InputStream is = new ByteArrayInputStream(o);
		ObjectInputStream oiss = new ObjectInputStream(is);
        Parameter ps = (Parameter) oiss.readObject();
        
        System.out.println(ps.getFunId());
		
		//String path = "D:/eclipse64/devops/pascloud-devops-parent/pascloud-webapps/src/main/webapp/static/resources/upload/pasfile/dn0/testhycx01.zip";
		//String dest = "D:/eclipse64/devops/pascloud-devops-parent/pascloud-webapps/src/main/webapp/static/resources/upload/pasfile/dn0/testhydr/testhydr.xml";
		//List<File> files = ZipUtils.unzipFile(path, dest);
		
		//System.out.println(files.size());
		
		
	}
	 
	

}
