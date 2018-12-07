package com.guangde.business.util;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateUtil {

	public static final String C_TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	public static final String C_DATE_PATTON_DEFAULT = "yyyy-MM-dd";
	public static final String C_DATA_PATTON_YYYYMMDD = "yyyyMMdd";
	public static final String C_DATA_PATTON_YYYYMMDDHHMM = "yyyy_MM_dd HH:mm";
	public static final String C_DATA_PATTON_YYMMDD = "yyMMdd";
	public static final String C_TIME_PATTON_HHMMSS = "HH:mm:ss";
	public static final String PATTON_YYYYMMDDEEEHHMMSS = "yyyy-MM-dd EEE HH:mm:ss";
	public static final String C_DATA_PATTON_YYYY = "yyyy";
	public static final long DURATION_HOUR = 1000L * 60 * 60;
	/**
	 * For log and file's date format
	 */
	public static final String LOG_DATE_TIME = "yyyyMMddHHmmssSSS";

	private DateUtil() {

	}

	public static String parseToFormatDateString(Date date, String patten) {
		SimpleDateFormat format = new SimpleDateFormat(patten);
		if (date != null) {
			return format.format(date);
		} else {
			return "";
		}
	}

	public static String getCurrentDateStr() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();

		return getFormatTime(currDate);
	}

	public static Date parse(String datetime, String pattern) {

		if (StringUtils.isEmpty(datetime) || StringUtils.isEmpty(pattern)) {
			return null;
		}

		SimpleDateFormat dateFromat = new SimpleDateFormat();
		dateFromat.applyPattern(pattern);

		try {
			return dateFromat.parse(datetime);
		} catch (ParseException e) {
			return null;
		}

	}

	public static Date parseUS(String datetime) {
		return parseUS(datetime, PATTON_YYYYMMDDEEEHHMMSS);
	}

	public static Date parseUS(String datetime, String pattern) {
		if (StringUtils.isEmpty(datetime) || StringUtils.isEmpty(pattern)) {
			return null;
		}

		SimpleDateFormat dateFromat = new SimpleDateFormat(pattern, Locale.US);

		try {
			return dateFromat.parse(datetime);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String getCurrentDateStr(String strFormat) {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();

		return format(currDate, strFormat);
	}

	public static String format(Date datetime, String pattern) {
		if (datetime == null || StringUtils.isEmpty(pattern))
			return null;

		try {
			SimpleDateFormat dateFromat = new SimpleDateFormat();
			dateFromat.applyPattern(pattern);
			return dateFromat.format(datetime);
		} catch (Exception e) {
			return null;
		}

	}

	public static String getFormatTime(Date dateTime) {
		return format(dateTime, C_TIME_PATTON_HHMMSS);
	}

	public static Date addMinuts(Date date, int minuts) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minuts);

		return calendar.getTime();
	}

	/**
	 * 计算二个时间相差的秒钟
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long getDistanceSecond(Date d1, Date d2) {
		DateFormat df = new SimpleDateFormat(C_TIME_PATTON_DEFAULT);
		try {
			long t1 = df.parse(df.format(d1)).getTime();
			long t2 = df.parse(df.format(d2)).getTime();

			return (t2 - t1) / 1000;

		} catch (ParseException e) {

			return 0;
		}

	}
	
	/**
     * 计算两个时间之间的小时数，精确到2位
     * @param time1
     * @param time2
     * @return
     */
    public static double calDoubleHours2(Date time1, Date time2) {
	    if (time1 == null || time2 == null) {
	        return 0;
	    }
       double v = (time1.getTime() - time2.getTime()) / (double)DURATION_HOUR;
       BigDecimal b = new BigDecimal(v);  
       double f1 = b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
       return f1;
    }
	
	/**
	 * 计算是否大于当前时间
	 * 
	 * @param now
	 * @param date
	 * @return
	 */
	public static boolean getNowOverDate(Date now, Date date) {
		DateFormat df = new SimpleDateFormat(C_TIME_PATTON_DEFAULT);
		try {
			long t1 = df.parse(df.format(now)).getTime();
			long t2 = df.parse(df.format(date)).getTime();

			if(t1 > t2){
				return true;
			}else {
				return false;
			}

		} catch (ParseException e) {

			return false;
		}

	}

	public static Date addDay(Date date, int days) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, days);

		return startDT.getTime();
	}

	/**
	 * 时间增加N秒
	 * 
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date addSecond(Date date, int seconds) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.SECOND, seconds);

		return startDT.getTime();
	}

	/**
	 * 
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date subSecond(Date date, int seconds) {
		seconds = 0 - seconds;
		return addSecond(date, seconds);
	}

	public static void setTime(int seconds) {
		String osName = System.getProperty("os.name");
		String cmd = null;

		if (osName.matches("^(?i)Windows.*$")) {
			String time = format(addSecond(new Date(), seconds),
					C_TIME_PATTON_HHMMSS);
			cmd = "  cmd /c time " + time;
		} else {
			String time = format(addSecond(new Date(), seconds),
					C_TIME_PATTON_HHMMSS);
			cmd = "  date -s " + time;
		}
		try {
			Runtime run = Runtime.getRuntime();

			run.exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 奖期顺延计算两个时间相差的天数
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return 天数
	 */
	public static int issueDateParse(String start, String end) {
		if (StringUtils.isBlank(start) && StringUtils.isBlank(end)) {
			return 0;
		}
		Calendar caStart = Calendar.getInstance();
		caStart.setTime(parse(start, C_DATA_PATTON_YYYYMMDD));
		int yearStart = caStart.get(Calendar.DAY_OF_YEAR);

		Calendar caEnd = Calendar.getInstance();
		caEnd.setTime(parse(end, C_DATA_PATTON_YYYYMMDD));
		int yearEnd = caEnd.get(Calendar.DAY_OF_YEAR);

		int num = yearEnd - yearStart + 1;
		return num;
	}
    
    public static Date getDate()
    {
        return new Date();
    }
    /**
     * 获取当天开始时间
     * @return
     */
    public static Date getCurrentDayStart()
    {

    	SimpleDateFormat sdf = new SimpleDateFormat(C_DATE_PATTON_DEFAULT);
    	String startdate = sdf.format(new Date());
    	String starttime = startdate + " 00:00:00";
    	sdf = new  SimpleDateFormat(C_TIME_PATTON_DEFAULT);
    	
    	Date date;
		try {
			date = sdf.parse(starttime);
		} catch (ParseException e) {
			return new Date();
		}
    	return date;
    }
    /**
     * 获取当天最后结束时间
     * @return
     */
    public static Date getCurrentDayEnd()
    {
    	SimpleDateFormat sdf = new SimpleDateFormat(C_DATE_PATTON_DEFAULT);
    	String enddate = sdf.format(new Date());
    	String endtime = enddate + " 59:59:59";
    	sdf = new  SimpleDateFormat(C_TIME_PATTON_DEFAULT);
    	
    	Date date;
		try {
			date = sdf.parse(endtime);
		} catch (ParseException e) {
			return new Date();
		}
    	return date;
    }
    
    /**
     * 判断 date是否大于等于开始日期,小于等于结束日期
     *
     * @param date 指定某个日期
     * @param from 开始日期
     * @param to   结束日期
     * @return
     * @author: zhanghailang
     */
    public static boolean isDateInRange(Date date, Date from, Date to) {
        if ((date.after(from) && date.before(to)) || date.compareTo(from) == 0 || date.compareTo(to) == 0) {
            return true;
        } else
            return false;
    }
    
    public static void main(String[] args) 
    {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sm.format(DateUtil.getCurrentDayStart()));
		System.out.println(sm.format(DateUtil.getCurrentDayEnd()));
		
		
	}
    
}
