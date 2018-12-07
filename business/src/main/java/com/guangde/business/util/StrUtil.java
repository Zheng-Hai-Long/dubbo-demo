package com.guangde.business.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public final class StrUtil {

	private StrUtil() {

	}


	/**
	 * 
	 * @param Str
	 * @return true 整数，false非整数
	 */
	public static boolean isInt(String str) {

		if (StringUtils.isEmpty(str)) {
			return false;
		}

		try {
			Integer.parseInt(str);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @param str
	 * @return true正整数 false 非正整数
	 */
	public static boolean isPosInt(String str) {

		if (StringUtils.isEmpty(str)) {
			return false;
		}

		boolean isNum = str.matches("[0-9]+");

		return isNum && isInt(str);
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPosLong(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		try {
			return Long.parseLong(str) > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isLong(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		try {
			Long.parseLong(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDouble(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 获得本地IP地址
	 * 
	 * @return
	 */
	public static List<String> getLocalIPList() {
		List<String> ipList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			NetworkInterface networkInterface;
			Enumeration<InetAddress> inetAddresses;
			InetAddress inetAddress;
			String ip;
			while (networkInterfaces.hasMoreElements()) {
				networkInterface = networkInterfaces.nextElement();
				inetAddresses = networkInterface.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					inetAddress = inetAddresses.nextElement();
					if (inetAddress != null
							&& inetAddress instanceof Inet4Address) { // IPV4
						ip = inetAddress.getHostAddress();
						ipList.add(ip);
					}
				}
			}
		} catch (SocketException e) {
            //			e.printStackTrace();
		}
		return ipList;
	}

	/**
	 * 暂停
	 * 
	 * @param second
	 *            单位：秒
	 */
	public static void sleep(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {

		}
	}
    
    public static boolean isEmpty(String str)
    {
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }
    
    public static List<Integer> convertIdList(String str, String regex)
    {
        String[] ss = str.split(regex);
        
        List<Integer> list = new ArrayList<Integer>();
        
        for (String temp : ss)
        {
        	if(!StringUtils.isEmpty(temp))
        	{
        		list.add(Integer.parseInt(temp));
        	}
        }

        return list;
    }

	/**
	 * 位数不够补0
	 * @param str
	 * @param strLength
	 * @return
	 */
	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}
}
