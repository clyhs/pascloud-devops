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

}
