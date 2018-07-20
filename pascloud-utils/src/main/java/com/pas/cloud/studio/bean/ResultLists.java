package com.pas.cloud.studio.bean;

import java.io.Serializable;

public class ResultLists implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1950478346628658309L;
	//	结果列表{check,name,cName,format,width,align} 是否显示，英文名称，中文，格式化，宽度，对齐方式
	private String check;
	private String name;
	private String cName;
	private String format;
	private String width;
	private String align;
	private String exp;
	private String zsy;
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public String getCName() {
		return cName;
	}
	public void setCName(String name) {
		cName = name;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getZsy() {
		return zsy;
	}
	public void setZsy(String zsy) {
		this.zsy = zsy;
	}
	
}
