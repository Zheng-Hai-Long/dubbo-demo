package com.guangde.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目反馈
 * @author phx
 *
 */

public class ApiProjectFeedback extends BaseBean implements Serializable
{
	
    private static final long serialVersionUID = -5396723439483242758L;
    
    static{
    	DEFAULT_RANGE_KEYS = new ArrayList<String>();
    	DEFAULT_RANGE_KEYS.add(getClassName()+"_range_key_"+RANGE_ALL);
    }
    
    private Integer id;
    
    public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	private Integer projectId;
    
    private Integer feedbackPeople;
    
    private String content;
    
    private Date feedbackTime;
    
    private Integer audit;
    
    private Integer auditState;
    
    /**
     * 内容图片id
     */
    private String contentImageId;
    
    /**
     * 内容图片地址(原图)
     */
    private List<String> contentImageUrl;

    /**
     * 内容图片地址集合
     */
    private List<List<String>> contentImageUrls;

    /**
     * 内容图片地址(中图)
     */
    private List<String> contentImageMiddleUrl;

    /**
     * 内容图片地址(小图)
     */
    private List<String> contentImageLittleUrl;
    
    /**
     * 反馈人
     */
    private String userName;
    
    private String nickName;
    
    /**
     * 反馈人头像地址
     */
    private String  headImageUrl ; 
    /**
     * 反馈人头像id
     */
    private Integer headImageId ; 
    /**
     * 反馈人角色
     */
    private Integer userType;
    
    /**
     * 项目标题
     */
    private String title ;
    
    /**
     * 项目领域
     */
    private String field;
    
    /**
     * 来源
     */
    private String source ;
    

    
    private Integer activityId;
    
    private Integer praise;
    
    /**
     * 项目封面图
     */
    private Integer projectImageId;
    /**
     * 项目封面图地址(原图)
     */
    private String projectImageUrl;

    /**
     * 项目封面图地址(中图)
     */
    private String projectImageMiddelUrl;

    /**
     * 项目封面图地址(小图)
     */
    private String projectImageLittleUrl;

    private List<ApiNewLeaveWord> apiNewLeaveWordList;

    public List<ApiNewLeaveWord> getApiNewLeaveWordList() {
        return apiNewLeaveWordList;
    }

    public void setApiNewLeaveWordList(List<ApiNewLeaveWord> apiNewLeaveWordList) {
        this.apiNewLeaveWordList = apiNewLeaveWordList;
    }

    public List<List<String>> getContentImageUrls() {
        return contentImageUrls;
    }

    public void setContentImageUrls(List<List<String>> contentImageUrls) {
        this.contentImageUrls = contentImageUrls;
    }

    public String getProjectImageMiddelUrl() {
        return projectImageMiddelUrl;
    }

    public void setProjectImageMiddelUrl(String projectImageMiddelUrl) {
        this.projectImageMiddelUrl = projectImageMiddelUrl;
    }

    public String getProjectImageLittleUrl() {
        return projectImageLittleUrl;
    }

    public void setProjectImageLittleUrl(String projectImageLittleUrl) {
        this.projectImageLittleUrl = projectImageLittleUrl;
    }

    public List<String> getContentImageMiddleUrl() {
        return contentImageMiddleUrl;
    }

    public void setContentImageMiddleUrl(List<String> contentImageMiddleUrl) {
        this.contentImageMiddleUrl = contentImageMiddleUrl;
    }

    public List<String> getContentImageLittleUrl() {
        return contentImageLittleUrl;
    }

    public void setContentImageLittleUrl(List<String> contentImageLittleUrl) {
        this.contentImageLittleUrl = contentImageLittleUrl;
    }

    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}

	public Integer getHeadImageId() {
		return headImageId;
	}

	public void setHeadImageId(Integer headImageId) {
		this.headImageId = headImageId;
	}


    
    public Integer getProjectId()
    {
        return projectId;
    }
    
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }
    
    public Integer getFeedbackPeople()
    {
        return feedbackPeople;
    }
    
    public void setFeedbackPeople(Integer feedbackPeople)
    {
        this.feedbackPeople = feedbackPeople;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public Date getFeedbackTime()
    {
        return feedbackTime;
    }
    
    public void setFeedbackTime(Date feedbackTime)
    {
        this.feedbackTime = feedbackTime;
    }
    
    public Integer getAudit()
    {
        return audit;
    }
    
    public void setAudit(Integer audit)
    {
        this.audit = audit;
    }
    
    public Integer getAuditState()
    {
        return auditState;
    }
    
    public void setAuditState(Integer auditState)
    {
        this.auditState = auditState;
    }
    
    public String getContentImageId()
    {
        return contentImageId;
    }
    
    public void setContentImageId(String contentImageId)
    {
        this.contentImageId = contentImageId;
    }
    
    public List<String> getContentImageUrl()
    {
        return contentImageUrl;
    }
    
    public void setContentImageUrl(List<String> contentImageUrl)
    {
        this.contentImageUrl = contentImageUrl;
    }
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

	public Integer getProjectImageId() {
		return projectImageId;
	}

	public void setProjectImageId(Integer projectImageId) {
		this.projectImageId = projectImageId;
	}

	public String getProjectImageUrl() {
		return projectImageUrl;
	}

	public void setProjectImageUrl(String projectImageUrl) {
		this.projectImageUrl = projectImageUrl;
	}

	@Override
	public String toString() {
		return "ApiProjectFeedback [id=" + id + ", projectId=" + projectId
				+ ", feedbackPeople=" + feedbackPeople + ", content=" + content
				+ ", feedbackTime=" + feedbackTime + ", audit=" + audit
				+ ", auditState=" + auditState + ", contentImageId="
				+ contentImageId + ", contentImageUrl=" + contentImageUrl
				+ ", userName=" + userName + ", nickName=" + nickName
				+ ", headImageUrl=" + headImageUrl + ", headImageId="
				+ headImageId + ", userType=" + userType + ", title=" + title
				+ ", field=" + field + ", source=" + source
				+ activityId + ", praise=" + praise + ", projectImageId="
				+ projectImageId + ", projectImageUrl=" + projectImageUrl + "]";
	}
    
}
