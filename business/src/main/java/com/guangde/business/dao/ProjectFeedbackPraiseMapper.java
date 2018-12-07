package com.guangde.business.dao;

import com.guangde.business.entry.ProjectFeedbackPraise;
import com.guangde.business.exception.BaseException;

public interface ProjectFeedbackPraiseMapper extends BaseMapper<ProjectFeedbackPraise>
{
	/**
     * 保存点赞状态
     * @param projectFeedbackPraise
     * @throws BaseException
     */
    void saveProjectFeedbackPraise(ProjectFeedbackPraise projectFeedbackPraise)
        throws BaseException;
    
    /**
     * 更新点赞状态
     * @param projectFeedbackPraise
     * @throws BaseException
     */
    void updateProjectFeedbackPraise(ProjectFeedbackPraise projectFeedbackPraise)
        throws BaseException;
    
    /**
     * 查询点赞表的记录
     * @param param
     * @return
     */
    ProjectFeedbackPraise queryByParamPraise(ProjectFeedbackPraise param);
}
