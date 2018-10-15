package com.pascloud.game.model.strut;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
/**
 * 用户
 * @author admin
 *
 */
@Entity(value = "user", noClassnameStored = true)
public class User {

	/**
	 * 用户ID
	 */
	@Id
	@JSONField
	private long id;

	/** 账号 */
	@JSONField
	private String accunt;

	/** 密码 */
	@JSONField
	private String password;

	/** 注册IP地址 */
	private String ip;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccunt() {
		return accunt;
	}

	public void setAccunt(String accunt) {
		this.accunt = accunt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
