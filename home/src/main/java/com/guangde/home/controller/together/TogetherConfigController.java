package com.guangde.home.controller.together;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guangde.api.commons.ICommonFacade;
import com.guangde.api.commons.IFileFacade;
import com.guangde.api.homepage.IProjectFacade;
import com.guangde.api.user.IDonateRecordFacade;
import com.guangde.api.user.IUserFacade;
import com.guangde.entry.*;
import com.guangde.home.constant.OtherConstans;
import com.guangde.home.utils.*;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.redis.utils.RedisService;
import com.tenpay.demo.H5Demo;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("together")
public class TogetherConfigController {
	private final Logger logger = LoggerFactory.getLogger(TogetherConfigController.class);
	@Autowired
	private IProjectFacade projectFacade;
	@Autowired
	private IUserFacade userFacade;
	@Autowired
	private RedisService redisService;
	@Autowired
	private IFileFacade fileFacade;
	@Autowired
	private ICommonFacade commonFacade;
	@Autowired
	private IDonateRecordFacade donateRecordFacade;
	
	/**
	 * 善园专用的一起捐详情页面
	 * @param projectId
	 * @return
	 */
	@RequestMapping("together_view")
	public ModelAndView together_view(@RequestParam(value="projectId",required=true)Integer projectId,
			HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("h5/together/together_detail");
		//--------------------授权登录-------------------------
		String browser = UserUtil.Browser(request);
		Integer userId = UserUtil.getUserId(request, response);
		String openId = "";
		String token = "";
		String unionid = "";
		String url = request.getRequestURL().toString();
		url = url.replace("http", "https");
		String queryString = request.getQueryString();
		String perfecturl = url + "?" + queryString;
		ApiFrontUser user = new ApiFrontUser();
		if (userId == null || userId == 0) {
			if (browser.equals("wx")) {
				String weixin_code = request.getParameter("code");
				Map<String, Object> mapToken = new HashMap<String, Object>(8);
				try {
					Object OToken = redisService.queryObjectData("weixin_token");
					token = (String) OToken;
					System.out.println("together_view >> weixin_code = " + weixin_code + "  openId = " + openId + "  OToken = " + OToken);
					if ("".equals(openId) || openId == null || OToken == null) {
						if ("".equals(weixin_code) || weixin_code == null
								|| weixin_code.equals("authdeny")) {
							String url_weixin_code = H5Demo.getCodeRequest(perfecturl);
							view = new ModelAndView("redirect:" + url_weixin_code);
							return view;
						}
						mapToken = CommonUtils.getAccessTokenAndopenidRequest(weixin_code);
						openId = mapToken.get("openid").toString();
						token = mapToken.get("access_token").toString();
						unionid = mapToken.get("unionid").toString();
						System.out.println("together_view >> tenpay.getAccessTokenAndopenidRequest openId " + openId + " token = " + token);
						redisService.saveObjectData("weixin_token", token, DateUtil.DURATION_HOUR_S);
					}
					//user = CommonUtils.queryUser(request, openId, token, unionid, response);
				} catch (Exception e) {
					logger.error("together_view>>getRedPaket>>" + e);

					logger.info("url = " + (url + StringUtil.getQueryUrl(queryString)));
					view = new ModelAndView("redirect:" + (url + StringUtil.getQueryUrl(queryString)));
					return view;

				}

				if (user.getCoverImageId() == null) {
					user.setCoverImageUrl("https://www.17xs.org/res/images/user/4.jpg"); // 个人头像
				} else {
					if (user.getCoverImageId() != null && user.getCoverImageId() == 0) {
						ApiBFile aBFile = fileFacade.queryBFileById(Integer.valueOf(user.getCoverImageId()));
						user.setCoverImageUrl(aBFile.getUrl()); // 个人头像
					}
				}
				try {

					userId = user.getId();
					// 自动登录
					SSOUtil.login(user, request, response);
				} catch (Exception e) {
					logger.error("", e);
				}
			} else {
				view = new ModelAndView("redirect:/ucenter/user/Login_H5.do");
				return view;
			}
		} else {
			user = userFacade.queryById(userId);
		}
		//===========微信用户自动登陆end==============//

		ApiProject project = projectFacade.queryProjectDetail(projectId);
		 double process = 0.0;
		if(project.getCryMoney()>=0.001){
			process = project.getDonatAmount() / project.getCryMoney();
			view.addObject("process", process > 1 ? "100" : StringUtil.doublePercentage(project.getDonatAmount(),project.getCryMoney(),0));
		}

		/*随机获取祝福语*/
		WxUtil wxUtil = new WxUtil();
		view = wxUtil.getRandomLeaveWord(view);

		//若是团队则读取团队信息
		ApiTogetherConfig apiTogetherConfig = new ApiTogetherConfig();
		apiTogetherConfig.setProjectId(projectId);
		apiTogetherConfig.setUserId(userId);
		apiTogetherConfig = projectFacade.queryByTogetherConfig(apiTogetherConfig);
		if(apiTogetherConfig != null && apiTogetherConfig.getTeamId() != null){
			ApiTogetherConfig t = new ApiTogetherConfig();
			t.setId(apiTogetherConfig.getTeamId());
			t = projectFacade.queryByTogetherConfig(t);
			view.addObject("together", t);
		}

		view.addObject("project", project);
		return view;
	}
	
