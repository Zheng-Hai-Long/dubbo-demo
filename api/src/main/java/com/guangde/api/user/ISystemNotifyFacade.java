package com.guangde.api.user;

import java.util.List;

import com.guangde.entry.ApiSystemNotify;
import com.guangde.entry.ApiSystemTypeModel;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;

/**
 * 消息与通知
 * 
 * @author phx
 * 
 */
public interface ISystemNotifyFacade
{
    
    /**
     * 查询用户的消息和通知
     * 
     * @param userId
     *            用户ID
     * @return 消息列表
     */
    List<ApiSystemNotify> queryByUserId(Integer userId);
    
    /**
     * 保存消息
     * @param apiSystemNotify  消息对象
     * @return  保存结果
     */
    ApiResult save(ApiSystemNotify apiSystemNotify);
    
    /**
     * 删除消息
     * @param id 消息对象ID
     * @param userId 用户ID
     * @return 删除结果
     */
    ApiResult delete(Integer id, Integer userId);
    
    /**
     * 消息设为已读
     * @param id 消息id
     * @param userId 用户ID
     * @return 操作结果
     */
    ApiResult setToRead(Integer id, Integer userId);
    
    /**
     * 消息查询
     * @param systemNotify  查询参数
     * @param pageNum 当前页码
     * @param pageSize 每页行数
     * @return ApiPage 对象
     */
    ApiPage<ApiSystemNotify> queryByCondition(ApiSystemNotify systemNotify, int pageNum, int pageSize);
    
    /**
     * 查询下一条消息
     * @param id ID
     * @param userId 用户ID
     * @return ApiSystemNotify
     */
    ApiSystemNotify queryNextRecord(Integer id, Integer userId);
    
    /**
     * 查询上一条消息
     * @param id ID
     * @param userId 用户ID
     * @return ApiSystemNotify
     */
    ApiSystemNotify queryPreviousRecord(Integer id, Integer userId);
    
    /**
     * 查询条消息
     * @param id ID
     * @param userId 用户ID
     * @return ApiSystemNotify
     */
    ApiSystemNotify queryDetail(Integer id, Integer userId);
    
    /**
     * 消息统计
     * @param userId  用户ID
     * @param state   消息状态  0 ： 未读     1：已读
     * @return
     */
    Integer countRead(Integer userId, Integer state);
    
    /**
     * 消息统计
     * @param systemNotify  查询参数
     * @return
     */
    Integer countRead(ApiSystemNotify systemNotify);
    
    /**
     * 根据多个id查询数据
     * @param systemTypeModel
     * @return
     */
    List<ApiSystemTypeModel> queryByIds(ApiSystemTypeModel systemTypeModel);
}
