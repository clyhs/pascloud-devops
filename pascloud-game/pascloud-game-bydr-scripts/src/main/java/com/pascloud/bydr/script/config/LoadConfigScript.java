package com.pascloud.bydr.script.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bydr.manager.ConfigManager;
import com.pascloud.core.script.IConfigScript;
import com.pascloud.game.model.mongo.bydr.dao.CFishDao;
import com.pascloud.game.model.mongo.bydr.entity.CFish;

/**
 * 加载配置脚本
 * 
 */
public class LoadConfigScript implements IConfigScript {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoadConfigScript.class);

	@Override
	public String reloadConfig(List<String> tables) {
		StringBuffer sb = new StringBuffer();
		synchronized (this) {
			try {
				// 鱼配置
				if (containTable(tables, CFish.class)) {
					Map<Integer, CFish> fishMap = new ConcurrentHashMap<>();
					CFishDao.getAll().forEach(fish -> {
						fishMap.put(fish.getId(), fish);
					});
					ConfigManager.getInstance().setFishMap(fishMap);
					sb.append("CFish:").append(fishMap.size());
				}

				// TODO 其他配置

			} catch (Exception e) {
				LOGGER.error("加载配置", e);
			}

		}
		LOGGER.info(sb.toString());
		return sb.toString();
	}
}
