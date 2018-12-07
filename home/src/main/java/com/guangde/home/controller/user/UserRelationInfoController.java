package com.guangde.home.controller.user;


import com.guangde.api.commons.ICommonFacade;
import com.guangde.api.commons.IFileFacade;
import com.guangde.api.homepage.IProjectFacade;
import com.guangde.api.user.IDonateRecordFacade;
import com.guangde.api.user.IUserFacade;
import com.guangde.api.user.IUserRelationInfoFacade;
import com.guangde.entry.ApiBFile;
import com.guangde.entry.ApiFrontUser;
import com.guangde.entry.ApiFrontUser_invoice;
import com.guangde.home.utils.*;
import com.guangde.home.vo.common.Page;
import com.guangde.home.vo.user.UInvoice;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.redis.utils.RedisService;
import com.tenpay.demo.H5Demo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("user")
public class UserRelationInfoController {

	private final Logger logger = LoggerFactory.getLogger(UserRelationInfoController.class);

	@Autowired
	private IUserRelationInfoFacade userRelationInfoFacade;
	
	@Autowired
	private IDonateRecordFacade donateRecordFacade;
	
	@Autowired
	private IUserFacade userFacade;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private IFileFacade fileFacade ; 
	
	@Autowired
	private IProjectFacade projectFacade ; 
	@Autowired
	private ICommonFacade commonFacade;

	
	/**
	 * 索取发票
	 * 	填写地址页
	 * @return
	 */
	@RequestMapping(value = "toAddress")
	public ModelAndView toAddress(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView("h5/userRelation/invoiceAddress");
		//Integer userId = UserUtil.getUserId(request, response);
		//--------------------授权登录-------------------------
		String browser = UserUtil.Browser(request);
		Integer userId = UserUtil.getUserId(request, response);
		String openId ="";
		String token = "";
		String unionid = "";
		StringBuffer url = request.getRequestURL();
		String queryString = request.getQueryString();
		String perfecturl = url + "?" + queryString;
		ApiFrontUser user = new ApiFrontUser();
        if(userId == null || userId == 0)
        {
        	if(browser.equals("wx")){
        		String weixin_code = request.getParameter("code");
        		Map<String, Object> mapToken = new HashMap<String, Object>(8);
        		try {
	   				 Object OToken = redisService.queryObjectData("weixin_token");
	   				 token = (String)OToken;
	   				 System.out.println("userCenter_h5 >> weixin_code = "+weixin_code + "  openId = "+openId+"  OToken = "+OToken);
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
        				System.out.println("userCenter_h5 >> tenpay.getAccessTokenAndopenidRequest openId "+openId+" token = "+token);
        				redisService.saveObjectData("weixin_token", token, DateUtil.DURATION_HOUR_S);
        			}
        			//user = CommonUtils.queryUser(request,openId,token,unionid,response);
        		} catch (Exception e) {
        			logger.error("H5RedPacketsController>>getRedPaket>>"+ e);
        		}
        		
        		if (user.getCoverImageId() == null) {
        			user.setCoverImageUrl("https://www.17xs.org/res/images/user/4.jpg"); // 个人头像
        		} else {
        			if(user.getCoverImageId() != null && user.getCoverImageId() == 0){
        				ApiBFile aBFile = fileFacade.queryBFileById(Integer.valueOf(user.getCoverImageId()));
        				user.setCoverImageUrl(aBFile.getUrl()); // 个人头像
        			}
        		}
        		try{
        			
        			// 自动登录
        			SSOUtil.login(user, request, response);
        			//view = new ModelAndView("redirect:" + perfecturl);
					//return view;
        		}
        		catch(Exception e)
        		{
        			logger.error("",e);
        		}
        	}
        	else
        	{
        		//to do >> 暂时跳转到登陆页
				view = new ModelAndView("redirect:/ucenter/user/Login_H5.do");
				view.addObject("flag", "toAddress");
				return view;
        		
        	}
        }else {
        	user = userFacade.queryById(userId);
		}
		//===========微信用户自动登陆end==============//
		//ApiFrontUser user = userFacade.queryById(userId);
		view.addObject("user", user);
		return view;
	}
	
	/**
	 * 索取发票
	 * 	发票金额页
	 * @return
	 */
	@RequestMapping(value = "toInvoice")
	public ModelAndView toInvoice(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView("h5/userRelation/invoice");
		Integer userId = UserUtil.getUserId(request, response);
		if(userId == null || userId == 0){
			view = new ModelAndView("redirect:/user/sso/login.do");
			return view ; 
		}
		return view;
	}
	
	/**
	 * 索取发票
	 * 	发票详情页
	 * @return
	 */
	@RequestMapping(value = "detailInvoice")
	public ModelAndView toDetailInvoice(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "id", required = false, defaultValue = "") Integer id) {
		ModelAndView view = new ModelAndView("h5/userRelation/detailInvoice");
		Integer userId = UserUtil.getUserId(request, response);
		if(userId == null || userId == 0){
			view = new ModelAndView("redirect:/user/sso/login.do");
			return view ; 
		}
		view.addObject("id",id);
		return view;
	}
	
	/**
	 * 索取发票
	 * 	发票记录列表
	 * @return
	 */
	@RequestMapping(value = "invoiceRecordList")
	public ModelAndView invoiceRecordList(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView("h5/userRelation/invoiceRecordList");
		Integer userId = UserUtil.getUserId(request, response);
		if(userId == null || userId == 0){
			view = new ModelAndView("redirect:/user/sso/login.do");
			return view ; 
		}
		return view;
	}
	
	@RequestMapping(value = "addInvoice")
	@ResponseBody
	public Map<String, Object> addInvoice(@ModelAttribute ApiFrontUser_invoice userInvoice,
			HttpServletRequest request, HttpServletResponse response) {
		Integer userId = UserUtil.getUserId(request, response);
		if(userId == null || userId == 0)
		{
			 return webUtil.resMsg(-1, "0001", "未登入", null);
		}
		if(userInvoice == null)
		{
			return webUtil.resMsg(-1, "0002", "参数错误", null);
		}
		else
		{
			if(userInvoice.getInvoiceAmount() >= 100)
			{
				userInvoice.setIsFree(0);// 0 包邮  1 不包邮(到付)
			}
			else
			{
				userInvoice.setIsFree(1);
			}
		}
		if(StringUtils.isEmpty(userInvoice.getInvoiceHead())){
			return webUtil.resMsg(0, "0007", "发票抬头不能空", null);
		}
		if(userInvoice.getInvoiceAmount() == 0){
			return webUtil.resMsg(0, "0007", "发票金额不能为0", null);
		}
		userInvoice.setUserId(userId);
		userInvoice.setCreateTime(new Date());
		userInvoice.setLastUpdateTime(new Date());
		userInvoice.setState(300);
		ApiResult result = userRelationInfoFacade.saveUserInvoice(userInvoice);
		if (1 != result.getCode()) {
			return webUtil.resMsg(0, "0007", "用户发票入库失败", null);
		}
		else
		{
			Map<String,Object> r = webUtil.successRes(null);
			return  r ;
		}
	}
	
	/**
	 * 发票记录列表
	 * @param request
	 * @param response
	 * @param page
	 * @param pageNum
	 * @param userId
	 * @return
	 */
 
    @RequestMapping("invoiceList")
    @ResponseBody
    public Map<String, Object> invoiceList(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "pageNum", required = false, defaultValue = "10") Integer pageNum,
        @RequestParam(value = "userId", required = false) Integer userId,@RequestParam(value = "state", required = false) Integer state)
    {
        
        if (userId == null)
        {
            // 1.验证是否登入
            userId = UserUtil.getUserId(request, response);
            if (userId == null)
            {
                return webUtil.loginFailedRes(null);
            }
        }
        
        // 分页计算
        Page p = new Page();
        p.setPageNum(pageNum);
        p.setPage(page);
        
        ApiFrontUser_invoice r = new ApiFrontUser_invoice();
        r.setUserId(userId);
        r.setState(state);
        ApiPage<ApiFrontUser_invoice> invoices = userRelationInfoFacade.queryInvoiceByCodition(r, p.getPage(), p.getPageNum());
        List<UInvoice> list = new ArrayList<UInvoice>();
        if (invoices != null)
        {
        	SimpleDateFormat sm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        	for(ApiFrontUser_invoice inv :invoices.getResultData() )
        	{
        		UInvoice  ui = new UInvoice();
        		ui.setAddressId(inv.getAddressId());
        		ui.setName(inv.getName());
        		ui.setMobile(inv.getMobile());
        		ui.setProvince(inv.getProvince());
        		ui.setCity(inv.getCity());
        		ui.setArea(inv.getArea());
        		ui.setDetailAddress(inv.getDetailAddress());
        		ui.setContent(inv.getContent());
        		ui.setCreateTime(sm.format(inv.getCreateTime()));
        		ui.setLastUpdateTime(sm.format(inv.getLastUpdateTime()));
        		ui.setId(inv.getId());
        		ui.setInvoiceAmount(inv.getInvoiceAmount());
        		ui.setInvoiceHead(inv.getInvoiceHead());
        		ui.setMailAmount(inv.getMailAmount());
        		ui.setMailCode(inv.getMailCode());
        		ui.setMailCompany(inv.getMailCompany());
        		ui.setIsFree(inv.getIsFree());
        		ui.setState(inv.getState());
        		ui.setUserId(inv.getUserId());
        		list.add(ui);
        	}
        	p.setData(list);
            p.setNums(invoices.getTotal());
        }
        else
        {
            p.setNums(0);
        }
        if (p.getTotal() == 0)
        {
            return webUtil.resMsg(2, "0002", "发票记录列表为空", p);
        }
        else
        {
            return webUtil.resMsg(1, "0000", "成功", p);
        }
    }

	
}
