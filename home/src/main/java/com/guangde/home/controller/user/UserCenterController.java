package com.guangde.home.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guangde.api.commons.ICommonFacade;
import com.guangde.api.homepage.IProjectFacade;
import com.guangde.api.homepage.IProjectVolunteerFacade;
import com.guangde.api.user.IDonateRecordFacade;
import com.guangde.api.user.IPayMoneyRecordFacade;
import com.guangde.api.user.IUserFacade;
import com.guangde.api.user.IUserRelationInfoFacade;
import com.guangde.entry.*;
import com.guangde.enums.ResultEnum;
import com.guangde.home.annotation.ActionLog;
import com.guangde.home.constant.UrlConstants;
import com.guangde.home.form.FrontUserAddressForm;
import com.guangde.home.utils.*;
import com.guangde.home.vo.user.UserCard;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/center")
public class UserCenterController {

	@Autowired
	private IProjectFacade projectFacade;
	@Autowired
	private IUserFacade userFacade;
	@Autowired
	private IPayMoneyRecordFacade payMoneyRecordFacade;
	@Autowired
	private IDonateRecordFacade donateRecordFacade;
	@Autowired
	private IUserRelationInfoFacade userRelationInfoFacade;
	@Autowired
	private IProjectVolunteerFacade projectVolunteerFacade;
	@Autowired
	private ICommonFacade commonFacade;

