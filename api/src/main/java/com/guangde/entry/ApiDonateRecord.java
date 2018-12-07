package com.guangde.entry;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 捐款记录表
 *
 */
public class ApiDonateRecord extends BaseBean implements Serializable
{
    
    private static final long serialVersionUID = -1568830649623399315L;

    private Integer id;
    
    private Integer userId;
    
    private Integer capitalinoutId;
    
    private Integer projectId;

    private Double donatAmount;

    private Integer state;
    
    private Date donatTime;
    
    private String donatTimeStr;
    
    private String donatType;

    private Integer donateTog;
    
    private String projectTitle;
    
    private String leaveWord;
    
    /** 
     * 交易号
     */
    private String tranNum;

    /**
     * 用户昵称
     */
    private String nickName;
    
    /**
     * 用户头像id
     */
    private int coverImageId;
    
    /**
     * 用户头像url
     */
    private String coverImageUrl;

    /**
     * 推广人
     */
    private Integer extensionPeople;

    /**
     * 是否匿名
     */
    private Integer nameOpen;

    private String donateSource;

    private List<ApiNewLeaveWord> apiNewLeaveWordList;
    
    /**捐款记录ids*/
    private List<Integer> donateIds;
    
    private String projectImageUrl;

    public List<ApiNewLeaveWord> getApiNewLeaveWordList() {
        return apiNewLeaveWordList;
    }

    public void setApiNewLeaveWordList(List<ApiNewLeaveWord> apiNewLeaveWordList) {
        this.apiNewLeaveWordList = apiNewLeaveWordList;
    }

    public String getDonateSource() {
        return donateSource;
    }

    public void setDonateSource(String donateSource) {
        this.donateSource = donateSource;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getLeaveWord() {
        return leaveWord;
    }

    public void setLeaveWord(String leaveWord) {
        this.leaveWord = leaveWord;
    }

    public String getTranNum() {
        return tranNum;
    }

    public void setTranNum(String tranNum) {
        this.tranNum = tranNum;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getCoverImageId() {
        return coverImageId;
    }

    public void setCoverImageId(int coverImageId) {
        this.coverImageId = coverImageId;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Integer getExtensionPeople() {
        return extensionPeople;
    }

    public void setExtensionPeople(Integer extensionPeople) {
        this.extensionPeople = extensionPeople;
    }

    public Integer getNameOpen() {
        return nameOpen;
    }

    public void setNameOpen(Integer nameOpen) {
        this.nameOpen = nameOpen;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCapitalinoutId() {
        return capitalinoutId;
    }

    public void setCapitalinoutId(Integer capitalinoutId) {
        this.capitalinoutId = capitalinoutId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Double getDonatAmount() {
        return donatAmount;
    }

    public void setDonatAmount(Double donatAmount) {
        this.donatAmount = donatAmount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getDonatTime() {
        return donatTime;
    }

    public void setDonatTime(Date donatTime) {
        this.donatTime = donatTime;
    }

    public String getDonatTimeStr() {
        return donatTimeStr;
    }

    public void setDonatTimeStr(String donatTimeStr) {
        this.donatTimeStr = donatTimeStr;
    }

    public String getDonatType() {
        return donatType;
    }

    public void setDonatType(String donatType) {
        this.donatType = donatType;
    }

    public Integer getDonateTog() {
        return donateTog;
    }

    public void setDonateTog(Integer donateTog) {
        this.donateTog = donateTog;
    }

    public List<Integer> getDonateIds() {
		return donateIds;
	}

	public void setDonateIds(List<Integer> donateIds) {
		this.donateIds = donateIds;
	}

	public String getProjectImageUrl() {
		return projectImageUrl;
	}

	public void setProjectImageUrl(String projectImageUrl) {
		this.projectImageUrl = projectImageUrl;
	}

	@Override
	public String toString() {
		return "ApiDonateRecord [id=" + id + ", userId=" + userId
				+ ", capitalinoutId=" + capitalinoutId + ", projectId="
				+ projectId + ", donatAmount=" + donatAmount + ", state="
				+ state + ", donatTime=" + donatTime + ", donatTimeStr="
				+ donatTimeStr + ", donatType=" + donatType + ", donateTog="
				+ donateTog + ", projectTitle=" + projectTitle + ", leaveWord="
				+ leaveWord + ", tranNum=" + tranNum + ", nickName=" + nickName
				+ ", coverImageId=" + coverImageId + ", coverImageUrl="
				+ coverImageUrl + ", extensionPeople=" + extensionPeople
				+ ", nameOpen=" + nameOpen + ", donateSource=" + donateSource
				+ ", apiNewLeaveWordList=" + apiNewLeaveWordList
				+ ", donateIds=" + donateIds + ", projectImageUrl="
				+ projectImageUrl + "]";
	}
}
