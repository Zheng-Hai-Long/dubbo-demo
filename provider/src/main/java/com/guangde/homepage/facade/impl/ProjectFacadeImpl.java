package com.guangde.homepage.facade.impl;

import com.github.pagehelper.Page;
import com.guangde.api.homepage.IProjectFacade;
import com.guangde.business.entry.*;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.*;
import com.guangde.dto.ProjectDTO;
import com.guangde.dto.ProjectDetailDTO;
import com.guangde.entry.*;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.guangde.util.ApiResultUtil;
import com.guangde.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service("projectFacade")
public class ProjectFacadeImpl implements IProjectFacade
{
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private ProjectUserInfoService projectUserInfoService;
    
    @Autowired
    private ProjectFeedbackService projectFeedbackService;
    
    @Autowired
    private NewLeaveWordService newLeaveWordService;

    @Autowired
    private ProjectMoneyConfigService projectMoneyConfigService;

    @Autowired
    private TogetherConfigService togetherConfigService;

    @Override
    public ProjectDetailDTO queryProjectDetailById(Integer id) {
        logger.info("queryProjectDetailById id = " + id);
        Project project = projectService.queryProjectDetail(id);
        ProjectDetailDTO detailDTO = new ProjectDetailDTO();
        BeanUtils.copyProperties(project, detailDTO);
        return detailDTO;
    }

    @Override
    public List<ApiProject> queryDonation(Integer hot, String code, int count)
    {
        logger.info(" queryDonation param : " + "hot >> " + hot + " code >> " + code + " count >> " + count);
        
        List<Project> projects = projectService.queryDonation(hot, code, count);
        if (null == projects || projects.isEmpty())
        {
            logger.info(" projects is null ");
            return null;
        }
        
        List<ApiProject> list = new ArrayList<ApiProject>();
        ApiProject pro = null;
        
        for (Project project : projects)
        {
            pro = BeanUtil.copy(project, ApiProject.class);
            if (null != pro)
            {
                list.add(pro);
            }
        }
        
        return list;
    }
    
    @Override
    public ApiPage<ProjectDTO> queryProjectList(ApiProject apiProject, int pageNum, int pageSize)
    {
        logger.info("receive query param:" + apiProject);
        
        Project p = BeanUtil.copy(apiProject, Project.class);
        
        if (p == null)
        {
            new ApiPage<ApiProject>(pageNum, pageSize);
        }
        
        List<Project> list = projectService.queryProjectList(p, pageNum, pageSize);
        
        ApiPage<ProjectDTO> ret = BeanUtil.copyPage((Page<Project>)list, ProjectDTO.class);
        
        return ret;
    }
    
    @Override
    public ApiProject queryProjectDetail(Integer id)
    {
        Project p = projectService.queryProjectDetail(id);
        
        if (p == null)
        {
            return null;
        }
        
        List<BFile> list = p.getBfileList();
        List<ApiBFile> apiList = BeanUtil.copyList(list, ApiBFile.class);
        ApiProject api = BeanUtil.copy(p, ApiProject.class);
        api.setBfileList(apiList);
        
        return api;
    }
    
