package com.pascloud.core.email;

import java.util.Arrays;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class MailConfig {

	/** 协议地址 */
	@Element(required = false)
	private String mailSmtpHost = "smtp.exmail.qq.com";

	/** ssl */
	@Element(required = false)
	private String mailSmtpSslEnable = "false";

	/** 验证 */
	@Element(required = false)
	private String mailSmtpAuth = "true";

	/** 邮件发送账号 */
	@Element(required = false)
	private String sendUser = "xxx@163.com";

	/** 邮件密码 */
	@Element(required = false)
	private String password = "xxxxxx";

	/** 协议地址 */
	@Element(required = false)
	private List<String> reciveUser = Arrays.asList("xxx@163.com");

	public String getMailSmtpHost() {
		return mailSmtpHost;
	}

	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}

	public String getMailSmtpSslEnable() {
		return mailSmtpSslEnable;
	}

	public void setMailSmtpSslEnable(String mailSmtpSslEnable) {
		this.mailSmtpSslEnable = mailSmtpSslEnable;
	}

	public String getMailSmtpAuth() {
		return mailSmtpAuth;
	}

	public void setMailSmtpAuth(String mailSmtpAuth) {
		this.mailSmtpAuth = mailSmtpAuth;
	}

	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getReciveUser() {
		return reciveUser;
	}

	public void setReciveUser(List<String> reciveUser) {
		this.reciveUser = reciveUser;
	}

}