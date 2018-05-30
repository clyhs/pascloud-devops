package com.pascloud.module.pasdev.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.constant.Constants;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.module.passervice.service.ScpClientService;
import com.pascloud.module.server.service.ServerService;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.ScpClientUtils;
import com.pascloud.utils.gzip.GZipUtils;
import com.pascloud.utils.gzip.TarUtils;
import com.pascloud.utils.xml.XmlParser;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.pasdev.PasfileVo;
import com.pascloud.vo.result.ResultCommon;
import com.pascloud.vo.server.ServerVo;

/**
 * pas+版本管理服务
 * @author chenly
 *
 */
@Service
public class PasdevService {
	
	private static Logger log = LoggerFactory.getLogger(PasdevService.class);
	
	@Autowired
	private PasCloudConfig     m_config;
	
	@Autowired
	private PasService         m_pasService;
	
	@Autowired
	private ScpClientService   m_scpClientService;
	
	@Autowired
	private ServerService      m_serverService;
	
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
				parserPasfile(f.getAbsolutePath(),vo);
				result.add(vo);
			}
		}
		
		return result;
		
	}
	
	private void parserPasfile(String filepath,PasfileVo vo){
		Document doc = XmlParser.getDocument(filepath);
		Element root = doc.getRootElement();
		vo.setTitle(root.attributeValue("title"));
		vo.setType(root.attributeValue("type"));
		if(null == root.attributeValue("version")){
			
		}else{
			vo.setVersion(root.attributeValue("version"));
		}
	}
	
	public synchronized Integer modifyPasdevFilesWidthID(String dbSchema){
        Integer totals = 0;
		List<File> files = new ArrayList<File>();
		files = FileUtils.listFilesInDirWithFilter(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()
		+"/"+Constants.PASCLOUD_DEV_DEFAULT, ".xml", false);
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
		+"/"+Constants.PASCLOUD_DEV_DEFAULT;
		String newpath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()
		+"/"+dbSchema;
		System.out.println(newpath);
		if(FileUtils.isFileExists(newpath)){
			
		}else{
			if(FileUtils.createOrExistsDir(newpath)){
				if(FileUtils.copyDir(defaultpath, newpath)){
					copyPasdevFilesWidthID(dbSchema);
				}
			}
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
		if(dbSchema.equals(Constants.PASCLOUD_DEV_DEFAULT)){
			result = -1;
		}else{
			if(FileUtils.isFileExists(path)){
				if(FileUtils.deleteDir(path)){
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
			if(files.size()>0 && !FileUtils.isFileExists(gzpath)){
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
	
	/**
	 * 上传到服务器并解压
	 * @param gzpath
	 * @param gzname
	 */
	private Boolean uploadGZIPToServer(String gzpath,String gzname,String dbSchema){
		String path = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR();
		ScpClientUtils scpClient = null;
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
					scpClient = m_scpClientService.buildScpClient(server.getIp(), server.getUsername(), server.getPassword());
					log.info("创建SSH连接工具类");
					if(null !=scpClient){
						log.info("上传");
						if(scpClient.putFileToServer( gzpath, serverPath)){
							String cmd="tar -zvxf "+serverGZpath;
							if(scpClient.execCommand(cmd)){
								String cpcmd = "cp -R ~/"+dbSchema+" "+serverPath;
								if(scpClient.execCommand(cpcmd)){
									flag = true;
								}
							}
						}
						//String cmd="tar -zvxf "+serverGZpath+" -C "+serverPath;这种方式太慢
						
						scpClient.close();
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
	
	
	 
	

}
