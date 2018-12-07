package com.guangde.user.facade.impl;

import com.github.pagehelper.Page;
import com.guangde.api.user.IDonateRecordFacade;
import com.guangde.business.dto.BuyProductFormDTO;
import com.guangde.business.dto.DepositFormDTO;
import com.guangde.business.entry.*;
import com.guangde.business.exception.BaseException;
import com.guangde.business.pojo.Result;
import com.guangde.business.service.DonateTimeService;
import com.guangde.business.service.NewLeaveWordService;
import com.guangde.business.service.ProjectService;
import com.guangde.business.service.user.IDonateRecordService;
import com.guangde.business.service.user.IUserAddressService;
import com.guangde.business.service.user.IUserInvoiceService;
import com.guangde.business.util.Constant;
import com.guangde.business.util.StrUtil;
import com.guangde.dto.ApiBuyProductFormDTO;
import com.guangde.dto.ApiDepositFormDTO;
import com.guangde.dto.ApiFrontUserInvoiceDTO;
import com.guangde.entry.*;
import com.guangde.enums.ResultEnum;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.guangde.util.ApiResultUtil;
import com.guangde.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("DonateRecordFacade")
public class DonateRecordFacadeImpl implements IDonateRecordFacade {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private IDonateRecordService donateRecordService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private DonateTimeService donateTimeService;

	@Autowired
	private NewLeaveWordService newLeaveWordService;
	
	@Autowired
	private IUserInvoiceService userInvoiceService;
	
	@Autowired
	private IUserAddressService userAddressService;

	@Override
	public ApiResult buyDonate(ApiDepositFormDTO depositForm) {
		logger.info("buyDonate ApiDepositFormDTO = " + depositForm);

		DepositFormDTO dto = new DepositFormDTO();
		BeanUtils.copyProperties(depositForm, dto);

		try {
			Result result = donateRecordService.buyDonate(dto);
			ApiResult apiResult = new ApiResult();
			BeanUtils.copyProperties(result, apiResult);
			return apiResult;
		}catch (BaseException e){
			e.printStackTrace();
		}

		return ApiResultUtil.ERROR;
	}

	@Override
	public ApiResult buyProduct(ApiBuyProductFormDTO dto) {
		logger.info("buyProduct ApiBuyProductFormDTO = " + dto);

		BuyProductFormDTO buyProductFormDTO = new BuyProductFormDTO();
		BeanUtils.copyProperties(dto, buyProductFormDTO);

		try {
			Result result = donateRecordService.buyProduct(buyProductFormDTO);
			ApiResult apiResult = new ApiResult();
			BeanUtils.copyProperties(result, apiResult);
			if(apiResult.getCode() == 1) {
				apiResult.setData(dto.getTransNum());
			}
			return apiResult;
		}catch (BaseException e){
			e.printStackTrace();
		}

		return ApiResultUtil.ERROR;
	}

	@Override
	public ApiPage<ApiDonateRecord> queryByCondition(
			ApiDonateRecord apiDonateRecord, int pageNum, int pageSize) {
		logger.info("queryByCondition ApiDonateRecord = " + apiDonateRecord);
		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);

		if (donateRecord == null) {
			return new ApiPage<ApiDonateRecord>(pageNum, pageSize);
		}

		List<DonateRecord> list = donateRecordService.queryByCondition(
				donateRecord, pageNum, pageSize);

		NewLeaveWord word = new NewLeaveWord();
		for (DonateRecord record : list) {
			word.setProjectDonateId(record.getId());
			List<NewLeaveWord> newLeaveWords = newLeaveWordService.queryNewLeaveWord(word, pageNum, pageSize);
			if(!CollectionUtils.isEmpty(newLeaveWords)){
				record.setApiNewLeaveWordList(newLeaveWords);
			}
		}

		ApiPage<ApiDonateRecord> apiPage = BeanUtil.copyPage(
				(Page<DonateRecord>) list, ApiDonateRecord.class);

