package com.guangde.business.service.user;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.guangde.business.BaseTest;
import com.guangde.business.entry.ServiceLeaveWord;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.ServiceLeaveWordService;

public class ServiceLeaveWordServiceTest extends BaseTest{
	@Resource
	private ServiceLeaveWordService serviceLeaveWordService;
	
	@Test
	public void queryServiceLeaveWordlistTest(){
		
		ServiceLeaveWord slWord = new ServiceLeaveWord();
		slWord.setOrderBy("createTime");
		slWord.setOrderDirection("DESC");
		List<ServiceLeaveWord> list = serviceLeaveWordService.queryServiceLeaveWordlist(slWord, 0, 20);
		for (ServiceLeaveWord slw:list) {
			System.out.println(slw.getId()+":"+slw.getCreateTime());
		}
	}
	//@Test
	public void saveServiceLeaveWordTest(){
		ServiceLeaveWord sLeaveWord = new ServiceLeaveWord();
		sLeaveWord.setUserId(732);
		sLeaveWord.setReply("哈哈哈哈哈哈哈哈");
		sLeaveWord.setServiceId(35);
		sLeaveWord.setState(300);
		try {
			serviceLeaveWordService.saveServiceLeaveWord(sLeaveWord);
		} catch (BaseException e) {
			
			e.printStackTrace();
		}
	}

}
