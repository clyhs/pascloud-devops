package com.pascloud.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import freemarker.template.Configuration;
import freemarker.template.Template;

public class JrdsUtils {
	
	private static Logger log = LoggerFactory.getLogger(JrdsUtils.class);

	private volatile static JrdsUtils jrdsUtils = null;

	private JrdsUtils() {};

	public static JrdsUtils getInstance() {
		if (jrdsUtils == null) {
			synchronized (JrdsUtils.class) {
				if (jrdsUtils == null) {
					jrdsUtils = new JrdsUtils();
				}
			}
		}
		return jrdsUtils;
	}

	public boolean newJrdsFile(String templateFile, String jrdsconf, Map<String, Object> paramData) {
		InputStream inputstate = null;
		Writer out = null;
		try {
			String packageName = this.getClass().getPackage().getName();
			String packagePath = packageName.replace('.', '/');
			ClassLoader classLoader = this.getClass().getClassLoader();
			
			log.info(packagePath + templateFile);
			inputstate = classLoader.getResourceAsStream(packagePath + templateFile);
			//inputstate = classLoader.getResourceAsStream(templateFile);
			Template tempState = new Template("", new InputStreamReader(inputstate), new Configuration());
			tempState.setEncoding("UTF-8");

			File file = new File(jrdsconf);
			if (!file.exists()) {
				file.getParentFile().mkdir();
				file.createNewFile();
			}
			out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			tempState.process(paramData, out);
			out.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (inputstate != null) {
					inputstate.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
			}
		}
	}

}
