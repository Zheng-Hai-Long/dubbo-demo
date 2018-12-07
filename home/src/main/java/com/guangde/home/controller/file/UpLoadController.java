package com.guangde.home.controller.file;

import com.guangde.api.commons.IFileFacade;
import com.guangde.api.user.IUserFacade;
import com.guangde.entry.ApiFrontUser;
import com.guangde.enums.ResultEnum;
import com.guangde.home.constant.ImageTypeConstants;
import com.guangde.home.constant.UrlConstants;
import com.guangde.home.utils.*;
import com.guangde.home.vo.file.ImageVO;
import com.guangde.pojo.ApiResult;
import com.redis.utils.RedisService;
import com.tenpay.utils.TenpayUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/file")
public class UpLoadController {

	Logger logger = LoggerFactory.getLogger(UpLoadController.class);
	@Autowired
	private IFileFacade fileFacade;

	@Autowired
	private IUserFacade userFacade;

	@Autowired
	private RedisService redisService;


	/**
	 * 上传图片
	 * @param file
	 * @param type
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/upload/image" ,method = RequestMethod.POST)
	public ApiResult upload(@RequestParam("file") CommonsMultipartFile file, @RequestParam("type") String type,
							HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");

		ImageVO imageVO = new ImageVO();
		if (!file.isEmpty()) {
			try {
				InputStream inputStream = (InputStream) file.getInputStream();

				type = ImageUtil.typename(type);
				if("".equals(type)){
					return new ApiResult(ResultEnum.UnknownBusinessType);
				}
				ApiResult resultUtil = ImageUtil.checkImg(file, type);
				if(resultUtil.getCode() !=1){
					return resultUtil;
				}
				if(ImageTypeConstants.HEAD_IMAGE.equals(type) || ImageTypeConstants.ID_IMAGE.equals(type) || ImageTypeConstants.COMMON_FORM_IMAGE.equals(type)){
					resultUtil = CommonUtils.valUserLogin(request);
					if(resultUtil.getCode() !=1){
						return resultUtil;
					}
					ApiFrontUser user = new ApiFrontUser();
					//压缩图片
					InputStream in = CompressPicUtil.compressPic(inputStream, file, request,"local");
					ApiResult result = fileFacade.upload(type, ImageUtil.checkExt(file,type), in);
					String imageId = result.getData().toString().split(";")[0];
					String imageUrl = result.getData().toString().split(";")[1];

					imageVO = new ImageVO(Integer.valueOf(imageId), imageUrl);

					if(ImageTypeConstants.HEAD_IMAGE.equals(type)) {
						user.setId(Integer.valueOf(resultUtil.getData().toString()));
						user.setCoverImageId(Integer.valueOf(imageId));
						userFacade.updateFrontUser(user);
					}
				}
				else {
					// 等比压缩图片 长宽不变
					InputStream in = CompressPicUtil.compressPic(inputStream, file, request,"local");

					ApiResult result = fileFacade.upload(type, ImageUtil.checkExt(file,type), in);

					String imageId = result.getData().toString().split(";")[0];
					String imageUrl = result.getData().toString().split(";")[1];

					logger.info("compressPic   imageId = "+imageId+" imageUrl = "+imageUrl);

					imageVO = new ImageVO(Integer.valueOf(imageId), imageUrl);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ApiResult(ResultEnum.Error);
			}
			return new ApiResult(ResultEnum.Success, imageVO);
		}
		return new ApiResult(ResultEnum.ImageEmptyError);
	}


	/**
	 * 上传文件
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="upload/file" ,method = RequestMethod.POST)
	public ApiResult uploadFile(@RequestParam("file") CommonsMultipartFile file,
									  HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);response.setHeader("Access-Control-Allow-Credentials", "true");
		return  FileUtil.uploadFile(file);
	}


	/**
	 * 删除文件
	 * @param images
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ApiResult Imagedelete(int[] images,
								 HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);response.setHeader("Access-Control-Allow-Credentials", "true");
		ApiResult resultUtil = CommonUtils.valUserLogin(request);
		if(resultUtil.getCode() != 1){
			return resultUtil;
		}
		if (images == null) {
			return new ApiResult(ResultEnum.ParameterError);
		}

		List<Integer> ids = new ArrayList<>();
		for(int id : images){
			ids.add(id);
		}
		ApiResult result = fileFacade.deleteBfileList(ids);
		if(result.getCode() == 1) {
			return new ApiResult(ResultEnum.Success);
		}
		return new ApiResult(ResultEnum.Error);
	}



	public static String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?";
	/**
	 * 获取媒体文件 微信上的已上传图片
	 *
	 *      接口访问凭证
	 *      媒体文件id
	 * */
	@ResponseBody
	@RequestMapping(value = "/image/weixin")
	public ApiResult downloadMedia(@RequestParam(value = "mId",required = true) String mId,
										 @RequestParam(value = "typeName",required = false) String typeName,
										 HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		//微信调度的唯一凭证AccessToken失效，要同时更新jsTicket这个临时凭证
		String accessToken = TenpayUtil.queryAccessToken();
		redisService.saveObjectData("AccessToken" , accessToken, DateUtil.DURATION_HOUR_S);
		String jsTicket = TenpayUtil.queryJsapiTicket(accessToken);
		redisService.saveObjectData("JsapiTicket" , jsTicket, DateUtil.DURATION_HOUR_S);
		String image="";
		List<String> imageL = new ArrayList<>();
		List<String> imageIds = new ArrayList<>();
		logger.info("图片上传："+mId);
		if(StringUtils.isNotEmpty(mId)){
			if(ImageTypeConstants.HEAD_IMAGE.equals(typeName)){
				imageL.add(queryImage(accessToken,mId,"8"));
				if(imageL != null){
					ApiResult resultUtil = CommonUtils.valUserLogin(request);
					if(resultUtil.getCode() !=1){
						return resultUtil;
					}
					ApiFrontUser user = new ApiFrontUser();
					user.setId(Integer.valueOf(resultUtil.getData().toString()));
					user.setCoverImageId(Integer.valueOf(imageL.toString()));

					userFacade.updateFrontUser(user);
				}else{
					return new ApiResult(ResultEnum.Error);
				}
			}/*else if("commonFormNew".equals(typeName)){//表单下载图片,路径在home下
				String[] mIdL = mId.split(",");
				for (int i = 0; i < mIdL.length; i++) {
					image +=(queryImageUrlNew(accessToken,mIdL[i],"4")+",");//http@11,http2@2,http3@3,
				}
				logger.info("image:"+image);
				String[] imgs = image.split("\\,");
				for (String img : imgs) {
					String im[] = img.split("\\@");
					imageL.add(im[0]);
					imageIds.add(im[1]);
				}
			}*/
			else {
				String[] mIdL = mId.split(",");
				for (int i = 0; i < mIdL.length; i++) {
					imageL.add(queryImage(accessToken,mIdL[i],"4")+",");
				}
			}
			ImageVO imageVO = new ImageVO(imageIds, imageL);
			logger.info("图片上传：imageVo = "+imageVO);
			return new ApiResult(ResultEnum.Success, imageVO);
		}
		return new ApiResult(ResultEnum.Error);
	}

