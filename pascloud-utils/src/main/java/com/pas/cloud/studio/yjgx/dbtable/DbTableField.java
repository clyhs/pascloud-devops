package com.pas.cloud.studio.yjgx.dbtable;

import java.io.Serializable;

/**
 * 数据库表字段
 * 
 * @author NongFei
 * @version 1.0
 * @created 24-二月-2014 14:37:03
 */
public class DbTableField implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3557241969663768509L;
	/**
	 * 字段名
	 */
	private String name;
	/**
	 * 字段数据类型
	 */
	private String dbType;
	/**
	 * 是否主键
	 */
	private boolean isPk = false;

	public DbTableField() {

	}

	public void finalize() throws Throwable {

	}

	public String getDbType() {
		return dbType;
	}

	public boolean isPk() {
		return isPk;
	}

	public String getName() {
		return name;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public void setPk(boolean isPk) {
		this.isPk = isPk;
	}

	public void setName(String name) {
		this.name = name;
	}

}