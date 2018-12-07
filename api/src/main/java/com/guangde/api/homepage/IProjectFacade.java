package com.guangde.api.homepage;

import com.guangde.dto.ProjectDTO;
import com.guangde.dto.ProjectDetailDTO;
import com.guangde.entry.*;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;

import java.util.List;

public interface IProjectFacade
{

	/**
	 * 查询列表
	 */
	ApiPage<ProjectDTO> queryProjectList(ApiProject apiProject, int pageNum, int pageSize);
	
	/**
	 * 随机2个项目
	 * @return
	 */
	ApiPage<ProjectDTO> queryProjectMaybeList(int pageNum, int pageSize);

	/**
	 * 项目详情
	 * @param id
	 * @return
     */
	ProjectDetailDTO queryProjectDetailById(Integer id);
    
    /**
     * 募捐列表的数据，根据不同的关键字即类型来显示的数据接口
     * 
     * @param hot
     *            是否热门 -1 所有 0：非热门 1：热门
     * @param code
     *            数据字典中领域的编号
     * @param count
     *            显示的条数
     * @return 募捐的项目列表
     */
    List<ApiProject> queryDonation(Integer hot, String code, int count);
    
    /**
     * 新项目列表
     */
    ApiPage<ApiProject> queryProjectListNew(ApiProject apiProject, int pageNum, int pageSize);
    
    /**
     * 查询项目详情
     * @param id
     * @return ApiProject
     */
    ApiProject queryProjectDetail(Integer id);
    
    /**
     * 发起项目
     * @param apiProject
     * @return
     */
    ApiResult launchProject(ApiProject apiProject);
    
    /**
     * 保存项目人员信息
     * @param list  证明人  求助人
     * @return
     */
    ApiResult saveProjectUserInfo(List<ApiProjectUserInfo> list);
    
    /**
     * 修改项目人员信息
     * @param list  证明人  求助人
     * @return
     */
    ApiResult updateProjectUserInfo(List<ApiProjectUserInfo> list);
    
    /**查找项目人员信息
     * 
     * @param apiProjectUserInfo
     * @return
     */
    ApiProjectUserInfo queryProjectUserInfo(ApiProjectUserInfo apiProjectUserInfo);
    
    /**
     * 查找项目人员信息列表
     * @param apiProjectUserInfo
     * @return
     */
    List<ApiProjectUserInfo> queryProjectUserInfoList(ApiProjectUserInfo apiProjectUserInfo);


    
    /**
     * 项目进度列表
     * @param apiProjectSchedule
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiProjectSchedule> queryProjectScheduleList(ApiProjectSchedule apiProjectSchedule, int pageNum, int pageSize);
    
    /**
     * 项目执行记录列表
     * @param apiReport
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiReport> queryReportList(ApiReport apiReport, int pageNum, int pageSize);
    
    /**
     * 项目反馈入库
     * @param apiProjectFeedback
     * @return
     */
    ApiResult saveProjectFeedback(ApiProjectFeedback apiProjectFeedback);

    
    /**
     * 项目反馈记录列表
     * @param apiProjectFeedback
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiProjectFeedback> queryProjectFeedbackList(ApiProjectFeedback apiProjectFeedback, int pageNum, int pageSize);

    
    /**
     * 按条件查询项目反馈数
     * @param apiProjectFeedback
     * @return
     */
    Integer countProjectFeedbackByParam(ApiProjectFeedback apiProjectFeedback);

    
    /**
     * 根据用户id查询是否留过言
     * @param userId
     * @return
     */
    boolean queryIsOrNotNewLeaveWordByUserId(Integer userId);

    
    /**
     * 关注的项目
     * @param apiProjectFeedback
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiProjectFeedback> queryCareProjectFeedbckByCondition(ApiProjectFeedback apiProjectFeedback, int pageNum, int pageSize);
    
    /**
     * h5的项目反馈的数据显示
     * @param apiProjectFeedback
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiProjectFeedback> queryH5ProjectFeedbckByCondition(ApiProjectFeedback apiProjectFeedback, int pageNum, int pageSize);

    
    /**
     * 分享的项目
     * @param apiProject
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiProject> queryShareProject(ApiProject apiProject, int pageNum, int pageSize);
    
    /**
     * 查询一级 类目
     * @param apiProject
     * @return
     */
    List<ApiProject> queryOneLevel(ApiProject apiProject);

    
    /**
     * 保存留言
     * @param param
     * @return
     */
    ApiResult saveNewLeaveWord(ApiNewLeaveWord param);
    /**
     * 根据条件查询留言回复信息
     * @param param
     * @return
     */
    ApiPage<ApiNewLeaveWord> queryApiNewLeaveWord(ApiNewLeaveWord param,int pageNum,int pageSize);
    
    
    ApiPage<ApiProject>queryUCenterProjectlist(ApiProject param,int pageNum,int pageSize);

    
    /**
     * 查询未回复的留言记录列表
     * @param param
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiNewLeaveWord> queryNoReplyByParam(ApiNewLeaveWord param,int pageNum,int pageSize);
    
    /**
	 * 根据条件查询支付配置
	 * @param param
	 * @return
	 */
	List<ApiPayConfig> queryByParam(ApiPayConfig param);

