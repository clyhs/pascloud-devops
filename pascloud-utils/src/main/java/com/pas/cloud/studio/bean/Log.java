package com.pas.cloud.studio.bean;

import java.io.Serializable;

public class Log implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4025022130505530923L;
	/**
	 * 请求数据日志类型
	 */
	public static final String LOG_TYPE_REQ = "LOG_TYPE_REQ";
	/**
	 * 异常日志类型
	 */
	public static final String LOG_TYPE_EXCEPTION = "LOG_TYPE_EXCEPTION";
	/**
	 * 执行的SQL日志类型
	 */
	public static final String LOG_TYPE_SQL = "LOG_TYPE_SQL";
	/**
	 * 返回数据日志类型
	 */
	public static final String LOG_TYPE_RESP = "LOG_TYPE_RESP";
	
	private long id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	private String url;
	private String funId;
	private long time;
	private String ip;
	private String hydh;
	private String sessionid;
	private String type;
	private String log;
	
	public String toJsonString(){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("url:'").append(url).append("',");
		sb.append("funId:'").append(funId).append("',");
		sb.append("time:'").append(time).append("',");
		sb.append("ip:'").append(ip).append("',");
		sb.append("hydh:'").append(hydh).append("',");
		sb.append("sessionid:'").append(sessionid).append("',");
		sb.append("type:'").append(type).append("',");
		sb.append("log:'").append(specialFilter(log)).append("'");
		sb.append("}");
		return sb.toString();
	}
	private String specialFilter(String s){
		return s;
	}
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFunId() {
		return funId;
	}
	public void setFunId(String funId) {
		this.funId = funId;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getHydh() {
		return hydh;
	}
	public void setHydh(String hydh) {
		this.hydh = hydh;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	
	
	
}
