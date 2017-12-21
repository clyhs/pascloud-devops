package com.pascloud.module.pas.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pascloud.module.common.web.BaseController;

@Controller
@RequestMapping("module/pas")
public class PasController extends BaseController {
	
	@RequestMapping("index.html")
	public String index(){
		return "pas/index";
	}

}
