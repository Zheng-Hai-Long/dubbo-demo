package com.guangde.business.service.commons.impl;

import com.guangde.business.dao.BFileMapper;
import com.guangde.business.dao.ConfigMapper;
import com.guangde.business.dao.TypeConfigMapper;
import com.guangde.business.entry.BFile;
import com.guangde.business.entry.Config;
import com.guangde.business.entry.TypeConfig;
import com.guangde.business.enums.ResultEnum;
import com.guangde.business.exception.BaseException;
import com.guangde.business.pojo.Result;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.commons.FileService;
import com.guangde.business.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("fileService")
public class FileServiceImpl extends BaseService implements FileService
{
    @Autowired
    private BFileMapper bFileMapper;
    
    @Autowired
    private TypeConfigMapper typeConfigMapper;
    
    @Autowired
    private ConfigMapper configMapper;
    
    @Override
    public Result upload(String type, String fileExtension, InputStream in)
    {
    	InputStream in2  = null ;
    	
    	InputStream in3 = null ;
    	
    	Result result = new Result();
    
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(fileExtension) || null == in)
        {
            result.setResultCode(ResultCode.ParameterError);
            return result;
        }
        
        logger.info("file upload beging");
        
        String fileBasicURL = FileUtil.getBasicFilePath(ConfigLoader.getFileUploadPath(), fileExtension);
        
        logger.info("fileBasicURL:" + fileBasicURL);
        
        String dateFileName = DateUtil.format(new Date(), DateUtil.C_DATA_PATTON_YYYYMMDD);
        
        String filePath = FileUtil.assemblyFilePath(fileBasicURL, type, dateFileName);
        
        logger.info("file upload filePath:" + filePath);
        
        boolean creFile = FileUtil.createFolder(filePath);
        
        if (!creFile)
        {
            result.setResultCode(ResultCode.SystemError);
            return result;
        }
        
        String fileName = String.valueOf(System.currentTimeMillis()) + "_" + (int)(Math.random() * 1000) + "." + fileExtension;
        
        logger.info("file upload fileName:" + fileName);
        
        boolean ret = FileUtil.writeFile(new File(filePath + File.separator + fileName), in);
        
        if (!ret)
        {
            result.setResultCode(ResultCode.SystemError);
            return result;
        }
        
        String fileName_middle = String.valueOf(System.currentTimeMillis()) + "_" + (int)(Math.random() * 1000) + "." + fileExtension;
        
        logger.info("file upload fileName_middle:" + fileName_middle);
        try {
			in2= new FileInputStream(filePath + File.separator + fileName);
		} catch (FileNotFoundException e) {
			logger.error("image not find for middle");
		}
        if (null == in2)
        {
            result.setResultCode(ResultCode.ParameterError);
            return result;
        }
        
        ret = CompressPicUtil.compressPic(in2, new File(filePath + File.separator + fileName_middle),"middle");
        
        if (!ret)
        {
            result.setResultCode(ResultCode.SystemError);
            return result;
        }
        
        
        String fileName_litter = String.valueOf(System.currentTimeMillis()) + "_" + (int)(Math.random() * 1000) + "." + fileExtension;
        
        logger.info("file upload fileName_litter:" + fileName_litter);
        
        try {
			in3= new FileInputStream(filePath + File.separator + fileName);
		} catch (FileNotFoundException e) {
			logger.error("image not find for litter");
		}
        
        if (null == in3)
        {
            result.setResultCode(ResultCode.ParameterError);
            return result;
        }
        
        ret = CompressPicUtil.compressPic(in3, new File(filePath + File.separator + fileName_litter),"litter");
        
        if (!ret)
        {
            result.setResultCode(ResultCode.SystemError);
            return result;
        }
        
        
        logger.info("file upload success.");
    
        	
        	BFile bfile = new BFile();
        	bfile.setFileType(FileUtil.getFileType(fileExtension));
        	bfile.setCategory(type);
        	bfile.setUrl(type + File.separator + dateFileName + File.separator + fileName);
        	bfile.setMiddleUrl(type + File.separator + dateFileName + File.separator + fileName_middle);
        	bfile.setLitterUrl(type + File.separator + dateFileName + File.separator + fileName_litter);
        	bfile.setIsHide(0);
        	bFileMapper.save(bfile);
        	
