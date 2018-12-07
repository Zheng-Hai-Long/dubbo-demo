package com.guangde.home.controller.project;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guangde.api.commons.ICommonFacade;
import com.guangde.api.commons.IFileFacade;
import com.guangde.api.homepage.IProjectFacade;
import com.guangde.api.homepage.IProjectVolunteerFacade;
import com.guangde.api.user.IDonateRecordFacade;
import com.guangde.api.user.ISystemNotifyFacade;
import com.guangde.api.user.IUserFacade;
import com.guangde.api.user.IUserRelationInfoFacade;
import com.guangde.entry.*;
import com.guangde.home.constant.PengPengConstants;
import com.guangde.home.utils.DateUtil;
import com.guangde.home.utils.StringUtil;
import com.guangde.home.utils.UserUtil;
import com.guangde.home.utils.webUtil;
import com.guangde.home.vo.common.Page;
import com.guangde.home.vo.project.PFeedBack;
import com.guangde.home.vo.project.ProjectForm;
import com.guangde.pojo.ApiPage;
import com.redis.utils.RedisService;
import com.tenpay.demo.H5Demo;
import com.tenpay.utils.TenpayUtil;
import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
/*
 * h5页面的显示控制
 */
@Controller
@RequestMapping("project")
public class H5ProjectController {
	Logger logger = LoggerFactory.getLogger(H5ProjectController.class);

	@Autowired
	private IProjectFacade projectFacade;
	@Autowired
	private IDonateRecordFacade donateRecordFacade;
	@Autowired	private IUserFacade userFacade;
	@Autowired
	private ISystemNotifyFacade systemNotifyFacade;
	@Autowired
	private IFileFacade fileFacade;
	@Autowired
	private ICommonFacade commonFacade;
	@Autowired
	private RedisService redisService;
	@Autowired
	private IProjectVolunteerFacade projectVolunteerFacade;
	@Autowired
	private IUserRelationInfoFacade userRelationInfoFacade;



