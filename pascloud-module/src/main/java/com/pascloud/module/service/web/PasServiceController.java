package com.pascloud.module.service.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pascloud.module.common.web.BaseController;

@Controller
@RequestMapping("module/pasService")
public class PasServiceController extends BaseController {

	@RequestMapping("index.html")
	public String index(HttpServletRequest request){
		return "pasService/index";
	}
}
