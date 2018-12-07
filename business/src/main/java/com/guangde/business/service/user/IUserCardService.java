package com.guangde.business.service.user;

import java.util.List;

import com.guangde.business.entry.UserCard;
import com.guangde.business.exception.BaseException;

/**
 * 绑定银行卡
 * @author Administrator
 *
 */
public interface IUserCardService
{
    void saveUserCard(UserCard userCard)
        throws BaseException;
    /**
     * 根据项目id绑定银行卡
     * @param userCard
     * @throws BaseException
     */
    void saveNewUserCard(UserCard userCard)
            throws BaseException;
    
    List<UserCard> queryUserCardByParam(UserCard userCard, Integer pageNum, Integer pageSize);
    
    void updateUserCard(UserCard userCard)
        throws BaseException;
    
    /**
     * 设置默认选中的银行卡
     * @param userCard
     * @throws BaseException 
     */
    void resetUserCardSelectd(UserCard userCard)
        throws BaseException;
    
}
