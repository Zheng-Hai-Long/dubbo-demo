package com.guangde.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.NewLeaveWordMapper;
import com.guangde.business.entry.NewLeaveWord;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.NewLeaveWordService;
import com.guangde.business.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("newLeaveWordService")
public class NewLeaveWordServiceImpl extends BaseService implements NewLeaveWordService{
	@Autowired
	private NewLeaveWordMapper newLeaveWordMapper;
	
	

	@Override
	public void saveOrUpdate(NewLeaveWord param) throws Exception {
		try {
			if(param.getId()==null){
				newLeaveWordMapper.save(param);
			}else{
				newLeaveWordMapper.update(param);
			}
			
		} catch (Exception e) {
			logger.error(e);
            throw new BaseException(ResultCode.Error);
		}
		
	}



	@Override
	public List<NewLeaveWord> queryNewLeaveWord(NewLeaveWord param,int page,int pageSize){
		PageHelper.startPage(page, pageSize);
		return newLeaveWordMapper.queryByParam(param);
	}


	@Override
	public List<NewLeaveWord> queryNoReplyByParam(NewLeaveWord param,
			int pageNum, int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		return newLeaveWordMapper.queryNoReplyByParam(param);
	}



	@Override
	public List<NewLeaveWord> queryMessageWall(Integer projectId, int pageNum,
			int pageSize) throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		return newLeaveWordMapper.queryMessageWall(projectId);
	}



	@Override
	public boolean queryIsOrNotNewLeaveWordByUserId(Integer userId) {
		return newLeaveWordMapper.queryIsOrNotNewLeaveWordByUserId(userId)>0;
	}



	@Override
	public int countNewLeaveWordByNoRepeatUserId(NewLeaveWord param) {
		return newLeaveWordMapper.countNewLeaveWordByNoRepeatUserId(param);
	}

	@Override
	public List<NewLeaveWord> queryNotReadDonateNewLeaveWordByUserId(Integer userId) {
		return newLeaveWordMapper.queryNotReadDonateNewLeaveWordByUserId(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(List<Integer> ids) throws BaseException {
		NewLeaveWord newLeaveWord = new NewLeaveWord();
		newLeaveWord.setIsRead(1);
		try {
			for (Integer id : ids){
				newLeaveWord.setProjectDonateId(id);
				newLeaveWordMapper.updateIsRead(newLeaveWord);
			}
		}catch (Exception e){
			e.printStackTrace();
			throw new BaseException(ResultCode.Error);
		}
	}

}
