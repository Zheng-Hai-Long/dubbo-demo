package com.guangde.business.service.user;

import com.guangde.business.dto.BuyProductFormDTO;
import com.guangde.business.dto.DepositFormDTO;
import com.guangde.business.entry.*;
import com.guangde.business.exception.BaseException;
import com.guangde.business.pojo.Result;

import java.util.List;

public interface IDonateRecordService
{

    /**
     * 生成捐赠记录
     * @param depositFormDTO
     */
    Result buyDonate(DepositFormDTO depositFormDTO) throws BaseException;

    /**
     * 生成购买记录
     * @param buyProductFormDTO
     */
    Result buyProduct(BuyProductFormDTO buyProductFormDTO) throws BaseException;

    List<DonateRecord> queryByCondition(DonateRecord donateRecord, int pageNum, int pageSize);
    /**
     * 发票详情中的捐款记录
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryByIdList(DonateRecord donateRecord);
    
    /**
     * 未开发票的捐款记录
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryDonateListByInvoice(DonateRecord donateRecord,Integer pageNum,Integer pageSize);
    
    /**
     * 统计未开发票的捐款记录的金额
     * @param donateRecord
     * @return
     */
    Double sumQueryDonateListByInvoice(DonateRecord donateRecord);
    
    /**
     * 按条件统计捐款总额
     * @param donateRecord
     * @return
     */
    Double countQueryByCondition(DonateRecord donateRecord);
    
    /**
     * 根据条件查询捐助金额
     * @param donateRecord
     * @return
     */
    Double countQueryDonateRecordList(DonateRecord donateRecord);
    
    /**
     * 按条件统计捐款人次
     * @param donateRecord
     * @return
     */
    Integer countNumQueryByCondition(DonateRecord donateRecord);
    
    Integer queryProjectIdByCapitalinoutId(String tranNum);
    
    /**
     * 微信的支付通知信息
     * @param tranNum 订单的id
     * @return
     */
    DonateRecord queryWeixinPayNoticeByTranNum(String tranNum);

    
    /**
     * pc支付成功页信息
     * @param tranNum
     * @return
     */
    DonateRecord queryPayNoticeByTranNum(String tranNum);
    
    /**
     * 捐赠排行榜
     * @param donateRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<DonateRecord> queryByDonate(DonateRecord donateRecord, int pageNum, int pageSize);
    
    /**
     * 最新获捐记录
     * @param donateRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<DonateRecord> queryLatestDonateRecordList(DonateRecord donateRecord, int pageNum, int pageSize);
    
    /**
     * 查询最新获捐记录id
     * @param donateRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<DonateRecord> queryLatestDonateRecordIdList(DonateRecord donateRecord, int pageNum, int pageSize);
    
    /**
     * 根据捐款最新id查询最新获捐记录
     * @param donateRecord
     * @return
     */
    List<DonateRecord> queryLatestDonateRecordIdDetailList(DonateRecord donateRecord);
    
    /**
     * 我的捐助记录
     * @param donateRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<DonateRecord> queryMyDonateRecordList(DonateRecord donateRecord, int pageNum, int pageSize);
    
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
     *  userId 
     *      donatType
     * @param donateRecord
     * @return
     */
    Integer countByCompanyGoodHelp(DonateRecord donateRecord);

    
    /**
     * 捐款后留言送祝
     * @return
     * @throws BaseException 
     */
    int updateDonatLeaveWord(DonateRecord donateRecord) throws BaseException;

    
    /**根据Parma查询资金明细*/
    Capitalinout queryCapitalinoutByParam(Capitalinout param);
    
    /**根据项目id查询捐款人昵称、头像*/
    List<DonateRecord> queryDonateHeadImgByProjectId(Integer projectId,int pageNum, int pageSize);

    
    /**
     * 余额日捐
     * @param ca
     * @param dr
     * @param project
     * @param user
     * @return
     */
    int addDayDonate(Capitalinout ca,DonateRecord dr,Project project,FrontUser user);
    
    /**
     * 根据时间、标题查询捐款记录行数
     * @param state（2015,2016,2017）
     * @return
     */
    int countDonateRecord(Integer state, String title);
    
    DonateRecord queryByDonateId(Integer id);

    void update(DonateRecord param) throws BaseException;
    
    /**
     * 根据项目Id、teamId查询一起捐团队成员捐款信息
     * @param togetherConfig
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<DonateRecord> queryYiXingTeamDonate(TogetherConfig togetherConfig,int pageNum, int pageSize);
    
    /**
     * 个人信息根据用户Id查询捐款列表项目列表
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<DonateRecord> queryByUserIdSelectLeaveWord(Integer userId,Integer isRead,int pageNum, int pageSize);


    /**
     * 根据用户Id查询捐赠日期
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<DonateRecord> queryByUserIdForDonateTime(DonateRecord donateRecord,int pageNum, int pageSize);
    
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
     * 根据条件查询捐款人数
     * @param donateRecord
     * @return
     */
    int countByDonateRecordParam(DonateRecord donateRecord);

    /**
     * 根据条件查询项目推荐人捐款记录
     * @param donateRecord
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<DonateRecord> queryDonateRecordByEp(DonateRecord donateRecord, int pageNum, int pageSize);
    
    List<DonateRecord> queryByInvoiceApplyList(Integer userId, int pageNum, int pageSize);
    
    DonateRecord queryDonateRecordSuccess(DonateRecord donateRecord);
    
}
