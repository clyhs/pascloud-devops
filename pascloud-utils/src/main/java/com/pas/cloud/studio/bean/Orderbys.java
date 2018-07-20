package com.pas.cloud.studio.bean;

import java.io.Serializable;

public class Orderbys implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 8986270175457906314L;
	//	排序条件,array｛name，cname，orderby ｝
	private String name;
	private String cname;
	private String orderby;
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
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
}