	private String queryImage(String accessToken , String media_id, String type){
		String Url = requestUrl+"access_token="+ accessToken+"&media_id="+media_id;
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			URL url = new URL(Url);
			logger.info("Url:"+Url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);

			in =  conn.getInputStream();
			type = ImageUtil.typename(type);
			// 等比压缩图片 长宽不变
			ApiResult result = fileFacade.upload(type,"jpg", in);
			String imageId = result.getData().toString().split(";")[0];
			return imageId;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null){
				conn.disconnect();
			}
		}
		return null;
	}

	/**
	 * 下载图片，返回地址
	 * @param accessToken
	 * @param media_id
	 * @param type
	 * @return
	 */
	/*private String queryImageUrlNew(String accessToken , String media_id, String type){
		String Url = requestUrl+"access_token="+ accessToken+"&media_id="+media_id;
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			URL url = new URL(Url);
			logger.info("Url:"+Url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);

			in =  conn.getInputStream();
			type = ImageUtil.typename(type);
			// 等比压缩图片 长宽不变
			ApiResult result = fileFacade.uploadHome(type,"jpg", in);
			String imageUrl = result.getData().split(";")[1];
			String imageId = result.getData().split(";")[0];
			return imageUrl+"@"+imageId;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null){
				conn.disconnect();
			}
		}
		return null;
	}*/

	/**
	 * 下载图片，返回地址
	 * @param accessToken
	 * @param media_id
	 * @param type
	 * @return
	 */
	private String queryImageUrl(String accessToken , String media_id, String type){
		String Url = requestUrl+"access_token="+ accessToken+"&media_id="+media_id;
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			URL url = new URL(Url);
			logger.info("Url:"+Url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);

			in =  conn.getInputStream();
			type = ImageUtil.typename(type);
			// 等比压缩图片 长宽不变
			ApiResult result = fileFacade.upload(type,"jpg", in);
			String imageUrl = result.getData().toString().split(";")[1];
			String imageId = result.getData().toString().split(";")[0];
			return imageUrl+"@"+imageId;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null){
				conn.disconnect();
			}
		}
		return null;
	}
}
