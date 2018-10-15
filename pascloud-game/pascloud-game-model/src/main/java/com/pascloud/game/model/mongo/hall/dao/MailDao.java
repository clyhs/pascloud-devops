package com.pascloud.game.model.mongo.hall.dao;

import java.util.List;

import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.WriteResult;
import com.pascloud.core.mongo.AbsMongoManager;
import com.pascloud.game.model.strut.Mail;

/**
 * 邮件
 * 
 */
public class MailDao extends BasicDAO<Mail, Long> {
	private static volatile MailDao mailDao;

	public MailDao(AbsMongoManager mongoManager) {
		super(Mail.class, mongoManager.getMongoClient(), mongoManager.getMorphia(),
				mongoManager.getMongoConfig().getDbName());
	}

	public static MailDao getDB(AbsMongoManager mongoManager) {
		if (mailDao == null) {
			synchronized (MailDao.class) {
				if (mailDao == null) {
					mailDao = new MailDao(mongoManager);
				}
			}
		}
		return mailDao;
	}

	/**
	 * 玩家所有邮件
	 * 
	 * @return
	 */
	public static List<Mail> getMails(long receiverId) {
		return mailDao.createQuery().filter("receiverId", receiverId).asList();
	}

	/**
	 * 获取公共邮件
	 * 
	 * @return
	 */
	public static List<Mail> getPublicMails() {
		return mailDao.createQuery().filter("type", Mail.MailType.PUBLIC_SYSTEM.ordinal()).asList();
	}

	/**
	 * 存储邮件
	 * 
	 * @param mail
	 */
	public static void saveMail(Mail mail) {
		mailDao.save(mail);
	}

	/**
	 * 改变邮件状态
	 * 
	 * @param id
	 * @param mailState
	 */
	public static Mail modifyMailState(long id, int mailState) {
		Query<Mail> query = mailDao.createQuery().filter("id", id);
		UpdateOperations<Mail> operations = mailDao.createUpdateOperations().set("state", mailState);
		return mailDao.getDs().findAndModify(query, operations);
	}
	
	/**
	 * 删除邮件
	 * @param id
	 * @return
	 */
	public static int deleteMail(long id) {
		WriteResult writeResult = mailDao.deleteById(id);
		return writeResult.getN();
	}
	
	/**
	 * 获取邮件
	 * @param mailId
	 * @return
	 */
	public static Mail getMail(long mailId) {
		return mailDao.get(mailId);
	}
	
}
