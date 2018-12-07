package com.guangde.user.facade.impl;

import com.guangde.api.user.ICompanyFacade;
import com.guangde.business.entry.ActivityConfig;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.ActivityConfigService;
import com.guangde.dto.ApiCapitalinoutDto;
import com.guangde.entry.ApiActivityConfig;
import com.guangde.entry.ApiCapitalinout;
import com.guangde.entry.ApiDonateRecord;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.guangde.util.ApiResultUtil;
import com.guangde.util.BeanUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("companyFacade")
public class CompanyFacadeImpl implements ICompanyFacade
{
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ActivityConfigService activityConfigService;

    
    @Override
    public ApiResult updateActivityConfig(ApiActivityConfig apiActivityConfig){
    	ActivityConfig activityConfig=BeanUtil.copy(apiActivityConfig, ActivityConfig.class);
    	try {
			activityConfigService.updateActivityConfig(activityConfig);
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
    }
    
    @Override
    public ApiResult saveActivityConfig(ApiActivityConfig apiActivityConfig){
    	ActivityConfig activityConfig=BeanUtil.copy(apiActivityConfig, ActivityConfig.class);
    	try {
			activityConfigService.saveActivityConfig(activityConfig);
			ApiResult result = new ApiResult();
			//result.setMessage("" + activityConfig.getId());
			result.setCode(1);
			return result;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
    }
    
    @Override
    public ApiActivityConfig queryByActivityConfig(Integer id){
    	logger.info("queryEmployeeById param : id = " + id);
    	if (id == null)
        {
            return null;
        }
    	ActivityConfig activityConfig=activityConfigService.queryById(id);
    	
    	ApiActivityConfig apiActivityConfig=BeanUtil.copy(activityConfig, ApiActivityConfig.class);
    	
    	return apiActivityConfig;
    }

    @Override
    public ApiResult launchCompanyGoodHelpResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg) {
        return null;
    }

    @Override
    public ApiResult companyReCharge(ApiCapitalinout apiCapitalinout) {
        return null;
    }

    @Override
    public ApiResult reCharge(ApiCapitalinout apiCapitalinout) {
        return null;
    }

    @Override
    public ApiResult companyReChargeResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg) {
        return null;
    }

    @Override
    public boolean checkAlipayResult(String tranNum, String payNum) {
        return false;
    }

    @Override
    public boolean checkAlipayResultByTranNum(String tranNum) {
        return false;
    }

    @Override
    public ApiPage<ApiCapitalinout> queryCapitalinoutList(ApiCapitalinout apiCapitalinout, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public ApiPage<ApiDonateRecord> queryCapitalinoutListByCompanyHelp(ApiDonateRecord apiDonateRecord, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public Double countQueryCapitalinoutListByCompanyHelp(ApiDonateRecord apiDonateRecord) {
        return null;
    }

    @Override
    public ApiCapitalinoutDto queryCapitalinoutDetail(ApiCapitalinout apiCapitalinout) {
        return null;
    }

    @Override
    public Double queryCapitalinoutListCountMoeny(ApiCapitalinout apiCapitalinout) {
        return null;
    }

    @Override
    public Integer updateShankuRecharge(Integer shanKuUserId, Integer libraryId, String chargeMoney, ApiCapitalinout capitalinout) {
        return null;
    }

    @Override
    public boolean isCompany(Integer userId) {
        return false;
    }

    @Override
    public boolean isOrNotCompanyMember(Integer userId) {
        return false;
    }

}
