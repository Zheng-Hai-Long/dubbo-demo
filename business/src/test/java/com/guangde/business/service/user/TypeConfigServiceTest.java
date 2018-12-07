package com.guangde.business.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.guangde.business.BaseTest;
import com.guangde.business.entry.TypeConfig;
import com.guangde.business.service.commons.TypeConfigService;

public class TypeConfigServiceTest extends BaseTest
{
    @Resource
    private TypeConfigService typeConfigService;
    
    //@Test
    public void queryListTest()
    {
        List<TypeConfig> list = typeConfigService.queryList();
        System.out.println(list.size());
    }
    
    @Test
    public void queryTest()
    {
        TypeConfig t = new TypeConfig();
        t.setTypeName("助学");
        TypeConfig typeConfig = typeConfigService.queryTypeConfig(t);
        System.out.println(typeConfig.getTypeName());
    }
}
