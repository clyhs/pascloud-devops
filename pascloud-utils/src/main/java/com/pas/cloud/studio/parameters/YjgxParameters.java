package com.pas.cloud.studio.parameters;

import java.io.Serializable;

public class YjgxParameters implements Serializable, Parameter {

	private static final long serialVersionUID = -7322099117009320400L;
	
	private String funId;
	private String funName;
	private String pageJson;
	
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

	public String getPageJson() {
		return pageJson;
	}

	public void setPageJson(String pageJson) {
		this.pageJson = pageJson;
	}

	public void setFunId(String funId) {
		this.funId = funId;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

	public String getFunId() {
		return this.funId;
	}

	public String getFunName() {
		return this.funName;
	}

	public String getFunType() {
		return "yjgx";
	}

	public String toJsonString() {
		StringBuffer sb = new StringBuffer("[{");
		sb.append("'funId':'"+funId).append("',");
		sb.append("'funName':'"+funName).append("',");
		sb.append("'version':'"+version).append("',");
		sb.append("'desc':'"+desc).append("',");
		sb.append("'pid':'"+pid).append("',");
		sb.append("'pageJson':"+pageJson).append("}]");
		return sb.toString();
	}

}
