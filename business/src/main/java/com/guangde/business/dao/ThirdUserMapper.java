package com.guangde.business.dao;

import java.util.List;

import com.guangde.business.entry.ThirdUser;

public interface ThirdUserMapper extends BaseMapper<ThirdUser>{

	/**
     * 保存第三方用户信息
     * @param ThirdUser
     */
    void saveThirdUser(ThirdUser thirdUser);
    
    
    /**
     * 按条件查询第三方用户
     */
    List<ThirdUser> queryThirdUserByParam(ThirdUser thirdUser);
    
    /**
     * 按id修改第三方用户信息
     * @return 
     */
    int updateThirdUserById(ThirdUser thirdUser);


}
