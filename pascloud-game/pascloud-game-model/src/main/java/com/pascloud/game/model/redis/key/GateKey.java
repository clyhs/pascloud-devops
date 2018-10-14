package com.pascloud.game.model.redis.key;
public enum GateKey {
	/** 服务器启动时间 */
	GM_Gate_StartTime("GM_%d:Hall:starttime"),
	;
	
	private final String key;

	private GateKey(String key) {
		this.key = key;
	}

	public String getKey(Object... objects) {
		return String.format(key, objects);
	}
}