    @Override
    public ApiResult launchProject(ApiProject apiProject)
    {
        
        logger.info("receive save apiProject param : " + apiProject);
        
        Project project = BeanUtil.copy(apiProject, Project.class);
        
        if (!valParam(project))
        {
            return ApiResultUtil.getParameterError();
        }
        try
        {
            projectService.saveProject(project);
            ApiResult apiResult = ApiResultUtil.SUCCESS;
            //apiResult.setMessage(project.getId() + "");
            
            return apiResult;
            
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
        
    }
    
    public boolean valParam(Project project)
    {
        boolean ret = true;
        if (null == project)
        {
            logger.info("project is  null");
            ret = false;
            
        }
        else if (null != project.getState() && 200 != project.getState())
        {
            
            if (StringUtils.isEmpty(project.getTitle()))
            {
                logger.info("title is null");
                ret = false;
            }
            else if (StringUtils.isEmpty(project.getLocation()))
            {
                logger.info("location is null");
                ret = false;
            }
            else if (StringUtils.isEmpty(project.getDetailAddress()))
            {
                logger.info("detailAddress is null");
                ret = false;
            }
            else if (null == project.getCryMoney())
            {
                logger.info("cryMoney is  null");
                ret = false;
            }
            else if (null == project.getUserId())
            {
                logger.info(" userId is null ");
                ret = false;
            }
        }
        
        return ret;
    }
    
    @Override
    public ApiPage<ApiProjectSchedule> queryProjectScheduleList(ApiProjectSchedule apiProjectSchedule, int pageNum, int pageSize)
    {
        logger.info("queryProjectScheduleList param : " + apiProjectSchedule);
        
        ProjectSchedule p = BeanUtil.copy(apiProjectSchedule, ProjectSchedule.class);
        if (p == null)
        {
            new ApiPage<ApiProjectSchedule>(pageNum, pageSize);
        }
        List<ProjectSchedule> list = projectService.queryProjectScheduleList(p, pageNum, pageSize);
        
        ApiPage<ApiProjectSchedule> ret = BeanUtil.copyPage((Page<ProjectSchedule>)list, ApiProjectSchedule.class);
        
        return ret;
    }
    
    public boolean valParam(Report report)
    {
        boolean ret = true;
        
        if (report == null)
        {
            logger.info("report is  null");
            ret = false;
        }
        else if (report.getProjectId() == null)
        {
            logger.info("projectId  is null");
            ret = false;
        }
        else if (report.getReportPeople() == null)
        {
            logger.info("reportpeople is null");
            ret = false;
        }
        
        return ret;
        
    }
    
    @Override
    public ApiPage<ApiReport> queryReportList(ApiReport apiReport, int pageNum, int pageSize)
    {
        logger.info("queryReportList param : " + apiReport);
        
        Report report = BeanUtil.copy(apiReport, Report.class);
        if (report == null)
        {
            return new ApiPage<ApiReport>(pageNum, pageSize);
        }
        
        List<Report> list = projectService.queryReportList(report, pageNum, pageSize);
        ApiPage<ApiReport> ret = BeanUtil.copyPage((Page<Report>)list, ApiReport.class);
        
        return ret;
    }

    
    @Override
    public ApiResult saveProjectFeedback(ApiProjectFeedback apiProjectFeedback)
    {
        logger.info("saveReport Param : " + apiProjectFeedback);
        
        ProjectFeedback projectFeedback = BeanUtil.copy(apiProjectFeedback, ProjectFeedback.class);
        
        if (projectFeedback == null)
        {
            logger.info("projectFeedback  is null");
            return ApiResultUtil.getParameterError();
        }
        /*else if (projectFeedback.getProjectId() == null)
        {
            logger.info("projectId  is null");
            return ApiResultUtil.getParameterError();
        }*/
        else if (projectFeedback.getFeedbackPeople() == null)
        {
            logger.info("FeedbackPeople  is null");
            return ApiResultUtil.getParameterError();
        }
        try
        {
            
            projectFeedbackService.saveProjectFeedback(projectFeedback);
            return ApiResultUtil.SUCCESS;
            
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }

    @Override
    public ApiPage<ApiProjectFeedback> queryProjectFeedbackList(ApiProjectFeedback apiProjectFeedback, int pageNum, int pageSize) {
        return null;
    }


    @Override
    public Integer countProjectFeedbackByParam(ApiProjectFeedback apiProjectFeedback)
    {
        logger.info("countProjectFeedbackByParam  param : " + apiProjectFeedback);
        if (apiProjectFeedback == null)
        {
            return 0;
        }
        ProjectFeedback projectFeedback = BeanUtil.copy(apiProjectFeedback, ProjectFeedback.class);
        
        Integer count = projectFeedbackService.countProjectFeedbackByParam(projectFeedback);
        
        return count;
    }

    
    @Override
    public ApiResult saveProjectUserInfo(List<ApiProjectUserInfo> list)
    {
        List<ProjectUserInfo> userInfolist = BeanUtil.copyList(list, ProjectUserInfo.class);
        
        try
        {
            for (ProjectUserInfo p : userInfolist)
            {
                if (p.getProjectId() == null || p.getPersonType() == null)
                {
                    return ApiResultUtil.getParameterError();
                }
                projectUserInfoService.saveProjectUserInfo(p);
            }
            
            return ApiResultUtil.SUCCESS;
            
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
        
    }
    
    @Override
    public ApiProjectUserInfo queryProjectUserInfo(ApiProjectUserInfo apiProjectUserInfo)
    {
        logger.info("receive queryProjectUserInfo param : " + apiProjectUserInfo);
        
        if (apiProjectUserInfo == null)
        {
            return null;
        }
        
        ProjectUserInfo projectUserInfo = BeanUtil.copy(apiProjectUserInfo, ProjectUserInfo.class);
        
        projectUserInfo = projectUserInfoService.queryProjectUserInfo(projectUserInfo);
        
        ApiProjectUserInfo ret = BeanUtil.copy(projectUserInfo, ApiProjectUserInfo.class);
        return ret;
    }
    
    @Override
    public List<ApiProjectUserInfo> queryProjectUserInfoList(ApiProjectUserInfo apiProjectUserInfo)
    {
        logger.info("receive queryProjectUserInfo param : " + apiProjectUserInfo);
        
        if (apiProjectUserInfo == null)
        {
            return null;
        }
        ProjectUserInfo projectUserInfo = BeanUtil.copy(apiProjectUserInfo, ProjectUserInfo.class);
        
        List<ProjectUserInfo> list = projectUserInfoService.queryProjectUserInfoList(projectUserInfo);
        
        List<ApiProjectUserInfo> ret = BeanUtil.copyList(list, ApiProjectUserInfo.class);
        
        return ret;
    }
    
    @Override
    public ApiResult updateProjectUserInfo(List<ApiProjectUserInfo> list)
    {
        List<ProjectUserInfo> userInfolist = BeanUtil.copyList(list, ProjectUserInfo.class);
        
        try
        {
            for (ProjectUserInfo p : userInfolist)
            {
                
                projectUserInfoService.updateProjectUserInfo(p);
            }
            
            return ApiResultUtil.SUCCESS;
            
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }

	@Override
	public ApiPage<ApiProjectFeedback> queryCareProjectFeedbckByCondition(ApiProjectFeedback apiProjectFeedback,
			int pageNum, int pageSize) {
		
			logger.info("receive queryCareProjectFeedbckList param:" + apiProjectFeedback);
	        
			ProjectFeedback p = BeanUtil.copy(apiProjectFeedback, ProjectFeedback.class);
	        
	        if (p == null)
	        {
	            new ApiPage<ApiProject>(pageNum, pageSize);
	        }
	        
	        List<ProjectFeedback> list = projectService.queryCareProjectFeedbackList(p, pageNum, pageSize);
	        
	        ApiPage<ApiProjectFeedback> ret = BeanUtil.copyPage((Page<ProjectFeedback>)list, ApiProjectFeedback.class);
	        
	        return ret;
		
	}
	
	@Override
	public ApiPage<ApiProjectFeedback> queryH5ProjectFeedbckByCondition(ApiProjectFeedback apiProjectFeedback,
			int pageNum, int pageSize) {
		
        logger.info("receive queryH5ProjectFeedbckByCondition param:" + apiProjectFeedback);

        ProjectFeedback p = BeanUtil.copy(apiProjectFeedback, ProjectFeedback.class);

        if (p == null)
        {
            new ApiPage<ApiProject>(pageNum, pageSize);
        }

        List<ProjectFeedback> list = projectService.queryH5ProjectFeedbackList(p, pageNum, pageSize);
        NewLeaveWord newLeaveWord = new NewLeaveWord();
        for (ProjectFeedback projectFeedback : list) {
            newLeaveWord.setProjectFeedbackId(projectFeedback.getId());
            List<NewLeaveWord> newLeaveWords = newLeaveWordService.queryNewLeaveWord(newLeaveWord, pageNum, pageSize);
            if(!CollectionUtils.isEmpty(newLeaveWords)) {
                projectFeedback.setNewLeaveWordList(newLeaveWords);
            }
        }

        ApiPage<ApiProjectFeedback> ret = BeanUtil.copyPage((Page<ProjectFeedback>)list, ApiProjectFeedback.class);
	        
        return ret;
		
	}



	@Override
	public ApiPage<ApiProject> queryProjectListNew(ApiProject apiProject,
			int pageNum, int pageSize) {
			logger.info("receive query param:" + apiProject);
	        
	        Project p = BeanUtil.copy(apiProject, Project.class);
	        
	        if (p == null)
	        {
	            new ApiPage<ApiProject>(pageNum, pageSize);
	        }
	        
	        List<Project> list = projectService.queryProjectListNew(p, pageNum, pageSize);
	        //logger.info(list.get(0));
	        ApiPage<ApiProject> ret = BeanUtil.copyPage((Page<Project>)list, ApiProject.class);
	        //logger.info(ret.getResultData());
	        return ret;
	}
	

    @Override
	public ApiPage<ApiProject> queryShareProject(ApiProject apiProject,
			int pageNum, int pageSize) {
		
			logger.info("receive query param:" + apiProject);
	        
	        Project p = BeanUtil.copy(apiProject, Project.class);
	        
	        if (p == null)
	        {
	            new ApiPage<ApiProject>(pageNum, pageSize);
	        }
	        
	        List<Project> list = projectService.queryShareProject(p, pageNum, pageSize);
	        
	        ApiPage<ApiProject> ret = BeanUtil.copyPage((Page<Project>)list, ApiProject.class);
	        
	        return ret;
	}



	@Override
	public List<ApiProject> queryOneLevel(ApiProject apiProject) {
		if(apiProject == null)
		{
			return null ;
		}
		Project project = BeanUtil.copy(apiProject, Project.class);
		List<Project> list = projectService.queryOneLevel(project);
		List<ApiProject> ret = BeanUtil.copyList(list, ApiProject.class);
		return ret;
	}



	@Override
	public ApiResult saveNewLeaveWord(ApiNewLeaveWord param){
		logger.info("param is "+param);
		NewLeaveWord newLeaveWord = BeanUtil.copy(param, NewLeaveWord.class);
			try {
				newLeaveWordService.saveOrUpdate(newLeaveWord);
				return ApiResultUtil.SUCCESS;
			
		} catch (Exception e) {
			return ApiResultUtil.getApiResult(((BaseException) e).getResultCode());
		}
		}

	@Override
	public ApiPage<ApiNewLeaveWord> queryApiNewLeaveWord(ApiNewLeaveWord param,int pageNum,int pageSize) {
		logger.info("param is "+param);
		NewLeaveWord newLeaveWord = BeanUtil.copy(param, NewLeaveWord.class);
		 ApiPage<ApiNewLeaveWord> apiList=null;
		 if (newLeaveWord == null)
	        {
	            new ApiPage<ApiNewLeaveWord>(pageNum, pageSize);
	        }
		try {
			List<NewLeaveWord> list=newLeaveWordService.queryNewLeaveWord(newLeaveWord,pageNum,pageSize);
		    if(list.size()>0)
		    	apiList=BeanUtil.copyPage((Page<NewLeaveWord>)list, ApiNewLeaveWord.class);
		    
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return apiList;
	}

	@Override
	public ApiPage<ApiProject> queryUCenterProjectlist(ApiProject param,
			int pageNum, int pageSize) {
		Project project=BeanUtil.copy(param, Project.class);
		ApiPage<ApiProject> apiList=null;
		if(project!=null){
			new ApiPage<ApiProject>(pageNum, pageSize);
		}
		try {
			List<Project> list=projectService.queryUCenterProjectlist(project, pageNum, pageSize);
			if(list.size()>0){
				apiList=BeanUtil.copyPage((Page<Project>)list, ApiProject.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiList;
	}
	


	@Override
	public ApiPage<ApiNewLeaveWord> queryNoReplyByParam(ApiNewLeaveWord param,
			int pageNum, int pageSize) {
		NewLeaveWord newLeaveWord=BeanUtil.copy(param, NewLeaveWord.class);
		if(newLeaveWord!=null){
			new ApiPage<>(pageNum,pageSize);
		}
		ApiPage<ApiNewLeaveWord> apList=null;
		try {
			List<NewLeaveWord> list=newLeaveWordService.queryNoReplyByParam(newLeaveWord, pageNum, pageSize);
			if(list.size()>0){
				apList=BeanUtil.copyPage((Page<NewLeaveWord>)list,ApiNewLeaveWord.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apList;
	}



    @Override
    public List<ApiPayConfig> queryByParam(ApiPayConfig param) {
        return null;
    }


    @Override
		public List<ApiProjectMoneyConfig> queryMoneyConfigByParam(ApiProjectMoneyConfig param) {
		 ProjectMoneyConfig moneyConfig = BeanUtil.copy(param, ProjectMoneyConfig.class);
			List<ProjectMoneyConfig> list = projectMoneyConfigService.queryMoneyConfigParam(moneyConfig);
			 List<ApiProjectMoneyConfig> alist = new ArrayList<ApiProjectMoneyConfig>();
			 ApiProjectMoneyConfig ap = null;
		        for (ProjectMoneyConfig p : list)
		        {
		        	if(p!=null){
		        		ap= BeanUtil.copy(p, ApiProjectMoneyConfig.class);
		        		alist.add(ap);
		        	}
		        }
			return alist;
		}
	 
		@Override
		public ApiPage<ApiProject> queryRandomProjectList(ApiProject param,
				int pageNum, int pageSize) {
		 Project project=BeanUtil.copy(param, Project.class);
			if(project!=null){
				new ApiPage<>(pageNum,pageSize);
			}
			ApiPage<ApiProject> apList=null;
			try {
				List<Project> list=projectService.queryRandomProjectList(project, pageNum, pageSize);
				if(list!=null&&list.size()>0){
					apList=BeanUtil.copyPage((Page<Project>)list,ApiProject.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return apList;
		}
		
		@Override
		 public List<ApiProject> queryProjectAndUserInfo(ApiProject param) {
			 Project project = BeanUtil.copy(param, Project.class);
			 List<Project> list = projectService.queryProjectAndUserInfo(project);
			 List<ApiProject> alist = new ArrayList<ApiProject>();
			 ApiProject ap = null;
			 for (Project p : list)
			 {
				 if(p!=null){
					 ap= BeanUtil.copy(p, ApiProject.class);
					 alist.add(ap);
				 }
			 }
			 return alist;
		 }


    @Override
    public List<ApiCommonFormUser> queryCommonFormUserByParam(ApiCommonFormUser param) {
        return null;
    }


    @Override
	    public ApiResult saveTogetherConfig(ApiTogetherConfig apiTogetherConfig)
	    {
	        logger.info("receive save ApiTogetherConfig param : " + apiTogetherConfig);
	        TogetherConfig togetherConfig = BeanUtil.copy(apiTogetherConfig, TogetherConfig.class);
	        if (!valTogetherConfigParam(togetherConfig))
	        {
	            return ApiResultUtil.getParameterError();
	        }
	        try
	        {
	            togetherConfigService.save(togetherConfig);
	            ApiResult apiResult = ApiResultUtil.SUCCESS;
	            //apiResult.setMessage(togetherConfig.getId() + "");
	            return apiResult;
	            
	        }
	        catch (BaseException e)
	        {
	            return ApiResultUtil.getApiResult(e.getResultCode());
	        }
	    }
		 
		@Override
	    public ApiResult updateTogetherConfig(ApiTogetherConfig apiTogetherConfig)
	    {
	        logger.info("receive update ApiTogetherConfig param : " + apiTogetherConfig);
	        TogetherConfig togetherConfig = BeanUtil.copy(apiTogetherConfig, TogetherConfig.class);
	        if (togetherConfig == null)
	        {
	            logger.info(" togetherConfig is null");
	            return ApiResultUtil.getParameterError();
	        }
	        else if (togetherConfig.getId() == null)
	        {
	            logger.info(" id is null");
	            return ApiResultUtil.getParameterError();
	        }
	        
	        try
	        {
	            togetherConfigService.update(togetherConfig);
	            return ApiResultUtil.SUCCESS;
	        }
	        catch (BaseException e)
	        {
	            return ApiResultUtil.getApiResult(e.getResultCode());
	        }
	    }
		 
		@Override
		public ApiPage<ApiTogetherConfig> queryTogetherConfigList(ApiTogetherConfig param,
				int pageNum, int pageSize) {
		 TogetherConfig togetherConfig=BeanUtil.copy(param, TogetherConfig.class);
			if(togetherConfig!=null){
				new ApiPage<>(pageNum,pageSize);
			}
			ApiPage<ApiTogetherConfig> apList=null;
			try {
				List<TogetherConfig> list=togetherConfigService.queryByParam(togetherConfig, pageNum, pageSize);
				if(list!=null&&list.size()>0){
					apList=BeanUtil.copyPage((Page<TogetherConfig>)list,ApiTogetherConfig.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return apList;
		}
		
		@Override
		public ApiPage<ApiTogetherConfig> queryTogetherConfigByProjectId(
				Integer projectId, int pageNum, int pageSize) {
			new ApiPage<>(pageNum,pageSize);
			ApiPage<ApiTogetherConfig> apList=null;
			try {
				List<TogetherConfig> list=togetherConfigService.queryTogetherConfigByProjectId(projectId, pageNum, pageSize);
				if(list!=null&&list.size()>0){
					apList=BeanUtil.copyPage((Page<TogetherConfig>)list,ApiTogetherConfig.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return apList;
		}
		
		@Override
	    public ApiTogetherConfig queryByTogetherConfig(ApiTogetherConfig apiTogetherConfig)
	    {
	        logger.info("receive queryByTogetherConfig param : " + apiTogetherConfig);
	        if (apiTogetherConfig == null)
	        {
	            return null;
	        }
	        TogetherConfig togetherConfig = BeanUtil.copy(apiTogetherConfig, TogetherConfig.class);
	        
	        togetherConfig = togetherConfigService.selectByParam(togetherConfig);
	        
	        ApiTogetherConfig ret = BeanUtil.copy(togetherConfig, ApiTogetherConfig.class);
	        return ret;
	    }
		

		public boolean valTogetherConfigParam(TogetherConfig togetherConfig)
	    {
	        boolean ret = true;
	        if (togetherConfig == null)
	        {
	            logger.info("togetherConfig is null");
	            ret = false;
	        }
	        else if (togetherConfig.getUserId() == null)
	        {
	            logger.info("userId is null");
	            ret = false;
	        }
	        else if (togetherConfig.getProjectId() == null)
	        {
	            logger.info("projectId is  null");
	            ret = false;
	        }
	        else if (togetherConfig.getType() == null)
	        {
	            logger.info("type is  null");
	            ret = false;
	        }
	        else if (togetherConfig.getLaunchName() == null)
	        {
	            logger.info("launchName is  null");
	            ret = false;
	        }
	        else if (togetherConfig.getTotalMoney() == null)
	        {
	            logger.info("totalMoney is  null");
	            ret = false;
	        }
	        else if (togetherConfig.getContent() == null)
	        {
	            logger.info("content is  null");
	            ret = false;
	        }
	        
	        return ret;
	    }
		
		@Override
		public ApiPage<ApiProjectFeedback> queryFeedbackAndReportList(int pageNum, int pageSize) {
			new ApiPage<>(pageNum,pageSize);
			ApiPage<ApiProjectFeedback> apList=null;
			try {
				List<ProjectFeedback> list=projectFeedbackService.queryFeedbackAndReportList(pageNum, pageSize);
				if(list!=null&&list.size()>0){
					apList=BeanUtil.copyPage((Page<ProjectFeedback>)list,ApiProjectFeedback.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return apList;
		}

		@Override
		public boolean queryIsOrNotNewLeaveWordByUserId(Integer userId) {
			return newLeaveWordService.queryIsOrNotNewLeaveWordByUserId(userId);
		}
		
		@Override
		public ApiPage<ApiTogetherConfig> queryDetailByProjectId(
				Integer projectId, int pageNum, int pageSize) {
			ApiPage<ApiTogetherConfig> apList=null;
			try {
				List<TogetherConfig> list=togetherConfigService.queryDetailByProjectId(projectId, pageNum, pageSize);
				if(list!=null&&list.size()>0){
					apList=BeanUtil.copyPage((Page<TogetherConfig>)list,ApiTogetherConfig.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return apList;
		}

		@Override
		public ApiPage<ApiTogetherConfig> queryTogetherDonateListByProjectId(
				Integer projectId, int pageNum, int pageSize) {
			ApiPage<ApiTogetherConfig> apList=null;
			try {
				List<TogetherConfig> list=togetherConfigService.queryTogetherDonateListByProjectId(projectId, pageNum, pageSize);
				if(list!=null&&list.size()>0){
					apList=BeanUtil.copyPage((Page<TogetherConfig>)list,ApiTogetherConfig.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return apList;
		}

		//TODO
		@Override
		public List<ApiTogetherConfig> oneApiTogetherConfigTotal(ApiTogetherConfig apiTogetherConfig) {
			
			 if (apiTogetherConfig == null)
		        {
		            return null;
		        }
		        TogetherConfig togetherConfig=BeanUtil.copy(apiTogetherConfig, TogetherConfig.class);
		        
		        List<TogetherConfig> list = togetherConfigService.oneApiTogetherConfigTotal(togetherConfig);
		        
		        List<ApiTogetherConfig> ret=BeanUtil.copyList(list, ApiTogetherConfig.class);
		        
		        return ret;
		}
		


    @Override
    public ApiPage<ApiProject> queryProjectBy30(int pageNum,int pageSize) {
        ApiPage<ApiProject> apList=null;
        try {
            List<Project> list=projectService.queryProjectBy30(pageNum, pageSize);
            if(list!=null&&list.size()>0){
                apList=BeanUtil.copyPage((Page<Project>)list,ApiProject.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apList;
    }

    @Override
    public ApiTogetherConfig countTogetherDonateMoneyAndDonateNumberByProjectId(Integer projectId)
    {
        ApiTogetherConfig togetherConfig = null;
        try
        {
            TogetherConfig newTogether = this.togetherConfigService.countTogetherDonateMoneyAndDonateNumberByProjectId(projectId);
            togetherConfig = (ApiTogetherConfig)BeanUtil.copy(newTogether, ApiTogetherConfig.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return togetherConfig;
    }



	@Override
	public ApiPage<ApiProjectFeedback> queryH5ProjectFeedbckByUserList(
			Integer userId, int pageNum, int pageSize) {
        
		ApiPage<ApiProjectFeedback> ret = null;
        try {
        	List<ProjectFeedback> list = projectService.queryByUserIdSelectList(userId, pageNum, pageSize);
            ret = BeanUtil.copyPage((Page<ProjectFeedback>)list, ApiProjectFeedback.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return ret;
	}

	@Override
	public ApiPage<ApiProject> queryByIndexProjectList(ApiProject apiProject,
			int pageNum, int pageSize) {
		logger.info("receive query param:" + apiProject);
        
        Project p = BeanUtil.copy(apiProject, Project.class);
        
        if (p == null)
        {
            new ApiPage<ApiProject>(pageNum, pageSize);
        }
        
        List<Project> list = projectService.queryByIndexProjectList(p, pageNum, pageSize);
        
        ApiPage<ApiProject> ret = BeanUtil.copyPage((Page<Project>)list, ApiProject.class);
        
        return ret;
	}

	@Override
	public ApiPage<ApiProject> queryByNewProjectList(ApiProject apiProject,
			int pageNum, int pageSize) {
        logger.info("receive query param:" + apiProject);
        
        Project p = BeanUtil.copy(apiProject, Project.class);
        
        if (p == null)
        {
            new ApiPage<ApiProject>(pageNum, pageSize);
        }
        
        List<Project> list = projectService.queryByNewProjectList(p, pageNum, pageSize);
        
        ApiPage<ApiProject> ret = BeanUtil.copyPage((Page<Project>)list, ApiProject.class);
        
        return ret;
	}


    @Override
	public ApiPage<ApiProject> queryByFieldAndArea(
			String field, int pageNum, int pageSize) {
		ApiPage<ApiProject> apList=null;
		try {
			List<Project> list = projectService.queryByFieldAndArea(field,pageNum,pageSize);
			apList = BeanUtil.copyPage((Page<Project>)list, ApiProject.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apList;
	}

	@Override
	public ApiResult updateProjectShareCount(Integer projectId) {
		if(projectId == null){
			logger.info(" projectId is null");
            return ApiResultUtil.getParameterError();
		}
		try {
			projectService.updateProjectShareCount(projectId);
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}

	@Override
	public ApiPage<ProjectDTO> queryProjectMaybeList(int pageNum, int pageSize) {
		logger.info("receive queryProjectMaybeList:");
        
        
        List<Project> list = projectService.queryProjectMaybeList(pageNum, pageSize);
        
        ApiPage<ProjectDTO> ret = BeanUtil.copyPage((Page<Project>)list, ProjectDTO.class);
        
        return ret;
	}
}
