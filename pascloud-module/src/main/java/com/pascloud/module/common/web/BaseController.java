package com.pascloud.module.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;


public abstract class BaseController {

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public String exceptionProcess(HttpServletRequest request, HttpServletResponse response,
			RuntimeException ex) {
		JSONObject json = new JSONObject();
		json.put("isError", true);
		json.put("msg", ex.getMessage());
		return json.toString();
	}

}
