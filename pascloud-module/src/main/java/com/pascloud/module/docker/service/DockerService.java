package com.pascloud.module.docker.service;

import static com.spotify.docker.client.DockerClient.ListContainersParam.allContainers;
import static com.spotify.docker.client.DockerClient.ListContainersParam.withStatusCreated;
import static com.spotify.docker.client.DockerClient.ListContainersParam.withStatusExited;
import static com.spotify.docker.client.DockerClient.ListContainersParam.withStatusPaused;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pascloud.bean.docker.ContainerVo;
import com.pascloud.bean.docker.NodeVo;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.swarm.Node;
import com.spotify.docker.client.messages.swarm.NodeDescription;
import com.spotify.docker.client.messages.swarm.NodeSpec;
import com.spotify.docker.client.messages.swarm.ServiceSpec;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.ContainerMount;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;

//{"id":"046zdmp9snmftx4a5uc4anv86","version":{"index":56},"createdAt":"Dec 18, 2017 10:24:22 AM","updatedAt":"Dec 21, 2017 9:01:49 AM","spec":{"labels":{},"role":"manager","availability":"active"},"description":{"hostname":"centoss1.pascloud.com","platform":{"architecture":"x86_64","os":"linux"},"resources":{"nanoCpus":8000000000,"memoryBytes":16658255872},"engine":{"engineVersion":"17.05.0-ce","plugins":[{"type":"Network","name":"bridge"},{"type":"Network","name":"host"},{"type":"Network","name":"macvlan"},{"type":"Network","name":"null"},{"type":"Network","name":"overlay"},{"type":"Volume","name":"local"}]}},"status":{"state":"ready","addr":"192.168.0.16"},"managerStatus":{"leader":true,"reachability":"reachable","addr":"192.168.0.16:2377"}}
//{"id":"1upextneedo75p5sg27lk3fei","version":{"index":58},"createdAt":"Dec 18, 2017 10:29:41 AM","updatedAt":"Dec 21, 2017 9:01:54 AM","spec":{"labels":{},"role":"worker","availability":"active"},"description":{"hostname":"centoss2.pascloud.com","platform":{"architecture":"x86_64","os":"linux"},"resources":{"nanoCpus":8000000000,"memoryBytes":16659865600},"engine":{"engineVersion":"1.13.1","plugins":[{"type":"Network","name":"bridge"},{"type":"Network","name":"host"},{"type":"Network","name":"macvlan"},{"type":"Network","name":"null"},{"type":"Network","name":"overlay"},{"type":"Volume","name":"local"}]}},"status":{"state":"ready","addr":"192.168.0.7"}}
//{"id":"bstwmwsnik8hz5iy2ucxh8m7d","version":{"index":57},"createdAt":"Dec 18, 2017 10:29:34 AM","updatedAt":"Dec 21, 2017 9:01:53 AM","spec":{"labels":{},"role":"worker","availability":"active"},"description":{"hostname":"centosdb.pascloud.com","platform":{"architecture":"x86_64","os":"linux"},"resources":{"nanoCpus":16000000000,"memoryBytes":84298596352},"engine":{"engineVersion":"1.13.1","plugins":[{"type":"Network","name":"bridge"},{"type":"Network","name":"host"},{"type":"Network","name":"macvlan"},{"type":"Network","name":"null"},{"type":"Network","name":"overlay"},{"type":"Volume","name":"local"}]}},"status":{"state":"ready","addr":"192.168.0.17"}}


@Service
public class DockerService {
	
	private static Logger log = LoggerFactory.getLogger(DockerService.class);
	
