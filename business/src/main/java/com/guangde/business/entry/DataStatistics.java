package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

public class DataStatistics implements Serializable{
	
	private static final long serialVersionUID = 7313204915159307296L;
	
	private Integer id;
	private Integer newProjectNum;
	private Double needMoney;
	private Double donatedMoney;
	private Double payedMoney;
	private Integer successProjectNum;
	private Integer pageView;
	private Integer totalDonationNum;
	private Integer newRegisterNum;
	private Integer newDonationNum;
	private Integer nickDonationNum;
	private Date createTime;
	private String source;
	private Integer reorder;
	private Integer donationPeopleNum;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNewProjectNum() {
		return newProjectNum;
	}
	public void setNewProjectNum(Integer newProjectNum) {
		this.newProjectNum = newProjectNum;
	}
	public Double getNeedMoney() {
		return needMoney;
	}
	public void setNeedMoney(Double needMoney) {
		this.needMoney = needMoney;
	}
	public Double getDonatedMoney() {
		return donatedMoney;
	}
	public void setDonatedMoney(Double donatedMoney) {
		this.donatedMoney = donatedMoney;
	}
	public Double getPayedMoney() {
		return payedMoney;
	}
	public void setPayedMoney(Double payedMoney) {
		this.payedMoney = payedMoney;
	}
	public Integer getSuccessProjectNum() {
		return successProjectNum;
	}
	public void setSuccessProjectNum(Integer successProjectNum) {
		this.successProjectNum = successProjectNum;
	}
	public Integer getPageView() {
		return pageView;
	}
	public void setPageView(Integer pageView) {
		this.pageView = pageView;
	}
	public Integer getTotalDonationNum() {
		return totalDonationNum;
	}
	public void setTotalDonationNum(Integer totalDonationNum) {
		this.totalDonationNum = totalDonationNum;
	}
	public Integer getNewRegisterNum() {
		return newRegisterNum;
	}
	public void setNewRegisterNum(Integer newRegisterNum) {
		this.newRegisterNum = newRegisterNum;
	}
	public Integer getNewDonationNum() {
		return newDonationNum;
	}
	public void setNewDonationNum(Integer newDonationNum) {
		this.newDonationNum = newDonationNum;
	}
	public Integer getNickDonationNum() {
		return nickDonationNum;
	}
	public void setNickDonationNum(Integer nickDonationNum) {
		this.nickDonationNum = nickDonationNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getReorder() {
		return reorder;
	}
	public void setReorder(Integer reorder) {
		this.reorder = reorder;
	}
	public Integer getDonationPeopleNum() {
		return donationPeopleNum;
	}
	public void setDonationPeopleNum(Integer donationPeopleNum) {
		this.donationPeopleNum = donationPeopleNum;
	}
	@Override
	public String toString() {
		return "ActionFunction [id=" + id + ", newProjectNum=" + newProjectNum
				+ ", needMoney=" + needMoney + ", donatedMoney=" + donatedMoney
				+ ", payedMoney=" + payedMoney + ", successProjectNum=" + successProjectNum
				+ ", pageView=" + pageView + ", totalDonationNum=" + totalDonationNum 
				+", newRegisterNum="+newRegisterNum+", newDonationNum="+newDonationNum
				+", nickDonationNum="+nickDonationNum+", createTime="+createTime
				+", source="+source+", reorder="+reorder+", donationPeopleNum="+donationPeopleNum+ "]";
	}
	
	
	
}
