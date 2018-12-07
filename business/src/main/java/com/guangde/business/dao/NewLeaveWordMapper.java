package com.guangde.business.dao;

import java.util.List;

import com.guangde.business.entry.NewLeaveWord;

public interface NewLeaveWordMapper extends BaseMapper<NewLeaveWord>{
	/**
	 * 按条件查询评论数（去除自己留言、回复）
	 * @param newLeaveWord
	 * @return
	 */
	Integer countLeaveWordNum(NewLeaveWord newLeaveWord);
	
	/**
	 * 查询未回复的留言记录
	 * @param newLeaveWord
	 * @return
	 */
	List<NewLeaveWord>  queryNoReplyByParam(NewLeaveWord newLeaveWord);
	
	/**
	 * 根据项目id查询留言
	 * @param projectId
	 * @return
	 */
	List<NewLeaveWord> queryMessageWall(Integer projectId);
	
	/**
	 * 根据用户id查询是否留过言
	 * @param userId
	 * @return
	 */
	int queryIsOrNotNewLeaveWordByUserId(Integer userId);
	
	/**
     * 根据项目id查询不重复的留言人数
     * @param projectId 项目id
     * @param type 0:今天   1:所有
     * @return
     */
    int countNewLeaveWordByNoRepeatUserId(NewLeaveWord param);

	/**
	 * 根据用户id查询捐款评论未读数
	 * @param userId
	 * @return
     */
	List<NewLeaveWord> queryNotReadDonateNewLeaveWordByUserId(Integer userId);

	int updateIsRead(NewLeaveWord data);
}
