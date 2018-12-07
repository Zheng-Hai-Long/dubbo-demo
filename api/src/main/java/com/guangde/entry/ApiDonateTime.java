package com.guangde.entry;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ApiDonateTime extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6184487109321148490L;
	
	private Integer id;
	private Integer userId;
	private Integer type;
	private Integer notice;
	private Integer number;
	private String category;
	private Integer state;
	private Date createtime;
	private String projectIds;
	private Integer dayNumber;
	public Integer getDayNumber() {
		return dayNumber;
	}
	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}
	/**
	 * 用于转换时间用的
	 */
	private String cTime;
	private Double money;
	/**
	 * 用户电话号码
	 */
	private String mobileNum;
	/**
	 * 类目列表
	 */
	private List<String> categorys;
	
	public String getProjectIds() {
		return projectIds;
	}
	public void setProjectIds(String projectIds) {
		this.projectIds = projectIds;
	}
	public String getCTime() {
		return cTime;
	}
	public void setCTime(String cTime) {
		this.cTime = cTime;
	}
	public List<String> getCategorys() {
		return categorys;
	}
	public void setCategorys(List<String> categorys) {
		this.categorys = categorys;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getNotice() {
		return notice;
	}
	public void setNotice(Integer notice) {
		this.notice = notice;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}

}
