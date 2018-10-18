package com.pascloud.core.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.netty.code.DefaultClientChannelInitializer;
import com.pascloud.core.netty.config.NettyClientConfig;
import com.pascloud.core.netty.service.NettyClientService;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * netty多连接tcp，连接多个服务器
 * 
 */
public class NettyMutilTcpClient {
	private static final Logger LOGGER=LoggerFactory.getLogger(NettyMutilTcpClient.class);
	
	private final Map<Integer, NettyTcpClient> tcpClients=new ConcurrentHashMap<>();

	public NettyMutilTcpClient() {
		super();
	}
	
	/**
	 * 添加客户端
	 * 
	 * @param service
	 * @param config
	 * @param clientProtocolHandler
	 */
	public void addTcpClient(NettyClientService service, NettyClientConfig config, ChannelInitializer<SocketChannel> channelInitializer) {
		NettyTcpClient client = null;
		if (tcpClients.containsKey(config.getId())) {
			client = tcpClients.get(config.getId());
			client.setNettyClientConfig(config);
			return;
		}
		client = new NettyTcpClient(service, channelInitializer,config);
//		new Thread(client).start();
		tcpClients.put(config.getId(), client);
	}

	/**
	 * 添加客户端
	 * 
	 * @param service
	 * @param config
	 * @param clientProtocolHandler
	 */
	public void addTcpClient(NettyClientService service, NettyClientConfig config) {
		this.addTcpClient(service, config, new DefaultClientChannelInitializer(service));
	}

	public NettyTcpClient getTcpClient(Integer id) {
		if (!tcpClients.containsKey(id)) {
			return null;
		}
		return tcpClients.get(id);
	}

	public void removeTcpClient(Integer id) {
		tcpClients.remove(id);
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public boolean containsKey(Integer id) {
		return tcpClients.containsKey(id);
	}

	/**
	 * 向服务器发送数据
	 *
	 * @param sid
	 *            客户端ID
	 * @param obj 消息
	 * @return
	 */
	public boolean sendMsg(Integer sid, Object obj) {
		if (!tcpClients.containsKey(sid)) {
			return false;
		}
		NettyTcpClient client = tcpClients.get(sid);
		if (client == null) {
			return false;
		}
		return client.getService().sendMsg(obj);
	}
	
	
	/**
	 * 监测服务器状态
	 */
	public void checkStatus(){
		tcpClients.forEach((id,client)->client.checkStatus());
	}

	public Map<Integer, NettyTcpClient> getTcpClients() {
		return tcpClients;
	}
}
