package com.guangde.business.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class UserUtil
{
    private static String[] randomStr = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    public static final String KEY_1 = "CGUWClf80AtMVGqwjD6xR0ATbcry8Gp8";
	 public static final String url = "http://api.map.baidu.com/geocoder/v2/?callback=renderOption&output=json&address=addressValue&ak=akValue";
    /**
     * 获得保护的用户真实名
     * 非数字字符不加密，只有连续连续5位数字，加密成3位数字，后面数字替换成星号
     * @param name
     * @return
     */
    public static String getSafeName(String name)
    {
        if (isMobile(name))
        {
            name = name.substring(0, 3) + "****" + name.substring(7, 11);
        }
        return name;
        
    }
    
    public static boolean isMobile(String str)
    {
        if (StringUtils.isBlank(str) || str.length() != 11)
        {
            return false;
        }
        String reg = "1(2|3|4|5|6|7|8)[0-9]{9}";
        return str.matches(reg);
    }
    
    /**
     * 获取助善口令
     * @return
     */
    public static String getGoodPassWord()
    {
        
        String[] code = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "X", "Y", "S", "V", "W", "T", "U", "Z"};
        
        Random r = new Random();
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < 10; i++)
        {
            str.append(code[r.nextInt(26)]);
        }
        
        String goodPassWord = str.toString();
        
        return goodPassWord;
    }
    
    // 生成随机数
    public static String randonNums(int length)
    {
        StringBuffer randomCode = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++)
        {
            String rand = randomStr[random.nextInt(10)];
            randomCode.append(rand);
        }
        return randomCode.toString();
    }
    
    /**
     * 生成 trunNum
     * @return
     */
    public static String uniqueCode()
    {
        Date date = new Date();
        return date.getTime() + randonNums(4);
    }
    
    /**
     * 获取捐款人类型
     * @param userType
     * @return
     */
    public static String getDonorType(String userType)
    {
        String donorType = "";
        //游客 
        if (userType.equals("touristUsers"))
        {
            donorType = "touristPers";
        }
        // 个人
        else if (userType.equals("individualUsers"))
        {
            donorType = "InternalPers";
        }
        // 企业
        else if (userType.equals("enterpriseUsers"))
        {
            donorType = "enterprisePers";
        }
        // 外部
        else
        {
            donorType = "externalPers";
        }
        return donorType;
    }
    /** 
     * 返回输入地址的经纬度坐标 
     * key lng(经度),lat(纬度) 
     */  
    public static Map<String,String> getGeocoderLatitude(String address){  
        BufferedReader in = null;  
        try {  
            //将地址转换成utf-8的16进制  
            address = URLEncoder.encode(address, "UTF-8");
            String urls =url.replace("addressValue", address).replace("akValue", KEY_1);
            URL tirc = new URL(urls);
            in = new BufferedReader(new InputStreamReader(tirc.openStream(),"UTF-8"));  
            String res;  
            StringBuilder sb = new StringBuilder("");  
            while((res = in.readLine())!=null){  
                sb.append(res.trim());  
            }  
            String str = sb.toString();  
            Map<String,String> map = null;  
            if(StringUtils.isNotEmpty(str)){  
                int lngStart = str.indexOf("lng\":");  
                int lngEnd = str.indexOf(",\"lat");  
                int latEnd = str.indexOf("},\"precise");  
                if(lngStart > 0 && lngEnd > 0 && latEnd > 0){  
                    String lng = str.substring(lngStart+5, lngEnd);  
                    String lat = str.substring(lngEnd+7, latEnd);  
                    map = new HashMap<String,String>();  
                    map.put("lng", lng);  
                    map.put("lat", lat);  
                    return map;  
                }  
            }  
        }catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return null;  
    }
}
