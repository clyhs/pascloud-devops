package com.pascloud.core.script;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.IHandler;

public final class ScriptPool {

	private static final Logger log = LoggerFactory.getLogger(ScriptPool.class);
	// 源文件夹
	private String sourceDir;
	// 输出文件夹
	private String outDir;
	// 附加的jar包地址
	private String jarsDir;

	// 脚本容器
	Map<String, Map<String, IScript>> scriptInstances = new ConcurrentHashMap<>(0);
	Map<String, Map<String, IScript>> tmpScriptInstances = new ConcurrentHashMap<>(0);
	Map<Integer, IIdScript> idScriptInstances = new ConcurrentHashMap<>(0);
	Map<Integer, IIdScript> tmpIdScriptInstances = new ConcurrentHashMap<>(0);
	// tcp handler容器
	Map<Integer, Class<? extends IHandler>> tcpHandlerMap = new ConcurrentHashMap<>(0);
	Map<Integer, HandlerEntity> tcpHandlerEntityMap = new ConcurrentHashMap<>(0);

	// http handler容器
	Map<String, Class<? extends IHandler>> httpHandlerMap = new ConcurrentHashMap<>(0);
	Map<String, HandlerEntity> httpHandlerEntityMap = new ConcurrentHashMap<>(0);

	public ScriptPool() {
	}

	public void setSource(String source, String out, String jarsDir) throws Exception {
		if (stringIsNullEmpty(source)) {
			log.error("指定 输入 输出 目录为空");
			throw new Exception("目录为空");
		}
		this.sourceDir = source;
		this.outDir = out;
		this.jarsDir = jarsDir;
		log.info("脚本指定 输入 {} 输出 {} jars目录 {}", source, out, jarsDir);
		//log.info("", source, out, jarsDir);
	}
	
	final boolean stringIsNullEmpty(String str) {
		return str == null || str.length() <= 0 || "".equals(str.trim());
	}
}
