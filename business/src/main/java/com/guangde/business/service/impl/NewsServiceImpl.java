package com.guangde.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.BFileMapper;
import com.guangde.business.dao.NewsCategoryMapper;
import com.guangde.business.dao.NewsMapper;
import com.guangde.business.entry.BFile;
import com.guangde.business.entry.News;
import com.guangde.business.entry.NewsCategory;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.NewsService;
import com.guangde.business.util.ConfigLoader;
import com.guangde.business.util.Constant;
import com.guangde.business.util.ResultCode;
import com.guangde.business.util.StrUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("newsService")
public class NewsServiceImpl extends BaseService implements NewsService
{
    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private BFileMapper bFileMapper;

    @Autowired
    private NewsCategoryMapper newsCategoryMapper;

    @Override
    public News queryNewsById(Integer id)
    {
        News news = newsMapper.queryById(id);
        if(news == null){
        	return null;
        }
        String ids = news.getContentImageId();
		String resUrl = ConfigLoader.getResPictrueURL();
		if (StrUtil.isNotEmpty(ids)) {
			List<Integer> idList = StrUtil.convertIdList(ids,
					Constant.data_separator);

			List<BFile> list = bFileMapper.queryByIdList(idList);

			if (!CollectionUtils.isEmpty(list)) {

				for (BFile bFile : list) {
					bFile.setUrl(resUrl + bFile.getUrl());
					if (!StringUtils.isEmpty(bFile.getMiddleUrl())) {
						bFile.setMiddleUrl(resUrl + bFile.getMiddleUrl());
					}
					if (!StringUtils.isEmpty(bFile.getLitterUrl())) {
						bFile.setLitterUrl(resUrl + bFile.getLitterUrl());
					}
				}
				news.setBfileList(list);
			}
		}
		if (news.getCoverImageUrl() != null) {
			news.setCoverImageUrl(resUrl + news.getCoverImageUrl());
		}
        return news;
    }
    
    @Override
    public List<News> queryNewsListByParam(News news, Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);
        news.setType(1);
        List<News> list = newsMapper.queryByParam(news);
        if(list != null && list.size() >0){
        	String resUrl = ConfigLoader.getResPictrueURL();
        	for(News n:list){
        		if(n.getCoverImageId()!= null){
        			BFile bf  = bFileMapper.queryById(n.getCoverImageId());
            		if(bf != null){
            			n.setCoverImageUrl(resUrl+bf.getUrl());
            		}
        		}
        	}
        }
        return list;
    }
    
    @Override
    public void updateNews(News news)
        throws BaseException
    {
        try
        {
            News n = newsMapper.queryById(news.getId());
            Integer visits = n.getVisits() + 1;
            
            news.setVisits(visits);
            //  news.setOrdertime(DateUtil.getDate());
            newsMapper.update(news);
            
        }
        catch (Exception e)
        {
            throw new BaseException(ResultCode.Error);
        }
    }
    
    @Override
    public News queryNextRecord(News news)
    {
        return newsMapper.queryNextNews(news);
    }
    
    @Override
    public News queryPreviousRecord(News news)
    {
        return newsMapper.queryPreviousNews(news);
    }

    @Override
    public List<NewsCategory> queryNewsCategory() {
        return newsCategoryMapper.queryAll() ;
    }
}
