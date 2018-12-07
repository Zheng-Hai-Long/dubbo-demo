package com.guangde.home.utils;

import java.util.UUID;

/**
 * Created by Administrator on 2018/4/10.
 */
public class UUIDUtil {

    private static long num=0;

    /**
     * 随机生成UUID
     * @return
     */
    public static synchronized String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }
    /**
     * 根据字符串生成固定UUID
     * @param name
     * @return
     */
    public static synchronized String getUUID(String name){
        UUID uuid=UUID.nameUUIDFromBytes(name.getBytes());
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }

    /**
     * 得到32位的uuid
     * @return
     */
    public static String getUUID32(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
