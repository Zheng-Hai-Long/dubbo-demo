package com.guangde.business.service;

import com.guangde.business.entry.TogetherConfig;
import com.guangde.business.exception.BaseException;

import java.util.List;

public interface TogetherConfigService
{

	/**
	 * 保存一起捐配置
	 * @param param
	 * @return
	 */
	void save(TogetherConfig param)throws BaseException;
	
	/**
	 * 更新一起捐配置
	 * @param param
	 * @return
	 */
	void update(TogetherConfig param)throws BaseException;
	
	/**
	 * 根据param查询一起捐
	 * @param param
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<TogetherConfig> queryByParam(TogetherConfig param,int pageNum,int pageSize);
	
	/**
	 * 根据param查询一起捐
	 * @param param
	 * @return
	 */
	TogetherConfig selectByParam(TogetherConfig param);
	
	/**
	 * 根据id查询一起捐
	 * @param id
	 * @return
	 */
	TogetherConfig queryById(Integer id);
	
	/**
	 * 根据项目id查询发起一起捐人数
	 * @param projectId
	 * @return
	 */
	int countTogetherConfig(Integer projectId);
	
	/**
	 * 根据项目id查询参与一起捐人数
	 * @param projectId
	 * @return
	 */
	int countTogetherConfigSum(Integer projectId);
    
	/**
	 *  根据项目id查询一起捐list（包括用户昵称、头像）
	 * @param projectId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<TogetherConfig> queryTogetherConfigByProjectId(Integer projectId, int pageNum, int pageSize);
	
	/**
	 * 根据项目id查询一起捐，捐款，留言
	 * @param projectId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<TogetherConfig> queryDetailByProjectId(Integer projectId,int pageNum,int pageSize);
	
	/**
	 * 根据项目id查询一起捐，捐款，同一用户累计
	 * @param projectId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<TogetherConfig> queryTogetherDonateListByProjectId(Integer projectId,int pageNum,int pageSize);
	
	/**
	 * 根据项目Id查询一起捐列表的发起人数、募捐金额、募捐次数的总和
	 * @param togetherConfig
	 * @return
	 */
	List<TogetherConfig> oneApiTogetherConfigTotal(TogetherConfig togetherConfig);
	
	/**
	 * 根据param查询一起捐返回捐赠总金额排序
	 * @param param
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<TogetherConfig> queryByParamForMoney(TogetherConfig param,int pageNum,int pageSize);

	public TogetherConfig countTogetherDonateMoneyAndDonateNumberByProjectId(Integer paramInteger);
}
