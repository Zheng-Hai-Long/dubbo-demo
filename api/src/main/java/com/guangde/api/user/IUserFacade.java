package com.guangde.api.user;

import com.guangde.entry.*;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;

public interface IUserFacade {

    /**
     * 根据手机号查询用户
     * @param mobileNum
     * @return
     */
    ApiFrontUser queryByMobile(String mobileNum);

    /**
     * 根据条件查询用户列表
     * @param model
     * @return
     */
    ApiFrontUser queryOneUserByParam(ApiFrontUser model);

    /**
     * 根据unionId查询第三方用户列表
     * @param unionId
     * @return
     */
    ApiResult queryThirdUserByParam(String unionId);

    /**
     * 第三方用户注册
     *
     * @param user 用户信息
     * @return 注册结果
     */
    ApiResult thirdUserRegistered(ApiFrontUser user, ApiThirdUser tuser);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    ApiResult queryUserInfo(Integer userId);

    ApiResult queryUserSet(Integer userId);



    /**
     * 本地注册用户登陆
     *
     * @param user 本地注册用户
     * @return 登陆结果
     */
    public ApiResult localUserlogin(ApiFrontUser user);

    /**
     * 第三方用户登陆
     *
     * @param user 第三方用户
     * @return 登陆结果
     */
    public ApiResult thirdUserLogin(ApiThirdUser user);

    /**
     * 前端用户注册
     *
     * @param user 用户信息
     * @return 注册结果
     */
    ApiResult registered(ApiFrontUser user);



    /**
     * 判断用户名称是否存在
     *
     * @param userName 用户名
     * @return 判断用户名称是否存在
     */
    public ApiResult userExistence(String userName);

    /**
     * (完善资料)
     *
     * @param apiFrontUser
     * @return
     */
    public ApiResult updateFrontUser(ApiFrontUser apiFrontUser);

    /**
     * 更新用户信息
     *
     * @param apiFrontUser
     * @return
     */
    public ApiResult updateUser(ApiFrontUser apiFrontUser);

    /**
     * 实名验证
     *
     * @param auditStaff 人员审核表
     * @param frontUser  实名验证的人员信息
     * @return 实名验证结果
     */
    public ApiResult realName(ApiAuditStaff auditStaff, ApiFrontUser frontUser);

    /**
     * 查询用户信息
     *
     * @param id
     * @return ApiFrontUser
     */
    ApiFrontUser queryById(Integer id);

    /**
     * 个人中心
     *
     * @param user
     * @return
     */
    ApiFrontUser queryPersonCenter(ApiFrontUser user);

    /**
     * 根据条件查询用户信息
     *
     * @param user
     * @return
     */
    ApiFrontUser queryUserByParam(ApiFrontUser user);

    /**
     * 根据条件查询第三方用户信息
     *
     * @return
     */
    ApiThirdUser queryThirdUserByParam(ApiThirdUser Tuser);

    /**
     * 根据条件查询用户信息列表
     *
     * @param user
     * @return
     */
    ApiPage<ApiFrontUser> queryUserList(ApiFrontUser user, Integer pageNum, Integer pageSize);


    /**
     * 修改用户信息
     * 重置密码
     *
     * @param apiFrontUser
     * @return
     */
    ApiResult resetFrontUser(ApiFrontUser apiFrontUser);

    /**
     * 绑定银行卡
     *
     * @param apiUserCard
     * @return
     */
    public ApiResult saveUserCard(ApiUserCard apiUserCard);

    /**
     * 根据项目id绑定以后卡
     */
    public ApiResult saveNewUserCard(ApiUserCard apiUserCard);

    /**
     * 条件查询绑定银行卡
     *
     * @param apiUserCard
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ApiPage<ApiUserCard> queryUserCardList(ApiUserCard apiUserCard, Integer pageNum, Integer pageSize);

    /**
     * 删除银行卡
     *
     * @return
     */
    public ApiResult updateUserCard(ApiUserCard apiUserCard);

    /**
     * 重置默认卡号
     *
     * @param apiUserCard
     * @return
     */
    public ApiResult resetUserCardSelected(ApiUserCard apiUserCard);



    public ApiBFile queryBFileById(Integer id);

    /**
     * 更新用户实名认证信息
     *
     * @param apiUser
     * @param ids
     * @return
     */
    public int updateUserAndAudit(ApiFrontUser apiUser, String ids);

    /**
     * 根据param查询证实信息
     *
     * @param apiAuditProject
     * @return
     */
    public ApiAuditProject selectByParam(ApiAuditProject apiAuditProject);


    /**
     * 查询证实人的数目
     *
     * @param apiAuditProject
     * @return
     */
    public ApiResult selectAuditProjectCountByParam(ApiAuditProject apiAuditProject);

    public ApiResult saveAuditProject(ApiAuditProject apiAuditProject);


    /**
     * 查询证实人列表
     *
     * @param apiAuditProject
     * @return
     */
    public ApiPage<ApiAuditProject> queryApiAuditProjectList(ApiAuditProject apiAuditProject, Integer pageNum, Integer pageSize);


    /**
     * 保存微信模板
     *
     * @param apiWeixinModel
     * @return
     */
    public ApiResult saveWeixinModel(ApiWeixinModel apiWeixinModel);


    public ApiResult saveTeam(Integer userId, Integer projectId, Integer coverImgId, String launchName, Integer state);

    public ApiResult addTeamPeopleAndUpdateTeam(Integer userId, Integer projectId, Integer teamId);


}