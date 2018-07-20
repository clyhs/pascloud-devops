package com.pas.cloud.studio.parameters;

import java.io.Serializable;

import com.pas.cloud.studio.bean.DataSource;
import com.pas.cloud.studio.bean.ResultShowLogics;
import com.pas.cloud.studio.bean.ResultsList;
import com.pas.cloud.studio.bean.ReusltBindDataSource;
import com.pas.cloud.studio.bean.SearchConditions;

/**
 * 查询类功能的参数
 * @author luoxt
 *
 */
public class Parameters implements Serializable,Parameter{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7302712038059549758L;
	
	private DataSource[] dataSource;
	
	private ResultsList[] resultsList;
	
	private ReusltBindDataSource[] reusltBindDataSources;
	
	private SearchConditions searchConditions;
	
	private ResultShowLogics resultShowLogics;
	
	private String fileName;
	private String dsJson;
	private String rsJson;
	private String rsBinDsJson;
	private String scJson;
	private String rslJson;
	private String saveFunctionName;
	private String fnuctionId;
	
	private String scripts;
	private String plotJson;
	
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

	public String getFnuctionId() {
		return fnuctionId;
	}
	
	public void setFnuctionId(String fnuctionId) {
		this.fnuctionId = fnuctionId;
	}


	public String getSaveFunctionName() {
		return saveFunctionName;
	}


	public void setSaveFunctionName(String saveFunctionName) {
		this.saveFunctionName = saveFunctionName;
	}


	public String toJsonString(){
		StringBuffer sb = new StringBuffer("[{");
		sb.append("'dsJson':'"+dsJson).append("',");
		sb.append("'rsJson':'"+rsJson).append("',");
		sb.append("'rsBinDsJson':'"+rsBinDsJson).append("',");
		sb.append("'scJson':'"+scJson).append("',");
		sb.append("'saveFunctionName':'"+saveFunctionName).append("',");
		sb.append("'version':'"+version).append("',");
		sb.append("'desc':'"+desc).append("',");
		sb.append("'pid':'"+pid).append("',");
		sb.append("'fileName':'"+fileName).append("',");
		sb.append("'fnuctionId':'"+fileName).append("',");
		sb.append("'scripts':'"+scripts).append("',");
		sb.append("'plotJson':'"+plotJson).append("',");
		sb.append("'rslJson':'"+rslJson).append("'}]");
		return sb.toString();
	}
	
	
	public DataSource[] getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource[] dataSource) {
		this.dataSource = dataSource;
	}
	public String getDsJson() {
		return dsJson;
	}
	public void setDsJson(String dsJson) {
		this.dsJson = dsJson;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public ResultShowLogics getResultShowLogics() {
		return resultShowLogics;
	}
	public void setResultShowLogics(ResultShowLogics resultShowLogics) {
		this.resultShowLogics = resultShowLogics;
	}
	public ResultsList[] getResultsList() {
		return resultsList;
	}
	public void setResultsList(ResultsList[] resultsList) {
		this.resultsList = resultsList;
	}
	public ReusltBindDataSource[] getReusltBindDataSources() {
		return reusltBindDataSources;
	}
	public void setReusltBindDataSources(
			ReusltBindDataSource[] reusltBindDataSources) {
		this.reusltBindDataSources = reusltBindDataSources;
	}
	public String getRsBinDsJson() {
		return rsBinDsJson;
	}
	public void setRsBinDsJson(String rsBinDsJson) {
		this.rsBinDsJson = rsBinDsJson;
	}
	public String getRsJson() {
		return rsJson;
	}
	public void setRsJson(String rsJson) {
		this.rsJson = rsJson;
	}
	public String getRslJson() {
		return rslJson;
	}
	public void setRslJson(String rslJson) {
		this.rslJson = rslJson;
	}
	public String getScJson() {
		return scJson;
	}
	public void setScJson(String scJson) {
		this.scJson = scJson;
	}
	public SearchConditions getSearchConditions() {
		return searchConditions;
	}
	public void setSearchConditions(SearchConditions searchConditions) {
		this.searchConditions = searchConditions;
	}


	public String getScripts() {
		return scripts;
	}


	public void setScripts(String scripts) {
		this.scripts = scripts;
	}


	public String getFunId() {
		// TODO Auto-generated method stub
		return this.fileName;//历史遗留问题
	}


	public String getFunName() {
		// TODO Auto-generated method stub
		return this.saveFunctionName;//历史遗留问题
	}


	public String getFunType() {
		// TODO Auto-generated method stub
		return "query";
	}


	public String getPlotJson() {
		return plotJson;
	}

	public void setPlotJson(String plotJson) {
		this.plotJson = plotJson;
	}
	
}
