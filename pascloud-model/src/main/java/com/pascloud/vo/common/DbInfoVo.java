package com.pascloud.vo.common;

import java.io.Serializable;

public class DbInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String key;
	
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	

}
