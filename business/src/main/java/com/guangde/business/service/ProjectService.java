package com.guangde.business.service;

import com.guangde.business.entry.*;
import com.guangde.business.exception.BaseException;

import java.util.List;

public interface ProjectService
{
    
    /**
     * 募捐列表的数据，根据不同的关键字即类型来显示的数据接口
     * @param hot 是否热门  -1 所有  0：非热门  1：热门
     * @param code 数据字典中领域的编号
     * @param count 显示的条数
     * @return 募捐的项目列表
     */
    List<Project> queryDonation(Integer hot, String code, int count);
    
    Project queryById(Integer id);
    
    /**
     * 感兴趣的项目
     * @param userId  用户
     * @return
     */
    List<Project> queryProjectListByInterest(Integer userId, Integer size);
    
    /**
     * 查询不在ids范围，点击量最高的count条 项目
     * @param ids ids
     * @param count 查询数据量
     * @return 项目列表
     */
    List<Project> queryProectByHits(String ids, int count);
    
    /**
     * 查询首面分类项目
     * @param field 领域编号
     * @param count 查询项目条数
     * @return 项目列表
     */
    List<Project> queryHomeDonation(String field, int count);
    
    /**
     * 查询项目列表
     * @param project
     * @param pageSize 
     * @param pageNum 
     * @return list
     */
    List<Project> queryProjectList(Project project, int pageNum, int pageSize);
    
    /**
     * 随机查询项目
     * @return
     */
    List<Project> queryProjectMaybeList(int pageNum, int pageSize);
    
    /**
     * 新项目列表
     * @param project
     * @param pageSize 
     * @param pageNum 
     * @return list
     */
    List<Project> queryProjectListNew(Project project, int pageNum, int pageSize);
    
    /**
     * 新项目列表(增加已捐未捐条件)
     * @param project
     * @param pageSize 
     * @param pageNum 
     * @return list
     */
    List<Project> queryProjectListNew(Project project, int pageNum, int pageSize, int state, int userId);
    
    /**
     * 查询关注项目列表
     * @param pageSize
     * @param pageNum 
     * @return list
     */
    List<ProjectFeedback> queryCareProjectFeedbackList(ProjectFeedback projectFeedback, int pageNum, int pageSize);
    
    /**
     * 查询h5的项目反馈列表
     * @param pageSize
     * @param pageNum 
     * @return list
     */
    List<ProjectFeedback> queryH5ProjectFeedbackList(ProjectFeedback projectFeedback, int pageNum, int pageSize);
    /**
     * 关注项目数
     * @param projectFeedback
     * @return
     */
    Integer countCareProjectFeedbackList(ProjectFeedback projectFeedback);

    
    /**
     * 查询项目详情
     * @param id
     * @return Project
     */
    Project queryProjectDetail(Integer id);
    
    /**
     * 认捐
     * @param donate 认捐记录
     * @param payType 支付类型
     * @throws BaseException 
     */
    void updateDonate(DonateRecord donate, String payType, FrontUser user, String slogans)
        throws BaseException;

    /**
     * 认捐（微信+红包）
     * @param donate
     * @param payType
     * @param user
     * @param slogans
     * @param redPacketId 红包id
     */
    void updateDonate(DonateRecord donate, String payType, FrontUser user, String slogans, Integer redPacketId)throws BaseException;
    /**
     * 认捐(账户余额)
     * @param donate 认捐记录
     * @param payType 支付类型
     * @throws BaseException 
     */
    void updateDonate(DonateRecord donate, Capitalinout capitalinout, String payType)
        throws BaseException;
    
    /**
     * 认捐(微信步数)
     * @param donate 认捐记录
     * @param payType 支付类型
     * @throws BaseException 
     */
    void updateWeRunDonate(DonateRecord donate, Capitalinout capitalinout, String payType)
        throws BaseException;
    
    /**
     * 认捐(账户余额)
     * @param donate 认捐记录
     * @param payType 支付类型
     * @throws BaseException 
     */
    void updateDonate2(DonateRecord donate, Capitalinout capitalinout, String payType)
        throws BaseException;
    
    /**
     * 认捐(红包)
     * @param donate 认捐记录
     * @param payType 支付类型
     * @param object 额外参数
     * @throws BaseException 
     */
    void updateDonate(DonateRecord donate, Capitalinout capitalinout, String payType,Object object,Object userRedpackets, Object obj1)
        throws BaseException;
    /**
     * 取消认捐
     * @throws BaseException
     */
    void updateDonate(DonateRecord donate)
        throws BaseException;
    
    int updateProject(Project project);
    
    /**
     * 项目发起
     * @param project
     * @return
     * @throws BaseException 
     */
    void saveProject(Project project)
        throws BaseException;
    
