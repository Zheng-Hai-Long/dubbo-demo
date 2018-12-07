package com.guangde.homepage.facade.impl;

import com.github.pagehelper.Page;
import com.guangde.api.homepage.INewsFacade;
import com.guangde.business.entry.News;
import com.guangde.business.entry.NewsCategory;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.NewsService;
import com.guangde.business.util.CollectionUtil;
import com.guangde.dto.NewsDTO;
import com.guangde.entry.ApiNews;
import com.guangde.enums.ResultEnum;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.guangde.util.ApiResultUtil;
import com.guangde.util.BeanUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("newsFacade")
public class NewsFacadeImpl implements INewsFacade
{
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private NewsService newsService;
    
    @Override
    public NewsDTO queryNewsDetail(Integer id)
    {
        logger.info("receive queryNewsDetail param  :  id = " + id);
        News news = newsService.queryNewsById(id);
        if(news == null){
            return null;
        }
        NewsDTO newsDTO = new NewsDTO();
        BeanUtils.copyProperties(news,newsDTO);
        return newsDTO;
    }
    
    @Override
    public ApiPage<NewsDTO> queryNewsList(ApiNews apiNews, Integer pageNum, Integer pageSize)
    {
        logger.info("receive queryNewsList param : " + apiNews);
        if (apiNews == null)
        {
            return new ApiPage<NewsDTO>(pageNum, pageSize);
        }
        News news = BeanUtil.copy(apiNews, News.class);
        List<News> list = newsService.queryNewsListByParam(news, pageNum, pageSize);

        ApiPage<NewsDTO> ret = BeanUtil.copyPage((Page<News>)list, NewsDTO.class);
        
        return ret;
    }

    @Override
    public ApiResult queryNewsCategory() {
        List<NewsCategory> newsCategories = newsService.queryNewsCategory();
        if(CollectionUtil.isEmpty(newsCategories)){
            return new ApiResult(ResultEnum.EmptyData);
        }
        return new ApiResult(ResultEnum.Success, newsCategories);
    }

    @Override
    public ApiResult updateVisits(ApiNews apiNews)
    {
        logger.info("receive updateVisits param : " + apiNews);
        if (!valParam(apiNews))
        {
            return ApiResultUtil.getParameterError();
        }
        try
        {
            News news = BeanUtil.copy(apiNews, News.class);
            newsService.updateNews(news);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }
    
    public boolean valParam(ApiNews apiNews)
    {
        boolean ret = true;
        if (apiNews == null)
        {
            logger.info("apiNews is null");
            ret = false;
        }
        else if (apiNews.getId() == null)
        {
            logger.info("id is null");
            ret = false;
        }
        return ret;
    }
}
