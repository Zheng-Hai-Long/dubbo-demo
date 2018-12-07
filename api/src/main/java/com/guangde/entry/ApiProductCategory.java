package com.guangde.entry;

import java.io.Serializable;
import java.util.Date;

public class ApiProductCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7734768203150810464L;
	

	private Integer id;
	
	private String productCategoryName;
	
	private Date createTime;
	
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "productCategory [id=" + id + ", productCategoryName="
				+ productCategoryName + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	
}
