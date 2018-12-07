package com.guangde.business.service.user.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.FrontUserMapper;
import com.guangde.business.dao.LeaveWordMapper;
import com.guangde.business.entry.FrontUser;
import com.guangde.business.entry.LeaveWord;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.user.ILeaveWordService;
import com.guangde.business.util.ConfigCache;
import com.guangde.business.util.ResultCode;

@Service("leaveWordService")
public class LeaveWordServiceImpl extends BaseService implements ILeaveWordService
{
    @Autowired
    private LeaveWordMapper leaveWordMapper;
    
    @Autowired
    private FrontUserMapper frontUserMapper;
    
    @Override
    public List<LeaveWord> queryLeaveWordByParam(LeaveWord leaveWord, Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);
        
        List<LeaveWord> list = leaveWordMapper.queryByParam(leaveWord);
        
        return list;
    }
    
    @Override
    public void saveLeaveWord(LeaveWord leaveWord)
        throws BaseException
    {
        if (leaveWord.getUserId() == null)
        {
            FrontUser f = frontUserMapper.getUserByUserName("游客");
            if (f == null)
            {
                throw new BaseException(ResultCode.ParameterError);
            }
            leaveWord.setUserId(f.getId());
        }
        try
        {
            String lw = ConfigCache.getSingle().getConfigValue("ILLEGAL_KEY_WORD") == null ? "" : ConfigCache.getSingle().getConfigValue("ILLEGAL_KEY_WORD");
            String[] words = lw.split(";");
            if (words != null && words.length > 0)
            {
                if (!StringUtils.isEmpty(leaveWord.getContent()))
                {
                    
                    for (String s : words)
                    {
                        if (leaveWord.getContent().contains(s.trim()))
                        {
                            throw new BaseException(ResultCode.illegalError);
                        }
                    }
                }
                
            }
            leaveWordMapper.save(leaveWord);
        }
        catch (BaseException e)
        {
            logger.error(e);
            throw new BaseException(ResultCode.illegalError);
        }
        catch (Exception e)
        {
            logger.info(e);
            
            throw new BaseException(ResultCode.Error);
        }
    }
}
