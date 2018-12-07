package com.guangde.api.user;

import com.guangde.entry.ApiCapitalinout;
import com.guangde.entry.ApiPayMoneyRecord;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;

public interface IPayMoneyRecordFacade
{
    /**
     * 提款
     * @param apiPayMoneyRecord
     * @return
     */
    ApiResult drawMoney(ApiPayMoneyRecord apiPayMoneyRecord);
    
    /**
     * 提现记录
     * @param apiPayMoneyRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiPayMoneyRecord> queryPayMoneyRecordList(ApiPayMoneyRecord apiPayMoneyRecord, Integer pageNum, Integer pageSize);
    
    
    /**
     * 善款跟踪
     * @param apiPayMoneyRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiPayMoneyRecord> queryFollowPayMoneyRecordList(ApiPayMoneyRecord apiPayMoneyRecord, Integer pageNum, Integer pageSize);
  
    
    /**
     * 善款去向
     * @param apiPayMoneyRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiPayMoneyRecord> queryPayMoneyRecordGO(ApiPayMoneyRecord apiPayMoneyRecord, Integer pageNum, Integer pageSize);
    
    
    /**
     * 提现结果
     * @param payNum 支付号
     * @param tranNum 交易号
     * @param isPaySuccess 是否提现成功
     * @param errorMsg
     * @return
     */
    ApiResult drawMoneyResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg);
    
    /**
     * 资金进出
     *      充值明细
     * @param apiCapitalinout
     * @param pageNum
     * @param pageSize
     *  type 0 : 资金进  1：资金出
     * @return
     */
    ApiPage<ApiCapitalinout> queryCapitalinout(ApiCapitalinout apiCapitalinout, Integer pageNum, Integer pageSize);

    /**
     * 打款总金额
     * @return
     */
    ApiPayMoneyRecord sumPayMoney();
}
