package com.guangde.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.guangde.business.entry.SystemNotify;
import com.guangde.business.entry.SystemTypeModel;

public interface SystemNotifyMapper
{
    List<SystemNotify> queryByUserId(Integer userId);
    
    void save(SystemNotify systemNotify);
    
    void delete(@Param("id") Integer id, @Param("userId") Integer userId);
    
    /**
     * 消息查询
     * @param startTime 开始时间
     * @param endTime 结果时间
     * @param userId 用户ID
     * @param state 状态 null：所有  0：未读  1：已读
     * @return 消息列表
     */
    List<SystemNotify> queryByQueryTime(Map<String, Object> map);
    
    void setToRead(@Param("id") Integer id, @Param("userId") Integer userId);
    
    /**
     * 按条件查询
     * 
     * @param systemNotify
     * @return list
     */
    List<SystemNotify> queryByCondition(SystemNotify systemNotify);
    
    SystemNotify queryNextRecord(@Param("id") Integer id, @Param("userId") Integer userId);
    
    SystemNotify queryPreviousRecord(@Param("id") Integer id, @Param("userId") Integer userId);
    
    SystemNotify queryByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);
    
    /**
     * 统计未读消息
     * @param userId
     * @param state
     * @return
     */
    Integer countNoReadByUserIdAndState(@Param("userId") Integer userId, @Param("state") Integer state);
    
    /**
     * 统计未读消息,根据对象SystemNotify
     * @param SystemNotify
     * @return
     */
    Integer countNoReadBySystemNotify(SystemNotify systemNotify);
    
    List<SystemTypeModel> queryByIds(SystemTypeModel systemTypeModel);
}
