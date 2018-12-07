package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目志愿者
 * @author Administrator
 *
 */
public class ProjectVolunteer implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private Integer projectId;
    
    private String name;
    
    private String sex;
    private String birthday ;
    /**
     * 所在地
     */
    private String location;
    
    private String indentity;
    
    private String mobile;
    /**
     * 职业
     */
    private String position;
    
    private String email;
    

    private String serviceTime;
    
    private String historyService;
    /**
     * 人员类型   ： 0 ：个人  1 ： 组织 
     */
    private Integer personType;
    
    private String groupName;

    private String groupType;
    
    private Integer number;
    
    private String linkMan ;
    
    private String address;
    
    private Integer status ;
    
    private Date createTime;
    /**
     * 项目标题
     */
    private String pTitle;
    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 领域
     */
    private String field;
    
    /**
     *签到次数 
     */
    private Integer countSign;
    
    /**
     * 累计时长
     */
    private Integer totalTimeNum;

	/**
	 * 文化程度
	 */
	private String education;

	/**
	 * 技能/兴趣/特长/优势
	 */
	private String skill;

	/**
	 * 备注
	 */
	private String remark;

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getTotalTimeNum() {
		return totalTimeNum;
	}

	public void setTotalTimeNum(Integer totalTimeNum) {
		this.totalTimeNum = totalTimeNum;
	}

    /**
     * 志愿者报名信息
     */
    private String information;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getCountSign() {
		return countSign;
	}

	public void setCountSign(Integer countSign) {
		this.countSign = countSign;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getIndentity() {
		return indentity;
	}

	public void setIndentity(String indentity) {
		this.indentity = indentity;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getHistoryService() {
		return historyService;
	}

	public void setHistoryService(String historyService) {
		this.historyService = historyService;
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getpTitle() {
		return pTitle;
	}

	public void setpTitle(String pTitle) {
		this.pTitle = pTitle;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	@Override
	public String toString() {
		return "ProjectVolunteer{" +
				"id=" + id +
				", projectId=" + projectId +
				", name='" + name + '\'' +
				", sex='" + sex + '\'' +
				", birthday='" + birthday + '\'' +
				", location='" + location + '\'' +
				", indentity='" + indentity + '\'' +
				", mobile='" + mobile + '\'' +
				", position='" + position + '\'' +
				", email='" + email + '\'' +
				", serviceTime='" + serviceTime + '\'' +
				", historyService='" + historyService + '\'' +
				", personType=" + personType +
				", groupName='" + groupName + '\'' +
				", groupType='" + groupType + '\'' +
				", number=" + number +
				", linkMan='" + linkMan + '\'' +
				", address='" + address + '\'' +
				", status=" + status +
				", createTime=" + createTime +
				", pTitle='" + pTitle + '\'' +
				", userId=" + userId +
				", field='" + field + '\'' +
				", countSign=" + countSign +
				", totalTimeNum=" + totalTimeNum +
				", education='" + education + '\'' +
				", skill='" + skill + '\'' +
				", remark='" + remark + '\'' +
				", information='" + information + '\'' +
				'}';
	}


}
