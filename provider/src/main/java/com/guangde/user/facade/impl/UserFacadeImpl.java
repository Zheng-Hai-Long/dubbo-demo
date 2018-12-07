package com.guangde.user.facade.impl;

import com.github.pagehelper.Page;
import com.guangde.api.user.IUserFacade;
import com.guangde.business.entry.*;
import com.guangde.business.exception.BaseException;
import com.guangde.business.pojo.Result;
import com.guangde.business.service.AuditProjectService;
import com.guangde.business.service.WeixinModelService;
import com.guangde.business.service.commons.FileService;
import com.guangde.business.service.user.IAuditStaffService;
import com.guangde.business.service.user.IUserAddressService;
import com.guangde.business.service.user.IUserCardService;
import com.guangde.business.service.user.IUserService;
import com.guangde.dto.ApiUserSetDTO;
import com.guangde.dto.UserDTO;
import com.guangde.entry.*;
import com.guangde.enums.ResultEnum;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.guangde.util.ApiResultUtil;
import com.guangde.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Service("userFacade")
public class UserFacadeImpl implements IUserFacade
{
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    public IUserService userService;
    
    @Autowired
    public IAuditStaffService auditStaffService;
    
    @Autowired
    public IUserCardService userCardService;
    

    @Autowired
    private FileService fileService;
    
    @Autowired
    private AuditProjectService auditProjectService;
    
    @Autowired
    private WeixinModelService weixinModelService;

    @Autowired
    private IUserAddressService userAddressService;

