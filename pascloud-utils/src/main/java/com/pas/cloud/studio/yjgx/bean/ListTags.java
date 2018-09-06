package com.pas.cloud.studio.yjgx.bean;

import java.io.Serializable;
import java.util.List;

public class ListTags implements Serializable {

	private List<ListTag> tags;

	public List<ListTag> getTags() {
		return tags;
	}

	public void setTags(List<ListTag> tags) {
		this.tags = tags;
	}

}
