package com.pascloud.module.passervice.service;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.module.docker.service.DockerService;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.pass.ContainerThreadVo;
import com.pascloud.vo.pass.PasTypeEnum;
import com.spotify.docker.client.DefaultDockerClient;

public class ContainerThread implements Callable<ContainerThreadVo> {
	
	private static Logger log = LoggerFactory.getLogger(ContainerThread.class);
	
	private PasService m_pasService;
	
	private DefaultDockerClient m_dockerClient;
	
	private ContainerVo m_containerVo;
	
	private DockerService m_dockerService;
	
	private Integer     m_defaultPort = 2375;
	
	public ContainerThread(PasService pasService,ContainerVo containerVo ,DockerService dockerService){
		this.m_pasService = pasService;
		this.m_containerVo = containerVo;
		this.m_dockerService = dockerService;
		
	}

	private DefaultDockerClient getDockerClient(){
		this.m_dockerClient= DefaultDockerClient.builder()
				.uri("http://"+this.m_containerVo.getIp()+":"+m_defaultPort).build();
		return this.m_dockerClient;
	}

	@Override
	public ContainerThreadVo call() throws Exception {
		// TODO Auto-generated method stub
		String status = "";
		ContainerThreadVo vo = new ContainerThreadVo();
		vo.setVo(m_containerVo);
		if(uploadConfig()){
			log.info("重新上传配置文件成功");
			log.info("重新开始服务"+m_containerVo.getName());
			status = m_dockerService.restartContainer(getDockerClient(), m_containerVo.getId());
		}else{
			status = "";
		}
		vo.setStatus(status);
		return vo;
	}
	
	private Boolean uploadConfig(){
		log.info("重新上传配置文件");
		Boolean flag = false;
		if(m_containerVo.getName().contains(PasTypeEnum.DEMO.getValue())){
			m_pasService.uploadConfigForStart(m_containerVo.getIp(), PasTypeEnum.DEMO, m_containerVo.getName());
			flag = true;
		}else if(m_containerVo.getName().contains(PasTypeEnum.PASPM.getValue())){
			m_pasService.uploadConfigForStart(m_containerVo.getIp(), PasTypeEnum.PASPM, m_containerVo.getName());
			flag = true;
		}
		return flag;
	} 
	

}
