package com.guangde.business.dao;

import java.util.List;

import com.guangde.business.entry.ProjectFeedback;

public interface ProjectFeedbackMapper extends BaseMapper<ProjectFeedback>
{
    Integer queryCountByParam(ProjectFeedback projectFeedback);
    /**
     * 关注项目列表
     * @param user
     * @return
     */
    List<ProjectFeedback> queryCareProjectFeedbackList(ProjectFeedback projectFeedback);
    
    /**
     * h5的项目反馈的数据显示
     * @param projectFeedback
     * @return
     */
    List<ProjectFeedback> queryH5ProjectFeedbackList(ProjectFeedback projectFeedback);
    
    Integer countCareProjectFeedbackList(ProjectFeedback projectFeedback);
    
    /**
     * 查询项目反馈和项目进度
     * @return
     */
    List<ProjectFeedback> queryFeedbackAndReportList();
    
    /**
     * h5的根据用户查询项目反馈的数据显示
     * @param projectFeedback
     * @return
     */
    List<ProjectFeedback> queryH5ProjectFeedbackByUserList(Integer userId);
}
