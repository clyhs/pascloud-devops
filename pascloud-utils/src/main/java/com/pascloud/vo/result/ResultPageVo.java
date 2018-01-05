package com.pascloud.vo.result;

import java.util.ArrayList;
import java.util.List;

public class ResultPageVo<T> extends ResultCommon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private int total = -1;
    
    //private List<String> headers = new ArrayList<String>();
    
    private List<T> rows = new ArrayList<>();
    
    public ResultPageVo(){
    	super();
    }
    
    public ResultPageVo(Integer code,String desc) {
    	super(code,desc);
    }

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	/*
	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}*/
    
    
}
