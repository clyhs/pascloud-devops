package com.pascloud.module.docker;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.google.gson.Gson;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerInfo.Node;
import com.spotify.docker.client.messages.swarm.Swarm;






@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:META-INF/spring/pascloud-*.xml" })
public class DockerTest {
	
	private String dockerHost = "tcp://192.168.0.16:2375";
    private String version = "1.13.1";
    
    @Autowired
	private WebApplicationContext wac;
    
    private MockMvc mockMvc;
    
    @Before  
    public void setup() {  
        mockMvc = MockMvcBuilders.standaloneSetup(wac).build();  
    }  
	
    /**
	 * {"architecture":"x86_64","containers":19,"containersStopped":7,"containersPaused":0,"containersRunning":12,"cpuCfsPeriod":true,"cpuCfsQuota":true,"cpuShares":true,"cpuSet":true,"debug":false,"dockerRootDir":"/var/lib/docker","driver":"devicemapper","driverStatuses":[["Pool Name","docker-253:1-201992528-pool"],["Pool Blocksize","65.54 kB"],["Base Device Size","10.74 GB"],["Backing Filesystem","xfs"],["Data file","/dev/loop0"],["Metadata file","/dev/loop1"],["Data Space Used","15.35 GB"],["Data Space Total","107.4 GB"],["Data Space Available","4.296 GB"],["Metadata Space Used","17.38 MB"],["Metadata Space Total","2.147 GB"],["Metadata Space Available","2.13 GB"],["Thin Pool Minimum Free Space","10.74 GB"],["Udev Sync Supported","true"],["Deferred Removal Enabled","false"],["Deferred Deletion Enabled","false"],["Deferred Deleted Device Count","0"],["Data loop file","/var/lib/docker/devicemapper/devicemapper/data"],["Metadata loop file","/var/lib/docker/devicemapper/devicemapper/metadata"],["Library Version","1.02.135-RHEL7 (2016-11-16)"]],"plugins":{"Volume":["local"],"Network":["overlay","bridge","null","host"]},"executionDriver":"","loggingDriver":"journald","experimentalBuild":false,"httpProxy":"","httpsProxy":"","id":"YGGN:ZDE7:JLCP:3NSE:CPAY:EO3D:CF6D:HTPW:O7JZ:HRCG:M43J:BFLM","ipv4Forwarding":true,"bridgeNfIptables":true,"bridgeNfIp6tables":true,"images":87,"indexServerAddress":"https://index.docker.io/v1/","kernelVersion":"3.10.0-514.10.2.el7.x86_64","memoryLimit":true,"memTotal":16658255872,"name":"centoss1.pascloud.com","ncpu":8,"nEventsListener":1,"nfd":82,"nGoroutines":80,"noProxy":"","oomKillDisable":true,"osType":"linux","operatingSystem":"CentOS Linux 7 (Core)","registryConfig":{"indexConfigs":{"docker.io":{"mirrors":["http://9077b1f5.m.daocloud.io"],"name":"docker.io","official":true,"secure":true}},"insecureRegistryCIDRs":["127.0.0.0/8"],"mirrors":["http://9077b1f5.m.daocloud.io"]},"swapLimit":true,"systemTime":"2017-12-12T15:34:13.206699627+08:00","serverVersion":"1.12.6","clusterStore":"","clusterAdvertise":""}
	 */
	@Test
	public void testDockerInfo(){
		
		DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerHost)
                //.withApiVersion("1.13.1")
                .build();
		DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();
        Info info = dockerClient.infoCmd().exec();
        Gson g = new Gson();
        System.out.println(g.toJson(info));
        
        
        try {
			dockerClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
    public void testContainer() throws IOException, DockerException, InterruptedException {
        DefaultDockerClient docker = DefaultDockerClient.builder()
                .uri("http://192.168.0.16:2375")
                .build();

        Gson g = new Gson();
        List<com.spotify.docker.client.messages.Container> containers = docker.listContainers();
        for (com.spotify.docker.client.messages.Container container : containers) {
            System.out.println(g.toJson(container));
        }
        //Swarm swarm = docker.inspectSwarm();
        //System.out.println("*****************************");
        //System.out.println(g.toJson(swarm));
        
        
        
        docker.close();
    }
	
	@Test
    public void testImages() throws IOException, DockerException, InterruptedException {
        DefaultDockerClient docker = DefaultDockerClient.builder()
                .uri("http://192.168.0.16:2375")
                .build();

        Gson g = new Gson();
        List<com.spotify.docker.client.messages.Image> images = docker.listImages();
        for (com.spotify.docker.client.messages.Image image : images) {
            System.out.println(g.toJson(image));
        }
        Swarm swarm = docker.inspectSwarm();
        
        //System.out.println("*****************************");
        //System.out.println(g.toJson(swarm));
        docker.listNodes();
        
        
        docker.close();
    }
	
	
	@Test
	public void testDockerNode() throws DockerException, InterruptedException{
		DefaultDockerClient docker = DefaultDockerClient.builder()
                .uri("http://192.168.0.16:2375")
                .build();

        Gson g = new Gson();
        List<com.spotify.docker.client.messages.swarm.Node> nodes = docker.listNodes();
        for (com.spotify.docker.client.messages.swarm.Node node : nodes) {
            System.out.println(g.toJson(node));
        }
        
        
        
        docker.close();
		
	}
	
	

}
