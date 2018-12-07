package com.guangde.business.service;

import java.util.List;

import com.guangde.business.entry.NewLeaveWord;
import com.guangde.business.exception.BaseException;

public interface NewLeaveWordService {

	/**
	 * 保存留言
	 * @param param
	 * @return
	 */
	void saveOrUpdate(NewLeaveWord param) throws Exception; 
	
	/**
	 * 根据条件查询留言记录
	 * @param param
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	List<NewLeaveWord> queryNewLeaveWord(NewLeaveWord param,int pageNum,int pageSize);
	/**
	 * 根据条件查询未回复的留言记录
	 * @param param
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	List<NewLeaveWord> queryNoReplyByParam(NewLeaveWord param,int pageNum,int pageSize)throws  Exception;
	/**
	 * 根据项目id查询留言
	 * @param projectId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	List<NewLeaveWord> queryMessageWall(Integer projectId,int pageNum,int pageSize)throws  Exception;
	 
	/**
     * 根据用户id查询是否留过言
     * @param userId
     * @return
     */
	boolean queryIsOrNotNewLeaveWordByUserId(Integer userId);
	
	/**
	 * 根据项目id查询不重复的留言数
	 * @param param
	 * @param type 0:今天   1:所有
	 * @return
	 */
	int countNewLeaveWordByNoRepeatUserId(NewLeaveWord param);

	/**
	 * 根据用户id查询捐款评论未读列表
	 * @param userId
	 * @return
	 */
	List<NewLeaveWord> queryNotReadDonateNewLeaveWordByUserId(Integer userId);

	/**
	 * 批量更新评论为已读
	 * @param ids
	 * @throws Exception
     */
	void update(List<Integer> ids) throws BaseException;
	
}
