package com.pascloud.module.login.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.bean.system.User;
import com.pascloud.module.common.web.BaseController;
import com.pascloud.utils.PasCloudCode;
import com.pascloud.vo.result.ResultCommon;

@Controller
@RequestMapping("module/login")
public class LoginController extends BaseController {
	
	@RequestMapping("index.html")
	public String index(HttpServletRequest request){
		return "login/index";
	}
	
	@RequestMapping(value="login.json",method = RequestMethod.POST)
	@ResponseBody
	public ResultCommon login(HttpServletRequest request,
			@RequestParam(value="username",defaultValue="",required=true) String username,
			@RequestParam(value="password",defaultValue="",required=true) String password){
		ResultCommon result = null;
		
		HttpSession session = request.getSession();
		
		if(username.length() == 0 || password.length()==0){
			return new ResultCommon(PasCloudCode.NONEAUTH.getCode(),"请输入用户名和验证码");
		}
		
		if(username.equals("admin") && password.equals("123456")){
			log.info("登录成功");
			User user = new User();
			user.setId(10000);
			user.setName(username);
			session.setAttribute("user", user);
			result =new ResultCommon(PasCloudCode.SUCCESS);
		}else{
			result =new ResultCommon(PasCloudCode.LOGINFAILURE);
		}
		return result;
	}
	
	@RequestMapping(value="exit.json",method = RequestMethod.POST)
	@ResponseBody
	public ResultCommon exit(HttpServletRequest request){
		ResultCommon result = null;
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(null!=user){
			log.info("退出成功");
			session.removeAttribute("user");;
			result =new ResultCommon(PasCloudCode.SUCCESS);
		}else{
			result =new ResultCommon(PasCloudCode.ERROR);
		}
		return result;
	}

}
