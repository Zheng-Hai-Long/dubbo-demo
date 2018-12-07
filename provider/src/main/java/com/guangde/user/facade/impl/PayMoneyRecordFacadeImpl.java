package com.guangde.user.facade.impl;

import com.github.pagehelper.Page;
import com.guangde.api.user.IPayMoneyRecordFacade;
import com.guangde.business.entry.Capitalinout;
import com.guangde.business.entry.PayMoneyRecord;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.user.IPayMoneyRecordService;
import com.guangde.business.util.StrUtil;
import com.guangde.entry.ApiCapitalinout;
import com.guangde.entry.ApiPayMoneyRecord;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.guangde.util.ApiResultUtil;
import com.guangde.util.BeanUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("payMoneyRecordFacade")
public class PayMoneyRecordFacadeImpl implements IPayMoneyRecordFacade
{
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private IPayMoneyRecordService payMoneyRecordService;
    
    @Override
    public ApiResult drawMoney(ApiPayMoneyRecord apiPayMoneyRecord)
    {
        logger.info("drawMoney param : " + apiPayMoneyRecord);
        PayMoneyRecord payMoneyRecord = BeanUtil.copy(apiPayMoneyRecord, PayMoneyRecord.class);
        if (!valParam(payMoneyRecord))
        {
            return ApiResultUtil.getParameterError();
        }
        try
        {
            
            payMoneyRecordService.saveMoney(payMoneyRecord);
            return ApiResultUtil.SUCCESS;
            
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }
    
    public boolean valParam(PayMoneyRecord payMoneyRecord)
    {
        
        boolean ret = true;
        if (null == payMoneyRecord)
        {
            logger.info("payMoneyRecord is  null");
            ret = false;
        }
        else if (payMoneyRecord.getAccount() == null)
        {
            logger.info("account is  null");
            ret = false;
        }
        else if (payMoneyRecord.getApplyMoney() == null)
        {
            logger.info("ApplyMoney is null");
            ret = false;
        }
        else if (payMoneyRecord.getRecipientBankType() == null)
        {
            logger.info("recipientBankType is null");
            ret = false;
        }
        else if (payMoneyRecord.getRecipientName() == null)
        {
            logger.info("recipientName is null");
            ret = false;
        }
        else if (payMoneyRecord.getUserId() == null)
        {
            logger.info("userId is null");
            ret = false;
        }
        else if (payMoneyRecord.getReceiptImageId() == null)
        {
            logger.info("receiptImageId is null");
            ret = false;
        }
        
        return ret;
    }
    
    @Override
    public ApiPage<ApiPayMoneyRecord> queryPayMoneyRecordList(ApiPayMoneyRecord apiPayMoneyRecord, Integer pageNum, Integer pageSize)
    {
        logger.info("queryPayMoneyRecordList param ： " + apiPayMoneyRecord);
        if (apiPayMoneyRecord == null)
        {
            return new ApiPage<ApiPayMoneyRecord>(pageNum, pageSize);
        }
        PayMoneyRecord payMoneyRecord = BeanUtil.copy(apiPayMoneyRecord, PayMoneyRecord.class);
        
        List<PayMoneyRecord> list = payMoneyRecordService.queryPayMoneyRecord(payMoneyRecord, pageNum, pageSize);
        
        ApiPage<ApiPayMoneyRecord> ret = BeanUtil.copyPage((Page<PayMoneyRecord>)list, ApiPayMoneyRecord.class);
        
        return ret;
    }
    
    @Override
    public ApiResult drawMoneyResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg)
    {
        logger.info("receive drawMoneyResult param : tranNum = " + tranNum + " payNum = " + payNum + " isPaySuccess = " + isPaySuccess + " errorMsg = " + errorMsg);
        if (StrUtil.isEmpty(payNum))
        {
            return ApiResultUtil.getParameterError();
        }
        
        try
        {
            payMoneyRecordService.updatePayMoneyRecordResult(tranNum, payNum, isPaySuccess, errorMsg);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }
    
    @Override
    public ApiPage<ApiCapitalinout> queryCapitalinout(ApiCapitalinout apiCapitalinout, Integer pageNum, Integer pageSize)
    {
        logger.info("receive queryCapitalinout param : " + apiCapitalinout);
        if (apiCapitalinout == null)
        {
            return new ApiPage<ApiCapitalinout>(pageNum, pageSize);
        }
        Capitalinout capitalinout = BeanUtil.copy(apiCapitalinout, Capitalinout.class);
        List<Capitalinout> list = payMoneyRecordService.queryCapitalinoutList(capitalinout, pageNum, pageSize);
        
        ApiPage<ApiCapitalinout> ret = BeanUtil.copyPage((Page<Capitalinout>)list, ApiCapitalinout.class);
        
        return ret;
    }

    @Override
    public ApiPayMoneyRecord sumPayMoney() {
        PayMoneyRecord record = payMoneyRecordService.sumPayMoney();
        ApiPayMoneyRecord newRecord = BeanUtil.copy(record, ApiPayMoneyRecord.class);
        return newRecord;
    }

    @Override
	public ApiPage<ApiPayMoneyRecord> queryFollowPayMoneyRecordList(
			ApiPayMoneyRecord apiPayMoneyRecord, Integer pageNum,
			Integer pageSize) {
		
		 	logger.info("queryFollowPayMoneyRecordList param ： " + apiPayMoneyRecord);
		 	
	        if (apiPayMoneyRecord == null)
	        {
	            return new ApiPage<ApiPayMoneyRecord>(pageNum, pageSize);
	        }
	        PayMoneyRecord payMoneyRecord = BeanUtil.copy(apiPayMoneyRecord, PayMoneyRecord.class);
	        
	        List<PayMoneyRecord> list = payMoneyRecordService.queryFollowPayMoneyRecord(payMoneyRecord, pageNum, pageSize);
	        
	        ApiPage<ApiPayMoneyRecord> ret = BeanUtil.copyPage((Page<PayMoneyRecord>)list, ApiPayMoneyRecord.class);
	        
	        return ret;
		
		
	}

	@Override
	public ApiPage<ApiPayMoneyRecord> queryPayMoneyRecordGO(
			ApiPayMoneyRecord apiPayMoneyRecord, Integer pageNum,
			Integer pageSize) {

	 	logger.info("queryPayMoneyRecordGO param ： " + apiPayMoneyRecord);
	 
        PayMoneyRecord payMoneyRecord = BeanUtil.copy(apiPayMoneyRecord, PayMoneyRecord.class);
        
        List<PayMoneyRecord> list = payMoneyRecordService.queryPayMoneyRecordGo(payMoneyRecord, pageNum, pageSize);
        
        ApiPage<ApiPayMoneyRecord> ret = BeanUtil.copyPage((Page<PayMoneyRecord>)list, ApiPayMoneyRecord.class);
        
        return ret;
	}
}