	/**
	 * 添加或编辑一起捐内容
	 * @param config
	 * @return
	 */
	@RequestMapping("addTogetherConfig")
	@ResponseBody
	public JSONObject addTogetherConfig(ApiTogetherConfig config,
			HttpServletRequest request,HttpServletResponse response){
		//userId projectId totalMoney launchName content type coverImageId
		JSONObject item = new JSONObject();
		Integer userId = UserUtil.getUserId(request, response);
		if(userId==null){
			item.put("code", -1);
			item.put("msg", "用户未登录！");
			return item;
		}

		ApiFrontUser user = userFacade.queryById(userId);
		if(config.getType().equals("personal") && (config.getLaunchName()==null||config.getLaunchName().equals(""))){
			config.setLaunchName(user.getNickName());
			config.setCoverImageId(user.getCoverImageId());
		}
		if(config.getType().equals("group") && (config.getCoverImageId()==null||config.getLaunchName().equals(""))){
			config.setCoverImageId(user.getCoverImageId());
		}
		
		if(config.getProjectId()==null){
			item.put("code", -1);
			item.put("msg", "项目id为空！");
			return item;
		}
		ApiTogetherConfig apiTogetherConfig = new ApiTogetherConfig();
		apiTogetherConfig.setProjectId(config.getProjectId());
		if(config.getId() != null && config.getType().equals("group")){
			apiTogetherConfig.setId(config.getId());
		}else{
			config.setUserId(userId);
			apiTogetherConfig.setUserId(config.getUserId());

		}

		apiTogetherConfig = projectFacade.queryByTogetherConfig(apiTogetherConfig);
		if(apiTogetherConfig!=null){//update
			config.setId(apiTogetherConfig.getId());
			config.setUpdateTime(new Date());
			ApiResult result = projectFacade.updateTogetherConfig(config);
			if(result.getCode()==1){
				item.put("msg", "发起成功！");
			}
			else{
				item.put("msg", "发起失败！");
			}
			item.put("code", result.getCode());
			return item;
		}
		else{//add
			config.setTeamState(OtherConstans.TOGETHER_TEAM_400);
			config.setClick(0);
			config.setDonateMoney(BigDecimal.ZERO);
			config.setDonateNum(0);
			config.setCreateTime(new Date());
			ApiResult result = projectFacade.saveTogetherConfig(config);
			if(result.getCode()==1){
				item.put("msg", "发起成功！");
			}
			else{
				item.put("msg", "发起失败！");
			}
			item.put("code", result.getCode());
			return item;
		}
	}
	
