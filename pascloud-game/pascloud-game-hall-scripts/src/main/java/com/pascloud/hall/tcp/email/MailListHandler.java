package com.pascloud.hall.tcp.email;

import java.util.Date;
import java.util.List;

import com.pascloud.core.handler.HandlerEntity;
import com.pascloud.core.handler.TcpHandler;
import com.pascloud.game.model.mongo.hall.dao.MailDao;
import com.pascloud.game.model.strut.Mail;
import com.pascloud.hall.manager.MailManager;
import com.pascloud.message.Mid.MID;
import com.pascloud.message.hall.HallChatMessage.MailListRequest;
import com.pascloud.message.hall.HallChatMessage.MailListResponse;

/**
 * 请求邮件列表
 */
@HandlerEntity(mid=MID.MailListReq_VALUE,msg=MailListRequest.class)
public class MailListHandler extends TcpHandler {
	
	@Override
	public void run() {
		List<Mail> publicMails = MailDao.getPublicMails();
		List<Mail> mails = MailDao.getMails(rid);
		if(mails!=null&&publicMails!=null) {
			mails.addAll(publicMails);
		}
		MailListResponse.Builder builder=MailListResponse.newBuilder();
		if(mails!=null) {
			Date now=new Date();
			mails.forEach(mail->{
				//过期邮件监测
				if(mail.getDeleteTime()!=null&&mail.getDeleteTime().after(now)) {
					MailDao.deleteMail(mail.getId());
				}else {
					builder.addMails(MailManager.getInstance().buildMailInfo(mail));
				}
			});
		}
		sendIdMsg(builder.build());
	}

}
