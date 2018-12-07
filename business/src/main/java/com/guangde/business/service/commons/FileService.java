package com.guangde.business.service.commons;

import com.guangde.business.entry.BFile;
import com.guangde.business.entry.Config;
import com.guangde.business.entry.TypeConfig;
import com.guangde.business.exception.BaseException;
import com.guangde.business.pojo.Result;

import java.io.InputStream;
import java.util.List;

public interface FileService
{
    /**
     * 上传文件
     * @param type 文件类型(数据字典)
     * @param fileExtension(文件后缀名)
     * @param in 文件流  ,Integer imageId,String picType
     * @return 上传结果(data：文件id;文件路径)
     */
    public Result upload(String type, String fileExtension, InputStream in);

    /**
     * 上传文件
     * @param type 文件类型(数据字典)
     * @param fileExtension(文件后缀名)
     * @param in 文件流  ,Integer imageId,String picType
     * @return 上传结果(data：文件id;文件路径)
     */
    public Result uploadHome(String type, String fileExtension, InputStream in);
    
    void deleteBFile(Integer id)
        throws BaseException;
    void deleteBFileList(List<Integer> ids)
            throws BaseException;
    
    BFile queryById(Integer id);
    
    void updateBfile(BFile bFile)
        throws BaseException;
    
    void saveBfile(BFile bFile)
            throws BaseException;
    
    List<Config> queryList(TypeConfig typeConfig);
    
    List<BFile> queryBFileList(BFile bfile);
    
    /**
     * 根据图片类型随机获取图片
     * @param fileType
     * @param category
     * @return
     */
    BFile randomPic(String fileType,String category);
}
