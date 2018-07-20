package com.pas.cloud.studio.bean;

/**
 * 
 * @author luoxt
 *
 */
public class Rule {
	
	private String id;//sqlid zk
	private String text;//放置模板中文名 zk
	
	private String name;//放的是 模板的id
	private String rname;//规则的名称、如date 、number等
	private String ruleType;//java、business，已经废弃
	private String custom;//该规则的参数,除了number的参数是使用逗号（,）分隔，其它的，都是一个参数
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCustom() {
		return custom;
	}
	public void setCustom(String custom) {
		this.custom = custom;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
