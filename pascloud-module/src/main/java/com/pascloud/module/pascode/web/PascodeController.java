package com.pascloud.module.pascode.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.pascode.service.PascodeService;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.vo.pascode.PascodeVo;
import com.pascloud.vo.pasdev.PasfileVo;
import com.pascloud.vo.result.ResultCommon;
import com.pascloud.vo.result.ResultPageVo;

@Controller
@RequestMapping("module/pascode")
public class PascodeController extends BaseController {
	
	@Autowired
	private PascodeService     m_pascodeService;
	
	@RequestMapping("/index.html")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView view = new ModelAndView("pascode/index");
		return view;
	}
	
	@RequestMapping("/getpascode.json")
	@ResponseBody
	public ResultPageVo<PascodeVo> getPascode(HttpServletRequest request,
			@RequestParam(value="name",defaultValue="",required=false) String name,
			@RequestParam(value="page",defaultValue="",required=true) Integer page,
			@RequestParam(value="rows",defaultValue="",required=true) Integer rows){
		ResultPageVo<PascodeVo> result = new ResultPageVo<>(PasCloudCode.SUCCESS);
		
		if(page < 0 || rows < 0){
			result = new ResultPageVo<>(PasCloudCode.PARAMEXCEPTION);
		}
		if(name.length()>0){
			result = m_pascodeService.getPageData(name, page, rows);
		}else{
			result = m_pascodeService.getPageData( page, rows);
		}
		
		//result = m_pasdevService.getPasdevFiles(dirId);
		return result;
	}
	
	@RequestMapping("/uploadPascode.json")
	@ResponseBody
	public ResultCommon uploadPascode(HttpServletRequest request,
			 @RequestParam(name = "file", required = false) CommonsMultipartFile file){
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		
		if(null!=file){
			log.info(file.getOriginalFilename());
		}
		
		result = m_pascodeService.uploadPascode(file);
		return result;
		
	}
	
	@RequestMapping("/deletePascode.json")
	@ResponseBody
	public ResultCommon deletePascode(HttpServletRequest request,
			 @RequestParam(name = "id", defaultValue="0", required = true) Integer id,
			 @RequestParam(name = "name", defaultValue="", required = true) String name){
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		if(id<=0 || name.length()<=0){
			result = new ResultCommon(PasCloudCode.PARAMEXCEPTION);
			return result;
		}
		PascodeVo vo = new PascodeVo();
		vo.setId(id);
		vo.setName(name);
		result = m_pascodeService.deletePascode(vo);
		return result;
		
	}
	
	
	@RequestMapping("/uploadPascodeAndRestart.json")
	@ResponseBody
	public ResultCommon uploadPascodeAndRestart(HttpServletRequest request,
			 @RequestParam(name = "id", defaultValue="0", required = true) Integer id,
			 @RequestParam(name = "name", defaultValue="", required = true) String name,
			 @RequestParam(name = "type", defaultValue="0", required = true) Integer type){
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		
		if(id<=0 || name.length()<=0 || type < 0){
			result = new ResultCommon(PasCloudCode.PARAMEXCEPTION);
			return result;
		}
		PascodeVo vo = new PascodeVo();
		vo.setId(id);
		vo.setName(name);
		vo.setType(type);
		result = m_pascodeService.uploadPascodeAndRestart(vo);
		return result;
		
	}

}
