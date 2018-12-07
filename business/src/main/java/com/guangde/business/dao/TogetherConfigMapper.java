package com.guangde.business.dao;

import java.util.List;

import com.guangde.business.entry.TogetherConfig;

public interface TogetherConfigMapper extends BaseMapper<TogetherConfig>
{

	void updateTogetherTeamPeopleNum(Integer paramInteger);

	void saveTogether(TogetherConfig paramTogetherConfig);

    TogetherConfig selectByParam(TogetherConfig param);
    
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
	List<TogetherConfig> queryTogetherConfigByProjectId(Integer projectId);
	
	/**
	 * 根据项目id查询一起捐，捐款，留言
	 * @param projectId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<TogetherConfig> queryDetailByProjectId(Integer projectId);
	
	/**
	 * 根据项目id查询一起捐，捐款，同一用户累计
	 * @param projectId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<TogetherConfig> queryTogetherDonateListByProjectId(Integer projectId);
	
	List<TogetherConfig> oneApiTogetherConfigTotal(TogetherConfig togetherConfig);
	
	List<TogetherConfig> queryByParamForMoney(TogetherConfig togetherConfig);

	TogetherConfig countTogetherDonateMoneyAndDonateNumberByProjectId(Integer paramInteger);
}
