package com.pas.cloud.studio.bean;

import java.io.Serializable;

public class Selects implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -3015190795416366906L;
	//	查询结果。array｛tname,cname,name｝表名，字段名，别名
	private String tname;
	private String cname;
	private String name;
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	
}
