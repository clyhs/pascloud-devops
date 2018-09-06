package com.pas.cloud.studio.yjgx.bean;

import java.io.Serializable;

public class ListTag implements Serializable {

	private String name;
	private String field;
	private String type;
	private String valcn;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValcn() {
		return valcn;
	}

	public void setValcn(String valcn) {
		this.valcn = valcn;
	}

}
