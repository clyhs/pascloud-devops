package com.pascloud.vo.database;

import java.io.Serializable;

public class DBColumnVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String columnName;
	
	private String dataType;
	
	private String isNullable;
	
	private String columnType;
	
	private String columnComment;
	
	private String columnKey;
	
	

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	

}
