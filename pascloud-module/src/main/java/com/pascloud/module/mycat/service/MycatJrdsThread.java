package com.pascloud.module.mycat.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.constant.Constants;
import com.pascloud.module.config.PasCloudConfig;
import com.pascloud.utils.FileUtils;
import com.pascloud.utils.JrdsUtils;

public class MycatJrdsThread extends Thread {
	
	private static Logger log = LoggerFactory.getLogger(MycatJrdsThread.class);
	
	private PasCloudConfig m_config;

	private String ip;

	private Boolean isRunning = false;

	public MycatJrdsThread(String mycatIp,PasCloudConfig config) {
		this.ip = mycatIp;
		this.m_config = config;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!isRunning) {
			Boolean flag = false;
			flag = createMycatJrds();
			if (flag) {
				log.info("创建jrds文件成功");
			}
			isRunning = true;
		}
	}

	private Boolean createMycatJrds() {

		Boolean flag = false;
		try {
			
			Map<String, Object> jdbc = new HashMap<>();
			jdbc.put("mangerPort", "9066");
			jdbc.put("ip", ip);
			jdbc.put("username", "root");
			jdbc.put("password", "root");
			jdbc.put("dbName", "alldb");

			String jrdsconfg = System.getProperty(Constants.WEB_APP_ROOT_DEFAULT) + "/WEB-INF/jrdsconf/hosts/";

			List<File> files = FileUtils.listFilesInDirWithFilter(jrdsconfg, ".xml", false);
			if (null != files && files.size() > 0) {
				for (File f : files) {
					log.info("删除文件：" + f.getAbsolutePath());
					FileUtils.deleteFile(f.getAbsolutePath());
				}
			}
			jrdsconfg = jrdsconfg + "D_" + ip + "_" + jdbc.get("mangerPort") + ".xml";
			log.info("createMycatJrds:" + jrdsconfg);
			String templatepath = "/template/mycatjrds.ftl";
			flag = JrdsUtils.getInstance().newJrdsFile(templatepath, jrdsconfg, jdbc);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}
}