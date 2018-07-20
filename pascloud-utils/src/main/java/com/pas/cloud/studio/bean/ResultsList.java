package com.pas.cloud.studio.bean;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;

public class ResultsList implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1455279061303804664L;
	//{"name":"新结果集1","rss":[{"check":true,"name":"JSDH","cName":"JSDH","format":"default","width":"100","align":"center"},{"check":true,"name":"JSMC","cName":"JSMC","format":"default","width":"100","align":"center"},{"check":true,"name":"FHDH","cName":"FHDH","format":"default","width":"100","align":"center"}]}
	private String name;
	private String page;
	private String multiple;
	private String dym;
	private String needplot;
	private List<ResultLists> rss;
	private List<Ztree> ztree;
	
	public static void main(String[] args) {
		Gson gson = new Gson();
		
		String gsons = "[{'name':'新结果集1','rss':[{'check':true,'name':'JSDH','cName':'JSDH','format':'default','width':'100','align':'center'},{'check':true,'name':'JSMC','cName':'JSMC','format':'default','width':'100','align':'center'},{'check':true,'name':'FHDH','cName':'FHDH','format':'default','width':'100','align':'center'}]},{'name':'新结果集1','rss':[{'check':true,'name':'JSDH','cName':'JSDH','format':'default','width':'100','align':'center'},{'check':true,'name':'JSMC','cName':'JSMC','format':'default','width':'100','align':'center'},{'check':true,'name':'FHDH','cName':'FHDH','format':'default','width':'100','align':'center'}]}]";
		
		ResultsList[] rs = gson.fromJson(gsons, ResultsList[].class);
		
		System.out.println(rs[0].getRss().get(0).getCheck());
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ResultLists> getRss() {
		return rss;
	}
	public void setRss(List<ResultLists> rss) {
		this.rss = rss;
	}

	public String getDym() {
		return dym;
	}

	public void setDym(String dym) {
		this.dym = dym;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public List<Ztree> getZtree() {
		return ztree;
	}

	public void setZtree(List<Ztree> ztree) {
		this.ztree = ztree;
	}

	public String getNeedplot() {
		return needplot;
	}

	public void setNeedplot(String needplot) {
		this.needplot = needplot;
	}

}