	/**
	 * 一起捐分享详情页面
	 * @return
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	@RequestMapping("raiseDetail_view")
	public ModelAndView raiseDetail_view(@RequestParam(value="projectId",required=true)Integer projectId,
			@RequestParam(value="show",required=false,defaultValue="0")Integer show,
			@RequestParam(value="donateShow",required=false,defaultValue="0")Integer donateShow,
			@RequestParam(value="shareUserId",required=false)Integer shareUserId,
			HttpServletRequest request,HttpServletResponse response) throws JDOMException, IOException{
		ModelAndView view = new ModelAndView("h5/together/raise_detail");
		request.getSession().setAttribute("extensionPeople", shareUserId);

		//--------------------授权登录-------------------------
		String browser = UserUtil.Browser(request);
		Integer userId = UserUtil.getUserId(request, response);
		String openId = "";
		String token = "";
		String unionid = "";
		String url = request.getRequestURL().toString();
		url = url.replace("http", "https");
		String queryString = request.getQueryString();
		String perfecturl = url + "?" + queryString;
		ApiFrontUser user = new ApiFrontUser();
		if (userId == null || userId == 0) {
			if (browser.equals("wx")) {
				String weixin_code = request.getParameter("code");
				Map<String, Object> mapToken = new HashMap<String, Object>(8);
				try {
					Object OToken = redisService.queryObjectData("weixin_token");
					token = (String) OToken;
					System.out.println("userCenter_h5 >> weixin_code = " + weixin_code + "  openId = " + openId + "  OToken = " + OToken);
					if ("".equals(openId) || openId == null || OToken == null) {
						if ("".equals(weixin_code) || weixin_code == null
								|| weixin_code.equals("authdeny")) {
							String url_weixin_code = H5Demo.getCodeRequest(perfecturl);
							view = new ModelAndView("redirect:" + url_weixin_code);
							return view;
						}
						mapToken = CommonUtils.getAccessTokenAndopenidRequest(weixin_code);
						openId = mapToken.get("openid").toString();
						token = mapToken.get("access_token").toString();
						unionid = mapToken.get("unionid").toString();
						System.out.println("entryFormNewView >> tenpay.getAccessTokenAndopenidRequest openId " + openId + " token = " + token);
						redisService.saveObjectData("weixin_token", token, DateUtil.DURATION_HOUR_S);
					}
					//user = CommonUtils.queryUser(request, openId, token, unionid, response);
				} catch (Exception e) {
					logger.error("entryFormNewView>>getRedPaket>>" + e);
					logger.info("url = " + (url + StringUtil.getQueryUrl(queryString)));
					view = new ModelAndView("redirect:" + (url + StringUtil.getQueryUrl(queryString)));
					return view;

				}

				if (user.getCoverImageId() == null) {
					user.setCoverImageUrl("https://www.17xs.org/res/images/user/4.jpg"); // 个人头像
				} else {
					if (user.getCoverImageId() != null && user.getCoverImageId() == 0) {
						ApiBFile aBFile = fileFacade.queryBFileById(Integer.valueOf(user.getCoverImageId()));
						user.setCoverImageUrl(aBFile.getUrl()); // 个人头像
					}
				}
				try {
					userId = user.getId();
					// 自动登录
					SSOUtil.login(user, request, response);
				} catch (Exception e) {
					logger.error("", e);
				}
			} else {
				view = new ModelAndView("redirect:/ucenter/user/Login_H5.do");
				return view;
			}
		} else {
			user = userFacade.queryById(userId);
		}
		//===========微信用户自动登陆end==============//


        if(shareUserId!=null){
        	 ApiFrontUser userT = userFacade.queryById(shareUserId);
        	 view.addObject("shareUser", userT);
        }
        
        //user = userFacade.queryById(userId);
        view.addObject("user", user);
		view.addObject("userId", userId);
		view.addObject("show", show);
		//项目详情
		ApiProject project = projectFacade.queryProjectDetail(projectId);

		Integer projectType = 0;
		view.addObject("projectType",projectType);

		view.addObject("project", project);
		ApiTogetherConfig togetherConfig = new ApiTogetherConfig();
		if(shareUserId!=null){
			togetherConfig.setUserId(shareUserId);
		}
		else{
			togetherConfig.setUserId(userId);
		}
		view.addObject("shareUserId", shareUserId);
		togetherConfig.setProjectId(projectId);
		togetherConfig = projectFacade.queryByTogetherConfig(togetherConfig);
		if(donateShow==1 && togetherConfig!=null && togetherConfig.getShareState()==0){
			//判断是否是第一次分享
			togetherConfig.setShareState(1);
			projectFacade.updateTogetherConfig(togetherConfig);
        	view.addObject("donateShow", donateShow);
        }
		view.addObject("togetherConfig", togetherConfig);
		/***/
		double process = 0.0;
		if(togetherConfig.getTotalMoney().doubleValue()>=0.0001){
			process = togetherConfig.getDonateMoney().doubleValue() / togetherConfig.getTotalMoney().doubleValue();
			view.addObject("process", process > 1 ? "100" : StringUtil.doublePercentage(togetherConfig.getDonateMoney().doubleValue(),togetherConfig.getTotalMoney().doubleValue(),0));
		}
		/*增加点击量*/
		if(togetherConfig!=null){
			togetherConfig.setClick(togetherConfig.getClick()+1);
			projectFacade.updateTogetherConfig(togetherConfig);
		
		}
		
