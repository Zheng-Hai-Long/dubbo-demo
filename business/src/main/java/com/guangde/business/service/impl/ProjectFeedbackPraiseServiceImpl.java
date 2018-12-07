package com.guangde.business.service.impl;

import java.util.Date;

import com.guangde.business.dao.ProjectFeedbackMapper;
import com.guangde.business.dao.ProjectFeedbackPraiseMapper;
import com.guangde.business.dao.TogetherConfigMapper;
import com.guangde.business.entry.ProjectFeedback;
import com.guangde.business.entry.ProjectFeedbackPraise;
import com.guangde.business.entry.TogetherConfig;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.ProjectFeedbackPraiseService;
import com.guangde.business.util.ResultCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("projectFeedbackPraiseService")
public class ProjectFeedbackPraiseServiceImpl extends BaseService implements ProjectFeedbackPraiseService
{
    @Autowired
    private ProjectFeedbackPraiseMapper projectFeedbackPraiseMapper;

    @Autowired
    private ProjectFeedbackMapper projectFeedbackMapper;
    
    @Autowired
    private TogetherConfigMapper togetherConfigMapper;
    
	@Override
	public void saveOrUpdateProjectFeedbackPraise(
			ProjectFeedbackPraise projectFeedbackPraise) throws BaseException {
		logger.info("receive update or save ApiProjectFeedbackPraise param : " + projectFeedbackPraise);
		try
		{
			ProjectFeedbackPraise p = new ProjectFeedbackPraise();
			p.setUserId(projectFeedbackPraise.getUserId());
			p.setFeedbackId(projectFeedbackPraise.getFeedbackId());
			p.setTeamId(projectFeedbackPraise.getTeamId());
			if(p.getFeedbackId() != null && !"".equals(p.getFeedbackId())){
        		ProjectFeedbackPraise praise2 = projectFeedbackPraiseMapper.queryByParamPraise(p);
    			if(praise2 == null){
    				projectFeedbackPraise.setCreateTime(new Date());
    				projectFeedbackPraiseMapper.saveProjectFeedbackPraise(projectFeedbackPraise);
    				ProjectFeedback pFeedback = projectFeedbackMapper.queryById(p.getFeedbackId());
    				if(projectFeedbackPraise.getState()==1){
    					pFeedback.setPraise(pFeedback.getPraise() + 1);
    				}else{
    					pFeedback.setPraise(pFeedback.getPraise() - 1);
    				}
    				projectFeedbackMapper.update(pFeedback);
    			}else{
    				projectFeedbackPraise.setId(praise2.getId());
    				projectFeedbackPraise.setCreateTime(praise2.getCreateTime());
    				projectFeedbackPraiseMapper.updateProjectFeedbackPraise(projectFeedbackPraise);
    				ProjectFeedback pFeedback = projectFeedbackMapper.queryById(p.getFeedbackId());
    				if(projectFeedbackPraise.getState()==1){
    					pFeedback.setPraise(pFeedback.getPraise() + 1);
    				}else{
    					pFeedback.setPraise(pFeedback.getPraise() - 1);
    				}
    				projectFeedbackMapper.update(pFeedback);
    			}
        	}else if(p.getTeamId() != null && !"".equals(p.getTeamId())){
        		ProjectFeedbackPraise praise2 = projectFeedbackPraiseMapper.queryByParamPraise(p);
    			if(praise2 == null){
    				projectFeedbackPraise.setCreateTime(new Date());
    				projectFeedbackPraiseMapper.saveProjectFeedbackPraise(projectFeedbackPraise);
    				TogetherConfig tConfig = togetherConfigMapper.queryById(p.getTeamId());
    				if(projectFeedbackPraise.getState()==1){
    					tConfig.setPraise(tConfig.getPraise() + 1);
    				}else{
    					tConfig.setPraise(tConfig.getPraise() - 1);
    				}
    				togetherConfigMapper.update(tConfig);
    			}else{
    				projectFeedbackPraise.setId(praise2.getId());
    				projectFeedbackPraise.setCreateTime(praise2.getCreateTime());
    				projectFeedbackPraiseMapper.updateProjectFeedbackPraise(projectFeedbackPraise);
    				TogetherConfig tConfig = togetherConfigMapper.queryById(p.getTeamId());
    				if(projectFeedbackPraise.getState()==1){
    					tConfig.setPraise(tConfig.getPraise() + 1);
    				}else{
    					tConfig.setPraise(tConfig.getPraise() - 1);
    				}
    				togetherConfigMapper.update(tConfig);
    			}
        	}else{
        		logger.info(" teamId and feedbackId is null");
        	}
        	
		} catch (BaseException e) {
			logger.error("update or save praise error", e);
            throw new BaseException(ResultCode.Error);
		}
		
	}

	@Override
	public ProjectFeedbackPraise queryByPraiseList(
			ProjectFeedbackPraise p) {
		logger.info("query TogetherConfig param:" + p);
		return projectFeedbackPraiseMapper.queryByParamPraise(p);
	}
    
    
}