		return apiPage;
	}


	@Override
	public ApiResult buyDonate(ApiDonateRecord apiDonateRecord,
			ApiCapitalinout apiCapitalinout, String payType, ApiFrontUser apiFrontUser, String slogans) {

		logger.info("receive buy donate:" + apiDonateRecord + ", payType:"
				+ payType);

		Capitalinout capitalinout = BeanUtil.copy(apiCapitalinout,
				Capitalinout.class);

		DonateRecord donate = BeanUtil
				.copy(apiDonateRecord, DonateRecord.class);
		FrontUser user = BeanUtil
				.copy(apiFrontUser, FrontUser.class);
		
		if (BeanUtil.isNull(donate, donate.getProjectId())) {
			return ApiResultUtil.getParameterError();
		}

		try {
			if (payType.equals(Constant.FREEZ_PAY)) {
				projectService.updateDonate(donate, capitalinout, payType);
			}
			else if (payType.equals(Constant.WERUN_PAY)) {
				projectService.updateWeRunDonate(donate, capitalinout, payType);
			}/*else if(Constant.TEN_RED_PAY.equals(payType)){//微信支付+红包支付
				projectService.updateDonate(donate,payType,user,slogans,apiDonateRecord.getRedPacketId());
			}else if(Constant.ALI_RED_PAY.equals(payType)){//支付宝支付+红包支付
				projectService.updateDonate(donate,payType,user,slogans,apiDonateRecord.getRedPacketId());
			}
			else {
				if(capitalinout!=null&&capitalinout.getPayConfigId()!=null&&donate.getPayConfigId()!=null){
					projectService.updateDonate2(donate,capitalinout, payType);
				}
				else{
					projectService.updateDonate(donate, payType, user, slogans);
				}
				
			}*/
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}


	@Override
	public ApiResult buyDonateResult(String tranNum, String payNum,
			boolean isPaySuccess, String errorMsg, String payType) {

		logger.info("receive buy buyDonateResult tranNum:" + tranNum
				+ ",payNum:" + payNum + ",isPaySuccess:" + isPaySuccess
				+ ",errorMsg:" + errorMsg);

		if (StrUtil.isEmpty(payNum)) {
			return ApiResultUtil.getParameterError();
		}

		try {
			if (payType.equals(Constant.FREEZ_PAY)) {

				projectService.updateDonateResult(tranNum, payNum,
						isPaySuccess, errorMsg, payType);
			} else {
				projectService.updateDonateResult(tranNum, payNum,
						isPaySuccess, errorMsg);
			}
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}

	@Override
	public ApiResult buyDonateResult(String tranNum, String payNum, String payType) {

		logger.info("receive buy buyDonateResult tranNum:" + tranNum
				+ ",payNum:" + payNum);

		if (StrUtil.isEmpty(payNum)) {
			return ApiResultUtil.getParameterError();
		}

		try {
			projectService.updateDonateResult(tranNum, payNum, payType);
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}
	
	@Override
	public ApiResult reChargeResult(String tranNum, String payNum,
			boolean isPaySuccess, String errorMsg, String payType) {

		logger.info("receive buy reChargeResult tranNum:" + tranNum
				+ ",payNum:" + payNum + ",isPaySuccess:" + isPaySuccess
				+ ",errorMsg:" + errorMsg);

		if (StrUtil.isEmpty(payNum)) {
			return ApiResultUtil.getParameterError();
		}

		try {
				projectService.updateCapitalinoutResult(tranNum, payNum,
						isPaySuccess, errorMsg);
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}


	@Override
	public ApiDonateRecord queryCompanyCenter(ApiDonateRecord apiDonateRecord) {
		logger.info("receive queryCompanyCenter param : " + apiDonateRecord);
		if (apiDonateRecord == null) {
			return null;
		}
		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);
		donateRecord = donateRecordService.countByParam(donateRecord);

		ApiDonateRecord newApiDonateRecord = BeanUtil.copy(donateRecord,
				ApiDonateRecord.class);

		return newApiDonateRecord;
	}

	@Override
	public Double countQueryByCondition(ApiDonateRecord apiDonateRecord) {
		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);

		if (donateRecord == null) {
			return 0.0;
		}
		Double donateAmount = donateRecordService
				.countQueryByCondition(donateRecord);
		if (donateAmount == null) {
			return 0.0;
		}

		return donateAmount;
	}
	
	@Override
	public Double countQueryDonateRecordList(ApiDonateRecord apiDonateRecord) {
		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);

		if (donateRecord == null) {
			return 0.0;
		}
		Double donateAmount = donateRecordService
				.countQueryDonateRecordList(donateRecord);
		if (donateAmount == null) {
			return 0.0;
		}

		return donateAmount;
	}

	@Override
	public Integer queryProjectIdByCapitalinout(String tranNum) {
		logger.info("queryProjectIdByCapitalinouId  param  tranNum = "
				+ tranNum);
		if (StringUtils.isEmpty(tranNum)) {
			return null;
		}
		Integer projectId = donateRecordService
				.queryProjectIdByCapitalinoutId(tranNum);

		return projectId;
	}

	@Override
	public ApiDonateRecord queryWeixinToPayNoticeByTranNum(String tranNum) {
		logger.info("queryWeixinToPayNoticeByTranNum  param  tranNum = "
				+ tranNum);
		if (StringUtils.isEmpty(tranNum)) {
			return null;
		}

		DonateRecord donateRecord = new DonateRecord();
		donateRecord = donateRecordService
				.queryWeixinPayNoticeByTranNum(tranNum);

		ApiDonateRecord newApiDonateRecord = BeanUtil.copy(donateRecord,
				ApiDonateRecord.class);

		return newApiDonateRecord;
	}


	@Override
	public ApiPage<ApiDonateRecord> queryLatestDonateRecordList(
			ApiDonateRecord apiDonateRecord, int pageNum, int pageSize) {

		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);

		if (donateRecord == null) {
			return new ApiPage<ApiDonateRecord>(pageNum, pageSize);
		}

		List<DonateRecord> list = donateRecordService
				.queryLatestDonateRecordList(donateRecord, pageNum, pageSize);

		ApiPage<ApiDonateRecord> apiPage = BeanUtil.copyPage(
				(Page<DonateRecord>) list, ApiDonateRecord.class);

		return apiPage;
	}
	
	@Override
	public ApiPage<ApiDonateRecord> queryLatestDonateRecordIdList(
			ApiDonateRecord apiDonateRecord, int pageNum, int pageSize) {

		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);

		if (donateRecord == null) {
			return new ApiPage<ApiDonateRecord>(pageNum, pageSize);
		}

		List<DonateRecord> list = donateRecordService
				.queryLatestDonateRecordIdList(donateRecord, pageNum, pageSize);

		ApiPage<ApiDonateRecord> apiPage = BeanUtil.copyPage(
				(Page<DonateRecord>) list, ApiDonateRecord.class);

		return apiPage;
	}
	
	@Override
	public List<ApiDonateRecord> queryLatestDonateRecordIdDetailList(
			ApiDonateRecord apiDonateRecord) {
		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);
		List<DonateRecord> list = donateRecordService
				.queryLatestDonateRecordIdDetailList(donateRecord);
		List<ApiDonateRecord> newlist = BeanUtil.copyList(list, ApiDonateRecord.class);

		return newlist;
	}

	@Override
	public ApiPage<ApiDonateRecord> queryMyDonateRecordList(
			ApiDonateRecord apiDonateRecord, int pageNum, int pageSize) {

		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);

		if (donateRecord == null) {
			return new ApiPage<ApiDonateRecord>(pageNum, pageSize);
		}

		List<DonateRecord> list = donateRecordService.queryMyDonateRecordList(
				donateRecord, pageNum, pageSize);

		ApiPage<ApiDonateRecord> apiPage = BeanUtil.copyPage(
				(Page<DonateRecord>) list, ApiDonateRecord.class);

		return apiPage;
	}

	@Override
	public Integer countNumQueryByCondition(ApiDonateRecord apiDonateRecord) {

		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);
		if (donateRecord == null) {
			return 0;
		}
		Integer total = donateRecordService
				.countNumQueryByCondition(donateRecord);
		if (total == null) {
			total = 0;
		}
		return total;
	}

	@Override
	public int updateLeaveWord(ApiDonateRecord apiDonateRecord) {

		int result = 0;
		if (apiDonateRecord == null) {
			return 0;
		}
		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);
		try {
			result = donateRecordService.updateDonatLeaveWord(donateRecord);
		} catch (BaseException e) {

			result = 0;
		}

		return result;
	}

	@Override
	public ApiPage<ApiDonateRecord> queryDonateListByInvoice(
			ApiDonateRecord apiDonateRecord, int pageNum, int pageSize) {

		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);

		if (donateRecord == null) {
			return new ApiPage<ApiDonateRecord>(pageNum, pageSize);
		}

		List<DonateRecord> list = donateRecordService.queryDonateListByInvoice(
				donateRecord, pageNum, pageSize);

		ApiPage<ApiDonateRecord> apiPage = BeanUtil.copyPage(
				(Page<DonateRecord>) list, ApiDonateRecord.class);

		return apiPage;
	}

	@Override
	public Double sumQueryDonateListByInvoice(ApiDonateRecord apiDonateRecord) {
		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);
		return donateRecordService.sumQueryDonateListByInvoice(donateRecord);
	}


	@Override
	public ApiDonateRecord queryPayNoticeByTranNum(String tranNum) {

		logger.info("queryPayNoticeByTranNum  param  tranNum = " + tranNum);
		if (StringUtils.isEmpty(tranNum)) {
			return null;
		}

		DonateRecord donateRecord = new DonateRecord();
		donateRecord = donateRecordService.queryPayNoticeByTranNum(tranNum);

		ApiDonateRecord newApiDonateRecord = BeanUtil.copy(donateRecord,
				ApiDonateRecord.class);

		return newApiDonateRecord;
	}
	
	@Override
	public ApiResult batchBuyDonate(ApiDonateRecord apiDonateRecord,
			ApiCapitalinout apiCapitalinout, String payType) {

		logger.info("receive buy donate:" + apiDonateRecord + ", payType:"
				+ payType);

		Capitalinout capitalinout = BeanUtil.copy(apiCapitalinout,
				Capitalinout.class);

		DonateRecord donate = BeanUtil
				.copy(apiDonateRecord, DonateRecord.class);

		if (BeanUtil.isNull(donate, donate.getProjectId())) {
			return ApiResultUtil.getParameterError();
		}

		try {
			projectService.updateBatchDonate(donate, capitalinout, payType);
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}
	
	@Override
	public ApiResult batchBuyDonateResult(String tranNum, String payNum,
			boolean isPaySuccess, String errorMsg, String payType) {

		logger.info("receive buy batchBuyDonateResult tranNum:" + tranNum
				+ ",payNum:" + payNum + ",isPaySuccess:" + isPaySuccess
				+ ",errorMsg:" + errorMsg);

		if (StrUtil.isEmpty(payNum)) {
			return ApiResultUtil.getParameterError();
		}

		try {

			projectService.updateBatchBuyDonateResult(tranNum, payNum,
						isPaySuccess, errorMsg, payType);
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}


	@Override
	public ApiResult saveDonateTime(ApiDonateTime apidt) {
		
		logger.info("save donateTime param is "+apidt);
		DonateTime dt = BeanUtil.copy(apidt, DonateTime.class);
		if(dt.getUserId() == null){
			return ApiResultUtil.getParameterError();
		}
		try {
			donateTimeService.saveDonateTime(dt);
			ApiResult ret = ApiResultUtil.SUCCESS;
			return ret;
		} catch (BaseException e) {
			
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
		
	}


	@Override
	public ApiPage<ApiDonateTime> queryByUserId(Integer userId,int pageNum, int pageSize) {
		
		List<DonateTime> list = donateTimeService.queryByUserId(userId,pageNum,pageSize);
		
		ApiPage<ApiDonateTime> apiPage = BeanUtil.copyPage(
				(Page<DonateTime>) list, ApiDonateTime.class);
		return apiPage;
	}
	
	@Override
	public ApiPage<ApiDonateTime> queryDonateTimeByParam(ApiDonateTime param,int pageNum, int pageSize) {
		logger.info("query param is "+ param);
		DonateTime dr = BeanUtil.copy(param, DonateTime.class);
		List<DonateTime> list = donateTimeService.queryDonateTimeByParam(dr, pageNum, pageSize);
		ApiPage<ApiDonateTime> apiList = BeanUtil.copyPage((Page<DonateTime>)list, ApiDonateTime.class);
		return apiList;
	}

	@Override
	public ApiResult updateDonateTime(ApiDonateTime apidt) {
		logger.info("update param is" + apidt);

		if(apidt == null) {
			logger.info("param is null");
			return ApiResultUtil.getParameterError();
		}
		
		DonateTime dt = BeanUtil.copy(apidt, DonateTime.class);
		try {
			donateTimeService.updateDonateTime(dt);
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}

	
	@Override
	public ApiCapitalinout queryCapitalinoutByParam(ApiCapitalinout param){
		logger.info("queryCapitalinoutByParam  param  tranNum = "
				+ param.toString());
		Capitalinout capitalinout = BeanUtil.copy(param,
				Capitalinout.class);
		capitalinout = donateRecordService.queryCapitalinoutByParam(capitalinout);

		ApiCapitalinout apiCapitalinout = BeanUtil.copy(capitalinout,
				ApiCapitalinout.class);

		return apiCapitalinout;
	}
	
	@Override
	public ApiPage<ApiDonateRecord> queryDonateHeadImgByProjectId(Integer projectId,int pageNum, int pageSize){
		List<DonateRecord> list = donateRecordService.queryDonateHeadImgByProjectId(projectId,pageNum, pageSize);
		ApiPage<ApiDonateRecord> apiPage = BeanUtil.copyPage(
				(Page<DonateRecord>) list, ApiDonateRecord.class);
		return apiPage;
	}


	@Override
	public ApiResult addDayDonate(ApiCapitalinout cp, ApiDonateRecord dr,
			ApiProject project, ApiFrontUser user) {
		Capitalinout capitalinout = BeanUtil.copy(cp, Capitalinout.class);
		DonateRecord dRecord = BeanUtil.copy(dr, DonateRecord.class);
		Project p = BeanUtil.copy(project, Project.class);
		FrontUser u = BeanUtil.copy(user, FrontUser.class);
		int res = donateRecordService.addDayDonate(capitalinout,dRecord,p,u);
		if(res>0){
			return ApiResultUtil.SUCCESS;
		}else{
			return ApiResultUtil.ERROR;
		}
		
	}
	


	@Override
	public int countDonateRecord(Integer state, String title) {
		return donateRecordService.countDonateRecord(state, title);
	}

	@Override
	public ApiDonateRecord queryByDonateId(Integer id) {
		DonateRecord d = donateRecordService.queryByDonateId(id);
		ApiDonateRecord apiDonateRecord = BeanUtil.copy(d, ApiDonateRecord.class);
		return apiDonateRecord;
	}


	@Override
	public ApiPage<ApiDonateRecord> queryYiXingTeamDonate(
			ApiTogetherConfig apiTogetherConfig, int pageNum, int pageSize) {
		TogetherConfig togetherConfig = BeanUtil.copy(apiTogetherConfig,
				TogetherConfig.class);

		if (togetherConfig == null) {
			return new ApiPage<ApiDonateRecord>(pageNum, pageSize);
		}
		List<DonateRecord> list = donateRecordService.queryYiXingTeamDonate(togetherConfig,pageNum, pageSize);
		ApiPage<ApiDonateRecord> apiPage = BeanUtil.copyPage(
				(Page<DonateRecord>) list, ApiDonateRecord.class);
		return apiPage;
	}

	@Override
	public ApiPage<ApiDonateRecord> queryByUserIdSelectLeaveWord(
			Integer userId,Integer isRead, int pageNum, int pageSize) {
		List<DonateRecord> list = donateRecordService.queryByUserIdSelectLeaveWord(userId,isRead,pageNum, pageSize);
		ApiPage<ApiDonateRecord> apiPage = BeanUtil.copyPage(
				(Page<DonateRecord>) list, ApiDonateRecord.class);
		return apiPage;
	}



	@Override
	public ApiPage<ApiDonateRecord> queryByUserIdForDonateTime(ApiDonateRecord apiDonateRecord,
			int pageNum, int pageSize) {
		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);
		
		List<DonateRecord> list = donateRecordService
				.queryByUserIdForDonateTime(donateRecord,pageNum,pageSize);
		ApiPage<ApiDonateRecord> apiPage = BeanUtil.copyPage((Page<DonateRecord>) list, ApiDonateRecord.class);

		return apiPage;
	}

	@Override
	public List<ApiDonateRecord> queryByDonateTimeForInfo(ApiDonateRecord apiDonateRecord) {
		if (apiDonateRecord == null) {
			return null;
		}
		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);
		List<DonateRecord> list = donateRecordService
				.queryByDonateTimeForInfo(donateRecord);
		List<ApiDonateRecord> newlist = BeanUtil.copyList(list, ApiDonateRecord.class);

		return newlist;
	}

	@Override
	public List<ApiDonateRecord> queryByDonateTimeParam(
			ApiDonateRecord apiDonateRecord) {
		if (apiDonateRecord == null) {
			return null;
		}
		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord,
				DonateRecord.class);
		List<DonateRecord> list = donateRecordService
				.queryByDonateTimeParam(donateRecord);
		List<ApiDonateRecord> newlist = BeanUtil.copyList(list, ApiDonateRecord.class);

		return newlist;
	}


	@Override
	public int countByDonateRecordParam(ApiDonateRecord apiDonateRecord) {
		DonateRecord donateRecord = BeanUtil.copy(apiDonateRecord, DonateRecord.class);
		return donateRecordService.countByDonateRecordParam(donateRecord);
	}

	@Override
	public ApiPage<ApiDonateRecord> queryDonateRecordByEp(ApiDonateRecord donateRecord, int pageNum, int pageSize) {
		ApiPage<ApiDonateRecord> page = null;
		try {
			DonateRecord d = BeanUtil.copy(donateRecord, DonateRecord.class);
			List<DonateRecord> donateRecords =donateRecordService.queryDonateRecordByEp(d, pageNum, pageSize);
			page = BeanUtil.copyPage((Page<DonateRecord>) donateRecords, ApiDonateRecord.class);
		}catch (Exception e){
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public ApiPage<ApiDonateRecord> queryByInvoiceApplyList(Integer userId, int pageNum, int pageSize) {
		logger.info("queryByInvoiceApplyList userId = " + userId);


		List<DonateRecord> list = donateRecordService.queryByInvoiceApplyList(userId, pageNum, pageSize);

		ApiPage<ApiDonateRecord> apiPage = BeanUtil.copyPage(
				(Page<DonateRecord>) list, ApiDonateRecord.class);

		return apiPage;
	}

	@Override
	public ApiFrontUserInvoiceDTO queryByInvoiceIndex(Integer userId) {
		logger.info("queryByInvoiceIndex userId = " + userId);
		
		FrontUser_invoice frontUser_invoice = userInvoiceService.queryByInvoiceIndex(userId);
		//ApiFrontUser_invoice apiFrontUser_invoice = BeanUtil.copy(frontUser_invoice, ApiFrontUser_invoice.class);
		ApiFrontUserInvoiceDTO dto = new ApiFrontUserInvoiceDTO();
		BeanUtils.copyProperties(frontUser_invoice, dto);


		FrontUser_address address = new FrontUser_address();
		address.setIsSelected(1);
		List<FrontUser_address> frontUser_addresses = userAddressService.queryByParam(address, 1, 1);
		if(CollectionUtils.isEmpty(frontUser_addresses)){
			address.setIsSelected(null);
			frontUser_addresses = userAddressService.queryByParam(address, 1, 1);
		}

		if(!CollectionUtils.isEmpty(frontUser_addresses)){
			ApiFrontUser_address apiFrontUser_address = new ApiFrontUser_address();
			BeanUtils.copyProperties(frontUser_addresses.get(0), apiFrontUser_address);
			dto.setFrontUser_address(apiFrontUser_address);
		}

		return dto;
	}

	@Override
	public ApiDonateRecord queryDonateRecordSuccess(ApiDonateRecord param) {
		logger.info("queryDonateRecordSuccess param = " + param);
		
		DonateRecord d = BeanUtil.copy(param, DonateRecord.class);
		DonateRecord donateRecord = donateRecordService.queryDonateRecordSuccess(d);
		ApiDonateRecord apiDonateRecord = BeanUtil.copy(donateRecord, ApiDonateRecord.class);
		return apiDonateRecord;
	}

	@Override
	public ApiResult saveInvoice(Integer userId, String invoiceList,Integer addressId, String invoiceHead) {
		logger.info("save saveInvoice >>userId= "+userId + "invoiceList=" +invoiceList + "addressId=" + addressId + "invoiceHead" + invoiceHead);
		
		if(invoiceList == null || invoiceList == "")
		{
			return ApiResultUtil.getParameterError();
		}
		try
		{
			FrontUser_address frontUser_address = userAddressService.queryUserAddress(addressId, userId);
			if(frontUser_address == null){
				return new ApiResult<>(ResultEnum.OtherError.getCode(), "地址Id不能为空");
			}
			userInvoiceService.saveInvoice(userId,invoiceList,addressId,invoiceHead);
			return ApiResultUtil.SUCCESS;
			
		}
		catch(BaseException e)
		{
			e.printStackTrace();
			return new ApiResult(ResultEnum.Error);
		}
	}

}
