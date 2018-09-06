package com.pas.cloud.studio.yjgx.bean;

import java.io.Serializable;
import java.util.List;

public class MainPart implements Serializable {

	private String javascript;
	private List<Tag> tags;

	public String getJavascript() {
		return javascript;
	}

	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
}
