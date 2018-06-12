package com.pascloud.utils.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.utils.DBUtils;
/**
 * mcyat配置文件操作类
 * @author chenly
 *
 */
public class MycatXmlUtils {

	private static final Logger log = LoggerFactory.getLogger(MycatXmlUtils.class);
	
	/**
	 * 添加mycat配置文件schema.xml数据库节点
	 * @param schemaPath
	 * @param name
	 * @param dbType
	 * @param ip
	 * @param user
	 * @param password
	 * @param database
	 * @param port
	 * @return
	 */
	public static Document addSchemaAndNodeAndHost(String schemaPath,String name,
			String dbType,String ip,String user,String password,
			String database,Integer port){
		System.out.println("addSchemaAndNodeAndHost");
		Document doc = XmlParser.getDocument(schemaPath);
		Element root = doc.getRootElement();
		addSchema(root,name);
		String dataHostName = "dataHost_"+name;
		addDataNode(root,name,dataHostName,database);
		addDataHost(root,dataHostName,dbType,ip,user,password,database,port);
		sort(root);
		saveDocument(schemaPath,doc);
		return doc;
	}
	
	
	
	/**
	 * 删除mycat配置文件schema.xml的数据库节
	 * @param schemaPath
	 * @param name
	 * @return
	 */
	public static Document delSchemaAndNodeAndHost(String schemaPath,String name){
		Document doc = XmlParser.getDocument(schemaPath);
		Element root = doc.getRootElement();
		delSchema(root,name);
		delDataNode(root,name);
		String dataHostName = "dataHost_"+name;
		delDataHost(root,dataHostName);
		saveDocument(schemaPath,doc);
		return doc;
	}
	
	private static void addDataHost(Element root,String dataHostName,String dbType,String ip,String user,String password,
			String database,Integer port){
		Element e = root.addElement("dataHost");
		e.addAttribute("name", dataHostName);
		e.addAttribute("maxCon", "1000");
		e.addAttribute("minCon", "10");
		e.addAttribute("balance", "0");
		e.addAttribute("writeType", "0");
		e.addAttribute("dbType", dbType);
		if(dbType.toLowerCase().equals("mysql")){
			e.addAttribute("dbDriver", "native");
		}else if(dbType.toLowerCase().equals("oracle")){
			e.addAttribute("dbDriver", "jdbc");
		}else if(dbType.toLowerCase().equals("db2")){
			e.addAttribute("dbDriver", "jdbc");
		}
		String url = DBUtils.getUrlByParams(dbType, ip, database, port);
		
		addHeartbeat(e,dbType);
		addDataHostOthers(e,dbType);
		addWriteHost(e,url,user,password);
		//saveDocument(schemaPath,doc);
	}
	
	public static Boolean setDataHostWithDn0(String schemaPath,String ip,String database,
			Integer port,String user,String password,
			String serverPath){
		log.info("修改公共库地址");
		Boolean flag = false;
		Document doc = XmlParser.getDocument(schemaPath);
		Element root = doc.getRootElement();
		List<Element> nodes = root.elements("dataHost");
		Element dn0 = null;
		if(nodes.size()>0){
			//dataHost_dn0
			for(Element e:nodes){
				if(e.attributeValue("name").equals("dataHost_dn0")){
					dn0 = e;
				}
			}
		}
		if(null!=dn0){
			System.out.println("修改公共库地址:dn0");
			Element e =dn0.element("writeHost");
			String url = ip+":"+port;
			e.addAttribute("url", url);
			e.addAttribute("user", user);
			e.addAttribute("password", password);
			saveDocument(schemaPath,doc);
		}else{
			System.out.println("add dn0");
			MycatXmlUtils.addSchemaAndNodeAndHost(schemaPath, "dn0", "mysql", ip, user, password, database, port);
			MycatXmlUtils.addServer(serverPath, "dn0");
		}
		
		flag = true;
		return flag;
	}
	
	
	/**
	 * 
	 *<dataHost name="orahost20" maxCon="1000" minCon="1" balance="0" writeType="0" dbType="oracle" dbDriver="jdbc">
	 *<dataHost name="mysqlhost" maxCon="1000" minCon="10" balance="0" writeType="0" dbType="mysql" dbDriver="native">
	 *<dataHost name="db2host02" maxCon="1000" minCon="1" balance="0" writeType="0" dbType="db2" dbDriver="jdbc">
     *<heartbeat>select 1 from dual</heartbeat>
     *<connectionInitSql>alter session set nls_date_format='yyyy-mm-dd hh24:mi:ss'</connectionInitSql>
     *<writeHost host="hostM1" url="jdbc:oracle:thin:@192.168.0.17:1521:cpas20" user="pas" password="pas"/>
     *</dataHost>
	 */
	
