package com.guangde.business.pojo;

import java.io.Serializable;

public class FocusMap implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3305100499939426914L;

	/**
	 * 图片URL
	 */
	private String pictureURL;
	
	private String title;
	
	/**
	 * 跳转地址
	 */
	private String jumpURL;
	
	public String getPictureURL() {
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJumpURL() {
		return jumpURL;
	}
	public void setJumpURL(String jumpURL) {
		this.jumpURL = jumpURL;
	}
	
	
}
