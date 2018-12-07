package com.guangde.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.BFileMapper;
import com.guangde.business.dao.ProjectFeedbackMapper;
import com.guangde.business.dao.ProjectMapper;
import com.guangde.business.entry.BFile;
import com.guangde.business.entry.Project;
import com.guangde.business.entry.ProjectFeedback;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.ProjectFeedbackService;
import com.guangde.business.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service("projectFeedbackService")
public class ProjectFeedbackServiceImpl extends BaseService implements ProjectFeedbackService
{
    @Autowired
    private ProjectFeedbackMapper projectFeedbackMapper;
    
    @Autowired
    private BFileMapper bFileMapper;
    
    @Autowired
    private ProjectMapper projectMapper;
    
    @Override
    public List<ProjectFeedback> queryProjectFeedbackList(ProjectFeedback projectFeedback, Integer pageNum, Integer pageSize)
    {
        
        logger.info("query param:" + projectFeedback);
        
        PageHelper.startPage(pageNum, pageSize);
        
        List<ProjectFeedback> list = projectFeedbackMapper.queryByParam(projectFeedback);
        String ids = "";
        String resUrl = ConfigLoader.getResPictrueURL();
        for (ProjectFeedback p : list)
        {
            ids = p.getContentImageId();
            if (StrUtil.isNotEmpty(ids))
            {
                List<Integer> idList = StrUtil.convertIdList(ids, Constant.data_separator);
                
                List<BFile> blist = bFileMapper.queryByIdList(idList);
                
                if (!CollectionUtils.isEmpty(list))
                {
                    
                    List<String> imageList = new ArrayList<String>();
                    
                    for (BFile bFile : blist)
                    {
                        imageList.add(resUrl + bFile.getUrl());
                    }
                    
                    p.setContentImageUrl(imageList);
                }
            }
            p.setHeadImageUrl(resUrl+p.getHeadImageUrl());
        }
        return list;
    }
    
    @Override
    public void saveProjectFeedback(ProjectFeedback projectFeedback)
        throws BaseException
    {
        try
        {
            String leaveWord = ConfigCache.getSingle().getConfigValue("ILLEGAL_KEY_WORD") == null ? "" : ConfigCache.getSingle().getConfigValue("ILLEGAL_KEY_WORD");
            String[] words = leaveWord.split(";");
            if (words != null && words.length > 0)
            {
                if (!StringUtils.isEmpty(projectFeedback.getContent()))
                {
                    
                    for (String s : words)
                    {
                        if (projectFeedback.getContent().contains(s.trim()))
                        {
                            throw new BaseException(ResultCode.illegalError);
                        }
                    }
                }
                
            }
            
            projectFeedback.setAuditState(State.pass);
            projectFeedback.setSource("home");
            projectFeedbackMapper.save(projectFeedback);
            
            //项目反馈时间更新
            Project p = new Project();
            p.setId(projectFeedback.getProjectId());
            p.setLastFeedbackTime(DateUtil.getDate());
            projectMapper.update(p);
        }
        catch (BaseException e)
        {
            logger.error(e);
            throw new BaseException(ResultCode.illegalError);
        }
        catch (Exception e)
        {
            logger.error(e);
            logger.error("save database error", e);
            
            throw new BaseException(ResultCode.Error);
        }
    }
    
    @Override
    public void updateProjectFeedback(ProjectFeedback projectFeedback)
        throws BaseException
    {
        try
        {
            projectFeedbackMapper.update(projectFeedback);
        }
        catch (Exception e)
        {
            logger.error("update database error", e);
            throw new BaseException(ResultCode.Error);
        }
    }
    
    @Override
    public Integer countProjectFeedbackByParam(ProjectFeedback projectFeedback)
    {
        
        return projectFeedbackMapper.queryCountByParam(projectFeedback);
    }
    
    @Override
    public List<ProjectFeedback> queryFeedbackAndReportList(Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);
        List<ProjectFeedback> list = projectFeedbackMapper.queryFeedbackAndReportList();
        String ids = "";
        String resUrl = ConfigLoader.getResPictrueURL();
        if(list != null && list.size() > 0){
        for (ProjectFeedback p : list)
        {
            ids = p.getContentImageId();
            if (StrUtil.isNotEmpty(ids))
            {
                List<Integer> idList = StrUtil.convertIdList(ids, Constant.data_separator);
                List<BFile> blist = bFileMapper.queryByIdList(idList);
                if (!CollectionUtils.isEmpty(list))
                {
                    List<String> imageList = new ArrayList<String>();
                    for (BFile bFile : blist)
                    {
                        imageList.add(resUrl + bFile.getUrl());
                    }
                    p.setContentImageUrl(imageList);
                }
            }
            if(p.getHeadImageUrl()!=null && !p.getHeadImageUrl().contains("http://")){
            	p.setHeadImageUrl(resUrl+p.getHeadImageUrl());
            }else if(p.getHeadImageUrl()==null){
            	p.setHeadImageUrl("https://www.17xs.org/res/images/user/user-icon-gt.png");
            }
        }
        }
        return list;
    }
    
}
