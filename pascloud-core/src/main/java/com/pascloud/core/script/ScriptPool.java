package com.pascloud.core.script;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.IHandler;
import com.pascloud.utils.FileUtils;

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
	
	String compile() {
		FileUtils.deleteDir(this.outDir);
		List<File> sourceFileList = new ArrayList<>();
		sourceFileList = FileUtils.listFilesInDirWithFilter(this.sourceDir, ".java", true);
		return this.compile(sourceFileList);
	}
	
	String compile(List<File> sourceFileList) {
		
		StringBuilder sb = new StringBuilder();
		if (null != sourceFileList) {
			DiagnosticCollector<JavaFileObject> oDiagnosticCollector = new DiagnosticCollector<>();
			// 获取编译器实例
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			// 获取标准文件管理器实例
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(oDiagnosticCollector, null,
					Charset.forName("utf-8"));
			
			try {
				if (sourceFileList.isEmpty()) {
					// log.warn(this.sourceDir + "目录下查找不到任何java文件");
					return this.sourceDir + "目录下查找不到任何java文件";
				}
				log.warn("找到脚本并且需要编译的文件共：" + sourceFileList.size());
				
				
				FileUtils.createOrExistsDir(this.outDir);
				
				// 获取要编译的编译单元
				Iterable<? extends JavaFileObject> compilationUnits = fileManager
						.getJavaFileObjectsFromFiles(sourceFileList);
				
				/**
				 * 编译选项，在编译java文件时，编译程序会自动的去寻找java文件引用的其他的java源文件或者class。
				 * -sourcepath选项就是定义java源文件的查找目录， -classpath选项就是定义class文件的查找目录。
				 */
				ArrayList<String> options = new ArrayList<>(0);
				options.add("-g");
				options.add("-source");
				options.add("1.8");
				// options.add("-Xlint");
				// options.add("unchecked");
				options.add("-encoding");
				options.add("UTF-8");
				options.add("-sourcepath");
				options.add(this.sourceDir); // 指定文件目录
				options.add("-d");
				options.add(this.outDir); // 指定输出目录
				
				List<File> jarsList = new ArrayList<>();
				jarsList = FileUtils.listFilesInDirWithFilter(this.jarsDir, ".jar", true);
				
				String jarString = "";
				jarString = jarsList.stream().map((jar) -> jar.getPath() + File.pathSeparator).reduce(jarString,
						String::concat);
				
				log.warn("jarString:" + jarString);
				if (!stringIsNullEmpty(jarString)) {
					options.add("-classpath");
					options.add(jarString);// 指定附加的jar包
				}
				
				JavaCompiler.CompilationTask compilationTask = compiler.getTask(null, fileManager, oDiagnosticCollector,
						options, null, compilationUnits);
				// 运行编译任务
				Boolean call = compilationTask.call();
				if (!call) {
					oDiagnosticCollector.getDiagnostics().forEach(f -> {
						sb.append(";").append(((JavaFileObject) (f.getSource())).getName()).append(" line:")
								.append(f.getLineNumber());
						log.warn("加载脚本错误：" + ((JavaFileObject) (f.getSource())).getName() + " line:"
								+ f.getLineNumber());
					});
				}
				
			}catch (Exception ex) {
				sb.append(this.sourceDir).append("错误：").append(ex);
				log.error("加载脚本错误：", ex);
			} finally {
				try {
					fileManager.close();
				} catch (IOException ex) {
					log.error("", ex);
				}
			}
		}else{
			log.warn(this.sourceDir + "目录下查找不到任何java文件");
		}
		
		return sb.toString();
	}
	
	public String loadJava(Consumer<String> condition) {
		String compile = this.compile();
		StringBuilder sb=new StringBuilder();
		if (compile == null || compile.isEmpty()) {
			List<File> sourceFileList = new ArrayList<>(0);
			// 得到编译后的class文件
			sourceFileList = FileUtils.listFilesInDirWithFilter(this.outDir, ".class", true);
			String[] fileNames = new String[sourceFileList.size()]; // 类路径列表
			for (int i = 0; i < sourceFileList.size(); i++) {
				fileNames[i] = sourceFileList.get(i).getPath();
				sb.append(fileNames[i]).append(";");
			}
			tmpScriptInstances = new ConcurrentHashMap<>();
			tmpIdScriptInstances = new ConcurrentHashMap<>();
			//loadClass(fileNames);
			if (tmpScriptInstances.size() > 0) {
				scriptInstances.clear();
				scriptInstances = tmpScriptInstances;
			}
			if (tmpIdScriptInstances.size() > 0) {
				idScriptInstances.clear();
				idScriptInstances = tmpIdScriptInstances;
			}
		} else {
			if (!compile.isEmpty()) {
				if (condition != null) {
					condition.accept(compile);
				}
			}
		}
		return sb.toString();
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
	
	public static void main(String[] args){
		System.out.println("l");
	}
}
