package com.pascloud.hall.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.mina.config.MinaClientConfig;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.mq.MQConsumer;
import com.pascloud.core.redis.jedis.JedisPubListener;
import com.pascloud.core.server.ServerState;
import com.pascloud.core.thread.ThreadPoolExecutorConfig;
import com.pascloud.core.utils.FileUtil;
import com.pascloud.game.model.constant.Config;
import com.pascloud.game.model.redis.channel.HallChannel;
import com.pascloud.game.model.timer.GameServerCheckTimer;
import com.pascloud.hall.AppHall;
import com.pascloud.message.ServerMessage;
import com.pascloud.message.ServerMessage.ServerRegisterRequest;

/**
 * 大厅服务器
 * 
 */
public class HallServer implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(HallServer.class);

    /** 连接网关 （接收网关转发过来的消息） */
    private Hall2GateClient hall2GateClient;

    /** 连接集群服 （获取各服务器信息） */
    private Hall2ClusterClient hall2ClusterClient;

    /** HTTP服务 */
    private HallHttpServer hallHttpServer;

    /** redis订阅发布 */
    private final JedisPubListener hallPubListener;

    /** 服务器状态监测 */
    private GameServerCheckTimer hallServerCheckTimer;

    /** MQ消息 */
    private MQConsumer mqConsumer;

    public HallServer(String configPath) {

        // 加载连接大厅客户端配置
        ThreadPoolExecutorConfig hallClientThreatPool = FileUtil.getConfigXML(configPath, "hallClientThreadPoolExecutorConfig.xml", ThreadPoolExecutorConfig.class);
        if (hallClientThreatPool == null) {
            LOGGER.error("{}/hallClientThreadPoolExecutorConfig.xml未找到", configPath);
            System.exit(0);
        }
        MinaClientConfig minaClientConfig_gate = FileUtil.getConfigXML(configPath, "minaClientConfig_gate.xml", MinaClientConfig.class);
        if (minaClientConfig_gate == null) {
            LOGGER.error("{}/minaClientConfig_hall.xml未找到", configPath);
            System.exit(0);
        }

        // 加载连接集群配置
        MinaClientConfig minaClientConfig_cluster = FileUtil.getConfigXML(configPath, "minaClientConfig_cluster.xml", MinaClientConfig.class);
        if (minaClientConfig_cluster == null) {
            LOGGER.error("{}/minaClientConfig_hall.xml未找到", configPath);
            System.exit(0);
        }

        // http配置
        MinaServerConfig minaServerConfig_http = FileUtil.getConfigXML(configPath, "minaServerConfig_http.xml", MinaServerConfig.class);
        if (minaServerConfig_http == null) {
            LOGGER.error("{}/minaServerConfig_http.xml未找到", configPath);
            System.exit(0);
        }

        this.hall2GateClient = new Hall2GateClient(hallClientThreatPool, minaClientConfig_gate);
        this.hall2ClusterClient = new Hall2ClusterClient(minaClientConfig_cluster);

        this.hallServerCheckTimer = new GameServerCheckTimer(hall2ClusterClient, hall2GateClient, minaClientConfig_gate);

        this.hallHttpServer = new HallHttpServer(minaServerConfig_http);

        hallPubListener = new JedisPubListener(HallChannel.getChannels());

        mqConsumer = new MQConsumer(configPath, "hall");

        Config.SERVER_ID = minaClientConfig_gate.getId();
    }

    public static HallServer getInstance() {
        return AppHall.getBydrServer();
    }

    @Override
    public void run() {
        new Thread(this.hall2GateClient).start();
        new Thread(this.hall2ClusterClient).start();
        new Thread(this.hallHttpServer).start();
        this.hallServerCheckTimer.start();
        hallPubListener.start();
        // Thread thread = new Thread(mqConsumer);
        // thread.setName("MQConsumer");
        // thread.start();
    }

    public Hall2GateClient getHall2GateClient() {
        return hall2GateClient;
    }

    public Hall2ClusterClient getHall2ClusterClient() {
        return hall2ClusterClient;
    }

    public HallHttpServer getHallHttpServer() {
        return hallHttpServer;
    }


    /**
     * 构建服务器更新注册信息
     * 
     * @param minaServerConfig
     * @return
     */
    public ServerRegisterRequest buildServerRegisterRequest(MinaClientConfig minaClientConfig) {
        ServerRegisterRequest.Builder builder = ServerRegisterRequest.newBuilder();
        ServerMessage.ServerInfo.Builder info = ServerMessage.ServerInfo.newBuilder();
        info.setId(minaClientConfig.getId());
        info.setIp("");
        info.setMaxUserCount(1000);
        info.setOnline(1);
        info.setName(minaClientConfig.getName());
        info.setState(ServerState.NORMAL.getState());
        info.setType(minaClientConfig.getType().getType());
        info.setWwwip("");
        builder.setServerInfo(info);
        return builder.build();
    }

}
