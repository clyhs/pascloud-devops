package com.pascloud.vo.server;

import java.io.Serializable;

public class SysServerInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cpu_idle ;
	
	private String cpu_used;
	
	private String memory_used;
	
	private String memory_total ;
	
	private String memory_free;
	
	private String os;
	
	private String uname ;
	
	private String ip;
	
	private String hostname;
	
	
	

	public String getMemory_used() {
		return memory_used;
	}

	public void setMemory_used(String memory_used) {
		this.memory_used = memory_used;
	}

	public String getCpu_used() {
		return cpu_used;
	}

	public void setCpu_used(String cpu_used) {
		this.cpu_used = cpu_used;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	

	public String getCpu_idle() {
		return cpu_idle;
	}

	public void setCpu_idle(String cpu_idle) {
		this.cpu_idle = cpu_idle;
	}

	public String getMemory_total() {
		return memory_total;
	}

	public void setMemory_total(String memory_total) {
		this.memory_total = memory_total;
	}

	public String getMemory_free() {
		return memory_free;
	}

	public void setMemory_free(String memory_free) {
		this.memory_free = memory_free;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}
	
	
	
}
