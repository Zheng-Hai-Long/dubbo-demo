package com.guangde.commons.facade.impl;

import com.guangde.api.commons.IFileFacade;
import com.guangde.business.entry.BFile;
import com.guangde.business.entry.Config;
import com.guangde.business.entry.TypeConfig;
import com.guangde.business.exception.BaseException;
import com.guangde.business.pojo.Result;
import com.guangde.business.service.commons.FileService;
import com.guangde.entry.ApiBFile;
import com.guangde.entry.ApiConfig;
import com.guangde.entry.ApiTypeConfig;
import com.guangde.pojo.ApiResult;
import com.guangde.util.ApiResultUtil;
import com.guangde.util.BeanUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service("fileFacade")
public class FileFacadeImpl implements IFileFacade
{
    
    private static Logger logger = Logger.getLogger(FileFacadeImpl.class);
    
    @Autowired
    private FileService fileService;
    
    @Override
    public ApiResult upload(String type, String fileExtension, InputStream in)
    {
      
        logger.info("receive file upload msg, type:" + type + "fileExtension:" + fileExtension);
      
        Result resultBuss = fileService.upload(type, fileExtension, in);
        if (null == resultBuss)
        {
            return null;
        }
        
        ApiResult result = BeanUtil.copy(resultBuss, ApiResult.class);
        
        return result;
    }

    @Override
    public ApiResult uploadHome(String type, String fileExtension, InputStream in)
    {

        logger.info("receive file upload msg, type:" + type + "fileExtension:" + fileExtension);

        Result resultBuss = fileService.uploadHome(type, fileExtension, in);
        if (null == resultBuss)
        {
            return null;
        }

        ApiResult result = BeanUtil.copy(resultBuss, ApiResult.class);

        return result;
    }
    
    @Override
    public ApiResult deleteBfile(Integer id)
    {
        logger.info(" deleteBfile id = " + id);
        if (id == null)
        {
            return ApiResultUtil.getParameterError();
        }
        try
        {
            fileService.deleteBFile(id);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }

    @Override
    public ApiResult deleteBfileList(List<Integer> ids) {
        logger.info(" deleteBfile ids = " + ids);
        if (ids == null)
        {
            return ApiResultUtil.getParameterError();
        }
        try
        {
            fileService.deleteBFileList(ids);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }

    @Override
    public List<ApiConfig> queryApiConfigList(ApiTypeConfig apiTypeConfig)
    {
        logger.info("receive queryBfileList param : apiTypeConfig >> " + apiTypeConfig);
        
        TypeConfig typeConfig = BeanUtil.copy(apiTypeConfig, TypeConfig.class);
        
        List<Config> list = fileService.queryList(typeConfig);
        List<ApiConfig> ret = BeanUtil.copyList(list, ApiConfig.class);
        
        return ret;
    }
    
    @Override
    public ApiResult updateBfile(ApiBFile apiBfile)
    {
        logger.info("receive updateBfile param : apiBfile = " + apiBfile);
        
        BFile bfile = BeanUtil.copy(apiBfile, BFile.class);
        
        try
        {
            fileService.updateBfile(bfile);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }
    
    @Override
    public ApiResult saveBfile(ApiBFile apiBfile)
    {
        logger.info("receive saveBfile param : apiBfile = " + apiBfile);
        
        BFile bfile = BeanUtil.copy(apiBfile, BFile.class);
        
        try
        {
            fileService.saveBfile(bfile);
            ApiResult result = new ApiResult();
            result.setCode(1);
            //result.setMessage(bfile.getId() + "");
            return result;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }
    
    @Override
    public ApiBFile queryBFileById(Integer id)
    {
        logger.info("receive queryBFileById param id = " + id);
        
        BFile file = fileService.queryById(id);
        
        ApiBFile ret = BeanUtil.copy(file, ApiBFile.class);
        return ret;
    }
    
    @Override
    public ApiBFile randomPic(String fileType,String category)
    {
        logger.info("receive randomPic param fileType = " + fileType+", category="+category);
        
        BFile file = fileService.randomPic(fileType,category);
        
        ApiBFile ret = BeanUtil.copy(file, ApiBFile.class);
        return ret;
    }


}
