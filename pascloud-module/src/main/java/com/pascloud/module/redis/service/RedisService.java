package com.pascloud.module.redis.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSParser;

import com.google.gson.Gson;
import com.pas.cloud.studio.parameters.ImportParameters;
import com.pas.cloud.studio.parameters.ManageParameters;
import com.pas.cloud.studio.parameters.Parameter;
import com.pas.cloud.studio.parameters.Parameters;
import com.pas.cloud.studio.parameters.YjgxParameters;
import com.pascloud.constant.Constants;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.module.passervice.service.PasService;
import com.pascloud.utils.redis.JedisPoolUtils;
import com.pascloud.utils.redis.SerializeUtils;
import com.pascloud.utils.xml.XmlParser;
import com.pascloud.vo.pass.RedisVo;
import com.pascloud.vo.redis.RedisInfo;
import com.pascloud.vo.redis.RedisServerDetailInfo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

/**
 * 缓存服务
 * @author chenly
 *
 */
@Service
public class RedisService extends AbstractRedisService {
	
	@Autowired
	private PasService   m_pasService;
	
	@Autowired
	private PasCloudConfig m_config;
	
	public List<String> getRedisServers(){
		List<String> lists = new ArrayList<String>();
		
		ConcurrentHashMap<String,JedisPool> map = JedisPoolUtils.getJedisPoolMaps();
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			lists.add(key);
		}
		
