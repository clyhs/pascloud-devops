package com.pascloud.core.server;
public enum ServerState {
	/** 正常 */
	NORMAL(0),
	/** 维护 */
	MAINTAIN(1),

	;
	private int state;

	private ServerState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public static ServerState valueOf(int state) {
		for (ServerState serverState : ServerState.values()) {
			if (state == serverState.getState()) {
				return serverState;
			}
		}
		throw new RuntimeException(String.format("服务器状态%d未知", state));
	}

}
