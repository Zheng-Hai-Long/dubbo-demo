package com.guangde.business.service.user;

import com.guangde.business.entry.*;
import com.guangde.business.exception.BaseException;
import com.guangde.business.pojo.Result;

import java.util.List;

public interface IUserService
{

    /**
     * 根据手机号查询
     * @param mobileNum
     * @return
     */
    FrontUser queryByMobile(String mobileNum);

    /**
     * 根据条件查询用户列表
     * @param model
     * @return
     */
    List<FrontUser> queryByParam(FrontUser model);

    /**
     * 根据unionId查询第三方用户列表
     * @param openId
     * @return
     */
    List<ThirdUser> queryThirdUserByParam(String openId) throws BaseException;

    /**
     * 第三方用户注册
     * @param fUser 用户信息
     * @return 注册结果
     */
    Result thirdUserRegistered(FrontUser fUser,ThirdUser tUser) throws BaseException;


    /**
     * 登陆
     * @param fUser  登陆用户
     * @return 登陆结果
     */
    public Result login(FrontUser fUser, ThirdUser tUser);
    
    /**
     * 前端用户注册 
     * @param fUser 用户信息
     * @return 注册结果
     */
     int register(FrontUser fUser) throws BaseException;
    

    /**
     * 前端用户口令注册
     * @param fUser 用户信息
     * @return  注册结果
     */
    public Result registeredByGoodPassWord(FrontUser fUser);
    
    /**
     * 判断用户名称是否存在
     * @param userName 用户名
     * @return 判断用户名称是否存在
     */
    public Result userExistence(String userName);
    
    /**
     * 实名验证
     * @param auditStaff  人员审核表
     * @param frontUser 人员信息
     * @return 实名验证结果
     */
    public Result realName(AuditStaff auditStaff, FrontUser frontUser);
    
    /**
     * 完善资料
     * @param frontUser
     * @throws BaseException 
     */
    public void updateFrontUser(FrontUser frontUser)
        throws BaseException;
    
    /**
     * 基本信息更新
     * @param frontUser
     * @throws BaseException
     */
    public void updateUser(FrontUser frontUser)
        throws BaseException;
    
    /**
     * 查询用户
     * @param id
     * @return
     */
    public FrontUser queryById(Integer id);
    
    /**
     * 个人中心
     * @param user
     * @return
     */
    public FrontUser queryPersonCenter(FrontUser user);
    
    /**
     * 按条件查询第三方用户
     * @param tser
     * @return
     */
    public List<ThirdUser> queryThirdUserByParam(ThirdUser tser, Integer pageNum, Integer pageSize);
    
    /**
     * 按修改第三方用户信息
     * @param tser
     * @return
     */
    public int updateThirdUserById(ThirdUser tser);
    /**
     * 按条件查询用户
     * @param user
     * @return
     */
    public List<FrontUser> queryUserByParam(FrontUser user, Integer pageNum, Integer pageSize);
    
    /**
     * 重置密码
     * @throws BaseException 
     */
    public void resetFrontUser(FrontUser user)
        throws BaseException;
    
    /**
     * 查找企业用户列表
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<FrontUser> queryCompanyUserList(FrontUser user, Integer pageNum, Integer pageSize);
    
    Double countDonateAmount(boolean isUseConfig);
    
    int  countDonatePeople(boolean isUseConfig);
    /**
     * 首页累计项目发布数
     * @param isUseConfig
     * @param field 排除该领域，""表示不排除  
     * @param isHide 排除隐藏，-1表示不排除
     * @return
     */
    int  countDonateProject(boolean isUseConfig,String field,Integer isHide);
    
    /**
     * 根据善库id查询users头像
     * @param libraryId
     * @return
     */
    List<FrontUser> queryByIdList(Integer libraryId);
    
    /**
     * 查询数据统计
     * @param dataStatistics
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<DataStatistics> queryDataStatisticsList(DataStatistics dataStatistics,Integer pageNum,Integer pageSize)throws Exception;


    public void saveTeam(Integer userId, Integer projectId, Integer coverImgId, String launchName, Integer state) throws BaseException;

    public void addTeamPeopleAndUpdateTeam(Integer userId, Integer projectId, Integer teamId) throws BaseException;

    /**
     * 根据userType查询爱心数据
     * @param userType
     * @param donateTimeStr
     * @return
     */
    List<FrontUser> queryLoveData(String userType, String donateTimeStr, Integer pageNum, Integer pageSize);

    /**
     * 查询企业团体爱心数据
     * @param donateTimeStr
     * @return
     */
    List<FrontUser> queryEnterpriseLoveData(String donateTimeStr, Integer pageNum, Integer pageSize);

    /**
     * 根据userType查询人数
     * @param userType
     * @return
     */
    int countLoveData(String userType);

    /**
     * 查询企业团体人数
     * @return
     */
    int countEnterpriseLoveData();
}
