package com.guangde.home.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.guangde.api.homepage.IProjectFacade;
import com.guangde.api.user.ICompanyFacade;
import com.guangde.api.user.IUserFacade;
import com.guangde.entry.ApiAuditStaff;
import com.guangde.entry.ApiFrontUser;
import com.guangde.home.utils.CodeUtil;
import com.guangde.home.utils.UserUtil;
import com.guangde.home.utils.storemanage.StoreManage;
import com.guangde.home.vo.user.PUser;
import com.guangde.pojo.ApiResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("user")
public class PerfectUserController{

	private final Logger logger = LoggerFactory
			.getLogger(PerfectUserController.class);

	@Autowired
	private IUserFacade userFacade;
	@Autowired
	private IProjectFacade projectFacade;
	@Autowired
	private ICompanyFacade companyFacade;



	/**
	 * 完善信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("perfect")
	public JSONObject sso(PUser user,HttpServletRequest request,HttpServletResponse response) {
		ApiFrontUser frontUser = new ApiFrontUser();
		JSONObject jObject = new JSONObject();
		Integer userId = UserUtil.getUserId(request, response);
		//1:error 0:success
		jObject.put("reslut", "1");
		if (userId == null) {
			jObject.put("error", "请登录。");
			return jObject;
		}
		frontUser.setRealName(user.getRealname());
		frontUser.setIdCard(user.getIdCard());
		ApiFrontUser userFrom = userFacade.queryById(userId);
		if (userFrom.getMobileState() != 203) {
			frontUser.setMobileNum(user.getMobileNum());
			// 手机验证
			String phonekey = "";//CodeUtil.certificationprex + user.getMobileNum();
			// 可以替换为其他的存储方式
			StoreManage storeManage = StoreManage.create(
					StoreManage.STROE_TYPE_SESSION, request.getSession());
			int codeR = CodeUtil.VerifiCode(phonekey, storeManage,
					user.getPhoneCode(), false);
			if (codeR == -1) {
				jObject.put("error", "手机验证码过期");
				return jObject;
			} else if (codeR == 0) {
				jObject.put("error", "手机验证码错误");
				return jObject;
			}
			frontUser.setMobileState(203);
		}
		// 需要检查的身份信息图片的ID
		String imageId = user.getImageId();
		// 检查有没上传3张图片，类型与图片个数一致
		if (StringUtils.isBlank(user.getImageId())) {
			jObject.put("error", "上传图片份数不对。");
			return jObject;
		}
		String[] imageType = user.getImageId().split(",");
		if ( imageType.length != 3) {
			jObject.put("error", "上传图片份数不对。");
			return jObject;
		}
		ApiAuditStaff auditStaff = new ApiAuditStaff();
		auditStaff.setUserId(userId);
		auditStaff.setPersonType("helpPeople");
		auditStaff.setFileId(imageId);
		ApiResult result2 = userFacade.realName(auditStaff, frontUser);
		if (result2 != null && result2.getCode() == 1) {
			jObject.put("reslut", "0");
		} else {
			//jObject.put("error", result2.getMessage());
		}
		return jObject;
	}

}
