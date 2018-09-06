package com.pas.cloud.studio.yjgx.bean;

import java.io.Serializable;
import java.util.List;

public class Jxb implements Serializable {

	private String tableName;
	private List<TableCols> tableCols;
	private String[] innerFields;
	private String dataField; // 应该是 dateField

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<TableCols> getTableCols() {
		return tableCols;
	}

	public void setTableCols(List<TableCols> tableCols) {
		this.tableCols = tableCols;
	}

	public String[] getInnerFields() {
		return innerFields;
	}

	public void setInnerFields(String[] innerFields) {
		this.innerFields = innerFields;
	}

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

}
