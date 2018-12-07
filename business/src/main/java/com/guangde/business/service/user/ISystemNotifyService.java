package com.guangde.business.service.user;

import java.util.Date;
import java.util.List;

import com.guangde.business.entry.SystemNotify;
import com.guangde.business.entry.SystemTypeModel;
import com.guangde.business.pojo.Result;

public interface ISystemNotifyService
{
    /**
     * 查询系统通知
     * 
     * @param userId
     *            用户ID
     * @return 系统通知列表
     */
    List<SystemNotify> queryByUserId(Integer userId);
    
    /**
     * 保存消息
     * 
     * @param systemNotify
     *            消息对象
     * @return 保存结果
     */
    Result save(SystemNotify systemNotify);
    
    /**
    * 删除消息
    * 
    * @param id
    *            消息对象id
    * @param userId 
    * @return 删除结果
    */
    Result delete(Integer id, Integer userId);
    
    /**
     * 消息查询
     * @param startTime 开始时间
     * @param endTime 结果时间
     * @param userId 用户ID
     * @param state 状态 null：所有  0：未读  1：已读
     * @return 消息列表
     */
    List<SystemNotify> queryByQueryTime(Date startTime, Date endTime, Integer userId, Integer state);
    
    /**
     * 消息设为已读
     * @param id 消息id
     * @param userId 用户ID
     * @return 操作结果
     */
    Result setToRead(Integer id, Integer userId);
    
    //	Page<SystemNotify> queryPage(SystemNotify systemNotify,int pageSize,int pageNo);
    
    /**
     * 按条件查询
     * 返回结果list可以转Page对象
     * 
     * @param systemNotify 查询
     * @param pageNum
     * @param pageSize
     * @return list
     */
    List<SystemNotify> queryByCondition(SystemNotify systemNotify, int pageNum, int pageSize);
    
    SystemNotify queryNextRecord(Integer id, Integer userId);
    
    SystemNotify queryPreviousRecord(Integer id, Integer userId);
    
    SystemNotify queryByIdAndUserId(Integer id, Integer userId);
    
    /**
     * 消息统计
     * @param userId
     * @param state
     * @return
     */
    Integer countRead(Integer userId, Integer state);
    
    /**
     * 消息统计 对象SystemNotify
     * @param SystemNotify
     * @return
     */
    Integer countRead(SystemNotify systemNotify);
    
    List<SystemTypeModel> queryByIds(SystemTypeModel systemTypeModel);
}
