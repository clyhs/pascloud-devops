package com.pas.cloud.studio.yjgx.bean;

import java.io.Serializable;
import java.util.List;

public class Page implements Serializable {

	private static final long serialVersionUID = 8180208765854066885L;
	private String fid;
	private String fname;
	private String version;
	private String desc;
	private String pid;
	
	
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	private Gxb gxb;
	private Gxb rzzb;
	private Gxb rzb;
	private Jxb jxb;
	private List<Expb> expb;
	private MainPart mainPart;
	private Result result;
	private ListTags listTag;

	public ListTags getListTag() {
		return listTag;
	}

	public void setListTag(ListTags listTag) {
		this.listTag = listTag;
	}

	public Gxb getGxb() {
		return gxb;
	}

	public void setGxb(Gxb gxb) {
		this.gxb = gxb;
	}

	public Gxb getRzzb() {
		return rzzb;
	}

	public void setRzzb(Gxb rzzb) {
		this.rzzb = rzzb;
	}

	public Gxb getRzb() {
		return rzb;
	}

	public void setRzb(Gxb rzb) {
		this.rzb = rzb;
	}

	public Jxb getJxb() {
		return jxb;
	}

	public void setJxb(Jxb jxb) {
		this.jxb = jxb;
	}

	public MainPart getMainPart() {
		return mainPart;
	}

	public void setMainPart(MainPart mainPart) {
		this.mainPart = mainPart;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public List<Expb> getExpb() {
		return expb;
	}

	public void setExpb(List<Expb> expb) {
		this.expb = expb;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

}
