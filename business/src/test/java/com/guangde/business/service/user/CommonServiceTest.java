package com.guangde.business.service.user;

import javax.annotation.Resource;

import org.junit.Test;

import com.guangde.business.BaseTest;
import com.guangde.business.service.commons.impl.AnnounceService;

public class CommonServiceTest extends BaseTest
{
    @Resource
    AnnounceService announceService;
    
    @Test
    public void testSM()
    {
        //        Announce a = new Announce();
        //        a.setDestination("15557131230");
        //        a.setContent("欢迎使用手机验证码: 888888 ,请在10分钟内完成验证。如非本人操作，请致电客服");
        //        a.setPriority(1);
        //        a.setCause("手机验证码");
        //        a.setType(1);
        //        try
        //        {
        //            announceService.saveAnnounce(a, true);
        //        }
        //        catch (BaseException e)
        //        {
        //            e.printStackTrace();
        //            System.out.println(e.getMessage());
        //        }
    }
}
