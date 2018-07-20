package com.pas.cloud.studio.bean;

import java.io.Serializable;

public class ReusltBindDataSource implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -8057245840560193714L;
	//	{rsName,dsName} 结果集名称、数据源名称
	private String rsName;
	private String dsName;
	public String getDsName() {
		return dsName;
	}
	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	public String getRsName() {
		return rsName;
	}
	public void setRsName(String rsName) {
		this.rsName = rsName;
	}
}
