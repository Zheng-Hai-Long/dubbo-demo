package com.tenpay.demo;
/** 
 * 项目名称：weixinpay 
 *  
 * @description:二维码生成<br> 
 * 注意生成二维码和解析过程中的编码必须为GBK，否则解析过程会出错。 
 *  
 * @author spg 
 *  
 *  
 * @version V1.0.0 
 *  
 */ 
import java.awt.Color;  
import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.geom.AffineTransform;  
import java.awt.image.AffineTransformOp;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.util.HashMap;  
import java.util.Map;  

import javax.imageio.ImageIO;  

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
   
public class QrCode  
{  
   
    /** 
     * 二维码宽高度默认200 
     */ 
    private static final int DEFAULT_IMAGE_WIDTH = 300;  
    private static final int DEFAULT_IMAGE_HEIGHT = 300;  
       
    /** 
     * 生成带图片二维码时内部图片大小 
     */ 
    private static final int INNER_IMAGE_WIDTH = 60;  
    private static final int INNER_IMAGE_HEIGHT = 60;  
    private static final int IMAGE_HALF_WIDTH = INNER_IMAGE_WIDTH / 2;  
    private static final int FRAME_WIDTH = 2;  
   
    /** 
     * 生成普通二维码 
     *  
     * @param contents 内容 
     * @param width 二维码宽度，如果小于0，则按默认大小生成 
     * @param height 二维码高度，如果小于0，则按默认大小生成 
     * @param imgPath 生成后的文件完整存放路径，包含文件名。形如D:\aa.jpg 
     */ 
    public static void encodePR(String contents, int width, int height,  
            String imgPath)  
    {  
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        // 指定纠错等级  
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  
        // 指定编码格式  
        hints.put(EncodeHintType.CHARACTER_SET, "GBK");  
        if (width <= 0 || height <= 0)  
        {  
            width = DEFAULT_IMAGE_WIDTH;  
            height = DEFAULT_IMAGE_HEIGHT;  
        }  
        try 
        {  
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,  
                    BarcodeFormat.QR_CODE, width, height, hints);  
            MatrixToImageWriter.writeToStream(bitMatrix, "jpg",  
                    new FileOutputStream(imgPath));  
        } catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
    }  
   
    /** 
     * 生成带图片的二维码 
     *  
     * @param content 
     * @param width 
     * @param height 
     * @param srcImagePath 
     * @param destImagePath 
     */ 
    public static void encodePR(String content, int width, int height,  
            String srcImagePath, String destImagePath)  
    {  
        try 
        {  
            ImageIO.write(genBarcode(content, width, height, srcImagePath),  
                    "jpg", new File(destImagePath));  
        } catch (IOException e)  
        {  
            e.printStackTrace();  
        } catch (WriterException e)  
        {  
            e.printStackTrace();  
        }  
    }  
   
    /** 
     * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标 
     *  
     * @param srcImageFile 
     *            源文件地址 
     * @param height 
     *            目标高度 
     * @param width 
     *            目标宽度 
     * @param hasFiller 
     *            比例不对时是否需要补白：true为补白; false为不补白; 
     * @throws IOException 
     */ 
    private static BufferedImage scale(String srcImageFile, int height,  
            int width, boolean hasFiller) throws IOException  
    {  
        double ratio = 0.0; // 缩放比例  
        File file = new File(srcImageFile);  
        BufferedImage srcImage = ImageIO.read(file);  
        Image destImage = srcImage.getScaledInstance(width, height,  
                BufferedImage.SCALE_SMOOTH);  
        // 计算比例  
        if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width))  
        {  
            if (srcImage.getHeight() > srcImage.getWidth())  
            {  
                ratio = (new Integer(height)).doubleValue()  
                        / srcImage.getHeight();  
            } else 
            {  
                ratio = (new Integer(width)).doubleValue()  
                        / srcImage.getWidth();  
            }  
            AffineTransformOp op = new AffineTransformOp(  
                    AffineTransform.getScaleInstance(ratio, ratio), null);  
            destImage = op.filter(srcImage, null);  
        }  
        if (hasFiller)  
        {// 补白  
            BufferedImage image = new BufferedImage(width, height,  
                    BufferedImage.TYPE_INT_RGB);  
            Graphics2D graphic = image.createGraphics();  
            graphic.setColor(Color.white);  
            graphic.fillRect(0, 0, width, height);  
            if (width == destImage.getWidth(null))  
                graphic.drawImage(destImage, 0,  
                        (height - destImage.getHeight(null)) / 2,  
                        destImage.getWidth(null), destImage.getHeight(null),  
                        Color.white, null);  
            else 
                graphic.drawImage(destImage,  
                        (width - destImage.getWidth(null)) / 2, 0,  
                        destImage.getWidth(null), destImage.getHeight(null),  
                        Color.white, null);  
            graphic.dispose();  
            destImage = image;  
        }  
        return (BufferedImage) destImage;  
    }  
   
    /** 
     * 产生带有图片的二维码缓冲图像 
     *  
     * @param content 
     * @param width 
     * @param height 
     * @param srcImagePath 
     * @return 
     * @throws WriterException 
     * @throws IOException 
     */ 
    public static BufferedImage genBarcode(String content, int width,  
            int height, String srcImagePath) throws WriterException,  
            IOException  
    {  
        // 读取源图像  
        BufferedImage scaleImage = scale(srcImagePath, INNER_IMAGE_WIDTH,  
                INNER_IMAGE_HEIGHT, true);  
        int[][] srcPixels = new int[INNER_IMAGE_WIDTH][INNER_IMAGE_HEIGHT];  
        for (int i = 0; i < scaleImage.getWidth(); i++)  
        {  
            for (int j = 0; j < scaleImage.getHeight(); j++)  
            {  
                srcPixels[i][j] = scaleImage.getRGB(i, j);  
            }  
        }  
   
        Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();  
        hint.put(EncodeHintType.CHARACTER_SET, "GBK");  
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
        // 生成二维码  
        MultiFormatWriter mutiWriter = new MultiFormatWriter();  
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,  
                width, height, hint);  
   
        // 二维矩阵转为一维像素数组  
        int halfW = matrix.getWidth() / 2;  
        int halfH = matrix.getHeight() / 2;  
        int[] pixels = new int[width * height];  
   
        for (int y = 0; y < matrix.getHeight(); y++)  
        {  
            for (int x = 0; x < matrix.getWidth(); x++)  
            {  
                // 读取图片  
                if (x > halfW - IMAGE_HALF_WIDTH  
                        && x < halfW + IMAGE_HALF_WIDTH  
                        && y > halfH - IMAGE_HALF_WIDTH  
                        && y < halfH + IMAGE_HALF_WIDTH)  
                {  
                    pixels[y * width + x] = srcPixels[x - halfW  
                            + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];  
                }  
                // 在图片四周形成边框  
                else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH  
                        && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH  
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH  
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)  
                        || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH  
                                && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH  
                                && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH  
                                + IMAGE_HALF_WIDTH + FRAME_WIDTH)  
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH  
                                && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH  
                                && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH  
                                - IMAGE_HALF_WIDTH + FRAME_WIDTH)  
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH  
                                && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH  
                                && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH  
                                + IMAGE_HALF_WIDTH + FRAME_WIDTH))  
                {  
                    pixels[y * width + x] = 0xfffffff;  
                } else 
                {  
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；  
                    pixels[y * width + x] = matrix.get(x, y) ? 0xff000000 
                            : 0xfffffff;  
                }  
            }  
        }  
   
        BufferedImage image = new BufferedImage(width, height,  
                BufferedImage.TYPE_INT_RGB);  
        image.getRaster().setDataElements(0, 0, width, height, pixels);  
   
        return image;  
    }  
   
    public static void main(String[] args)  
    {  
    	
    	String value=null;
    	// 带图片的二维的生成与解析  
		if(value == null){
			value="no";
		}
		value=System.getProperty("user.dir");
        String contents2 = "weixin://wxpay/bizpayurl?pr=9yDaupc";
        String imgPath = System.getProperty("user.dir")+"/src/main/webapp/res/images/charge/wx-pay-code.jpg"; 
        String srcPath = System.getProperty("user.dir")+"/src/main/webapp/res/images/charge/logo-pay.png"; 
         QrCode.encodePR(contents2, 300, 300, srcPath, imgPath);  
         System.out.println(value);  
    }  
    
    
}