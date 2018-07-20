package com.pas.cloud.studio.manage.bean;

import java.io.Serializable;

public class ManageTargetSet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String insertBefore;
	private String insertAfter;
	private String deleteBefore;
	private String deleteAfter;
	private String updateBefore;
	private String updateAfter;
	public String getInsertBefore() {
		return insertBefore;
	}
	public void setInsertBefore(String insertBefore) {
		this.insertBefore = insertBefore;
	}
	public String getInsertAfter() {
		return insertAfter;
	}
	public void setInsertAfter(String insertAfter) {
		this.insertAfter = insertAfter;
	}
	public String getDeleteBefore() {
		return deleteBefore;
	}
	public void setDeleteBefore(String deleteBefore) {
		this.deleteBefore = deleteBefore;
	}
	public String getDeleteAfter() {
		return deleteAfter;
	}
	public void setDeleteAfter(String deleteAfter) {
		this.deleteAfter = deleteAfter;
	}
	public String getUpdateBefore() {
		return updateBefore;
	}
	public void setUpdateBefore(String updateBefore) {
		this.updateBefore = updateBefore;
	}
	public String getUpdateAfter() {
		return updateAfter;
	}
	public void setUpdateAfter(String updateAfter) {
		this.updateAfter = updateAfter;
	}
	
}
