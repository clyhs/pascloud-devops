package com.pascloud.vo.result;

import com.pascloud.utils.PasCloudCode;

public class ResultBean<T> extends ResultCommon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private T bean;

	public ResultBean() {
	}

	public T getBean() {
		return bean;
	}

	public void setBean(T bean) {
		this.bean = bean;
	}

	public ResultBean(PasCloudCode c) {
		super(c);
	}

	public ResultBean(Integer code, String desc) {
		super(code, desc);
	}

}
