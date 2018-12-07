package com.guangde.business.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.CapitalinoutMapper;
import com.guangde.business.dao.PayMoneyRecordMapper;
import com.guangde.business.dao.ProjectMapper;
import com.guangde.business.entry.Capitalinout;
import com.guangde.business.entry.PayMoneyRecord;
import com.guangde.business.entry.Project;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.user.IPayMoneyRecordService;
import com.guangde.business.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("payMoneyRecordService")
public class PayMoneyRecordService extends BaseService implements IPayMoneyRecordService
{
    @Autowired
    private PayMoneyRecordMapper payMoneyRecordMapper;
    
    @Autowired
    private CapitalinoutMapper capitalinoutMapper;
    
    @Autowired
    private ProjectMapper projectMapper;
    
    @Override
    public void saveMoney(PayMoneyRecord payMoenyRecord)
        throws BaseException
    {
        logger.info("drawMoney param ： " + payMoenyRecord);
        
        Project p = projectMapper.queryById(payMoenyRecord.getProjectId());
        if (p == null)
        {
            throw new BaseException(ResultCode.Error);
        }
        
        //已募捐金额
        Double donatAmount = p.getDonatAmount();
        //已打款金额
        Double panyAmount = p.getPanyAmount();
        
        BigDecimal bd1 = new BigDecimal(Double.toString(donatAmount)); 
    	BigDecimal bd2 = new BigDecimal(Double.toString(panyAmount)); 
    	
    	//剩余可打款金额
    //	Double leaveAmount = donatAmount - panyAmount;
    	Double leaveAmount = bd1.subtract(bd2).doubleValue(); 
        
        if (leaveAmount < payMoenyRecord.getApplyMoney())
        {
            throw new BaseException(ResultCode.buy_not_enough_amount);
        }
        payMoenyRecord.setState(State.notPay);
        List<PayMoneyRecord> payList = payMoneyRecordMapper.queryByParam(payMoenyRecord);
        if (payList != null && payList.size() > 0)
        {
            throw new BaseException(ResultCode.drawMoneyNoEnd);
        }
        try
        {
            
            // 资金进出
            Capitalinout capitalinout = new Capitalinout();
            capitalinout.setUserId(payMoenyRecord.getUserId());
            capitalinout.setType(Constant.CAPITAL_OUT);
            capitalinout.setInType(Constant.DRAW_MONEY_OUT);
            capitalinout.setMoney(payMoenyRecord.getApplyMoney());
            capitalinout.setPayType(payMoenyRecord.getPayType());
            capitalinout.setPayState(Constant.PAY_STATE_300);
            capitalinout.setTranNum(payMoenyRecord.getTranNum());
            capitalinout.setSource(payMoenyRecord.getSource());
            
            int ret = capitalinoutMapper.save(capitalinout);
            if (ret > 0)
            {
                //                DonateRecord donate = new DonateRecord();
                //                donate.setProjectId(payMoenyRecord.getProjectId());
                //                donate.setDonatAmount(payMoenyRecord.getPanMoney());
                //                donate.setDonatType("drawMoney");
                //                donate.setCapitalinoutId(capitalinout.getId());
                //                donate.setDonorType(donate.getUserId() != null ? Constant.DONOR_TYPE_IN : Constant.DONOR_TYPE_OUT);
                //                donate.setState(Constant.PAY_STATE_300);
                //                donate.setDonatTime(DateUtil.getDate());
                //                
                //                donateRecordMapper.save(donate);
                
                // 打款记录表
                payMoenyRecord.setCapitalinoutId(capitalinout.getId());
                payMoenyRecord.setState(Constant.PAY_STATE_300);
                payMoenyRecord.setPayMoneyTime(DateUtil.getDate());
                
                payMoneyRecordMapper.save(payMoenyRecord);
                
            }
        }
        catch (Exception e)
        {
            logger.error("drawMoney error", e);
            throw new BaseException(ResultCode.Error);
        }
    }
    
