package com.pas.cloud.studio.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class SearchConditions implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5370062716273647142L;
	private List<HashMap> searchConditions;
	public List<HashMap> getSearchConditions() {
		return searchConditions;
	}
	public void setSearchConditions(List<HashMap> searchConditions) {
		this.searchConditions = searchConditions;
	}
	public static void main(String[] args) {
		//String gs = "{'searchConditions':[{'id':1328627946202,'type':'date','label':'统计日期','name':'tjrq','isNull':true,'value':'','dmmc':'','values':[],'format':'ymd','defval':'sys','isAll':true,'isQx':true,'size':10,'zz':'','zzs':'','dymsql':''},{'id':1328627963187,'type':'jg','label':'开户机构','name':'jgkhdxdh','isNull':true,'value':'','dmmc':'','values':[],'format':'ymd','defval':'sys','isAll':true,'isQx':true,'size':10,'zz':'','zzs':'','dymsql':''}]}";
		/*
		String gs="{'searchConditions':[{'id':1328629002772,'type':'select','label':'开户','name':'selects','isNull':true,'value':'','dmmc':'','values':[],'format':'ymd','defval':'sys','isAll':true,'isQx':true,'size':10,'zz':'','zzs':'','dymsql':''},{'id':1328629004062,'type':'date','label':'统计日期','name':'tjrq','isNull':true,'value':'','dmmc':'','values':[],'format':'ymd','defval':'sys','isAll':true,'isQx':true,'size':10,'zz':'','zzs':'','dymsql':''}]}";
		Gson gson = new Gson();  
		SearchConditions obj = gson.fromJson(gs,SearchConditions.class); 
		System.out.println(obj.getSearchConditions().get(0).getLabel());
		*/
		/*
		Gson gson = new Gson();  
		String ngs = "{'hms':[{'dmmc':'1','onclick':'2','name':'3','value':'4','size':'5','onchange':'6','style':'7','styleClass':'8','isAll':'true','multiple':'true'}]}";
		Nsc nsc = gson.fromJson(ngs, Nsc.class);
		System.out.println(nsc.getHms().get(0).get("onchange"));
		*/
	}
	
	class Nsc{
		private List<HashMap> hms;

		public List<HashMap> getHms() {
			return hms;
		}

		public void setHms(List<HashMap> hms) {
			this.hms = hms;
		}
	}
}
