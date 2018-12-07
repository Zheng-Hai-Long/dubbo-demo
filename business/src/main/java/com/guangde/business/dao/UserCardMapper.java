package com.guangde.business.dao;

import com.guangde.business.entry.UserCard;

public interface UserCardMapper extends BaseMapper<UserCard>
{
    UserCard queryUserCardByCard(String card, Integer userId);
    
    /**
     * 根据项目id和用户id查询银行卡
     * @param card
     * @param userId
     * @return
     */
    UserCard queryNewUserCardByCard(Integer projectId, Integer userId);
    
    void updateUserCard(UserCard userCard);
}
