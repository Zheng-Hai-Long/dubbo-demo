package com.guangde.api.homepage;

import com.guangde.dto.NewsDTO;
import com.guangde.entry.ApiNews;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;

public interface INewsFacade
{
    /**
     * 增加访问量
     * @param apiNews
     * @return
     */
    ApiResult updateVisits(ApiNews apiNews);
    
    /**
     * 查看新闻详情
     * @param id
     * @return
     */
    NewsDTO queryNewsDetail(Integer id);
    
    /**
     * 查看新闻列表
     * @param apiNews
     * @param pageNum
     * @param pageSize
     *  说明：1、默认 按置顶top,排序时间(最后更新时间)ordertime 先后排序
     *        2、排序属性有传值则按所传属性值排序
     * @return
     */
    ApiPage<NewsDTO> queryNewsList(ApiNews apiNews, Integer pageNum, Integer pageSize);

    /**
     * 新闻分类列表
     * @return
     */
    ApiResult queryNewsCategory();
}
