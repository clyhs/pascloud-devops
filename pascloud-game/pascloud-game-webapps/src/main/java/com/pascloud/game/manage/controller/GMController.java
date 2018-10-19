package com.pascloud.game.manage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pascloud.core.utils.HttpUtil;
import com.pascloud.game.manage.constant.Constant;
import com.pascloud.game.manage.core.vo.ResultVO;

/**
 * GM 管理
 */
@RequestMapping("/gm")
@Controller
public class GMController {
	private static final Logger LOGGER= LoggerFactory.getLogger(GMController.class);

	@Value("${cluster.url}")
	private String clusterUrl;

	/**gm调用地址*/
	private String gmUrl;

	@Value("${server.auth}")
	private String serverAuth;

	/**
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView gmList(){
		//http://127.0.0.1:8001/server/hall/ip
		String url = clusterUrl + "/server/hall/ip";
		String urlGet = HttpUtil.URLGet(url);
		gmUrl="http://"+urlGet+"/gm?";
		return new ModelAndView("gm","gmUrl",gmUrl);
	}

	/**
	 *
	 * @param queryString
	 * @return
	 */
	@RequestMapping("/execute")
	@ResponseBody
	public ResultVO<String> executeGm(String queryString){
		String url=gmUrl+"auth="+serverAuth+queryString;
		String urlGet = HttpUtil.URLGet(url);
		LOGGER.info("gm：{}",url);
		return new ResultVO<>(Constant.CODE_SUCCESS, urlGet,urlGet);
	}
}