    @Override
    public List<PayMoneyRecord> queryPayMoneyRecord(PayMoneyRecord payMoneyRecord, Integer pageNum, Integer pageSize)
    {
        logger.info("queryPayMoneyRecord param : " + payMoneyRecord);
        
        PageHelper.startPage(pageNum, pageSize);
        
        List<PayMoneyRecord> list = payMoneyRecordMapper.queryByParam(payMoneyRecord);
        
        return list;
    }
    
    @Override
    public void updatePayMoneyRecordResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg)
        throws BaseException
    {
        logger.info("updatePayMoneyRecordResult>> tranNum = " + tranNum + " payNum = " + payNum + " isPaySuccess = " + isPaySuccess + " errorMsg = " + errorMsg);
        
        Capitalinout param = new Capitalinout();
        param.setTranNum(tranNum);
        
        List<Capitalinout> list = capitalinoutMapper.queryByParam(param);
        
        if (CollectionUtil.isEmpty(list))
        {
            logger.info("update drawMoneyResult error, tranNum:" + tranNum);
            throw new BaseException(ResultCode.buy_pay_num_error);
        }
        
        Capitalinout c = list.get(0);
        
        PayMoneyRecord dparam = new PayMoneyRecord();
        dparam.setCapitalinoutId(c.getId());
        List<PayMoneyRecord> dlist = payMoneyRecordMapper.queryByParam(dparam);
        
        if (CollectionUtil.isEmpty(dlist))
        {
            logger.info("update drawMoneyResult error, capitalinoutId:" + c.getId());
            throw new BaseException(ResultCode.drawMoney_not_found);
        }
        
        try
        {
            Capitalinout newCapitalinout = new Capitalinout();
            newCapitalinout.setId(c.getId());
            newCapitalinout.setPayState(isPaySuccess ? Constant.PAY_STATE_302 : Constant.PAY_STATE_301);
            newCapitalinout.setTranNum(tranNum);
            newCapitalinout.setPayNum(payNum);
            capitalinoutMapper.update(newCapitalinout);
            
            PayMoneyRecord newPayMoneyRecord = new PayMoneyRecord();
            newPayMoneyRecord.setId(dlist.get(0).getId());
            newPayMoneyRecord.setState(isPaySuccess ? Constant.PAY_STATE_302 : Constant.PAY_STATE_301);
            
            payMoneyRecordMapper.update(newPayMoneyRecord);
        }
        catch (Exception e)
        {
            logger.error("updatePayMoneyRecordResult error", e);
            throw new BaseException(ResultCode.Error);
        }
    }
    
    @Override
    public List<Capitalinout> queryCapitalinoutList(Capitalinout capitalinout, Integer pageNum, Integer pageSize)
    {
        logger.info("queryCapitalinoutList param : " + capitalinout);
        
        PageHelper.startPage(pageNum, pageSize);
        List<Capitalinout> list = null;
        try
        {
            
            list = capitalinoutMapper.queryByParam(capitalinout);
        }
        catch (Exception e)
        {
            logger.error("queryCapitalinoutList error", e);
        }
        
        return list;
    }

    @Override
    public PayMoneyRecord sumPayMoney() {
        return payMoneyRecordMapper.sumPayMoney();
    }

    @Override
	public List<PayMoneyRecord> queryFollowPayMoneyRecord(
			PayMoneyRecord payMoneyRecord, Integer pageNum, Integer pageSize) {
		
		  	logger.info("queryFollowPayMoneyRecord param : " + payMoneyRecord);
	        
	        PageHelper.startPage(pageNum, pageSize);
	        
	        List<PayMoneyRecord> list = payMoneyRecordMapper.queryFollowPayMoneyList(payMoneyRecord);
	        
	        return list;
	}

	@Override
	public List<PayMoneyRecord> queryPayMoneyRecordGo(
			PayMoneyRecord payMoneyRecord, Integer pageNum, Integer pageSize) {
		
	 	logger.info("queryPayMoneyRecordGo param : " + payMoneyRecord);
        
        PageHelper.startPage(pageNum, pageSize);
        
        List<PayMoneyRecord> list = payMoneyRecordMapper.queryPayMoneyListGo(payMoneyRecord);
        
        return list;
	}
    
}
