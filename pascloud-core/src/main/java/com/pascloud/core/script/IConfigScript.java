package com.pascloud.core.script;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;

public interface IConfigScript extends IScript{

	/**
	 * 加载配置
	 * @param tableName 指定的配置表 null加载所有
	 * @return
	 */
	default String reloadConfig(List<String> tableName) {
		return "未加载任何配置";
	}
	
	/**
	 * 是否包含加载表
	 * 
	 * @param tables
	 * @param clazz
	 * @return
	 */
	default boolean containTable(List<String> tables, Class<?> clazz) {
		if (tables == null || tables.isEmpty()) {
			return true;
		}
		Entity entity = clazz.getAnnotation(Entity.class);
		if (entity != null && tables.contains(entity.value())) {
			return true;
		}
		return false;
	}
}