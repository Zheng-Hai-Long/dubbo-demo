package com.guangde.commons.facade.impl;

import com.guangde.api.commons.ICommonFacade;
import com.guangde.business.entry.*;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.ConfigService;
import com.guangde.business.service.ProjectTagService;
import com.guangde.business.service.commons.FileService;
import com.guangde.business.service.commons.SystemLogService;
import com.guangde.business.service.commons.TypeConfigService;
import com.guangde.business.service.commons.impl.AnnounceService;
import com.guangde.entry.*;
import com.guangde.pojo.ApiResult;
import com.guangde.util.ApiResultUtil;
import com.guangde.util.BeanUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CommonFacade")
public class CommonFacadeImpl implements ICommonFacade {
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private AnnounceService announceService;

    @Autowired
    private TypeConfigService typeConfigService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private SystemLogService systemLogService;
    
    @Autowired
    private ProjectTagService projectTagService;


    @Override
    public ApiResult sendSms(ApiAnnounce apiAnnounce, boolean isDelay) {
        logger.info("receive save param:" + apiAnnounce);

        Announce announce = BeanUtil.copy(apiAnnounce, Announce.class);

        if (!valParam(announce)) {
            return ApiResultUtil.getParameterError();
        }

        try {
            boolean ret = announceService.saveAnnounce(announce, isDelay);

            return ret ? ApiResultUtil.SUCCESS : ApiResultUtil.ERROR;
        } catch (BaseException e) {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }

    }

    private boolean valParam(Announce announce) {
        boolean ret = true;
        if (announce == null) {
            logger.info("announce is null");
            ret = false;
        } else if (announce.getDestination() == null) {
            logger.info("destination is null");
            ret = false;
        } else if (announce.getCause() == null) {
            logger.info("cause is null");
            ret = false;
        } else if (announce.getContent() == null) {
            logger.info("content is null");
            ret = false;
        } else if (announce.getType() == null) {
            logger.info("type is null");
            ret = false;
        } else if (announce.getPriority() == null) {
            logger.info("priority is null");
            ret = false;
        }

        return ret;
    }

    public List<ApiTypeConfig> queryList() {
        List<TypeConfig> list = typeConfigService.queryList();

        if (list != null && list.size() > 0) {
            for (TypeConfig tc : list) {
                if (tc.getCoverImageId() != null) {
                    BFile bFile = fileService.queryById(tc.getCoverImageId());
                    tc.setCoverImageUrl(bFile.getUrl());
                }
            }
        }
        List<ApiTypeConfig> ret = BeanUtil.copyList(list, ApiTypeConfig.class);

        return ret;
    }

    @Override
    public List<ApiConfig> queryList(ApiConfig apiConfig) {
        logger.info("query apiConfig param ： " + apiConfig);
        Config config = BeanUtil.copy(apiConfig, Config.class);
        List<Config> list = configService.queryList(config);
        if (list.size() > 0) {
            List<ApiConfig> ret = BeanUtil.copyList(list, ApiConfig.class);
            return ret;
        } else {
            return null;
        }
    }

    @Override
    public ApiTypeConfig queryApiTypeConfig(ApiTypeConfig apiTypeConfig) {
        logger.info("receive quertApiTypeConfig param ： " + apiTypeConfig);

        TypeConfig typeConfig = BeanUtil.copy(apiTypeConfig, TypeConfig.class);

        typeConfig = typeConfigService.queryTypeConfig(typeConfig);

        ApiTypeConfig ret = BeanUtil.copy(typeConfig, ApiTypeConfig.class);

        return ret;
    }

    @Override
    public List<ApiBFile> queryApiBfile(ApiBFile apiBfile) {

        logger.info("receive queryApiBfile param : apiBfile >> " + apiBfile);

        BFile bfile = BeanUtil.copy(apiBfile, BFile.class);

        List<BFile> list = fileService.queryBFileList(bfile);
        List<ApiBFile> ret = BeanUtil.copyList(list, ApiBFile.class);

        return ret;
    }

    @Override
    public ApiBFile queryBFileById(Integer id) {
        logger.info("receive queryBFileById param id = " + id);

        BFile file = fileService.queryById(id);

        ApiBFile ret = BeanUtil.copy(file, ApiBFile.class);

        return ret;
    }

    @Override
    public ApiResult saveSystemLog(ApiSystemLog param) {
        logger.info("receive saveSystemLog param = " + param);
        SystemLog systemLog = BeanUtil.copy(param, SystemLog.class);
        try {
            systemLogService.save(systemLog);
            ApiResult apiResult = ApiResultUtil.SUCCESS;

            return apiResult;
        } catch (BaseException e) {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }

	@Override
	public List<String> queryTypeConfigAndProjectTag(Integer id) {
		logger.info("receive queryTypeConfigAndProjectTag param id = " + id);

		List<String> list = typeConfigService.queryTypeConfigAndProjectTag(id);

        return list;
	}

	@Override
	public List<ApiProjectTag> queryProjectTagByParam(ApiProjectTag projectTag) {
		logger.info("query projectTag param ： " + projectTag);
		ProjectTag p = BeanUtil.copy(projectTag, ProjectTag.class);
        List<ProjectTag> list = projectTagService.queryProjectTagByParam(p);
        if (list.size() > 0) {
            List<ApiProjectTag> ret = BeanUtil.copyList(list, ApiProjectTag.class);
            return ret;
        } else {
            return null;
        }
	}
}
