package com.guangde.business.dao;

import com.guangde.business.entry.News;

public interface NewsMapper extends BaseMapper<News>
{
    News queryNextNews(News news);
    
    News queryPreviousNews(News news);
}
