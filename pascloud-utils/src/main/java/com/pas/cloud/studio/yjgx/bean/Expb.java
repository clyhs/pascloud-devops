package com.pas.cloud.studio.yjgx.bean;

import java.io.Serializable;
import java.util.List;

public class Expb implements Serializable {
	private String qzbJoinType;
	private String tableName;
	/**
	 * 表别名
	 */
	private String tableAlias;
	private String fieldName;
	private String dbType;
	private String operator;
	private String con_tableName;
	private String con_fieldName;
	private String con_dbType;
	private List<TableCols> tableCols;
	private String limitCondition;

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	public String getQzbJoinType() {
		return qzbJoinType;
	}

	public void setQzbJoinType(String qzbJoinType) {
		this.qzbJoinType = qzbJoinType;
	}

	public String getLimitCondition() {
		return limitCondition;
	}

	public void setLimitCondition(String limitCondition) {
		this.limitCondition = limitCondition;
	}

	public List<TableCols> getTableCols() {
		return tableCols;
	}

	public void setTableCols(List<TableCols> tableCols) {
		this.tableCols = tableCols;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getCon_dbType() {
		return con_dbType;
	}

	public void setCon_dbType(String con_dbType) {
		this.con_dbType = con_dbType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCon_tableName() {
		return con_tableName;
	}

	public void setCon_tableName(String con_tableName) {
		this.con_tableName = con_tableName;
	}

	public String getCon_fieldName() {
		return con_fieldName;
	}

	public void setCon_fieldName(String con_fieldName) {
		this.con_fieldName = con_fieldName;
	}

}
