package com.guangde.api.commons;

import com.guangde.entry.ApiBFile;
import com.guangde.entry.ApiConfig;
import com.guangde.entry.ApiTypeConfig;
import com.guangde.pojo.ApiResult;

import java.io.InputStream;
import java.util.List;

public interface IFileFacade
{
    
    /**
     * 上传文件
     * @param type 文件类型(数据字典)
     * @param fileExtension(文件后缀名)
     * @param in 文件流
     * @return 上传结果(data：文件id;文件路径)
     */
    public ApiResult upload(String type, String fileExtension, InputStream in);

    /**
     * 上传文件
     * @param type 文件类型(数据字典)
     * @param fileExtension(文件后缀名)
     * @param in 文件流
     * @return 上传结果(data：文件id;文件路径)
     */
    public ApiResult uploadHome(String type, String fileExtension, InputStream in);
    
    /**
     * 删除文件
     * @param id
     * @return
     */
    public ApiResult deleteBfile(Integer id);

    /**
     * 批量删除文件
     * @param ids
     * @return
     */
    ApiResult deleteBfileList(List<Integer> ids);
    
    /**
     * 修改文件信息
     * @param apiBFile
     * @return
     */
    public ApiResult updateBfile(ApiBFile apiBFile);
    
    /**
     * 保存文件
     * @param apiBFile
     * @return
     */
    public ApiResult saveBfile(ApiBFile apiBFile);
    
    /**
     * 查找文件详情
     * @param id
     * @return
     */
    public ApiBFile queryBFileById(Integer id);
  
    public List<ApiConfig> queryApiConfigList(ApiTypeConfig apiTypeConfig);
   
    /**
     * 根据图片类型随机获取图片
     * @param fileType
     * @param category
     * @return
     */
    public ApiBFile randomPic(String fileType,String category);
    
}
