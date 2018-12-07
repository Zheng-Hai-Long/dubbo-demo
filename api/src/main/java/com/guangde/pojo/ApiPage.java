package com.guangde.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApiPage<T> implements Serializable
{
    private static final long serialVersionUID = -1489935560666654036L;

    public final static int PAGE_DEFAULT_COUNT = 20;
    
    /**
     * 当前页码
     */
    private int pageNum;
    
    /**
     * 每页行数
     */
    private int pageSize;
    
    
    /**
     * 总记录数
     */
    private long total;
    
    /**
     * 总页数
     */
    private int pages;
    
    private List<T> resultData = new ArrayList<T>();
    
    public ApiPage()
    {
        
    }
    
    public ApiPage(int pageNum, int pageSize)
    {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
    
    public int getPageNum()
    {
        return pageNum;
    }
    
    public void setPageNum(int pageNum)
    {
        this.pageNum = pageNum;
    }
    
    public int getPageSize()
    {
        return pageSize > 0 ? pageSize : PAGE_DEFAULT_COUNT;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public long getTotal()
    {
        return total;
    }
    
    public void setTotal(long total)
    {
        this.total = total;
    }
    
    public int getPages()
    {
        return pages;
    }
    
    public void setPages(int pages)
    {
        this.pages = pages;
    }
    
    public List<T> getResultData()
    {
        return resultData;
    }
    
    public void setResultData(List<T> resultData)
    {
        this.resultData = resultData;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public boolean valPage(ApiPage<T> t, int pageNum){
        if(t != null && t.getTotal() > 0 && t.getPages() >= pageNum){
            return true;
        }
        return false;
    }
}

