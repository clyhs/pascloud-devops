package com.pascloud.vo.mversion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MVersionTreeVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String id;
	
	private String text;
	
	private Boolean isLeaf ;
	
	private String iconCls;
	
	private String url;
	
	private String pid;
	
	private String version;
	
	private String state;
	
	private String level;
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@SuppressWarnings("unused")
	private List<MVersionTreeVo> children = new ArrayList<MVersionTreeVo>();

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<MVersionTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<MVersionTreeVo> children) {
		this.children = children;
	}
	
	

}