		return lists;
	}
	
	public List<String> getRedisDB(){
		List<String> lists = new ArrayList<String>();
		for(int i=0 ; i<defaultDBNum;i++){
			lists.add("db"+i);
		}
		return lists;
	}
	
	public List<RedisServerDetailInfo> getRedisInfo(String id){
		List<RedisServerDetailInfo> result = new ArrayList<RedisServerDetailInfo>();
		
		JedisPool jedisPool= JedisPoolUtils.getJedisPool(id);
		Jedis jedis= getJedis(jedisPool);
		
		String str = jedis.info();
		String[] lineStrs = str.split("\n");
		for(String line:lineStrs){
			if(line.indexOf(":")!=-1){
				RedisServerDetailInfo o = new RedisServerDetailInfo();
				String[] ls = line.split(":");
				o.setKey(ls[0]);
				o.setValue(ls[1]);
				result.add(o);
			}
		}
		returnResource(jedis,jedisPool);
		return result;
	}
	
	public Boolean delRedisByKey(String id,Integer index ,String selectkey){
		Boolean flag = false;
		JedisPool jedisPool = null;
		Jedis jedis = null;
		try{
			log.info("删除reids开始");
			jedisPool= JedisPoolUtils.getJedisPool(id);
			jedis= getJedis(jedisPool);
			jedis.select(index);
			//Long t = jedis.del(key);
			Set nodekeys = new HashSet();
			if (selectkey.equals("nokey")) {
				
			}else{
				nodekeys = jedis.keys("*" + selectkey + "*");
			}
			Iterator it = nodekeys.iterator();
			int i=0;
			while (it.hasNext()) {
				Map<String, Object> map = new HashMap();
				String key = (String) it.next();
				jedis.del(key);
				i++;
			}
			
			log.info("删除reids结束" + i);
		}catch(Exception e){
			log.error(e.getMessage());
			//e.printStackTrace();
		}finally{
			returnResource(jedis,jedisPool);
		}
		return flag;
	}
	
	public Map<String, Object> getDBPageData(String id,Integer index,Integer startRow,Integer pageSize,
			String selectKey){
		Map<String, Object> tempMap = new HashMap();
		List<Map<String, Object>> list = new ArrayList();
		JedisPool jedisPool = null;
		Jedis jedis = null;
		Gson g = new Gson();
		try{
			jedisPool= JedisPoolUtils.getJedisPool(id);
			jedis= getJedis(jedisPool);
			jedis.select(index);
			//total
			Long total = jedis.dbSize();
			Set nodekeys = new HashSet();
			if (selectKey.equals("nokey")) {
				if (total.longValue() > 10000L) {
					startRow = 0;
					for (int z = 0; z < pageSize; z++) {
						nodekeys.add(jedis.randomKey());
					}
				} else {
					nodekeys = jedis.keys("*");
				}
			} else {
				nodekeys = jedis.keys("*" + selectKey + "*");
			}
			
			Iterator it = nodekeys.iterator();
			int i = 1;
			String value = "";
			while (it.hasNext()) {
				if ((i >= startRow) && (i <= startRow + pageSize)) {
					Map<String, Object> map = new HashMap();
					String key = (String) it.next();
					String type = jedis.type(key);
					map.put("key", key);
					map.put("type", type);
					if (type.equals("string")) {
						value = getCacheString(jedis,key);
						if (value.length() > 80) {
							map.put("value", value.substring(0, 79) + "......");
						} else {
							map.put("value", value);
						}
					}
					if (type.equals("list")) {
						Long lon = jedis.llen(key);
						if (lon.longValue() > 20L) {
							lon = Long.valueOf(20L);
						}
						map.put("value", jedis.lrange(key, 0L, lon.longValue()));
					}
					if (type.equals("set")) {
						if(key.contains("funList")){
							Set set = getCacheSet(jedis,key);
							
							map.put("value", g.toJson(set));
						}else{
							map.put("value", jedis.smembers(key).toString());
						}
					}
					if (type.equals("zset")) {
						Long lon = jedis.zcard(key);
						if (lon.longValue() > 20L) {
							lon = Long.valueOf(20L);
						}
						Set<Tuple> set = jedis.zrangeWithScores(key, 0L, lon.longValue());
						Iterator<Tuple> itt = set.iterator();
						String ss = "";
						while (itt.hasNext()) {
							Tuple str = (Tuple) itt.next();
							ss = ss + "[" + str.getScore() + "," + str.getElement() + "],";
						}
						ss = ss.substring(0, ss.length() - 1);
						map.put("value", "[" + ss + "]");
					}
					if (type.equals("hash")) {
						Map hsmap = getCacheMap(jedis,key);
						map.put("value", g.toJson(hsmap));
					}
					list.add(map);
				} else {
					it.next();
				}
				i++;
			}
			
			if (selectKey.equals("nokey")) {
				tempMap.put("total", Integer.valueOf(Integer.parseInt(total.toString())));
			} else {
				tempMap.put("total", Integer.valueOf(i));
			}
			tempMap.put("rows", list);
			
		}catch (Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}finally{
			returnResource(jedis,jedisPool);
		}
		
		return tempMap;
	}
	
	private Set<String> getCacheSet(Jedis jedis,String key){
		Set<String> sets = new HashSet<>();
		
		Set<byte[]> gs = jedis.smembers(key.getBytes());
		
		Iterator<byte[]> it = gs.iterator();
		while(it.hasNext()){
			byte[] b = it.next();
			String s = (String) SerializeUtils.unserizlize(b);
			sets.add(s);
		}
		return sets;
	}
	
	private Map<String,String> getCacheMap(Jedis jedis,String key){
		Map<String,String> map = new HashMap<>();
		
		Map<byte[],byte[]> gmap = jedis.hgetAll(key.getBytes());
        Iterator<Entry<byte[], byte[]>> it = gmap.entrySet().iterator();
        while(it.hasNext()){
        	Map.Entry<byte[], byte[]> t = it.next();
        	String k = new String(t.getKey());
        	String v = (String) SerializeUtils.unserizlize(t.getValue());
        	map.put(k, v);
        }
		return map;
	}
	
	private String getCacheString(Jedis jedis,String key){
		String res = "";
		Object obj = SerializeUtils.unserizlize(jedis.get(key.getBytes()));
		Gson g = new Gson();
		return g.toJson(obj);
	}
	
	private synchronized Jedis getJedis(final JedisPool jedisPool) {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	private void returnResource(final Jedis jedis,final JedisPool jedisPool) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);

        }
    }
	
