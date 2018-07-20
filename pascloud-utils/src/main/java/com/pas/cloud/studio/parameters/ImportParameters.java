package com.pas.cloud.studio.parameters;

import java.io.Serializable;

import com.pas.cloud.studio.bean.ReusltBindDataSource;

public class ImportParameters implements Serializable,Parameter{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7302712038059549758L;

	private String funId;//功能id号
	
	private String funName;//功能名称
	
	private String templateJson;//前端模板设置收集到的参数
	
	private String templateRuleJson;//模板规则
	private String targetSetJson;//模板表
	
	private String scJson;//查询条件
	private String selectJson;//查询sql
	
	private String version;
	
	private String desc;
	
	private String pid;
	
	
	
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSelectJson() {
		return selectJson;
	}

	public void setSelectJson(String selectJson) {
		this.selectJson = selectJson;
	}

	public String getScJson() {
		return scJson;
	}

	public void setScJson(String scJson) {
		this.scJson = scJson;
	}

	public String getTargetSetJson() {
		return targetSetJson;
	}

	public void setTargetSetJson(String targetSetJson) {
		//由于DECIMAL(31,0) 在oracle下的精度是1-38  在db2下的精度是1-31，所以需要将所有的改成31
		this.targetSetJson = targetSetJson.replaceAll("38", "31");
	}

	public String getTemplateRuleJson() {
		return templateRuleJson;
	}

	public void setTemplateRuleJson(String templateRuleJson) {
		this.templateRuleJson = templateRuleJson;
	}

	public String toJsonString(){
		StringBuffer sb = new StringBuffer("[{");
		/*
		sb.append("'dsJson':'"+dsJson).append("',");
		sb.append("'rsJson':'"+rsJson).append("',");
		sb.append("'rsBinDsJson':'"+rsBinDsJson).append("',");
		sb.append("'scJson':'"+scJson).append("',");
		sb.append("'saveFunctionName':'"+saveFunctionName).append("',");
		sb.append("'fileName':'"+fileName).append("',");
		sb.append("'fnuctionId':'"+fileName).append("',");
		sb.append("'scripts':'"+scripts).append("',");
		sb.append("'rslJson':'"+rslJson).append("'}]");
		*/
		sb.append("'funId':'"+funId).append("',");
		sb.append("'funName':'"+funName).append("',");
		sb.append("'version':'"+version).append("',");
		sb.append("'desc':'"+desc).append("',");
		sb.append("'pid':'"+pid).append("',");
		sb.append("'templateJson':"+templateJson).append(",");
		sb.append("'templateRuleJson':"+templateRuleJson).append(",");
		sb.append("'scJson':"+scJson).append(",");
		sb.append("'selectJson':"+selectJson).append(",");
		sb.append("'targetSetJson':"+targetSetJson).append("}]");
		return sb.toString();
	}

	public String getFunId() {
		// TODO Auto-generated method stub
		return this.funId;
	}

	public String getFunName() {
		// TODO Auto-generated method stub
		return this.funName;
	}

	public String getFunType() {
		// TODO Auto-generated method stub
		return "import";
	}

	public String getTemplateJson() {
		return templateJson;
	}

	public void setTemplateJson(String templateJson) {
		this.templateJson = templateJson;
	}

	public void setFunId(String funId) {
		this.funId = funId;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}
	

}
