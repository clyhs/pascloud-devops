package com.pascloud.module.script.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.script.service.ScriptService;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.vo.common.TreeVo;
import com.pascloud.vo.pascode.PascodeVo;
import com.pascloud.vo.result.ResultPageVo;
import com.pascloud.vo.script.ScriptEnum;

@Controller
@RequestMapping("module/script")
public class ScriptController extends BaseController {
	
	@Autowired
	private ScriptService m_scriptService;
	
	@RequestMapping("/index.html")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView view = new ModelAndView("script/index");
		return view;
	}
	
	@RequestMapping("/getscripts.json")
	@ResponseBody
	public List<TreeVo> getscripts(HttpServletRequest request){
		List<TreeVo> result = new ArrayList<>();
		
		List<TreeVo> oras = new ArrayList<>();
	
		List<TreeVo> db2s = new ArrayList<>();
	
		oras = m_scriptService.getScriptWithLocal(ScriptEnum.ORA);
		db2s = m_scriptService.getScriptWithLocal(ScriptEnum.DB2);
		
		result.addAll(oras);
		result.addAll(db2s);
		
		//result = m_pasdevService.getPasdevFiles(dirId);
		return result;
	}

}
