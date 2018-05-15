package com.pascloud.vo.result;

import java.io.Serializable;

import com.pascloud.utils.PasCloudCode;

public class ResultCommon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer code;
	
	private String  desc;
	
	public ResultCommon(){}
	
	public ResultCommon(PasCloudCode c){
		this.code = c.getCode();
		this.desc = c.getDesc();
	}
	
	public ResultCommon(Integer code,String  desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	
}