	/**
	 * 善园项目详情
	 * 
	 * @param projectId
	 * @param request
	 * @param response
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	@RequestMapping("gardenview_h5")
	public ModelAndView gardenview(
			@RequestParam(value = "projectId") Integer projectId,
			HttpServletRequest request, HttpServletResponse response)
			throws JDOMException, IOException {

		// 项目详情
		ApiProject project = projectFacade.queryProjectDetail(projectId);
		// 项目进度
		ApiReport report = new ApiReport();
		report.setProjectId(projectId);
		String key = PengPengConstants.PROJECT_SCHEDUlE_LIST + "_" + projectId;
		report.initNormalCache(true, DateUtil.DURATION_TEN_S, key);
		ApiPage<ApiReport> reports = projectFacade.queryReportList(report, 1,
				30);
		ModelAndView view = new ModelAndView("h5/garden/garden_project_detail");
		view.addObject("project", project);
		double process = 0.0;
		Integer userId = UserUtil.getUserId(request, response);
		if (project != null) {
			view.addObject("desc",
					StringUtil.convertTodelete(project.getContent()));
			project.setContent(StringUtil.convertToHtml(project.getContent()));
			process = 0.0;
			if (project.getCryMoney() >= 0.001) {
				process = project.getDonatAmount() / project.getCryMoney();
			}
			view.addObject(
					"process",
					process > 1 ? "100" : StringUtil.doublePercentage(
							project.getDonatAmount(), project.getCryMoney(), 0));
			view.addObject("processbfb", StringUtil.doublePercentage(
					project.getDonatAmount(), project.getCryMoney(), 0));
			if (userId != null && project.getUserId().equals(userId)) {
				// 是否是项目发起人
				view.addObject("owner", true);
			}
			ApiProjectUserInfo userInfo = new ApiProjectUserInfo();
			userInfo.setProjectId(projectId);
			key = PengPengConstants.PROJECT_USERINFO_LIST + "_" + projectId;
			userInfo.initNormalCache(false, DateUtil.DURATION_HOUR_S, key);
			List<ApiProjectUserInfo> userInfos = projectFacade
					.queryProjectUserInfoList(userInfo);
			for (ApiProjectUserInfo u : userInfos) {
				if (u.getPersonType() == 0) {
					view.addObject("shouzhu", u);
				} else if (u.getPersonType() == 1) {
					view.addObject("zhengming", u);
				} else if (u.getPersonType() == 2) {
					view.addObject("fabu", u);
				}
			}
		}
		if (reports != null && reports.getTotal() > 0) {
			view.addObject("reports", reports.getResultData());
		}
		String userType = "";//SSOUtil.getCurrentUserType(request, response);
		// 最新捐款列表

		ApiDonateRecord r = new ApiDonateRecord();
		Date edate = new Date();
		r.setProjectId(projectId);
		r.setState(302);
		List<String> llist = new ArrayList<String>(1);
		llist.add(ApiDonateRecord.getCacheRange(r.getClass().getName(),
				BaseBean.RANGE_WHOLE, projectId));
		r.initCache(true, DateUtil.DURATION_MIN_S, llist, "projectId");
		ApiPage<ApiDonateRecord> donats = donateRecordFacade.queryByCondition(
				r, 1, 10);
		view.addObject("donates", donats.getResultData());
		view.addObject("peopleNum", donats.getTotal());
		String browser = UserUtil.Browser(request);
		if (browser.equals("wx")) {
			// 微信调度的唯一凭证AccessToken失效，要同时更新jsTicket这个临时凭证
			String jsTicket = (String) redisService
					.queryObjectData("JsapiTicket");
			String accessToken = (String) redisService
					.queryObjectData("AccessToken");
			if (jsTicket == null || accessToken == null) {
				accessToken = TenpayUtil.queryAccessToken();
				redisService.saveObjectData("AccessToken", accessToken,
						DateUtil.DURATION_HOUR_S);
				jsTicket = TenpayUtil.queryJsapiTicket(accessToken);
				redisService.saveObjectData("JsapiTicket", jsTicket,
						DateUtil.DURATION_HOUR_S);
			}
			String url = request.getRequestURL().toString();
			url = url.replace("http", "https");
			String queryString = request.getQueryString();
			String perfecturl = url + "?" + queryString;
			SortedMap<String, String> map = H5Demo.getConfigweixin(jsTicket,
					perfecturl);
			view.addObject("appId", map.get("appId"));
			view.addObject("timestamp", map.get("timeStamp"));
			view.addObject("noncestr", map.get("nonceStr"));
			view.addObject("signature", map.get("signature"));
		}
		view.addObject("projectId", projectId);
		return view;
	}

	@RequestMapping("view_list_h5")
	public ModelAndView view_list_h5(ProjectForm from,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = new ModelAndView("h5/project/project_list");
		List<ApiTypeConfig> atc = commonFacade.queryList();
		view.addObject("atc", atc);
		view.addObject("extensionPeople",
				request.getSession().getAttribute("extensionPeople"));
		view.addObject("field", from.getField());
		view.addObject("tag", from.getTag());
		return view;
	}





	/*
	 * @param projectId 项目ID 显示项目详情
	 */
	@RequestMapping("view_test_h5")
	public ModelAndView viewtest(
			@RequestParam(value = "projectId") Integer projectId,
			HttpServletRequest request, HttpServletResponse response)
			throws JDOMException, IOException {
		// 项目详情
		ApiProject project = projectFacade.queryProjectDetail(projectId);
		// 项目进度
		ApiReport report = new ApiReport();
		report.setProjectId(projectId);
		String key = PengPengConstants.PROJECT_SCHEDUlE_LIST + "_" + projectId;
		report.initNormalCache(true, DateUtil.DURATION_TEN_S, key);
		ApiPage<ApiReport> reports = projectFacade.queryReportList(report, 1,
				30);
		ModelAndView view = new ModelAndView("h5/project/project_detail_test");
		view.addObject("project", project);
		double process = 0.0;
		Integer userId = UserUtil.getUserId(request, response);
		if (project != null) {
			view.addObject("desc",
					StringUtil.convertTodelete(project.getContent()));
			project.setContent(StringUtil.convertToHtml(project.getContent()));
			process = 0.0;
			if (project.getCryMoney() >= 0.001) {
				process = project.getDonatAmount() / project.getCryMoney();
			}
			view.addObject(
					"process",
					process > 1 ? "100" : StringUtil.doublePercentage(
							project.getDonatAmount(), project.getCryMoney(), 0));
			view.addObject("processbfb", StringUtil.doublePercentage(
					project.getDonatAmount(), project.getCryMoney(), 0));
			if (userId != null && project.getUserId().equals(userId)) {
				// 是否是项目发起人
				view.addObject("owner", true);
			}
			ApiProjectUserInfo userInfo = new ApiProjectUserInfo();
			userInfo.setProjectId(projectId);
			key = PengPengConstants.PROJECT_USERINFO_LIST + "_" + projectId;
			userInfo.initNormalCache(false, DateUtil.DURATION_HOUR_S, key);
			List<ApiProjectUserInfo> userInfos = projectFacade
					.queryProjectUserInfoList(userInfo);
			for (ApiProjectUserInfo u : userInfos) {
				if (u.getPersonType() == 0) {
					view.addObject("shouzhu", u);
				} else if (u.getPersonType() == 1) {
					view.addObject("zhengming", u);
				} else if (u.getPersonType() == 2) {
					view.addObject("fabu", u);
				}
			}
		}
		if (reports != null && reports.getTotal() > 0) {
			view.addObject("reports", reports.getResultData());
		}
		String userType = "";//SSOUtil.getCurrentUserType(request, response);
		// 最新捐款列表

		ApiDonateRecord r = new ApiDonateRecord();
		Date edate = new Date();
		Date bdate = DateUtil.add(edate, -12 * 30);
		r.setQueryStartDate(bdate);
		r.setQueryEndDate(edate);
		r.setProjectId(projectId);
		r.setState(302);
		List<String> llist = new ArrayList<String>(1);
		llist.add(ApiDonateRecord.getCacheRange(r.getClass().getName(),
				BaseBean.RANGE_WHOLE, projectId));
		r.initCache(true, DateUtil.DURATION_MIN_S, llist, "projectId");
		ApiPage<ApiDonateRecord> donats = donateRecordFacade.queryByCondition(
				r, 1, 10);
		view.addObject("donates", donats.getResultData());
		view.addObject("peopleNum", donats.getTotal());

		String browser = UserUtil.Browser(request);
		if (browser.equals("wx")) {
			// 微信调度的唯一凭证AccessToken失效，要同时更新jsTicket这个临时凭证
			String jsTicket = (String) redisService
					.queryObjectData("JsapiTicket");
			String accessToken = (String) redisService
					.queryObjectData("AccessToken");
			if (jsTicket == null || accessToken == null) {
				accessToken = TenpayUtil.queryAccessToken();
				redisService.saveObjectData("AccessToken", accessToken,
						DateUtil.DURATION_HOUR_S);
				jsTicket = TenpayUtil.queryJsapiTicket(accessToken);
				redisService.saveObjectData("JsapiTicket", jsTicket,
						DateUtil.DURATION_HOUR_S);
			}
			String url = request.getRequestURL().toString();
			url = url.replace("http", "https");
			String queryString = request.getQueryString();
			String perfecturl = url + "?" + queryString;
			SortedMap<String, String> map = H5Demo.getConfigweixin(jsTicket,
					perfecturl);
			view.addObject("appId", map.get("appId"));
			view.addObject("timestamp", map.get("timeStamp"));
			view.addObject("noncestr", map.get("nonceStr"));
			view.addObject("signature", map.get("signature"));
		}
		return view;
	}

