package com.pascloud.bydr;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.manager.MongoManager;
import com.pascloud.bydr.server.BydrServer;
import com.pascloud.core.redis.jedis.JedisManager;
import com.pascloud.core.script.ScriptManager;


/**
 * Hello world!
 *
 */
public class AppBydr 
{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppBydr.class);
	private static String configPath;
	protected static JedisManager redisManager;
	private static BydrServer bydrServer;
	
    public static void main( String[] args )
    {
        
        initConfigPath();
		// redis
		redisManager = new JedisManager(configPath);
		redisManager.initScript(configPath);
		JedisManager.setRedisManager(redisManager);
//		String result = RedisManager.getInstance().executeScript("Test", Arrays.asList("foo"), Arrays.asList("jzy"));
//		LOGGER.debug("redis 脚本测试:" + result);
//		RedissonManager.connectRedis(configPath);

		
		// 创建mongodb连接
		MongoManager.getInstance().createConnect(configPath);

		// 加载脚本
		ScriptManager.getInstance().init(str -> System.exit(0));

		// 启动通信连接
		bydrServer = new BydrServer(configPath);
		new Thread(bydrServer).start();
    }
    
    private static void initConfigPath() {
		File file = new File(System.getProperty("user.dir"));
		if ("target".equals(file.getName())) {
			configPath = file.getPath() + File.separatorChar + "config";
		} else {
			configPath = file.getPath() + File.separatorChar + "target" + File.separatorChar + "config";
		}
		LOGGER.info("配置路径为：" + configPath);
	}

	public static BydrServer getBydrServer() {
		return bydrServer;
	}
    
    public static String getConfigPath() {
		return configPath;
	}
}
