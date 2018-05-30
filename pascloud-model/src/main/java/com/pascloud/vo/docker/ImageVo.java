package com.pascloud.vo.docker;

import java.io.Serializable;

/**
 * {
 * "created":"1522920848",
 * "id":"sha256:4db09019de0df77ca40cd9d7b9330af1bddc992e1ab0d3756c8e11ed7080ceca",
 * "parentId":"",
 * "repoTags":["tomcat:latest"],
 * "repoDigests":["tomcat@sha256:15f12b529a268986eb86224477f22ddfdf4a42383d6758ea14eaed10b3c8a8e9"],
 * "size":553820368,
 * "virtualSize":553820368
 * }
 * @author chenly
 *
 */

public class ImageVo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String  id;
	
	private String  repoTags;
	
	private Long    size;
	
	private Long    virtualSize;
	
	private String  virtualSizeM;
	
	private String  ip;
	
	

	public String getVirtualSizeM() {
		return virtualSizeM;
	}

	public void setVirtualSizeM(String virtualSizeM) {
		this.virtualSizeM = virtualSizeM;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepoTags() {
		return repoTags;
	}

	public void setRepoTags(String repoTags) {
		this.repoTags = repoTags;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getVirtualSize() {
		return virtualSize;
	}

	public void setVirtualSize(Long virtualSize) {
		this.virtualSize = virtualSize;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	

}
