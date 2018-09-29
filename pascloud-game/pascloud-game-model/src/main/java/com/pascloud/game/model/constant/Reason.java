package com.pascloud.game.model.constant;

/**
 * 全局原因、类型区别
 */
public enum Reason {
	SessionIdle("会话空闲"),
	SessionClosed("会话关闭"),
	
	UserQuit("用户退出"),
	UserLogin("用户登录"),
	UserReconnect("用户登录"),
	
	CrossServer("跨服"),
	
	RoleUseItem("使用物品"),
	RoleFire("开炮"),
	RoleSync("角色同步"),
	
	GmTickRole("gm踢角色下线"),
	
	HallGoldChange("大厅金币改变"),
	
	ServerClose("服务器关闭");
	private String reason;

	private Reason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}
	
}
