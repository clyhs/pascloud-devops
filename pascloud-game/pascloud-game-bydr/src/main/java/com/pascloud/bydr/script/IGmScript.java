package com.pascloud.bydr.script;

import com.pascloud.core.handler.HttpHandler;
import com.pascloud.core.script.IScript;

/**
 * gm
 *
 */
public interface IGmScript extends IScript{

	/**
	 * 验证http请求sid
	 * @param handler
	 * @return
	 */
	default boolean authHttpSid(HttpHandler handler) {
		return false;
	}
}