	/**
	 * h5 项目列表
	 * 
	 * @param from
	 *            sortType 0： 最新发布（时间倒序） 1： 关注最多（捐款人最多） 2： 最早发布（时间顺序） 3：
	 *            最新反馈（根据求助人反馈的时间，最新的反馈排在最前） 4： 捐助最多
	 * @return
	 */
	@ResponseBody
	@RequestMapping("list_h5")
	public JSONObject list(ProjectForm from, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject data = new JSONObject();
		JSONArray items = new JSONArray();
		// Integer userId = UserUtil.getUserId(request, response);
		try {
			ApiProject ap = new ApiProject();

			ap.setIsHide(Integer.valueOf(0));
			if ((from.getSortType() == null)
					|| (from.getSortType().intValue() == 0)) {
				ap.setOrderDirection("desc");
				ap.setOrderBy("registrTime");
			} else if (from.getSortType().intValue() == 1) {
				ap.setOrderDirection("desc");
				ap.setOrderBy("donationNum");
			} else if (from.getSortType().intValue() == 2) {
				ap.setOrderDirection("desc");
				ap.setOrderBy("lastUpdateTime");
			} else if (from.getSortType().intValue() == 3) {
				ap.setOrderDirection("desc");
				ap.setOrderBy("lastFeedbackTime");
			} else if (from.getSortType().intValue() == 4) {
				ap.setOrderDirection("desc");
				ap.setOrderBy("donatAmount");
			} else if (from.getSortType().intValue() == 5) {
				ap.setOrderDirection("desc");
				ap.setOrderBy("issueTime desc,donatAmount/cryMoney");
			}
			if (StringUtils.isNotEmpty(from.getTypeName())) {
				ap.setField(from.getTypeName());
			}
			if (from.getStatus() == null) {
				List<Integer> states = new ArrayList();
				states.add(Integer.valueOf(240));
				states.add(Integer.valueOf(260));
				ap.setStates(states);
			} else if (from.getStatus().intValue() == 1) {
				ap.setState(Integer.valueOf(240));
			} else if (from.getStatus().intValue() == 2) {
				ap.setState(Integer.valueOf(260));
			} else {
				List<Integer> states = new ArrayList();
				states.add(Integer.valueOf(240));
				states.add(Integer.valueOf(260));
				ap.setStates(states);
			}
			if (from != null && from.getTag() != null) {
				ap.setTag(from.getTag());
			}
			// ap.setDonateState(from.getState());

			// ap.setUserId(userId);
			ApiPage<ApiProject> apiPage = this.projectFacade
					.queryProjectListNew(ap, from.getPage(), from.getLen());

			// Integer noDonateNum = this.projectFacade.countUserDonateNum(ap,
			// userId.intValue(), 0);

			// Integer donateNum = this.projectFacade.countUserDonateNum(ap,
			// userId.intValue(), 1);
			if (apiPage != null
					&& apiPage.getTotal() > 0
					&& (apiPage.getTotal() - from.getPage() * from.getLen() >= 0 || from
							.getPage() * from.getLen() - apiPage.getTotal() < from
								.getLen())) {

				List<ApiProject> projects = apiPage.getResultData();
				if (projects.size() == 0) {
					data.put("result", Integer.valueOf(1));
				} else {
					for (ApiProject project : projects) {
						JSONObject item = new JSONObject();
						item.put("itemId", project.getId());
						item.put("field", project.getField());
						item.put("type", project.getType());
						item.put("title", project.getTitle());
						item.put("information", project.getInformation());
						item.put("donaAmount", project.getDonatAmount());
						item.put("cryMoney", project.getCryMoney());
						item.put(
								"process",
								StringUtil.doublePercentage(
										project.getDonatAmount(),
										project.getCryMoney(), 0));
						item.put("imageurl", project.getCoverImageUrl());
						item.put("endtime", new Date());
						item.put("state", project.getState());
						items.add(item);
					}
					data.put("items", items);
					data.put("page", Integer.valueOf(apiPage.getPageNum()));
					data.put("pageNum", Integer.valueOf(apiPage.getPageSize()));
					data.put("total", apiPage.getTotal());
					data.put("result", Integer.valueOf(0));
					// data.put("donateNum", donateNum);
					// data.put("noDonateNum", noDonateNum);
				}
			} else {
				data.put("total", 0);
				data.put("result", Integer.valueOf(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			data.put("result", Integer.valueOf(2));
			return data;
		}
		return data;
	}

	/**
	 * h5 按最近30天，捐款比例排序的项目列表
	 *
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("list_h5_30")
	public JSONObject list_h5_30(ProjectForm from, HttpServletRequest request,
						   HttpServletResponse response) {
		JSONObject data = new JSONObject();
		JSONArray items = new JSONArray();
		try {
			ApiPage<ApiProject> apiPage = this.projectFacade
					.queryProjectBy30(from.getPage(), from.getLen());
			if (apiPage != null
					&& apiPage.getTotal() > 0
					&& (apiPage.getTotal() - from.getPage() * from.getLen() >= 0 || from
					.getPage() * from.getLen() - apiPage.getTotal() < from
					.getLen())) {

				List<ApiProject> projects = apiPage.getResultData();
				if (projects.size() == 0) {
					data.put("result", Integer.valueOf(1));
				} else {
					for (ApiProject project : projects) {
						JSONObject item = new JSONObject();
						item.put("itemId", project.getId());
						item.put("field", project.getField());
						item.put("type", project.getType());
						item.put("title", project.getTitle());
						item.put("information", project.getInformation());
						item.put("donaAmount", project.getDonatAmount());
						item.put("cryMoney", project.getCryMoney());
						item.put(
								"process",
								StringUtil.doublePercentage(
										project.getDonatAmount(),
										project.getCryMoney(), 0));
						item.put("imageurl", project.getCoverImageUrl());
						item.put("endtime", new Date());
						item.put("state", project.getState());
						items.add(item);
					}
					data.put("items", items);
					data.put("page", Integer.valueOf(apiPage.getPageNum()));
					data.put("pageNum", Integer.valueOf(apiPage.getPageSize()));
					data.put("total", apiPage.getTotal());
					data.put("result", Integer.valueOf(0));
					// data.put("donateNum", donateNum);
					// data.put("noDonateNum", noDonateNum);
				}
			} else {
				data.put("total", 0);
				data.put("result", Integer.valueOf(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			data.put("result", Integer.valueOf(2));
			return data;
		}
		return data;
	}





	@RequestMapping("view_feedback_h5")
	public ModelAndView view_feedback_h5(ProjectForm from,
			HttpServletRequest request, HttpServletResponse response)
			throws JDOMException, IOException {

		ModelAndView view = new ModelAndView("h5/project/feedback");
		view.addObject("projectId", from.getItemId());

		StringBuffer url = request.getRequestURL();
		String queryString = request.getQueryString();
		String perfecturl = url + "?" + queryString;
		//view = CommonUtils.wxView(view, request, perfecturl);

		return view;
	}

	/**
	 * 
	 * H5的项目反馈 * @param name
	 * 
	 * @param projectId
	 * @param request
	 * @param response
	 * @return
	 * 
	 */
	@RequestMapping(value = "H5careProjectList")
	@ResponseBody
	public Map<String, Object> careProjectList(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(value = "pageNum", required = false, defaultValue = "10") Integer pageNum,
			@RequestParam(value = "projectId", required = false) Integer projectId) {
		Page p = new Page();
		p.setPage(page);
		p.setPageNum(pageNum);
		List<PFeedBack> feedbacks = new ArrayList<PFeedBack>();
		p.setData(feedbacks);

		ApiProjectFeedback feedBack = new ApiProjectFeedback();
		feedBack.setAuditState(203);
		feedBack.setProjectId(projectId);

		ApiPage<ApiProjectFeedback> result = projectFacade
				.queryH5ProjectFeedbckByCondition(feedBack, p.getPage(),
						p.getPageNum());

		if (result != null) {
			PFeedBack tempf = null;
			for (ApiProjectFeedback f : result.getResultData()) {
				tempf = new PFeedBack();
				tempf.setId(f.getId());
				tempf.setContent(f.getContent());
				if (f.getFeedbackTime() != null) {
					tempf.setcTime(f.getFeedbackTime().getTime());
				}
				tempf.setImgs(f.getContentImageUrl());
				tempf.setPid(f.getProjectId());
				tempf.setuName(f.getNickName());
				tempf.setUserType(f.getUserType());
				tempf.setTitle(f.getTitle());
				tempf.setUserImageUrl(f.getHeadImageUrl());
				tempf.setPid(f.getProjectId());
				// tempf.setSource(f.getSource());
				feedbacks.add(tempf);
			}
			p.setNums(result.getTotal());
		} else {
			p.setNums(0);
		}
		if (p.getTotal() == 0) {
			return webUtil.resMsg(2, "0002", "没有数据", p);
		} else {
			return webUtil.successRes(p);
		}
	}

}
