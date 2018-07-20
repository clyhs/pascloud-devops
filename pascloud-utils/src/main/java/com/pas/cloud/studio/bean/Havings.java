package com.pas.cloud.studio.bean;

import java.io.Serializable;

public class Havings implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 5393922820136868559L;
	//	having条件，array｛tname,cname，logic,ttname,tcname,isvalue｝//同where条件
	private String sname;
	private String scname;
	private String logic;
	private String tname;
	private String tcname;
	private String isvalue;
	public String getIsvalue() {
		return isvalue;
	}
	public void setIsvalue(String isvalue) {
		this.isvalue = isvalue;
	}
	public String getLogic() {
		return logic;
	}
	public void setLogic(String logic) {
		this.logic = logic;
	}
	public String getScname() {
		return scname;
	}
	public void setScname(String scname) {
		this.scname = scname;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getTcname() {
		return tcname;
	}
	public void setTcname(String tcname) {
		this.tcname = tcname;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
}
