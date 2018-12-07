package com.guangde.business.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.FrontUserAddressMapper;
import com.guangde.business.dao.FrontUserMapper;
import com.guangde.business.entry.FrontUser;
import com.guangde.business.entry.FrontUser_address;
import com.guangde.business.enums.ResultEnum;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.user.IUserAddressService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("userAddressService")
public class UserAddressServiceImpl extends BaseService implements IUserAddressService
{
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private FrontUserAddressMapper frontUserAddresssMapper;

    @Autowired
    private FrontUserMapper userMapper;

	@Override
	public FrontUser_address queryUserAddress(Integer id, Integer userId) {
		return frontUserAddresssMapper.queryUserAddress(id, userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public int saveUserAddress(FrontUser_address userAddress) throws BaseException {
		userAddress.setCreateTime(new Date());
		userAddress.setLastUpdateTime(new Date());

		//取消其他地址为不默认
		int flag = 0;
		int count = frontUserAddresssMapper.countUserAddress(userAddress.getUserId());
		if(count > 0) {
			if (userAddress.getIsSelected().equals(1)) {
				flag = frontUserAddresssMapper.cancelDefaultAddress(userAddress.getUserId());
				if (flag <= 0) {
					logger.info("用户id：" + userAddress.getUserId() + "，cancel other default address error");
					throw new BaseException(ResultEnum.Error);
				}
			}
		}
		flag = frontUserAddresssMapper.save(userAddress);
		if(flag <= 0){
			logger.info("用户id："+ userAddress.getUserId() +"，save address error");
			throw new BaseException(ResultEnum.Error);
		}

		FrontUser user = userMapper.queryById(userAddress.getUserId());
		if(user == null){
			logger.info("用户id："+ userAddress.getUserId() +"，用户不存在");
			throw new BaseException(ResultEnum.NotExistUser);
		}

		if(StringUtils.isBlank(user.getMobileNum())){
			FrontUser user2 = new FrontUser();
			user2.setId(user.getId());
			user2.setMobileNum(userAddress.getMobile());
			flag = userMapper.update(user2);
			if(flag <= 0){
				logger.info("用户id："+ userAddress.getUserId() +"，添加用户手机号error");
				throw new BaseException(ResultEnum.Error);
			}
		}
		return userAddress.getId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public void updateUserAddress(FrontUser_address userAddress) throws BaseException {

		//取消其他地址为不默认
		int flag = 0;
		int count = frontUserAddresssMapper.countUserAddress(userAddress.getUserId());
		if(count > 0) {
			if (userAddress.getIsSelected().equals(1)) {
				 frontUserAddresssMapper.cancelDefaultAddress(userAddress.getUserId());
			}
		}
		userAddress.setLastUpdateTime(new Date());
		flag = frontUserAddresssMapper.update(userAddress);
		if(flag <= 0){
			logger.info("地址id："+ userAddress.getId() +"，更新地址error");
			throw new BaseException(ResultEnum.Error);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public void deleteUserAddress(List<String> idList, Integer userId)
			throws BaseException {
		for (String id : idList) {
			int res = frontUserAddresssMapper.deleteByIdAndUserId(Integer.valueOf(id) , userId);
			if(res <= 0){
				logger.info("deleteUserAddress id = " + id + "， error");
				throw new BaseException(ResultEnum.Error);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public void defaultUserAddress(Integer id, Integer userId) throws BaseException {
		//取消其他地址为不默认
		int res = 0;
		int count = frontUserAddresssMapper.countUserAddress(userId);
		if(count > 0) {
			res = frontUserAddresssMapper.cancelDefaultAddress(userId);
			if(res <= 0){
				logger.info("cancel defaultUserAddress userId = " + userId + "， error");
				throw new BaseException(ResultEnum.Error);
			}
		}
		res = frontUserAddresssMapper.defaultUserAddress(id, userId);
		if(res <= 0){
			logger.info("cancel defaultUserAddress userId = " + userId + "，id = "+ id +"， error");
			throw new BaseException(ResultEnum.Error);
		}
	}

	@Override
	public List<FrontUser_address> queryByParam(FrontUser_address userAddress, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<FrontUser_address> frontUserAddressList = frontUserAddresssMapper.queryByParam(userAddress);
		return frontUserAddressList;
	}

	@Override
	public int countByUserId(Integer userId) {
		return frontUserAddresssMapper.countByUserId(userId);
	}
}
