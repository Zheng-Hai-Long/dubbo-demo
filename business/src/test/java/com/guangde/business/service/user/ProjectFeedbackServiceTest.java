package com.guangde.business.service.user;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.guangde.business.BaseTest;

import com.guangde.business.entry.ProjectFeedback;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.ProjectFeedbackService;
import com.guangde.business.service.ProjectService;

public class ProjectFeedbackServiceTest extends BaseTest

{
    @Resource
    ProjectFeedbackService projectFeedbackService;
    
    @Resource
    ProjectService projectService;
    //  @Test
    public void save()
    {
        
        ProjectFeedback project = new ProjectFeedback();
        project.setProjectId(1);
        project.setFeedbackPeople(2);
        project.setContent("test123");
        project.setFeedbackTime(new Date());
        try
        {
            projectFeedbackService.saveProjectFeedback(project);
        }
        catch (BaseException e)
        {
            System.out.println(e.getResultCode().getCode() + "   " + e.getResultCode().getDescription());
        }
        
    }
    
    //@Test
    public void updateTest()
    {
        ProjectFeedback project = new ProjectFeedback();
        
        project.setProjectId(1);
        project.setFeedbackTime(new Date());
        try
        {
            projectFeedbackService.updateProjectFeedback(project);
        }
        catch (BaseException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
   // @Test
    public void queryListTest()
    {
        ProjectFeedback project = new ProjectFeedback();
        project.setProjectId(9000);
        List<ProjectFeedback> list = projectFeedbackService.queryProjectFeedbackList(project, 1, 20);
        System.out.println(list.size());
        for (ProjectFeedback p : list)
        {
            if (null != p.getContentImageUrl())
            {
                
                System.out.println(p.getId() + "  url = " + p.getContentImageUrl().toString());
            }
        }
    }
    
     //@Test
    public void testCount()
    {
        ProjectFeedback projectFeedback = new ProjectFeedback();
        projectFeedback.setProjectId(309);
        Integer count = projectFeedbackService.countProjectFeedbackByParam(projectFeedback);
        System.out.println("count = " + count);
    }
    
   
    
}