    /**
     * 更新认捐结果
     * @param tranNum
     * @param payNum
     * @param isPaySuccess
     * @param errorMsg
     * @throws BaseException
     * @author  Jfeng
     */
    void updateDonateResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg)
        throws BaseException;

    void updateDonateResult(String tranNum, String payNum, String payType)
            throws BaseException;

    /**
     * 更新资金明细结果
     * @param tranNum
     * @param payNum
     * @param isPaySuccess
     * @param errorMsg
     * @throws BaseException
     * @author  Jfeng
     */
    void updateCapitalinoutResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg)
        throws BaseException;
 
    /**
     * 更新认捐结果
     * @param tranNum
     * @param payNum
     * @param isPaySuccess
     * @param errorMsg
     * @throws BaseException
     * @author  Jfeng
     */
    void updateDonateResult(String tranNum, String payNum, boolean isPaySuccess, String errorMsg, String payType)
        throws BaseException;
    
    /**
     * 删除项目
     * @param id
     * @throws BaseException 
     */
    void deleteProject(Integer id)
        throws BaseException;
    
    /**
     * 项目进度信息列表
     * @param projectSchedule
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ProjectSchedule> queryProjectScheduleList(ProjectSchedule projectSchedule, int pageNum, int pageSize);
    
    /**
     * 项目执行记录(报表)入库
     * @param report
     * @throws BaseException 
     */
    void saveReport(Report report)
        throws BaseException;
    
    /**
     * 项目执行记录(报表)列表
     * @param report
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Report> queryReportList(Report report, int pageNum, int pageSize);
    
    /**
     * 按项目状态 统计项目数量
     * @param project
     * @return
     */
    List<Project> countKeeperProjectByState(Project project);
    
    /**
     * 分享的项目
     * @param project
     * @return
     */
    List<Project> queryShareProject(Project project,Integer pageNum,Integer pageSize);
    
    /**
     * 分享的项目数
     * @param project
     * @return
     */
    Integer countShareProjectList(Project project);
    
    /**
     * 分享的项目链接  参与的人数
     * @param project
     * @return
     */
    Integer countShareProjectListUser(Project project);
    
    /**
     * 分享的项目链接  募捐到的善款总额
     * @param project
     * @return
     */
    Double sumShareProjectListUser(Project project);
    
    /**
     * 我的求助项目 参与捐款的爱心人数
     * @param project
     * @return
     */
    Integer countDonatePeopleByUser(Project project);
    
    /**
     * 同一发起人获得的捐赠总金额和总捐赠笔数
     * @param project
     * @return
     */
    Project countDonatePeopleByUserId(Project project);
    
    /**
     * 一级类目
     * @param project
     * @return
     */
    List<Project> queryOneLevel(Project project);
    
    /**
     * 二级类目
     * @param project
     * @return
     */
    List<Project> queryTwoLevel(Project project);
    
    
    /**
     * 批量认捐
     * @param donate 认捐记录
     * @param payType 支付类型
     * @throws BaseException 
     */
    void updateBatchDonate(DonateRecord donate, Capitalinout capitalinout, String payType)
        throws BaseException;
    /**
     * 更新批量认捐结果
     * @param tranNum
     * @param payNum
     * @param isPaySuccess
     * @param errorMsg
     * @throws BaseException
     * @author  Jfeng
     */
    void updateBatchBuyDonateResult(String tranNum, String payNum,boolean isPaySuccess, String errorMsg, String payType)
			throws BaseException;
    /**
     * 通过标签统计项目数
     * @param project
     * @return
     */
    Integer countProjectNumByTags(Project project);
    
    /**
     * 通过用户id和项目状态来查询已捐和未捐项目数
     * @param project
     * @return
     */
    Integer countUserDonateNum(Project project,int userId,int state);
    
    /**
     * 用户中心根据条件查询项目列表
     * @param project
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Project> queryUCenterProjectlist(Project project,Integer pageNum,Integer pageSize);
    
    /**
     * 按条件查询评论数（去除自己留言、回复）
     * @return
     */
    Integer countLeaveWordNum(NewLeaveWord newLeaveWord);
    
    /**
     * 根据条件查询项目详情
     * @param param
     * @return
     */
    List<Project> queryProjectByIds(Project param,Integer pageNumInteger,Integer pageSize);
    
    /**
     * 根据条件查询good项目
     * @param param
     * @return
     */
    List<Project> queryRandomProjectList(Project param,Integer pageNumInteger,Integer pageSize);
    
    /**
	 * 根据条件查询项目详情
	 * @param param
	 * @return
	 */
	List<Project> queryProjectAndUserInfo(Project param);
	
	/**
	 * 根据用户id查询项目ids
	 * @param userId
	 * @return
	 */
	List<Integer> queryProjectIds(Integer userId);
	
	 /**
     * 查询项目发起人
     * @return
     */
    List<Project> queryProjectUserIds(int pageNum,int pageSize);
    
    /**
     * 根据项目id查询一对一受助人数
     * @param projectId
     * @return
     */
    int queryOneAidHelpByprojectId(Integer projectId);

    /**
     * 查询最近30天的项目，按捐款比例排序
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Project> queryProjectBy30(int pageNum, int pageSize);
    
    /**
     * h5的根据用户查询项目反馈列表
     * @param pageSize
     * @param pageNum 
     * @return list
     */
    List<ProjectFeedback> queryByUserIdSelectList(Integer userId, int pageNum, int pageSize);
    
    /**
     * 查询医疗救助项目列表
     * @param project
     * @param pageSize 
     * @param pageNum 
     * @return list
     */
    List<Project> queryByIndexProjectList(Project project, int pageNum, int pageSize);
    
    /**
     * 查询新版项目列表
     * @param project
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Project> queryByNewProjectList(Project project, int pageNum, int pageSize);
    
    /**
     * 根据领域查询项目信息
     * @param field
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Project> queryByFieldAndArea(String field,Integer pageNum,Integer pageSize);

    /**
     * 更新分享数
     * @param projectId
     * @throws BaseException
     */
    void updateProjectShareCount(Integer projectId) throws BaseException;
}
