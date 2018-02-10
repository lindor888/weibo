package com.ctvit.weibo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ctvit.weibo.exception.DateException;

public class TimeUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 一个小时
	 */
	public static long HOUR = 1000 * 60 * 60;
	
	/**
	 * 一天
	 */
	public static long DAY = 1000 * 60 * 60 *24;
	
	/**
	 * 转换时间为时间戳
	 * @param time
	 * @return
	 */
	public static int tranTimeToSecond(String time){
		Date infactDate = null;
		try {
			infactDate = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (int)(infactDate.getTime()/1000);
	}
	
	/**
	 * 转换时间为date类型
	 * @param time
	 * @return
	 */
	public static Date tranTimeToDate(String time){
		Date infactDate = null;
		try {
			infactDate = sdf_day.parse(time);
		} catch (ParseException e) {
			throw new DateException(e);
		}
		return infactDate;
	}
	
	public static Date tranDateToDate(Date date){
		String dateStr = sdf_day.format(date);
		Date infactDate = null;
		try {
			infactDate = sdf_day.parse(dateStr);
		} catch (ParseException e) {
			throw new DateException(e);
		}
		return infactDate;
	}
	
	/**
	 * 获取第count天的时间(例count=1是明天时间；count=-1是昨天时间；依此类推)
	 * @param date
	 * @param count
	 * @return
	 */
	public static Date getCountDay(Date date, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}
	
}
