package com.guangde.home.controller.deposit;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.guangde.api.homepage.IProductFacade;
import com.guangde.api.homepage.IProjectFacade;
import com.guangde.api.user.ICompanyFacade;
import com.guangde.api.user.IDonateRecordFacade;
import com.guangde.api.user.IUserFacade;
import com.guangde.dto.ApiBuyProductFormDTO;
import com.guangde.dto.ApiDepositFormDTO;
import com.guangde.dto.ProjectDetailDTO;
import com.guangde.entry.*;
import com.guangde.enums.ResultEnum;
import com.guangde.home.constant.OrderTypePreConstants;
import com.guangde.home.constant.RedisConstans;
import com.guangde.home.constant.UrlConstants;
import com.guangde.home.form.BuyProductForm;
import com.guangde.home.utils.*;
import com.guangde.home.vo.deposit.WeixinPayVO;
import com.guangde.pojo.ApiResult;
import com.redis.utils.RedisService;
import com.tenpay.demo.Demo;
import com.tenpay.demo.H5Demo;
import com.tenpay.demo.WxPayDto;
import com.tenpay.utils.TenpayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * 支付宝成功处理. 要确定你的收款支付宝账号，合作身份者ID，商户的私钥是正确的
 * 同时要把AlipaySubmit.buildRequest的输出文本显示在jsp上
 */
@Controller
@RequestMapping("/tenpay")
public class TenpayAction extends DepositBaseAction {

	public static String notify_url = "http://api.wyxcszh.com/tenpay/asyncresult/TRANSNUM/";

	// 服务器异步通知页面路径
	//public static final String notify_url = "https://www.17xs.org/tenpay/async.do";
	// 页面跳转同步通知页面路径
	//public static final String return_url = "https://www.17xs.org/tenpay/return_url.do";
	//二维码的底版
	//二维ima的图标
	public static final String srcPath = "/res/images/charge/logo-pay.png";


	Logger logger = LoggerFactory.getLogger(TenpayAction.class);

	@Autowired
	private IDonateRecordFacade donateRecordFacade;

	@Autowired
	private IProjectFacade projectFacade;

	@Autowired
	private IUserFacade userFacade;

	@Autowired
	private ICompanyFacade companyFacade;

	@Autowired
	private RedisService redisService;

	@Autowired
	private IProductFacade productFacade;


