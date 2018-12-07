package com.guangde.business.service.user;

import java.util.List;

import com.guangde.business.entry.LeaveWord;
import com.guangde.business.exception.BaseException;

public interface ILeaveWordService
{
    void saveLeaveWord(LeaveWord leaveWord)
        throws BaseException;
    
    List<LeaveWord> queryLeaveWordByParam(LeaveWord leaveWord, Integer pageNum, Integer pageSize);
}
