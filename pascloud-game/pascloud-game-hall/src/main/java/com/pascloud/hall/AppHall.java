package com.pascloud.hall;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.redis.jedis.JedisManager;
import com.pascloud.hall.server.HallServer;


/**
 * Hello world!
 *
 */
public class AppHall 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AppHall.class);
	private static String configPath;
	protected static JedisManager redisManager;
	private static HallServer bydrServer;
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
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

	public static HallServer getBydrServer() {
		return bydrServer;
	}
}