	public List<NodeVo> getNodes(DefaultDockerClient dockerClient){
		List<NodeVo> nodes = new ArrayList<NodeVo>();
		
		List<Node> dockernodes;
		try {
			dockernodes = dockerClient.listNodes();
			for (Node node : dockernodes) {
				NodeVo nodeVo = new NodeVo();
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
	
	//{"id":"0caf539d41ce186a231b6badb725607ecc60e680edaedc579acd226171e20423","names":["/paspb"],"image":"tomcat:7.0.82-jre8","imageId":"sha256:35ff5178191f62051b75ec9cce1bc63d802145635b4d8624d3492bd77b3e41aa","command":"catalina.sh run","created":1513822753,"state":"running","status":"Up 4 hours","ports":[{"privatePort":8080,"publicPort":8668,"type":"tcp","ip":"0.0.0.0"}],"labels":{},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"1ac231fabeac75d29dd4e24cdd63ac9452ef9b2931ad7441303984e37ceb68c5","endpointId":"4139cd8f328134be6e1a546072cbce72db629b461a43ebde99cd8b90171558aa","gateway":"172.17.0.1","ipAddress":"172.17.0.11","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:0b"}}},"mounts":[{"type":"bind","source":"/usr/local/tomcat7/webapps","destination":"/usr/local/tomcat/webapps","mode":"","rw":true,"propagation":""}]}
	//{"id":"1177fddeafc0c33c60c25ba8baea193503366f7ddfd62b5fbacdbf82562b877c","names":["/shipyard-controller"],"image":"pascloud/shipyard:v2.0","imageId":"sha256:0baddea792c644689bd4b0ddef175149e31e9893e9fe1952ccc80fcb8bb1c2e3","command":"/bin/controller server -d tcp://swarm:3375","created":1513819029,"state":"running","status":"Up 5 hours","ports":[{"privatePort":8080,"publicPort":6969,"type":"tcp","ip":"0.0.0.0"}],"labels":{},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"1ac231fabeac75d29dd4e24cdd63ac9452ef9b2931ad7441303984e37ceb68c5","endpointId":"33533cc99533365c330ef5237220e6e00c34265dcccdac0bbe67b68e51c088a1","gateway":"172.17.0.1","ipAddress":"172.17.0.10","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:0a"}}},"mounts":[]}
	//{"id":"dfb9d66a2c1eb61bca6d147f8f416525a575d707a1056311d6c1dab01022cc42","names":["/shipyard-swarm-agent"],"image":"swarm:latest","imageId":"sha256:36b1e23becabc0b27c5787712dce019982c048665fd9e7e6cb032a46bcac510d","command":"/swarm join --addr 192.168.0.16:2375 zk://192.168.0.16:2181","created":1513819002,"state":"running","status":"Up 5 hours","ports":[{"privatePort":2375,"publicPort":0,"type":"tcp"}],"labels":{},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"1ac231fabeac75d29dd4e24cdd63ac9452ef9b2931ad7441303984e37ceb68c5","endpointId":"6069b5ec6d3605b06e0d652096072b5bf47edbe955cd47753871d801aa6267c5","gateway":"172.17.0.1","ipAddress":"172.17.0.9","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:09"}}},"mounts":[{"type":"volume","name":"344cbcf3593e1d7fffe08b3342fb373a2f243d1976407c382f7ab4b072d6fc7d","source":"/var/lib/docker/volumes/344cbcf3593e1d7fffe08b3342fb373a2f243d1976407c382f7ab4b072d6fc7d/_data","destination":"/.swarm","driver":"local","mode":"","rw":true,"propagation":""}]}
	//{"id":"3ce87dc6a360e1408ccb27e79eabf93803bbec5a639fb2727e05f91d2bad651a","names":["/shipyard-swarm-manager","/shipyard-controller/swarm"],"image":"swarm:latest","imageId":"sha256:36b1e23becabc0b27c5787712dce019982c048665fd9e7e6cb032a46bcac510d","command":"/swarm manage --host tcp://0.0.0.0:3375 zk://192.168.0.16:2181","created":1513818978,"state":"running","status":"Up 5 hours","ports":[{"privatePort":2375,"publicPort":0,"type":"tcp"}],"labels":{},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"1ac231fabeac75d29dd4e24cdd63ac9452ef9b2931ad7441303984e37ceb68c5","endpointId":"a73b3ccb31176fc41370342d8f6710d2c45e48ca1721d4227bfbd59cca8c3c01","gateway":"172.17.0.1","ipAddress":"172.17.0.8","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:08"}}},"mounts":[{"type":"volume","name":"97389fe83f1ee6172c1111b3e45dc077589fc2b99415a306d7bf9a45bd695b1b","source":"/var/lib/docker/volumes/97389fe83f1ee6172c1111b3e45dc077589fc2b99415a306d7bf9a45bd695b1b/_data","destination":"/.swarm","driver":"local","mode":"","rw":true,"propagation":""}]}
	//{"id":"1540c15dfb6fb4796fcfb6628bef4964cf9a6cd9378fd53918f339ca100feb6b","names":["/shipyard-proxy"],"image":"shipyard/docker-proxy:latest","imageId":"sha256:cfee14e5d6f280892b9fb16e830aae5c9458e61a7502048ac45b6b1723e8b0b9","command":"/usr/local/bin/run","created":1513818949,"state":"running","status":"Up 5 hours","ports":[{"privatePort":2375,"publicPort":2375,"type":"tcp","ip":"0.0.0.0"}],"labels":{},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"1ac231fabeac75d29dd4e24cdd63ac9452ef9b2931ad7441303984e37ceb68c5","endpointId":"b5d256f6d8b6f81ada65a3978d884899569ec868b15550306a69b58d280179ff","gateway":"172.17.0.1","ipAddress":"172.17.0.7","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:07"}}},"mounts":[{"type":"bind","source":"/var/run/docker.sock","destination":"/var/run/docker.sock","mode":"","rw":true,"propagation":""}]}
	//{"id":"53cddb2d0dc1a76a4b9f34a2e68ab651d88e09464546e202ca33ae22423d340b","names":["/shipyard-discovery"],"image":"microbox/etcd","imageId":"sha256:6aef84b9ec5a64742d9a24a5191c9b17a48a12a57168d5cc5d053725d8d555c2","command":"/bin/etcd -name discovery","created":1513818939,"state":"running","status":"Up 5 hours","ports":[{"privatePort":4001,"publicPort":4001,"type":"tcp","ip":"0.0.0.0"},{"privatePort":7001,"publicPort":7001,"type":"tcp","ip":"0.0.0.0"}],"labels":{},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"1ac231fabeac75d29dd4e24cdd63ac9452ef9b2931ad7441303984e37ceb68c5","endpointId":"67b6137cf4b57e218100355f56e18b11cd27356778a60671096c4e2da3b418d0","gateway":"172.17.0.1","ipAddress":"172.17.0.6","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:06"}}},"mounts":[{"type":"volume","name":"9fe705afaf841c6d38fd3842e0ab584d6bbccf90cd329b74129e3d46c3d4ca36","source":"/var/lib/docker/volumes/9fe705afaf841c6d38fd3842e0ab584d6bbccf90cd329b74129e3d46c3d4ca36/_data","destination":"/data","driver":"local","mode":"","rw":true,"propagation":""}]}
    public List<ContainerVo> getContainer(DefaultDockerClient dockerClient){
    	List<ContainerVo> containers = new ArrayList<>();
    	List<Container> dockerContainers = new ArrayList<>();
    	try {
    		String host = dockerClient.getHost();
			dockerContainers = dockerClient.listContainers(allContainers());
			for(Container container:dockerContainers){
				ContainerVo vo = new ContainerVo();
				vo.setId(container.id());
				String name = container.names().get(0);
				if(null != name){
					if(name.startsWith("/")){
						name = name.substring(1, name.length());
						vo.setName(name);
					}
				}
				//vo.setPublicPort(container.ports().);
				vo.setState(container.state());
				vo.setStatus(container.status());
				vo.setIp(host);
				vo.setPublicPort(container.portsAsString());
				
				if(name.startsWith("paspb")){
					containers.add(vo);
				}
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
    	
    	return containers;
    }
    
    public String addContainer(DefaultDockerClient dockerClient,String publicPort,
    		String volumeFrom,String volumeTo,String imageName,String containerName){
    	String[] ports = {"8080"};
		ContainerCreation creation;
		String id = "";
		try {
			Map<String, List<PortBinding>> portBindings = new HashMap<>();
			for (String port : ports) {
			    List<PortBinding> hostPorts = new ArrayList<>();
			    hostPorts.add(PortBinding.of("0.0.0.0", publicPort));
			    portBindings.put(port, hostPorts);
			}
			String bindStringFrom = volumeFrom;
			String bindStringTo = "/usr/local/tomcat/webapps";
	        HostConfig hostConfig = HostConfig.builder()
	        		.portBindings(portBindings)
	        		.appendBinds(bindStringFrom + ":" + bindStringTo)
	        		.build();
			ContainerConfig config = ContainerConfig.builder().image(imageName).hostConfig(hostConfig).
					cmd("catalina.sh", "run").exposedPorts(ports).build();
			creation = dockerClient.createContainer(config, containerName);
			id = creation.id();
			//dockerClient.startContainer(id);
		} catch (DockerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return id;
    }
    
    public String startContainer(DefaultDockerClient dockerClient,String containerId){
    	String status = "";
    	try {
			dockerClient.startContainer(containerId);
			ContainerInfo containerInfo = dockerClient.inspectContainer(containerId);
			status = containerInfo.state().status();
		} catch (DockerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		}
    	
    	return status;
    }
    
    public String pauseContainer(DefaultDockerClient dockerClient,String containerId){
    	String status = "";
    	try {
    		
			dockerClient.pauseContainer(containerId);
			ContainerInfo containerInfo = dockerClient.inspectContainer(containerId);
			status = containerInfo.state().status();
		} catch (DockerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		}
    	
    	return status;
    }
    
    public String unpauseContainer(DefaultDockerClient dockerClient,String containerId){
    	String status = "";
    	try {
    		
			dockerClient.unpauseContainer(containerId);
			ContainerInfo containerInfo = dockerClient.inspectContainer(containerId);
			status = containerInfo.state().status();
		} catch (DockerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		}
    	return status;
    }
    
    public String restartContainer(DefaultDockerClient dockerClient,String containerId){
    	String status = "";
    	try {
    		
			dockerClient.restartContainer(containerId);
			ContainerInfo containerInfo = dockerClient.inspectContainer(containerId);
			status = containerInfo.state().status();
		} catch (DockerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		}
    	return status;
    }

}
