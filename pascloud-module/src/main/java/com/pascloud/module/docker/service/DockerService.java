package com.pascloud.module.docker.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pascloud.bean.docker.Node;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.swarm.NodeDescription;
import com.spotify.docker.client.messages.swarm.NodeSpec;
import com.spotify.docker.client.messages.swarm.ServiceSpec;

//{"id":"046zdmp9snmftx4a5uc4anv86","version":{"index":56},"createdAt":"Dec 18, 2017 10:24:22 AM","updatedAt":"Dec 21, 2017 9:01:49 AM","spec":{"labels":{},"role":"manager","availability":"active"},"description":{"hostname":"centoss1.pascloud.com","platform":{"architecture":"x86_64","os":"linux"},"resources":{"nanoCpus":8000000000,"memoryBytes":16658255872},"engine":{"engineVersion":"17.05.0-ce","plugins":[{"type":"Network","name":"bridge"},{"type":"Network","name":"host"},{"type":"Network","name":"macvlan"},{"type":"Network","name":"null"},{"type":"Network","name":"overlay"},{"type":"Volume","name":"local"}]}},"status":{"state":"ready","addr":"192.168.0.16"},"managerStatus":{"leader":true,"reachability":"reachable","addr":"192.168.0.16:2377"}}
//{"id":"1upextneedo75p5sg27lk3fei","version":{"index":58},"createdAt":"Dec 18, 2017 10:29:41 AM","updatedAt":"Dec 21, 2017 9:01:54 AM","spec":{"labels":{},"role":"worker","availability":"active"},"description":{"hostname":"centoss2.pascloud.com","platform":{"architecture":"x86_64","os":"linux"},"resources":{"nanoCpus":8000000000,"memoryBytes":16659865600},"engine":{"engineVersion":"1.13.1","plugins":[{"type":"Network","name":"bridge"},{"type":"Network","name":"host"},{"type":"Network","name":"macvlan"},{"type":"Network","name":"null"},{"type":"Network","name":"overlay"},{"type":"Volume","name":"local"}]}},"status":{"state":"ready","addr":"192.168.0.7"}}
//{"id":"bstwmwsnik8hz5iy2ucxh8m7d","version":{"index":57},"createdAt":"Dec 18, 2017 10:29:34 AM","updatedAt":"Dec 21, 2017 9:01:53 AM","spec":{"labels":{},"role":"worker","availability":"active"},"description":{"hostname":"centosdb.pascloud.com","platform":{"architecture":"x86_64","os":"linux"},"resources":{"nanoCpus":16000000000,"memoryBytes":84298596352},"engine":{"engineVersion":"1.13.1","plugins":[{"type":"Network","name":"bridge"},{"type":"Network","name":"host"},{"type":"Network","name":"macvlan"},{"type":"Network","name":"null"},{"type":"Network","name":"overlay"},{"type":"Volume","name":"local"}]}},"status":{"state":"ready","addr":"192.168.0.17"}}


@Service
public class DockerService {
	
	private static Logger log = LoggerFactory.getLogger(DockerService.class);
	
	public List<Node> getNodes(DefaultDockerClient dockerClient){
		List<Node> nodes = new ArrayList<Node>();
		
		List<com.spotify.docker.client.messages.swarm.Node> dockernodes;
		try {
			dockernodes = dockerClient.listNodes();
			for (com.spotify.docker.client.messages.swarm.Node node : dockernodes) {
				Node nodeVo = new Node();
				nodeVo.setId(node.id());
				nodeVo.setRole(node.spec().role());
				nodeVo.setHostname(node.description().hostname());
				nodeVo.setMemory((node.description().resources().memoryBytes() / (1024 * 1204 * 1024))+"G");
				nodeVo.setAddr(node.status().addr());
				nodes.add(nodeVo);
			}
		} catch (DockerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		
		
		return nodes;
	}

}
