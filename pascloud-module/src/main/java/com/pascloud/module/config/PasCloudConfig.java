package com.pascloud.module.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasCloudConfig {
	
	@Value("${project.config}")
	private String PASCLOUD_SPRINGXML_DIR;
	
	@Value("${project.xml}")
	private String PASCLOUD_SPRINGXML_PATH;
	
	@Value("${project.databasedir}")
	private String PASCLOUD_DATABASE_DIR;
	
	@Value("${project.redisdir}")
	private String PASCLOUD_REDIS_DIR;
	
	@Value("${project.pasdevdir}")
	private String PASCLOUD_DEV_DIR;
	
	@Value("${project.mycatconf}")
	private String PASCLOUD_MYCAT_DIR;
	
	@Value("${project.serviceconf}")
	private String PASCLOUD_SERVICE_DIR;
	
	@Value("${project.serverdir}")
	private String PASCLOUD_SERVER_DIR;
	
	@Value("${project.scriptoracledir}")
	private String PASCLOUD_SCRIPT_ORACLE_DIR;
	
	@Value("${project.psconfig}")
	private String PASCLOUD_PSCONFIG;
	
	@Value("${project.tomcat}")
	private String PASCLOUD_TOMCAT;
	
	@Value("${project.mysql}")
	private String PASCLOUD_MYSQL;
	
	@Value("${project.tree}")
	private String PASCLOUD_TREE;
	
	@Value("${project.upload}")
	private String PASCLOUD_UPLOAD;
	
	
	
	public String getPASCLOUD_UPLOAD() {
		return PASCLOUD_UPLOAD;
	}

	public void setPASCLOUD_UPLOAD(String pASCLOUD_UPLOAD) {
		PASCLOUD_UPLOAD = pASCLOUD_UPLOAD;
	}

	public String getPASCLOUD_TREE() {
		return PASCLOUD_TREE;
	}

	public void setPASCLOUD_TREE(String pASCLOUD_TREE) {
		PASCLOUD_TREE = pASCLOUD_TREE;
	}

	public String getPASCLOUD_MYSQL() {
		return PASCLOUD_MYSQL;
	}

	public void setPASCLOUD_MYSQL(String pASCLOUD_MYSQL) {
		PASCLOUD_MYSQL = pASCLOUD_MYSQL;
	}

	public String getPASCLOUD_TOMCAT() {
		return PASCLOUD_TOMCAT;
	}

	public void setPASCLOUD_TOMCAT(String pASCLOUD_TOMCAT) {
		PASCLOUD_TOMCAT = pASCLOUD_TOMCAT;
	}

	public String getPASCLOUD_PSCONFIG() {
		return PASCLOUD_PSCONFIG;
	}

	public void setPASCLOUD_PSCONFIG(String pASCLOUD_PSCONFIG) {
		PASCLOUD_PSCONFIG = pASCLOUD_PSCONFIG;
	}

	public String getPASCLOUD_SCRIPT_ORACLE_DIR() {
		return PASCLOUD_SCRIPT_ORACLE_DIR;
	}

	public void setPASCLOUD_SCRIPT_ORACLE_DIR(String pASCLOUD_SCRIPT_ORACLE_DIR) {
		PASCLOUD_SCRIPT_ORACLE_DIR = pASCLOUD_SCRIPT_ORACLE_DIR;
	}

	public String getPASCLOUD_SERVER_DIR() {
		return PASCLOUD_SERVER_DIR;
	}

	public void setPASCLOUD_SERVER_DIR(String pASCLOUD_SERVER_DIR) {
		PASCLOUD_SERVER_DIR = pASCLOUD_SERVER_DIR;
	}

	public String getPASCLOUD_SERVICE_DIR() {
		return PASCLOUD_SERVICE_DIR;
	}

	public void setPASCLOUD_SERVICE_DIR(String pASCLOUD_SERVICE_DIR) {
		PASCLOUD_SERVICE_DIR = pASCLOUD_SERVICE_DIR;
	}

	public String getPASCLOUD_MYCAT_DIR() {
		return PASCLOUD_MYCAT_DIR;
	}

	public void setPASCLOUD_MYCAT_DIR(String pASCLOUD_MYCAT_DIR) {
		PASCLOUD_MYCAT_DIR = pASCLOUD_MYCAT_DIR;
	}

	public String getPASCLOUD_DEV_DIR() {
		return PASCLOUD_DEV_DIR;
	}

	public void setPASCLOUD_DEV_DIR(String pASCLOUD_DEV_DIR) {
		PASCLOUD_DEV_DIR = pASCLOUD_DEV_DIR;
	}

	public String getPASCLOUD_REDIS_DIR() {
		return PASCLOUD_REDIS_DIR;
	}

	public void setPASCLOUD_REDIS_DIR(String pASCLOUD_REDIS_DIR) {
		PASCLOUD_REDIS_DIR = pASCLOUD_REDIS_DIR;
	}

	public String getPASCLOUD_SPRINGXML_PATH() {
		return PASCLOUD_SPRINGXML_PATH;
	}

	public void setPASCLOUD_SPRINGXML_PATH(String pASCLOUD_SPRINGXML_PATH) {
		PASCLOUD_SPRINGXML_PATH = pASCLOUD_SPRINGXML_PATH;
	}

	public String getPASCLOUD_SPRINGXML_DIR() {
		return PASCLOUD_SPRINGXML_DIR;
	}

	public void setPASCLOUD_SPRINGXML_DIR(String pASCLOUD_SPRINGXML_DIR) {
		PASCLOUD_SPRINGXML_DIR = pASCLOUD_SPRINGXML_DIR;
	}

	public String getPASCLOUD_DATABASE_DIR() {
		return PASCLOUD_DATABASE_DIR;
	}

	public void setPASCLOUD_DATABASE_DIR(String pASCLOUD_DATABASE_DIR) {
		PASCLOUD_DATABASE_DIR = pASCLOUD_DATABASE_DIR;
	}
	
	

	
}
