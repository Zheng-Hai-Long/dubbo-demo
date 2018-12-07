package com.guangde.business.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 竞拍项目表
 * @author Administrator
 *
 */
public class AuctionProject extends BaseBean implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -3737314003232993609L;
    
    private Integer id;
        
    /**
     * 标题
     */
    private String title;
    
    /**
     * 内容
     */
    private String content;
    
    /**
     * 状态
     */
    private Integer state;
    
    /**
     * 注册时间
     */
    private Date registrTime;
    

    /**
     * 项目截止时间
     */
    private Date deadline;
 
    /**
     * 项目标题图
     */
    private String coverImageUrl;
    
    /**
     * 项目标题图 h5
     */
    private String coverImageUrlMiddle;
    
    /**
     * 图片描述
     */
    private String coverImageDecription;
    
    /**
     * 内容图片
     */
    private List<BFile> bfileList = new ArrayList<BFile>();
    
    private Integer coverImageId;
    
    /**
     * 内容图片ID，多个ID以逗号分隔
     */
    private String contentImageId;
    
    /**
     * 人次
     */
    private Integer number;
    /**
     * 人数
     */
    private Integer peopleNum;
    /**
     * 当前价
     */
    private double currentPrice ;
    
    /**
     * 起拍价
     */
    private double reservePrice;
    
    private String spare ;
    /**
     * 归属人userId
     */
    private Integer belong;
    /**
     * 状态集合
     */
    private List<Integer> states;
    /**
     * 隐藏 ：1， 显示：0
     */
    private Integer isHide;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 捐款的项目Id
     */
    private Integer projectId;
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getRegistrTime() {
		return registrTime;
	}

	public void setRegistrTime(Date registrTime) {
		this.registrTime = registrTime;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getCoverImageUrlMiddle() {
		return coverImageUrlMiddle;
	}

	public void setCoverImageUrlMiddle(String coverImageUrlMiddle) {
		this.coverImageUrlMiddle = coverImageUrlMiddle;
	}

	public String getCoverImageDecription() {
		return coverImageDecription;
	}

	public void setCoverImageDecription(String coverImageDecription) {
		this.coverImageDecription = coverImageDecription;
	}

	public List<BFile> getBfileList() {
		return bfileList;
	}

	public void setBfileList(List<BFile> bfileList) {
		this.bfileList = bfileList;
	}

	public Integer getCoverImageId() {
		return coverImageId;
	}

	public void setCoverImageId(Integer coverImageId) {
		this.coverImageId = coverImageId;
	}

	public String getContentImageId() {
		return contentImageId;
	}

	public void setContentImageId(String contentImageId) {
		this.contentImageId = contentImageId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public double getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(double reservePrice) {
		this.reservePrice = reservePrice;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

	@Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AuctionProject other = (AuctionProject)obj;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}

	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	public List<Integer> getStates() {
		return states;
	}

	public void setStates(List<Integer> states) {
		this.states = states;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getBelong() {
		return belong;
	}

	public void setBelong(Integer belong) {
		this.belong = belong;
	}
    
}