        	result.setResultCode(ResultCode.Success);
            result.setData(bfile.getId() + ";" + ConfigLoader.getResPictrueURL() + File.separator + bfile.getUrl());
    
        return result;
    }

    @Override
    public Result uploadHome(String type, String fileExtension, InputStream in)
    {
        InputStream in2  = null ;

        InputStream in3 = null ;

        Result result = new Result();

        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(fileExtension) || null == in)
        {
            result.setResultCode(ResultCode.ParameterError);
            return result;
        }

        logger.info("file upload beging");

        String fileBasicURL = "/mnt/usr/local/www.17xs.org/home/upload/picture";//FileUtil.getBasicFilePath(ConfigLoader.getFileUploadPath(), fileExtension);

        logger.info("fileBasicURL:" + fileBasicURL);

        String dateFileName = DateUtil.format(new Date(), DateUtil.C_DATA_PATTON_YYYYMMDD);

        String filePath = FileUtil.assemblyFilePath(fileBasicURL, type, dateFileName);

        logger.info("file upload filePath:" + filePath);

        boolean creFile = FileUtil.createFolder(filePath);

        if (!creFile)
        {
            result.setResultCode(ResultCode.SystemError);
            return result;
        }

        String fileName = String.valueOf(System.currentTimeMillis()) + "_" + (int)(Math.random() * 1000) + "." + fileExtension;

        logger.info("file upload fileName:" + fileName);

        boolean ret = FileUtil.writeFile(new File(filePath + File.separator + fileName), in);

        if (!ret)
        {
            result.setResultCode(ResultCode.SystemError);
            return result;
        }

        String fileName_middle = String.valueOf(System.currentTimeMillis()) + "_" + (int)(Math.random() * 1000) + "." + fileExtension;

        logger.info("file upload fileName_middle:" + fileName_middle);
        try {
            in2= new FileInputStream(filePath + File.separator + fileName);
        } catch (FileNotFoundException e) {
            logger.error("image not find for middle");
        }
        if (null == in2)
        {
            result.setResultCode(ResultCode.ParameterError);
            return result;
        }

        ret = CompressPicUtil.compressPic(in2, new File(filePath + File.separator + fileName_middle),"middle");

        if (!ret)
        {
            result.setResultCode(ResultCode.SystemError);
            return result;
        }


        String fileName_litter = String.valueOf(System.currentTimeMillis()) + "_" + (int)(Math.random() * 1000) + "." + fileExtension;

        logger.info("file upload fileName_litter:" + fileName_litter);

        try {
            in3= new FileInputStream(filePath + File.separator + fileName);
        } catch (FileNotFoundException e) {
            logger.error("image not find for litter");
        }

        if (null == in3)
        {
            result.setResultCode(ResultCode.ParameterError);
            return result;
        }

        //ret = FileUtil.writeFile(new File(filePath + File.separator + fileName_litter), in3);
        ret = CompressPicUtil.compressPic(in3, new File(filePath + File.separator + fileName_litter),"litter");

        if (!ret)
        {
            result.setResultCode(ResultCode.SystemError);
            return result;
        }


        logger.info("file upload success.");


        BFile bfile = new BFile();
        bfile.setFileType(FileUtil.getFileType(fileExtension));
        bfile.setCategory(type);
        bfile.setUrl(type + File.separator + dateFileName + File.separator + fileName);
        bfile.setMiddleUrl(type + File.separator + dateFileName + File.separator + fileName_middle);
        bfile.setLitterUrl(type + File.separator + dateFileName + File.separator + fileName_litter);
        bfile.setIsHide(0);
        bFileMapper.save(bfile);

        result.setResultCode(ResultCode.Success);
        result.setData(bfile.getId() + ";https://www.17xs.org" + File.separator  + "upload" + File.separator + "picture"+ File.separator + bfile.getUrl());

        return result;
    }

    @Override
    public void deleteBFile(Integer id)
            throws BaseException
    {
        String realPath = "";
        try
        {
            BFile bFile = bFileMapper.queryById(id);
            List<String> paths = new ArrayList<>();
            if(bFile != null){
                if(org.apache.commons.lang.StringUtils.isNotBlank(bFile.getUrl())){
                    paths.add(realPath + bFile.getUrl());
                }
                if(org.apache.commons.lang.StringUtils.isNotBlank(bFile.getMiddleUrl())){
                    paths.add(realPath + bFile.getMiddleUrl());
                }
                if(org.apache.commons.lang.StringUtils.isNotBlank(bFile.getLitterUrl())){
                    paths.add(realPath + bFile.getLitterUrl());
                }
                FileUtil.delete(paths);
            }
            bFileMapper.delete(id);
        }
        catch (Exception e)
        {
            throw new BaseException(ResultEnum.Error);
        }
    }

    @Override
    public void deleteBFileList(List<Integer> ids) throws BaseException {
        String realPath = "";
        try
        {
            for (Integer id : ids) {
                BFile bFile = bFileMapper.queryById(id);
                List<String> paths = new ArrayList<>();
                if(bFile != null){
                    if(org.apache.commons.lang.StringUtils.isNotBlank(bFile.getUrl())){
                        paths.add(realPath + bFile.getUrl());
                    }
                    if(org.apache.commons.lang.StringUtils.isNotBlank(bFile.getMiddleUrl())){
                        paths.add(realPath + bFile.getMiddleUrl());
                    }
                    if(org.apache.commons.lang.StringUtils.isNotBlank(bFile.getLitterUrl())){
                        paths.add(realPath + bFile.getLitterUrl());
                    }
                    FileUtil.delete(paths);
                }
                bFileMapper.delete(id);
            }
        }
        catch (Exception e)
        {
            throw new BaseException(ResultEnum.Error);
        }
    }
    
    @Override
    public BFile queryById(Integer id)
    {
        BFile file = bFileMapper.queryById(id);
        if(null != file)
        {
        	if(!"weixinImage".equals(file.getCategory()==null?"":file.getCategory()))
        	{
        		String resUrl = ConfigLoader.getResPictrueURL();
        		file.setUrl(resUrl + file.getUrl());
        	}
        }
        return file;
    }
    
    @Override
    public List<Config> queryList(TypeConfig typeConfig)
    {
        TypeConfig tc = typeConfigMapper.queryTypeConfig(typeConfig);
        if (tc == null || StringUtils.isEmpty(tc.getNeeddata()))
        {
            return null;
        }
        String[] idList = tc.getNeeddata().replaceAll("，", ",").split(",");
        List<Integer> list = new ArrayList<Integer>();
        for (String str : idList)
        {
            if (!StringUtils.isEmpty(str))
            {
                list.add(Integer.parseInt(str));
            }
        }
        List<Config> configList = configMapper.queryByIdList(list);
        
        return configList;
    }
    
    @Override
    public void updateBfile(BFile bFile)
        throws BaseException
    {
        try
        {
            
            bFileMapper.update(bFile);
        }
        catch (Exception e)
        {
            throw new BaseException(ResultCode.Error);
        }
    }
    
    @Override
    public void saveBfile(BFile bFile)
        throws BaseException
    {
        try
        {
            
            bFileMapper.save(bFile);
        }
        catch (Exception e)
        {
            throw new BaseException(ResultCode.Error);
        }
    }

	@Override
	public List<BFile> queryBFileList(BFile bfile) {
		
		List<BFile> list =  bFileMapper.queryByParam(bfile);
		Config config = new Config();
		String resUrl = ConfigLoader.getResPictrueURL();
 		for(BFile b :list){
 			config.setConfigKey(b.getId()+"");
 			List<Config> cs = configMapper.queryByParam(config);
 			if(cs!=null && cs.size()>0){
 				Config c = cs.get(0);
 				b.setLinkUrl(c.getConfigValue());
 			}
 			if(!StringUtils.isEmpty(b.getMiddleUrl())){
				b.setMiddleUrl(resUrl+b.getMiddleUrl());
			}
			if(!StringUtils.isEmpty(b.getLitterUrl())){
				b.setLitterUrl(resUrl+b.getLitterUrl());
			}
			if(!StringUtils.isEmpty(b.getUrl())){
				b.setUrl(resUrl+b.getUrl());
			}
 		}
 		
		return list;
	}
	
	@Override
    public BFile randomPic(String fileType,String category)
    {
        BFile file = bFileMapper.randomPic(fileType,category);
        if(null != file)
        {
        	if(!"weixinImage".equals(file.getCategory()==null?"":file.getCategory()))
        	{
        		String resUrl = ConfigLoader.getResPictrueURL();
        		file.setUrl(resUrl + file.getUrl());
        	}
        }
        return file;
    }
}
