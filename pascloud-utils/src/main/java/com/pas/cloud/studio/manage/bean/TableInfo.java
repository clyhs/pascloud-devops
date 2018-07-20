package com.pas.cloud.studio.manage.bean;

import java.io.Serializable;

/***
 * 表信息
 * 
 * @author joy.shen
 * @version 1.0
 * @since 2012-05-30
 * 
 */
public class TableInfo implements Serializable {

	private static final long serialVersionUID = -2045389032539995280L;
	private String fieldName;
	private String dataType;
	private String isPrimaryKey;
	private String isEdit;
	private String bindTag;
	private String isQjpz;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(String isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getBindTag() {
		return bindTag;
	}

	public void setBindTag(String bindTag) {
		this.bindTag = bindTag;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getIsQjpz() {
		return isQjpz;
	}

	public void setIsQjpz(String isQjpz) {
		this.isQjpz = isQjpz;
	}

}
