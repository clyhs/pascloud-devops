package com.pascloud.cluster;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.cluster.server.ClusterServer;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.redis.jedis.JedisClusterConfig;
import com.pascloud.core.thread.ThreadPoolExecutorConfig;
import com.pascloud.core.utils.FileUtil;
import com.pascloud.core.utils.SysUtil;



/**
 * Hello world!
 *
 */
public class AppCluster 
{
	private static final Logger log = LoggerFactory.getLogger(AppCluster.class);

	private static ClusterServer clusterServer;
	public static String path = "";
	
    public static void main( String[] args )
    {
    	File file = new File(System.getProperty("user.dir"));
		if ("target".equals(file.getName())) {
			path = file.getPath() + File.separatorChar + "config";
		} else {
			path = file.getPath() + File.separatorChar + "target" + File.separatorChar + "config";
		}
		log.info("配置路径为：" + path);
		JedisClusterConfig jedisClusterConfig = FileUtil.getConfigXML(path, "jedisclusterConfig.xml",
				JedisClusterConfig.class);
		if (jedisClusterConfig == null) {
			SysUtil.exit(AppCluster.class, null, "jedisclusterConfig");
		}
		ThreadPoolExecutorConfig threadExcutorConfig_http = FileUtil.getConfigXML(path, "threadExcutorConfig_http.xml",
				ThreadPoolExecutorConfig.class);
		if (threadExcutorConfig_http == null) {
			SysUtil.exit(AppCluster.class, null, "threadExcutorConfig_http");
		}
		ThreadPoolExecutorConfig threadExcutorConfig_tcp = FileUtil.getConfigXML(path, "threadExcutorConfig_tcp.xml",
				ThreadPoolExecutorConfig.class);
		if (threadExcutorConfig_tcp == null) {
			SysUtil.exit(AppCluster.class, null, "threadExcutorConfig_tcp");
		}
		MinaServerConfig minaServerConfig_http = FileUtil.getConfigXML(path, "minaServerConfig_http.xml",
				MinaServerConfig.class);
		if (minaServerConfig_http == null) {
			SysUtil.exit(AppCluster.class, null, "minaServerConfig_http");
		}
		MinaServerConfig minaServerConfig_tcp = FileUtil.getConfigXML(path, "minaServerConfig_tcp.xml",
				MinaServerConfig.class);
		if (minaServerConfig_tcp == null) {
			SysUtil.exit(AppCluster.class, null, "minaServerConfig_tcp");
		}
		//RedisManager redisManager = new RedisManager(jedisClusterConfig);

		clusterServer = new ClusterServer(threadExcutorConfig_http, minaServerConfig_http, threadExcutorConfig_tcp,
				minaServerConfig_tcp);
		new Thread(clusterServer).start();
    }
    
    public static ClusterServer getClusterServer() {
		return clusterServer;
	}
}
