package com.pas.cloud.studio.yjgx.dbtable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表
 * @author NongFei
 * @version 1.0
 * @created 24-二月-2014 14:37:02
 */
public class DbTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 数据表名
	 */
	private String name;
	/**
	 * 字段
	 */
	private List<DbTableField> cols = new ArrayList<DbTableField>();
	

	public DbTable(){

	}

	public void finalize() throws Throwable {

	}

	public List<DbTableField> getCols() {
		return cols;
	}

	public String getName() {
		return name;
	}

	public void setCols(List<DbTableField> cols) {
		this.cols = cols;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void appendDbtField(DbTableField fld){
		this.cols.add(fld);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	

}