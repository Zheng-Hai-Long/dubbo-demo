package com.guangde.api.user;

import com.guangde.dto.ApiBuyProductFormDTO;
import com.guangde.dto.ApiDepositFormDTO;
import com.guangde.dto.ApiFrontUserInvoiceDTO;
import com.guangde.entry.*;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;

import java.util.List;

public interface IDonateRecordFacade
{

    /**
     * 认捐
     * @return
     */
    ApiResult buyDonate(ApiDepositFormDTO depositForm);

    /**
     * 生成购买商品订单
     * @return
     */
    ApiResult buyProduct(ApiBuyProductFormDTO dto);

    /**
     * 捐款明细
     *  募捐项目
     *      最新捐助
     * @param apiDonateRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiDonateRecord> queryByCondition(ApiDonateRecord apiDonateRecord, int pageNum, int pageSize);

    
    /**
     * 未开发票的捐款记录
     * @param apiDonateRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiDonateRecord> queryDonateListByInvoice(ApiDonateRecord apiDonateRecord, int pageNum, int pageSize);

    /**
     * 统计未开发票的捐款记录金额
     * @param apiDonateRecord
     * @return
     */
    Double sumQueryDonateListByInvoice(ApiDonateRecord apiDonateRecord);
    
    /** 捐款明细
     * 募捐项目    金额统计
     * @param apiDonateRecord
     * @return
     */
    Double countQueryByCondition(ApiDonateRecord apiDonateRecord);
    
    /**
     * 根据条件统计捐助金额
     * @param apiDonateRecord
     * @return
     */
    Double countQueryDonateRecordList(ApiDonateRecord apiDonateRecord);
    
    /**
     * 按条件统计捐款人次
     * @param apiDonateRecord
     * @return
     */
    Integer countNumQueryByCondition(ApiDonateRecord apiDonateRecord);

    
    /**
     * 认捐
     * @param donate 认捐记录
     * @param payType 支付类型  当payType = freezType 时走余额通道
     * @param apiCapitalinout   当 余额足够支付时  apiCapitalinout 给 null 值 不够支付 apiCaitalinout 给需要充值的金额信息
     * 
     * @return
     */
    ApiResult buyDonate(ApiDonateRecord donate, ApiCapitalinout apiCapitalinout, String payType, ApiFrontUser apiFrontUser, String slogans);
    

