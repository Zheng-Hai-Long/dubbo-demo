package com.guangde.entry;

import java.io.Serializable;
import java.util.Date;

public class ApiPayConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6514749111248290654L;
	private Integer id;
	/**
	 * 商户userId
	 */
	private Integer action_userId;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 商户名称
	 */
	private String name;
	/**
	 * weixinAppId
	 */
	private String weixinAppId;
	/**
	 * weixinAppSecret
	 */
	private String weixinAppSecret;
	/**
	 * weixin商户号
	 */
	private String weixinPartner;
	/**
	 * weixinAPI秘钥
	 */
	private String weixinPartnerKey;
	/**
	 * 支付宝商户号
	 */
	private String alipayPartner;
	/**
	 * 支付宝收款账号
	 */
	private String alipaySellerEmail;
	/**
	 * 支付宝私钥
	 */
	private String alipayPrivateKey;
	/**
	 * 支付宝公钥
	 */
	private String alipayPublicKey;
	/**
	 * 创建时间
	 */
	private Date createtime;
    
   
	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Date getCreatetime() {
		return createtime;
	}


	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getAction_userId() {
		return action_userId;
	}


	public void setAction_userId(Integer action_userId) {
		this.action_userId = action_userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getWeixinAppId() {
		return weixinAppId;
	}


	public void setWeixinAppId(String weixinAppId) {
		this.weixinAppId = weixinAppId;
	}


	public String getWeixinAppSecret() {
		return weixinAppSecret;
	}


	public void setWeixinAppSecret(String weixinAppSecret) {
		this.weixinAppSecret = weixinAppSecret;
	}


	public String getWeixinPartner() {
		return weixinPartner;
	}


	public void setWeixinPartner(String weixinPartner) {
		this.weixinPartner = weixinPartner;
	}


	public String getWeixinPartnerKey() {
		return weixinPartnerKey;
	}


	public void setWeixinPartnerKey(String weixinPartnerKey) {
		this.weixinPartnerKey = weixinPartnerKey;
	}


	public String getAlipayPartner() {
		return alipayPartner;
	}


	public void setAlipayPartner(String alipayPartner) {
		this.alipayPartner = alipayPartner;
	}


	public String getAlipaySellerEmail() {
		return alipaySellerEmail;
	}


	public void setAlipaySellerEmail(String alipaySellerEmail) {
		this.alipaySellerEmail = alipaySellerEmail;
	}


	public String getAlipayPrivateKey() {
		return alipayPrivateKey;
	}


	public void setAlipayPrivateKey(String alipayPrivateKey) {
		this.alipayPrivateKey = alipayPrivateKey;
	}


	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}


	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}


	@Override
	public String toString() {
		return "ActionFunction [id=" + id + ", action_userId=" + action_userId
				+ ", name=" + name + ", weixinAppId=" + weixinAppId
				+ ", weixinAppSecret=" + weixinAppSecret + ", weixinPartnerKey=" + weixinPartnerKey 
				+ ", alipayPartner="+alipayPartner+", alipaySellerEmail="+alipaySellerEmail
				+", alipayPrivateKey="+alipayPrivateKey+", alipayPublicKey="+alipayPublicKey+"]";
	}
}
