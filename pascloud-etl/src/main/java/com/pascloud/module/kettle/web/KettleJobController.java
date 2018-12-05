package com.pascloud.module.kettle.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.module.kettle.service.KettleJobService;
import com.pascloud.vo.result.ResultCommon;

@RequestMapping("/module/kettlejob")
@Controller
public class KettleJobController {
	
	@Autowired
	private KettleJobService kettleJobService;
	
	@RequestMapping("execute")
	@ResponseBody
	public ResultCommon execute(){
		
		ResultCommon result = null;
		
		result = kettleJobService.execute("pascloud","admin","admin", "/", "testjob1");
		return result;
		
	}

}
