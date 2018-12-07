package com.guangde.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.guangde.business.entry.BFile;
import com.guangde.business.entry.TypeConfig;

public interface BFileMapper extends BaseMapper<BFile>
{
    List<BFile> queryByIdList(List<Integer> list);
    
    List<BFile> queryList(TypeConfig typeConfig);
    
    /**
     * 根据图片类型随机获取图片
     * @param fileType
     * @param category
     * @return
     */
     BFile randomPic(@Param("fileType") String fileType,@Param("category") String category);
}
