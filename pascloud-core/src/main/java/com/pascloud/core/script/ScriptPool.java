package com.pascloud.core.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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
	
	@SuppressWarnings("unchecked")
	public <T extends IIdScript> T getIIdScript(Integer modelID) {
		IIdScript iis = null;
		if (idScriptInstances.containsKey(modelID)) {
			iis = idScriptInstances.get(modelID);
		}
		return (T) iis;
	}
	
	/**
	 * 脚本列表
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> Collection<E> getEvts(String name) {
		Map<String, IScript> scripts = ScriptPool.this.scriptInstances.get(name);
		if (scripts != null) {
			return (Collection<E>) scripts.values();
		}
		return new ArrayList<>();
	}
	
	/**
	 * 脚本列表
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> Collection<E> getEvts(Class<E> clazz) {
		Map<String, IScript> scripts = ScriptPool.this.scriptInstances.get(clazz.getName());
		if (scripts != null) {
			return (Collection<E>) scripts.values();
		}
		return new ArrayList<>();
	}
	
	/**
	 * 执行脚本
	 * @param scriptClass
	 * @param action
	 */
	@SuppressWarnings("unchecked")
	public <T extends IScript> void executeScripts(Class<T> scriptClass, Consumer<T> action) {
		Collection<IScript> evts = getEvts(scriptClass.getName());
		if (evts != null && !evts.isEmpty() && action != null) {
			evts.forEach(scrpit -> {
				try {
					action.accept((T) scrpit);
				} catch (Exception e) {
					log.error("执行 IScript:" + scriptClass.getName(), e);
				}
			});
		}
	}
	
	
	/**
	 * 执行脚本，当执行结果为true时，中断执行，并返回true。否则统一返回执行false		
	 * @param scriptClass
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends IScript> boolean predicateScripts(Class<? extends IScript> scriptClass, Predicate<T> condition) {
		Collection<IScript> evts = getEvts(scriptClass.getName());
		if (evts != null && !evts.isEmpty() && condition != null) {
			Iterator<IScript> iterator = evts.iterator();
			while (iterator.hasNext()) {
				try {
					if (condition.test((T) iterator.next())) {
						return true;
					}
				} catch (Exception e) {
					log.error("predicateScripts IScript:" + scriptClass.getName(), e);
				}
			}
		}
		return false;
	}
	
	/**
	 * 执行脚本，并返回一个值
	 * @param scriptClass
	 * @param function
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends IScript, R> R functionScripts(Class<? extends IScript> scriptClass, Function<T, R> function) {
		Collection<IScript> evts = getEvts(scriptClass.getName());
		if (evts != null && !evts.isEmpty() && function != null) {
			Iterator<IScript> iterator = evts.iterator();
			while (iterator.hasNext()) {
				try {
					R r = function.apply((T) iterator.next());
					if (r != null) {
						return r;
					}
				} catch (Exception e) {
					log.error("functionScripts IBaseScript:" + scriptClass.getName(), e);
				}
			}
		}
		return null;
	}
	
	
	final boolean stringIsNullEmpty(String str) {
		return str == null || str.length() <= 0 || "".equals(str.trim());
	}

	public Map<String, Map<String, IScript>> getTmpScriptInstances() {
		return tmpScriptInstances;
	}

	public Map<Integer, IIdScript> getTmpIdScriptInstances() {
		return tmpIdScriptInstances;
	}

	public Map<Integer, Class<? extends IHandler>> getTcpHandlerMap() {
		return tcpHandlerMap;
	}

	public Map<Integer, HandlerEntity> getTcpHandlerEntityMap() {
		return tcpHandlerEntityMap;
	}

	public Map<String, Class<? extends IHandler>> getHttpHandlerMap() {
		return httpHandlerMap;
	}

	public Map<String, HandlerEntity> getHttpHandlerEntityMap() {
		return httpHandlerEntityMap;
	}
	
	
}
