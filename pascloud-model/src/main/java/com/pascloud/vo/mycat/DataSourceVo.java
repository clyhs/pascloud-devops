package com.pascloud.vo.mycat;

import java.io.Serializable;

public class DataSourceVo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String  datanode;
	
	private String  name;
	
	private String  type;
	
	private String  host;
	
	private Integer port;
	
	private Integer active;
	
	private Integer idle;
	
	private Integer size;
	
	private Integer execute;
	
	private Integer read_load;
	
	private Integer write_load;

	public String getDatanode() {
		return datanode;
	}

	public void setDatanode(String datanode) {
		this.datanode = datanode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getIdle() {
		return idle;
	}

	public void setIdle(Integer idle) {
		this.idle = idle;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getExecute() {
		return execute;
	}

	public void setExecute(Integer execute) {
		this.execute = execute;
	}

	public Integer getRead_load() {
		return read_load;
	}

	public void setRead_load(Integer read_load) {
		this.read_load = read_load;
	}

	public Integer getWrite_load() {
		return write_load;
	}

	public void setWrite_load(Integer write_load) {
		this.write_load = write_load;
	}

}
