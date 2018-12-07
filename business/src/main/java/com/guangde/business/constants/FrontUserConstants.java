package com.guangde.business.constants;

public class FrontUserConstants
{
    /**
     * 未填写实名验证信息
     */
    public static final int REALSTATE_SAVE = 200;
    
    /**
     * 实名验证未审核
     */
    public static final int REALSTATE_WSH = 201;
    
    /**
     * 实名验证审核未通过
     */
    public static final int REALSTATE_WTG = 202;
    
    /**
     * 实名验证审核通过
     */
    public static final int REALSTATE_TG = 203;
    
    /**
     * 手机号验证未通过
     */
    public static final int MOBILESTATE_WTG = 202;
    
    /**
     * 手机号验证通过
     */
    public static final int MOBILESTATE_TG = 203;
    
    /**
     * 善管家提交未审核
     */
    public static final int LOVESTATE_WSH = 201;
    
    /**
     * 善管家审核未通过
     */
    public static final int LOVESTATE_WTG = 202;
    
    /**
     * 善管家审核通过
     */
    public static final int LOVESTATE_TG = 203;
    
    /**
     * 实名审核
     */
    public static final String PERSONTYPE_REAL = "helpPeople";
    
    /**
     * 爱心团审核
     */
    public static final String PERSONTYPE_LOVE = "loveGroup";
    
    /**
     * 默认用户头像
     */
    public static final String DEFAULT_HEADIMG = "https://www.17xs.org/res/images/detail/people_avatar.jpg";
    
    /**
     * 匿名用户昵称
     */
    public static final String DEFAULT_DONATED_USER_NAME = "爱心人士";


    /**
     * 前端用户类型
     * 普通用户
     */
    public static final String FRONTUSER_TYPE_INDIVIDUAL = "individualUsers";
    /**
     * 前端用户类型
     * 游客
     */
    public static final String FRONTUSER_TYPE_TOURIST = "touristUsers";
    
}
