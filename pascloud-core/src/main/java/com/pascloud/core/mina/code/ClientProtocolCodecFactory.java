package com.pascloud.core.mina.code;
public class ClientProtocolCodecFactory extends ProtocolCodecFactoryImpl {

	public ClientProtocolCodecFactory() {
		super(new ClientProtocolDecoder(), new ClientProtocolEncoder());
		//待发送的数据量过低，关闭当前连接
		encoder.overScheduledWriteBytesHandler = io -> {
			io.closeNow();
			return true;
		};
	}

}
