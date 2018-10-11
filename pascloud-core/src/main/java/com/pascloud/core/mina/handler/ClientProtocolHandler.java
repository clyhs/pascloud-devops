package com.pascloud.core.mina.handler;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;
import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.IHandler;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.core.mina.config.BaseServerConfig;
import com.pascloud.core.mina.config.MinaServerConfig;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.server.AbsService;
import com.pascloud.core.utils.IntUtil;
import com.pascloud.core.utils.MsgUtil;

public class ClientProtocolHandler extends DefaultProtocolHandler {

	private static final Logger log = LoggerFactory.getLogger(ClientProtocolHandler.class);
	protected AbsService<MinaServerConfig> service;

	public ClientProtocolHandler(int messageHeaderLength) {
		super(messageHeaderLength);
	}

	@Override
	public void messageReceived(IoSession session, Object obj) throws Exception {
		byte[] bytes = (byte[]) obj;
		try {
			if (bytes.length < messageHeaderLength) {
				log.error("messageReceived:消息长度{}小于等于消息头长度{}", bytes.length, messageHeaderLength);
				return;
			}
			int mid = IntUtil.bigEndianByteToInt(bytes, 0, 4); // 消息ID
			// int protoLength=IntUtil.bigEndianByteToInt(bytes, 6, 4); //TODO
			// 消息长度,不需要？

			if (ScriptManager.getInstance().tcpMsgIsRegister(mid)) {
				Class<? extends IHandler> handlerClass = ScriptManager.getInstance().getTcpHandler(mid);
				HandlerEntity handlerEntity = ScriptManager.getInstance().getTcpHandlerEntity(mid);
				if (handlerClass != null) {
//					log.info("{} {} bytes:{}",messageHeaderLenght, bytes.length, bytes );
					Message message = MsgUtil.buildMessage(handlerEntity.msg(), bytes, messageHeaderLength,
							bytes.length - messageHeaderLength);
					TcpHandler handler = (TcpHandler) handlerClass.newInstance();
					if (handler != null) {
						messageHandler(session, handlerEntity, message, handler);
						return;
					}
				}
			}
			forward(session, mid, bytes);

		} catch (Exception e) {
			log.error("messageReceived", e);
		}
	}

	@Override
	protected void forward(IoSession session, int msgID, byte[] bytes) {
		log.warn("消息[{}]未实现", msgID);

	}

	@Override
	public AbsService<? extends BaseServerConfig> getService() {
		return service;
	}

	public void setService(AbsService<MinaServerConfig> service) {
		this.service = service;
	}

}
