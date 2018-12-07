package com.guangde.home.controller.news;

import com.guangde.api.homepage.INewsFacade;
import com.guangde.dto.NewsDTO;
import com.guangde.entry.ApiNews;
import com.guangde.enums.ResultEnum;
import com.guangde.home.utils.ResultUtil;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/news")
public class NewsController {

	@Autowired
	private INewsFacade newsFacade;

	/**
	 * 新闻列表
	 * @param type
	 * @param pageNum
	 * @param pageSize
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ApiResult newsList(@RequestParam(value = "type", required = false)String type, HttpServletRequest request,
							  @RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
							  @RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize){
		ApiNews model = new ApiNews();
		model.setOrderBy(" ordertime ");
		model.setOrderDirection(" desc ");
		model.setNews_column(type);
		ApiPage<NewsDTO> page = newsFacade.queryNewsList(model, pageNum, pageSize);
		if(page != null && page.valPage(page, pageNum)){
			return ResultUtil.SUCCESS(page);
		}
		if(pageNum > 1){
			return new ApiResult(ResultEnum.NotMoreData);
		}
		return new ApiResult(ResultEnum.EmptyData);
	}

	/**
	 * 新闻详情
	 * @param id
     * @return
     */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult newsDetail(@RequestParam(value="id",required=true)Integer id){
		NewsDTO news = newsFacade.queryNewsDetail(id);
		if(news == null){
			return new ApiResult(ResultEnum.EmptyData);
		}
		return ResultUtil.SUCCESS(news);
	}

	/**
	 * 新闻分类
	 * @return
     */
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult newsCategoryList(){
		return newsFacade.queryNewsCategory();
	}

}
