package com.guangde.home.vo.user;

import com.guangde.entry.ApiFrontUser;

import java.util.Date;

public class CommonUser {

	public CommonUser(ApiFrontUser user){
		this.id = user.getId();
		this.userName = user.getUserName();
		this.nickName = user.getNickName();
		this.coverImageUrl = user.getCoverImageUrl();
		this.realName = user.getRealName();
	}
	
	private Integer id;

	/**
	 * 用户等级
	 */
	private Integer grade;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 头像id
	 */
	private Integer coverImageId;

	/**
	 * 头像地址
	 */
	private String coverImageUrl;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 身份证号
	 */
	private String idCard;

	/**
	 * 手机号码
	 */
	private String mobileNum;

	/**
	 * 最后一次登陆时间
	 */
	private Date lastLoginTime;

	/**
	 * 用户编号
	 */
	private String cardNo;

	private Double donateMoney;

	public Double getDonateMoney() {
		return donateMoney;
	}

	public void setDonateMoney(Double donateMoney) {
		this.donateMoney = donateMoney;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Override
	public String toString() {
		return "CommonUser{" +
				"id=" + id +
				", grade=" + grade +
				", userName='" + userName + '\'' +
				", nickName='" + nickName + '\'' +
				", coverImageId=" + coverImageId +
				", coverImageUrl='" + coverImageUrl + '\'' +
				", realName='" + realName + '\'' +
				", idCard='" + idCard + '\'' +
				", mobileNum='" + mobileNum + '\'' +
				", lastLoginTime=" + lastLoginTime +
				", cardNo='" + cardNo + '\'' +
				", donateMoney=" + donateMoney +
				'}';
	}
}
