package com.guangde.business;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/context-business.xml")
@Rollback(value=false)
@Transactional(transactionManager = "transactionManager")
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests
{
    @Test
    public void test()
    {
        
    }
}
