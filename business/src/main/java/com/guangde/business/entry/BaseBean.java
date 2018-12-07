package com.guangde.business.entry;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BaseBean
{
    private String orderBy;
    
    private String orderDirection;
    
    private Date queryStartDate;
    
    private Date queryEndDate;

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
    public String getOrderBy()
    {
        return orderBy;
    }
    
    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }
    
    public String getOrderDirection()
    {
        return orderDirection;
    }
    
    public void setOrderDirection(String orderDirection)
    {
        this.orderDirection = orderDirection;
    }
    
    public Date getQueryStartDate()
    {
        return queryStartDate;
    }
    
    public void setQueryStartDate(Date queryStartDate)
    {
        this.queryStartDate = queryStartDate;
    }
    
    public Date getQueryEndDate()
    {
        return queryEndDate;
    }
    
    public void setQueryEndDate(Date queryEndDate)
    {
        this.queryEndDate = queryEndDate;
    }
    


}

