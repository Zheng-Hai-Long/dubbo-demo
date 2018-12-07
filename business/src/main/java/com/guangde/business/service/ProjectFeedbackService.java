package com.guangde.business.service;

import java.util.List;

import com.guangde.business.entry.ProjectFeedback;
import com.guangde.business.exception.BaseException;

public interface ProjectFeedbackService
{
    /**
     * 项目反馈记录入库
     * @param projectFeedback
     * @throws BaseException 
     */
    void saveProjectFeedback(ProjectFeedback projectFeedback)
        throws BaseException;
    
    /**
     * 项目反馈记录审核
     * @param projectFeedback
     * @throws BaseException 
     */
    void updateProjectFeedback(ProjectFeedback projectFeedback)
        throws BaseException;
    
    /**
     * 项目反馈记录列表
     * @param projectFeedback
     * @return
     * @throws BaseException 
     */
    List<ProjectFeedback> queryProjectFeedbackList(ProjectFeedback projectFeedback, Integer pageNum, Integer pageSize);
    
    /**
     * 按条件查询统计反馈数
     * @param projectFeedback
     * @return
     */
    Integer countProjectFeedbackByParam(ProjectFeedback projectFeedback);
    
    /**
     *查询项目反馈和项目进度
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<ProjectFeedback> queryFeedbackAndReportList(Integer pageNum, Integer pageSize);
}
