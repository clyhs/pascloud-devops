package com.pascloud.module.kettle.bean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;

/**
 * 
 * @author clyhs
 *
 */
public class RepositoryTree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String path;
	
	//private ObjectId objId;
	
	//private RepositoryDirectoryInterface rdi;
	
	private List<RepositoryTree> childrens = new ArrayList<RepositoryTree>();
	
	private List<String> filenames = new ArrayList<String>();
	
	
	

	public List<String> getFilenames() {
		return filenames;
	}

	public void setFilenames(List<String> filenames) {
		this.filenames = filenames;
	}

	public List<RepositoryTree> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<RepositoryTree> childrens) {
		this.childrens = childrens;
	}

//	public ObjectId getObjId() {
//		return objId;
//	}
//
//	public void setObjId(ObjectId objId) {
//		this.objId = objId;
//	}
//
//	public RepositoryDirectoryInterface getRdi() {
//		return rdi;
//	}
//
//	public void setRdi(RepositoryDirectoryInterface rdi) {
//		this.rdi = rdi;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	

}