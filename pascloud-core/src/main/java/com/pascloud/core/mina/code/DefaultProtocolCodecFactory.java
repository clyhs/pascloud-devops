package com.pascloud.core.mina.code;

/**
 * 默认消息解析工厂
 *
 */
public class DefaultProtocolCodecFactory extends ProtocolCodecFactoryImpl{

    public DefaultProtocolCodecFactory() {
        super(new ProtocolDecoderImpl(), new ProtocolEncoderImpl());
    }
}
