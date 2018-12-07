package com.guangde.business.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.guangde.business.entry.Config;
import com.guangde.business.service.ConfigService;

public final class ConfigCache
{
    
    private static ConfigCache cache = new ConfigCache();
    
    private ConfigService configService = SpringContextUtil.getBean("configService", ConfigService.class);
    
    private Map<String, Config> map = new HashMap<String, Config>();
    
    private ConfigCache()
    {
        init();
    }
    
    private void init()
    {
        List<Config> list = configService.queryAll();
        if (null != list && !list.isEmpty())
        {
            for (Config config : list)
            {
                map.put(config.getConfigKey(), config);
            }
        }
    }
    
    public static ConfigCache getSingle()
    {
        return cache;
    }
    
    public String getConfigValue(String key)
    {
        if (StringUtils.isNotEmpty(key))
        {
            Config config = map.get(key);
            if (null != config)
            {
                return config.getConfigValue();
            }
        }
        
        return null;
    }
    
    public Config getConfig(String key)
    {
        if (StringUtils.isNotEmpty(key))
        {
            return map.get(key);
        }
        
        return null;
    }
    
}
