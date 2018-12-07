package com.guangde.home.controller.project;

import com.guangde.api.homepage.IProjectFacade;
import com.guangde.api.homepage.IProjectVolunteerFacade;
import com.guangde.api.user.IDonateRecordFacade;
import com.guangde.dto.ApiFrontUserInvoiceDTO;
import com.guangde.dto.ProjectDTO;
import com.guangde.dto.ProjectDetailDTO;
import com.guangde.entry.ApiDonateRecord;
import com.guangde.entry.ApiProject;
import com.guangde.entry.ApiProjectCryPeople;
import com.guangde.enums.ProjectEnum;
import com.guangde.enums.ResultEnum;
import com.guangde.home.constant.UrlConstants;
import com.guangde.home.form.ProjectCryPeopleForm;
import com.guangde.home.utils.CommonUtils;
import com.guangde.home.utils.ResultUtil;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
@RequestMapping("/project")
public class ProjectController
{
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private IProjectFacade projectFacade;
    
    @Autowired
    private IDonateRecordFacade donateRecordFacade;

    @Autowired
    private IProjectVolunteerFacade projectVolunteerFacade;

    /**
     * 推荐项目
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/recommend/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult recommendProject(@RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
                                    @RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize){
        ApiProject param = new ApiProject();
        param.setIsRecommend(ProjectEnum.ProjectIsRecommend.getCode());
        param.setOrderBy(" lastUpdateTime ");
        param.setOrderDirection(" desc ");
        param.setIsHide(ProjectEnum.ProjectNotHide.getCode());
        param.setStates(Arrays.asList(ProjectEnum.ProjectSender.getCode(), ProjectEnum.ProjectEnd.getCode()));
        ApiPage<ProjectDTO> page = projectFacade.queryProjectList(param, pageNum, pageSize);
        if(page != null && page.valPage(page, pageNum)){
            return ResultUtil.SUCCESS(page);
        }
        if(pageNum > 1){
            return new ApiResult(ResultEnum.NotMoreData);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }

    /**
     * 项目列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult projectList(@RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
                                 @RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
                                 @RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "field", required = false) String field){
        ApiProject param = new ApiProject();
        param.setOrderBy(" lastUpdateTime ");
        param.setOrderDirection(" desc ");
        param.setIsHide(ProjectEnum.ProjectNotHide.getCode());
        param.setStates(Arrays.asList(ProjectEnum.ProjectSender.getCode(), ProjectEnum.ProjectEnd.getCode()));
        param.setKeyword(keyword);
        param.setField(field);
        ApiPage<ProjectDTO> page = projectFacade.queryProjectList(param, pageNum, pageSize);
        if(page != null && page.valPage(page, pageNum)){
            return ResultUtil.SUCCESS(page);
        }
        if(pageNum > 1){
            return new ApiResult(ResultEnum.NotMoreData);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }


    /**
     * 项目详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult projectDetail(@RequestParam("id") Integer id){
        ProjectDetailDTO detailDTO = projectFacade.queryProjectDetailById(id);
        if(detailDTO == null){
            return new ApiResult(ResultEnum.EmptyData);
        }
        return new ApiResult(ResultEnum.Success, detailDTO);
    }


    /**
     * 项目捐赠记录
     * @param pageNum
     * @param pageSize
     * @param param
     * @return
     */
    @RequestMapping(value = "/donate/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult donateList(@RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
                                @RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
                                ApiDonateRecord param){
        ApiPage<ApiDonateRecord> page = donateRecordFacade.queryByCondition(param, pageNum, pageSize);
        if(page != null && page.valPage(page, pageNum)){
            return ResultUtil.SUCCESS(page);
        }
        if(pageNum > 1){
            return new ApiResult(ResultEnum.NotMoreData);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }

    /**
     * 求助申请
     * @param response
     * @param request
     * @param form
     * @param bind
     * @return
     */
    @RequestMapping(value = "/help/apply", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult helpApply(HttpServletResponse response, HttpServletRequest request,
                               @Validated ProjectCryPeopleForm form,
                               BindingResult bind) {
        response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if(bind.hasErrors()){
            return new ApiResult(ResultEnum.OtherError.getCode(), bind.getFieldError().getDefaultMessage());
        }

        ApiResult result = CommonUtils.valUserLogin(request);
        if(result.getCode() != 1){
            return result;
        }

        Integer userId = Integer.valueOf(result.getData().toString());
        ApiProjectCryPeople projectCryPeople = new ApiProjectCryPeople();
        BeanUtils.copyProperties(form, projectCryPeople);
        projectCryPeople.setUserId(userId);
        return projectVolunteerFacade.save(projectCryPeople);
    }
    
    //可能会关注的项目
    @RequestMapping(value = "/maybeList", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult maybeList(@RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
                               @RequestParam(value="pageSize",required=false,defaultValue="2")Integer pageSize){
        ApiPage<ProjectDTO> page = projectFacade.queryProjectMaybeList(pageNum, pageSize);
        if(page != null && page.valPage(page, pageNum)){
            return ResultUtil.SUCCESS(page);
        }
        if(pageNum > 1){
            return new ApiResult(ResultEnum.NotMoreData);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }
    
    //捐赠发票可申请记录
    @RequestMapping(value = "/myInvoiceApply", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult myInvoiceApply(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
                                    @RequestParam(value="pageSize",required=false,defaultValue="20")Integer pageSize){
    	response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ApiResult result = CommonUtils.valUserLogin(request);
        if(result.getCode() != 1){
            return result;
        }

        Integer userId = Integer.valueOf(result.getData().toString());
    	ApiPage<ApiDonateRecord> page = donateRecordFacade.queryByInvoiceApplyList(userId, pageNum, pageSize);
        if(page != null && page.valPage(page, pageNum)){
            return ResultUtil.SUCCESS(page);
        }
        if(pageNum > 1){
            return new ApiResult(ResultEnum.NotMoreData);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }
    
    //捐赠发票首页
    @RequestMapping(value = "/myInvoiceIndex", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult myInvoiceIndex(HttpServletRequest request, HttpServletResponse response){
    	response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ApiResult result = CommonUtils.valUserLogin(request);
        if(result.getCode() != 1){
            return result;
        }

        Integer userId = Integer.valueOf(result.getData().toString());
        ApiFrontUserInvoiceDTO frontUser_invoice = donateRecordFacade.queryByInvoiceIndex(userId);
        if(frontUser_invoice != null){
            return ResultUtil.SUCCESS(frontUser_invoice);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }
    
    //用户的捐赠记录
    @RequestMapping(value = "/myDonateRecordList", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult myDonateRecordList(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
                                    @RequestParam(value="pageSize",required=false,defaultValue="20")Integer pageSize){
    	response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ApiResult result = CommonUtils.valUserLogin(request);
        if(result.getCode() != 1){
            return result;
        }

        Integer userId = Integer.valueOf(result.getData().toString());
        ApiDonateRecord donateRecord = new ApiDonateRecord();
        donateRecord.setUserId(userId);
    	ApiPage<ApiDonateRecord> page = donateRecordFacade.queryByCondition(donateRecord, pageNum, pageSize);
        if(page != null && page.valPage(page, pageNum)){
            return ResultUtil.SUCCESS(page);
        }
        if(pageNum > 1){
            return new ApiResult(ResultEnum.NotMoreData);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }
    
    //捐赠成功页
    @RequestMapping(value = "/donateRecordSuccess", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult donateRecordSuccess(HttpServletRequest request, HttpServletResponse response,@RequestParam("tranNum") String tranNum
    		){
    	response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ApiResult result = CommonUtils.valUserLogin(request);
        if(result.getCode() != 1){
            return result;
        }

        Integer userId = Integer.valueOf(result.getData().toString());
        ApiDonateRecord donateRecord = new ApiDonateRecord();
        donateRecord.setTranNum(tranNum);
        donateRecord.setUserId(userId);
        donateRecord = donateRecordFacade.queryDonateRecordSuccess(donateRecord);
        if(donateRecord != null){
            return ResultUtil.SUCCESS(donateRecord);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }
    
    //添加发票
    @RequestMapping(value = "/user/invoiceAdd", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult invoiceAdd(HttpServletResponse response, HttpServletRequest request,
    		@RequestParam("invoiceList") String invoiceList,
    		@RequestParam("addressId") Integer addressId,
    		@RequestParam("invoiceHead") String invoiceHead) {
        response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ApiResult result = CommonUtils.valUserLogin(request);
        if(result.getCode() != 1){
            return result;
        }

        Integer userId = Integer.valueOf(result.getData().toString());
        return donateRecordFacade.saveInvoice(userId,invoiceList,addressId,invoiceHead);
    }
}