	//生成项目捐赠记录，返回微信支付参数
	@RequestMapping(value = "/deposit/project")
	@ResponseBody
	public ApiResult projectDeposit(HttpServletRequest request, HttpServletResponse response,
									@Validated com.guangde.home.form.DepositForm form, BindingResult bind){
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");

		if(bind.hasErrors()){
			return new ApiResult(ResultEnum.OtherError.getCode(), bind.getFieldError().getDefaultMessage());
		}

		ApiResult result = CommonUtils.valUserLogin(request);
		if (result.getCode() != 1) {
			return result;
		}
		Integer userId = Integer.valueOf(result.getData().toString());
		form.setUserId(userId);
		ApiFrontUser user = new ApiFrontUser();
		user = CommonUtils.getRegisterResource(user, request);
		form.setSource(user.getRegistrSoure());

		ApiDepositFormDTO dto = new ApiDepositFormDTO();
		BeanUtils.copyProperties(form, dto);

		//生成订单号
		String transNum = OrderTypePreConstants.ORDER_PRE_DONATE + StringUtil.uniqueCode() + StringUtil.addZeroForNum(userId+"", 9);
		dto.setTransNum(transNum);

		result = donateRecordFacade.buyDonate(dto);
		if(result.getCode() != 1){
			return result;
		}

		//扫码支付
		ProjectDetailDTO detailDTO = projectFacade.queryProjectDetailById(form.getProjectId());

		ApiThirdUser thirdUser = new ApiThirdUser();
		thirdUser.setUserId(userId);
		thirdUser = userFacade.queryThirdUserByParam(thirdUser);
		if(thirdUser == null) {
			return new ApiResult(ResultEnum.Error);
		}

		//返回微信支付参数
		WxPayDto tpWxPay1 = new WxPayDto();
		tpWxPay1.setAttach("project");
		tpWxPay1.setOrderId(transNum);
		tpWxPay1.setSpbillCreateIp(SSOUtil.getUserIP(request));
		tpWxPay1.setTotalFee(form.getMoney()+"");
		tpWxPay1.setBody("认捐“"+detailDTO.getTitle()+"”项目");

		// 建立请求
		try {
			tpWxPay1.setPayWay("1");
			tpWxPay1.setOpenId(thirdUser.getAccountNum());
			tpWxPay1.setNotifyUrl(notify_url.replace("TRANSNUM",tpWxPay1.getOrderId()));
			SortedMap<String, String> map;
			map = H5Demo.getPayweixinview(tpWxPay1);
			System.out.println("commonPay map>>>"+map);
			WeixinPayVO vo = new WeixinPayVO();
			vo.setAppId(map.get("appId"));
			vo.setTradeNo(tpWxPay1.getOrderId());
			vo.setTimestamp(map.get("timeStamp"));
			vo.setNoncestr(map.get("nonceStr"));
			vo.setPackageValue(map.get("packageValue"));
			vo.setPaySign(map.get("paySign"));
			vo.setPaysignType(map.get("signType"));
			String config_timestamp = String.valueOf(new Date().getTime());
			String config_noncestr = H5Demo.getNonceStr();
			vo.setConfig_timestamp(config_timestamp);
			vo.setConfig_noncestr(config_noncestr);

			//微信调度的唯一凭证AccessToken失效，要同时更新jsTicket这个临时凭证
			String jsTicket,accessToken;
			accessToken = TenpayUtil.queryAccessToken();
			redisService.saveObjectData(RedisConstans.AccessToken, accessToken, DateUtil.DURATION_HOUR_S);
			jsTicket = TenpayUtil.queryJsapiTicket(accessToken);
			redisService.saveObjectData(RedisConstans.JsapiTicket , jsTicket, DateUtil.DURATION_HOUR_S);
			vo.setSignature(H5Demo.getSignature(jsTicket, config_timestamp, config_noncestr, form.getWeixinUrl()));

			return new ApiResult(ResultEnum.Success, vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ApiResult(ResultEnum.Error);
	}


	//h5支付
	@RequestMapping(value = "/deposit/h5/project")
	@ResponseBody
	public ApiResult projectH5Deposit(HttpServletRequest request, HttpServletResponse response,
									  @Validated com.guangde.home.form.DepositForm form, BindingResult bind){
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");

		if(bind.hasErrors()){
			return new ApiResult(ResultEnum.OtherError.getCode(), bind.getFieldError().getDefaultMessage());
		}

		ApiResult result = CommonUtils.valUserLogin(request);
		if (result.getCode() != 1) {
			return result;
		}

		Integer userId = Integer.valueOf(result.getData().toString());
		form.setUserId(userId);
		ApiFrontUser user = new ApiFrontUser();
		user = CommonUtils.getRegisterResource(user, request);
		form.setSource(user.getRegistrSoure());

		ApiDepositFormDTO dto = new ApiDepositFormDTO();
		BeanUtils.copyProperties(form, dto);

		//生成订单号
		String transNum = OrderTypePreConstants.ORDER_PRE_DONATE + StringUtil.uniqueCode() + StringUtil.addZeroForNum(user.getId()+"", 9);
		form.setTransNum(transNum);

		result = donateRecordFacade.buyDonate(dto);
		if(result.getCode() != 1){
			return result;
		}

		ProjectDetailDTO detailDTO = projectFacade.queryProjectDetailById(form.getProjectId());

		String redirect_url = "http://www.wyxcszh.com";

		//h5微信支付
		WxPayDto tpWxPay1 = new WxPayDto();
		tpWxPay1.setOrderId(transNum);
		String addressIp = SSOUtil.getUserIP(request);
		System.out.println("h5支付ip: " + addressIp);
		tpWxPay1.setSpbillCreateIp(addressIp);
		tpWxPay1.setTotalFee(form.getMoney()+"");

		tpWxPay1.setBody("认捐“"+detailDTO.getTitle()+"”项目");

		tpWxPay1.setNotifyUrl(notify_url.replace("TRANSNUM",tpWxPay1.getOrderId()));

		// 建立请求
		try {
			//生成二维码
			String mweb_url = Demo.getH5WeixinPayurl(tpWxPay1);
			System.out.print("mweb url = " + mweb_url);
			return new ApiResult(ResultEnum.Success,  mweb_url +"&redirect_url=" + URLEncoder.encode(redirect_url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ApiResult(ResultEnum.Error);
	}




	//生成订单
	@RequestMapping(value = "/product/create/order", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult createProductOrder(HttpServletRequest request, HttpServletResponse response,
										@Validated BuyProductForm form, BindingResult bind){
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");

		if(bind.hasErrors()){
			return new ApiResult(ResultEnum.OtherError.getCode(), bind.getFieldError().getDefaultMessage());
		}

		ApiResult result = CommonUtils.valUserLogin(request);
		if (result.getCode() != 1) {
			return result;
		}
		Integer userId = Integer.valueOf(result.getData().toString());
		form.setUserId(userId);
		ApiFrontUser user = new ApiFrontUser();
		user = CommonUtils.getRegisterResource(user, request);
		form.setSource(user.getRegistrSoure());

		ApiBuyProductFormDTO dto = new ApiBuyProductFormDTO();
		BeanUtils.copyProperties(form, dto);

		//生成订单号
		String transNum = OrderTypePreConstants.ORDER_PRE_BUY + StringUtil.uniqueCode() + StringUtil.addZeroForNum(userId+"", 9);
		dto.setTransNum(transNum);

		return donateRecordFacade.buyProduct(dto);
	}

	//购买商品
	@RequestMapping(value = "/product/deposit")
	@ResponseBody
	public ApiResult productDeposit(HttpServletRequest request, HttpServletResponse response,
									@RequestParam("transNum") String transNum,
									@RequestParam("weixinUrl") String weixinUrl){
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");

		ApiResult result = CommonUtils.valUserLogin(request);
		if (result.getCode() != 1) {
			return result;
		}
		Integer userId = Integer.valueOf(result.getData().toString());

		ApiProductOrder apiProductOrder = productFacade.queryProductOrder(transNum, userId);
		if(apiProductOrder == null || apiProductOrder.getState() != 101){
			return new ApiResult(ResultEnum.OrderError);
		}

		ApiThirdUser thirdUser = new ApiThirdUser();
		thirdUser.setUserId(userId);
		thirdUser = userFacade.queryThirdUserByParam(thirdUser);
		if(thirdUser == null) {
			return new ApiResult(ResultEnum.Error);
		}

		//返回微信支付参数
		WxPayDto tpWxPay1 = new WxPayDto();
		tpWxPay1.setAttach("product");
		tpWxPay1.setOrderId(transNum);
		tpWxPay1.setSpbillCreateIp(SSOUtil.getUserIP(request));
		tpWxPay1.setTotalFee(apiProductOrder.getAmountMoney()+"");
		tpWxPay1.setBody("购买“"+apiProductOrder.getProductName()+"”商品");

		// 建立请求
		try {
			tpWxPay1.setPayWay("1");
			tpWxPay1.setOpenId(thirdUser.getAccountNum());
			tpWxPay1.setNotifyUrl(notify_url.replace("TRANSNUM",tpWxPay1.getOrderId()));
			SortedMap<String, String> map;
			map = H5Demo.getPayweixinview(tpWxPay1);
			System.out.println("commonPay map>>>"+map);
			WeixinPayVO vo = new WeixinPayVO();
			vo.setAppId(map.get("appId"));
			vo.setTradeNo(tpWxPay1.getOrderId());
			vo.setTimestamp(map.get("timeStamp"));
			vo.setNoncestr(map.get("nonceStr"));
			vo.setPackageValue(map.get("packageValue"));
			vo.setPaySign(map.get("paySign"));
			vo.setPaysignType(map.get("signType"));
			String config_timestamp = String.valueOf(new Date().getTime());
			String config_noncestr = H5Demo.getNonceStr();
			vo.setConfig_timestamp(config_timestamp);
			vo.setConfig_noncestr(config_noncestr);

			//微信调度的唯一凭证AccessToken失效，要同时更新jsTicket这个临时凭证
			String jsTicket,accessToken;
			accessToken = TenpayUtil.queryAccessToken();
			redisService.saveObjectData(RedisConstans.AccessToken, accessToken, DateUtil.DURATION_HOUR_S);
			jsTicket = TenpayUtil.queryJsapiTicket(accessToken);
			redisService.saveObjectData(RedisConstans.JsapiTicket , jsTicket, DateUtil.DURATION_HOUR_S);
			vo.setSignature(H5Demo.getSignature(jsTicket, config_timestamp, config_noncestr, weixinUrl));

			return new ApiResult(ResultEnum.Success, vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ApiResult(ResultEnum.Error);
	}
	/**
	 * 异步通知付款状态的Controller
	 *
	 */
	@RequestMapping(value = "/async")
	@ResponseBody
	public String async(HttpServletRequest request, HttpServletResponse response) {
		String tradeNo = request.getParameter("out_trade_no");
		String tradeStatus = request.getParameter("result_code");
		String notifyId = request.getParameter("transaction_id");
		String ep = request.getParameter("attach");
		logger.info("ep>>>"+ep);
		logger.info("tradeStatus>>>"+tradeStatus);
			if (StringUtils.isNotEmpty(tradeStatus) && tradeStatus.equals("SUCCESS")) {
				if(companyFacade.checkAlipayResult(tradeNo, notifyId)){
					if("4".equals(ep)){logger.info("ep>>>freezType");
						donateRecordFacade.buyDonateResult(tradeNo, notifyId, true, "","freezType");
					}else if("5".equals(ep)) {logger.info("ep>>>alipay");
						donateRecordFacade.buyDonateResult(tradeNo, notifyId, true, "","alipay");
					}else{logger.info("ep>>>null");
						donateRecordFacade.buyDonateResult(tradeNo, notifyId, true, "","");
					}
				}else {
					logger.info("对交易号"+tradeNo+"：：微信ID"+notifyId+"重复回调的屏蔽");
				}
				// 要写的逻辑。自己按自己的要求写
				logger.info("微信充值成功>>>"+tradeNo);
				System.out.println(">>>>>充值成功 微信" + tradeNo);
			}
			return "/deposit/success";

	}
	@ResponseBody
	@RequestMapping(value = "result")
	public Map<String, Object> getPayResult(@RequestParam(value = "OrderId", required = true) String OrderId,
			HttpServletRequest request, HttpServletResponse response){


		WxPayDto tpWxPay1 = new WxPayDto();
	    tpWxPay1.setOrderId(OrderId);
	    //ApiDonateRecord dd = donateRecordFacade.queryPayNoticeByTranNum(OrderId);
	    String[] msg;
	    /*if(dd!=null&&dd.getProjectId()!=null){
	    	ApiProject project = projectFacade.queryProjectDetail(dd.getProjectId());*/
		msg = Demo.getPayResult(tpWxPay1).split("\\|");

	    if("SUCCESS".equals(msg[0]) && !"null".equals(msg[2])){
	    	Integer pId = donateRecordFacade.queryProjectIdByCapitalinout(OrderId);
	    	Integer userId = UserUtil.getUserId(request, response);
	    	String tradeNo = msg[1];
	    	String notifyId = msg[2];
	    	String attach=msg[3];
	    	if(companyFacade.checkAlipayResult(tradeNo, notifyId)){
				if("4".equals(attach)){
					donateRecordFacade.buyDonateResult(tradeNo, notifyId, true, "","freezType");
				}else if("5".equals(attach)) {
					donateRecordFacade.buyDonateResult(tradeNo, notifyId, true, "","tenpay");
				}else{
					donateRecordFacade.buyDonateResult(tradeNo, notifyId, true, "","");
				}
			}
	    	if(userId == null){
	    		return webUtil.successRes(pId);
	    	}else {
	    		ApiFrontUser user = userFacade.queryById(userId);
	    		if("individualUsers".equals(user.getUserType())){
	    			return webUtil.successRes("individualUsers");
	    		}else if ("enterpriseUsers".equals(user.getUserType())) {
	    			return webUtil.successRes("enterpriseUsers");
				}
	    		else if("touristUsers".equals(user.getUserType()))
				{
	    			return webUtil.successRes(pId);
				}
			}
	    }
		return webUtil.failedRes("0123", "没有这条数据", null) ;
	}

	@ResponseBody
	@RequestMapping(value = "h5result")
	public ModelAndView getPayH5Result(@RequestParam(value = "OrderId", required = true) String OrderId,
			@RequestParam(value = "projectId", required = true) String projectId,
			HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView("redirect:/project/view_h5.do?projectId="+projectId);
		WxPayDto tpWxPay1 = new WxPayDto();
	    tpWxPay1.setOrderId(OrderId);
	    ApiDonateRecord dd = donateRecordFacade.queryPayNoticeByTranNum(OrderId);
	    String[] msg;
	    if(dd!=null&&dd.getProjectId()!=null){
	    	ApiProject project = projectFacade.queryProjectDetail(dd.getProjectId());
	    	ApiPayConfig config = new ApiPayConfig();
	      	config.setUserId(project.getUserId());
	      	List<ApiPayConfig> list = projectFacade.queryByParam(config);
	      	if(list.size()>0){
	      		msg = Demo.getPayResult(tpWxPay1,list.get(0)).split("\\|");
	      	}
	      	else{
	      		msg = Demo.getPayResult(tpWxPay1).split("\\|");
	      	}
	    }
	    else{
	    	msg = Demo.getPayResult(tpWxPay1).split("\\|");
	    }
	    //String[] msg = Demo.getPayResult(tpWxPay1).split("\\|");
	    if("SUCCESS".equals(msg[0]) && !"null".equals(msg[2])){
	    	String tradeNo = msg[1];
	    	String notifyId = msg[2];
	    	String attach=msg[3];
	    	if(companyFacade.checkAlipayResult(tradeNo, notifyId)){
				if("4".equals(attach)){
					donateRecordFacade.buyDonateResult(tradeNo, notifyId, true, "","freezType");
				}else if("5".equals(attach)) {
					donateRecordFacade.buyDonateResult(tradeNo, notifyId, true, "","tenpay");
				}else{
					donateRecordFacade.buyDonateResult(tradeNo, notifyId, true, "","");
				}
			}
	    }
	    return view;
	}

	@ResponseBody
	@RequestMapping(value = "/asyncresult/{transNum}")
	public void getPayAsyncResult(@PathVariable("transNum") String transNum,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("param orderId >>"+ transNum);

		//根据订单号查询是否支付成功
		ApiDonateRecord dd = donateRecordFacade.queryPayNoticeByTranNum(transNum);
		if(dd != null && dd.getState() != 300){
			//重复回调
			response.getWriter().print("SUCCESS");
			return;
		}
		WxPayDto tpWxPay1 = new WxPayDto();
		tpWxPay1.setOrderId(transNum);
		String[] msg = Demo.getPayResult(tpWxPay1).split("\\|");
		System.out.println("Demo.getPayResult(tpWxPay1)>>>>>>"+Demo.getPayResult(tpWxPay1));
		if(msg.length<4){
			return ;
		}

		if("SUCCESS".equals(msg[0]) && !"null".equals(msg[2])) {
			System.out.println("Demo.getPayResult>>>>" + msg[0] + ">>>" + msg[1] + ">>>" + msg[2] + ">>>" + msg[3]);
			String tradeNo = msg[1];
			String notifyId = msg[2];
			String attach = msg[3];
			//1.项目
			//增加项目捐赠金额,捐赠次数   增加用户捐赠金额、捐赠次数
			//判断订单状态是否是未支付状态
			//2.商品
			ApiResult result = donateRecordFacade.buyDonateResult(tradeNo, notifyId, attach);
			if(result.getCode() == 1){
				response.getWriter().print("SUCCESS");
			}
		}
	}

}
