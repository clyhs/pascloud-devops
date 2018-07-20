package com.pas.cloud.studio.bean;

import java.io.Serializable;

public class ResultShowLogic implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 6444371704334061069L;
	//{"name":"selecth","value":"1","rsName":"新结果集1"}{"name":"selecth","value":"2","rsName":"新结果集2"}
	private String name;
	private String value;
	private String rsName;
	private String logic;
	public String getLogic() {
		return logic;
	}
	public void setLogic(String logic) {
		this.logic = logic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRsName() {
		return rsName;
	}
	public void setRsName(String rsName) {
		this.rsName = rsName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
