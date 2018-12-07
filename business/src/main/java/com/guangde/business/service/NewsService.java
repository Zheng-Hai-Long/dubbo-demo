package com.guangde.business.service;

import com.guangde.business.entry.News;
import com.guangde.business.entry.NewsCategory;
import com.guangde.business.exception.BaseException;

import java.util.List;

public interface NewsService
{
    void updateNews(News news)
        throws BaseException;
    
    News queryNewsById(Integer id);
    
    List<News> queryNewsListByParam(News news, Integer pageNum, Integer pageSize);
    
    News queryNextRecord(News news);
    
    News queryPreviousRecord(News news);

    List<NewsCategory> queryNewsCategory();

}
