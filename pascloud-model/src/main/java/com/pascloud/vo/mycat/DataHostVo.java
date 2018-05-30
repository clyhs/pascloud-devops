package com.pascloud.vo.mycat;

import java.io.Serializable;

public class DataHostVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String dbType;
	
	private String url;
	
	private String user;
	
	private String password;
	
	private String dbDriver;
	
	private String port;
	
	
	

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
