package com.pascloud.utils;

import java.io.File;

import org.dom4j.Document;

import com.pascloud.utils.xml.XmlParser;
import com.pascloud.vo.database.DBInfo;
import com.thoughtworks.xstream.XStream;

public class XStreamDemo {
	
	public static void main(String[] args) {
		/*
		String header =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		DBInfo info = new DBInfo();
		info.setId("123424325edw");
		info.setName("test");
		info.setUrl("//.oeorewrwe");
		XStream xstream = new XStream(); 
		xstream.alias("dbinfo", DBInfo.class);
		String xml = xstream.toXML(info);
		xml = header + xml;
		//System.out.println(xml);
		
		String path = "d:/test.xml";
		
		File file = new File(path);
		
		//FileUtils.writeFileFromString(file, xml, false);
		
		Document doc = XmlParser.getDocument(path);
		
		System.out.println(doc.getRootElement().asXML());
		
		DBInfo a = (DBInfo) xstream.fromXML(file);
		System.out.println(a.getName());*/
		
		
	 
		
	}

}
