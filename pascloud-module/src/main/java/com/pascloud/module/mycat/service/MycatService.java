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
				parserWritehost(e,vo);
				
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
				DataHostVo dvo = getDataHostByName(vo.getDataHost(),root);
				if(null != dvo){
					vo.setUrl(dvo.getUrl());
					vo.setPassword(dvo.getPassword());
					vo.setUser(dvo.getUser());
					vo.setDbType(dvo.getDbType());
					vo.setDbDriver(dvo.getDbDriver());
					vo.setPort(parserPort(dvo.getUrl(),dvo.getDbType()));
					vo.setIp(parserIP(dvo.getUrl(),dvo.getDbType()));
				}
				
				result.add(vo);
			}	
		}
		return result;
	}
	
    private List<DataHostVo> getDataHosts(Element root){
		List<DataHostVo> result = new ArrayList<>();
		
		//String mycat_schema_path = m_config.getPASCLOUD_MYCAT_DIR()+File.separator+"schema.xml";
		//Document doc = XmlParser.getDocument(mycat_schema_path);
		//Element root = doc.getRootElement();
		List<Element> nodes = root.elements("dataHost");
		
		if(nodes.size()>0){
			Iterator<Element> it = nodes.iterator();
			while(it.hasNext()){
				Element e = it.next();
				DataHostVo vo = new DataHostVo();
				vo.setName(e.attributeValue("name"));
				vo.setDbType(e.attributeValue("dbType"));
				vo.setDbDriver(e.attributeValue("dbDriver"));
				parserWritehost(e,vo);
				//vo.setUrl(paserPort(vo.getUrl(),vo.getDbType()));
				result.add(vo);
			}
		}
		
		return result;
		
	}
    
    
    private DataHostVo getDataHostByName(String name,Element root){
		DataHostVo vo = null;
		List<DataHostVo> result = getDataHosts(root);
		if(null != result && result.size()>0){
			for(DataHostVo v : result){
				if(v.getName().equals(name)){
					vo = v;
				}
			}
		}
		return vo;
	}
	
	
	private void parserWritehost(Element e,DataHostVo vo){
		
		Element children = (Element) e.selectSingleNode("writeHost");
		vo.setUrl(children.attributeValue("url"));
		vo.setPassword(children.attributeValue("password"));
		vo.setUser(children.attributeValue("user"));
		
		//System.out.println(children.asXML());
	}
	
	private String parserIP(String url,String dbType){
		String ip = "";
		if(url.length()>0){
			if(dbType.equals("mysql")){
				int index = url.lastIndexOf(":");
				System.out.println(index);
				url = url.substring(0, index);
			}else if(dbType.equals("oracle")){
				int index = url.lastIndexOf("@");
				url = url.substring(index+1,url.length());
				index = url.indexOf(":");
				url = url.substring(0, index);
				//ip = url;
			}else if(dbType.equals("db2")){
				//int index = url.indexOf(":");
				url = url.replace("jdbc:db2://", "");
				url = url.substring(0, url.length());
				int index = url.indexOf(":");
				url = url.substring(0, index);
			}
			ip = url;
			System.out.println(url);
		}
		return ip;
	}
	
	private String parserPort(String url,String dbType){
		System.out.println(url+dbType);
		String port = "";
		
		if(url.length()>0){
			if(dbType.equals("mysql")){
				int index = url.lastIndexOf(":");
				System.out.println(index);
				port = url.substring(index+1,url.length());
			}else if(dbType.equals("oracle")){
				int index = url.lastIndexOf(":");
				url = url.substring(0, index);
				index = url.lastIndexOf(":");
				port = url.substring(index+1,url.length());
			}else if(dbType.equals("db2")){
				int index = url.lastIndexOf("/");
				url = url.substring(0, index);
				index = url.lastIndexOf(":");
				port = url.substring(index+1,url.length());
			}
		}
		return port;
		
	}

}
