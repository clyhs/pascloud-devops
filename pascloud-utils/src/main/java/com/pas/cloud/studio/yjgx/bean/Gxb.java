package com.pas.cloud.studio.yjgx.bean;

import java.io.Serializable;
import java.util.List;

public class Gxb implements Serializable {

	private List<TableCols> tableCols;
	private String tableName;

	public List<TableCols> getTableCols() {
		return tableCols;
	}

	public void setTableCols(List<TableCols> tableCols) {
		this.tableCols = tableCols;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
