
package com.guangde.home.controller.index;

import com.guangde.api.commons.ICommonFacade;
import com.guangde.entry.ApiBFile;
import com.guangde.enums.ResultEnum;
import com.guangde.home.constant.PengPengConstants;
import com.guangde.home.utils.DateUtil;
import com.guangde.home.utils.ResultUtil;
import com.guangde.pojo.ApiResult;
import com.redis.utils.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("index")
public class IndexController {

	@Autowired
	private ICommonFacade CommonFacade;

	@Autowired
	private RedisService redisService;

	/**
	 * 首页轮播图
     * @return
     */
	@RequestMapping(value = "/banner/list", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getIndexBannerList() {
		Object obj = redisService.queryObjectData(PengPengConstants.WYXCSZH_UNIQE_PRE_ + PengPengConstants.INDEX_BANNER_LIST);
		if (obj != null) {
			return ResultUtil.SUCCESS(obj);
		} else {
			ApiBFile apiBfile = new ApiBFile();
			apiBfile.setCategory("banner");
			List<ApiBFile> bfiles = CommonFacade.queryApiBfile(apiBfile);
			if (bfiles == null) {
				return new ApiResult(ResultEnum.EmptyData);
			}
			redisService.saveObjectData(PengPengConstants.WYXCSZH_UNIQE_PRE_ + PengPengConstants.INDEX_BANNER_LIST, bfiles, DateUtil.DURATION_FIVE_S);
			return ResultUtil.SUCCESS(bfiles);
		}
	}
}
