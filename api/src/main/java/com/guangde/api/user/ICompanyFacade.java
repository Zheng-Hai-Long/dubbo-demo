package com.guangde.api.user;


import com.guangde.dto.ApiCapitalinoutDto;
import com.guangde.entry.ApiActivityConfig;
import com.guangde.entry.ApiCapitalinout;
import com.guangde.entry.ApiDonateRecord;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;

public interface ICompanyFacade
{
    /**
     * 添加活动
     * @param activityConfig
     * @return
     */
    ApiResult updateActivityConfig(ApiActivityConfig activityConfig);
    
    /**
     * 编辑活动
     * @param param
     * @return
     */
	ApiResult saveActivityConfig(ApiActivityConfig param);
	
	/**
	 * 根据id查询活动表
	 * @param id
	 * @return
	 */
	ApiActivityConfig queryByActivityConfig(Integer id);
    

    /**
     * 发起企业助善结果
     * @param tranNum
     * @param payNum
     * @param isPaySuccess
     * @param errorMsg
     * @return
     */
    ApiResult launchCompanyGoodHelpResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg);
    
    /**
     * 企业充值
     * @param apiCapitalinout
     * @return
     */
    ApiResult companyReCharge(ApiCapitalinout apiCapitalinout);
    
    /**
     * 充值
     * @param apiCapitalinout
     * @return
     */
    ApiResult reCharge(ApiCapitalinout apiCapitalinout);
    
    /**
     *  企业充值结果 
     * @param tranNum
     * @param payNum
     * @param isPaySuccess
     * @param errorMsg
     * @return
     */
    ApiResult companyReChargeResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg);
    
    /**校验支付宝回调结果
     * 
     * @param tranNum
     * @param payNum
     * @return
     */
    boolean checkAlipayResult(String tranNum, String payNum);
    
    /**
     * 根据订单号校验支付宝回调结果
     * @param tranNum
     * @return
     */
    boolean checkAlipayResultByTranNum(String tranNum);

    /**
     * 充值记录
     *  资金明细
     * @param apiCapitalinout
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiCapitalinout> queryCapitalinoutList(ApiCapitalinout apiCapitalinout, Integer pageNum, Integer pageSize);
    
    /**
     * 企业助善， 助善项目 助捐的资金明细（已号召人数连接）
     *  
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiDonateRecord> queryCapitalinoutListByCompanyHelp(ApiDonateRecord apiDonateRecord, Integer pageNum, Integer pageSize);
    
    /**
     * 企业助善， 助善项目 助捐的资金明细 金额统计
     * @param apiDonateRecord
     * @return
     */
    Double countQueryCapitalinoutListByCompanyHelp(ApiDonateRecord apiDonateRecord);
    
    /**
     * 资金明细详情
     * @param apiCapitalinout
     * @return
     */
    ApiCapitalinoutDto queryCapitalinoutDetail(ApiCapitalinout apiCapitalinout);
    
    /**
     * 充值记录金额统计
     * @param apiCapitalinout
     * @return
     */
    Double queryCapitalinoutListCountMoeny(ApiCapitalinout apiCapitalinout);
    

    
    /**
     * 个人用户余额为进行善库充值
     * @param shanKuUserId
     * @param libraryId
     * @param chargeMoney
     * @param capitalinout
     * @return
     */
    Integer updateShankuRecharge(Integer shanKuUserId,Integer libraryId,String chargeMoney,ApiCapitalinout capitalinout);

    
    /**
     * 判断是个人还是团队
     * @param userId
     * @return
     */
    boolean isCompany(Integer userId);
    
    /**
     * 判断用户是否是‘社会团体’的成员
     * @param userId
     * @return
     */
    boolean isOrNotCompanyMember(Integer userId);
    
}
