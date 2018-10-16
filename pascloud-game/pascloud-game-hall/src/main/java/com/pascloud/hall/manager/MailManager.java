package com.pascloud.hall.manager;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.script.ScriptManager;
import com.pascloud.game.model.mongo.hall.dao.MailDao;
import com.pascloud.game.model.strut.Mail;
import com.pascloud.game.model.strut.Mail.MailType;
import com.pascloud.hall.script.IMailScript;
import com.pascloud.message.hall.HallChatMessage.MailInfo;

/**
 * 邮件
 * <p>
 * 个人邮件单独存储，系统通用邮件只存一封,直接操作mongodb，不缓存
 * </p>
 * 
 */
public class MailManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailManager.class);
	private static volatile MailManager mailManager;

	private MailManager() {

	}

	public static MailManager getInstance() {
		if (mailManager == null) {
			synchronized (MailManager.class) {
				if (mailManager == null) {
					mailManager = new MailManager();
				}
			}
		}
		return mailManager;
	}

	public Mail getMail(long mailId) {
		return MailDao.getMail(mailId);
	}

	/**
	 * 发送邮件
	 * 
	 * @param title
	 * @param content
	 * @param type
	 * @param mailConsumer
	 */
	public void sendMail(long senderId,long receiverId,String title, String content, MailType type, Consumer<Mail> mailConsumer) {
		ScriptManager.getInstance().getBaseScriptEntry().executeScripts(IMailScript.class,
				script -> script.sendMail(senderId,receiverId,title, content, type, mailConsumer));
	}

	/**
	 * 构建邮箱信息
	 * 
	 * @param mail
	 * @return
	 */
	public MailInfo buildMailInfo(Mail mail) {
		MailInfo.Builder builder = MailInfo.newBuilder();
		builder.setContent(mail.getContent());
		builder.setTitle(mail.getTitle());
		builder.setCreateTime(mail.getCreateTime().getTime());
		builder.setId(mail.getId());
		builder.setSenderId(mail.getSenderId());
		builder.setState(mail.getState());
		return builder.build();
	}
}