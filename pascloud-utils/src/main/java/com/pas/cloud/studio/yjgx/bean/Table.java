package com.pas.cloud.studio.yjgx.bean;

import java.io.Serializable;

public class Table implements Serializable {

	private String id;
	private String title;
	private String tvalue;
	/**
	 * 表别名
	 */
	private String tableAlias;
	private String type;
	private String source;
	private String align;
	private String width;
	private String format;

	private String isExp;// 是否导出

	private String isZsy;

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTvalue() {
		return tvalue;
	}

	public void setTvalue(String tvalue) {
		this.tvalue = tvalue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getIsExp() {
		return isExp;
	}

	public void setIsExp(String isExp) {
		this.isExp = isExp;
	}

	public String getIsZsy() {
		return isZsy;
	}

	public void setIsZsy(String isZsy) {
		this.isZsy = isZsy;
	}
}
