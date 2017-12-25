package com.pascloud.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.pascloud.utils.xml.XmlParser;

public class Dom4jTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String filepath = "D:/applicationContext.xml";
		
		String newfile  = "D:/applicationContext2.xml";
		
		Document doc = XmlParser.getDocument(filepath);
		List<Element> xmlList = doc.selectNodes("/beans/bean");  
		
		Element driver = null;
		
		for(Element e:xmlList){
			
			if(e.attributeValue("id").equals("dataSource")){
				System.out.println("id:" + e.attributeValue("id")  
	            + " / class:" + e.attributeValue("class"));
				List<Element> childerns = XmlParser.getChildList(e);
				for(Element el:childerns){
					//System.out.println(el.asXML());
					Attribute a = el.attribute(0);
					//System.out.println(a.getText());
					if(a.getText().equals("driverClassName")){
						driver = el;
					}
				}
			}
		}
		//Node node = doc    
	                //.selectSingleNode("/beans/bean[@id='dataSource']/property[@name='driverClassName']");   
		
		Attribute a = driver.attribute("value");
		System.out.println(a.getText());
		//a.setText("com.ibm.db2.jcc.DB2Driver2");
		//System.out.println("..."+node);
		
		//Node dataSource = doc  
                //.selectSingleNode("/beans/bean[@id='dataSource']/property[@name='driverClassName']/value");  
        //System.out.println("原始值为：" + dataSource);  
        
        //dataSource.setText("com.ibm.db2.jcc.DB2Driver2");  
        XMLWriter outXml = null;
		try {
			OutputFormat format = new OutputFormat();     
	        format.setIndent(true);     
	        format.setNewlines(true);
			outXml = new XMLWriter(new FileWriter(new File(newfile)),format);
			//outXml.write(doc);  
	         
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			try {
				outXml.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
        

	}

}