    @Override
    public ApiFrontUser queryByMobile(String mobileNum) {
        try {
            FrontUser user = userService.queryByMobile(mobileNum);
            ApiFrontUser newUser = BeanUtil.copy(user, ApiFrontUser.class);
            return newUser;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ApiFrontUser queryOneUserByParam(ApiFrontUser model) {
        logger.info("queryOneUserByParam param user = "+ model);
        try {
            FrontUser user = BeanUtil.copy(model, FrontUser.class);
            List<FrontUser> list = userService.queryByParam(user);
            if(list != null && list.size() > 0){
                ApiFrontUser newUser = BeanUtil.copy(list.get(0), ApiFrontUser.class);
                return newUser;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ApiResult queryThirdUserByParam(String unionId) {
        ApiResult result = new ApiResult();
        try {
            List<ThirdUser> thirdUsers = userService.queryThirdUserByParam(unionId);
            List<ApiThirdUser> apiThirdUsers = BeanUtil.copyList(thirdUsers, ApiThirdUser.class);
            if(apiThirdUsers == null || apiThirdUsers.size() ==0){//需注册
                result.setCode(3);
            }else if(apiThirdUsers != null && apiThirdUsers.size() == 1) {//注册
                result.setCode(1);
                result.setData(apiThirdUsers.get(0));
            }else{//用户错误，查出多个
                result.setCode(2);
            }
        }catch (BaseException e){
            e.printStackTrace();
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
        return result;
    }

    @Override
    public ApiResult localUserlogin(ApiFrontUser user)
    {
        if (null == user)
        {
            return null;
        }
        
        FrontUser fUser = BeanUtil.copy(user, FrontUser.class);
        if (null == fUser)
        {
            return null;
        }
        
        Result resultBuss = userService.login(fUser, null);
        if (null == resultBuss)
        {
            return null;
        }
        
        ApiResult result = BeanUtil.copy(resultBuss, ApiResult.class);
      /*  FrontUser newF = (FrontUser)result.getObject();
        ApiFrontUser apiNewF = BeanUtil.copy(newF, ApiFrontUser.class);
        result.setObject(apiNewF);*/
        
        return result;
    }
    
    @Override
    public ApiResult registered(ApiFrontUser user) {
        logger.info("receive register param : " + user);
        try
        {
            FrontUser model = BeanUtil.copy(user, FrontUser.class);
            Integer res = userService.register(model);
            if(res > 0) {
                return new ApiResult(ResultEnum.Success, res);
            }
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
        return new ApiResult(ResultEnum.Error);
    }
    
    
    @Override
    public ApiResult thirdUserRegistered(ApiFrontUser user ,ApiThirdUser tuser)
    {
        logger.info("thirdUserRegistered ApiFrontUser = "+ user +", ApiThirdUser = " + tuser);
        if (null == user || null == tuser)
        {
            return new ApiResult(ResultEnum.ParameterError);
        }
        
        FrontUser fUser = BeanUtil.copy(user, FrontUser.class);
        ThirdUser tUser = BeanUtil.copy(tuser, ThirdUser.class);
        if (null == fUser || null == tUser)
        {
            return new ApiResult(ResultEnum.ParameterError);
        }
        Result result = new Result();
        try {
            result = userService.thirdUserRegistered(fUser, tUser);
        }catch (BaseException e){
            e.printStackTrace();
            return new ApiResult(ResultEnum.Error);
        }

        ApiResult apiResult = new ApiResult();

        BeanUtils.copyProperties(result, apiResult);

        return apiResult;
    }

    @Override
    public ApiResult queryUserInfo(Integer userId) {
        FrontUser user = userService.queryById(userId);

        logger.info("user totalAmount = " + user.getTotalAmount());
        UserDTO dto = new UserDTO();
        dto.setTotalAmount(new BigDecimal(user.getTotalAmount()));
        BeanUtils.copyProperties(user, dto);
        if(dto == null){
            return new ApiResult(ResultEnum.EmptyData);
        }
        FrontUser_address address = new FrontUser_address();
        address.setUserId(userId);
        List<FrontUser_address> frontUser_addresses = userAddressService.queryByParam(address, 1, 1);
        if(!CollectionUtils.isEmpty(frontUser_addresses)){
            address = frontUser_addresses.get(0);
            dto.setAddress((StringUtils.isBlank(address.getProvince())?"":address.getProvince()) + (StringUtils.isBlank(address.getCity())?"":address.getCity()) +
                    (StringUtils.isBlank(address.getArea())?"":address.getArea()) + (StringUtils.isBlank(address.getDetailAddress())?"":address.getDetailAddress()));
        }
        return new ApiResult(ResultEnum.Success, dto);
    }
    @Override
    public ApiResult queryUserSet(Integer userId) {
        FrontUser user = userService.queryById(userId);
        if(user == null){
            return new ApiResult(ResultEnum.EmptyData);
        }

        int count = userAddressService.countByUserId(userId);

        ApiUserSetDTO dto = new ApiUserSetDTO();
        dto.setNickName(user.getNickName());
        dto.setCoverImageUrl(user.getCoverImageUrl());
        dto.setMobileState(user.getMobileState());
        dto.setAddressNum(count);

        return new ApiResult(ResultEnum.Success, dto);
    }

    @Override
    public ApiResult thirdUserLogin(ApiThirdUser user)
    {
        
        if (null == user)
        {
            return null;
        }
        
        ThirdUser tUser = BeanUtil.copy(user, ThirdUser.class);
        if (null == tUser)
        {
            return null;
        }
        
        Result resultBuss = userService.login(null, tUser);
        if (null == resultBuss)
        {
            return null;
        }
        
        ApiResult result = BeanUtil.copy(resultBuss, ApiResult.class);
        
        return result;
    }
    
    @Override
    public ApiResult userExistence(String userName)
    {
        if (StringUtils.isEmpty(userName))
        {
            return ApiResultUtil.getParameterError();
        }
        Result resultBuss = userService.userExistence(userName);
        if (null == resultBuss)
        {
            return null;
        }
        
        ApiResult result = BeanUtil.copy(resultBuss, ApiResult.class);
        
        return result;
    }
    
    @Override
    public ApiResult realName(ApiAuditStaff auditStaff, ApiFrontUser apifrontUser)
    {
        logger.info("receive realName param:" + auditStaff);
        if (null == auditStaff)
        {
            return null;
        }
        
        AuditStaff bAuditStaff = BeanUtil.copy(auditStaff, AuditStaff.class);
        FrontUser frontUser = null;
        if (null != apifrontUser)
        {
            frontUser = BeanUtil.copy(apifrontUser, FrontUser.class);
        }
        
        Result resultBuss = userService.realName(bAuditStaff, frontUser);
        if (null == resultBuss)
        {
            return null;
        }
        
        ApiResult result = BeanUtil.copy(resultBuss, ApiResult.class);
        
        return result;
    }
    
    @Override
    public ApiFrontUser queryById(Integer id)
    {
        logger.info("receive query param:" + id);
        
        if (id == null)
        {
            return null;
        }
        
        FrontUser user = userService.queryById(id);
        
        return BeanUtil.copy(user, ApiFrontUser.class);
    }
    
    @Override
    public ApiFrontUser queryPersonCenter(ApiFrontUser apiUser)
    {
        
        logger.info("receive query param:" + apiUser);
        
        FrontUser user = BeanUtil.copy(apiUser, FrontUser.class);
        if (!valParam(user))
        {
            ApiResultUtil.getParameterError();
        }
        FrontUser frontUser = userService.queryPersonCenter(user);
        
        return BeanUtil.copy(frontUser, ApiFrontUser.class);
    }

    
    @Override
    public ApiResult updateFrontUser(ApiFrontUser apiFrontUser)
    {
        logger.info("receive updateFrontUser param : " + apiFrontUser);
        FrontUser user = BeanUtil.copy(apiFrontUser, FrontUser.class);
        if (!valParam(user))
        {
            return ApiResultUtil.getParameterError();
        }
        try
        {
            
            userService.updateFrontUser(user);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
        
    }
    
    public boolean valParam(FrontUser frontUser)
    {
        boolean ret = true;
        if (null == frontUser)
        {
            logger.info("frontUser is null");
            ret = false;
        }
        else if (frontUser.getId() == null)
        {
            logger.info("frontUserId is  null");
            ret = false;
        }
        return ret;
    }
    
    @Override
    public ApiFrontUser queryUserByParam(ApiFrontUser apiUser)
    {
        logger.info("receive queryUserByParam param:" + apiUser);
        
        FrontUser user = BeanUtil.copy(apiUser, FrontUser.class);
        if (null == user)
        {
            logger.info("apiUser is null");
            
            ApiResultUtil.getParameterError();
        }
        List<FrontUser> frontUsers = userService.queryUserByParam(user, 1, 20);
        FrontUser frontUser = null;
        if (frontUsers != null && frontUsers.size() > 0)
        {
            frontUser = frontUsers.get(0);
        }
        
        return BeanUtil.copy(frontUser, ApiFrontUser.class);
    }
    
    @Override
    public ApiThirdUser queryThirdUserByParam(ApiThirdUser apiTuser)
    {
        logger.info("receive queryUserByParam param:" + apiTuser);
        
        ThirdUser tuser = BeanUtil.copy(apiTuser, ThirdUser.class);
        if (null == tuser)
        {
            logger.info("apiTuser is null");
            
            ApiResultUtil.getParameterError();
        }
        ThirdUser thirdUser = null;
        List<ThirdUser> ThirdUsers = userService.queryThirdUserByParam(tuser, 1, 2);
        if(ThirdUsers != null && ThirdUsers.size() == 0){
        	if(tuser.getAccountNum() == null){
        		thirdUser = null;
        	}else{
        		String unionid = tuser.getUnionid();
            	tuser.setUnionid(null);
            	ThirdUsers = userService.queryThirdUserByParam(tuser, 1, 2);
        		if(ThirdUsers != null && ThirdUsers.size() == 0){
            		String accountNum = tuser.getAccountNum();
            		tuser.setAccountNum(null);
            		tuser.setUnionid(unionid);
            		ThirdUsers = userService.queryThirdUserByParam(tuser, 1, 2);
            		if(ThirdUsers != null && ThirdUsers.size() > 0){
            			thirdUser = ThirdUsers.get(0);
                		thirdUser.setAccountNum(accountNum);
                		userService.updateThirdUserById(thirdUser);
            		}
            	}else {
            		thirdUser = ThirdUsers.get(0);
            		thirdUser.setUnionid(unionid);
            		userService.updateThirdUserById(thirdUser);
    			}
        	}
        }else{
        	thirdUser = ThirdUsers.get(0);
        }
        
        return BeanUtil.copy(thirdUser, ApiThirdUser.class);
    }
    @Override
    public ApiResult resetFrontUser(ApiFrontUser apiFrontUser)
    {
        logger.info("resetPwd param : " + apiFrontUser);
        FrontUser user = BeanUtil.copy(apiFrontUser, FrontUser.class);
        if (!valUserParam(user))
        {
            ApiResultUtil.getParameterError();
            
        }
        try
        {
            userService.resetFrontUser(user);
            
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.ERROR;
        }
    }
    
    public boolean valUserParam(FrontUser frontUser)
    {
        boolean ret = true;
        if (frontUser == null)
        {
            logger.info("frontUser is  null");
            ret = false;
        }
        else if (frontUser.getId() == null)
        {
            ret = false;
            logger.info("userId is  null ");
            
        }
        else if (frontUser.getUserPass() == null)
        {
            ret = false;
            logger.info("");
        }
        return ret;
    }
    
    @Override
    public ApiPage<ApiUserCard> queryUserCardList(ApiUserCard apiUserCard, Integer pageNum, Integer pageSize)
    {
        logger.info("queryUserCardList param : " + apiUserCard);
        if (null == apiUserCard)
        {
            return new ApiPage<ApiUserCard>(pageNum, pageSize);
        }
        UserCard userCard = BeanUtil.copy(apiUserCard, UserCard.class);
        
        List<UserCard> list = userCardService.queryUserCardByParam(userCard, pageNum, pageSize);
        ApiPage<ApiUserCard> ret = BeanUtil.copyPage((Page<UserCard>)list, ApiUserCard.class);
        
        return ret;
    }
    
    @Override
    public ApiResult saveUserCard(ApiUserCard apiUserCard)
    {
        logger.info("saveUserCard  param : " + apiUserCard);
        UserCard userCard = BeanUtil.copy(apiUserCard, UserCard.class);
        
        if (!valParam(userCard))
        {
            return ApiResultUtil.getParameterError();
        }
        try
        {
            
            userCardService.saveUserCard(userCard);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }
    
    @Override
    public ApiResult saveNewUserCard(ApiUserCard apiUserCard)
    {
        logger.info("saveUserCard  param : " + apiUserCard);
        UserCard userCard = BeanUtil.copy(apiUserCard, UserCard.class);
        
        if (!valParam(userCard))
        {
            return ApiResultUtil.getParameterError();
        }
        try
        {
            
            userCardService.saveNewUserCard(userCard);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }
    
    public boolean valParam(UserCard userCard)
    {
        boolean ret = true;
        if (StringUtils.isEmpty(userCard.getBankName()))
        {
            logger.info("bankName is null");
            ret = false;
        }
        else if (StringUtils.isEmpty(userCard.getCard()))
        {
            logger.info("card is null");
            ret = false;
        }
        else if (userCard.getUserId() == null)
        {
            logger.info("useId is null");
            ret = false;
        }
        else if (StringUtils.isEmpty(userCard.getProvince()))
        {
            logger.info("province is null");
            ret = false;
        }
        else if (StringUtils.isEmpty(userCard.getCity()))
        {
            logger.info("city is null");
            ret = false;
        }
        return ret;
    }
    
    @Override
    public ApiResult updateUserCard(ApiUserCard apiUserCard)
    {
        logger.info("updateUserCard param  = " + apiUserCard);
        try
        {
            UserCard userCard = BeanUtil.copy(apiUserCard, UserCard.class);
            if (!valUpdParam(userCard))
            {
                return ApiResultUtil.getParameterError();
            }
            userCardService.updateUserCard(userCard);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }
    
    public boolean valUpdParam(UserCard userCard)
    {
        boolean ret = true;
        if (null == userCard)
        {
            logger.info("userCard is null");
            ret = false;
        }
        else if (userCard.getId() == null)
        {
            logger.info("id is null");
            ret = false;
        }
        else if (userCard.getUseState() == null)
        {
            logger.info("useState is null");
            ret = false;
        }
        
        return ret;
    }
    
    @Override
    public ApiPage<ApiFrontUser> queryUserList(ApiFrontUser apiUser, Integer pageNum, Integer pageSize)
    {
        logger.info("receive queryUserByParam param:" + apiUser);
        
        FrontUser user = BeanUtil.copy(apiUser, FrontUser.class);
        if (null == user)
        {
            logger.info("apiUser is null");
            
            ApiResultUtil.getParameterError();
        }
        List<FrontUser> frontUsers = userService.queryUserByParam(user, pageNum, pageSize);
        ApiPage<ApiFrontUser> ret = BeanUtil.copyPage((Page<FrontUser>)frontUsers, ApiFrontUser.class);
        
        return ret;
    }
    
    @Override
    public ApiResult resetUserCardSelected(ApiUserCard apiUserCard)
    {
        logger.info("receive resetUserCardSelected param : " + apiUserCard);
        
        try
        {
            if (!valParam(apiUserCard))
            {
                return ApiResultUtil.getParameterError();
            }
            UserCard userCard = BeanUtil.copy(apiUserCard, UserCard.class);
            userCardService.resetUserCardSelectd(userCard);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }
    
    public boolean valParam(ApiUserCard apiUserCard)
    {
        boolean ret = true;
        if (apiUserCard == null)
        {
            logger.info("apiUserCard is null");
            ret = false;
        }
        else if (apiUserCard.getUserId() == null)
        {
            logger.info("userId is null");
            ret = false;
        }
        else if (apiUserCard.getId() == null)
        {
            logger.info("id is null");
            ret = false;
        }
        else if (apiUserCard.getIsSelected() == null)
        {
            logger.info("isSelected is null");
            ret = false;
        }
        return ret;
    }
    
    @Override
    public ApiResult updateUser(ApiFrontUser apiFrontUser)
    {
        if (apiFrontUser == null)
        {
            return ApiResultUtil.getParameterError();
        }
        else if (apiFrontUser.getId() == null)
        {
            return ApiResultUtil.getParameterError();
        }
        FrontUser user = BeanUtil.copy(apiFrontUser, FrontUser.class);
        
        try
        {
            userService.updateUser(user);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
        
    }
    
    @Override
    public ApiBFile queryBFileById(Integer id)
    {
        logger.info("receive queryBFileById param ： " + id);
        
        BFile file = fileService.queryById(id);
        
        ApiBFile apiFile = BeanUtil.copy(file, ApiBFile.class);
        
        return apiFile;
    }
    

    @Override
    public int updateUserAndAudit(ApiFrontUser apiUser,String ids){
    	FrontUser user = BeanUtil.copy(apiUser, FrontUser.class);
    	int result =0;
    	try {
    		
    		if(ids!=""||ids!=null){
    			AuditStaff auditStaff = new AuditStaff();
    			auditStaff.setUserId(apiUser.getId());
    			//auditStaff.setState(203);
    			auditStaff.setPersonType("helpPeople");
    			List<AuditStaff>list = auditStaffService.queryAuditStaffByParam(auditStaff);
    			if(list.size()>0 && list.get(0).getState()==203){//已经认证过
    				//list.get(0).setFileId(ids);
    				//auditStaffService.updateAuditStaff(list.get(0));
    			}
    			else if(list.size()>0 && list.get(0).getState()!=203){
    				auditStaff = new AuditStaff();
    				auditStaff.setId(list.get(0).getId());
    				auditStaff.setFileId(""+ids+"");
    				auditStaff.setState(201);
    				auditStaff.setPersonType("helpPeople");
    				auditStaff.setUserId(apiUser.getId());
    				auditStaffService.updateAuditStaff(auditStaff);
    				result=list.get(0).getId();
    				userService.updateUser(user);
    			}
    			else{
    				auditStaff = new AuditStaff();
    				auditStaff.setFileId(ids);
    				auditStaff.setState(201);
    				auditStaff.setPersonType("helpPeople");
    				auditStaff.setUserId(apiUser.getId());
    				result = auditStaffService.save(auditStaff);
    				if(result==1)
    					result=auditStaff.getId();
    				userService.updateUser(user);
    			}
    		}
    		return result;
		} catch (BaseException e) {
			return result;
		}
    }



	@Override
	public ApiResult selectAuditProjectCountByParam(ApiAuditProject apiAuditProject) {
		AuditProject auditProject=BeanUtil.copy(apiAuditProject, AuditProject.class);
		ApiResult result=new ApiResult();
		try {
			int count= auditProjectService.selectProjectCount(auditProject);
			result.setCode(1);

		} catch (Exception e) {
			result.setCode(1);			
			e.printStackTrace();
			
		}
		return result;
	}

    
    @Override
    public ApiAuditProject selectByParam(ApiAuditProject apiAuditProject){
    	logger.info("receive query param:" + apiAuditProject);
        if (apiAuditProject == null)
        {
            return null;
        }
        AuditProject project = BeanUtil.copy(apiAuditProject, AuditProject.class);
        AuditProject auditProject=null;
		try {
			auditProject = auditProjectService.selectByParam(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return BeanUtil.copy(auditProject, ApiAuditProject.class);
    }
    
    @Override
    public ApiResult saveAuditProject(ApiAuditProject apiAuditProject){
    	logger.info("receive updateFrontUser param : " + apiAuditProject);
    	AuditProject project = BeanUtil.copy(apiAuditProject, AuditProject.class);
        if (project==null)
        {
            return ApiResultUtil.getParameterError();
        }
        try
        {
        	auditProjectService.save(project);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }
    
    /**
     * 获取证实人列表
     */
    public ApiPage<ApiAuditProject> queryApiAuditProjectList(ApiAuditProject apiAuditProject, Integer pageNum,Integer pageSize){
    	AuditProject auditProject=BeanUtil.copy(apiAuditProject, AuditProject.class);
    	
    	ApiPage<ApiAuditProject> list1=null;
		try {
			List<AuditProject> list = auditProjectService.selectAuditProjectList(auditProject,pageNum ,pageSize);
		    list1=BeanUtil.copyPage((Page<AuditProject>)list, ApiAuditProject.class);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    		
    	return list1;
    }

    
    @Override
    public ApiResult saveWeixinModel(ApiWeixinModel apiWeixinModel)
    {
        logger.info("saveWeixinModel  param : " + apiWeixinModel);
        WeixinModel weixinModel = BeanUtil.copy(apiWeixinModel, WeixinModel.class);
        
        
        try
        {
        	weixinModelService.save(weixinModel);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }


    @Override
    public ApiResult saveTeam(Integer userId, Integer projectId, Integer coverImgId, String launchName, Integer state) {
        logger.info("saveTeam  userId = " + userId + ",projectId = " + projectId + ",coverImgId = " + coverImgId
                + ",launchName = " + launchName + ", state = " + state);
        if (!valTeam(userId, projectId, coverImgId, launchName, state))
        {
            return ApiResultUtil.getParameterError();
        }
        try
        {
            userService.saveTeam(userId,projectId,coverImgId,launchName,state);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }
    private static boolean valTeam(Integer userId, Integer projectId, Integer coverImgId, String launchName, Integer state){
        if(userId == null || projectId == null || coverImgId == null || "".equals(launchName) || state == null){
            return false;
        }
        return true;
    }

    @Override
    public ApiResult addTeamPeopleAndUpdateTeam(Integer userId, Integer projectId, Integer teamId) {
        logger.info("saveTeam  userId = " + userId + ",projectId = " + projectId
                + ",teamId = " + teamId);
        if (!valTeamPeople(userId, projectId, teamId))
        {
            return ApiResultUtil.getParameterError();
        }
        try
        {
            userService.addTeamPeopleAndUpdateTeam(userId,projectId,teamId);
            return ApiResultUtil.SUCCESS;
        }
        catch (BaseException e)
        {
            return ApiResultUtil.getApiResult(e.getResultCode());
        }
    }

    private static boolean valTeamPeople(Integer userId, Integer projectId, Integer teamId ){
        if(userId == null || projectId == null || teamId == null){
            return false;
        }
        return true;
    }

}
