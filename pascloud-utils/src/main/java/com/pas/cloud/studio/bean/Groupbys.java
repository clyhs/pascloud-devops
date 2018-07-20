package com.pas.cloud.studio.bean;

import java.io.Serializable;

public class Groupbys implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1111005638649920614L;
	//	聚合条件，array{tname,cname}
	private String tname;
	private String cname;
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
}
