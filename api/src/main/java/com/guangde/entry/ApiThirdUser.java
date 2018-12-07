package com.guangde.entry;

import java.io.Serializable;

/**
 * 第三方登陆用户表
 * 
 * @author phx
 * 
 */

public class ApiThirdUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7980879232925873402L;
	private Integer id;
	private String type;
	private String accountNum;
	private String unionid;//唯一的识别号
	private Integer userId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "ThirdUser [id=" + id + ", type=" + type + ", accountNum="
				+ accountNum + ", userId=" + userId + "]";
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	
}
