package com.guangde.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.BFileMapper;
import com.guangde.business.dao.ServiceLeaveWordMapper;
import com.guangde.business.entry.BFile;
import com.guangde.business.entry.ServiceLeaveWord;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.ServiceLeaveWordService;
import com.guangde.business.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service("serviceLeaveWordService")
public class ServiceLeaveWordServiceImpl extends BaseService implements ServiceLeaveWordService{
	@Autowired
	private ServiceLeaveWordMapper serviceLeaveWordMapper;
	
	private BFileMapper bFileMapper;

	@Override
	public void saveServiceLeaveWord(ServiceLeaveWord serviceLeaveWord) 
			throws BaseException{
		try {
			serviceLeaveWord.setCreateTime(DateUtil.getDate());
			serviceLeaveWord.setState(300);
			serviceLeaveWordMapper.save(serviceLeaveWord);
		} catch (Exception e) {
			logger.error("save database error", e);
			throw new BaseException(ResultCode.Error);
		}
		
	}

	@Override
	public List<ServiceLeaveWord> queryServiceLeaveWordlist(
			ServiceLeaveWord serviceLeaveWord, int pageNum, int pageSize) {
		logger.info("query param is" + serviceLeaveWord);
		PageHelper.startPage(pageNum, pageSize);
		List<ServiceLeaveWord> list = null;
		
		try {
			list = serviceLeaveWordMapper.queryByParam(serviceLeaveWord);
			String safeName = "";
			if(list != null && list.size() > 0){
			for (ServiceLeaveWord slWord:list) {
				if(slWord.getUserNickName() != null){
					safeName = UserUtil.getSafeName(slWord.getUserNickName());
					slWord.setUserNickName(safeName);
				}
				if(StringUtils.isNotEmpty(slWord.getHeadUrl())){
					if(!slWord.getHeadUrl().contains("http://")){
						String resUrl = ConfigLoader.getResPictrueURL();
						slWord.setHeadUrl(resUrl+slWord.getHeadUrl());
					}
				}
				String ids = slWord.getContentImageId();
				String resUrl = ConfigLoader.getResPictrueURL();
				if(StringUtils.isNotEmpty(ids)){
					List<Integer> idList = StrUtil.convertIdList(ids, Constant.data_separator);
					List<BFile> bFiles = bFileMapper.queryByIdList(idList);
					if(!CollectionUtils.isEmpty(bFiles)){
						for (BFile bFile : bFiles){
							bFile.setUrl(resUrl + bFile.getUrl());
							if(!StringUtils.isEmpty(bFile.getMiddleUrl()))
		                    {
		                    	bFile.setMiddleUrl(resUrl+bFile.getMiddleUrl());
		                    }
		                    if(!StringUtils.isEmpty(bFile.getLitterUrl()))
		                    {
		                    	bFile.setLitterUrl(resUrl+bFile.getLitterUrl());
		                    }
						}
						slWord.setBfList(bFiles);
					}
				}
			}}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
