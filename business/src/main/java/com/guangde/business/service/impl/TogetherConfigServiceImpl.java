package com.guangde.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.constants.FrontUserConstants;
import com.guangde.business.dao.BFileMapper;
import com.guangde.business.dao.TogetherConfigMapper;
import com.guangde.business.entry.BFile;
import com.guangde.business.entry.TogetherConfig;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.TogetherConfigService;
import com.guangde.business.util.ConfigLoader;
import com.guangde.business.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("togetherConfigService")
public class TogetherConfigServiceImpl extends BaseService implements TogetherConfigService {

    @Autowired
    private TogetherConfigMapper togetherConfigMapper;
    @Autowired
    private BFileMapper bFileMapper;

    @Override
    public void save(TogetherConfig param) throws BaseException {
        try {
            togetherConfigMapper.save(param);
        } catch (Exception e) {
            logger.error("update database error", e);
            throw new BaseException(ResultCode.Error);
        }
    }

    @Override
    public void update(TogetherConfig param) throws BaseException {
        try {
            togetherConfigMapper.update(param);
        } catch (Exception e) {
            logger.error("update database error", e);
            throw new BaseException(ResultCode.Error);
        }
    }

    @Override
    public List<TogetherConfig> queryByParam(TogetherConfig param, int pageNum,
                                             int pageSize) {
        logger.info("query TogetherConfig param:" + param);
        PageHelper.startPage(pageNum, pageSize);
        List<TogetherConfig> list = togetherConfigMapper.queryByParam(param);
        String resUrl = ConfigLoader.getResPictrueURL();
        if (list != null && list.size() > 0) {
            for (TogetherConfig togetherConfig : list) {
                if (togetherConfig.getCoverImageId() != null) {
                    BFile bFile = bFileMapper.queryById(togetherConfig.getCoverImageId());
                    if (bFile != null && bFile.getUrl() != null) {
                        if (!bFile.getUrl().contains("http://")) {
                            togetherConfig.setCoverImageUrl(resUrl + bFile.getUrl());
                        } else {
                            togetherConfig.setCoverImageUrl(bFile.getUrl());
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public TogetherConfig selectByParam(TogetherConfig param) {
        logger.info("query TogetherConfig param:" + param);
        TogetherConfig togetherConfig = togetherConfigMapper.selectByParam(param);
        String resUrl = ConfigLoader.getResPictrueURL();
        if (togetherConfig != null && togetherConfig.getCoverImageId() != null) {
            BFile bFile = bFileMapper.queryById(togetherConfig.getCoverImageId());
            if (bFile != null && bFile.getUrl() != null) {
                if (!bFile.getUrl().contains("http://")) {
                    togetherConfig.setCoverImageUrl(resUrl + bFile.getUrl());
                } else {
                    togetherConfig.setCoverImageUrl(bFile.getUrl());
                }
            }
        }
        return togetherConfig;
    }

    @Override
    public TogetherConfig queryById(Integer id) {
        TogetherConfig togetherConfig = togetherConfigMapper.queryById(id);
        String resUrl = ConfigLoader.getResPictrueURL();
        if (togetherConfig != null && togetherConfig.getCoverImageId() != null) {
            BFile bFile = bFileMapper.queryById(togetherConfig.getCoverImageId());
            if (bFile != null && bFile.getUrl() != null) {
                if (!bFile.getUrl().contains("http://")) {
                    togetherConfig.setCoverImageUrl(resUrl + bFile.getUrl());
                } else {
                    togetherConfig.setCoverImageUrl(bFile.getUrl());
                }
            }
        }
        return togetherConfig;
    }

    @Override
    public int countTogetherConfig(Integer projectId) {
        int count = togetherConfigMapper.countTogetherConfig(projectId);
        return count;
    }

    @Override
    public int countTogetherConfigSum(Integer projectId) {
        int count = togetherConfigMapper.countTogetherConfigSum(projectId);
        return count;
    }

    @Override
    public List<TogetherConfig> queryTogetherConfigByProjectId(
            Integer projectId, int pageNum, int pageSize) {
        logger.info("query TogetherConfig projectId:" + projectId);
        PageHelper.startPage(pageNum, pageSize);
        List<TogetherConfig> list = togetherConfigMapper.queryTogetherConfigByProjectId(projectId);
        String resUrl = ConfigLoader.getResPictrueURL();
        if (list != null && list.size() > 0) {
            for (TogetherConfig togetherConfig : list) {
                /*if (togetherConfig.getNameOpen() == 1) {//匿名
                    togetherConfig.setNickName(FrontUserConstants.DEFAULT_DONATED_USER_NAME);
                    togetherConfig.setCoverImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
                } else {//不匿名*/
                    if (togetherConfig.getCoverImageId() != null) {
                        BFile bFile = bFileMapper.queryById(togetherConfig.getCoverImageId());
                        if (bFile != null && bFile.getUrl() != null) {
                            if (!bFile.getUrl().contains("http://")) {
                                togetherConfig.setCoverImageUrl(resUrl + bFile.getUrl());
                            } else {
                                togetherConfig.setCoverImageUrl(bFile.getUrl());
                            }
                        }else{
                            togetherConfig.setCoverImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
                        }
                    }
                /*}*/

            }
        }
        return list;
    }

    @Override
    public List<TogetherConfig> queryDetailByProjectId(Integer projectId,
                                                       int pageNum, int pageSize) {
        logger.info("query DetailByProjectId projectId:" + projectId);
        PageHelper.startPage(pageNum, pageSize);
        List<TogetherConfig> list = togetherConfigMapper.queryDetailByProjectId(projectId);
        String resUrl = ConfigLoader.getResPictrueURL();
        if (list != null && list.size() > 0) {
            for (TogetherConfig togetherConfig : list) {
                if (togetherConfig.getCoverImageId() != null) {
                    BFile bFile = bFileMapper.queryById(togetherConfig.getCoverImageId());
                    if (bFile != null && bFile.getUrl() != null) {
                        if (!bFile.getUrl().contains("http://")) {
                            togetherConfig.setCoverImageUrl(resUrl + bFile.getUrl());
                        } else {
                            togetherConfig.setCoverImageUrl(bFile.getUrl());
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<TogetherConfig> queryTogetherDonateListByProjectId(
            Integer projectId, int pageNum, int pageSize) {
        logger.info("query TogetherDonateListByProjectId projectId:" + projectId);
        PageHelper.startPage(pageNum, pageSize);
        List<TogetherConfig> list = togetherConfigMapper.queryTogetherDonateListByProjectId(projectId);
        String resUrl = ConfigLoader.getResPictrueURL();
        if (list != null && list.size() > 0) {
            for (TogetherConfig togetherConfig : list) {
                if (togetherConfig.getCoverImageId() != null) {
                    BFile bFile = bFileMapper.queryById(togetherConfig.getCoverImageId());
                    if (bFile != null && bFile.getUrl() != null) {
                        if (!bFile.getUrl().contains("http://")) {
                            togetherConfig.setCoverImageUrl(resUrl + bFile.getUrl());
                        } else {
                            togetherConfig.setCoverImageUrl(bFile.getUrl());
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<TogetherConfig> oneApiTogetherConfigTotal(TogetherConfig togetherConfig) {

        return togetherConfigMapper.oneApiTogetherConfigTotal(togetherConfig);
    }

    @Override
    public TogetherConfig countTogetherDonateMoneyAndDonateNumberByProjectId(Integer projectId) {
        logger.info("query countTogetherDonateMoneyAndDonateNumberByProjectId projectId:" + projectId);
        TogetherConfig togetherConfig = togetherConfigMapper.countTogetherDonateMoneyAndDonateNumberByProjectId(projectId);
        return togetherConfig;
    }

	
	@Override
	public List<TogetherConfig> queryByParamForMoney(TogetherConfig param, int pageNum,
			int pageSize) {
		logger.info("query TogetherConfig param:" + param);
        PageHelper.startPage(pageNum, pageSize);
        List<TogetherConfig> list = togetherConfigMapper.queryByParamForMoney(param);
        String resUrl = ConfigLoader.getResPictrueURL();
        if(list != null && list.size() > 0){
        for (TogetherConfig togetherConfig : list) {
			if(togetherConfig.getCoverImageId()!=null){
				BFile bFile = bFileMapper.queryById(togetherConfig.getCoverImageId());
				if(bFile!=null && bFile.getUrl()!=null){
					if(!bFile.getUrl().contains("http://")){
						togetherConfig.setCoverImageUrl(resUrl + bFile.getUrl());
					}else{
						togetherConfig.setCoverImageUrl(bFile.getUrl());
					}
				}
			}
        }}
        return list;
	}
}
