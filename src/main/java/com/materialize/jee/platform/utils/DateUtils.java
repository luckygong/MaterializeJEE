package com.materialize.jee.platform.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	/**
     * 获取指定月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getCurrent() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * 字符串转日期的工具类
	 * @param dateString 日期字符
	 * @param format 字符串格式
	 * @return
	 * @throws ParseException
	 */
	public static final Date toDate(String dateString, String format) throws ParseException  {
		SimpleDateFormat dtFormat = new SimpleDateFormat(format);
		return dtFormat.parse(dateString);
	}

	/**
	 * 返回指定格式的日期
	 * 
	 * @param date
	 * @param length
	 * @return
	 */
	public static final String formatDate(Date date, String formatString) { 
		SimpleDateFormat format = new SimpleDateFormat(formatString); 
		return  format.format(date); 

	}
	/**
	 * 返回指定格式yyyy-MM-dd的日期
	 * 
	 * @param date
	 * @param length
	 * @return
	 */
	public static final String formatDate(Date date) {  
		return  formatDate(date, "yyyy-MM-dd");

	}
	
	/**
	 * 获取指定日期的Calendar
	 * @param date
	 * @return
	 */
	public static Calendar date2Calendar(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		return ca;
	}
	
	/**
	 * 获取当前月的第一天
	 * 
	 * @return date
	 */
	public static Date getFirstDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前月的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}
	
	/**
     * 获取指定月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获取指定月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前日期星期一日期
	 * 
	 * @return date
	 */
	public static Date getFirstDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		return calendar.getTime();
	}

	/**
	 * 获取当前日期星期日日期
	 * 
	 * @return date
	 */
	public static Date getLastDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Monday
		return calendar.getTime();
	}

	/**
	 * 获取指定日期星期一日期
	 * 
	 * @param 指定日期
	 * @return date
	 */
	public static Date getFirstDayOfWeek(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Monday
		return calendar.getTime();
	}
 
	/**
	 * 获取指定日期星期一日期
	 * 
	 * @param 指定日期
	 * @return date
	 */
	public static Date getLastDayOfWeek(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Monday
		return calendar.getTime();
	}
	
	/**
	 * 日期加减
	 * 
	 * @param date
	 * @return
	 */
	public static Date addDay(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
	
	/**
	 * 月份加减
	 * 
	 * @param date
	 * @return
	 */
	public static Date addMonth(Date date, int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}
	
	/**
	 * 年加减
	 * 
	 * @param date
	 * @return
	 */
	public static Date addYear(Date date, int years) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, years);
		return calendar.getTime();
	}
	
	/**
	 * 分钟加减
	 * 
	 * @param date
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static int getNowYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取当前月份
	 * 
	 * @return
	 */
	public static int getNowMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/** 
     * 获得当前月中天数
     *  
     * @return 
     */  
    public static int getDay() {  
    	Calendar calendar = Calendar.getInstance();
    	return calendar.get(Calendar.DAY_OF_MONTH);
    }  
 
	/**
	 * 获取当月天数
	 * 
	 * @return
	 */
	public static int getNowMonthDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getActualMaximum(Calendar.DATE);
	}
	
	/**
	 * 计算两个日期之间相差的天数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int diffdates(Date beginDate, Date endDate){
		int days = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		 
		c1.setTime(beginDate);
		c2.setTime(endDate);
		 
		while(c1.before(c2)){
			days++;
		    c1.add(Calendar.DAY_OF_YEAR, 1);
		}
		 
		return days;
	}
	
	public static void main(String[] args) {
		System.out.println(formatDate(getLastDayOfWeek()));
	}
}
