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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.bean.pasdev.PasfileVo;
import com.pascloud.constant.Constants;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.xml.XmlParser;

@Service
public class PasdevService {
	
	@Autowired
	private PasCloudConfig m_config;
	
	public List<PasfileVo> getPasdevFiles(){
		List<PasfileVo> result = new ArrayList<>();
		List<File> files = new ArrayList<File>();
		files = FileUtils.listFilesInDirWithFilter(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR(), ".xml", false);
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
				vo.setFilepath(f.getAbsolutePath());;
				parserPasfile(f.getAbsolutePath(),vo);
				parserPasfileForID(f.getAbsolutePath());
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
	
	private void parserPasfileForID(String filepath){
		Document doc = XmlParser.getDocument(filepath);
		Element root = doc.getRootElement();
		
		Element sqlmap = (Element) root.selectNodes("sqlMap").get(0);
		
		List<Element> selectNodes = sqlmap.selectNodes("select");
		for(Element node:selectNodes){
			//System.out.println(node.attributeValue("id"));
		}
		
		List<Element> insertNodes = sqlmap.selectNodes("insert");
		for(Element node:insertNodes){
			//System.out.println(node.attributeValue("id"));
		}
		
		List<Element> updateNodes = sqlmap.selectNodes("insert");
		for(Element node:updateNodes){
			//System.out.println(node.attributeValue("id"));
		}
		
		List<Element> deleteNodes = sqlmap.selectNodes("insert");
		for(Element node:deleteNodes){
			//System.out.println(node.attributeValue("id"));
		}
	}
	
	public Integer modifyPasdevFilesWidthID(String dbSchema){
        Integer totals = 0;
		List<File> files = new ArrayList<File>();
		files = FileUtils.listFilesInDirWithFilter(System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR(), ".xml", false);
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
	
	private Integer modifyPasfileForID(String filepath,String dbSchema){
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
	
	private void saveDocument(String schemaPath,Document doc){
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
	
	
	
	

}
