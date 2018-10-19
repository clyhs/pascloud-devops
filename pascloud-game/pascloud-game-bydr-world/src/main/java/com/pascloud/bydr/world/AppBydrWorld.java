package com.pascloud.bydr.world;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.world.server.BydrWorldServer;
import com.pascloud.core.redis.jedis.JedisManager;
import com.pascloud.core.script.ScriptManager;


/**
 * Hello world!
 *
 */
public class AppBydrWorld 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AppBydrWorld.class);
	private static String configPath;
	protected static JedisManager redisManager;
	private static BydrWorldServer bydrWorldServer;

	public static void main(String[] args) {
		initConfigPath();
		// redis
		redisManager = new JedisManager(configPath);

		// 加载脚本
		ScriptManager.getInstance().init(str -> System.exit(0));
		
		bydrWorldServer=new BydrWorldServer(configPath);
		new Thread(bydrWorldServer).start();
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

	public static BydrWorldServer getBydrWorldServer() {
		return bydrWorldServer;
	}
}
