package com.pascloud.module.passervice.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.passervice.service.ConfigService;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.xml.XmlParser;
import com.pascloud.vo.common.MapVo;
import com.pascloud.vo.pass.MqVo;
import com.pascloud.vo.pass.MycatVo;
import com.pascloud.vo.pass.PasConfigVo;
import com.pascloud.vo.pass.RedisVo;
import com.pascloud.vo.pass.ZookeeperVo;
import com.thoughtworks.xstream.XStream;


@Controller
@RequestMapping("module/config")
public class ConfigController extends BaseController {
	
	
	@Autowired
	private PasService    m_pasService;
	
	@RequestMapping("index.html")
	public String index(HttpServletRequest request){
		return "pasService/config";
	}
	
	@RequestMapping("config.json")
	@ResponseBody
	public List<MapVo> getPasConfig(HttpServletRequest request){
		List<MapVo> map = new ArrayList<>();
		log.info("查询配置信息");
		//PasConfigVo config = m_pasService.getPasConfig();
		//map = m_pasService.convertPasConfigToList(config);
		
		map = m_pasService.convertPasConfigToList();
		return map;
	}

	public static void main(String[] args){
		PasConfigVo c = new PasConfigVo();
		
		ZookeeperVo zk = new ZookeeperVo();
		zk.setIp("192.168.0.7");
		zk.setPort(2181);
		
		MycatVo mycat = new MycatVo();
		mycat.setIp("192.168.0.7");
		mycat.setPort(8066);
		
		RedisVo redis = new RedisVo();
		redis.setIp("192.168.0.7");
		redis.setPort(6379);
		
		MqVo mq = new MqVo();
		mq.setIp("192.168.0.7");
		mq.setPort(61616);
		
		c.setZookeeper(zk);
		c.setMycat(mycat);
		c.setRedis(redis);
		c.setMq(mq);
		
		String header =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		
		XStream xstream = new XStream(); 
		xstream.alias("config", PasConfigVo.class);
		String xml = xstream.toXML(c);
		xml = header + xml;
		
        String path = "d:/config.xml";
		
		File file = new File(path);
		
		//FileUtils.writeFileFromString(file, xml, false);
        
        Document doc = XmlParser.getDocument(path);
		
		//System.out.println(doc.getRootElement().asXML());
		
		PasConfigVo a = (PasConfigVo) xstream.fromXML(file);
		System.out.println(a.getZookeeper().getIp());
	}
}
