package com.guangde.business.service.commons.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guangde.business.dao.AnnounceMapper;
import com.guangde.business.entry.Announce;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.sms.SmsSenderService;
import com.guangde.business.util.DateUtil;
import com.guangde.business.util.ResultCode;

@Service("announceService")
public class AnnounceService extends BaseService
{
    public static final ExecutorService pool = Executors.newCachedThreadPool();
    
    @Autowired
    private AnnounceMapper announceMapper;
    
    @Autowired
    private SmsSenderService ymSmsSenderService;
    
    /**
     * @param announce
     * @param isDelay 是否延迟发送
     * @return
     * @throws BaseException 
     */
    public boolean saveAnnounce(final Announce announce, boolean isDelay)
        throws BaseException
    {
        boolean ret = false;
        
        announce.setState(100);
        announce.setSendType("ym");
        announce.setSendTime(DateUtil.getDate());
        
        Thread t = new Thread()
        {
            public void run()
            {
                logger.info("  send sm  >>  start ");
                ymSmsSenderService.send(announce);
                logger.info("  send sm  >>  end ");
            }
        };
        pool.execute(t);
        
        try
        {
            int n = announceMapper.save(announce);
            ret = n == 1 ? true : false;
        }
        catch (Exception e)
        {
            logger.error("update database error.", e);
            throw new BaseException(ResultCode.Error);
        }
        
        return ret;
    }
}
