package com.pas.cloud.studio.bean;

import java.io.Serializable;

public class TargetCls implements Serializable{

	/**
	 * 目标表和临时表对应关系的列的属性
	 */
	private static final long serialVersionUID = 1L;
	
	//cname:cols[i].etn,key:false,del:false,tname:tr.find('.template').val(),type:'excel',sys:'',lt:'',lc:'',lv:''
	/**
	 * 目标表字段名(大写)
	 */
	private String cname;
	/**
	 * 是否主键(true/false)
	 */
	private String key;
	/**
	 * 是否自定义删除(true/false)
	 */
	private String del;
	/**
	 * 取值方式： excel（Excel取值） inner（内置参数） other（关联其他表--单表）
	 */
	private String type;
	/**
	 * 内置参数值
	 */
	private String sys;
	/**
	 * 关联表表名
	 */
	private String lt;
	/**
	 * 关联取值
	 */
	private String lc;
	/**
	 * 关联条件
	 */
	private String lv;
	/**
	 * 关联条件字段的数据类型
	 */
	private String selectSql;//多表取数sql
	
	private String lvCast;//用于截断时转型
	
	private boolean hasLvCast = true;
	
	/**
	 * 目标表字段数据类型
	 */
	private String cast;//强制转型 如：Integer 、DECIMAL(19,2)
	
	private boolean hasCast = true;
	
	public String getSelectSql() {
		return selectSql;
	}
	public void setSelectSql(String selectSql) {
		this.selectSql = selectSql;
	}
	/**
	 * 关联表的别名
	 */
	private String alias;
	/**
	 * 模板列ID号
	 */
	private String tname;
	/**
	 * 模板列id号
	 * @return
	 */
	
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	/**
	 * 目标表列名
	 * @return
	 */
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLc() {
		return lc;
	}
	public void setLc(String lc) {
		this.lc = lc;
	}
	public String getLt() {
		return lt;
	}
	public void setLt(String lt) {
		this.lt = lt;
	}
	public String getLv() {
		return lv;
	}
	public void setLv(String lv) {
		this.lv = lv;
	}
	public String getSys() {
		return sys;
	}
	public void setSys(String sys) {
		this.sys = sys;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCast() {
		if(cast.trim().startsWith("CHAR")||cast.trim().startsWith("VARCHAR")||cast.trim().startsWith("DECIMAL")){
			return cast;
		}
		String c = cast.indexOf("(")!=-1?cast.substring(0, cast.indexOf("(")):cast;
		return c;
	}
	public void setCast(String cast) {
		this.cast = cast;
	}
	public boolean isHasCast() {
		if(cast.trim().startsWith("VARCHAR")){//varchar类型不用转型
			return false;
		}
		return hasCast;
	}
	public boolean isHasLvCast() {
		if(lvCast.trim().startsWith("VARCHAR")){//varchar类型不用转型
			return false;
		}
		return hasLvCast;
	}
	public void setHasLvCast(boolean hasLvCast) {
		this.hasLvCast = hasLvCast;
	}
	public String getLvCast() {
		if(lvCast.trim().startsWith("CHAR")||lvCast.trim().startsWith("VARCHAR")||lvCast.trim().startsWith("DECIMAL")){
			return lvCast;
		}
		String c = lvCast.indexOf("(")!=-1?lvCast.substring(0, lvCast.indexOf("(")):lvCast;
		return c;
	}
	public void setLvCast(String lvCast) {
		this.lvCast = lvCast;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	
}
