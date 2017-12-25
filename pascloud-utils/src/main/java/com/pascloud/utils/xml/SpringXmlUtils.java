package com.pascloud.utils.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SpringXmlUtils {
	
	private static final Logger log = LoggerFactory.getLogger(SpringXmlUtils.class);
	
	public static Element getElement(Document doc,String id){
		
		Element element = null;
		List<Element> xmlList = doc.selectNodes("/beans/bean");
		for(Element e:xmlList){
			if(e.attributeValue("id").equals(id)){
				element = e;
			}
		}
		return element;
	}
	
	public static Element getChildElement(Element e,String name){
		Element child = null;
		List<Element> childerns = XmlParser.getChildList(e);
		for(Element el:childerns){
			Attribute a = el.attribute(0);
			if(a.getText().equals(name)){
				child = el;
			}
		}
		return child;
	}
	
	public static void modifyAttribute(Element e,String name,String value){
		Attribute a = e.attribute(name);
		a.setText(value);
	}
	
	public static void wirteXml(String newfile,Document doc){
		XMLWriter outXml = null;
		try {
			OutputFormat format = new OutputFormat();     
	        format.setIndent(true);     
	        format.setNewlines(true);
			outXml = new XMLWriter(new FileWriter(new File(newfile)),format);
			outXml.write(doc);  
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}finally{
			try {
				outXml.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		String filepath = "D:/applicationContext.xml";
		String newfile  = "D:/applicationContext2.xml";
		Document doc = XmlParser.getDocument(filepath);
		Element  datasource = SpringXmlUtils.getElement(doc, "dataSource");
		
		Element  drive = SpringXmlUtils.getChildElement(datasource, "driverClassName");
		
		//Attribute drive = SpringXmlUtils.getAttribute(datasource, 0);
		//Attribute url = SpringXmlUtils.getAttribute(datasource, 1);
		//Attribute username = SpringXmlUtils.getAttribute(datasource, 2);
		//Attribute password = SpringXmlUtils.getAttribute(datasource, 3);
		SpringXmlUtils.modifyAttribute(drive, "value", "11111");
		//SpringXmlUtils.modifyAttribute(drive, value);
		System.out.println(drive);
		
		SpringXmlUtils.wirteXml(newfile, doc);
	}

}
