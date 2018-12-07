package com.guangde.business.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.guangde.business.BaseTest;
import com.guangde.business.entry.BFile;
import com.guangde.business.entry.Config;
import com.guangde.business.entry.TypeConfig;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.commons.FileService;

public class FileServiceTest extends BaseTest
{
    @Resource
    private FileService fileService;
    
    //  @Test
    public void testDeleteFile()
    {
        try
        {
            fileService.deleteBFile(1);
        }
        catch (BaseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    
    //@Test
    public void testQueryFile()
    {
        BFile b = fileService.queryById(1919);
        System.out.println(b.getUrl());
    }
    
    // @Test
    public void testQueryList()
    {
        TypeConfig typeConfig = new TypeConfig();
        typeConfig.setTypeName("求助");
        List<Config> list = fileService.queryList(typeConfig);
        if (list != null)
        {
            System.out.println(list.size());
            for (Config c : list)
            {
                System.out.println(c.getConfigValue());
            }
        }
    }
    
    @Test
    public void updateTest()
    {
        BFile bfile = new BFile();
        bfile.setId(10);
        bfile.setDescription("test123");
        try
        {
            fileService.updateBfile(bfile);
        }
        catch (BaseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}