package com.pascloud.game.model.mongo.hall.dao;

import org.mongodb.morphia.dao.BasicDAO;

import com.pascloud.core.mongo.AbsMongoManager;
import com.pascloud.game.model.strut.Role;

/**
 * 角色
 *
 */
public class RoleDao extends BasicDAO<Role, Long> {

	private static volatile RoleDao roleDao;

	private RoleDao(AbsMongoManager mongoManager) {
		super(Role.class, mongoManager.getMongoClient(), mongoManager.getMorphia(),
				mongoManager.getMongoConfig().getDbName());
	}

	public static RoleDao getDB(AbsMongoManager mongoManager) {
		if (roleDao == null) {
			synchronized (RoleDao.class) {
				if (roleDao == null) {
					roleDao = new RoleDao(mongoManager);
				}
			}
		}
		return roleDao;
	}

	public static Role getRoleByUserId(long userId) {
		Role role = roleDao.createQuery().filter("userId", userId).get();
		return role;
	}

	public static void saveRole(Role role) {
		roleDao.save(role);
	}

}