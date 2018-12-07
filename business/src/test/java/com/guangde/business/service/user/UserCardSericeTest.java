package com.guangde.business.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.guangde.business.BaseTest;
import com.guangde.business.entry.UserCard;
import com.guangde.business.exception.BaseException;

public class UserCardSericeTest extends BaseTest
{
    @Resource
    private IUserCardService userCardService;
    
    @Resource
    private ISystemNotifyService systemNotifyService;
    
    // @Test
    public void testSave()
    {
        UserCard userCard = new UserCard();
        userCard.setUserId(67);
        userCard.setBankName("工商");
        userCard.setCard("62213431341343434449");
        userCard.setProvince("浙江");
        userCard.setCity("杭州");
        userCard.setUseState(100);
        userCard.setAccountName("lin");
        userCard.setAccountType(1);
        
        try
        {
            userCardService.saveUserCard(userCard);
        }
        catch (BaseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        
    }
    
    @Test
    public void testQuery()
    {
        UserCard userCard = new UserCard();
        userCard.setUserId(352);
        List<UserCard> list = userCardService.queryUserCardByParam(userCard, 1, 20);
        System.out.println(list.get(0).getBankName());
    }
    
    // @Test
    public void deleteTest()
    {
        try
        {
            UserCard userCard = new UserCard();
            userCard.setId(10);
            userCard.setUseState(102);
            userCardService.updateUserCard(userCard);
        }
        catch (BaseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }
    }
    
    //@Test
    public void resetUserCardSelectedTest()
    {
        UserCard userCard = new UserCard();
        userCard.setId(11);
        userCard.setUserId(315);
        userCard.setIsSelected(0);
        try
        {
            userCardService.resetUserCardSelectd(userCard);
        }
        catch (BaseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    // @Test
    public void testNotify()
    {
        int c = systemNotifyService.countRead(90, 1);
        System.out.println(c);
    }
    
    public static void main(String[] args)
    {
        String name = "呵呵";
        String mobile = "sss";
        String str = "{\"name\":\"" + name + "\",\"mobile\":\"" + mobile + "\"}";
        JSONObject dataJson = (JSONObject)JSONObject.parse(str);
        String name2 = dataJson.getString("name");
        System.out.println(name2);
    }
}
