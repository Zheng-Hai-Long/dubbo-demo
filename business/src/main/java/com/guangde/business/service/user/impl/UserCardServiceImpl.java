package com.guangde.business.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.UserCardMapper;
import com.guangde.business.entry.UserCard;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.user.IUserCardService;
import com.guangde.business.util.Constant;
import com.guangde.business.util.ResultCode;
import com.guangde.business.util.State;

@Service("userCardService")
public class UserCardServiceImpl extends BaseService implements IUserCardService
{
    @Autowired
    private UserCardMapper userCardMapper;
    
    @Override
    public List<UserCard> queryUserCardByParam(UserCard userCard, Integer pageNum, Integer pageSize)
    {
        logger.info("queryUserCardByParam param : " + userCard);
        
        PageHelper.startPage(pageNum, pageSize);
        
        List<UserCard> list = userCardMapper.queryByParam(userCard);
        
        return list;
    }
    
    @Override
    public void saveUserCard(UserCard userCard)
        throws BaseException
    {
        logger.info("saveUserCard param : " + userCard);
        try
        {
            UserCard card = new UserCard();
            if (userCard.getUserId() != null)
            {
                card.setUserId(userCard.getUserId());
            }
            card.setUseState(State.enable);
            List<UserCard> list = userCardMapper.queryByParam(card);
            if (list != null && list.size() >= 3)
            {
                throw new BaseException(ResultCode.tooManyCard);
            }
            card = new UserCard();
            card = userCardMapper.queryUserCardByCard(userCard.getCard(), userCard.getUserId());
            if (card != null)
            {
                throw new BaseException(ResultCode.hasBingding);
            }
            userCard.setUseState(100);
            if (list == null || list.size() == 0)
            {
                userCard.setIsSelected(0);
            }
            else
            {
                userCard.setIsSelected(1);
            }
            userCardMapper.save(userCard);
        }
        catch (BaseException e)
        {
            logger.error(e);
            throw e;
        }
        catch (Exception e)
        {
            logger.error(e);
            throw new BaseException(ResultCode.Error);
        }
    }
    
    @Override
    public void saveNewUserCard(UserCard userCard)
        throws BaseException
    {
        logger.info("saveUserCard param : " + userCard);
        try
        {
            UserCard card = new UserCard();
            if (userCard.getUserId() != null)
            {
                card.setUserId(userCard.getUserId());
            }
            card.setUseState(State.enable);
            List<UserCard> list = userCardMapper.queryByParam(card);
            //银行卡超限制
            /*if (list != null && list.size() >= 3)
            {
                throw new BaseException(ResultCode.tooManyCard);
            }*/
            card = new UserCard();
            card = userCardMapper.queryNewUserCardByCard(userCard.getProjectId(), userCard.getUserId());
            //已绑定
            if (card != null)
            {
                throw new BaseException(ResultCode.hasBingding);
            }
            userCard.setUseState(100);
            /*if (list == null || list.size() == 0)
            {
                userCard.setIsSelected(0);
            }
            else
            {
                userCard.setIsSelected(1);
            }*/
            userCard.setIsSelected(0);
            userCardMapper.save(userCard);
        }
        catch (BaseException e)
        {
            logger.error(e);
            throw e;
        }
        catch (Exception e)
        {
            logger.error(e);
            throw new BaseException(ResultCode.Error);
        }
    }
    
    @Override
    public void updateUserCard(UserCard userCard)
        throws BaseException
    {
        try
        {
            userCardMapper.update(userCard);
        }
        catch (Exception e)
        {
            logger.error(e);
            throw new BaseException(ResultCode.Error);
        }
    }
    
    @Override
    public void resetUserCardSelectd(UserCard userCard)
        throws BaseException
    {
        try
        {
            UserCard card = new UserCard();
            card.setIsSelected(Constant.CARD_NOSELECTED);
            card.setUserId(userCard.getUserId());
            userCardMapper.updateUserCard(card);
            
            card = new UserCard();
            card.setId(userCard.getId());
            card.setIsSelected(Constant.CARD_SELECTED);
            
            userCardMapper.updateUserCard(card);
        }
        catch (Exception e)
        {
            logger.error(e);
            throw new BaseException(ResultCode.Error);
        }
    }
}
