package com.guangde.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ApiProductOrder extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7734768203150810464L;
	

	private Integer id;
	
	private Integer userId;
	
	private String tranNum;
	
	private Integer productId;
	
	private String productName;
	
	private BigDecimal price;
	
	private BigDecimal amountMoney;
	
	private Integer stockNum;
	
	private Integer state;
	
	private String leaveWord;
	
	private Date createTime;
	
	private Date updateTime;

	private Integer shipmentNum;

	private ApiProductShipment shipment;

	private String productSpecificationName;

	private Integer coverImageId;

	private String coverImageUrl;

	private Integer productSpecificationId;
	
	private String coverImageUrlMiddle;

	public Integer getProductSpecificationId() {
		return productSpecificationId;
	}

	public void setProductSpecificationId(Integer productSpecificationId) {
		this.productSpecificationId = productSpecificationId;
	}

	public Integer getCoverImageId() {
		return coverImageId;
	}

	public void setCoverImageId(Integer coverImageId) {
		this.coverImageId = coverImageId;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getProductSpecificationName() {
		return productSpecificationName;
	}

	public void setProductSpecificationName(String productSpecificationName) {
		this.productSpecificationName = productSpecificationName;
	}

	public ApiProductShipment getShipment() {
		return shipment;
	}

	public void setShipment(ApiProductShipment shipment) {
		this.shipment = shipment;
	}

	public Integer getShipmentNum() {
		return shipmentNum;
	}

	public void setShipmentNum(Integer shipmentNum) {
		this.shipmentNum = shipmentNum;
	}
	
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmountMoney() {
		return amountMoney;
	}

	public void setAmountMoney(BigDecimal amountMoney) {
		this.amountMoney = amountMoney;
	}

	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getLeaveWord() {
		return leaveWord;
	}

	public void setLeaveWord(String leaveWord) {
		this.leaveWord = leaveWord;
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

	public String getCoverImageUrlMiddle() {
		return coverImageUrlMiddle;
	}

	public void setCoverImageUrlMiddle(String coverImageUrlMiddle) {
		this.coverImageUrlMiddle = coverImageUrlMiddle;
	}

	@Override
	public String toString() {
		return "ApiProductOrder [id=" + id + ", userId=" + userId
				+ ", tranNum=" + tranNum + ", productId=" + productId
				+ ", productName=" + productName + ", price=" + price
				+ ", amountMoney=" + amountMoney + ", stockNum=" + stockNum
				+ ", state=" + state + ", leaveWord=" + leaveWord
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", shipmentNum=" + shipmentNum + ", shipment=" + shipment
				+ ", productSpecificationName=" + productSpecificationName
				+ ", coverImageId=" + coverImageId + ", coverImageUrl="
				+ coverImageUrl + ", productSpecificationId="
				+ productSpecificationId + ", coverImageUrlMiddle="
				+ coverImageUrlMiddle + "]";
	}

}
