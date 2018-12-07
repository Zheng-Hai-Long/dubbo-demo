package com.guangde.api.commons;

import com.guangde.entry.*;
import com.guangde.pojo.ApiResult;

import java.util.List;

public interface ICommonFacade
{
    /**
     * 发送短信
     * 
     * @param isDelay 是否延迟发送
     * @return
     */
    public ApiResult sendSms(ApiAnnounce apiAnnounce, boolean isDelay);
    
    /**
     * 求救类型
     */
    public List<ApiTypeConfig> queryList();
    
    /**
     *取数据库的系统参数表数据
     */
    public List<ApiConfig> queryList(ApiConfig apiConfig);
    /**
     * 查找求救详情
     * @param apiTypeConfig
     * @return
     */
    public ApiTypeConfig queryApiTypeConfig(ApiTypeConfig apiTypeConfig);
    
    /**
     * 文件列表
     * @param apiBfile
     * @return
     */
    public List<ApiBFile> queryApiBfile(ApiBFile apiBfile);
    
    /**
     * 查找文件详情
     * @param id
     * @return
     */
    public ApiBFile queryBFileById(Integer id);

    /**
     * 记录日志
     * @param param
     * @return
     */
    public ApiResult saveSystemLog(ApiSystemLog param);
    
    public List<String> queryTypeConfigAndProjectTag(Integer id);
    
    public List<ApiProjectTag> queryProjectTagByParam(ApiProjectTag projectTag);
}
