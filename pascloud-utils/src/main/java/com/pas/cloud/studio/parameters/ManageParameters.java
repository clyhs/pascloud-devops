package com.pas.cloud.studio.parameters;

import java.io.Serializable;

import com.pas.cloud.studio.bean.DataSource;
import com.pas.cloud.studio.bean.ResultShowLogics;
import com.pas.cloud.studio.bean.ResultsList;
import com.pas.cloud.studio.bean.ReusltBindDataSource;
import com.pas.cloud.studio.bean.SearchConditions;
import com.pas.cloud.studio.manage.bean.TableInfo;
/***
 * 管理功能设计器页面参数
 * @author joy.shen
 * @version 1.0
 * @since 2012-06-11
 *
 */
public class ManageParameters implements Serializable, Parameter {

	private static final long serialVersionUID = 8767505844327593323L;

	private DataSource[] dataSource;

	private ResultsList[] resultsList;

	private ReusltBindDataSource[] reusltBindDataSources;

	private SearchConditions searchConditions;

	private ResultShowLogics resultShowLogics;

	private String dataTable;
	private TableInfo[] tableInfos;
	private String tableInfosJson;
	private SearchConditions tagInfos;
	private String tagInfosJson;
	private String editScript;
	private String importView;
	private String importOption;
	private String fileName;
	private String dsJson;
	private String rsJson;
	private String rsBinDsJson;
	private String scJson;
	private String rslJson;
	private String saveFunctionName;
	private String fnuctionId;;
	private String manageTargetSetJson;
	
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

	private String scripts;

	public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public String getTableInfosJson() {
		return tableInfosJson;
	}

	public void setTableInfosJson(String tableInfosJson) {
		this.tableInfosJson = tableInfosJson;
	}

	public TableInfo[] getTableInfos() {
		return tableInfos;
	}

	public void setTableInfos(TableInfo[] tableInfos) {
		this.tableInfos = tableInfos;
	}

	public SearchConditions getTagInfos() {
		return tagInfos;
	}

	public void setTagInfos(SearchConditions tagInfos) {
		this.tagInfos = tagInfos;
	}

	public String getTagInfosJson() {
		return tagInfosJson;
	}

	public void setTagInfosJson(String tagInfosJson) {
		this.tagInfosJson = tagInfosJson;
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

	public DataSource[] getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource[] dataSource) {
		this.dataSource = dataSource;
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

	public SearchConditions getSearchConditions() {
		return searchConditions;
	}

	public void setSearchConditions(SearchConditions searchConditions) {
		this.searchConditions = searchConditions;
	}

	public ResultShowLogics getResultShowLogics() {
		return resultShowLogics;
	}

	public void setResultShowLogics(ResultShowLogics resultShowLogics) {
		this.resultShowLogics = resultShowLogics;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDsJson() {
		return dsJson;
	}

	public void setDsJson(String dsJson) {
		this.dsJson = dsJson;
	}

	public String getRsJson() {
		return rsJson;
	}

	public void setRsJson(String rsJson) {
		this.rsJson = rsJson;
	}

	public String getRsBinDsJson() {
		return rsBinDsJson;
	}

	public void setRsBinDsJson(String rsBinDsJson) {
		this.rsBinDsJson = rsBinDsJson;
	}

	public String getScJson() {
		return scJson;
	}

	public void setScJson(String scJson) {
		this.scJson = scJson;
	}

	public String getRslJson() {
		return rslJson;
	}

	public void setRslJson(String rslJson) {
		this.rslJson = rslJson;
	}

	public String getScripts() {
		return scripts;
	}

	public void setScripts(String scripts) {
		this.scripts = scripts;
	}
	
	

	public String getEditScript() {
		return editScript;
	}

	public void setEditScript(String editScript) {
		this.editScript = editScript;
	}
	

	public String getImportView() {
		return importView;
	}

	public void setImportView(String importView) {
		this.importView = importView;
	}

	public String getImportOption() {
		return importOption;
	}

	public void setImportOption(String importOption) {
		this.importOption = importOption;
	}

	public String toJsonString() {
		StringBuffer sb = new StringBuffer("[{");
		sb.append("'dataTable':'" + dataTable).append("',");
		sb.append("'tagInfosJson':'" + tagInfosJson).append("',");
		sb.append("'tableInfosJson':'" + tableInfosJson).append("',");
		sb.append("'editScript':'" + editScript).append("',");
		sb.append("'importOption':'" + importOption).append("',");
		sb.append("'importView':'" + importView).append("',");
		
		sb.append("'dsJson':'" + dsJson).append("',");
		sb.append("'rsJson':'" + rsJson).append("',");
		sb.append("'rsBinDsJson':'" + rsBinDsJson).append("',");
		sb.append("'scJson':'" + scJson).append("',");
		sb.append("'saveFunctionName':'" + saveFunctionName).append("',");
		sb.append("'fileName':'" + fileName).append("',");
		sb.append("'fnuctionId':'" + fileName).append("',");
		sb.append("'version':'" + version).append("',");
		sb.append("'desc':'" + desc).append("',");
		sb.append("'pid':'" + pid).append("',");
		sb.append("'scripts':'" + scripts).append("',");
		sb.append("'rslJson':'" + rslJson).append("',");
		sb.append("'manageTargetSetJson':"+manageTargetSetJson).append("}]");
		return sb.toString();
	}



	public String getFunId() {
		// TODO Auto-generated method stub
		return this.fileName;// 历史遗留问题
	}

	public String getFunName() {
		// TODO Auto-generated method stub
		return this.saveFunctionName;// 历史遗留问题
	}

	public String getFunType() {
		// TODO Auto-generated method stub
		return "manage";
	}

	public String getManageTargetSetJson() {
		return manageTargetSetJson;
	}

	public void setManageTargetSetJson(String manageTargetSetJson) {
		this.manageTargetSetJson = manageTargetSetJson;
	}
}
