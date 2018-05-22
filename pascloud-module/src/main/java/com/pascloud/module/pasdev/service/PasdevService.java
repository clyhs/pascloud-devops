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

import com.pascloud.bean.pasdev.PasfileVo;
import com.pascloud.constant.Constants;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.xml.XmlParser;
import com.pascloud.vo.result.ResultCommon;

/**
 * pas+版本管理服务
 * @author chenly
 *
 */
@Service
public class PasdevService {
	
	private static Logger log = LoggerFactory.getLogger(PasdevService.class);
	
	@Autowired
	private PasCloudConfig m_config;
	
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
				lists.add(f.getName());
			}
		}
		return lists;
	}
	
	
	

}
