package com.guangde.business.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;

public final class FileUtil {
    
    private static Logger logger = Logger.getLogger(FileUtil.class);
    
	private FileUtil()
	{
		
	}
	
	/**
	 * 创建文件侠
	 * @param folderPath  文件路径
	 * @return true：创建成功    false:创建失败
	 */
	public static boolean createFolder(String folderPath)
	{
		try {
			File file = new File(folderPath);
			if(!file.exists() )
			{
				file.mkdirs();
			}
		} catch (Exception e) {
            logger.error("create folder fail", e);
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * 根据文件后缀名获得文件类型
	 * @param fileExtension  文件后缀名
	 * @return 数据字典中文件类型名
	 */
	public static String getFileType(String fileExtension)
	{
		return "picture";
	}
	
	public static String assemblyFilePath(String ... strs)
	{
		if(null == strs || 0 == strs.length)
		{
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < strs.length-1 ;i++)
		{
			sb.append(strs[i]);
			sb.append(File.separator);
		}
		
		return sb.toString()+strs[strs.length-1];
	}
	
    public static boolean writeFile(File file, InputStream in)
    {
        OutputStream out = null;
        int b = 0;
        
        try
        {
            out = new BufferedOutputStream(new FileOutputStream(file));
            while ((b = in.read()) != -1)
            {
                out.write(b);
            }
            out.flush();
            return true;

        }
        catch (FileNotFoundException e)
        {
            logger.error("create file error", e);
            return false;
        }
        catch (IOException e)
        {
            logger.error("write stream error", e);
            return false;
        }
        finally
        {
            if (null != out)
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                }
            }
        }
    }

    public static String getBasicFilePath(String path, String fileExtension)
    {
        String str = null;
        
        str = assemblyFilePath(path, getFileType(fileExtension));
        
        if (str == null)
        {
            str = path;
        }
        
        return str;
    }

    /**
     * 删除文件
     * @param path
     */
    public static void delete(List<String> path)
    {
        try
        {
            for (String p : path){
                File file = new File(p);
                if (file.exists())
                {
                    file.delete();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
