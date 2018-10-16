package com.pascloud.hall.script;

import java.util.function.Consumer;

import com.pascloud.core.script.IScript;
import com.pascloud.game.model.strut.Mail;
import com.pascloud.game.model.strut.Mail.MailType;


/**
 * 邮件
 */
public interface IMailScript extends IScript {

	/**
	 * 发送邮件
	 * @param title
	 * @param content
	 * @param type
	 * @param mailConsumer
	 */
	default void sendMail(long senderId,long receiverId,String title,String content,MailType type,Consumer<Mail> mailConsumer) {
		
	}
}
