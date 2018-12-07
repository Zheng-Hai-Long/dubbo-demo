package com.guangde.business.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 读取配置文件
 * 
 * @author phx
 * 
 */
public final class ConfigLoader
{
    private static Logger logger = Logger.getLogger(ConfigLoader.class);
    
    private static ConfigLoader config = new ConfigLoader();
    
    private String file = "config.properties";
    
    private Properties p = new Properties();
    
    private ConfigLoader()
    {
        init();
    }
    
    private void init()
    {
        InputStream in = null;
        try
        {
            in = this.getClass().getClassLoader().getResourceAsStream(file);
            p.load(in);
        }
        catch (IOException e)
        {
            logger.error("load fail file:" + file, e);
        }
        finally
        {
            if (null != in)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    
                }
            }
        }
    }
    
    /**
     * 读取配置文件的值
     * 
     * @param key
     * @return 配置文件的值
     */
    public String getValue(String key)
    {
        if (StringUtils.isEmpty(key))
        {
            return null;
        }
        return p.getProperty(key);
    }
    
    /**
     * 读取配置文件的值
     * @param key
     * @return
     */
    public int getIntValue(String key)
    {
        String value = getValue(key);
        
        if (StrUtil.isInt(value))
        {
            return Integer.parseInt(value);
        }
        
        return -1;
    }
    
    /**
     * 单例
     * 
     * @return
     */
    public static ConfigLoader getSingle()
    {
        return config;
    }

    public static String getResPictrueURL()
    {
        String url = getSingle().getValue(Constant.Config.res_url_pic);
        
        return url == null ? "" : url;
    }
    
    public static String getFileUploadPath()
    {
        String url = getSingle().getValue(Constant.Config.fileStoragePath);
        
        return url == null ? "" : url;
    }

}
