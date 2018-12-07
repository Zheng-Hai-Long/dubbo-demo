package com.guangde.business.service;

import com.guangde.business.entry.ProjectFeedbackPraise;
import com.guangde.business.exception.BaseException;

public interface ProjectFeedbackPraiseService
{
    /**
     * 更新点赞状态
     * @param projectFeedbackPraise
     * @throws BaseException
     */
    void saveOrUpdateProjectFeedbackPraise(ProjectFeedbackPraise projectFeedbackPraise)
        throws BaseException;
    
    /**
     * 按条件查询点赞数
     * @param projectFeedbackPraise
     * @return
     */
    ProjectFeedbackPraise queryByPraiseList(ProjectFeedbackPraise projectFeedbackPraise);
}