	private static Document addDataHost(String schemaPath,String dataHostName,String dbType,String ip,String user,String password,
			String database,Integer port){
		Document doc = XmlParser.getDocument(schemaPath);
		Element root = doc.getRootElement();
		Element e = root.addElement("dataHost");
		e.addAttribute("name", dataHostName);
		e.addAttribute("maxCon", "1000");
		e.addAttribute("minCon", "10");
		e.addAttribute("balance", "0");
		e.addAttribute("writeType", "0");
		e.addAttribute("dbType", dbType);
		if(dbType.toLowerCase().equals("mysql")){
			e.addAttribute("dbDriver", "native");
		}else if(dbType.toLowerCase().equals("oracle")){
			e.addAttribute("dbDriver", "jdbc");
		}else if(dbType.toLowerCase().equals("db2")){
			e.addAttribute("dbDriver", "jdbc");
		}
		String url = DBUtils.getUrlByParams(dbType, ip, database, port);
		
		addHeartbeat(e,dbType);
		addDataHostOthers(e,dbType);
		addWriteHost(e,url,user,password);
		
		return doc;
		//saveDocument(schemaPath,doc);
	}
	
	private static void delDataHost(Element root,String dataHostName){
		List<Element> nodes = root.selectNodes("dataHost");
		if(nodes.size()>0){
			for(Element e:nodes){
				String s = e.attribute("name").getText();
				if(s.equals(dataHostName)){
					//System.out.println(e.asXML());
					root.remove(e);
				}
			}
		}
		
	}
	
	private static void addWriteHost(Element root,String url,String user,String password){
		Element e = root.addElement("writeHost");
		e.addAttribute("host", "hostM1");
		e.addAttribute("url", url);
		e.addAttribute("user", user);
		e.addAttribute("password", password);
	}
	
	
	
	//<heartbeat>select 1 from sysibm.sysdummy1</heartbeat>
	private static void addHeartbeat(Element root,String dbType){
		Element e = root.addElement("heartbeat");
		switch(dbType){
		case "mysql":
			e.addText("select user()");
			break;
		case "oracle":
			e.addText("select 1 from dual");
			break;
		case "db2":
			e.addText("select 1 from sysibm.sysdummy1");
			break;
		}
	}
	
	private static void addDataHostOthers(Element root,String dbType){
		switch(dbType){
		case "mysql":
			break;
		case "oracle":
			Element ora = root.addElement("connectionInitSql");
			ora.addText("alter session set nls_date_format='yyyy-mm-dd hh24:mi:ss'");
			break;
		case "db2":
			break;
		}
	}
	
	private static Document addDataNode(String schemaPath,String name,String dataHost,String database){
		Document doc = XmlParser.getDocument(schemaPath);
		Element root = doc.getRootElement();
		
		Element e = root.addElement("dataNode");
		
		e.addAttribute("name", name);
		e.addAttribute("dataHost", dataHost);
		e.addAttribute("database", database);
		//saveDocument(schemaPath,doc);
		return doc;
	}
	
	private static void addDataNode(Element root,String name,String dataHost,String database){
        Element e = root.addElement("dataNode");
		e.addAttribute("name", name);
		e.addAttribute("dataHost", dataHost);
		e.addAttribute("database", database);
	}
	
	private static void delDataNode(Element root,String name){
		List<Element> nodes = root.selectNodes("dataNode");
		if(nodes.size()>0){
			for(Element e:nodes){
				String s = e.attribute("name").getText();
				if(s.equals(name)){
					//System.out.println(e.asXML());
					root.remove(e);
				}
			}
		}
	}
	
	
	/**
	 * <schema name="dn17_schema" checkSQLschema="false" sqlMaxLimit="100">
     *<table name="khdx_jg" primaryKey="KHDXDH" autoIncrement="true" dataNode="dn17"/>
     *</schema>
	 */
	private static void addSchema(Element root,String name){
		System.out.println(name);
		Element e = root.addElement("schema");
		e.addAttribute("name", name+"_schema");
		e.addAttribute("checkSQLschema", "false");
		e.addAttribute("sqlMaxLimit", "100");
		
		Element table = e.addElement("table");
		table.addAttribute("name", "khdx_jg");
		table.addAttribute("primaryKey", "KHDXDH");
		table.addAttribute("autoIncrement", "true");
		table.addAttribute("dataNode", name);
		
	}
	
