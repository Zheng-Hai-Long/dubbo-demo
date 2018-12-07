package com.guangde.business.service.user;

import com.guangde.business.entry.Capitalinout;
import com.guangde.business.entry.PayMoneyRecord;
import com.guangde.business.exception.BaseException;

import java.util.List;

public interface IPayMoneyRecordService
{
    /**
     * 提款
     * @param payMoenyRecord
     * @throws BaseException 
     */
    void saveMoney(PayMoneyRecord payMoenyRecord)
        throws BaseException;
    
    /**
     * 提现记录
     * @param payMoneyRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PayMoneyRecord> queryPayMoneyRecord(PayMoneyRecord payMoneyRecord, Integer pageNum, Integer pageSize);
    
    /**
     * 善款跟踪
     * @param payMoneyRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PayMoneyRecord> queryFollowPayMoneyRecord(PayMoneyRecord payMoneyRecord, Integer pageNum, Integer pageSize);
    
    /**
     * 善款去向
     * @param payMoneyRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PayMoneyRecord> queryPayMoneyRecordGo(PayMoneyRecord payMoneyRecord, Integer pageNum, Integer pageSize);
    
    /**
     * 修改提现结果
     * @throws BaseException 
     */
    void updatePayMoneyRecordResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg)
        throws BaseException;
    
    /**
     * 资金进出
     *      充值明细
     * @param capitalinout
     * @return
     */
    List<Capitalinout> queryCapitalinoutList(Capitalinout capitalinout, Integer pageNum, Integer pageSize);

    /**
     * 打款总金额
     * @return
     */
    PayMoneyRecord sumPayMoney();
    
}
