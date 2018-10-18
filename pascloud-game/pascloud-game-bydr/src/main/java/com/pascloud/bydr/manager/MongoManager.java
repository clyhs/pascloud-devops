package com.pascloud.bydr.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.mongo.AbsMongoManager;
import com.pascloud.game.model.mongo.bydr.dao.CFishDao;

public class MongoManager extends AbsMongoManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoManager.class);
	private static final MongoManager INSTANCE_MANAGER = new MongoManager();

	public static final MongoManager getInstance() {
		return INSTANCE_MANAGER;
	}

	@Override
	protected void initDao() {
		CFishDao.getDB(INSTANCE_MANAGER);

	}

}