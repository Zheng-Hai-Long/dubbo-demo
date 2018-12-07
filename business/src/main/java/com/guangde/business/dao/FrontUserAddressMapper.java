package com.guangde.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.guangde.business.entry.FrontUser_address;

public interface FrontUserAddressMapper extends BaseMapper<FrontUser_address>{
	
	int deleteByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);

	/**
	 * 取消其他默认地址
	 * @return
     */
	int cancelDefaultAddress(Integer userId);

	/**
	 * 查询用户默认地址记录数
	 * @param userId
	 * @return
     */
	int countUserAddress(Integer userId);

	int defaultUserAddress(@Param("id") Integer id, @Param("userId") Integer userId);

	/**
	 * 根据id查询用户地址
	 * @param id
	 * @param userId
     * @return
     */
	FrontUser_address queryUserAddress(@Param("id") Integer id, @Param("userId") Integer userId);

	int countByUserId(Integer userId);
	
	List<FrontUser_address> queryByUserId(Integer userId);
}
