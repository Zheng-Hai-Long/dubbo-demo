package com.guangde.entry;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 报告表
 * @author phx
 *
 */

public class ApiReport extends BaseBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 6013876966816688893L;
    
    private Integer id;
    
    private Integer projectId;
    
    private Integer type;
    
    private String content;
    
    private Integer reportPeople;
    
    private Date operatorTime;
    
    private String reportPeopleName;
    
    /**
     * 内容图片id
     */
    private String contentImageId;
    
    /**
     * 内容图片地址
     */
    private List<String> contentImageUrl;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public Integer getProjectId()
    {
        return projectId;
    }
    
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public Date getOperatorTime()
    {
        return operatorTime;
    }
    
    public void setOperatorTime(Date operatorTime)
    {
        this.operatorTime = operatorTime;
    }
    
    public Integer getReportPeople()
    {
        return reportPeople;
    }
    
    public void setReportPeople(Integer reportPeople)
    {
        this.reportPeople = reportPeople;
    }
    
    public String getReportPeopleName()
    {
        return reportPeopleName;
    }
    
    public void setReportPeopleName(String reportPeopleName)
    {
        this.reportPeopleName = reportPeopleName;
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
    
    @Override
    public String toString()
    {
        return "Report [id=" + id + ", projectId=" + projectId + ", type=" + type + ", content=" + content + ", reportPeople=" + reportPeople + ", operatorTime=" + operatorTime + "]";
    }
    
}
