package com.pascloud.core.mina.config;

import java.util.Objects;

import org.simpleframework.xml.Element;

public abstract class BaseServerConfig {
	
	// 服务器标识
    @Element(required = false)
    protected int id;
    
    // 服务器名称
    @Element(required = false)
    protected String name="无";
    
    // 服务器渠道
    @Element(required = false)
    protected String channel;
    
    // 服务器版本
    @Element(required = false)
    protected String version="0.0.1";
    
    // 接收数据缓冲大小
    @Element(required = false)
    protected int receiveBufferSize = 1048576;

    // 发送数据缓冲大小
    @Element(required = false)
    protected int sendBufferSize = 1048576;
    
    // 接收数据最大字节数
    @Element(required = false)
    protected int maxReadSize = 1048576;

    // 发送数据缓冲消息数
    @Element(required = false)
    protected int maxScheduledWriteMessages = 1024;
    

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String web) {
        this.channel = web;
    }

    // eq id + channel
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.channel);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseServerConfig other = (BaseServerConfig) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.channel, other.channel)) {
            return false;
        }
        return true;
    }

    public int getMaxReadSize() {
        return maxReadSize;
    }

    public void setMaxReadSize(int maxReadSize) {
        this.maxReadSize = maxReadSize;
    }

    public int getMaxScheduledWriteMessages() {
        return maxScheduledWriteMessages;
    }

    public void setMaxScheduledWriteMessages(int maxScheduledWriteMessages) {
        this.maxScheduledWriteMessages = maxScheduledWriteMessages;
    }

    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public void setReceiveBufferSize(int receiveBufferSize) {
        this.receiveBufferSize = receiveBufferSize;
    }

    public int getSendBufferSize() {
        return sendBufferSize;
    }

    public void setSendBufferSize(int sendBufferSize) {
        this.sendBufferSize = sendBufferSize;
    }

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
    

}
