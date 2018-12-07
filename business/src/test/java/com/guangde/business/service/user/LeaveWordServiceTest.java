package com.guangde.business.service.user;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.guangde.business.BaseTest;
import com.guangde.business.entry.LeaveWord;
import com.guangde.business.exception.BaseException;

public class LeaveWordServiceTest extends BaseTest
{
    @Autowired
    private ILeaveWordService leaveWordService;
    
    @Test
    public void testSave()
    {
        LeaveWord leaveWord = new LeaveWord();
        leaveWord.setProjectFeedback_id(2);
        leaveWord.setContent("test");
        
        try
        {
            leaveWordService.saveLeaveWord(leaveWord);
        }
        catch (BaseException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    // @Test
    public void testQuery()
    {
        LeaveWord leaveWord = new LeaveWord();
        leaveWord.setProjectFeedback_id(2);
        
        List<LeaveWord> list = leaveWordService.queryLeaveWordByParam(leaveWord, 1, 20);
        if (list != null && list.size() > 0)
        {
            for (LeaveWord l : list)
            {
                System.out.println(l.getContent());
            }
        }
    }
}