//	private void initRedisPoolWithServer(){
//		List<RedisVo> rediss = m_pasService.getRedisServer();
//		if(null!=rediss){
//			log.info("初始化redis--开始--");
//			for(RedisVo vo:rediss){
//				JedisPoolConfig config = new JedisPoolConfig();
//		        config.setMaxTotal(10);
//		        config.setMaxIdle(5);
//		        config.setMaxWaitMillis(15000);
//		        config.setTestOnBorrow(true);
//		        String id = vo.getIp()+":"+ vo.getPort();
//		        JedisPool jedisPool = new JedisPool(config, vo.getIp(), vo.getPort());
//		        JedisPoolUtils.addJedisPool(id, jedisPool);
//			}
//			log.info("初始化redis--结束--");
//		}
//	}
//	
	
	public Integer setCacheForPasfile(Jedis jedis,String funId,String dirId){
		Integer res = 0;
		String filepath = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT)+m_config.getPASCLOUD_DEV_DIR()+"/"+dirId;
		String filepath_xml = filepath +"/"+funId+".xml";
		String filepath_para= filepath +"/"+funId+".para";
		
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Parameter p = null;
		byte[] para_byte = null;
		try {
			//log.info("set "+funId+".xml");
			Document document = XmlParser.getDocument(filepath_xml);
			String   content  = document.asXML();
			if(null!=content){
				log.info("set "+funId+".xml");
			}
			byte[]   content_byte = SerializeUtils.serialize(content);
			String key_xml = dirId+"."+funId;
			jedis.set(key_xml.getBytes(), content_byte);
			
			//log.info("set "+funId+".para");
			
			File file_para = new File(filepath_para);
			
			if(null!=file_para){
				log.info("set "+funId+".para");
			}
			
			fis = new FileInputStream(file_para);
			ois = new ObjectInputStream(fis);
			
			
			p = (Parameter) ois.readObject();
			if(p.getFunType().equals("query")){
				Parameters para = (Parameters) p;
				para_byte = SerializeUtils.serialize(para);
			}else if(p.getFunType().equals("manage")){
				ManageParameters para = (ManageParameters) p;
				para_byte = SerializeUtils.serialize(para);
			}else if(p.getFunType().equals("import")){
				ImportParameters para = (ImportParameters) p;
				para_byte = SerializeUtils.serialize(para);
			}else if(p.getFunType().equals("yjgx")){
				YjgxParameters para = (YjgxParameters) p;
				para_byte = SerializeUtils.serialize(para);
			}
			
			String key_para = dirId+"."+funId+".para";
			
			jedis.set(key_para.getBytes(), para_byte);
			
			res = 1;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		}finally{
			if(null!=ois){
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			if(null!=fis){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
		return res;
	}
	
	public String getCacheXmlFunId(Jedis jedis,String funId,String dirId){
		String result = null;
		String key_xml = dirId+"."+funId;
		ObjectInputStream ois = null;
		InputStream is = null;
		try {
			byte[] content = jedis.get(key_xml.getBytes());
			if(null!=content){
				is = new ByteArrayInputStream(content);
				ois = new ObjectInputStream(is);
				result = (String) ois.readObject();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		}finally{
			if(null!=ois){
				try {
					ois.close();
					ois = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			if(null!=is){
				try {
					is.close();
					is=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public Parameter getCacheParaFunId(Jedis jedis,String funId,String dirId){
		Parameter p= null;
		String key_para = dirId+"."+funId+".para";
		byte[] bytes = jedis.get(key_para.getBytes());
		InputStream is = null;
		ObjectInputStream ois = null;
		try {
			is = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(is);
			p = (Parameter) ois.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		}finally{
			if(null!=ois){
				try {
					ois.close();
					ois = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			if(null!=is){
				try {
					is.close();
					is=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
		return p;
	}
	
	
	public Set<String> getCacheFunList(Jedis jedis,String dirId,String type){
		String key = dirId+"."+type+".funList";
		Set<byte[]> sets = jedis.smembers(key.getBytes());
		
		Set<String> setall = new HashSet<>();
		Iterator<byte[]> it = sets.iterator();
		while(it.hasNext()){
			byte[] b = it.next();
			String s = (String) SerializeUtils.unserizlize(b);
			setall.add(s);
		}
		
		return setall;
	}
	
	public Long setCacheFunList(Jedis jedis,String funId,String dirId,String type){
		String key = dirId+"."+type+".funList";
		String Id = dirId+"."+funId;
		
		return jedis.sadd(key.getBytes(),SerializeUtils.serialize( Id));
	}
	
	public Map<byte[],byte[]> getCacheFunMap(Jedis jedis,String dirId){
		String key = dirId+".funTitleMap";
		return jedis.hgetAll(key.getBytes());
	}
	
	public void setCacheFunMap(Jedis jedis,String funId,String title,String dirId){
		String key = dirId+".funTitleMap";
		Map<byte[],byte[]> map = null;
		map =jedis.hgetAll(key.getBytes());
		
		if(null == map){
			map = new HashMap<>();
		}
		

		String key2 = dirId+"."+funId;
		
		map.put(key2.getBytes(), SerializeUtils.serialize(title));
		jedis.hmset(key.getBytes(), map);
		
	}
	
	public void setCacheFunMapAll(Jedis jedis,Map<byte[],byte[]> map,String dirId){
		String key = dirId+".funTitleMap";
		if(null == map){
			map = new HashMap<>();
		}
		jedis.hmset(key.getBytes(), map);
	}
	
	public Map<byte[],byte[]> getCacheFunVersMap(Jedis jedis,String dirId,String funId){
		String key = dirId+"."+funId+".vers";
		return jedis.hgetAll(key.getBytes());
	}
	
	public void setCacheFunVersMap(Jedis jedis,String funId,String version,String dirId){
		String key = dirId+"."+funId+".vers";
		Map<byte[],byte[]> map = null;
		map =jedis.hgetAll(key.getBytes());
		
		if(null == map){
			map = new HashMap<>();
		}
		map.put(funId.getBytes(), SerializeUtils.serialize(version));
		jedis.hmset(key.getBytes(), map);
	}
	
	public void setCacheFunVersMap(Jedis jedis,String pid,String funId,String version,String dirId){
		String key = dirId+"."+pid+".vers";
		Map<byte[],byte[]> map = null;
		map =jedis.hgetAll(key.getBytes());
		
		if(null == map){
			map = new HashMap<>();
		}
		map.put(funId.getBytes(), SerializeUtils.serialize(version));
		jedis.hmset(key.getBytes(), map);
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		
		JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(15000);
        config.setTestOnBorrow(true);
        String id = "192.168.0.7"+":"+ "6379";
        JedisPool jedisPool = new JedisPool(config, "192.168.0.7", 6379);
        Jedis j = jedisPool.getResource();
        
        //Set<String> querys = j.smembers("dn1.query.funList");
        //j.sadd("dn1.query.funList", "test");
        //System.out.println(querys.size());
        
        
		
        /*
        Map<byte[],byte[]> map = j.hgetAll("dn2.funTitleMap".getBytes());
        Iterator<Entry<byte[], byte[]>> it = map.entrySet().iterator();
        while(it.hasNext()){
        	Map.Entry<byte[], byte[]> t = it.next();
        	String key = new String(t.getKey());
        	ByteArrayInputStream is = new ByteArrayInputStream(t.getValue());
        	ObjectInputStream ois = new ObjectInputStream(is);
        	String v = (String) ois.readObject();
        	
        	System.out.println(key+","+v);
        }*/
        
        Set<byte[]> sets = j.smembers("dn1.query.funList".getBytes());
		
		Set<String> setall = new HashSet<>();
		Iterator<byte[]> itt = sets.iterator();
		while(itt.hasNext()){
			byte[] b = itt.next();
			String s = (String) SerializeUtils.unserizlize(b);
			setall.add(s);
		}
		Gson g = new Gson();
		System.out.println(g.toJson(setall));
        
		
	}
	
}
