package com.pascloud.vo.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeVo implements Serializable{
	
	private static final long serialVersionUID = 6820646617788770682L;

	private String id;
	
	private String text;
	
	private boolean isLeaf ;
	
	private List<TreeVo> children = new ArrayList<TreeVo>();
	
	private String iconCls;
	
	private String url;
	
	private String pid;
	
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}


	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public List<TreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<TreeVo> children) {
		this.children = children;
	}


	
	
	
	

}
