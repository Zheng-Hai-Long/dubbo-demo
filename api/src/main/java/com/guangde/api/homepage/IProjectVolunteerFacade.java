package com.guangde.api.homepage;

import com.guangde.entry.*;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;

public interface IProjectVolunteerFacade {

    /**
     * 求救者信息入库
     *
     * @param apiProjectCryPeople
     * @return
     */
    ApiResult save(ApiProjectCryPeople apiProjectCryPeople);


    /**
     * 根据model查询报名信息
     *
     * @param model
     * @return
     */
    ApiCommonForm selectByParam(ApiCommonForm model);

    /**
     * 保存报名信息
     *
     * @param model
     * @return
     */
    int save(ApiCommonFormUser model);

    /**
     * 保存报名信息
     *
     * @param model
     * @return
     */
    int update(ApiCommonFormUser model);

    /**
     * 更新报名信息
     *
     * @param model
     * @return
     */
    int update(ApiCommonForm model);

    /**
     * 根据model查询报名人数
     *
     * @param model
     * @return
     */
    int countCommonFormUserByParam(ApiCommonFormUser model);

    /**
     * 根据model查询报名信息详细
     *
     * @param model
     * @return
     */
    ApiCommonFormUser queryCommonFormUserDetailByParam(ApiCommonFormUser model);

    /**
     * 根据formId查询报名信息
     *
     * @param apiCommonFormUser
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiCommonFormUser> queryCommonFormList(ApiCommonFormUser apiCommonFormUser, Integer pageNum, Integer pageSize);


    /**
     * 判断用户是否是此活动的志愿者
     *
     * @param userId
     * @param activityId
     * @return
     */
    boolean isOrNotActivityVolunteer(Integer userId, Integer activityId);

    ApiResult saveCommformAndUpdateCommonformUser(ApiCommonFormUser apiCommonFormUser, ApiCommonForm commonForm);


    /**
     * 保存记录
     * @param data
     * @return
     */
    ApiResult saveCommonRecord(ApiCommonFormConfig data);

}
