package com.guangde.dto;

import com.guangde.entry.ApiFrontUser_address;
import com.guangde.entry.BaseBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户发票记录表
 * @author Administrator
 *
 */
public class ApiFrontUserInvoiceDTO extends BaseBean implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -7848163355551519111L;
    
    private Integer id;


	private ApiFrontUser_address frontUser_address;

    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 收货地址id
     */
    private Integer addressId;
    private String province;
    
    private String city ;
    
    private String area ; 
    
    private String detailAddress ;
    
    private String name;
    
    private String mobile ;
    /**
     * 开票金额
     */
    private Double invoiceAmount ;
    /**
     * 开票内容
     */
    private String content ;
    /**
     * 是否包邮
     */
    private Integer isFree ;
    
    /**
     * 开票抬头
     */
    private String invoiceHead ; 
    
    /**
     * 快递费用
     */
    private Double mailAmount ;
    /**
     * 快递公司
     */
    private String mailCompany  ;
    /**
     * 发票状态
     */
    private Integer state ;
    /**
     * 快递单号
     */
    private String mailCode ;
    
    private Date createTime ;
    
    private Date lastUpdateTime ;
    /**
     * 描述信息
     */
    private String info  ;
    
    private String nickName;
    /**
     * 待发货数
     */
    private Integer noStateNum;
    /**
     * 发货数
     */
    private Integer yesStateNum;
    /**
     * 用户头像
     */
    private String coverImageUrl;
    
    /**
     * 可开票金额
     */
    private BigDecimal openMoney;

	public ApiFrontUser_address getFrontUser_address() {
		return frontUser_address;
	}

	public void setFrontUser_address(ApiFrontUser_address frontUser_address) {
		this.frontUser_address = frontUser_address;
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
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public Double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsFree() {
		return isFree;
	}
	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}
	public String getInvoiceHead() {
		return invoiceHead;
	}
	public void setInvoiceHead(String invoiceHead) {
		this.invoiceHead = invoiceHead;
	}
	public Double getMailAmount() {
		return mailAmount;
	}
	public void setMailAmount(Double mailAmount) {
		this.mailAmount = mailAmount;
	}
	public String getMailCompany() {
		return mailCompany;
	}
	public void setMailCompany(String mailCompany) {
		this.mailCompany = mailCompany;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMailCode() {
		return mailCode;
	}
	public void setMailCode(String mailCode) {
		this.mailCode = mailCode;
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
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getNoStateNum() {
		return noStateNum;
	}
	public void setNoStateNum(Integer noStateNum) {
		this.noStateNum = noStateNum;
	}
	public Integer getYesStateNum() {
		return yesStateNum;
	}
	public void setYesStateNum(Integer yesStateNum) {
		this.yesStateNum = yesStateNum;
	}
	public String getCoverImageUrl() {
		return coverImageUrl;
	}
	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}
	public BigDecimal getOpenMoney() {
		return openMoney;
	}
	public void setOpenMoney(BigDecimal openMoney) {
		this.openMoney = openMoney;
	}
	@Override
	public String toString() {
		return "ApiFrontUser_invoice [id=" + id + ", userId=" + userId
				+ ", addressId=" + addressId + ", province=" + province
				+ ", city=" + city + ", area=" + area + ", detailAddress="
				+ detailAddress + ", name=" + name + ", mobile=" + mobile
				+ ", invoiceAmount=" + invoiceAmount + ", content=" + content
				+ ", isFree=" + isFree + ", invoiceHead=" + invoiceHead
				+ ", mailAmount=" + mailAmount + ", mailCompany=" + mailCompany
				+ ", state=" + state + ", mailCode=" + mailCode
				+ ", createTime=" + createTime + ", lastUpdateTime="
				+ lastUpdateTime + ", info=" + info + ", nickName=" + nickName
				+ ", noStateNum=" + noStateNum + ", yesStateNum=" + yesStateNum
				+ ", coverImageUrl=" + coverImageUrl + ", openMoney="
				+ openMoney + "]";
	}

    
    
}
