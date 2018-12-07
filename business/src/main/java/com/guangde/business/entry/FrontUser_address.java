package com.guangde.business.entry;

import java.io.Serializable;

import java.util.Date;

/**
 * 用户地址表
 * @author Administrator
 *
 */
public class FrontUser_address extends BaseBean implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -7848163355551519111L;
    
    private Integer id;

    
    /**
     * 用户id
     */
    private Integer userId;
    
    
    /**
     * 收货人联系号码
     */
    private String mobile ; 
    /**
     * 收货人姓名
     */
    private String name  ;
    
    private String province ; 
    
    private String city ;
    
    private String area ; 
    
    private String detailAddress ; 

    private Date createTime ;
    
    private Date lastUpdateTime ;
    
    private String email ;
    
    /**
     * 省市区  代号
     */
    private String code ; 
    
    /**
     * 是否默认 选中  0 是  1不是
     */
    private Integer isSelected ; 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Integer isSelected) {
		this.isSelected = isSelected;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Override
	public String toString() {
		return "FrontUser_giftRecord [area=" + area + ", city=" + city
				+ ", createTime=" + createTime + ", detailAddress="
				+ detailAddress + ", id=" + id + ", lastUpdateTime="
				+ lastUpdateTime + ", mobile=" + mobile + ", name=" + name
				+ ", province=" + province + ", userId=" + userId + "]";
	}

	
    
}
