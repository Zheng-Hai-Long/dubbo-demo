package com.guangde.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ApiProductShipment extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7734768203150810464L;
	

	private Integer id;
	
	private Integer userId;
	
	private String tranNum;
	
	private Integer productId;
	
	private String productName;
	
	private BigDecimal postage;
	
	private String courierName;
	
	private String courierNum;
	
	private String addresseeAddress;
	
	private Integer state;
	
	private String receiverName;
	
	private String receiverPhone;
	
	private Date shipmentTime;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String userName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTranNum() {
		return tranNum;
	}

	public void setTranNum(String tranNum) {
		this.tranNum = tranNum;
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

	public BigDecimal getPostage() {
		return postage;
	}

	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getCourierNum() {
		return courierNum;
	}

	public void setCourierNum(String courierNum) {
		this.courierNum = courierNum;
	}

	public String getAddresseeAddress() {
		return addresseeAddress;
	}

	public void setAddresseeAddress(String addresseeAddress) {
		this.addresseeAddress = addresseeAddress;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public Date getShipmentTime() {
		return shipmentTime;
	}

	public void setShipmentTime(Date shipmentTime) {
		this.shipmentTime = shipmentTime;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "ApiProductShipment [id=" + id + ", userId=" + userId
				+ ", tranNum=" + tranNum + ", productId=" + productId
				+ ", productName=" + productName + ", postage=" + postage
				+ ", courierName=" + courierName + ", courierNum=" + courierNum
				+ ", addresseeAddress=" + addresseeAddress + ", state=" + state
				+ ", receiverName=" + receiverName + ", receiverPhone="
				+ receiverPhone + ", shipmentTime=" + shipmentTime
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", userName=" + userName + "]";
	}

}
