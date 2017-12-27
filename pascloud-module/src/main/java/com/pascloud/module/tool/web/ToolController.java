package com.pascloud.module.tool.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("module/tool")
public class ToolController implements Serializable {

	@RequestMapping("index.html")
	public String index(HttpServletRequest request){
		return "tool/index";
	}
}
