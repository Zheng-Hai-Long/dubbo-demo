package com.guangde.dto;

import java.io.Serializable;

/**
 * 口令注册成功后返回信息
 *      
 */
public class ApiFrontUserDto implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    private String userName;
    
    private String project_title;
    
    /**
     * 注册助捐的金额
     */
    private Double perMoney;
    
    private Integer projectId;
    
    private Integer companyId;
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public Integer getProjectId()
    {
        return projectId;
    }
    
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }
    
    public Integer getCompanyId()
    {
        return companyId;
    }
    
    public void setCompanyId(Integer companyId)
    {
        this.companyId = companyId;
    }
    
    public String getProject_title()
    {
        return project_title;
    }
    
    public void setProject_title(String projectTitle)
    {
        project_title = projectTitle;
    }
    
    public Double getPerMoney()
    {
        return perMoney;
    }
    
    public void setPerMoney(Double perMoney)
    {
        this.perMoney = perMoney;
    }
    
}
