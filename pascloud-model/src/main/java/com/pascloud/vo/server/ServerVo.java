package com.pascloud.vo.server;

import java.io.Serializable;

public class ServerVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String  ip;
	
	private String  username;
	
	private String  password;
	
	private String  port;
	
	private String  desc;
	
	private String  type;
	
	private Integer typeEnum;
	
	private String  dockerVersion;
	
	
	
	public String getDockerVersion() {
		return dockerVersion;
	}

	public void setDockerVersion(String dockerVersion) {
		this.dockerVersion = dockerVersion;
	}

	public Integer getTypeEnum() {
		return typeEnum;
	}

	public void setTypeEnum(Integer typeEnum) {
		this.typeEnum = typeEnum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
