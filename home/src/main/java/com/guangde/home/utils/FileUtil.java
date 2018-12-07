package com.guangde.home.utils;

import com.guangde.api.commons.IFileFacade;
import com.guangde.entry.ApiBFile;
import com.guangde.enums.ResultEnum;
import com.guangde.home.constant.UrlConstants;
import com.guangde.home.vo.file.FileVO;
import com.guangde.pojo.ApiResult;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 生成验证码图片
 */
public class FileUtil {

	private final static Logger logger = Logger.getLogger(FileUtil.class);

	public static IFileFacade fileFacade = SpringContextUtil.getBean("fileFacade",IFileFacade.class);

	/** 验证码图片的宽度。 */
	private static int width = 70;

	/** 验证码图片的高度。 */
	private static int height = 28;

	public static BufferedImage codeImg(String code) throws Exception {
		return codeImg(code, width, height, BufferedImage.TYPE_INT_RGB,
				new Font("Times New Roman", Font.PLAIN, height - 2));
	}

	public static BufferedImage codeImg(String code, int width, int height,
										int type, Font font) throws Exception {
		if (StringUtils.isBlank(code)) {
			throw new Exception("codeImg验证码不能为空");
		}
		// 定义图像buffer
		BufferedImage image = new BufferedImage(width, height, type);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 生成随机类
		Random random = new Random();
		// 设定背景色
		g.setColor(getRandColor(200, 250, random));
		g.fillRect(0, 0, width, height);

		// 设定字体
		g.setFont(font);

		// 画边框
		// g.setColor(new Color());
		// g.drawRect(0,0,width-1,height-1);

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200, random));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 取随机产生的认证码(codeCount位数字)
		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。

		int codeY = height - 6;
		char[] array = code.toCharArray();
		for (int i = 0; i < array.length; i++) {
			// 将认证码显示到图象中
			// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(String.valueOf(array[i]), 13 * i + 6, codeY);
		}
		// 图象生效
		g.dispose();
		return image;
	}

	/**
	 * 给定范围获得随机颜色
	 *
	 * @param fc
	 * @param bc
	 * @return
	 */
	private static Color getRandColor(int fc, int bc, Random random) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/*
	 * 根据文件的扩展名,判断是否符合上传要求
	 *
	 * @param MultipartFile
	 */
	public static String checkExt(MultipartFile file) {

		// 定义一个数组，用于保存可上传的文件类型
		List<String> fileTypes = new ArrayList<String>();
		fileTypes.add("pdf");
		fileTypes.add("xls");
		fileTypes.add("xlsx");
		fileTypes.add("doc");
		fileTypes.add("docx");

		String fileName = file.getOriginalFilename();
		// 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		// 对扩展名进行小写转换
		ext = ext.toLowerCase();
		if (fileTypes.contains(ext)) { // 如果扩展名属于允许上传的类型，则创建文件
			return ext;
		}
		return "error";
	}

	public static String checkSize(MultipartFile imgFile) {
		// 判断文件大小是否大于10M
		if (imgFile.getSize() > 10485760) {
			return "error";
		}
		return "ok";
	}
	/**
	 *
	 * @param file
	 * @return 0,正常，1，文件太大，2，文件类型不对
	 */
	public static ApiResult checkFileSize(MultipartFile file){
		if ("error".equals(FileUtil.checkSize(file))) {
			// 文件的大小
			return new ApiResult(ResultEnum.FileSizeError);
		}
		if ("error".equals(FileUtil.checkExt(file))) {
			// 文件的扩展名不符合要求
			return new ApiResult(ResultEnum.FileTypeError);
		}
		return new ApiResult(ResultEnum.Success);
	}

	/**
	 * 删除图片文件
	 * @param path
	 */
	public static boolean deletePicture(String path)
	{
		try
		{
			File file = new File(path);
			if (file.exists())
			{
				file.delete();
				return true;
			}
			return false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 生成文件名（时间戳_原名称）
	 * @param file
	 * @return
	 */
	public static String makeFileName(CommonsMultipartFile file){
		return System.currentTimeMillis() + "_" +file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
	}

	/**
	 * 创建文件侠
	 * @param folderPath  文件路径
	 * @return true：创建成功    false:创建失败
	 */
	public static boolean createFolder(String folderPath)
	{
		try {
			File file = new File(folderPath);
			if(!file.exists() )
			{
				file.mkdirs();
			}
		} catch (Exception e) {
			logger.error("create folder fail", e);
			return false;
		}

		return true;
	}

	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
	public static ApiResult uploadFile(CommonsMultipartFile file){
		String realPath = UrlConstants.REAL_PATH + UrlConstants.UPLOAD_TXT + UrlConstants.SAVE_File_PATH;
		String path = UrlConstants.RES_URL + UrlConstants.SAVE_File_PATH;
		FileVO fileVO = new FileVO();
		if (!file.isEmpty()) {
			try {
				InputStream inputStream = (InputStream) file.getInputStream();


				ApiResult resultUtil = FileUtil.checkFileSize(file);
				if(resultUtil.getCode() != 1){
					return resultUtil;
				}
				// 上传文件，返回文件访问路径

				BufferedInputStream bis = new BufferedInputStream(inputStream);

				//生成文件名
				String newFileName = FileUtil.makeFileName(file);
				logger.info("upload file real path " + realPath + newFileName);

				createFolder(realPath);

				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(realPath + newFileName));
				byte[] buff = new byte[1024];
				int len = 1;
				while ((len = bis.read(buff)) != -1) {
					bos.write(buff, 0, len);
				}
				bos.close();
				bis.close();

				logger.info("upload file url " + path + newFileName);
				//存到file
				ApiBFile apiBFile = new ApiBFile();
				apiBFile.setUrl("fileImport/"+newFileName);
				apiBFile.setFileType("file");
				apiBFile.setCategory("file");
				apiBFile.setIsHide(0);
				ApiResult result = fileFacade.saveBfile(apiBFile);

				fileVO = new FileVO(Integer.valueOf(result.getMsg()), path + newFileName);
				return new ApiResult(ResultEnum.Success, fileVO);
			} catch (Exception e) {
				e.printStackTrace();
				return new ApiResult(ResultEnum.Error);
			}
		}
		return new ApiResult(ResultEnum.FileEmptyError);
	}

}
