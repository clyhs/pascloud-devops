package com.pascloud.module.system.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pascloud.bean.system.User;
import com.pascloud.dao.system.UserDao;
import com.pascloud.module.common.web.BaseController;

@Controller
@RequestMapping("module/user")
public class UserController extends BaseController {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("getusers")
	@ResponseBody
	public List<User> getusers(){
		
		return userDao.selectall();
	}

}
