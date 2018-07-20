package com.pas.cloud.studio.bean;

import java.io.Serializable;
import java.util.List;

public class Ztree  implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -6510058883947386942L;
	//{"id":"JSMC","pId":1,"name":"角色名称","label":"角色名称","align":"center","type":"string","width":110,"frozen":"false","parent":"","format":"",childs[]
	private String id;
	private String pId;
	private String name;
	private String label;
	private String align;
	private String type;
	private String width;
	private String frozen;
	private String parent;
	private String format;
	private String mergeCells;
	private String footerCells;
	
	private String xsType;
	private String onclick;
	private String showDymCol; // 是否显示动态列
	
	private String refcolumn; //动态表头排序参考列
	
	private String orderby; //动态表头group by后，排序
	
	private String isChecked;
	
	private String isExp;//是否导出
	
	private String isZsy;//是否自适应
	
	private List<Ztree> children;
	
	public String getRefcolumn() {
		return refcolumn;
	}
	public void setRefcolumn(String refcolumn) {
		this.refcolumn = refcolumn;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public List<Ztree> getChildren() {
		return children;
	}
	public void setChildren(List<Ztree> children) {
		this.children = children;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getFrozen() {
		return frozen;
	}
	public void setFrozen(String frozen) {
		this.frozen = frozen;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getPId() {
		return pId;
	}
	public void setPId(String id) {
		pId = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public String getXsType() {
		return xsType;
	}
	public void setXsType(String xsType) {
		this.xsType = xsType;
	}
	public String getMergeCells() {
		return mergeCells;
	}
	public void setMergeCells(String mergeCells) {
		this.mergeCells = mergeCells;
	}
	public String getFooterCells() {
		return footerCells;
	}
	public void setFooterCells(String footerCells) {
		this.footerCells = footerCells;
	}
	public String getShowDymCol() {
		return showDymCol;
	}
	public void setShowDymCol(String showDymCol) {
		this.showDymCol = showDymCol;
	}
	public String getIsExp() {
		return isExp;
	}
	public void setIsExp(String isExp) {
		this.isExp = isExp;
	}
	public String getIsZsy() {
		return isZsy;
	}
	public void setIsZsy(String isZsy) {
		this.isZsy = isZsy;
	}
	
	
}
