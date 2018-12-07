package com.guangde.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ApiProduct extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7734768203150810464L;
	

	private Integer id;
	
	private String productName;
	
	private BigDecimal productPrice;
	
	private String productContent;
	
	private BigDecimal salesMoney;
	
	private Integer salesNum;
	
	private Integer stockNum;
	
	private Integer coverImageId;
	
	private String coverImageUrl;
	
	private Date createTime;
	
	private Date updateTime;
	
	private Integer isGrounding;
	
	private Integer isHide;
	
	private Integer productCategoryId;
	
	private String productCategoryName;

	private List<ApiProductSpecification> apiProductSpecificationList;


	public List<ApiProductSpecification> getApiProductSpecificationList() {
		return apiProductSpecificationList;
	}

	public void setApiProductSpecificationList(List<ApiProductSpecification> apiProductSpecificationList) {
		this.apiProductSpecificationList = apiProductSpecificationList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductContent() {
		return productContent;
	}

	public void setProductContent(String productContent) {
		this.productContent = productContent;
	}

	public BigDecimal getSalesMoney() {
		return salesMoney;
	}

	public void setSalesMoney(BigDecimal salesMoney) {
		this.salesMoney = salesMoney;
	}

	public Integer getSalesNum() {
		return salesNum;
	}

	public void setSalesNum(Integer salesNum) {
		this.salesNum = salesNum;
	}

	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
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

	public Integer getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Integer productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Integer getIsGrounding() {
		return isGrounding;
	}

	public void setIsGrounding(Integer isGrounding) {
		this.isGrounding = isGrounding;
	}

	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	@Override
	public String toString() {
		return "ApiProduct [id=" + id + ", productName=" + productName
				+ ", productPrice=" + productPrice + ", productContent="
				+ productContent + ", salesMoney=" + salesMoney + ", salesNum="
				+ salesNum + ", stockNum=" + stockNum + ", coverImageId="
				+ coverImageId + ", coverImageUrl=" + coverImageUrl
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", isGrounding=" + isGrounding + ", isHide=" + isHide
				+ ", productCategoryId=" + productCategoryId
				+ ", productCategoryName=" + productCategoryName + "]";
	}
	
}
