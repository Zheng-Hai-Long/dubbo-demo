package com.guangde.business.service.user.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.SystemNotifyMapper;
import com.guangde.business.entry.SystemNotify;
import com.guangde.business.entry.SystemTypeModel;
import com.guangde.business.pojo.Result;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.user.ISystemNotifyService;
import com.guangde.business.util.ResultCode;

@Service("systemNotifyService")
public class SystemNotifyServiceImpl extends BaseService implements ISystemNotifyService
{
    @Autowired
    private SystemNotifyMapper systemNotifyMapper;
    
    @Override
    public List<SystemNotify> queryByUserId(Integer userId)
    {
        return systemNotifyMapper.queryByUserId(userId);
    }
    
    @Override
    public Result save(SystemNotify systemNotify)
    {
        Result result = new Result();
        if (null == systemNotify)
        {
            result.setResultCode(ResultCode.ParameterError);
            return result;
        }
        
        systemNotifyMapper.save(systemNotify);
        
        result.setResultCode(ResultCode.Success);
        return result;
    }
    
    @Override
    public Result delete(Integer id, Integer userId)
    {
        Result result = new Result();
        if (null == id || null == userId)
        {
            result.setResultCode(ResultCode.ParameterError);
        }
        systemNotifyMapper.delete(id, userId);
        
        result.setResultCode(ResultCode.Success);
        return result;
    }
    
    @Override
    public List<SystemNotify> queryByQueryTime(Date startTime, Date endTime, Integer userId, Integer state)
    {
        if (null == userId)
        {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (null != startTime)
        {
            map.put("startTime", startTime);
        }
        
        if (null != endTime)
        {
            map.put("endTime", endTime);
        }
        
        map.put("userId", userId);
        if (null != state)
        {
            map.put("state", state);
        }
        
        return systemNotifyMapper.queryByQueryTime(map);
    }
    
    @Override
    public Result setToRead(Integer id, Integer userId)
    {
        Result result = new Result();
        if (null == id)
        {
            result.setResultCode(ResultCode.ParameterError);
            return result;
        }
        
        systemNotifyMapper.setToRead(id, userId);
        result.setResultCode(ResultCode.Success);
        return result;
    }
    
    @Override
    public List<SystemNotify> queryByCondition(SystemNotify systemNotify, int pageNum, int pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);
        
        List<SystemNotify> list = systemNotifyMapper.queryByCondition(systemNotify);
        
        return list;
    }
    
    @Override
    public SystemNotify queryNextRecord(Integer id, Integer userId)
    {
        return systemNotifyMapper.queryNextRecord(id, userId);
    }
    
    @Override
    public SystemNotify queryPreviousRecord(Integer id, Integer userId)
    {
        return systemNotifyMapper.queryPreviousRecord(id, userId);
    }
    
    @Override
    public SystemNotify queryByIdAndUserId(Integer id, Integer userId)
    {
        return systemNotifyMapper.queryByIdAndUserId(id, userId);
    }
    
    @Override
    public Integer countRead(Integer userId, Integer state)
    {
        return systemNotifyMapper.countNoReadByUserIdAndState(userId, state);
    }
    
    @Override
    public Integer countRead(SystemNotify sysNotify)
    {
        return systemNotifyMapper.countNoReadBySystemNotify(sysNotify);
    }

	@Override
	public List<SystemTypeModel> queryByIds(SystemTypeModel systemTypeModel) {
		return systemNotifyMapper.queryByIds(systemTypeModel);
	}
    
}
