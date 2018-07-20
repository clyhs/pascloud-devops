package com.pas.cloud.studio.bean;

import java.io.Serializable;

public class Wheres implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -7324824673647654399L;
	//	关联条件 array｛sname,scname，logic,tname,tcname,isvalue｝//源表名、列名、逻辑符号，目标表名、目标列名、是否等于常量，当表关联时，为undefined
	private String sname;
	private String scname;
	private String logic;
	private String tname;
	private String tcname;
	private String isvalue;
	//add fix  -- isbetween,btname,btcname,isglobal,globalname,isbindSearchCon,searchConName
	private String isbetween;
	private String btname;
	private String btcname;
	private String isglobal;
	private String globalname;
	private String isbindSearchCon;
	private String searchConName;
	private String ist2t;
	public String getIst2t() {
		return ist2t;
	}
	public void setIst2t(String ist2t) {
		this.ist2t = ist2t;
	}
	public String getBtname() {
		return btname;
	}
	public void setBtname(String btname) {
		this.btname = btname;
	}
	public String getGlobalname() {
		return globalname;
	}
	public void setGlobalname(String globalname) {
		this.globalname = globalname;
	}
	public String getIsbetween() {
		return isbetween;
	}
	public void setIsbetween(String isbetween) {
		this.isbetween = isbetween;
	}
	public String getIsbindSearchCon() {
		return isbindSearchCon;
	}
	public void setIsbindSearchCon(String isbindSearchCon) {
		this.isbindSearchCon = isbindSearchCon;
	}
	public String getIsglobal() {
		return isglobal;
	}
	public void setIsglobal(String isglobal) {
		this.isglobal = isglobal;
	}
	public String getSearchConName() {
		return searchConName;
	}
	public void setSearchConName(String searchConName) {
		this.searchConName = searchConName;
	}
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
	public String getBtcname() {
		return btcname;
	}
	public void setBtcname(String btcname) {
		this.btcname = btcname;
	}
}
