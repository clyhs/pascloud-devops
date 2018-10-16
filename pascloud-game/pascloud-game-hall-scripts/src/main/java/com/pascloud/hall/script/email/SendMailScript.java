package com.pascloud.hall.script.email;

import java.util.Date;
import java.util.function.Consumer;

import com.pascloud.game.model.mongo.hall.dao.MailDao;
import com.pascloud.game.model.strut.Mail;
import com.pascloud.game.model.strut.Mail.MailState;
import com.pascloud.game.model.strut.Mail.MailType;
import com.pascloud.hall.script.IMailScript;

/**
 * 发送邮件
 */
public class SendMailScript implements IMailScript {

	@Override
	public void sendMail(long senderId, long receiverId, String title, String content, MailType type,
			Consumer<Mail> mailConsumer) {
		Mail mail = new Mail();
		mail.setTitle(title);
		mail.setContent(content);
		mail.setType(type.ordinal());
		mail.setCreateTime(new Date());
		mail.setSenderId(senderId);
		mail.setReceiverId(receiverId);
		mail.setState(MailState.NEW.ordinal());
		if (mailConsumer != null) {
			mailConsumer.accept(mail);
		}
		MailDao.saveMail(mail);

	}

}
