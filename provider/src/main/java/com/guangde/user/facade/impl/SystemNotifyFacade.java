package com.guangde.user.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.guangde.api.user.ISystemNotifyFacade;
import com.guangde.business.entry.SystemNotify;
import com.guangde.business.entry.SystemTypeModel;
import com.guangde.business.pojo.Result;
import com.guangde.business.service.user.ISystemNotifyService;
import com.guangde.entry.ApiSystemNotify;
import com.guangde.entry.ApiSystemTypeModel;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.guangde.util.BeanUtil;

@Service("SystemNotifyFacade")
public class SystemNotifyFacade implements ISystemNotifyFacade
{
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ISystemNotifyService systemNotifyService;
    
    @Override
    public List<ApiSystemNotify> queryByUserId(Integer userId)
    {
        
        if (null == userId)
        {
            return null;
        }
        
        List<SystemNotify> list = systemNotifyService.queryByUserId(userId);
        if (null != list && !list.isEmpty())
        {
            List<ApiSystemNotify> systemNotifys = new ArrayList<ApiSystemNotify>();
            for (SystemNotify sn : list)
            {
                ApiSystemNotify apisn = BeanUtil.copy(sn, ApiSystemNotify.class);
                if (null != apisn)
                {
                    systemNotifys.add(apisn);
                }
            }
            
            return systemNotifys;
        }
        
        return null;
    }
    
    @Override
    public ApiResult save(ApiSystemNotify apiSystemNotify)
    {
        if (null == apiSystemNotify)
        {
            return null;
        }
        
        SystemNotify systemNotify = BeanUtil.copy(apiSystemNotify, SystemNotify.class);
        if (null == systemNotify)
        {
            return null;
        }
        
        Result result = systemNotifyService.save(systemNotify);
        
        if (null == result)
        {
            return null;
        }
        
        ApiResult ar = BeanUtil.copy(result, ApiResult.class);
        
        return ar;
    }
    
    @Override
    public ApiResult delete(Integer id, Integer userId)
    {
        if (null == id || null == userId)
        {
            return null;
        }
        
        Result result = systemNotifyService.delete(id, userId);
        
        if (null == result)
        {
            return null;
        }
        
        ApiResult ar = BeanUtil.copy(result, ApiResult.class);
        
        return ar;
    }
    
    @Override
    public ApiResult setToRead(Integer id, Integer userId)
    {
        if (null == id || null == userId)
        {
            return null;
        }
        
        Result result = systemNotifyService.setToRead(id, userId);
        
        if (null == result)
        {
            return null;
        }
        
        ApiResult ar = BeanUtil.copy(result, ApiResult.class);
        
        return ar;
    }
    
    //	@Override
    //	public List<ApiSystemNotify> queryByQueryTime(Date startTime, Date endTime,
    //			Integer userId, Integer state) {
    //
    //		List<SystemNotify> list = systemNotifyService.queryByQueryTime(
    //				startTime, endTime, userId, state);
    //		if (null != list && !list.isEmpty()) {
    //			List<ApiSystemNotify> apisns = new ArrayList<ApiSystemNotify>();
    //			for (SystemNotify systemNotify : list) {
    //				ApiSystemNotify apiSystemNotify = BeanUtil.copy(systemNotify,
    //						ApiSystemNotify.class);
    //				if (null != apiSystemNotify) {
    //					apisns.add(apiSystemNotify);
    //				}
    //			}
    //
    //			return apisns;
    //		}
    //
    //		return null;
    //	}
    
    @Override
    public ApiPage<ApiSystemNotify> queryByCondition(ApiSystemNotify apiSystemNotify, int pageNum, int pageSize)
    {
        SystemNotify systemNotify = BeanUtil.copy(apiSystemNotify, SystemNotify.class);
        
        if (systemNotify == null)
        {
            return new ApiPage<ApiSystemNotify>(pageNum, pageSize);
        }
        
        List<SystemNotify> list = systemNotifyService.queryByCondition(systemNotify, pageNum, pageSize);
        
        ApiPage<ApiSystemNotify> apiPage = BeanUtil.copyPage((Page<SystemNotify>)list, ApiSystemNotify.class);
        
        return apiPage;
    }
    
    @Override
    public ApiSystemNotify queryNextRecord(Integer id, Integer userId)
    {
        SystemNotify obj = systemNotifyService.queryNextRecord(id, userId);
        
        return BeanUtil.copy(obj, ApiSystemNotify.class);
    }
    
    @Override
    public ApiSystemNotify queryPreviousRecord(Integer id, Integer userId)
    {
        SystemNotify obj = systemNotifyService.queryPreviousRecord(id, userId);
        
        return BeanUtil.copy(obj, ApiSystemNotify.class);
    }
    
    @Override
    public ApiSystemNotify queryDetail(Integer id, Integer userId)
    {
        SystemNotify obj = systemNotifyService.queryByIdAndUserId(id, userId);
        
        return BeanUtil.copy(obj, ApiSystemNotify.class);
    }
    
    @Override
    public Integer countRead(Integer userId, Integer state)
    {
        logger.info("receive countRead param : userId = " + userId + " state = " + state);
        
        return systemNotifyService.countRead(userId, state);
    }
    
    @Override
    public Integer countRead(ApiSystemNotify systemNotify)
    {
    	SystemNotify sysNotify = BeanUtil.copy(systemNotify, SystemNotify.class);
        if (null == sysNotify)
        {
            return null;
        }
        return systemNotifyService.countRead(sysNotify);
    }

	@Override
	public List<ApiSystemTypeModel> queryByIds(ApiSystemTypeModel apiSystemTypeModel) {
		
		if (apiSystemTypeModel.getIds() == null || apiSystemTypeModel.getIds().size() == 0)
        {
            return null;
        }
		SystemTypeModel systemTypeModel2 = BeanUtil.copy(apiSystemTypeModel, SystemTypeModel.class);
        List<SystemTypeModel> list = systemNotifyService.queryByIds(systemTypeModel2);
        if (null != list && !list.isEmpty())
        {
            List<ApiSystemTypeModel> s = new ArrayList<ApiSystemTypeModel>();
            for (SystemTypeModel sn : list)
            {
                ApiSystemTypeModel apisn = BeanUtil.copy(sn, ApiSystemTypeModel.class);
                if (null != apisn)
                {
                    s.add(apisn);
                }
            }
            
            return s;
        }
        
        return null;
	}
}
