package com.pascloud.module.etl.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.module.common.service.AbstractBaseService;
import com.pascloud.vo.common.TreeVo;

@Controller
@RequestMapping("module/etl")
public class EtlController extends AbstractBaseService {
	
	@RequestMapping("demo.json")
	@ResponseBody
	public String getLeftMenu(HttpServletRequest request) {
		return "hello world";
	}

}
