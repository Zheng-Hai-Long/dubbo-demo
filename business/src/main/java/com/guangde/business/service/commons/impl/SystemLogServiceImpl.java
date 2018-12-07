package com.guangde.business.service.commons.impl;

import com.guangde.business.dao.SystemLogMapper;
import com.guangde.business.entry.SystemLog;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.commons.SystemLogService;
import com.guangde.business.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("systemLogService")
public class SystemLogServiceImpl extends BaseService implements SystemLogService
{
    @Autowired
    private SystemLogMapper systemLogMapper;

    
    @Override
    public void save(SystemLog systemLog)
        throws BaseException
    {
        try
        {
            systemLogMapper.save(systemLog);
        }
        catch (Exception e)
        {
            throw new BaseException(ResultCode.Error);
        }
    }

}
