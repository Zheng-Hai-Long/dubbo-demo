package com.guangde.home.utils;

import com.guangde.enums.ResultEnum;
import com.guangde.home.constant.ImageTypeConstants;
import com.guangde.pojo.ApiResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 生成验证码图片
 */
public class ImageUtil {

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
	 * 根据图片的类型返回图片的类型名称  reverseside 1：反面 ;obverseside 2:正面; HoldingIDcard 3:手持身份证
	 *
	 * @param type
	 */
	public static String typename(String type) {

		if ("1".equals(type)) {
			return ImageTypeConstants.HEAD_IMAGE;
		}else if("2".equals(type)){
			return ImageTypeConstants.ID_IMAGE;
		}else if("3".equals(type)){
			return ImageTypeConstants.PROJECT_COVER_IMAGE;
		}else if("4".equals(type)){
			return ImageTypeConstants.PRODUCT_COVER_IMAGE;
		}else if("5".equals(type)){
			return ImageTypeConstants.COMMON_FORM_IMAGE;
		}
		return "";
	}

	/*
	 * 根据图片的扩展名,判断是否符合上传要求
	 *
	 * @param MultipartFile
	 */
	public static String checkExt(MultipartFile imgFile,String type) {

		// 定义一个数组，用于保存可上传的文件类型
		List<String> fileTypes = new ArrayList<String>();
		fileTypes.add("jpg");
		fileTypes.add("jpeg");
		fileTypes.add("bmp");
		//fileTypes.add("gif");
		fileTypes.add("png");

		String fileName = imgFile.getOriginalFilename();
		// 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		// 对扩展名进行小写转换
		ext = ext.toLowerCase();
		if (fileTypes.contains(ext)) { // 如果扩展名属于允许上传的类型，则创建文件
			return ext.equals("png")?"jpg":ext;//去除微信分享忽略的png格式
		}
		return "error";
	}

	public static String checkSize(MultipartFile imgFile,String type) {
		// 判断图片大小是否大于2M
		if("reverseside".equals(type)||"obverseside".equals(type)||"holdingIDcard".equals(type)){
			if (imgFile.getSize() > 10485760) {
				return "error";
			}
		}else{
			if (imgFile.getSize() > 10485760) {
				return "error";
			}
		}
		return "ok";
	}
	/**
	 *
	 * @param imgFile
	 * @param type
	 * @return 1正常，20001，文件太大，20002，文件类型不对
	 */
	public static ApiResult checkImg(MultipartFile imgFile, String type){
		if ("error".equals(ImageUtil.checkSize(imgFile,type))) {
			// 文件的大小
			return new ApiResult(ResultEnum.ImageSizeError);
		}
		if ("error".equals(ImageUtil.checkExt(imgFile,type))) {
			// 文件的扩展名不符合要求
			return new ApiResult(ResultEnum.ImageTypeError);
		}
		return new ApiResult(ResultEnum.Success);
	}
}
