package com.guangde.business.service.user;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.guangde.business.BaseTest;
import com.guangde.business.entry.News;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.NewsService;
import com.guangde.business.util.DateUtil;

public class NewsServiceTeset extends BaseTest
{
    @Resource
    private NewsService newsService;
    
    //  @Test
    public void testqueryById()
    {
        News news = newsService.queryNewsById(26);
        System.out.println(news.getTitle());
    }
    
    //  @Test
    public void updateNewsTest()
    {
        News news = new News();
        news.setId(26);
        try
        {
            newsService.updateNews(news);
        }
        catch (BaseException e)
        {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    //@Test
    public void queryListTest()
    {
        News news = new News();
        
        List<News> list = newsService.queryNewsListByParam(news, 1, 4);
        if (list != null && list.size() > 0)
        {
            for (News n : list)
            {
                System.out.println(n.getTitle()+"coverImageId="+n.getCoverImageId());
            }
        }
    }
    
   // @Test
    public void testNext()
    {
        News n = new News();
        n.setOrdertime(DateUtil.parse("2015-08-19 14:52:23", "yyyy-MM-dd hh:mm:ss"));
        n.setNews_column("公告");
        News news = newsService.queryNextRecord(n);
        if (news != null)
        {
            System.out.println(news.getTitle());
        }
    }
    
    // @Test
    public void testPrevious()
        throws UnsupportedEncodingException
    {
        News n = new News();
        n.setNews_column("公告");
        n.setOrdertime(DateUtil.parse("2015-08-19 14:52:23", "yyyy-MM-dd hh:mm:ss"));
        News news = newsService.queryPreviousRecord(n);
        
        if (news != null)
        {
            System.out.println(news.getTitle());
        }
    }
}
