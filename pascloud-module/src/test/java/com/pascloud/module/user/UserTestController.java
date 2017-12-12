package com.pascloud.module.user;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pascloud.bean.system.User;
import com.pascloud.dao.system.UserDao;
import com.pascloud.module.system.web.UserController;

//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(locations = { "classpath*:META-INF/spring/pascloud-*.xml" })
public class UserTestController   {
//
//	@Autowired
//	private WebApplicationContext wac;
//
//	@Autowired
//	private UserDao userDao;// 你要测试的Controller
//	@Autowired
//	private UserController userController;
//
//	private MockMvc mockMvc;
//	
//	@Before  
//    public void setup() {  
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();  
//    }  
//  
//    @Test  
//    public void testFindPageUsers() throws Exception {  
//    	/*
//        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders  
//                .get("/module/user/getusers")  
//                .accept(MediaType.APPLICATION_JSON).param("page", "1")  
//                .param("limit", "10"));  */
//    	
//    	ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders  
//                .get("/module/user/getusers")  
//                .accept(MediaType.APPLICATION_JSON));
//        MvcResult mr = ra.andReturn();  
//        String result = mr.getResponse().getContentAsString();  
//        System.out.println(result); 
//    	/*
//    	List<User> users = userDao.selectall();
//    	System.out.println(users.size());*/
//    } 

}