	public static void addServer(String filepath,String name){
		Document doc = XmlParser.getDocument(filepath);
		Element root = doc.getRootElement();
		List<Element> nodes = root.selectNodes("user");
		System.out.println(nodes.size());
		
		if(nodes.size()>0){
			for(Element e:nodes){
				List<Element> properties = e.selectNodes("property");
				for(Element p:properties){
					if(p.attribute("name").getText().equals("schemas")){
						//System.out.println(p.getText());
						String value = p.getText();
						value = value+","+name+"_schema";
						p.setText(value);
					}
				}
			}
		}
		saveDocument(filepath,doc);
	}
	
	public static void delServer(String filepath,String name){
		Document doc = XmlParser.getDocument(filepath);
		Element root = doc.getRootElement();
		List<Element> nodes = root.selectNodes("user");
		if(nodes.size()>0){
			for(Element e:nodes){
				List<Element> properties = e.selectNodes("property");
				for(Element p:properties){
					if(p.attribute("name").getText().equals("schemas")){
						//System.out.println(p.getText());
						String value = p.getText();
						String schemaname = name+"_schema";
						//value = value+","+name+"_schema";
						//p.setText(value);
						String[] schemanames = value.split(",");
						StringBuffer newvalue = new StringBuffer();
						for(String s:schemanames){
							if(s.equals(schemaname)){
								
							}else{
								newvalue.append(s).append(",");
							}
						}
						String newvalues = newvalue.toString().substring(0, newvalue.toString().length()-1);
						p.setText(newvalues);
						
					}
				}
			}
		}
		saveDocument(filepath,doc);
	}
	
	private static void delSchema(Element root,String name){
		List<Element> nodes = root.selectNodes("schema");
		String schemaname = name+"_schema";
		if(nodes.size()>0){
			for(Element e:nodes){
				String s = e.attribute("name").getText();
				if(s.equals(schemaname)){
					//System.out.println(e.asXML());
					root.remove(e);
				}
			}
		}
	}
	
	private static void saveDocument(String schemaPath,Document doc){
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
	
	private static void sort(Element root){
		List<Element> schemas = root.selectNodes("schema");
		List<Element> datanodes = root.selectNodes("dataNode");
		List<Element> dataHosts = root.selectNodes("dataHost");
		
		for(Element schema:schemas){
			root.remove(schema);
		}
		for(Element datanode:datanodes){
			root.remove(datanode);
		}
		for(Element datahost:dataHosts){
			root.remove(datahost);
		}
		
		for(Element schema:schemas){
			root.add(schema);
		}
		for(Element datanode:datanodes){
			root.add(datanode);
		}
		for(Element datahost:dataHosts){
			root.add(datahost);
		}
		
		
	}
	
	public static void main(String[] args) {
		String filepath = "D:/eclipse64/devops/pascloud-devops-parent/pascloud-webapps/src/main/webapp/static/resources/mycat/conf/schema.xml";
		String serverpath = "D:/eclipse64/devops/pascloud-devops-parent/pascloud-webapps/src/main/webapp/static/resources/mycat/conf/server.xml";
		
		//MycatXmlUtils.addDataNode(filepath);
		
		//MycatXmlUtils.addSchemaAndNodeAndHost(filepath,"dn22", "oracle", "192.168.0.16", "pas", "pas", "cpas", 1521);
		//MycatXmlUtils.delSchemaAndNodeAndHost(filepath, "dn22");
		MycatXmlUtils.setDataHostWithDn0(filepath, "192.168.0.16", "pascloud", 3306, "root", "root",serverpath);
		//String serverpath = "D:/eclipse64/devops/pascloud-devops-parent/pascloud-webapps/src/main/webapp/static/resources/mycat/conf/server.xml";
		
		//MycatXmlUtils.addServer(serverpath, "dn22");
		//MycatXmlUtils.delServer(serverpath, "dn22");
		
		//Document doc = MycatXmlUtils.addSchemaAndNodeAndHost(filepath,"dn22", "test", "oracle", "192.168.0.16", "pas", "pas", "cpas", 1521);
		//MycatXmlUtils.saveDocument(filepath, doc);
		
		//MycatXmlUtils.addServer(filepath, "dn22");
		
		//Document doc = XmlParser.getDocument(filepath);
		//Element root = doc.getRootElement();
		//MycatXmlUtils.delSchema(root, "dn17");
		//MycatXmlUtils.delDataNode(root, "dn20");
		//MycatXmlUtils.delDataHost(root, "orahost20");
		//MycatXmlUtils.saveDocument(filepath, doc);
		//MycatXmlUtils.delServer(filepath, "dn17");
		
	}
}
