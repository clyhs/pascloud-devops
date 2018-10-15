package com.pascloud.game.model.mongo.hall.dao;

import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import com.pascloud.core.mongo.AbsMongoManager;
import com.pascloud.game.model.strut.User;

public class UserDao extends BasicDAO<User, Long> {

    private static volatile UserDao userDao;

    private UserDao(AbsMongoManager mongoManager) {
        super(User.class, mongoManager.getMongoClient(), mongoManager.getMorphia(), mongoManager.getMongoConfig().getDbName());
    }

    public static UserDao getDB(AbsMongoManager mongoManager) {
        if (userDao == null) {
            synchronized (UserDao.class) {
                if (userDao == null) {
                    userDao = new UserDao(mongoManager);
                }
            }
        }
        return userDao;
    }

    public static User findByAccount(String accunt) {
        Query<User> query = userDao.createQuery().filter("accunt", accunt);
        return query.get();
    }
    
    public static void saveUser(User user){
        userDao.save(user);
    }

}