    /**
     * 认捐结果
     * @param payNum 支付号
     * @param tranNum 交易号
     * @param isPaySuccess 是否支付成功
     * @param errorMsg 失败原因，交易成功不用传值
     * @param payType = freezType 代表余额不足发起的捐款 
     * @return
     */
    ApiResult buyDonateResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg, String payType);

    ApiResult buyDonateResult(String tranNum, String payNum, String payType);
    
    /**
     * 充值结果
     * @param payNum 支付号
     * @param tranNum 交易号
     * @param isPaySuccess 是否支付成功
     * @param errorMsg 失败原因，交易成功不用传值
     * @param payType = freezType 代表余额不足发起的捐款 
     * @return
     */
    ApiResult reChargeResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg, String payType);

    
    /**
     * 企业助捐次数、企业助捐金额
     *      企业助善号召网友数、企业助善金额
     *          donatType -- 必传   personalItems : 个人    enterpriseDonation ： 企业
     *          userId    -- 必传
     *          state (302)-- 必传 
     * @param apiDonateRecord
     * @return
     */
    ApiDonateRecord queryCompanyCenter(ApiDonateRecord apiDonateRecord);
    
    /**
     * 根据资金id 查找项目Id
     * @param tranNum
     * @return
     */
    Integer queryProjectIdByCapitalinout(String tranNum);
    
    
    /**
     * 根据资金id 查找微信的支付通知信息
     * @param tranNum
     * @return
     */
    ApiDonateRecord queryWeixinToPayNoticeByTranNum(String tranNum);
    
    /**
     * 根据支付号查找订单信息
     * @param tranNum
     * @return
     */
    ApiDonateRecord queryPayNoticeByTranNum(String tranNum);
    
    /**
     * 最新获捐记录
     * @param apiDonateRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiDonateRecord> queryLatestDonateRecordList(ApiDonateRecord apiDonateRecord, int pageNum, int pageSize);
    
    /**
     * 查询最新获捐记录id
     * @param apiDonateRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiDonateRecord> queryLatestDonateRecordIdList(ApiDonateRecord apiDonateRecord, int pageNum, int pageSize);
    
    /**
     * 根据最新捐款记录id查询最新获捐记录
     * @param apiDonateRecord
     * @return
     */
    List<ApiDonateRecord> queryLatestDonateRecordIdDetailList(ApiDonateRecord apiDonateRecord);
    
    /**
     * 我的捐助
     * @param apiDonateRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiDonateRecord> queryMyDonateRecordList(ApiDonateRecord apiDonateRecord, int pageNum, int pageSize);
    
    /**
     * 用户捐款后送祝福
     * @param apiDonateRecord
     * @return
     */
    int updateLeaveWord(ApiDonateRecord apiDonateRecord);
    
    
    /**
     * 批量认捐
     * @param donate 认捐记录
     * @param payType 支付类型  当payType = freezType 时走余额通道
     * @param apiCapitalinout   当 余额足够支付时  apiCapitalinout 给 null 值 不够支付 apiCaitalinout 给需要充值的金额信息
     * 
     * @return
     */
    ApiResult batchBuyDonate(ApiDonateRecord donate, ApiCapitalinout apiCapitalinout, String payType);
    
    
    /**
     * 批量认捐结果
     * @param payNum 支付号
     * @param tranNum 交易号
     * @param isPaySuccess 是否支付成功
     * @param errorMsg 失败原因，交易成功不用传值
     * @param payType = freezType 代表余额不足发起的捐款 
     * @return
     */
    ApiResult batchBuyDonateResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg, String payType);
    /**
     * 日捐月捐入库
     * @param apidt
     * @return
     */
    ApiResult saveDonateTime(ApiDonateTime apidt);

    /**
     * 更新日捐月捐
     * @param apidt
     * @return
     */
    ApiResult updateDonateTime(ApiDonateTime apidt);
    /**
     * 通过userId查询月捐计划
     * @param userId
     * @return
     */
    ApiPage<ApiDonateTime> queryByUserId(Integer userId,int pageNum, int pageSize);
    /**
     * 通过param查询月捐计划
     * @param param
     * @return
     */
    ApiPage<ApiDonateTime> queryDonateTimeByParam(ApiDonateTime param,int pageNum, int pageSize);




    
    /**根据param查询资金明细*/
    ApiCapitalinout queryCapitalinoutByParam(ApiCapitalinout param);
    
    /**根据项目id查询捐款人昵称、头像*/
    ApiPage<ApiDonateRecord> queryDonateHeadImgByProjectId(Integer projectId,int pageNum, int pageSize);
    

    
    /**
     * 余额日捐
     * @param cp
     * @param dr
     * @param project
     * @param user
     * @return
     */
    ApiResult addDayDonate(ApiCapitalinout cp,ApiDonateRecord dr,ApiProject project,ApiFrontUser user);
    

    
    /**
     * 根据时间、标题查询捐款记录行数
     * @param state（2015,2016,2017）
     * @return
     */
    int countDonateRecord(Integer state, String title);
    
    ApiDonateRecord queryByDonateId(Integer id);

    
    /**
     * 根据项目Id、teamId查询一起捐团队成员捐款信息
     * @param apiTogetherConfig
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiDonateRecord> queryYiXingTeamDonate(ApiTogetherConfig apiTogetherConfig,int pageNum, int pageSize);
    
    /**
     * 根据用户Id查询捐赠列表及项目信息列表
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiDonateRecord> queryByUserIdSelectLeaveWord(Integer userId,Integer isRead,int pageNum, int pageSize);

    
    /**
     * 根据用户Id查询捐赠日期
     * @param apiDonateRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiDonateRecord> queryByUserIdForDonateTime(ApiDonateRecord apiDonateRecord,int pageNum, int pageSize);
    
    /**
     * 根据日期查询捐款记录详情
     * @param apiDonateRecord
     * @return
     */
    List<ApiDonateRecord> queryByDonateTimeForInfo(ApiDonateRecord apiDonateRecord);
    
    /**
     * 根据捐赠类型和时间查询详情
     * @param apiDonateRecord
     * @return
     */
    List<ApiDonateRecord> queryByDonateTimeParam(ApiDonateRecord apiDonateRecord);

    int countByDonateRecordParam(ApiDonateRecord apiDonateRecord);

    /**
     * 查询项目推荐人捐款记录
     * @param donateRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiDonateRecord> queryDonateRecordByEp(ApiDonateRecord donateRecord, int pageNum, int pageSize);
    
    /**
     * 查询用户可申请发票的捐赠记录
     * @param userId
     * @return
     */
    ApiPage<ApiDonateRecord> queryByInvoiceApplyList(Integer userId, int pageNum, int pageSize);

    ApiFrontUserInvoiceDTO queryByInvoiceIndex(Integer userId);
    
    ApiDonateRecord queryDonateRecordSuccess(ApiDonateRecord param);
    
    ApiResult saveInvoice(Integer userId,String invoiceList,Integer addressId,String invoiceHead);

}
