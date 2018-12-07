package com.guangde.home.controller.project;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guangde.api.commons.ICommonFacade;
import com.guangde.api.commons.IFileFacade;
import com.guangde.api.homepage.IProjectFacade;
import com.guangde.api.homepage.IProjectVolunteerFacade;
import com.guangde.api.user.*;
import com.guangde.entry.*;
import com.guangde.home.constant.EnterpriseConstants;
import com.guangde.home.utils.*;
import com.guangde.home.vo.common.Page;
import com.guangde.home.vo.project.Donation;
import com.guangde.home.vo.project.PFeedBack;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.redis.utils.RedisService;
import com.tenpay.demo.H5Demo;
import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("h5ProjectDetails")
public class H5ProjectDetailsController {
	
	Logger logger = LoggerFactory.getLogger(H5ProjectController.class);

	@Autowired
	private IProjectFacade projectFacade;
	@Autowired
	private IDonateRecordFacade donateRecordFacade;
	@Autowired
	private ICompanyFacade companyFacade;
	@Autowired
	private IUserFacade  userFacade;
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
     * 
     * H5的项目反馈
     * @param request
     * @param response
     * @return
     * 
     */
    @RequestMapping(value = "H5ProjectFeedBackList")
    @ResponseBody
    public Map<String, Object> careProjectList(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
        @RequestParam(value = "pageNum", required = false, defaultValue = "10") Integer pageNum,
        @RequestParam(value = "projectId", required = true) Integer projectId)
    {     
    	  JSONArray items =null;
    	  
          Page p = new Page();
          p.setPage(page);
          p.setPageNum(pageNum);
          
          
          Page newLeaveWordPage=new Page();
          newLeaveWordPage.setPage(page);
          newLeaveWordPage.setPageNum(pageNum);
          
          
          List<PFeedBack> feedbacks = new ArrayList<PFeedBack>();
          p.setData(feedbacks);
          
          List<Object> list=new ArrayList<Object>();
          
          ApiProjectFeedback feedBack = new ApiProjectFeedback();
          feedBack.setAuditState(203);
          feedBack.setProjectId(projectId);
          
          ApiPage<ApiProjectFeedback> result = projectFacade.queryH5ProjectFeedbckByCondition(feedBack, p.getPage(), p.getPageNum());
         
          if (result!= null)
          {
              PFeedBack tempf = null;
              for (ApiProjectFeedback f : result.getResultData())
              {   
            	  //获取对应的反馈信息
            	  items= new JSONArray();
            	  Integer feedBackId=f.getId();
            	  //根据项目反馈id去查询对应的回复的信息
            	  ApiNewLeaveWord newLeaveWord=new ApiNewLeaveWord();
            	  newLeaveWord.setProjectFeedbackId(feedBackId);
            	  newLeaveWord.setProjectId(f.getProjectId());
            	  newLeaveWord.setType(0);
            	  ApiPage<ApiNewLeaveWord> apiPage=projectFacade.queryApiNewLeaveWord(newLeaveWord, page, 20);
            	  tempf = new PFeedBack();
                  tempf.setId(f.getId());
                  tempf.setContent(f.getContent());
                  if (f.getFeedbackTime() != null)
                  {
                      tempf.setcTime(f.getFeedbackTime().getTime());
                  }
                  //项目反馈的时间
                  int d_value = DateUtil.minutesBetween(f.getFeedbackTime(),
							DateUtil.getCurrentTimeByDate());
					Boolean flag = DateUtil.dateFormat(f.getFeedbackTime()).equals(DateUtil.dateFormat(DateUtil.getCurrentTimeByDate()));
					if (d_value / 60 > 24 || !flag) {
						tempf.setShowTime(DateUtil.dateStringChinesePatternYD("yyyy年MM月dd日",f.getFeedbackTime()));
					} else {
						if (d_value / 60 >= 1) {
							tempf.setShowTime(d_value / 60 + "小时前");
						} else {
							if (d_value == 0) {
								tempf.setShowTime("刚刚");
							} else {
								tempf.setShowTime(d_value + "分钟前");
							}
						}
					}
                  tempf.setcDate(f.getFeedbackTime());
                  tempf.setImgs(f.getContentImageUrl());
                  tempf.setPid(f.getProjectId());
                  //获取发布人的信息当做项目反馈的人员
                  /*ApiProjectUserInfo ap=new ApiProjectUserInfo();
                  ap.setPersonType(2);
                  ap.setProjectId(projectId);
                  ApiProjectUserInfo apiProjectUserInfo=projectFacade.queryProjectUserInfo(ap);
                  if(apiProjectUserInfo!=null){
                	  tempf.setuName(apiProjectUserInfo.getRealName());
                  }*/
                  
                  tempf.setuName(f.getNickName());
                  tempf.setUserType(f.getUserType());
                  tempf.setTitle(f.getTitle());
                  tempf.setUserImageUrl(f.getHeadImageUrl());
                  tempf.setPid(f.getProjectId());
                  tempf.setSource(f.getSource());
                  feedbacks.add(tempf);
                 //当不为空是才能循环
                 if(apiPage!=null){
                	 for(ApiNewLeaveWord apiNewLeaveWord:apiPage.getResultData() ){
                   	  JSONObject item = new JSONObject();
                   	  item.put("id", apiNewLeaveWord.getId());
                   	  item.put("projectId", apiNewLeaveWord.getProjectId());
                   	  item.put("projectDonateId", apiNewLeaveWord.getProjectDonateId());
                   	  item.put("projectFeedbackId", apiNewLeaveWord.getProjectFeedbackId());
                   	  item.put("leavewordUserId", apiNewLeaveWord.getLeavewordUserId());
                   	  item.put("replyUserId", apiNewLeaveWord.getReplyUserId());
                   	  
                   	  item.put("leavewordName", apiNewLeaveWord.getLeavewordName());
                   	  item.put("replyName", apiNewLeaveWord.getReplyName());
                   	  item.put("content", apiNewLeaveWord.getContent());
                   	  item.put("updateTime", apiNewLeaveWord.getUpdateTime());
                   	  item.put("createTime", apiNewLeaveWord.getCreateTime());
                   	  //总的条数
                   	  item.put("total", apiPage.getTotal());
                   	  item.put("currentPage", page);
                   	  items.add(item);
                     }
                 } 
                 list.add(items);
              }
              p.setNums(result.getTotal());
              p.setData(feedbacks);
          }
          else
          {
              p.setNums(0);
          }
          if (p.getTotal() == 0)
          {
              return webUtil.resMsg(2, "0002", "没有数据", p);
          }
          else
          {
              return webUtil.successResDoubleObject(p,list);
          }	
    } 
    
    
    /*
     * 当前项目的捐赠列表
     */
    @RequestMapping("projectDeatilsdonationlist")
    @ResponseBody
    public Map<String, Object> donationlist(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id", required = false) Integer id,
        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "pageNum", required = false, defaultValue = "10") Integer pageNum)
    {
        Integer userId = UserUtil.getUserId(request, response);
        if (id == null)
        {
            return webUtil.resMsg(0, "0001", "项目id不能为空", null);
        }
        List<Object> listObject=new ArrayList<Object>();
        //分页计算
        Page p = new Page();
        p.setPageNum(pageNum);
        p.setPage(page);
        
        ApiDonateRecord r = new ApiDonateRecord();
        r.setProjectId(id);
        r.setState(302);
        List<String> llist = new ArrayList<String>(1);
        llist.add(ApiDonateRecord.getCacheRange(r.getClass().getName(), BaseBean.RANGE_WHOLE, id));
        r.initCache(true, DateUtil.DURATION_MIN_S, llist, "projectId", "state");

        ApiPage<ApiDonateRecord> resultPage = donateRecordFacade.queryByCondition(r, p.getPage(), p.getPageNum());
        List<Donation> list = new ArrayList<Donation>();
        p.setData(list);
        Donation d;
        if (resultPage.getTotal() == 0)
        {
            p.setNums(0);
        }
        else
        {   ApiNewLeaveWord newLeaveWord=null;
            JSONArray items =null;
            
            for (ApiDonateRecord dr : resultPage.getResultData())
            {       
            	
            	 //获取每条记录的留言根据捐赠记录的id
            	    items= new JSONArray();
            	    newLeaveWord=new ApiNewLeaveWord();
            	    newLeaveWord.setProjectDonateId(dr.getId());
            	    newLeaveWord.setProjectId(dr.getProjectId());
            	    newLeaveWord.setType(1);
              	    ApiPage<ApiNewLeaveWord> apiPage=projectFacade.queryApiNewLeaveWord(newLeaveWord, page, 20);
                    if(apiPage!=null){
                    	 for(ApiNewLeaveWord apiNewLeaveWord:apiPage.getResultData() ){
                       	  JSONObject item = new JSONObject();
                       	  item.put("id", apiNewLeaveWord.getId());
                       	  item.put("projectId", apiNewLeaveWord.getProjectId());
                       	  item.put("projectDonateId", apiNewLeaveWord.getProjectDonateId());
                       	  item.put("projectFeedbackId", apiNewLeaveWord.getProjectFeedbackId());
                       	  item.put("leavewordUserId", apiNewLeaveWord.getLeavewordUserId());
                       	  item.put("replyUserId", apiNewLeaveWord.getReplyUserId());
                       	  item.put("leavewordName", apiNewLeaveWord.getLeavewordName());
                       	  item.put("replyName", apiNewLeaveWord.getReplyName());
                       	  item.put("content", apiNewLeaveWord.getContent());
                       	  item.put("updateTime", apiNewLeaveWord.getUpdateTime());
                       	  item.put("createTime", apiNewLeaveWord.getCreateTime()); 
                       	  item.put("total", apiPage.getTotal());
                       	  item.put("currentPage", page);
                       	  items.add(item);
                          }
                    } 
              	    listObject.add(items);
              	    
              	    //捐款记录
	                d = new Donation();
	                d.setField(dr.getDonatType());
	                d.setId(dr.getId());
	                d.setdMoney(dr.getDonatAmount());
	                d.setdTime(dr.getDonatTime());

	                String name = dr.getNickName();
	                if(!StringUtils.isEmpty(name) && name.contains("游客")){
	                	name="爱心人士";

	                }else {
	                	if(dr.getCoverImageId() >0){
	                		ApiBFile bFile = fileFacade.queryBFileById(dr.getCoverImageId());
	                    	d.setImagesurl(bFile.getUrl());
	                	}
					}
	                d.setName(name);
	                
	                d.setTitle(dr.getProjectTitle());
	                if(dr.getLeaveWord()==null){
	                	d.setLeaveWord(null);
	                }else if (dr.getLeaveWord().contains("transfer:")) {
	                	d.setLeaveWord(dr.getLeaveWord().replace("transfer:", ""));
	                	d.setdType(6);
					}else {
						d.setLeaveWord(dr.getLeaveWord());
					}

	                
	                list.add(d);
            }
            p.setNums(resultPage.getTotal());
        }
        
        return webUtil.successResDoubleObject(p,listObject);
    }
    
    /**
     * goto留言框PC(增加未登录用游客身份留言)
     * @param model
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("gotoAlterReplyPC")
    public Map<String, Object> gotoAlterReplyPC(ApiNewLeaveWord model,
    		//@RequestParam(value="code",required=true)String code,
    		HttpServletRequest request,HttpServletResponse response){
    	//1.反馈留言：type projectId content createTime updateTime freebackId userId1 userName1
    	//2.反馈回复留言：type projectId content createTime updateTime freebackId userId1 userName1 userId2 userName2
    	//3.捐助留言：type projectId content createTime updateTime donateId userId1 userName1
    	//4.捐助回复留言：type projectId content createTime updateTime donateId userId1 userName1 userId2 userName2
    	//===========微信用户自动登陆start==============//
    	Integer userId = UserUtil.getUserId(request, response);
    	ApiFrontUser user = new ApiFrontUser();
    	if(userId == null)
    	{
    		user.setUserName("游客");
    		user = userFacade.queryUserByParam(user);
    		userId=user.getId();
    	}
    	else{
    		user = userFacade.queryById(userId);
    	}
        if(model!=null){
        	if(model.getType()==0){
        		if(model.getLeavewordUserId()==null){//反馈留言
        			model.setLeavewordUserId(userId);
        			model.setLeavewordName(user.getNickName()==null?"":user.getNickName());
        			return  webUtil.resMsg(101, "", "", model);
        		}
        		else{//反馈回复留言
        			ApiFrontUser newuser = userFacade.queryById(model.getLeavewordUserId());
        			model.setReplyUserId(model.getLeavewordUserId());
        			model.setReplyName(newuser.getNickName()==null?"":newuser.getNickName());
        			model.setLeavewordUserId(userId);
        			model.setLeavewordName(user.getNickName()==null?"":user.getNickName());
        			
        			return webUtil.resMsg(102, "", "", model);
        		}
        	}
        	else{
        		if(model.getLeavewordUserId()==null){//捐助留言
        			model.setLeavewordUserId(userId);
        			model.setLeavewordName(user.getNickName()==null?"":user.getNickName());
        			return webUtil.resMsg(201, "", "", model);
        		}
        		else{//捐助回复留言
        			ApiFrontUser newuser = userFacade.queryById(model.getLeavewordUserId());
        			model.setReplyUserId(model.getLeavewordUserId());
        			model.setReplyName(newuser.getNickName()==null?"":newuser.getNickName());
        			model.setLeavewordUserId(userId);
        			model.setLeavewordName(user.getNickName()==null?"":user.getNickName());
        			
        			return webUtil.resMsg(202, "", "", model);
        		}
        	}
        }
        return webUtil.resMsg(0, "0002", "参数错误！", null);
    }
    
    /**
     * goto留言框
     * @param model
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("gotoAlterReply")
    public Map<String, Object> gotoAlterReply(ApiNewLeaveWord model,
    		//@RequestParam(value="code",required=true)String code,
    		HttpServletRequest request,HttpServletResponse response){
    	//1.反馈留言：type projectId content createTime updateTime freebackId userId1 userName1
    	//2.反馈回复留言：type projectId content createTime updateTime freebackId userId1 userName1 userId2 userName2
    	//3.捐助留言：type projectId content createTime updateTime donateId userId1 userName1
    	//4.捐助回复留言：type projectId content createTime updateTime donateId userId1 userName1 userId2 userName2
    	//===========微信用户自动登陆start==============//
    			String openId ="";
    			String token = "";
    			String unionid = "";
    			StringBuffer url = request.getRequestURL();
    			String queryString = request.getQueryString();
				String perfecturl = "https://www.17xs.org/project/view_h5.do?" + queryString;//String perfecturl = "https://www.17xs.org/newReleaseProject/project_detail.do?" + queryString;//url + "?" + queryString;
    			String browser = UserUtil.Browser(request);
    			ApiFrontUser user1 = new ApiFrontUser();//用户
    			Integer userId = UserUtil.getUserId(request, response);
    	        if(userId != null && userId != 0)
    	        {
    		     	 user1 = userFacade.queryById(userId); 
    	        }
    	        else
    	        {
    	        	if(browser.equals("wx")){
    	        		String weixin_code = request.getParameter("code");
    	        		Map<String, Object> mapToken = new HashMap<String, Object>(8);
    	        		try {
    	          			 openId = (String) request.getSession().getAttribute("openId");
    		   				 Object OToken = redisService.queryObjectData("weixin_token");
    		   				 token = (String)OToken;
    		   				 System.out.println("userCenter_h5 >> weixin_code = "+weixin_code + "  openId = "+openId+"  OToken = "+OToken);
    	        			 if ("".equals(openId) || openId == null || OToken == null) {
    	        				if ("".equals(weixin_code) || weixin_code == null
    	        						|| weixin_code.equals("authdeny")) {
    	        					String url_weixin_code = H5Demo.getCodeRequest(perfecturl);
    	        					return webUtil.resMsg(-2, "", url_weixin_code, null);
    	        				}
    	        				mapToken = CommonUtils.getAccessTokenAndopenidRequest(weixin_code);
    	        				openId = mapToken.get("openid").toString();
    	        				unionid = mapToken.get("unionid").toString();
    	        				token = mapToken.get("access_token").toString();
    	        				request.getSession().setAttribute("openId", openId);
    	        				System.out.println("batch_project_list >> tenpay.getAccessTokenAndopenidRequest openId "+openId+" token = "+token);
    	        				redisService.saveObjectData("weixin_token", token, DateUtil.DURATION_HOUR_S);
    	        				//user1 = CommonUtils.queryUser(request,openId,token,unionid,response);
    	        				try
    	                		{
    	                			// 自动登录
    	        					SSOUtil.login(user1, request, response);
    	                		}
    	                		catch(Exception e)
    	                		{
    	                			logger.error("",e);
    	                			return webUtil.resMsg(2, "", "发生错误", null);
    	                		}
    	        				
    	        			}else{
    	        				//user1 = CommonUtils.queryUser(request,openId,token,unionid,response);
    	        				if (user1.getCoverImageId() == null) 
    	                		{
    	                			user1.setCoverImageUrl("https://www.17xs.org/res/images/user/4.jpg"); // 个人头像
    	                		}
    	                		else 
    	                		{
    	                			if(user1.getCoverImageId() != null && user1.getCoverImageId() == 0){
    	                				ApiBFile aBFile = fileFacade.queryBFileById(Integer.valueOf(user1.getCoverImageId()));
    	                				user1.setCoverImageUrl(aBFile.getUrl()); // 个人头像
    	                			}
    	                		}
    	        				}
    	        			
    	        		} catch (Exception e) {
    	        			logger.error("微信登录出现问题"+ e);
    	        			return webUtil.resMsg(2, "", "发生错误", null);
    	        		}
    	        	}
    	        	else{//返回到账号密码页面
    	        		String backUrlString="https://www.17xs.org/ucenter/user/Login_H5.do?flag=leadword_"+model.getProjectId();
    	        		return webUtil.resMsg(-2, "", backUrlString, null);
    	        	}
    	        	
    	        }
        ApiFrontUser user = userFacade.queryById(userId);
        if(model!=null){
        	if(model.getType()==0){
        		if(model.getLeavewordUserId()==null){//反馈留言
        			model.setLeavewordUserId(userId);
        			model.setLeavewordName(user.getNickName()==null?"":user.getNickName());
        			return  webUtil.resMsg(101, "", "", model);
        		}
        		else{//反馈回复留言
        			ApiFrontUser newuser = userFacade.queryById(model.getLeavewordUserId());
        			model.setReplyUserId(model.getLeavewordUserId());
        			model.setReplyName(newuser.getNickName()==null?"":newuser.getNickName());
        			model.setLeavewordUserId(userId);
        			model.setLeavewordName(user.getNickName()==null?"":user.getNickName());
        			
        			return webUtil.resMsg(102, "", "", model);
        		}
        	}
        	else{
        		if(model.getLeavewordUserId()==null){//捐助留言
        			model.setLeavewordUserId(userId);
        			model.setLeavewordName(user.getNickName()==null?"":user.getNickName());
        			return webUtil.resMsg(201, "", "", model);
        		}
        		else{//捐助回复留言
        			ApiFrontUser newuser = userFacade.queryById(model.getLeavewordUserId());
        			model.setReplyUserId(model.getLeavewordUserId());
        			model.setReplyName(newuser.getNickName()==null?"":newuser.getNickName());
        			model.setLeavewordUserId(userId);
        			model.setLeavewordName(user.getNickName()==null?"":user.getNickName());
        			
        			return webUtil.resMsg(202, "", "", model);
        		}
        	}
        }
        return webUtil.resMsg(0, "0002", "参数错误！", null);
    }
    
    /**
     * 添加留言
     * @param model
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("addNewLeaveWord")
    public Map<String, Object> addNewLeaveWord(ApiNewLeaveWord model,
    		HttpServletRequest request,HttpServletResponse response){
    	Integer userId = UserUtil.getUserId(request, response);
        if (userId == null)//未登录
        {
        	return webUtil.loginFailedRes(null);
        }
		ApiProject project=projectFacade.queryProjectDetail(model.getProjectId());
        if(project.getUserId().equals(userId)){
        	model.setIsRead(1);
        }else{
        	model.setIsRead(0);
        }
    	if(model!=null){
    		model.setCreateTime(new Date());
    		model.setUpdateTime(new Date());
    		if(model.getProjectFeedbackId()!=null && model.getProjectDonateId()==null){
    			model.setType(0);
    		}
    		if(model.getProjectFeedbackId()==null && model.getProjectDonateId()!=null){
    			model.setType(1);
					ApiDonateRecord d = donateRecordFacade.queryByDonateId(model.getProjectDonateId());
					if(d != null){
						ApiFrontUser user = userFacade.queryById(d.getUserId());
						ApiThirdUser thirdUser = new ApiThirdUser();
						thirdUser.setUserId(d.getUserId());
						thirdUser = userFacade.queryThirdUserByParam(thirdUser);
						if(thirdUser != null && thirdUser.getAccountNum() != null){
							ApiWeixinModel weiXinModel = new ApiWeixinModel();
							weiXinModel.setModel(7);
							weiXinModel.setState(0);
							weiXinModel.setUserId(d.getUserId());
							weiXinModel.setOpenId(thirdUser.getAccountNum());
							weiXinModel.setProjectId(project.getId());
							weiXinModel.setCreateTime(new Date());
							weiXinModel.setValue1(user.getNickName() + "，你有一条新的消息！");
							weiXinModel.setValue2(model.getContent());
							weiXinModel.setValue3(DateUtil.parseToDefaultDateTimeString(weiXinModel.getCreateTime()));
							weiXinModel.setValue4("请再帮【" + project.getTitle() + "】转发一下，让更多人帮助TA\n→点击帮助转发");
							userFacade.saveWeixinModel(weiXinModel);
						}
					}

    		}
    		if(model.getProjectFeedbackId()==null && model.getProjectDonateId()==null){
    			model.setType(2);
    		}
    		ApiResult result = projectFacade.saveNewLeaveWord(model);
    		if(result.getCode()==1)
    			return webUtil.resMsg(1, "0000", "添加成功！", null);
    	}
    	return webUtil.resMsg(0, "0002", "参数错误！", null);
    }
    
    /**
     * 添加留言pc端
     * @param model
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("addNewLeaveWordPc")
    public Map<String, Object> addNewLeaveWordPc(ApiNewLeaveWord model,
    		HttpServletRequest request,HttpServletResponse response){
    	Integer userId = UserUtil.getUserId(request, response);
    	ApiFrontUser user = new ApiFrontUser();
        if (userId == null)//未登录
        {
        	user.setUserName("游客");
    		user = userFacade.queryUserByParam(user);
    		userId=user.getId();
        	//return webUtil.loginFailedRes(null);
        }
		ApiProject project=projectFacade.queryProjectDetail(model.getProjectId());
        if(project.getUserId().equals(userId)){
        	model.setIsRead(1);
        }else{
        	model.setIsRead(0);
        }
    	if(model!=null){
    		model.setCreateTime(new Date());
    		model.setUpdateTime(new Date());
    		if(model.getProjectFeedbackId()!=null && model.getProjectDonateId()==null){
    			model.setType(0);
    		}
    		if(model.getProjectFeedbackId()==null && model.getProjectDonateId()!=null){
    			model.setType(1);
    		}
			model.setState(EnterpriseConstants.STATUS_SUCCESS);
    		ApiResult result = projectFacade.saveNewLeaveWord(model);
    		if(result.getCode()==1)
    			return webUtil.resMsg(1, "0000", "添加成功！", null);
    	}
    	return webUtil.resMsg(0, "0002", "参数错误！", null);
    }
    /**
     * loadmore留言
     * @param model
     * @param currentPage
     * @return
     */
    @ResponseBody
    @RequestMapping("loadMoreLeaveWord")
    public Map<String, Object> loadMoreLeaveWord(ApiNewLeaveWord model,
    		@RequestParam(value="currentPage",required=true)int currentPage,
    		int surplusTotal
    		){
    	 JSONArray items = new JSONArray();
    	if(model!=null){
    		ApiNewLeaveWord param = new ApiNewLeaveWord();
    		param.setProjectId(model.getProjectId());
    		if(model.getProjectDonateId()==null && model.getProjectFeedbackId()!=null){
    			param.setProjectFeedbackId(model.getProjectFeedbackId());
    			param.setType(0);
    		}
    		else if(model.getProjectDonateId()!=null && model.getProjectFeedbackId()==null){
				param.setProjectDonateId(model.getProjectDonateId());
				param.setType(1);
			}else{
				return webUtil.resMsg(0, "0002", "参数错误！", null);
			}
    		//加一因为最新插入了一条
    		int pageSize=100;
    	
    		ApiPage<ApiNewLeaveWord> page = projectFacade.queryApiNewLeaveWord(param,currentPage,pageSize);
    		if(page.getTotal()==0){
    			return webUtil.resMsg(0, "0040", "没有更多数据了 ！", null);
    		}
    		else if(page!=null){
    			
    			for(ApiNewLeaveWord apiNewLeaveWord:page.getResultData() ){
              	  JSONObject item = new JSONObject();
              	  item.put("id", apiNewLeaveWord.getId());
              	  item.put("projectId", apiNewLeaveWord.getProjectId());
              	  item.put("projectDonateId", apiNewLeaveWord.getProjectDonateId());
              	  item.put("projectFeedbackId", apiNewLeaveWord.getProjectFeedbackId());
              	  item.put("leavewordUserId", apiNewLeaveWord.getLeavewordUserId());
              	  item.put("replyUserId", apiNewLeaveWord.getReplyUserId());
              	  item.put("leavewordName", apiNewLeaveWord.getLeavewordName());
              	  item.put("replyName", apiNewLeaveWord.getReplyName());
              	  item.put("content", apiNewLeaveWord.getContent());
              	  item.put("updateTime", apiNewLeaveWord.getUpdateTime());
              	  item.put("createTime", apiNewLeaveWord.getCreateTime());
              	  item.put("currentPage",currentPage);
              	  item.put("total",page.getTotal());
              	  items.add(item);
                 }
    		  return webUtil.resMsg(1, "0000", "成功！",items);
    		}
    	}
    	return webUtil.resMsg(0, "0002", "参数错误！", null);
    }
    
    /**
     * 评论之后刷新回复的信息
     * @param model
     * @param currentPage
     * @return
     */
    @ResponseBody
    @RequestMapping("refleshLeaveWord")
    public Map<String, Object> refleshLeaveWord(ApiNewLeaveWord model,
    		@RequestParam(value="currentPage",required=true)int currentPage
    		){
    	 JSONArray items = new JSONArray();
    	if(model!=null){
    		ApiNewLeaveWord param = new ApiNewLeaveWord();
    		param.setProjectId(model.getProjectId());
    		if(model.getProjectDonateId()==null && model.getProjectFeedbackId()!=null){
    			param.setProjectFeedbackId(model.getProjectFeedbackId());
    			param.setType(0);
    		}
    		else if(model.getProjectDonateId()!=null && model.getProjectFeedbackId()==null){
				param.setProjectDonateId(model.getProjectDonateId());
				param.setType(1);
			}else{
				return webUtil.resMsg(0, "0002", "参数错误！", null);
			}
    	
    		ApiPage<ApiNewLeaveWord> page = projectFacade.queryApiNewLeaveWord(param,currentPage,20);
    		if(page ==null || page.getTotal()==0){
    			return webUtil.resMsg(0, "0040", "没有更多数据了 ！", null);
    		}
    		else if(page.getTotal() > 0){
    			for(ApiNewLeaveWord apiNewLeaveWord:page.getResultData() ){
              	  JSONObject item = new JSONObject();
              	  item.put("id", apiNewLeaveWord.getId());
              	  item.put("projectId", apiNewLeaveWord.getProjectId());
              	  item.put("projectDonateId", apiNewLeaveWord.getProjectDonateId());
              	  item.put("projectFeedbackId", apiNewLeaveWord.getProjectFeedbackId());
              	  item.put("leavewordUserId", apiNewLeaveWord.getLeavewordUserId());
              	  item.put("replyUserId", apiNewLeaveWord.getReplyUserId());
              	  item.put("leavewordName", apiNewLeaveWord.getLeavewordName());
              	  item.put("replyName", apiNewLeaveWord.getReplyName());
              	  item.put("content", apiNewLeaveWord.getContent());
              	  item.put("updateTime", apiNewLeaveWord.getUpdateTime());
              	  item.put("createTime", apiNewLeaveWord.getCreateTime());
              	  item.put("currentPage",currentPage);
              	  item.put("total",page.getTotal());
              	  items.add(item);
                 }
    		  return webUtil.resMsg(1, "0000", "成功！",items);
    		}
    	}
    	return webUtil.resMsg(0, "0002", "参数错误！", null);
    }
    
    /**
     * 项目详情
     * @param request
     * @param response
     * @param pageNum
     * @param pageSize
     * @return
     */
	@RequestMapping(value="/H5ProjectFeedBackByUserList",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject H5ProjectFeedBackByUserList(HttpServletRequest request, HttpServletResponse response, 
	    	@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
	        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
		JSONObject result = new JSONObject();
		JSONArray jsArray = new JSONArray();
		Integer userId = UserUtil.getUserId(request, response);
		String browser = UserUtil.Browser(request);
		ItemUtil itemUtil = new ItemUtil();
		if(userId == null){
			return itemUtil.noLogin(browser);
		}
	    try {
		ApiPage<ApiProjectFeedback> page = projectFacade.queryH5ProjectFeedbckByUserList(userId, pageNum, pageSize);
		if(page!=null && page.getTotal()>0 && (page.getTotal()-pageNum*pageSize>=0 || pageNum*pageSize-page.getTotal()<pageSize)) {
			for (ApiProjectFeedback f : page.getResultData()) {
				JSONObject userJson = new JSONObject();
				userJson.put("userHeadImage", f.getHeadImageUrl());
				userJson.put("nickName", f.getNickName());
				userJson.put("content", f.getContent());
				userJson.put("contentImage", f.getContentImageUrls());
				userJson.put("projectImage", f.getProjectImageUrl());
				userJson.put("projectLittleImage", f.getProjectImageMiddelUrl());
				userJson.put("projectMiddleImage", f.getProjectImageLittleUrl());
				userJson.put("projectTitle", f.getTitle());
				userJson.put("projectId", f.getProjectId());
				//捐赠次数和金额
				ApiDonateRecord apiDonateRecord = new ApiDonateRecord();
				apiDonateRecord.setProjectId(f.getProjectId());
				apiDonateRecord.setState(302);
				Double donateMoney = donateRecordFacade.countQueryDonateRecordList(apiDonateRecord);
				Integer donateNum = donateRecordFacade.countNumQueryByCondition(apiDonateRecord);
				userJson.put("donateNum", donateNum);
				userJson.put("donateMoney", donateMoney);
				//项目反馈的时间
				int d_value = DateUtil.minutesBetween(f.getFeedbackTime(),
						DateUtil.getCurrentTimeByDate());
				Boolean flag = DateUtil.dateFormat(f.getFeedbackTime()).equals(DateUtil.dateFormat(DateUtil.getCurrentTimeByDate()));
				if (d_value / 60 > 24 || !flag) {
					userJson.put("feedBackTime", DateUtil.dateStringChinesePatternYD("yyyy年MM月dd日", f.getFeedbackTime()));
				} else {
					if (d_value / 60 >= 1) {
						userJson.put("feedBackTime", d_value / 60 + "小时前");
					} else {
						if (d_value == 0) {
							userJson.put("feedBackTime", "刚刚");
						} else {
							userJson.put("feedBackTime", d_value + "分钟前");
						}
					}
				}
				jsArray.add(userJson);
			}
			result.put("count", page.getTotal());
			result.put("data", jsArray);
			return itemUtil.success(result);
		}
			return itemUtil.otherResult("没有更多数据了");
		}catch (Exception e){
			e.printStackTrace();
			return itemUtil.error();
		}

		}
	
	/**
	 * 个人信息留言列表
	 * @param request
	 * @param response
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/H5ProjectLeaveWordByUserList",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject H5ProjectLeaveWordByUserList(HttpServletRequest request, HttpServletResponse response, 
	    	@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
	        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
	        @RequestParam(value = "readState", required = false) Integer readState) {
		JSONObject result = new JSONObject();
		JSONArray jsArray = new JSONArray();
		ItemUtil itemUtil = new ItemUtil();
		Integer userId = UserUtil.getUserId(request, response);
		String browser = UserUtil.Browser(request);
		if (userId == null) {
			return itemUtil.noLogin(browser);
		}
		try {
			ApiPage<ApiDonateRecord> page = donateRecordFacade.queryByUserIdSelectLeaveWord(userId, readState, pageNum, pageSize);
			if (page != null && page.getTotal() > 0 && (page.getTotal() - pageNum * pageSize >= 0 || pageNum * pageSize - page.getTotal() < pageSize)) {
				for (ApiDonateRecord d : page.getResultData()) {
					JSONObject userJson = new JSONObject();
					JSONArray jsArray2 = new JSONArray();
					userJson.put("donatId", d.getId());

					userJson.put("nickName", d.getNickName());
					userJson.put("donatAmount", d.getDonatAmount());
					userJson.put("projectId", d.getProjectId());
					userJson.put("projectTitle", d.getProjectTitle());

					userJson.put("donatTime", DateUtil.parseToDefaultDateTimeString(d.getDonatTime()));
					//捐赠次数和金额
					ApiDonateRecord apiDonateRecord = new ApiDonateRecord();
					apiDonateRecord.setProjectId(d.getProjectId());
					Double donateMoney = donateRecordFacade.countQueryDonateRecordList(apiDonateRecord);
					Integer donateNum = donateRecordFacade.countNumQueryByCondition(apiDonateRecord);
					userJson.put("donateNum", donateNum);
					userJson.put("donateMoney", donateMoney);
					Integer isRead = 1;
					ApiNewLeaveWord apiNewLeaveWord = new ApiNewLeaveWord();
					apiNewLeaveWord.setProjectDonateId(d.getId());
					apiNewLeaveWord.setOrderBy("createTime");
					apiNewLeaveWord.setOrderDirection("ASC");
					ApiPage<ApiNewLeaveWord> page2 = projectFacade.queryApiNewLeaveWord(apiNewLeaveWord, 1, 100);
					boolean isRemove = true;
					if (page2 != null) {
						for (ApiNewLeaveWord n : page2.getResultData()) {
							if(readState != null && readState ==0 && n.getIsRead() == 0){//去除自己跟自己留言、自己回复自己
								if(n.getReplyUserId() == null && userId.equals(n.getLeavewordUserId())){//自己留言

								}else if(n.getReplyUserId() != null && n.getLeavewordUserId() != null
										&& userId.equals(n.getReplyUserId()) && userId.equals(n.getLeavewordUserId())){//自己回复自己

								}else{
									isRemove = false;
								}
							}else{
								isRemove = false;
							}
							JSONObject userJson2 = new JSONObject();
							userJson2.put("leavewordId", n.getId());
							userJson2.put("leavewordName", n.getLeavewordName());
							userJson2.put("leavewordUserId", n.getLeavewordUserId());
							userJson2.put("replyName", n.getReplyName());
							userJson2.put("replyUserId", n.getReplyUserId());
							userJson2.put("content", n.getContent());
							isRead = n.getIsRead() == 0 ? 0 : isRead;
							jsArray2.add(userJson2);
						}
					}
					userJson.put("leaveWord", jsArray2);
					userJson.put("leaveWordCount", page2.getTotal());
					userJson.put("donatIsRead", isRead);

					if(!isRemove){
						jsArray.add(userJson);
					}else{
						page.setTotal(page.getTotal()-1);
					}
				}
				result.put("count", page.getTotal());
				result.put("data", jsArray);
				result.put("userId", userId);
				ApiFrontUser apiFrontUser = new ApiFrontUser();
				apiFrontUser = userFacade.queryById(userId);
				result.put("userName", apiFrontUser.getNickName());
				return itemUtil.success(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return itemUtil.error();
		}
		return itemUtil.otherResult("没有更多数据了");
	}
	
	/**
	 * 个人信息系统消息
	 * @param request
	 * @param response
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/H5ProjectSystemNotifyByUserList",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject H5ProjectSystemNotifyByUserList(HttpServletRequest request, HttpServletResponse response, 
	    	@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
	        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
		JSONObject result = new JSONObject();
		JSONArray jsArray = new JSONArray();
		ItemUtil itemUtil = new ItemUtil();
		Integer userId = UserUtil.getUserId(request, response);
		String browser = UserUtil.Browser(request);
		if (userId == null) {
			return itemUtil.noLogin(browser);
		}
		try {
			ApiSystemNotify systemNotify = new ApiSystemNotify();
			systemNotify.setUserId(userId);
			systemNotify.setSender("系统消息");
			ApiPage<ApiSystemNotify> page = systemNotifyFacade.queryByCondition(systemNotify, pageNum, pageSize);
			if (page != null && page.getTotal() > 0 && (page.getTotal() - pageNum * pageSize >= 0 || pageNum * pageSize - page.getTotal() < pageSize)) {
				for (ApiSystemNotify s : page.getResultData()) {
					JSONObject userJson = new JSONObject();
					userJson.put("content", s.getContent());
					userJson.put("time", DateUtil.parseToDefaultDateTimeString(s.getCreateTime()));
					List<Integer> ids = new ArrayList<>();
					if(s != null && s.getTypeModelId() != null){
						String[] str = s.getTypeModelId().split(",");
						for (String string : str) {
							ids.add(Integer.valueOf(string));
						}
					}
					JSONArray jsArray2 = new JSONArray();
					ApiSystemTypeModel systemTypeModel = new ApiSystemTypeModel();
					systemTypeModel.setIds(ids);
					List<ApiSystemTypeModel> list = systemNotifyFacade.queryByIds(systemTypeModel);
					if(list != null){
						for (ApiSystemTypeModel apiSystemTypeModel : list) {
							JSONObject userJson2 = new JSONObject();
							userJson2.put("buttonName", apiSystemTypeModel.getButtonName());
							userJson2.put("link", apiSystemTypeModel.getLink());
							jsArray2.add(userJson2);
						}
					}
					userJson.put("nameAndLink", jsArray2);
					userJson.put("projectId", s.getProjectId());
					jsArray.add(userJson);
				}
				result.put("count", page.getTotal());
				result.put("data", jsArray);
				result.put("userId", userId);
				return itemUtil.success(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return itemUtil.error();
		}
		return itemUtil.otherResult("没有更多数据了");
	}
}
