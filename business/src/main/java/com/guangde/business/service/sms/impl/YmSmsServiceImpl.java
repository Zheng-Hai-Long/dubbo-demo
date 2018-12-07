package com.guangde.business.service.sms.impl;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import cn.emay.sdk.client.api.Client;

import com.guangde.business.entry.Announce;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.sms.SmsSenderService;

@Service("ymSmsSenderService")
public class YmSmsServiceImpl extends BaseService implements SmsSenderService
{
    public static String softwareSerialNo_Normal = "6SDK-EMY-6688-KDXPO";//软件序列号,请通过销售人员获取
    
    public static String key_Normal = "892043";//序列号首次激活时自己设定 
    
    public static String password_Normal = "892043";// 密码,请通过销售人员获取
    
    private static Client client_Normal = null;
    
    public static String SIGNATURE = "【善园基金会】";
    
    //    private static final int MOBILE_TYPE_MOBILE = 1;
    
    //    private static final int MOBILE_TYPE_UNICOM = 2;
    
    //    private static final String[] UNICOM_PREFIX = {"130", "131", "132", "155", "156", "186", "185", "133", "180", "181", "189", "153"};
    
    @PostConstruct
    public void init()
    {
        try
        {
            client_Normal = new Client(softwareSerialNo_Normal, key_Normal, null, "sdk4report.eucp.b2m.cn");
        }
        catch (Exception e)
        {
            logger.error("init client error", e);
        }
    }
    
    @Override
    public boolean send(String[] mobiles, String content, Integer priority)
    {
        boolean ret = false;
        
        try
        {
            //优先级，彩票2元网站1-10.1最高；亿美1-5，5最高
            int pro = 5;
            if (priority != null)
            {
                pro = (12 - priority) / 2;
            }
            
            String smsContent = SIGNATURE + content;
            int i = client_Normal.sendSMS(mobiles, smsContent, pro);
            
            ret = i == 0 ? true : false;
            
            logger.info("send mobiles:" + Arrays.toString(mobiles) + ",smsContent:" + smsContent + ",result:" + ret);
        }
        catch (Exception e)
        {
            logger.error("send error.", e);
        }
        return ret;
    }
    
    @Override
    public double getBalance()
    {
        double balance = -1.0;
        
        if (client_Normal != null)
        {
            try
            {
                String value = client_Normal.getBalance();
                balance = NumberUtils.toDouble(value, 0);
            }
            catch (Exception e)
            {
                logger.error("getBalance error", e);
            }
        }
        
        return balance;
    }
    
    @Override
    public int registCilent()
    {
        if (client_Normal == null)
        {
            return -1;
        }
        
        int n = client_Normal.registEx(password_Normal);
        return n;
    }
    
    @Override
    public boolean send(Announce announce)
    {
        return send(new String[] {announce.getDestination()}, announce.getContent(), announce.getPriority());
    }
    
    public static void main(String[] args)
    {
        YmSmsServiceImpl ym = new YmSmsServiceImpl();
        ym.init();
        
        StringBuilder sb = new StringBuilder();
        sb.append("【善基金】");
        sb.append("您的校验码为：17593，请在10分钟内完成验证。如非本人操作，请致电客服");
        
        boolean ret = ym.send(new String[] {"13735821816"}, sb.toString(), 5);
        
        System.out.println(ret);
    }
    
}