	/**
	 * 根据条件查询项目捐款配置列表
	 * @param param
	 * @return
	 */
	List<ApiProjectMoneyConfig> queryMoneyConfigByParam(ApiProjectMoneyConfig param);
	
	/**
	 * 根据条件查询good项目
	 * @param param
	 * @return
	 */
	ApiPage<ApiProject> queryRandomProjectList(ApiProject param,int pageNum,int pageSize);
	
	/**
	 * 根据条件查询项目详情
	 * @param param
	 * @return
	 */
	List<ApiProject> queryProjectAndUserInfo(ApiProject param);

	
	/**根据model查询报名信息
	 * 
	 * @param param
	 * @return
	 */
	List<ApiCommonFormUser> queryCommonFormUserByParam(ApiCommonFormUser param);
	
	/**
	 * 保存一起捐配置
	 * @param param
	 * @return
	 */
	ApiResult saveTogetherConfig(ApiTogetherConfig param);
	
	/**
	 * 更新一起捐配置
	 * @param param
	 * @return
	 */
	ApiResult updateTogetherConfig(ApiTogetherConfig param);
	
	/**
	 * 根据param查询一起捐
	 * @param param
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	ApiPage<ApiTogetherConfig> queryTogetherConfigList(ApiTogetherConfig param,int pageNum,int pageSize);
	
	/**
	 * 根据param查询一起捐
	 * @param param
	 * @return
	 */
	ApiTogetherConfig queryByTogetherConfig(ApiTogetherConfig param);
	
	/**
	 *  根据项目id查询一起捐list（包括用户昵称、头像）
	 * @param projectId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	ApiPage<ApiTogetherConfig> queryTogetherConfigByProjectId(Integer projectId,int pageNum,int pageSize);
	
	/**
	 * 查询项目反馈和项目进度
	 * @return
	 */
	ApiPage<ApiProjectFeedback> queryFeedbackAndReportList(int pageNum,int pageSize);
	
	/**
	 * 根据项目id查询一起捐，捐款，留言
	 * @param projectId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	ApiPage<ApiTogetherConfig> queryDetailByProjectId(Integer projectId,int pageNum,int pageSize);
	
	/**
	 * 根据项目id查询一起捐，捐款，同一用户累计
	 * @param projectId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	ApiPage<ApiTogetherConfig> queryTogetherDonateListByProjectId(Integer projectId,int pageNum,int pageSize);
	
	/**
	 * 根据项目Id查询一起捐列表的发起人数、募捐金额、募捐次数的总和
	 * @param apiTogetherConfig
	 * @return
	 */
	List<ApiTogetherConfig> oneApiTogetherConfigTotal(ApiTogetherConfig apiTogetherConfig);

    /**
     * 查询最近30天的项目，按捐款比例排序
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiProject> queryProjectBy30(int pageNum, int pageSize);

    ApiTogetherConfig countTogetherDonateMoneyAndDonateNumberByProjectId(Integer paramInteger);
	
	/**
     * h5的根据用户查询项目反馈的数据显示
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiProjectFeedback> queryH5ProjectFeedbckByUserList(Integer userId, int pageNum, int pageSize);
    
    /**
     * 查询首页项目列表
     */
    ApiPage<ApiProject> queryByIndexProjectList(ApiProject apiProject, int pageNum, int pageSize);

    /**
     * 查询新版项目列表
     * @param apiProject
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiProject> queryByNewProjectList(ApiProject apiProject, int pageNum, int pageSize);

    
    /**
     * 根据领域查询项目信息
     * @param field
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiProject> queryByFieldAndArea(String field,int pageNum,int pageSize);
    
    /**
     * 更新项目分享数
     * @param projectId
     * @return
     */
    ApiResult updateProjectShareCount(Integer projectId);
}
