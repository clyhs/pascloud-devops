package com.pas.cloud.studio.bean;

import java.io.Serializable;

public class Tables implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6552498394368542850L;
	//相关表的名称、别名。array｛tname,name｝
	private String tname;
	private String name;
	private String joinType;
	public String getJoinType() {
		return joinType;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
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
