package com.guangde.business.dao;

import com.guangde.business.entry.DonateRecord;
import com.guangde.business.entry.TogetherConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DonateRecordMapper extends BaseMapper<DonateRecord>
{
    /**
     * 未开发票的
     * 		捐款记录
     * @param donateRecord
     * @return
     */
     List<DonateRecord> queryDonateListByInvoice(DonateRecord donateRecord);
     
     /**
      * 助善项目，助捐列表 金额统计
      * @param donateRecord
      * @return
      */
     Double sumQueryDonateListByInvoice(DonateRecord donateRecord);
    
    /**
     * 捐赠排行榜
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryByDonate(DonateRecord donateRecord);
    
    /**
     * 发票 捐款记录
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryByIdList(DonateRecord donateRecord);
    
    /**
     * 助善项目，助捐列表
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryDonateRecordList(DonateRecord donateRecord);
    
    
    /**
     * 
     * @param donateRecord
     * @return
     */
    Double countQueryDonateRecordList(DonateRecord donateRecord);
    
    /**
     * 信息统计
     *      1、捐助次数，捐助总额
     *          2、企业助善号召网友数，企业助善总额
     * @param donateRecord
     * @return
     */
    DonateRecord countByParam(DonateRecord donateRecord);
    
    /**
     * 企业助善项目数
     * @param donateRecord
     * @return
     */
    Integer countByCompanyGoodHelp(DonateRecord donateRecord);
    
    /**
     * 统计企业助捐的项目 (包括企业员工捐助的项目)
     * @param userId
     * @return
     */
    Integer countCompanyDonateProject(Integer companyId, Integer userId);
    
    DonateRecord queryByDonateId(Integer id);
    
    /**
     * 企业累计捐赠
     * @param companyId
     * @return
     */
    Double countDonateAmountByCompanyId(Integer companyId, Integer userId);
    /**
     * 按条件统计捐款总额
     * @param donateRecord
     * @return
     */
    Double countQueryByParam(DonateRecord donateRecord);
    /**
     * 按条件统计捐款人次
     * @param donateRecord
     * @return
     */
    Integer countNumQueryByParam(DonateRecord donateRecord);
    
    Integer queryProjectIdByCapitalinoutId(String stranNum);
    
    DonateRecord queryWeixinPayNoticeByTranNum(String stranNum);
    
    DonateRecord queryBatchWeixinPayNoticeByTranNum(String stranNum);
    /**
     * 根据支付号查找支付信息
     * @param tranNum
     * @return
     */
    DonateRecord queryPayNoticeByTranNum(String tranNum);
    
    /**
     * 根据支付号查找支付信息
     * @param tranNum
     * @return
     */
    DonateRecord queryPayNoticeByPayNum(String tranNum);
    
    
    /**
     * 获捐记录
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryDonateRecordListByUser(DonateRecord donateRecord);
    
    /**
     * 最新获捐记录
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryLatestDonateRecordList(DonateRecord donateRecord);
    
    /**
     * 最新获捐记录id
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryLatestDonateRecordIdList(DonateRecord donateRecord);
    
    /**
     * 根据最新捐款id查询最新获捐记录
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryLatestDonateRecordIdDetailList(DonateRecord donateRecord);
    
    /**
     * h5我的捐助
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryMyDonate(DonateRecord donateRecord);
    
    /**
     * 捐款后留言送祝福
     * @param donateRecord
     * @return
     */
    int updateGoodLuck(DonateRecord donateRecord);
    
    /**
     *  h5我的捐助项目数统计
     * @param donateRecord
     * @return
     */
    Integer countQueryMyDonate(DonateRecord donateRecord);
    /**
     * 用户捐款统计排除善库和红包
     * @param userId
     * @return
     */
    DonateRecord countDonateByUser(Integer userId);
    /**
     * 通过用户id统计捐赠项目数
     * @param userId
     * @return
     */
    Integer countProjectNumByUserId(Integer userId);
    /**
     * 查询包括善库和红包的所有行善记录
     * @param dr
     * @return
     */
    List<DonateRecord> queryDonateRecordByParam(DonateRecord dr);
    /**
     * 根据useId查询捐款项目ids
     * @param dr
     * @return
     */
    List<DonateRecord> queryByParamUserId(DonateRecord dr);
    /**
     * 查询捐助的用户数（一个用户捐助多次算一次）
     * @param projectId
     * @return
     */
    Integer countDistDonateUserNum(Integer projectId);
    
    /**
     * 根据项目id查询捐款详情，同一用户捐款累加
     * @param dr
     * @return
     */
    List<DonateRecord> queryByParamBycount(DonateRecord dr);
    
    /**
     * 统计上周捐款排行
     * @return
     */
    List<DonateRecord> countDonateByWeek(DonateRecord dr);
    
    /**
     * 统计上月捐款排行
     * @return
     */
    List<DonateRecord> countDonateByMonth(DonateRecord dr);
    
    /**
     * 统计本年捐款排行
     * @return
     */
    List<DonateRecord> countDonateByYear(DonateRecord dr);
    
    /**统计15秒前捐款成功的用户数据*/
    List<DonateRecord> queryDonateBy15();
    
    /**根据项目id查询捐款人昵称、头像*/
    List<DonateRecord> queryDonateHeadImgByProjectId(Integer projectId);
    
    /**
     * 查询项目日捐详情
     * @param projectId
     * @return
     */
    List<DonateRecord> queryDayDonateDetail(Integer projectId);
    
    /**
     * 根据tradeNo查询捐款信息
     * @param tradeNo
     * @return
     */
    DonateRecord queryDonateInfoByTradeNo(String tradeNo);
    
    /**
     * 根据tradeNo查询步捐信息
     * @param tradeNo
     * @return
     */
    DonateRecord queryDonateStepByTranNum(String tradeNo);
    
    /**
     * 根据param查询步捐项目
     * @param param
     * @return
     */
    DonateRecord queryStepProjectByParam(DonateRecord param);
    
    /**
     * 根据时间、标题查询捐款记录行数
     * @param state（2015,2016,2017）
     * @return
     */
    int countDonateRecord(@Param("state")Integer state, @Param("title")String title);
    
    /**
     * 根据项目Id、teamId查询一起捐团队成员捐款信息
     * @param togetherConfig
     * @return
     */
    List<DonateRecord> queryYiXingTeamDonate(TogetherConfig togetherConfig);
    
    List<DonateRecord> queryByUserIdSelectLeaveWord(@Param("userId")Integer userId, @Param("isRead")Integer isRead);

    /**
     * 将项目id按最新捐款排序
     * @param ids
     * @return
     */
    List<Integer> queryLastDonateProjectIds(@Param("ids")List<Integer> ids);
    
    /**
     * 根据用户Id查询捐赠日期 
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryByUserIdForDonateTime(DonateRecord donateRecord);
    
    /**
     * 根据日期查询捐款记录详情 
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryByDonateTimeForInfo(DonateRecord donateRecord);
    
    /**
     * 根据捐赠类型和时间查询详情 
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryByDonateTimeParam(DonateRecord donateRecord);
    
    /**
     * 根据日期查询善库人员捐款记录详情 
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryByDonateTimeForGoodLibrary(DonateRecord donateRecord);

    /**
     * 根据条件查询捐款人数
     * @return
     */
    int countByDonateRecordParam(DonateRecord donateRecord);

    /**
     * 根据条件查询项目推荐人捐款记录
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryDonateRecordByEp(DonateRecord donateRecord);

    /**
     * 根据用户id查询有效推广项目数
     * @param userId
     * @return
     */
    int countEpProject(Integer userId);
    
    DonateRecord queryDonateRecordSuccess(DonateRecord donateRecord);
    
    List<DonateRecord> selectDonateRecordForInvoice(DonateRecord donateRecord);
}
