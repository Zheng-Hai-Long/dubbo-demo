package com.guangde.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guangde.business.dao.BFileMapper;
import com.guangde.business.entry.BFile;
import com.guangde.business.pojo.FocusMap;
import com.guangde.business.service.BFileService;
import com.guangde.business.service.BaseService;
import com.guangde.business.util.ConfigCache;
import com.guangde.business.util.ConfigLoader;
import com.guangde.business.util.Constant;
import com.guangde.business.util.StrUtil;

@Service("bFileService")
public class BFileServiceImpl extends BaseService implements BFileService
{
    
    @Autowired
    private BFileMapper bFileMapper;
    
    @Override
    public List<FocusMap> queryFocusMap()
    {
        
        String fileBasicURL = ConfigLoader.getSingle().getValue(Constant.Config.fileStoragePath);
        //文件表的ID,跳转URL,标题;
        String value = ConfigCache.getSingle().getConfigValue(Constant.ConfigParam.focusMap);
        if (StringUtils.isEmpty(value))
        {
            return null;
        }
        
        String[] values = value.split("\\;");
        List<FocusMap> list = new ArrayList<FocusMap>();
        FocusMap focusmap = null;
        for (String str : values)
        {
            String[] fm = str.split("\\,");
            if (3 == fm.length)
            {
                if (!StrUtil.isInt(fm[0]) || StringUtils.isEmpty(fm[1]) || StringUtils.isEmpty(fm[2]))
                {
                    continue;
                }
                
                BFile bfile = bFileMapper.queryById(Integer.parseInt(fm[0]));
                if (null == bfile)
                {
                    continue;
                }
                
                focusmap = new FocusMap();
                focusmap.setPictureURL(fileBasicURL + bfile.getUrl());
                focusmap.setJumpURL(fm[1]);
                focusmap.setTitle(fm[2]);
                list.add(focusmap);
            }
        }
        
        return list;
    }
    
}
