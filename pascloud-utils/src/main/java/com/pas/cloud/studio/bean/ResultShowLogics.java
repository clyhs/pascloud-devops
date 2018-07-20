package com.pas.cloud.studio.bean;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;

public class ResultShowLogics implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5550987501396823451L;
	private List<ResultShowLogic> resultShowLogics;
	public static void main(String[] args) {
		Gson gson = new Gson();
		
		String gsons = "{'resultShowLogics':[{'name':'selects','value':'1','rsName':'新结果集1'},{'name':'selects','value':'2','rsName':'新结果集2'}]}";
		
		ResultShowLogics rs = gson.fromJson(gsons, ResultShowLogics.class);
		
		//System.out.println(rs.getResultShowLogics().get(0).getRsName());
	}
	public List<ResultShowLogic> getResultShowLogics() {
		return resultShowLogics;
	}
	public void setResultShowLogics(List<ResultShowLogic> resultShowLogics) {
		this.resultShowLogics = resultShowLogics;
	}
}