	/**
	 * 用户地址列表
	 * @param request
	 * @param pageNum
	 * @param pageSize
     * @return
     */
	@RequestMapping(value = "/address/list", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult addressList(HttpServletRequest request, HttpServletResponse response,
								 @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
								 @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		ApiResult result = CommonUtils.valUserLogin(request);
		if (result.getCode() != 1) {
			return result;
		}
		Integer userId = Integer.valueOf(result.getData().toString());
		return userRelationInfoFacade.queryUserAddressPageByUserId(userId, pageNum, pageSize);
	}

	/**
	 * 新增收货地址
	 * @param request
	 * @param addressForm
	 * @param bindingResult
     * @return
     */
	@RequestMapping(value = "/add/address", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult addAddress(HttpServletRequest request, HttpServletResponse response,
								@Validated FrontUserAddressForm addressForm, BindingResult bindingResult){
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(bindingResult.hasErrors()){
			return new ApiResult(ResultEnum.OrderError.getCode(), bindingResult.getFieldError().getDefaultMessage());
		}
		ApiResult result = CommonUtils.valUserLogin(request);
		if(result.getCode() != 1){
			return result;
		}

		Integer userId = Integer.valueOf(result.getData().toString());
		addressForm.setUserId(userId);

		ApiFrontUser_address apiFrontUser_address = new ApiFrontUser_address();
		BeanUtils.copyProperties(addressForm, apiFrontUser_address);
		return userRelationInfoFacade.saveUserAddress(apiFrontUser_address);
	}

	/**
	 * 编辑用户地址
	 * @param request
	 * @param address
     * @return
     */
	@RequestMapping(value = "/edit/address", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult editAddress(HttpServletRequest request, HttpServletResponse response,
								ApiFrontUser_address address){
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");

		ApiResult result = CommonUtils.valUserLogin(request);
		if(result.getCode() != 1){
			return result;
		}

		Integer userId = Integer.valueOf(result.getData().toString());
		address.setUserId(userId);
		return userRelationInfoFacade.updateUserAddress(address);
	}

	/**
	 * 删除用户地址
	 * @param request
	 * @param id
     * @return
     */
	@RequestMapping(value = "/delete/address", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult deleteAddress(HttpServletRequest request, HttpServletResponse response,
								   @RequestParam("id") String id){

		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");

		ApiResult result = CommonUtils.valUserLogin(request);
		if(result.getCode() != 1){
			return result;
		}
		Integer userId = Integer.valueOf(result.getData().toString());
		return userRelationInfoFacade.deleteUserAddress(Arrays.asList(id.split(",")), userId);
	}

	/**
	 * 默认用户地址
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/default/address", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult defaultAddress(HttpServletRequest request, HttpServletResponse response,
								   @RequestParam("id") Integer id){

		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");

		ApiResult result = CommonUtils.valUserLogin(request);
		if(result.getCode() != 1){
			return result;
		}
		Integer userId = Integer.valueOf(result.getData().toString());
		return userRelationInfoFacade.defaultUserAddress(id, userId);
	}


	//根据id查询地址
	@RequestMapping(value = "/address/detail", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult addressDetail(HttpServletRequest request, HttpServletResponse response,
								   @RequestParam("id") Integer id){

		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");

		ApiResult result = CommonUtils.valUserLogin(request);
		if(result.getCode() != 1){
			return result;
		}
		Integer userId = Integer.valueOf(result.getData().toString());
		return userRelationInfoFacade.queryUserAddress(id, userId);
	}


	/*
	 * 银行卡的绑定
	 * 
	 * @param cardNo 卡号
	 * 
	 * @param bankName 开户行
	 * 
	 * @param province 开户行所在省
	 * 
	 * @param city 开户行所在市
	 * 
	 * @param bankType 卡类型
	 */
	@RequestMapping(value = "coredata/boundbankcard")
	@ResponseBody
	public Map<String, Object> BoundBankCard(UserCard usercard,
			HttpServletRequest request, HttpServletResponse response) {
		Integer userId = UserUtil.getUserId(request, response);
		if (userId == null) {
			return webUtil.loginFailedRes(null);
		}
		ApiUserCard apiUserCard = new ApiUserCard();
		if (StringUtils.isEmpty(usercard.getBankName())
				|| StringUtils.isEmpty(usercard.getCardNo())
				|| StringUtils.isEmpty(usercard.getProvince())
				|| StringUtils.isEmpty(usercard.getBankType())) {

			return webUtil.failedRes("0009", "参数错误", null);
		} else {
			String[] adr = usercard.getProvince().split(" ");
			if (adr.length != 3) {
				return webUtil.failedRes("0009", "参数错误", null);
			} else {
				usercard.setCity(adr[1]);
				usercard.setProvince(adr[0]);
				usercard.setArea(adr[2]);
			}
		}
		/*if (usercard.getPid() != null) {
			ApiProjectUserInfo ao = new ApiProjectUserInfo();
			ao.setProjectId(usercard.getPid());
			List<ApiProjectUserInfo> list = projectFacade.queryProjectUserInfoList(ao);
			System.out.println("list.size()>>>>>>>>>>>>"+list.size());
			int count= 0;
			for (int i = 0; i < list.size(); i++) {
				ao = list.get(i);
				if (ao.getPersonType() != 1) {
					if(ao.getRealName().trim().equals(usercard.getName().trim())){
						count++;
					}
				}
			}
//			if(usercard.getAccountType() != 0){
//				if (count == 0) {
//					return webUtil.failedRes("90012", "开户名与受助人,发布人的姓名不一样", null);
//				}
//			}
			
		}else{
			return webUtil.failedRes("90013", "没有指定的提款项目，不能绑定银行卡", null);
		}*/
		if(usercard.getPid()==null){
			return webUtil.failedRes("90013", "没有指定的提款项目，不能绑定银行卡", null);
		}
		apiUserCard.setUserId(userId);
		apiUserCard.setBankName(usercard.getBankName());
		apiUserCard.setCard(usercard.getCardNo());
		apiUserCard.setCity(usercard.getCity());
		apiUserCard.setArea(usercard.getArea());
		apiUserCard.setUseState(100);
		apiUserCard.setBankType(usercard.getBankType());
		apiUserCard.setProvince(usercard.getProvince());
		apiUserCard.setAccountName(usercard.getName());
		apiUserCard.setAccountType(usercard.getAccountType());
		//增加projectId
		apiUserCard.setProjectId(usercard.getPid());
		ApiResult res = userFacade.saveNewUserCard(apiUserCard);
		if (res != null && res.getCode() == 1) {
			return webUtil.successRes(null);
		} else if (res.getCode() == 10003) {
			return webUtil.failedRes("0009", "银行卡超出限制", null);
		} else if (res.getCode() == 10004) {
			return webUtil.failedRes("0009", "银行卡已绑定", null);
		} else {
			return webUtil.failedRes("0009", "参数错误", null);
		}
	}

	/*
	 * 获取绑定的银行卡List
	 * 
	 * @param 根据用户ID
	 */
	@RequestMapping(value = "coredata/boundbankcardlist")
	@ResponseBody
	public Map<String, Object> BoundBankCardList(UserCard usercard,
			HttpServletRequest request, HttpServletResponse response) {
		Integer userId = UserUtil.getUserId(request, response);
		if (userId == null) {
			return webUtil.loginFailedRes(null);
		}
		ApiUserCard apiUserCard = new ApiUserCard();
		apiUserCard.setUserId(userId);
		apiUserCard.setUseState(100);
		ApiPage<ApiUserCard> apiPage = userFacade.queryUserCardList(
				apiUserCard, usercard.getPage(), usercard.getPageSize());
		List<ApiUserCard> list = apiPage.getResultData();
		JSONObject data = new JSONObject();
		JSONArray items = new JSONArray();
		for (ApiUserCard card : list) {
			JSONObject item = new JSONObject();
			item.put("id", card.getId());
			item.put("bankName", card.getBankName());
			item.put("bankCard", StringUtil.getSafeNumber(card.getCard()));
			item.put("cardType", card.getBankType());
			item.put("isdefault", card.getIsSelected());
			items.add(item);
		}
		data.put("data", items);
		data.put("total", apiPage.getTotal());// 总页数
		return webUtil.successRes(data);
	}

	/*
	 * 设置默认银行卡
	 * 
	 * @param uid 是usercard表的主键
	 */
	@RequestMapping(value = "coredata/defaultbankcard")
	@ResponseBody
	public Map<String, Object> defaultBankcard(UserCard usercard,
			HttpServletRequest request, HttpServletResponse response) {
		Integer userId = UserUtil.getUserId(request, response);
		if (userId == null) {
			return webUtil.loginFailedRes(null);
		}
		// 绑定表的主键ID改变表状态
		ApiUserCard apiUserCard = new ApiUserCard();
		apiUserCard.setUserId(userId);
		apiUserCard.setId(usercard.getBid());
		apiUserCard.setIsSelected(0);
		ApiResult res = userFacade.resetUserCardSelected(apiUserCard);

		if (res != null && res.getCode() == 1) {
			return webUtil.successRes(null);
		} else {
			return webUtil.failedRes("0009", "参数错误", null);
		}
	}

	/*
	 * 解除绑定的银行卡
	 * 
	 * @param uid 是usercard表的主键
	 */
	@RequestMapping(value = "coredata/deletebankcard")
	@ResponseBody
	public Map<String, Object> deleteBankCard(UserCard usercard,
			HttpServletRequest request, HttpServletResponse response) {
		Integer userId = UserUtil.getUserId(request, response);
		if (userId == null) {
			return webUtil.loginFailedRes(null);
		}
		// 绑定表的主键ID改变表状态
		ApiUserCard apiUserCard = new ApiUserCard();
		apiUserCard.setUserId(userId);
		apiUserCard.setUseState(102);
		apiUserCard.setId(usercard.getBid());
		ApiResult res = userFacade.updateUserCard(apiUserCard);

		if (res != null && res.getCode() == 1) {
			return webUtil.successRes(null);
		} else {
			return webUtil.failedRes("0009", "参数错误", null);
		}
	}

	/*
	 * 提款的页面
	 */
	@RequestMapping(value = "core/viewWithdrawDeposit")
	@ActionLog(content = "查看提款")
	public ModelAndView ViewWithdrawDeposit(UserCard usercard,
			HttpServletRequest request, HttpServletResponse response) {
		Integer userId = UserUtil.getUserId(request, response);
		ModelAndView view = new ModelAndView("deposit/enterprise/w_quick");
		if (userId == null) {
			view = new ModelAndView("redirect:/user/sso/login.do");
			return view;
		}
		ApiUserCard apiUserCard = new ApiUserCard();
		// 如果用户通过点击提款按钮来操作，自动填写已选的信息
		/** 页面的默认选择卡号 end **/
		if (usercard.getBid() != null) {
			apiUserCard.setUserId(userId);
			apiUserCard.setId(usercard.getBid());
			apiUserCard.setUseState(100);
		} else {
			apiUserCard.setUserId(userId);
			apiUserCard.setIsSelected(0);
			apiUserCard.setUseState(100);
		}
		ApiPage<ApiUserCard> mapiPage = userFacade.queryUserCardList(
				apiUserCard, usercard.getPage(), usercard.getPageSize());
		List<ApiUserCard> mlist = mapiPage.getResultData();
		if (mlist.size() > 0) {
			apiUserCard = mlist.get(0);
			apiUserCard
					.setCard(StringUtil.getSafeNumber(apiUserCard.getCard()));
			//apiUserCard.setBankName(BankConstants.BANK_IMAGE.get(apiUserCard.getBankName()));
			view.addObject("selectcard", apiUserCard);
		}
		/** 页面的默认选择卡号 end **/
		apiUserCard = new ApiUserCard();
		apiUserCard.setUserId(userId);
		apiUserCard.setUseState(100);
		ApiPage<ApiUserCard> apiPage = userFacade.queryUserCardList(
				apiUserCard, usercard.getPage(), usercard.getPageSize());
		List<ApiUserCard> list = apiPage.getResultData();
		for (int i = 0; i < list.size(); i++) {
			ApiUserCard card = list.get(i);
			card.setCard(StringUtil.getSafeNumber(card.getCard()));
			//card.setBankName(BankConstants.BANK_IMAGE.get(card.getBankName()));
		}
		view.addObject("cardList", list);
		view.addObject("size", list.size());
		ApiFrontUser user = userFacade.queryById(userId);
		view.addObject("user", user);
		return view;
	}

	/*
	 * 提款申请
	 * 
	 * @param uid 是usercard表的主键
	 */
	@ResponseBody
	@RequestMapping(value = "coredata/WithdrawDeposit")
	public Map<String, Object> withdrawDeposit(UserCard usercard,
			HttpServletRequest request, HttpServletResponse response) {
		ApiPayMoneyRecord apiPayMoneyRecord = new ApiPayMoneyRecord();
		apiPayMoneyRecord.setApplyMoney(usercard.getMoney());
		Integer userId = UserUtil.getUserId(request, response);
		// 没有登录
		if (userId == null) {
			return webUtil.loginFailedRes(null);
		}
		ApiUserCard apiUserCard = new ApiUserCard();
		apiUserCard.setUserId(userId);
		apiUserCard.setId(usercard.getBid());
		if(usercard.getPid() != null ){
			ApiProject apiProject = projectFacade.queryProjectDetail(usercard.getPid());
			if(apiProject == null){
				return webUtil.failedRes("0011", "没有这个项目", null);
			}
			/*if(!userId.equals(apiProject.getUserId())){
				return webUtil.failedRes("0010", "不是本人发起的项目", null);
			}*/
			apiUserCard.setProjectId(usercard.getPid());
		}else {
			return webUtil.failedRes("0009", "参数错误", null);
		}
		// 查看有没这张卡数据
		ApiPage<ApiUserCard> apiPage = userFacade.queryUserCardList(
				apiUserCard, usercard.getPage(), usercard.getPageSize());
		List<ApiUserCard> list = apiPage.getResultData();
		if (list.size() > 0) {
			ApiUserCard apcd = list.get(0);
			//-------------
			apcd.setBankName(usercard.getBankType());
			apcd.setCard(usercard.getCardNo());
			apcd.setAccountName(usercard.getBankName());
			ApiResult apiResult = userFacade.updateUserCard(apcd);
			//更新Card表
			apiPayMoneyRecord.setProjectId(usercard.getPid());
			apiPayMoneyRecord.setTranNum(StringUtil.uniqueCode());
			apiPayMoneyRecord.setRecipientName(apcd.getBankName());
			//TODO 增加开户名 accountName
			apiPayMoneyRecord.setAccountName(apcd.getAccountName());
			
			apiPayMoneyRecord.setAccount(apcd.getCard());
			apiPayMoneyRecord.setReceiptImageId(usercard.getImageId());
			apiPayMoneyRecord.setUserId(userId);
			apiPayMoneyRecord.setRecipientBankType(apcd.getBankType());
			apiPayMoneyRecord.setSource("PC");
		} else {
			return webUtil.failedRes("10001", "没有这张卡", null);
		}
		// 提款申请记入
		ApiResult res = payMoneyRecordFacade.drawMoney(apiPayMoneyRecord);
		if (res != null && res.getCode() == 1) {
			//短信通知
			ApiProjectUserInfo info = new ApiProjectUserInfo();
			info.setProjectId(usercard.getPid());
			info.setPersonType(0);
			info = projectFacade.queryProjectUserInfo(info);
			
			if(info!=null&&info.getLinkMobile()!=null&&!"".equals(info.getLinkMobile())){//受助人
				ApiAnnounce announce = new ApiAnnounce();
				announce.setCause("善款申请提醒");
				announce.setType(1);
				announce.setPriority(1);
				announce.setState(100);
				announce.setTryCount(0);
				announce.setCreateTime(new Date());
				announce.setContent(info.getRealName()+"，您好，您的筹款项目已发起一笔提款申请，提款金额："+usercard.getMoney()+"元，收款人："+list.get(0).getAccountName()+"，如有疑问请联系我们，电话0571-87165191，QQ：2777819027");
				announce.setDestination(info.getLinkMobile().trim());
				commonFacade.sendSms(announce, false);
			}	
			return webUtil.successRes(null);
		}/*else if (res.getCode() == 90014) {
			return webUtil.failedRes("90014", res.getMessage(), null);
		}else if (res.getCode() == 90004) {
			return webUtil.failedRes("90004", res.getMessage(), null);
		} else {
			return webUtil.failedRes("0009", res.getMessage(), null);
		}*/
		return null;

	}

	/*
	 * 提款的数据记录的页面显示
	 */
	@RequestMapping(value = "core/WithdrawDepositData")
	@ActionLog(content = "查看提款的数据记录")
	public ModelAndView WithdrawDepositRecord(UserCard usercard,
			HttpServletRequest request, HttpServletResponse response) {
		Integer userId = UserUtil.getUserId(request, response);
		ModelAndView view = new ModelAndView("deposit/enterprise/w_record");
		// 没有登录
		if (userId == null) {
			view = new ModelAndView("redirect:/user/sso/login.do");
			return view;
		}
		ApiFrontUser user = userFacade.queryById(userId);
		view.addObject("user", user);
		return view;
	}

	/*
	 * 提款的数据记录，接口返回值
	 */
	@ResponseBody
	@RequestMapping(value = "core/WithdrawDepositRecordData")
	@ActionLog(content = "查看提款的数据记录")
	public Map<String, Object> WithdrawDepositData(UserCard usercard,
			HttpServletRequest request, HttpServletResponse response) {
		Integer userId = UserUtil.getUserId(request, response);
		// 没有登录
		if (userId == null) {
			return webUtil.loginFailedRes(1);
		}
		ApiPayMoneyRecord apiPayMoneyRecord = new ApiPayMoneyRecord();
		// 处理时间
		Date bdate = null;
		Date edate = new Date();
		if (usercard.getTypeDt() == 0) {
			// 3个月内
			bdate = DateUtil.add(edate, -3 * 30);
			apiPayMoneyRecord.setQueryStartDate(bdate);
		} else if (usercard.getTypeDt() == 1) {
			// 半年
			bdate = DateUtil.add(edate, -6 * 30);
			apiPayMoneyRecord.setQueryStartDate(bdate);
		} else if (usercard.getTypeDt() == 2) {
			// 一年
			bdate = DateUtil.add(edate, -12 * 30);
			apiPayMoneyRecord.setQueryStartDate(bdate);
		} else {
			// 全部
		}
		apiPayMoneyRecord.setUserId(userId);
		apiPayMoneyRecord.setQueryEndDate(edate);

		// 提款申请记入
		ApiPage<ApiPayMoneyRecord> apiPage = payMoneyRecordFacade
				.queryPayMoneyRecordList(apiPayMoneyRecord, usercard.getPage(),
						usercard.getPageSize());
		List<ApiPayMoneyRecord> list = apiPage.getResultData();
		JSONObject data = new JSONObject();
		JSONArray items = new JSONArray();
		for (ApiPayMoneyRecord record : list) {
			JSONObject item = new JSONObject();
			item.put("createtime", record.getPayMoneyTime());
			item.put("bank", record.getRecipientName());
			item.put("bankNumber",StringUtil.getSafeNumber(record.getAccount()));
			item.put("money", record.getApplyMoney());
			item.put("title", record.getProjectTitle());
			item.put("state", record.getState());
			item.put("trannum", record.getTranNum());
			item.put("paydate", record.getPayMoneyTime());
			items.add(item);
		}

		data.put("data", items);
		data.put("total", apiPage.getTotal());// 总行数
		data.put("pages", apiPage.getPages());// 总页数
		data.put("page", apiPage.getPageNum());// 第几页
		return webUtil.successRes(data);
	}

	/*
	 * 充值的数据记录
	 */
	@RequestMapping(value = "core/rechargeDepositData")
	@ActionLog(content = "查看充值的数据记录")
	public Map<String, Object> rechargeDepositData(UserCard usercard,
			HttpServletRequest request, HttpServletResponse response) {
		Integer userId = UserUtil.getUserId(request, response);
		// 没有登录
		if (userId == null) {
			return webUtil.loginFailedRes(null);
		}
		ApiCapitalinout apiCapitalinout = new ApiCapitalinout();
		apiCapitalinout.setUserId(userId);
		apiCapitalinout.setPayState(302);
		// 提款申请记入
		ApiPage<ApiCapitalinout> apiPage = payMoneyRecordFacade
				.queryCapitalinout(apiCapitalinout, usercard.getPage(),
						usercard.getPageSize());
		List<ApiCapitalinout> list = apiPage.getResultData();
		JSONObject data = new JSONObject();
		JSONArray items = new JSONArray();
		for (ApiCapitalinout record : list) {
			JSONObject item = new JSONObject();
			item.put("createtime", record.getCreateTime());
			item.put("paytype", record.getPayType());
			item.put("money", record.getMoney());
			item.put("trannum", record.getTranNum());
			items.add(item);
		}
		data.put("data", items);
		data.put("total", apiPage.getTotal());// 总行数
		data.put("pages", apiPage.getPages());// 总页数
		data.put("page", apiPage.getPageNum());// 第几页
		return webUtil.successRes(data);
	}


	/**
	 * 开票记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="getInvoicList")
	public ModelAndView getInvoicList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("ucenter/invoice/invoiceList");
		Integer userId = UserUtil.getUserId(request, response);
		if (userId == null) {
			view = new ModelAndView("redirect:/user/sso/login.do");
			return view;
		}
		view.addObject("guideName", "开票记录");
		ApiFrontUser user = userFacade.queryById(userId);
		view.addObject("user", user);
		return view;
	}
	
}
