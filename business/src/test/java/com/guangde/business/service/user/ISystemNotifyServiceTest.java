package com.guangde.business.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.guangde.business.BaseTest;
import com.guangde.business.entry.SystemNotify;
import com.guangde.business.pojo.Result;

public class ISystemNotifyServiceTest extends BaseTest
{
    @Resource
    private ISystemNotifyService systemNotifyService;
    
    @Test
    public void testQueryByUserId()
    {
        List<SystemNotify> list = systemNotifyService.queryByUserId(1);
        Assert.isTrue(list.get(0).getUserId() == 1);
    }
    
    @Test
    public void queryTest()
    {
        SystemNotify systemNotify = new SystemNotify();
        systemNotify.setId(1);
        List<SystemNotify> page = systemNotifyService.queryByCondition(systemNotify, 1, 10);
        
        Assert.isTrue(page.get(0).getId() == 1);
        
        systemNotify.setId(null);
        systemNotify.setUserId(null);
        page = systemNotifyService.queryByCondition(systemNotify, 1, 10);
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testDelete()
    {
        Result ret = systemNotifyService.delete(8, 7);
        System.out.println(ret.getCode());
    }

    @Test
    public void queryDetailTest()
    {
        SystemNotify s = systemNotifyService.queryByIdAndUserId(11, 11);
        if (s != null)
        {
            Assert.isTrue(s.getId() == 11);
        }
    }
}


