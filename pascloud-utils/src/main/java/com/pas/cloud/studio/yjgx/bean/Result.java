package com.pas.cloud.studio.yjgx.bean;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {

	private List<Table> listTable;
	private List<Table> mainTable;

	public List<Table> getListTable() {
		return listTable;
	}

	public void setListTable(List<Table> listTable) {
		this.listTable = listTable;
	}

	public List<Table> getMainTable() {
		return mainTable;
	}

	public void setMainTable(List<Table> mainTable) {
		this.mainTable = mainTable;
	}

}
