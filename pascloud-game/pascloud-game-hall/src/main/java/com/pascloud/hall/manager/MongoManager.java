package com.pascloud.hall.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.mongo.AbsMongoManager;
import com.pascloud.game.model.mongo.hall.dao.HallInfoDao;
import com.pascloud.game.model.mongo.hall.dao.MailDao;
import com.pascloud.game.model.mongo.hall.dao.RoleDao;
import com.pascloud.game.model.mongo.hall.dao.UserDao;

public class MongoManager extends AbsMongoManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoManager.class);
	private static final MongoManager INSTANCE_MANAGER = new MongoManager();

	public static final MongoManager getInstance() {
		return INSTANCE_MANAGER;
	}

	@Override
	protected void initDao() {
		HallInfoDao.getDB(INSTANCE_MANAGER);
		UserDao.getDB(INSTANCE_MANAGER);
		RoleDao.getDB(INSTANCE_MANAGER);
		MailDao.getDB(INSTANCE_MANAGER);
	}

}