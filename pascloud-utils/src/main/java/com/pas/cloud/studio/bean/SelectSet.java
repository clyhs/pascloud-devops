package com.pas.cloud.studio.bean;

import java.io.Serializable;

/**
 * 填充数据模板
 * @author zk
 */
public class SelectSet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String selectIs;//true or false  是否填充模板
	private String selectSQL; //填充模板sql
	private String extendAre;//扩展模板列
	
	
	public String getExtendAre() {
		return extendAre;
	}
	public void setExtendAre(String extendAre) {
		this.extendAre = extendAre;
	}
	public String getSelectIs() {
		return selectIs;
	}
	public void setSelectIs(String selectIs) {
		this.selectIs = selectIs;
	}
	public String getSelectSQL() {
		return selectSQL;
	}
	public void setSelectSQL(String selectSQL) {
		this.selectSQL = selectSQL;
	}
	
}
