package com.guangde.entry;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 新闻表
 * @author Administrator
 *
 */
public class ApiNews extends BaseBean implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private String abstracts;
    
    private String keywords;
    
    private String content;
    
    private Integer visits;
    
    private Integer top;
    
    private String title;
    
    private String news_column;
    
    private String sub_column;
    
    private String third_column;
    
    private Integer source;
    
    private Integer type;
    
    private Date createtime;
    
    private Date ordertime;
    /**
     * 爱心活动封面图片id
     */
    private Integer coverImageId;
    /**
     * 爱心活动封面图片url
     */
    private String coverImageUrl;
    
    private String contentImageId;
    private List<ApiBFile> bfileList;
    /**专项基金id*/
    private Integer special_fund_id;
    /**来源名称*/
    private String sourceStr;
    /**新闻来源链接*/
    private String url;
    /**作者*/
    private String author;
    
    public Integer getSpecial_fund_id() {
		return special_fund_id;
	}

	public void setSpecial_fund_id(Integer special_fund_id) {
		this.special_fund_id = special_fund_id;
	}

	public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getAbstracts()
    {
        return abstracts;
    }
    
    public void setAbstracts(String abstracts)
    {
        this.abstracts = abstracts;
    }
    
    public String getKeywords()
    {
        return keywords;
    }
    
    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public Integer getVisits()
    {
        return visits;
    }
    
    public void setVisits(Integer visits)
    {
        this.visits = visits;
    }
    
    public Integer getTop()
    {
        return top;
    }
    
    public void setTop(Integer top)
    {
        this.top = top;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getNews_column()
    {
        return news_column;
    }
    
    public void setNews_column(String newsColumn)
    {
        news_column = newsColumn;
    }
    
    public String getSub_column()
    {
        return sub_column;
    }
    
    public void setSub_column(String subColumn)
    {
        sub_column = subColumn;
    }
    
    public String getThird_column()
    {
        return third_column;
    }
    
    public void setThird_column(String thirdColumn)
    {
        third_column = thirdColumn;
    }
    
    public Integer getSource()
    {
        return source;
    }
    
    public void setSource(Integer source)
    {
        this.source = source;
    }
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    public Date getCreatetime()
    {
        return createtime;
    }
    
    public void setCreatetime(Date createtime)
    {
        this.createtime = createtime;
    }
    
    public Date getOrdertime()
    {
        return ordertime;
    }
    
    public void setOrdertime(Date ordertime)
    {
        this.ordertime = ordertime;
    }
    
    public Integer getCoverImageId() {
		return coverImageId;
	}

	public void setCoverImageId(Integer coverImageId) {
		this.coverImageId = coverImageId;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}
	
	public String getContentImageId() {
		return contentImageId;
	}

	public void setContentImageId(String contentImageId) {
		this.contentImageId = contentImageId;
	}

	public List<ApiBFile> getBfileList() {
		return bfileList;
	}

	public void setBfileList(List<ApiBFile> bfileList) {
		this.bfileList = bfileList;
	}

	public String getSourceStr() {
		return sourceStr;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "ApiNews [id=" + id + ", abstracts=" + abstracts + ", keywords="
				+ keywords + ", content=" + content + ", visits=" + visits
				+ ", top=" + top + ", title=" + title + ", news_column="
				+ news_column + ", sub_column=" + sub_column
				+ ", third_column=" + third_column + ", source=" + source
				+ ", type=" + type + ", createtime=" + createtime
				+ ", ordertime=" + ordertime + ", coverImageId=" + coverImageId
				+ ", coverImageUrl=" + coverImageUrl + ", contentImageId="
				+ contentImageId + ", bfileList=" + bfileList
				+ ", special_fund_id=" + special_fund_id + ", sourceStr="
				+ sourceStr + ", url=" + url + ", author=" + author + "]";
	}
    
}
