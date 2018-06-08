package com.pascloud.module.docker.service;

import static com.google.common.base.Charsets.UTF_8;
import static com.spotify.docker.client.DockerClient.ListContainersParam.allContainers;
import static com.spotify.docker.client.DockerClient.ListContainersParam.withStatusCreated;
import static com.spotify.docker.client.DockerClient.ListContainersParam.withStatusExited;
import static com.spotify.docker.client.DockerClient.ListContainersParam.withStatusPaused;
import static com.spotify.docker.client.DockerClient.LogsParam.stderr;
import static com.spotify.docker.client.DockerClient.LogsParam.stdout;
import static com.spotify.docker.client.DockerClient.LogsParam.tail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.pascloud.vo.docker.ContainerVo;
import com.pascloud.vo.docker.ImageVo;
import com.pascloud.vo.docker.NodeVo;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient.ListContainersParam;
import com.spotify.docker.client.DockerClient.ListImagesParam;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.swarm.Node;
import com.spotify.docker.client.messages.swarm.NodeDescription;
import com.spotify.docker.client.messages.swarm.NodeSpec;
import com.spotify.docker.client.messages.swarm.ServiceSpec;
import com.spotify.docker.client.messages.swarm.Swarm;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.ContainerMount;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.Image;
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
				nodeVo.setStatus(node.status().state());
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
	
	/**
	 * 获取镜像列表
	 * @param dockerClient
	 * @return
	 */
	public List<ImageVo> getImages(DefaultDockerClient dockerClient){
		
		List<ImageVo> result = new ArrayList<ImageVo>();
		
		try {
			
			List<Image> images = dockerClient.listImages();
			if(null!=images && images.size() > 0){
				for(Image img:images){
					ImageVo vo = new ImageVo();
					vo.setId(img.id());
					vo.setRepoTags(img.repoTags().get(0));
					vo.setSize(img.size());
					vo.setVirtualSize(img.virtualSize());
					vo.setVirtualSizeM(img.virtualSize()/(1024*1024)+"m");
					vo.setIp(dockerClient.getHost());
					result.add(vo);
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
		
		return result;
	}
	
    public List<ImageVo> getImageByName(DefaultDockerClient dockerClient,String name){
		
		List<ImageVo> result = new ArrayList<ImageVo>();
		
		try {
			List<Image> images = dockerClient.listImages();
			if(null!=images && images.size() > 0){
				for(Image img:images){
					ImageVo vo = new ImageVo();
					vo.setId(img.id());
					vo.setRepoTags(img.repoTags().get(0));
					vo.setSize(img.size());
					vo.setVirtualSize(img.virtualSize());
					vo.setVirtualSizeM(img.virtualSize()/(1024*1024)+"m");
					vo.setIp(dockerClient.getHost());
					if(img.repoTags().contains(name)){
						result.add(vo);
					}
					
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
		
		return result;
	}
	
	
	//{"id":"0caf539d41ce186a231b6badb725607ecc60e680edaedc579acd226171e20423","names":["/paspb"],"image":"tomcat:7.0.82-jre8","imageId":"sha256:35ff5178191f62051b75ec9cce1bc63d802145635b4d8624d3492bd77b3e41aa","command":"catalina.sh run","created":1513822753,"state":"running","status":"Up 4 hours","ports":[{"privatePort":8080,"publicPort":8668,"type":"tcp","ip":"0.0.0.0"}],"labels":{},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"1ac231fabeac75d29dd4e24cdd63ac9452ef9b2931ad7441303984e37ceb68c5","endpointId":"4139cd8f328134be6e1a546072cbce72db629b461a43ebde99cd8b90171558aa","gateway":"172.17.0.1","ipAddress":"172.17.0.11","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:0b"}}},"mounts":[{"type":"bind","source":"/usr/local/tomcat7/webapps","destination":"/usr/local/tomcat/webapps","mode":"","rw":true,"propagation":""}]}
	//{"id":"1177fddeafc0c33c60c25ba8baea193503366f7ddfd62b5fbacdbf82562b877c","names":["/shipyard-controller"],"image":"pascloud/shipyard:v2.0","imageId":"sha256:0baddea792c644689bd4b0ddef175149e31e9893e9fe1952ccc80fcb8bb1c2e3","command":"/bin/controller server -d tcp://swarm:3375","created":1513819029,"state":"running","status":"Up 5 hours","ports":[{"privatePort":8080,"publicPort":6969,"type":"tcp","ip":"0.0.0.0"}],"labels":{},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"1ac231fabeac75d29dd4e24cdd63ac9452ef9b2931ad7441303984e37ceb68c5","endpointId":"33533cc99533365c330ef5237220e6e00c34265dcccdac0bbe67b68e51c088a1","gateway":"172.17.0.1","ipAddress":"172.17.0.10","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:0a"}}},"mounts":[]}
	//{"id":"dfb9d66a2c1eb61bca6d147f8f416525a575d707a1056311d6c1dab01022cc42","names":["/shipyard-swarm-agent"],"image":"swarm:latest","imageId":"sha256:36b1e23becabc0b27c5787712dce019982c048665fd9e7e6cb032a46bcac510d","command":"/swarm join --addr 192.168.0.16:2375 zk://192.168.0.16:2181","created":1513819002,"state":"running","status":"Up 5 hours","ports":[{"privatePort":2375,"publicPort":0,"type":"tcp"}],"labels":{},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"1ac231fabeac75d29dd4e24cdd63ac9452ef9b2931ad7441303984e37ceb68c5","endpointId":"6069b5ec6d3605b06e0d652096072b5bf47edbe955cd47753871d801aa6267c5","gateway":"172.17.0.1","ipAddress":"172.17.0.9","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:09"}}},"mounts":[{"type":"volume","name":"344cbcf3593e1d7fffe08b3342fb373a2f243d1976407c382f7ab4b072d6fc7d","source":"/var/lib/docker/volumes/344cbcf3593e1d7fffe08b3342fb373a2f243d1976407c382f7ab4b072d6fc7d/_data","destination":"/.swarm","driver":"local","mode":"","rw":true,"propagation":""}]}
	//{"id":"3ce87dc6a360e1408ccb27e79eabf93803bbec5a639fb2727e05f91d2bad651a","names":["/shipyard-swarm-manager","/shipyard-controller/swarm"],"image":"swarm:latest","imageId":"sha256:36b1e23becabc0b27c5787712dce019982c048665fd9e7e6cb032a46bcac510d","command":"/swarm manage --host tcp://0.0.0.0:3375 zk://192.168.0.16:2181","created":1513818978,"state":"running","status":"Up 5 hours","ports":[{"privatePort":2375,"publicPort":0,"type":"tcp"}],"labels":{},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"1ac231fabeac75d29dd4e24cdd63ac9452ef9b2931ad7441303984e37ceb68c5","endpointId":"a73b3ccb31176fc41370342d8f6710d2c45e48ca1721d4227bfbd59cca8c3c01","gateway":"172.17.0.1","ipAddress":"172.17.0.8","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:08"}}},"mounts":[{"type":"volume","name":"97389fe83f1ee6172c1111b3e45dc077589fc2b99415a306d7bf9a45bd695b1b","source":"/var/lib/docker/volumes/97389fe83f1ee6172c1111b3e45dc077589fc2b99415a306d7bf9a45bd695b1b/_data","destination":"/.swarm","driver":"local","mode":"","rw":true,"propagation":""}]}
	//{"id":"1540c15dfb6fb4796fcfb6628bef4964cf9a6cd9378fd53918f339ca100feb6b","names":["/shipyard-proxy"],"image":"shipyard/docker-proxy:latest","imageId":"sha256:cfee14e5d6f280892b9fb16e830aae5c9458e61a7502048ac45b6b1723e8b0b9","command":"/usr/local/bin/run","created":1513818949,"state":"running","status":"Up 5 hours","ports":[{"privatePort":2375,"publicPort":2375,"type":"tcp","ip":"0.0.0.0"}],"labels":{},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"1ac231fabeac75d29dd4e24cdd63ac9452ef9b2931ad7441303984e37ceb68c5","endpointId":"b5d256f6d8b6f81ada65a3978d884899569ec868b15550306a69b58d280179ff","gateway":"172.17.0.1","ipAddress":"172.17.0.7","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:07"}}},"mounts":[{"type":"bind","source":"/var/run/docker.sock","destination":"/var/run/docker.sock","mode":"","rw":true,"propagation":""}]}
	//{"id":"53cddb2d0dc1a76a4b9f34a2e68ab651d88e09464546e202ca33ae22423d340b","names":["/shipyard-discovery"],"image":"microbox/etcd","imageId":"sha256:6aef84b9ec5a64742d9a24a5191c9b17a48a12a57168d5cc5d053725d8d555c2","command":"/bin/etcd -name discovery","created":1513818939,"state":"running","status":"Up 5 hours","ports":[{"privatePort":4001,"publicPort":4001,"type":"tcp","ip":"0.0.0.0"},{"privatePort":7001,"publicPort":7001,"type":"tcp","ip":"0.0.0.0"}],"labels":{},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"1ac231fabeac75d29dd4e24cdd63ac9452ef9b2931ad7441303984e37ceb68c5","endpointId":"67b6137cf4b57e218100355f56e18b11cd27356778a60671096c4e2da3b418d0","gateway":"172.17.0.1","ipAddress":"172.17.0.6","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:06"}}},"mounts":[{"type":"volume","name":"9fe705afaf841c6d38fd3842e0ab584d6bbccf90cd329b74129e3d46c3d4ca36","source":"/var/lib/docker/volumes/9fe705afaf841c6d38fd3842e0ab584d6bbccf90cd329b74129e3d46c3d4ca36/_data","destination":"/data","driver":"local","mode":"","rw":true,"propagation":""}]}
    //{"id":"ead0afb7c8cdd30a2dba9466d0750278dcf6b624e6d19429ba74fae59efb2509","created":"Jan 10, 2018 8:50:59 AM","path":"/swarm","args":["manage","--host","tcp://0.0.0.0:3375","zk://192.168.0.16:2181"],"config":{"hostname":"ead0afb7c8cd","domainname":"","user":"","attachStdin":false,"attachStdout":false,"attachStderr":false,"exposedPorts":["2375/tcp"],"tty":true,"openStdin":true,"stdinOnce":false,"env":["PATH\u003d/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin","SWARM_HOST\u003d:2375"],"cmd":["manage","--host","tcp://0.0.0.0:3375","zk://192.168.0.16:2181"],"image":"swarm:latest","volumes":["/.swarm"],"workingDir":"","entrypoint":["/swarm"],"labels":{}},"hostConfig":{"blkioWeight":0,"containerIdFile":"","privileged":false,"portBindings":{},"publishAllPorts":false,"dns":[],"dnsOptions":[],"dnsSearch":[],"networkMode":"default","devices":[],"memory":0,"memorySwap":0,"memorySwappiness":-1,"memoryReservation":0,"nanoCpus":0,"cpuPeriod":0,"cpuShares":0,"cpusetCpus":"","cpusetMems":"","cpuQuota":0,"cgroupParent":"","restartPolicy":{"name":"always","maxRetryCount":0},"logConfig":{"logType":"json-file","logOptions":{}},"ipcMode":"","pidMode":"","shmSize":67108864,"oomKillDisable":false,"oomScoreAdj":0,"autoRemove":false,"pidsLimit":0,"readonlyRootfs":false},"state":{"status":"running","running":true,"paused":false,"restarting":false,"pid":25667,"exitCode":0,"startedAt":"Jun 8, 2018 9:19:49 AM","finishedAt":"Jun 8, 2018 8:53:15 AM","error":"","oomKilled":false},"image":"sha256:36b1e23becabc0b27c5787712dce019982c048665fd9e7e6cb032a46bcac510d","networkSettings":{"ipAddress":"172.17.0.3","ipPrefixLen":16,"gateway":"172.17.0.1","bridge":"","ports":{"2375/tcp":[]},"macAddress":"02:42:ac:11:00:03","networks":{"bridge":{"networkId":"177a975070f29f61a4c6dfb594cf9ae6b7318b6242361a00eaeb43fd21482518","endpointId":"35f118f2ba724263e33aaf2a22734cebd3bc2bc44aaf25ac1179099704a2eb3e","gateway":"172.17.0.1","ipAddress":"172.17.0.3","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:03"}},"endpointId":"35f118f2ba724263e33aaf2a22734cebd3bc2bc44aaf25ac1179099704a2eb3e","sandboxId":"43be113181acc28dad49fea6e1f77f380cfe4203276abd0590dd7f70f4d12ba0","sandboxKey":"/var/run/docker/netns/43be113181ac","hairpinMode":false,"linkLocalIPv6Address":"","linkLocalIPv6PrefixLen":0,"globalIPv6Address":"","globalIPv6PrefixLen":0,"ipv6Gateway":""},"resolvConfPath":"/var/lib/docker/containers/ead0afb7c8cdd30a2dba9466d0750278dcf6b624e6d19429ba74fae59efb2509/resolv.conf","hostnamePath":"/var/lib/docker/containers/ead0afb7c8cdd30a2dba9466d0750278dcf6b624e6d19429ba74fae59efb2509/hostname","hostsPath":"/var/lib/docker/containers/ead0afb7c8cdd30a2dba9466d0750278dcf6b624e6d19429ba74fae59efb2509/hosts","name":"/shipyard-swarm-manager","driver":"devicemapper","processLabel":"","mountLabel":"","appArmorProfile":"","logPath":"/var/lib/docker/containers/ead0afb7c8cdd30a2dba9466d0750278dcf6b624e6d19429ba74fae59efb2509/ead0afb7c8cdd30a2dba9466d0750278dcf6b624e6d19429ba74fae59efb2509-json.log","restartCount":0,"mounts":[{"type":"volume","name":"2813671f98c968691154c0286138ca3d567277e714e59e0db0d043206135842c","source":"/var/lib/docker/volumes/2813671f98c968691154c0286138ca3d567277e714e59e0db0d043206135842c/_data","destination":"/.swarm","driver":"local","mode":"","rw":true,"propagation":""}]}

    public List<ContainerVo> getContainer(DefaultDockerClient dockerClient , String str){
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
				
				if(name.startsWith(str)){
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
    
    public String addContainer(DefaultDockerClient dockerClient,Map<String,String> publicPort,
    		String volumeFrom,String volumeTo,String imageName,String containerName,String[] cmd,
    		List<String> envs){
    	ContainerCreation creation;
		String id = "";
		try {
			Set<String> ports = new HashSet<>();
			Map<String, List<PortBinding>> portBindings = new HashMap<>();
			Iterator<Entry<String, String>> it = publicPort.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String,String> entry = it.next();
			    List<PortBinding> hostPorts = new ArrayList<>();
			    hostPorts.add(PortBinding.of("0.0.0.0", entry.getValue()));
			    portBindings.put(entry.getKey(), hostPorts);
			    ports.add(entry.getKey());
			}
			String bindStringFrom = volumeFrom;
			String bindStringTo = volumeTo;
	        HostConfig hostConfig = null;
	        
	        if(!"".equals(bindStringFrom)){
	        	hostConfig = HostConfig.builder()
		        		.portBindings(portBindings)
		        		.appendBinds(bindStringFrom + ":" + bindStringTo)
		        		.build();
	        }else{
	        	hostConfig = HostConfig.builder()
		        		.portBindings(portBindings)
		        		.build();
	        }
	        ContainerConfig config = null;
	        if(envs.size()>0){
	        	config = ContainerConfig.builder()
						.image(imageName)
						.hostConfig(hostConfig)
						.env(envs)
						.cmd(cmd)
						.exposedPorts(ports)
						.build();
	        }else{
	        	config = ContainerConfig.builder()
						.image(imageName)
						.hostConfig(hostConfig)
						.cmd(cmd)
						.exposedPorts(ports)
						.build();
	        }
			
			System.out.println(config.toString());
			
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
    
    public String addContainerForPB(DefaultDockerClient dockerClient,String publicPort,
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
    
    public String addContainerFormycat(DefaultDockerClient dockerClient,Map<String,String> publicPort,
    		String volumeFrom,String volumeTo,String imageName,String containerName){
    	
		ContainerCreation creation;
		String id = "";
		try {
			Set<String> ports = new HashSet<>();
			Map<String, List<PortBinding>> portBindings = new HashMap<>();
			Iterator<Entry<String, String>> it = publicPort.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String,String> entry = it.next();
			    List<PortBinding> hostPorts = new ArrayList<>();
			    hostPorts.add(PortBinding.of("0.0.0.0", entry.getValue()));
			    portBindings.put(entry.getKey(), hostPorts);
			    ports.add(entry.getValue());
			}
			String bindStringFrom = volumeFrom;
			String bindStringTo = "/home/mycat";
	        HostConfig hostConfig = HostConfig.builder()
	        		.portBindings(portBindings)
	        		.appendBinds("/home/pascloud16/mycat/conf" + ":" + "/home/mycat/conf")
	        		.build();
			ContainerConfig config = ContainerConfig.builder().image(imageName).hostConfig(hostConfig).exposedPorts(ports).build();
			
			System.out.println(config.toString());
			
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
    
    public String stopContainer(DefaultDockerClient dockerClient,String containerId){
    	String status = "";
    	try {
    		
			dockerClient.stopContainer(containerId, 1);
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
    
    public String removeContainer(DefaultDockerClient dockerClient,String containerId){
    	String status = "";
    	try {
			dockerClient.removeContainer(containerId);
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
    
    public Boolean leaveSwarm(DefaultDockerClient dockerClient,String ip){
    	Boolean flag = false;
    	try {
    		DefaultDockerClient client = DefaultDockerClient.builder()
    				.uri("http://"+ip+":"+"2375").build();
    		log.info("删除节点");
    		client.leaveSwarm(true);
			
			
			List<Node> dockernodes = dockerClient.listNodes();
			for(Node node : dockernodes){
				if(node.status().addr().equals(ip)){
					dockerClient.deleteNode(node.id(),true);
				}
			}
			
			log.info("删除节点成功");
			flag = true;
		} catch (DockerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
			log.error("删除节点失败");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
			log.error("删除节点失败");
		}
    	return flag;
    }
    /**
     * {"id":"ead0afb7c8cdd30a2dba9466d0750278dcf6b624e6d19429ba74fae59efb2509","created":"Jan 10, 2018 8:50:59 AM","path":"/swarm","args":["manage","--host","tcp://0.0.0.0:3375","zk://192.168.0.16:2181"],"config":{"hostname":"ead0afb7c8cd","domainname":"","user":"","attachStdin":false,"attachStdout":false,"attachStderr":false,"exposedPorts":["2375/tcp"],"tty":true,"openStdin":true,"stdinOnce":false,"env":["PATH\u003d/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin","SWARM_HOST\u003d:2375"],"cmd":["manage","--host","tcp://0.0.0.0:3375","zk://192.168.0.16:2181"],"image":"swarm:latest","volumes":["/.swarm"],"workingDir":"","entrypoint":["/swarm"],"labels":{}},"hostConfig":{"blkioWeight":0,"containerIdFile":"","privileged":false,"portBindings":{},"publishAllPorts":false,"dns":[],"dnsOptions":[],"dnsSearch":[],"networkMode":"default","devices":[],"memory":0,"memorySwap":0,"memorySwappiness":-1,"memoryReservation":0,"nanoCpus":0,"cpuPeriod":0,"cpuShares":0,"cpusetCpus":"","cpusetMems":"","cpuQuota":0,"cgroupParent":"","restartPolicy":{"name":"always","maxRetryCount":0},"logConfig":{"logType":"json-file","logOptions":{}},"ipcMode":"","pidMode":"","shmSize":67108864,"oomKillDisable":false,"oomScoreAdj":0,"autoRemove":false,"pidsLimit":0,"readonlyRootfs":false},"state":{"status":"running","running":true,"paused":false,"restarting":false,"pid":25667,"exitCode":0,"startedAt":"Jun 8, 2018 9:19:49 AM","finishedAt":"Jun 8, 2018 8:53:15 AM","error":"","oomKilled":false},"image":"sha256:36b1e23becabc0b27c5787712dce019982c048665fd9e7e6cb032a46bcac510d","networkSettings":{"ipAddress":"172.17.0.3","ipPrefixLen":16,"gateway":"172.17.0.1","bridge":"","ports":{"2375/tcp":[]},"macAddress":"02:42:ac:11:00:03","networks":{"bridge":{"networkId":"177a975070f29f61a4c6dfb594cf9ae6b7318b6242361a00eaeb43fd21482518","endpointId":"35f118f2ba724263e33aaf2a22734cebd3bc2bc44aaf25ac1179099704a2eb3e","gateway":"172.17.0.1","ipAddress":"172.17.0.3","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:03"}},"endpointId":"35f118f2ba724263e33aaf2a22734cebd3bc2bc44aaf25ac1179099704a2eb3e","sandboxId":"43be113181acc28dad49fea6e1f77f380cfe4203276abd0590dd7f70f4d12ba0","sandboxKey":"/var/run/docker/netns/43be113181ac","hairpinMode":false,"linkLocalIPv6Address":"","linkLocalIPv6PrefixLen":0,"globalIPv6Address":"","globalIPv6PrefixLen":0,"ipv6Gateway":""},"resolvConfPath":"/var/lib/docker/containers/ead0afb7c8cdd30a2dba9466d0750278dcf6b624e6d19429ba74fae59efb2509/resolv.conf","hostnamePath":"/var/lib/docker/containers/ead0afb7c8cdd30a2dba9466d0750278dcf6b624e6d19429ba74fae59efb2509/hostname","hostsPath":"/var/lib/docker/containers/ead0afb7c8cdd30a2dba9466d0750278dcf6b624e6d19429ba74fae59efb2509/hosts","name":"/shipyard-swarm-manager","driver":"devicemapper","processLabel":"","mountLabel":"","appArmorProfile":"","logPath":"/var/lib/docker/containers/ead0afb7c8cdd30a2dba9466d0750278dcf6b624e6d19429ba74fae59efb2509/ead0afb7c8cdd30a2dba9466d0750278dcf6b624e6d19429ba74fae59efb2509-json.log","restartCount":0,"mounts":[{"type":"volume","name":"2813671f98c968691154c0286138ca3d567277e714e59e0db0d043206135842c","source":"/var/lib/docker/volumes/2813671f98c968691154c0286138ca3d567277e714e59e0db0d043206135842c/_data","destination":"/.swarm","driver":"local","mode":"","rw":true,"propagation":""}]}
     * @param dockerClient
     * @param containerId
     * @return
     */
    public ContainerInfo getContainerInfoById(DefaultDockerClient dockerClient,String containerId){
    	ContainerInfo containerInfo = null;
    	try {
			containerInfo = dockerClient.inspectContainer(containerId);
		} catch (DockerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.error(e.getMessage());
		}
    	return containerInfo;
    }
    
    public String getLog(DefaultDockerClient dockerClient,String containerName){
    	//final String logs ;
    	final StringBuffer sb = new StringBuffer();
    	try {
			final ContainerInfo info = dockerClient.inspectContainer(containerName);
			
			try (LogStream stream = dockerClient.logs(info.id(), stdout(), stderr(), tail(200))) {
				//logs = stream.readFully();
				//logs = stream.
				try {
					while (stream.hasNext()) {
						final String r = UTF_8.decode(stream.next().content()).toString();
						//System.out.println(r);
						sb.append(r);
					}
				} catch (Exception e) {
					//log.info(e.getMessage());
				}
			}
			return sb.toString();
		} catch (DockerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    	return "";
    }

}
