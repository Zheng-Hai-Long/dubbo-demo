package com.guangde.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ApiTogetherConfig  extends BaseBean  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4711830082323963889L;
	private Integer id;
	/**个人/团体  personal/group*/
	private String type;
	/**项目ID*/
	private Integer projectId;
	/**发起方*/
	private String launchName;
	/**用户ID*/
	private Integer userId;
	/**目标金额*/
	private BigDecimal totalMoney;
	/**发起说明*/
	private String content;
	/**点击量*/
	private Integer click;
	/**捐赠金额*/
	private BigDecimal donateMoney;
	/**捐赠总金额*/
	private BigDecimal totalDonateMoney;
	/**捐赠次数*/
	private Integer donateNum;
	/**捐赠总次数*/
	private Integer totalDonateNum;
	/**捐赠发起人数*/
	private Integer totalFaqiNum;
	
	/**发起方logo*/
	private Integer coverImageId;
	private Date createTime;
	private Date updateTime;
	/**logo url*/
	private String coverImageUrl;
	/**分享状态 0：未分享；1：已分享*/
	private Integer shareState;
	/**用户昵称*/
	private String nickName;
	/**是否匿名：1匿名，0不匿名*/
	private Integer nameOpen;
	/**成员人数*/
	private Integer teamPlayNum;
	/**限制人数*/
	private Integer teamPlayNumLimit;
	/**团队Id*/
	private Integer teamId;
	/**团队：100；队长：200；队员：300；其他：400*/
	private Integer teamState;
	/**点赞数*/
	private Integer praise;
	/**捐款百分比*/
	private String processbfb;
	/**是否隐藏：0：不隐藏；1：隐藏*/
	private Integer isHide;

	public String getProcessbfb() {
		return processbfb;
	}

	public void setProcessbfb(String processbfb) {
		this.processbfb = processbfb;
	}

	public Integer getNameOpen() {
		return nameOpen;
	}
	public void setNameOpen(Integer nameOpen) {
		this.nameOpen = nameOpen;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getShareState() {
		return shareState;
	}
	public void setShareState(Integer shareState) {
		this.shareState = shareState;
	}
	public String getCoverImageUrl() {
		return coverImageUrl;
	}
	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}
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
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getLaunchName() {
		return launchName;
	}
	public void setLaunchName(String launchName) {
		this.launchName = launchName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getClick() {
		return click;
	}
	public void setClick(Integer click) {
		this.click = click;
	}
	public BigDecimal getDonateMoney() {
		return donateMoney;
	}
	public void setDonateMoney(BigDecimal donateMoney) {
		this.donateMoney = donateMoney;
	}
	public Integer getDonateNum() {
		return donateNum;
	}
	public void setDonateNum(Integer donateNum) {
		this.donateNum = donateNum;
	}
	public Integer getCoverImageId() {
		return coverImageId;
	}
	public void setCoverImageId(Integer coverImageId) {
		this.coverImageId = coverImageId;
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
	public BigDecimal getTotalDonateMoney() {
		return totalDonateMoney;
	}
	public void setTotalDonateMoney(BigDecimal totalDonateMoney) {
		this.totalDonateMoney = totalDonateMoney;
	}
	public Integer getTotalDonateNum() {
		return totalDonateNum;
	}
	public void setTotalDonateNum(Integer totalDonateNum) {
		this.totalDonateNum = totalDonateNum;
	}
	public Integer getTotalFaqiNum() {
		return totalFaqiNum;
	}
	public void setTotalFaqiNum(Integer totalFaqiNum) {
		this.totalFaqiNum = totalFaqiNum;
	}
	public Integer getTeamPlayNum() {
		return teamPlayNum;
	}
	public void setTeamPlayNum(Integer teamPlayNum) {
		this.teamPlayNum = teamPlayNum;
	}
	public Integer getTeamPlayNumLimit() {
		return teamPlayNumLimit;
	}
	public void setTeamPlayNumLimit(Integer teamPlayNumLimit) {
		this.teamPlayNumLimit = teamPlayNumLimit;
	}
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public Integer getTeamState() {
		return teamState;
	}
	public void setTeamState(Integer teamState) {
		this.teamState = teamState;
	}
	public Integer getPraise() {
		return praise;
	}
	public void setPraise(Integer praise) {
		this.praise = praise;
	}
	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	@Override
	public String toString() {
		return "ApiTogetherConfig [id=" + id + ", type=" + type
				+ ", projectId=" + projectId + ", launchName=" + launchName
				+ ", userId=" + userId + ", totalMoney=" + totalMoney
				+ ", content=" + content + ", click=" + click
				+ ", donateMoney=" + donateMoney + ", totalDonateMoney="
				+ totalDonateMoney + ", donateNum=" + donateNum
				+ ", totalDonateNum=" + totalDonateNum + ", totalFaqiNum="
				+ totalFaqiNum + ", coverImageId=" + coverImageId
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", coverImageUrl=" + coverImageUrl + ", shareState="
				+ shareState + ", nickName=" + nickName + ", nameOpen="
				+ nameOpen + ", teamPlayNum=" + teamPlayNum
				+ ", teamPlayNumLimit=" + teamPlayNumLimit + ", teamId="
				+ teamId + ", teamState=" + teamState + ", praise=" + praise
				+ ", processbfb=" + processbfb + ", isHide=" + isHide + "]";
	}
	
}
