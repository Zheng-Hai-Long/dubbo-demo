package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

public class ProductShipmentInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7734768203150810464L;
	

	private Integer id;
	
	private Integer shipmentId;
	
	private Integer productId;
	
	private String productName;
	
	private Integer shipmentNum;
	
	private Date createTime;

	private String productSpecificationName;

	private Integer productSpecificationId;

	public String getProductSpecificationName() {
		return productSpecificationName;
	}

	public void setProductSpecificationName(String productSpecificationName) {
		this.productSpecificationName = productSpecificationName;
	}

	public Integer getProductSpecificationId() {
		return productSpecificationId;
	}

	public void setProductSpecificationId(Integer productSpecificationId) {
		this.productSpecificationId = productSpecificationId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Integer shipmentId) {
		this.shipmentId = shipmentId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getShipmentNum() {
		return shipmentNum;
	}

	public void setShipmentNum(Integer shipmentNum) {
		this.shipmentNum = shipmentNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ProductShipmentInfo [id=" + id + ", shipmentId=" + shipmentId
				+ ", productId=" + productId + ", productName=" + productName
				+ ", shipmentNum=" + shipmentNum + ", createTime=" + createTime
				+ "]";
	}

}
