package com.pascloud.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.bean.system.User;

public class PasCloudFilter implements Filter {

	private static Logger log = LoggerFactory.getLogger(PasCloudFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String path = req.getContextPath();
		String url = req.getRequestURI();
		User user = (User) session.getAttribute("user");
		
		//log.info(url);
		// 如果请求页是用户登录页 继续下一步
		if (url.endsWith("/module/login/index.html") 
				|| url.endsWith("/module/login/exit.html") 
				|| url.endsWith("/index.jsp") 
				|| url.endsWith("/module/main/health.json") 
				|| url.endsWith("/module/login/login.json")) {
			chain.doFilter(request, response);
		} else {// 不是登陆页
			//log.info(user.toString());
			if (user == null) {
					res.sendRedirect(path + "/module/login/index.html");
			} else {
				
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
