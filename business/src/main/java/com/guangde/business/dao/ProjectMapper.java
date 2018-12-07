package com.guangde.business.dao;

import com.guangde.business.entry.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectMapper extends BaseMapper<Project>
{

    List<Project> queryProjectList(Project param);
    
    List<Project> queryProjectMaybeList();

    /**
     * 不在ids内的，点击最高的count条英目
     * @param ids id组合
     * @param count  查询的条数
     * @return 项目列表
     */
    List<Project> queryProectByHits(String ids, int count);
    
    /**
     * 查询code领域中点击最高count条项目
     * @param code  领域编号
     * @param count 条数
     * @return 项目列表
     */
    List<Project> queryHomeDonation(String code, int count);

    
    /**
     * 善管家 项目列表
     * @return
     */
    List<Project> queryKeeperProjectList(Project project);
    

    
    /**
     * 善管家 项目列表(审核中)
     * @return
     */
    List<Project> queryKeeperProjectListChecking(Project project);
    
    /**
     * 按项目状态 统计项目数量
     * @param project
     * @return
     */
    List<Project> countKeeperProjectByState(Project project);
    
    /**
     * 用户感兴趣的项目
     * @param project
     * @return
     */
    List<Project> queryProjectByInterest(Project project);
    
    Project queryProjectByUser(Integer userId);
    
    List<Project> queryProjectListByUser(Integer userId);
    
    Project queryProjectByDonateRecord(Integer capitalinoutId);
    
    Project queryProjectByCompanyGoodHelp(Integer capitalinoutId);
    
    Project queryProjectByCompanyGoodHelpBack(Integer capitalinoutId);
    
    /**
     * 新列表展示
     * @param project
     * @return
     */
    List<Project> queryByParamNew(Project project);
    
    /**
     * 首页累计捐款人
     * @return
     */
    Integer countDonatePeople();
    
    /**
     * 首页累计项目发布数
     * @param field 排除该领域，""表示不排除  
     * @param isHide 排除隐藏，-1表示不排除
     * @return
     */
    Integer countDonateProject(String field,Integer isHide);
    
    /**
     * 分享的项目
     * @param project
     * @return
     */
    List<Project> queryShareProject(Project project);
    
    /**
     * 分享的项目数
     * @param project
     * @return
     */
    Integer countQueryShareProject(Project project);
    
    /**
     * 分享的项目 参与人数
     * @param project
     * @return
     */
    Integer countQueryShareProjectUser(Project project);
    
    /**
     * 分享的项目链接  募集到的善款总额
     * @param project
     * @return
     */
    Double sumQueryShareProject(Project project);
    
    /**
     * 我的求助项目 参与的爱心人数
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
     * 一级类目查询
     * @param project
     * @return
     */
    List<Project> queryOneLevel(Project project);
    
    /**
     * 二级类目查询
     * @param project
     * @return
     */
    List<Project> queryTwoLevel(Project project);
    /**
     * 通过标签统计项目数
     * @param project
     * @return
     */
    Integer countProjectNumByTags(Project project);

   
    /**
     * h5端个人用户中心项目查询
     * @param project
     * @return
     */
    List<Project > queryUCenterProjectlist(Project project);
    
    /**
     * 根据条件查询项目详情
     * @param project
     * @return
     */
    List<Project> queryProjectByIds(Project project);
    
    /**
     * 根据条件查询good项目详情
     * @param project
     * @return
     */
    List<Project> queryRandomProjectList(Project project);
    
    /**
     * 根据条件查询项目详情
     * @param project
     * @return
     */
    List<Project> queryProjectAndUserInfo(Project project);
    
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
    List<Project> queryProjectUserIds();
    
    /**
     * 根据项目id查询一对一受助人数
     * @param projectId
     * @return
     */
    int queryOneAidHelpByprojectId(Integer projectId);

    List<Project> queryProjectBy30();
    
    List<Project> queryByIndexProjectList(Project project);
    
    List<Project> queryByNewProjectList(Project project);
    
    List<Project> queryByFieldOrArea(@Param("field") String field);
    
    void updateProjectShareCount(Integer projectId);
}
