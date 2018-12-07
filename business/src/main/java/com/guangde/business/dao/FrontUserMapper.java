package com.guangde.business.dao;

import com.guangde.business.entry.FrontUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FrontUserMapper extends BaseMapper<FrontUser>
{

    /**
     * 根据手机号查询用户
     * @param mobileNum
     * @return
     */
    FrontUser queryByMobile(String mobileNum);

    /**
     * 根据用户查询前端用户
     * @param userName  用户名
     * @return 前端用户
     */
    FrontUser getUserByUserName(String userName);
    
    /**
     * 根据用户查询前端用户
     * @param registerIP  注册IP
     * @return 前端用户
     */
    FrontUser getUserByRegisterIP(String registerIP, String userType);
    
    /**
     * 保存前端用户
     * @param frontUser
     */
    int saveFrontUser(FrontUser frontUser);
    
    /**
     * 用户中心数据
     * @param user
     * @return
     */
    FrontUser queryPersonCenter(FrontUser user);
    
    /**
     * 按条件查询用户
     */
    List<FrontUser> queryUserByParam(FrontUser user);
    
    /**
     * 重置
     * @param user
     */
    void resetFrontUser(FrontUser user);
    
    /**
     * 统计企业用户捐助总金额
     * @param userId
     * @return
     */
    Double countCompanyTotalAmount(Integer userId);
    
    /**
     * 查找企业用户信息
     * @param user
     * @return
     */
    List<FrontUser> queryCompanyUserList(FrontUser user);
    
    Double countDonateAmount();
    
    FrontUser queryDaysById(Integer id);
    
    /**
     * 根据善库id查询users头像
     * @param libraryId
     * @return
     */
    List<FrontUser> queryByIdList(Integer libraryId);

    /**
     * 根据userType查询爱心数据
     * @param userType
     * @return
     */
    List<FrontUser> queryLoveData(@Param("userType") String userType, @Param("donateTimeStr") String donateTimeStr);

    /**
     * 查询企业团体爱心数据
     * @return
     */
    List<FrontUser> queryEnterpriseLoveData(@Param("donateTimeStr") String donateTimeStr);

    /**
     * 根据userType查询人数
     * @param userType
     * @return
     */
    int countLoveData(@Param("userType") String userType);

    /**
     * 查询企业团体人数
     * @return
     */
    int countEnterpriseLoveData();
}
