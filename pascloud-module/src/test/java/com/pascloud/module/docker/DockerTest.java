package com.pascloud.module.docker;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.spotify.docker.client.DockerClient.ListContainersParam.allContainers;
import static com.spotify.docker.client.DockerClient.ListContainersParam.withLabel;
import static com.spotify.docker.client.DockerClient.ListContainersParam.withStatusCreated;
import static com.spotify.docker.client.DockerClient.ListContainersParam.withStatusExited;
import static com.spotify.docker.client.DockerClient.ListContainersParam.withStatusPaused;
import static com.spotify.docker.client.DockerClient.ListContainersParam.withStatusRunning;
import static com.spotify.docker.client.DockerClient.ListImagesParam.allImages;
import static com.spotify.docker.client.DockerClient.ListImagesParam.byName;
import static com.spotify.docker.client.DockerClient.ListImagesParam.danglingImages;
import static com.spotify.docker.client.DockerClient.ListImagesParam.digests;
import static com.spotify.docker.client.DockerClient.RemoveContainerParam.forceKill;
import static java.lang.Long.toHexString;
import static java.lang.System.getenv;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

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

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.Image;
import com.spotify.docker.client.messages.ImageInfo;
import com.spotify.docker.client.messages.swarm.EngineConfig;
import com.spotify.docker.client.messages.swarm.EnginePlugin;
import com.spotify.docker.client.messages.swarm.Node;
import com.spotify.docker.client.messages.swarm.NodeDescription;
import com.spotify.docker.client.messages.swarm.NodeSpec;
import com.spotify.docker.client.messages.swarm.Service;
import com.spotify.docker.client.messages.swarm.ServiceSpec;
import com.spotify.docker.client.messages.swarm.Swarm;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:META-INF/spring/pascloud-*.xml" })
public class DockerTest {

	private static final String BUSYBOX = "busybox";
	private static final String BUSYBOX_LATEST = BUSYBOX + ":latest";
	private static final String BUSYBOX_BUILDROOT_2013_08_1 = BUSYBOX + ":buildroot-2013.08.1";
	private static final String MEMCACHED = "rohan/memcached-mini";
	private static final String MEMCACHED_LATEST = MEMCACHED + ":latest";
	private static final String CIRROS_PRIVATE = "dxia/cirros-private";
	private static final String CIRROS_PRIVATE_LATEST = CIRROS_PRIVATE + ":latest";

	private static final boolean CIRCLECI = !isNullOrEmpty(getenv("CIRCLECI"));
	private static final boolean TRAVIS = "true".equals(getenv("TRAVIS"));

	private static final String AUTH_USERNAME = "dxia2";
	private static final String AUTH_PASSWORD = "Tv38KLPd]M";

	private String dockerHost = "tcp://192.168.0.16:2375";
	private String version = "1.13.1";

	private final String nameTag = toHexString(ThreadLocalRandom.current().nextLong());

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private URI dockerEndpoint;

	private DefaultDockerClient sut;

	private String dockerApiVersion;

	private Gson g = new Gson();

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(wac).build();
		DefaultDockerClient docker = DefaultDockerClient.builder().uri("http://192.168.0.17:2375").build();
		sut = docker;
		dockerEndpoint = docker.builder().uri();
		dockerApiVersion = sut.version().apiVersion();

	}

	@Test
	public void testListImages() throws Exception {
		final List<Image> images = sut.listImages();
		for (Image image : images) {
			System.out.println(g.toJson(image));
		}
	}

	@Test
	public void testListNodes() throws Exception {
		List<Node> nodes = sut.listNodes();
		for (Node node : nodes) {
			System.out.println(g.toJson(node));
		}
	}

	@Test
	public void testListServices() throws Exception {
		List<Service> services = sut.listServices();
		for (Service s : services) {
			System.out.println(g.toJson(s));
		}

	}

	@Test
	public void testListContainers() throws Exception {

		List<Container> containers = sut.listContainers();
		for (Container s : containers) {
			System.out.println(g.toJson(s));
		}
	}

	@Test
	public void testInspectSwarm() throws Exception {

		final Swarm swarm = sut.inspectSwarm();
		System.out.println(g.toJson(swarm));

	}

	@Test
	public void testCommitContainer() throws Exception {
		// Pull image
		sut.pull(BUSYBOX_LATEST);

		// Create container
		final ContainerConfig config = ContainerConfig.builder().image(BUSYBOX_LATEST).build();
		final String name = randomName();
		final ContainerCreation creation = sut.createContainer(config, name);
		// final String id = creation.id();
		final String id = creation.id();

		final String tag = randomName();
		final ContainerCreation dockerClientTest = sut.commitContainer(id, "mosheeshel/busybox", tag, config,
				"CommitedByTest-" + tag, "DockerClientTest");

		final ImageInfo imageInfo = sut.inspectImage(dockerClientTest.id());
		 assertThat(imageInfo.author(), is("DockerClientTest"));
		 assertThat(imageInfo.comment(), is("CommitedByTest-" + tag));
		//System.out.println(imageInfo.comment());
		//System.out.println(imageInfo.author());

	}

	@Test
	public void testContainerLabels() throws Exception {
		sut.pull(BUSYBOX_LATEST);

		final Map<String, String> labels = ImmutableMap.of("name", "starship", "foo", "bar");

		// Create container
		final ContainerConfig config = ContainerConfig.builder().image(BUSYBOX_LATEST).labels(labels)
				.cmd("sleep", "1000").build();
		final String name = randomName();
		final ContainerCreation creation = sut.createContainer(config, name);
		final String id = creation.id();

		// Start the container
		sut.startContainer(id);

		final ContainerInfo containerInfo = sut.inspectContainer(id);
		assertThat(containerInfo.config().labels(), is(labels));
		//System.out.println(containerInfo.config().labels());

	}
	
	@Test
	public void testassertThat() throws Exception {
		
		assertThat("123", is("123"));
	}

	private String randomName() {
		return nameTag + '-' + toHexString(ThreadLocalRandom.current().nextLong());
	}
}
