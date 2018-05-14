package com.pascloud.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pascloud.bean.server.ServerVo;
import com.pascloud.utils.FileUtils;
import com.thoughtworks.xstream.XStream;

public class XStreamDemo {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<ServerVo> list = new ArrayList<>();
		/*
		ServerVo vo = new ServerVo();
		vo.setIp("192.168.0.16");
		vo.setPort("22");
		vo.setUsername("root");
		vo.setPassword("tccp@2012");
		vo.setDesc("应用服务器");
		
		ServerVo v1 = new ServerVo();
		v1.setIp("192.168.0.7");
		v1.setPort("22");
		v1.setUsername("root");
		v1.setPassword("tccp@2012");
		v1.setDesc("应用服务器");
		
		ServerVo v2 = new ServerVo();
		v2.setIp("192.168.0.17");
		v2.setPort("22");
		v2.setUsername("root");
		v2.setPassword("tccp@2012");
		v2.setDesc("数据库服务器");

		XStream xstream = new XStream(); 
		xstream.alias("server", ServerVo.class);
		list.add(vo);
		list.add(v1);
		list.add(v2);
		
		String xml = xstream.toXML(list);*/
		
		//System.out.println(xml);
		
		String header =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		
		String path = "d:/server.xml";
		
        File file = new File(path);
        XStream xstream = new XStream(); 
		//FileUtils.writeFileFromString(file, xml, false);
        xstream.alias("server", ServerVo.class);
        list =  (List<ServerVo>) xstream.fromXML(file);
        System.out.println(list.size());
		
	}

}
