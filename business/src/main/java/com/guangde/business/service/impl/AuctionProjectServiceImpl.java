package com.guangde.business.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.AuctionProjectMapper;
import com.guangde.business.dao.BFileMapper;
import com.guangde.business.entry.AuctionProject;
import com.guangde.business.entry.BFile;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.AuctionProjectService;
import com.guangde.business.service.BaseService;
import com.guangde.business.util.ConfigLoader;
import com.guangde.business.util.Constant;
import com.guangde.business.util.ResultCode;
import com.guangde.business.util.StrUtil;

@Service("auctionProjectService")
public class AuctionProjectServiceImpl extends BaseService implements AuctionProjectService {

	@Autowired
	private BFileMapper bFileMapper;
	
	@Autowired
	private AuctionProjectMapper auctionProjectMapper;


	@Override
	public AuctionProject queryProjectDetail(Integer id) {
		
		logger.info("query param id:" + id);
		
		AuctionProject p = auctionProjectMapper.queryById(id);
		if (p == null) {
			return null;
		}

		String ids = p.getContentImageId();
		String resUrl = ConfigLoader.getResPictrueURL();
		if (StrUtil.isNotEmpty(ids)) {
			List<Integer> idList = StrUtil.convertIdList(ids,
					Constant.data_separator);

			List<BFile> list = bFileMapper.queryByIdList(idList);

			if (!CollectionUtils.isEmpty(list)) {

				for (BFile bFile : list) {
					bFile.setUrl(resUrl + bFile.getUrl());
					if (!StringUtils.isEmpty(bFile.getMiddleUrl())) {
						bFile.setMiddleUrl(resUrl + bFile.getMiddleUrl());
					}
					if (!StringUtils.isEmpty(bFile.getLitterUrl())) {
						bFile.setLitterUrl(resUrl + bFile.getLitterUrl());
					}
				}

				p.setBfileList(list);
			}
		}
		if (p.getCoverImageUrl() != null) {
			p.setCoverImageUrl(resUrl + p.getCoverImageUrl());
		}
	
		return p;
	}



	@Override
	public List<AuctionProject> queryProjectList(AuctionProject project, int pageNum,
			int pageSize) {
		logger.info("query param:" + project);

		PageHelper.startPage(pageNum, pageSize);

		List<AuctionProject> list = null;
		try {
			list = auctionProjectMapper.queryByParam(project);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!CollectionUtils.isEmpty(list)) {
			String resUrl = ConfigLoader.getResPictrueURL();

			for (AuctionProject temp : list) {
				if (temp.getCoverImageUrl() != null) {
					temp.setCoverImageUrl(resUrl + temp.getCoverImageUrl());
				}
			}
		}

		return list;
	}

}