		//捐款金额配置
		ApiProjectMoneyConfig param = new ApiProjectMoneyConfig();
		param.setProjectId(projectId);
		List<ApiProjectMoneyConfig> moneyConfigs=projectFacade.queryMoneyConfigByParam(param);
		view.addObject("moneyConfigs", moneyConfigs);

		/*随机获取祝福语*/
		WxUtil wxUtil = new WxUtil();
		view = wxUtil.getRandomLeaveWord(view);

		//分享参数
		view = wxUtil.share(request, view);
		return view;
	}
	
	/**
	 * 查询金额配置
	 * @param config
	 * @return
	 */
	@RequestMapping("selectMoneyConfig")
	@ResponseBody
	public JSONObject selectMoneyConfig(ApiProjectMoneyConfig config){
		JSONObject item = new JSONObject();
		List<ApiProjectMoneyConfig> moneyConfigs=projectFacade.queryMoneyConfigByParam(config);
		if(moneyConfigs!=null && moneyConfigs.size()>0){
			item.put("content", moneyConfigs.get(0).getContent());
		}
		return item;
	}


	/**
	 * 加载一起捐
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/loadTogetherConfigList", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject togetherConfig_list(@RequestParam("projectId") Integer projectId,
			@RequestParam(value="pageNum",required=true,defaultValue="1")Integer pageNum,
			@RequestParam(value="pageSize",required=true,defaultValue="10")Integer pageSize){
		ItemUtil itemUtil = new ItemUtil();
		JSONObject result = new JSONObject();
		JSONArray items = new JSONArray();
		try {
			ApiPage<ApiTogetherConfig> apList = projectFacade.queryTogetherConfigByProjectId(projectId, pageNum, pageSize);
			ApiDonateRecord d = new ApiDonateRecord();
			d.setProjectId(projectId);
			if (apList != null && apList.getTotal() > 0 && apList.getPages() >= pageNum) {
				for (ApiTogetherConfig togetherConfig : apList.getResultData()) {
					JSONObject item = new JSONObject();
					//item.put("userId", togetherConfig.getUserId());
					item.put("nickName", togetherConfig.getLaunchName());
					item.put("coverImageUrl", togetherConfig.getCoverImageUrl());
					item.put("donateNum", togetherConfig.getDonateNum());
					item.put("donateMoney", togetherConfig.getDonateMoney());
					//item.put("content", togetherConfig.getContent());

					d.setExtensionPeople(togetherConfig.getUserId());
					item.put("donateUserNum", donateRecordFacade.countByDonateRecordParam(d));

					items.add(item);
				}
				result.put("togetherList", items);
			} else {
				return itemUtil.otherResult("没有更多数据了");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return itemUtil.error();
		}
	return itemUtil.success(result);
	}


	
	/**
	 * 加载一起捐 捐款 留言
	 * @param projectId
	 * @return
	 */
	@RequestMapping("loadDetailList")
	@ResponseBody
	public JSONObject loadDetail_list(Integer projectId,
			@RequestParam(value="page",required=true,defaultValue="1")Integer page,
			@RequestParam(value="pageNum",required=true,defaultValue="10")Integer pageNum){
		JSONObject data = new JSONObject();
		JSONArray items = new JSONArray();
		try {
			ApiPage<ApiTogetherConfig> apList =  projectFacade.queryDetailByProjectId(projectId, page, pageNum);
			List<ApiTogetherConfig> list = apList.getResultData();
	        if(list.size()==0){
	        	 //无数据
	        	 data.put("result", 1);
	         }
	         else {
				for(ApiTogetherConfig togetherConfig:list){
					 JSONObject item = new JSONObject();
					 item.put("userId", togetherConfig.getUserId());
					 item.put("nickName", togetherConfig.getLaunchName());
					 item.put("coverImageUrl", togetherConfig.getCoverImageUrl());
					 item.put("donateNum", togetherConfig.getDonateNum());
					 item.put("donateMoney", togetherConfig.getDonateMoney());
					 item.put("content", togetherConfig.getContent());
					 item.put("type", togetherConfig.getType());
					 items.add(item);
				}
				data.put("result",0);
				data.put("total",apList.getTotal());
				data.put("items", items);
			}
	} catch (Exception e) {
		data.put("result", 2);
	}
	return data;
	}
	
	/**
	 * 善园添砖加瓦广告页面
	 * @param projectId
	 * @return
	 */
		@RequestMapping("index_view")
	public ModelAndView index_view(@RequestParam(value="projectId",required=false,defaultValue="3429")Integer projectId){
		ModelAndView view = new ModelAndView("h5/together/index");
		view.addObject("projectId", projectId);
		return view;
	}
	
	/**
	 * 善园添砖加瓦捐款列表详情
	 * @param projectId
	 * @return
	 */
	@RequestMapping("record_view")
	public ModelAndView record_view(@RequestParam("projectId")Integer projectId){
		ModelAndView view = new ModelAndView("h5/together/record");
		view.addObject("projectId", projectId);
		return view;
	}
	
	/**
	 * 加载一起捐 捐款
	 * @param projectId
	 * @return
	 */
	@RequestMapping("loadTogetherDonateList")
	@ResponseBody
	public JSONObject loadTogetherDonate_list(Integer projectId,
			@RequestParam(value="page",required=true,defaultValue="1")Integer page,
			@RequestParam(value="pageNum",required=true,defaultValue="10")Integer pageNum){
		JSONObject data = new JSONObject();
		JSONArray items = new JSONArray();
		try {
			ApiPage<ApiTogetherConfig> apList =  projectFacade.queryTogetherDonateListByProjectId(projectId, page, pageNum);
			List<ApiTogetherConfig> list = apList.getResultData();
	        if(list.size()==0){
	        	 //无数据
	        	 data.put("result", 1);
	         }
	         else {
	        	 DecimalFormat df = new DecimalFormat("0.00");
				for(ApiTogetherConfig togetherConfig:list){
					 JSONObject item = new JSONObject();
					 item.put("userId", togetherConfig.getUserId());
					 item.put("nickName", togetherConfig.getLaunchName());
					 item.put("coverImageUrl", togetherConfig.getCoverImageUrl());
					 item.put("donateNum", togetherConfig.getDonateNum());
					 item.put("donateMoney", df.format(togetherConfig.getDonateMoney()));
					 item.put("content", togetherConfig.getContent());
					 item.put("type", togetherConfig.getType());
					 items.add(item);
				}
				data.put("result",0);
				data.put("total",apList.getTotal());
				data.put("items", items);
			}
	} catch (Exception e) {
		data.put("result", 2);
	}
	return data;
	}
	
	@RequestMapping(value="oneProjectTogetherNum",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject oneProjectTogetherNum(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="projectId",required=true)Integer projectId){
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject item=new JSONObject();
		JSONObject result = new JSONObject();
		
		
		ApiProject p=projectFacade.queryProjectDetail(projectId);
		if(p==null){
			item.put("result", result);
			item.put("code", -1);
			item.put("msg", "该项目不存在");
		}else{
			ApiTogetherConfig togetherConfig=new ApiTogetherConfig();
			togetherConfig.setProjectId(projectId);
			List<ApiTogetherConfig> list=projectFacade.oneApiTogetherConfigTotal(togetherConfig);
			BigDecimal totalDonateMoney=list.get(0).getTotalDonateMoney();
			Integer totalDonateNum=list.get(0).getTotalDonateNum();
			Integer totalFaqiNum=list.get(0).getTotalFaqiNum();
			//一起捐发起人数
			result.put("totalFaqiNum", totalFaqiNum);
			//单项目的捐赠总金额
			result.put("totalDonateMoney", totalDonateMoney==null?0:totalDonateMoney);
			//单项目的捐赠总人数
			result.put("totalDonateNum", totalDonateNum==null?0:totalDonateNum);
			
			item.put("result", result);
			item.put("code", 1);
			item.put("msg", "success");
		}
		return item;
	}


	@RequestMapping(value="getYiXingView", method = RequestMethod.GET)
	public ModelAndView getYiXingView(@RequestParam(value="projectId")Integer projectId,
									  HttpServletRequest request) throws JDOMException, IOException {
		ModelAndView view = new ModelAndView("h5/project/project_detail_yixing");
		ApiProject apiProject = projectFacade.queryProjectDetail(projectId);

		view.addObject("project",apiProject);

		//捐款百分比
		if(apiProject != null){
			Double process = 0.0;
			if (apiProject.getCryMoney() >= 0.001) {
				process = apiProject.getDonatAmount() / apiProject.getCryMoney();
			}
			view.addObject("process",process > 1 ? "100" : StringUtil.doublePercentage(apiProject.getDonatAmount(), apiProject.getCryMoney(), 0));
			view.addObject("processbfb", StringUtil.doublePercentage(apiProject.getDonatAmount(), apiProject.getCryMoney(), 0));
		}

		// 捐款笔数
		/*ApiDonateRecord donate = new ApiDonateRecord();
		donate.setState(302);
		donate.setProjectId(projectId);
		ApiDonateRecord donate1 = donateRecordFacade
				.queryCompanyCenter(donate);
		if (donate1 != null){
			view.addObject("peopleNum", donate1.getGoodHelpCount());
		}*/

		//根据项目id查询队伍列表
		ApiTogetherConfig togetherConfig = new ApiTogetherConfig();
		togetherConfig.setProjectId(projectId);
		//togetherConfig.setTeamPlayNum(0);
		togetherConfig.setIsHide(0);
		togetherConfig.setTeamState(OtherConstans.TOGETHER_TEAM_100);
		ApiPage<ApiTogetherConfig> apiTogetherConfigApiPage = projectFacade.queryTogetherConfigList(togetherConfig, 1, 5);
		if(apiTogetherConfigApiPage != null && apiTogetherConfigApiPage.getTotal() > 0){
			view.addObject("teams",apiTogetherConfigApiPage.getResultData());
		}

		//发起人，受助人，证明人
		WxUtil wx = new WxUtil();
		view = wx.getProjectUserInfo(view, projectId);

		//微信分享参数
		view = wx.share(request, view);

		return view;
	}

	@RequestMapping(value = "getTeamListView", method = RequestMethod.GET)
	public ModelAndView getTeamListView(@RequestParam(value="projectId")Integer projectId,
										HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView("h5/activity/group");
		Integer userId = UserUtil.getUserId(request,response);
		if(userId != null){
			ApiTogetherConfig apiTogetherConfig = new ApiTogetherConfig();
			apiTogetherConfig.setProjectId(projectId);
			apiTogetherConfig.setUserId(userId);
			ApiTogetherConfig apiTogetherConfig1 = projectFacade.queryByTogetherConfig(apiTogetherConfig);
			if(apiTogetherConfig1 == null ||
					(apiTogetherConfig1 != null && apiTogetherConfig1.getTeamState() == OtherConstans.TOGETHER_TEAM_400)){
				//未加入队伍
				view.addObject("state",0);
			}else{
				//已加入队伍
				view.addObject("state",1);
				view.addObject("projectId",apiTogetherConfig1.getProjectId());
				view.addObject("teamId",apiTogetherConfig1.getTeamId());
			}
		}else{
			view.addObject("state",0);
		}
		//统计队伍、队员捐赠金额总额，捐赠次数
		ApiTogetherConfig togetherConfig = projectFacade.countTogetherDonateMoneyAndDonateNumberByProjectId(projectId);
		view.addObject("togetherConfig",togetherConfig);
		return view;
	}

	@RequestMapping(value = "getTeamInfoView", method = RequestMethod.GET)
	public ModelAndView getTeamInfoView(@RequestParam(value="id")Integer id,@RequestParam(value="projectId")Integer projectId,
										HttpServletRequest request, HttpServletResponse response) throws JDOMException, IOException {
		ModelAndView view = new ModelAndView("h5/activity/group_detail");
		Integer userId = UserUtil.getUserId(request,response);
		//项目内容
		ApiProject apiProject = projectFacade.queryProjectDetail(projectId);
		if(apiProject != null){
			view.addObject("content", apiProject.getContent());
			view.addObject("projectId", apiProject.getId());
			view.addObject("projectTitle", apiProject.getTitle());
			view.addObject("projectImg", apiProject.getCoverImageUrl());
		}

		//团队信息 头像、团队名称、简介、目标金额
		ApiTogetherConfig together = new ApiTogetherConfig();
		together.setId(id);//sss
		ApiTogetherConfig togetherConfig = projectFacade.queryByTogetherConfig(together);
		view.addObject("together", togetherConfig);
		BigDecimal totalMoney = new BigDecimal(0);

		ApiTogetherConfig togetherConfig2 = new ApiTogetherConfig();
		togetherConfig2.setTeamId(id);
		togetherConfig2.setTeamState(OtherConstans.TOGETHER_TEAM_200);
		togetherConfig2 = projectFacade.queryByTogetherConfig(togetherConfig2);
		if(togetherConfig2 != null && togetherConfig2.getUserId().equals(userId)){
			//判断用户是否是该队的队长
			view.addObject("isTeamLeader",1);
		}
		if(togetherConfig != null){
			totalMoney = togetherConfig.getTotalDonateMoney();
			//进度条
			//ApiTogetherConfig total = projectFacade.countTogetherDonateMoneyAndDonateNumberByProjectId(projectId);
			if(apiProject != null){
				view.addObject("processbfb", StringUtil.doublePercentage(togetherConfig.getTotalDonateNum().doubleValue(), 10.0, 0));
			}
			view.addObject("totalDonateNum",togetherConfig.getTotalDonateNum());
		}

		//成员信息
		together.setProjectId(projectId);
		together.setClick(0);
		together.setTeamId(id);
		together.setOrderBy("id");
		together.setOrderDirection("ASC");
		ApiPage<ApiTogetherConfig> apiTogetherConfigApiPage = projectFacade.queryTogetherConfigList(together,1,10);
		ApiDonateRecord donate = new ApiDonateRecord();
		donate.setUserId(userId);
		donate.setProjectId(projectId);
		if(apiTogetherConfigApiPage != null && apiTogetherConfigApiPage.getTotal() > 0){
			for (ApiTogetherConfig t : apiTogetherConfigApiPage.getResultData()){
				if(BigDecimal.ZERO.equals(totalMoney)){
					t.setProcessbfb("0");
				}else{
					t.setProcessbfb(StringUtil.doublePercentage(t.getDonateMoney().doubleValue(),totalMoney.doubleValue(), 0));
				}

				if(userId == null){
					t.setClick(0);
				}else{
					donate.setExtensionPeople(t.getUserId());
					Integer num = donateRecordFacade.countNumQueryByCondition(donate);
					t.setClick(num);
				}

			}
		}
		view.addObject("teamUser", apiTogetherConfigApiPage.getResultData());


		//微信分享参数
		WxUtil wx = new WxUtil();
		view = wx.share(request, view);

		return view;
	}

	@RequestMapping(value = "getTeamState", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getTeamState(@RequestParam(value = "projectId") Integer projectId,
								   HttpServletRequest request, HttpServletResponse response){
		JSONObject item = new JSONObject();
		JSONObject result = new JSONObject();
		ItemUtil itemUtil = new ItemUtil();
		Integer userId = UserUtil.getUserId(request,response);
		if(userId == null){
			item.put("msg", "未登录");
			item.put("code", 0);
			return item;
		}

		ApiTogetherConfig apiTogetherConfig = new ApiTogetherConfig();
		apiTogetherConfig.setProjectId(projectId);
		apiTogetherConfig.setUserId(userId);
		ApiTogetherConfig apiTogetherConfig1 = projectFacade.queryByTogetherConfig(apiTogetherConfig);
		if(apiTogetherConfig1 == null ||
				(apiTogetherConfig1 != null && OtherConstans.TOGETHER_TEAM_400.equals(apiTogetherConfig1.getTeamState()))){
			//未加入队伍
			result.put("state",0);
		}else{
			//已加入队伍
			result.put("state",1);
			result.put("teamId",apiTogetherConfig1.getTeamId());
			result.put("projectId",apiTogetherConfig1.getProjectId());
		}

		item.put("result", result);
		item.put("msg", "成功");
		item.put("code", 1);
		return item;
	}

	@RequestMapping(value = "getLastTeamId", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getLastTeamId(@RequestParam(value = "projectId")Integer projectId, HttpServletRequest request, HttpServletResponse response){
		JSONObject item = new JSONObject();
		JSONObject result = new JSONObject();
		Integer userId = UserUtil.getUserId(request,response);
		if(userId != null) {
			try {
				ApiTogetherConfig apiTogetherConfig = new ApiTogetherConfig();
				apiTogetherConfig.setProjectId(projectId);
				apiTogetherConfig.setUserId(userId);
				//apiTogetherConfig.setTeamState(OtherConstans.TOGETHER_TEAM_100);
				apiTogetherConfig = projectFacade.queryByTogetherConfig(apiTogetherConfig);
				if (apiTogetherConfig != null) {
					result.put("teamId", apiTogetherConfig.getTeamId());
					item.put("result", result);
					item.put("msg", "成功");
					item.put("code", 1);
					return item;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		item.put("msg","失败");
		item.put("code", 0);
		return item;
	}
}
