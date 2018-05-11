package com.pascloud.module.mycat.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.bean.mycat.DataHostVo;
import com.pascloud.bean.mycat.DataNodeVo;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.utils.xml.XmlParser;

@Service
public class MycatService {
	
	@Autowired
	private PasCloudConfig m_config;
	
	public List<DataHostVo> getDataHosts(){
		
		List<DataHostVo> result = new ArrayList<>();
		
		String mycat_schema_path = m_config.getPASCLOUD_MYCAT_DIR()+File.separator+"schema.xml";
		Document doc = XmlParser.getDocument(mycat_schema_path);
		Element root = doc.getRootElement();
		List<Element> nodes = root.elements("dataHost");
		
		if(nodes.size()>0){
			Iterator<Element> it = nodes.iterator();
			while(it.hasNext()){
				Element e = it.next();
				DataHostVo vo = new DataHostVo();
				vo.setName(e.attributeValue("name"));
				vo.setDbType(e.attributeValue("dbType"));
				vo.setDbDriver(e.attributeValue("dbDriver"));
				paserWritehost(e,vo);
				
				result.add(vo);
			}
		}
		
		return result;
		
	}
	
	public List<DataNodeVo> getDataNodes(){
		List<DataNodeVo> result = new ArrayList<>();
		String mycat_schema_path = m_config.getPASCLOUD_MYCAT_DIR()+File.separator+"schema.xml";
		Document doc = XmlParser.getDocument(mycat_schema_path);
		Element root = doc.getRootElement();
		List<Element> nodes = root.elements("dataNode");
		
		if(nodes.size()>0){
			Iterator<Element> it = nodes.iterator();
			while(it.hasNext()){
				Element e = it.next();
				DataNodeVo vo = new DataNodeVo();
				vo.setName(e.attributeValue("name"));
				vo.setDataHost(e.attributeValue("dataHost"));
				vo.setDatabase(e.attributeValue("database"));
				
				result.add(vo);
			}	
		}
		return result;
	}
	
	private void paserWritehost(Element e,DataHostVo vo){
		
		Element children = (Element) e.selectSingleNode("writeHost");
		vo.setUrl(children.attributeValue("url"));
		vo.setPassword(children.attributeValue("password"));
		vo.setUser(children.attributeValue("user"));
		//System.out.println(children.asXML());
	}

}
