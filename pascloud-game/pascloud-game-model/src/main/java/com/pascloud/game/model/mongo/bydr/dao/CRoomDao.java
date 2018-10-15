
package com.pascloud.game.model.mongo.bydr.dao;

import java.util.List;

import org.mongodb.morphia.dao.BasicDAO;

import com.pascloud.core.mongo.AbsMongoManager;
import com.pascloud.game.model.mongo.bydr.entity.CRoom;

public class CRoomDao extends BasicDAO<CRoom, Integer> {
	private static volatile CRoomDao cRoomDao = null;

	public CRoomDao(AbsMongoManager mongoManager) {
		super(CRoom.class, mongoManager.getMongoClient(), mongoManager.getMorphia(),mongoManager.getMongoConfig().getDbName());
	}

	public static CRoomDao getDB(AbsMongoManager mongoManager) {
		if(cRoomDao == null) {
			synchronized (CRoomDao.class){
				if(cRoomDao == null){
					cRoomDao = new CRoomDao(mongoManager);
					}
				}
			}
		return cRoomDao;
	}

	public static List<CRoom> getAll(){
		 return cRoomDao.createQuery().asList();
	}

}