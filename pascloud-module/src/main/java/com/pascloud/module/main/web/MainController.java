package com.pascloud.module.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pascloud.module.common.web.BaseController;

@Controller
@RequestMapping("module/main")
public class MainController extends BaseController {
	
	@RequestMapping("index")
	public String index(){
		return "main/index";
	}

}
