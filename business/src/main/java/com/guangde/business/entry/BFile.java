package com.guangde.business.entry;

import java.io.Serializable;

public class BFile extends BaseBean implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -4763361151768823459L;
    
    private Integer id;
    
    private String fileType;
    
    private String category;
    /**
     * 大 图地址
     */
    private String url;
    
    /**
     * 中图地址
     */
    private String middleUrl;
    /**
     * 小图地址
     */
    private String litterUrl;
    /**
     * 链接地址
     */
    private String linkUrl ;
    
    private String description;
    
    /**
    * 是否隐藏
    */
    private Integer isHide;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getMiddleUrl() {
		return middleUrl;
	}

	public void setMiddleUrl(String middleUrl) {
		this.middleUrl = middleUrl;
	}

	public String getLitterUrl() {
		return litterUrl;
	}

	public void setLitterUrl(String litterUrl) {
		this.litterUrl = litterUrl;
	}

	public Integer getIsHide()
    {
        return isHide;
    }
    
    public void setIsHide(Integer isHide)
    {
        this.isHide = isHide;
    }
    
    public String getFileType()
    {
        return fileType;
    }
    
    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
}
