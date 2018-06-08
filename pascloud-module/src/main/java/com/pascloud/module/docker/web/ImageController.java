package com.pascloud.module.docker.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pascloud.module.common.web.BaseController;
import com.pascloud.module.docker.service.DockerService;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.docker.ImageVo;
import com.pascloud.vo.docker.NodeVo;
import com.spotify.docker.client.DefaultDockerClient;

@Controller
@RequestMapping("module/image")
public class ImageController extends BaseController {
	
	@Autowired
	private DockerService m_dockerService;
	
	@RequestMapping("/index.html")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView view = new ModelAndView("image/index");
		
		return view;
	}
	
	@RequestMapping("/images.json")
	@ResponseBody
	public List<ImageVo> getImageList(){
		List<ImageVo> result = new ArrayList<>();
		List<NodeVo> nodes = new ArrayList<>();
		nodes = m_dockerService.getNodes(getDockerClient());
		
		for(NodeVo vo: nodes){
			DefaultDockerClient client = DefaultDockerClient.builder()
					.uri("http://"+vo.getAddr()+":"+defaultPort).build();
			List<ImageVo> images = m_dockerService.getImages(client);
			if(null != images && images.size()>0){
				result.addAll(images);
			}
		}
		return result;
	}
	
	

}
