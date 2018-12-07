package com.guangde.business.service.user;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.guangde.business.BaseTest;
import com.guangde.business.entry.Capitalinout;
import com.guangde.business.entry.PayMoneyRecord;
import com.guangde.business.exception.BaseException;

public class PayMoneyRecordServiceTest extends BaseTest
{
    @Resource
    private IPayMoneyRecordService payMoneyRecordService;
    
    //@Test
    public void testDrawMoney()
    {
        PayMoneyRecord payMoneyRecord = new PayMoneyRecord();
        payMoneyRecord.setUserId(649);
        payMoneyRecord.setAccount("62213431341343434444");
        payMoneyRecord.setApplyMoney(20.0);
        // payMoneyRecord.setPayMoneyTime(new Date());
        payMoneyRecord.setRecipientBankType("2");
        payMoneyRecord.setRecipientName("工商银行");
        payMoneyRecord.setProjectId(847);
        
        try
        {
            payMoneyRecordService.saveMoney(payMoneyRecord);
        }
        catch (BaseException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    //@Test
    public void testQuery()
    {
        PayMoneyRecord payMoneyRecord = new PayMoneyRecord();
        payMoneyRecord.setUserId(67);
        payMoneyRecord.setQueryEndDate(new Date());
        List<PayMoneyRecord> list = payMoneyRecordService.queryPayMoneyRecord(payMoneyRecord, 1, 20);
        if (list.size() > 0)
        {
            System.out.println(list.get(0).getProjectTitle());
        }
        System.out.println(list.size());
    }
    
    //  @Test
    public void testUpdateDrawResult()
    {
        String tranNum = "1234";
        try
        {
            payMoneyRecordService.updatePayMoneyRecordResult(tranNum, "5678", true, "");
        }
        catch (BaseException e)
        {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    // @Test
    public void testQueryCapitalinoutList()
    {
        Capitalinout c = new Capitalinout();
        c.setType(1);
        
        List<Capitalinout> list = payMoneyRecordService.queryCapitalinoutList(c, 1, 20);
        System.out.println(list.size());
    }
    
    @Test
    public void testQueryFollowPanMoneyRecordList(){
    	
    	PayMoneyRecord  payMoneyRecord = new PayMoneyRecord();
    	payMoneyRecord.setUserId(650);
    	List<PayMoneyRecord> list = payMoneyRecordService.queryFollowPayMoneyRecord(payMoneyRecord, 1, 20);
    	for(PayMoneyRecord p :list){
    		System.out.println(p);
    	}
    	
    }
}
