package com.pascloud.utils;

import java.io.File;

import org.dom4j.Document;

import com.pascloud.utils.xml.XmlParser;
import com.pascloud.vo.database.DBInfo;
import com.thoughtworks.xstream.XStream;

public class XStreamDemo {
	
	public static void main(String[] args) {
		
		String header =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		DBInfo info = new DBInfo();
		info.setId("cloudpas17_ora");
		info.setName("cloudpas");
		info.setUrl("jdbc:oracle:thin:@192.168.0.17:1521:cloudpas");
		info.setUsername("pas2");
		info.setPassword("pas2");
		info.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		info.setDbType("ora");
		XStream xstream = new XStream(); 
		xstream.alias("dbinfo", DBInfo.class);
		String xml = xstream.toXML(info);
		xml = header + xml;
		//System.out.println(xml);
		
		String path = "d:/cloudpas17_ora.xml";
		
		File file = new File(path);
		
		FileUtils.writeFileFromString(file, xml, false);
		/*
		Document doc = XmlParser.getDocument(path);
		
		System.out.println(doc.getRootElement().asXML());
		
		DBInfo a = (DBInfo) xstream.fromXML(file);
		System.out.println(a.getName());*/
		
		
	 
		
	}

}
