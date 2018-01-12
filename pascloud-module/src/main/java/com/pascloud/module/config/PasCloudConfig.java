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
