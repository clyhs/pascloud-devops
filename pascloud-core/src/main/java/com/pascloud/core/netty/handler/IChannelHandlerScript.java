package com.pascloud.core.netty.handler;

import com.pascloud.core.mina.config.BaseServerConfig;
import com.pascloud.core.script.IScript;
import com.pascloud.core.server.AbsService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

/**
 * netty handler脚本
 */
public interface IChannelHandlerScript extends IScript{
	
	/**
	 * channel 激活
	 * @param handlerClass 
	 * @param channel
	 */
	default void channelActive(Class<? extends ChannelHandler> handlerClass,Channel channel){
		
	}
	
	/**
	 * channel 激活
	 * @param handlerClass 
	 * @param channel
	 */
	default void channelActive(Class<? extends ChannelHandler> handlerClass,AbsService<? extends BaseServerConfig> service,Channel channel){
		
	}
	
	/**
	 * channel 空闲
	 * @param handlerClass
	 * @param channel
	 */
	default void channelInActive(Class<? extends ChannelHandler> handlerClass,Channel channel){
		
	}

}
