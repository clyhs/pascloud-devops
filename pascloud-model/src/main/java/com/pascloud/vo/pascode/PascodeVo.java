package com.pascloud.vo.pascode;

import java.io.Serializable;
import java.util.Date;

public class PascodeVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String  name;
	
	private Integer type;
	
	private Date    createTime;
	
	private long   size;
	
	
	

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
