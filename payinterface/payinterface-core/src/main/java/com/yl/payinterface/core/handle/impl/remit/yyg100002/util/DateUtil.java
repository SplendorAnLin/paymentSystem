package com.yl.payinterface.core.handle.impl.remit.yyg100002.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	static public Date getNow() {
		Date datetime = new Date();
		String now = getDateTimeStr(datetime);
		java.sql.Timestamp time = new java.sql.Timestamp(parseDateTime(now).getTime());
		return time;
	}

	/**
	 * getDateStr get a string with format YYYY-MM-DD from a Date object
	 *
	 * @param date
	 *            date
	 * @return String
	 */
	static public String getDateStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	static public String getDateStrTOchina(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		return format.format(date);
	}

	/**
	 * getDateStr get a string with format YYYY-M-D from a Date object
	 *
	 * @param date
	 *            date
	 * @return String
	 */
	static public String getDateShortStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
		return format.format(date);
	}

	static public String getYear(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		return format.format(date);
	}

	static public String getDateStrC(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(date);
	}

	static public String getDateStrCompact(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String str = format.format(date);
		return str;
	}

	static public String getTodayDate() {
		return getDateStrCompact(new Date());
	}

	static public String getTimeStrCompact(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		String str = format.format(date);
		return str;
	}

	static public String getDateStrCompact2(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = format.format(date);
		return str;
	}

	static public String getTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}

	/**
	 * @return 准分
	 */
	static public String getmmTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		return format.format(new Date()) + "00";
	}

	/**
	 * getDateStr get a string with format YYYY-MM-DD HH:mm:ss from a Date
	 * object
	 *
	 * @param date
	 *            date
	 * @return String
	 */
	static public String getDateTimeStr(Date date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(date);
		} catch (Exception ex) {
			return "";
		}
	}

	static public String getNowDateTimeStr() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(new Date());
		} catch (Exception ex) {
			return "";
		}
	}

	static public String getDateTimeStrC(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return format.format(date);
	}

	public static String getCurDateStr(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

	/**
	 * Parses text in 'YYYY-MM-DD' format to produce a date.
	 *
	 * @param s
	 *            the text
	 * @return Date
	 * @throws ParseException
	 */
	static public Date parseDate(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(s);
	}

	/**
	 * Parses text in 'YYYYMMDD' format to produce a date.
	 *
	 * @param s
	 *            the text
	 * @return Date
	 * @throws ParseException
	 */
	static public Date parseDateShort(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.parse(s);
	}

	static public Date parseDateC(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.parse(s);
	}

	/**
	 * Parses text in 'YYYY-MM-DD' format to produce a date.
	 *
	 * @param s
	 *            the text
	 * @return Date
	 * @throws ParseException
	 */
	static public Date parseDateTime(String s) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.parse(s);
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * Parses text in 'YYYY-MM-DD' format to produce a date.
	 *
	 * @param s
	 *            the text
	 * @return Date
	 * @throws ParseException
	 */
	static public Date parseDateTimeNoFormat(String s) {
		try {
			SimpleDateFormat format = new SimpleDateFormat();
			return format.parse(s);
		} catch (Exception ex) {
			return null;
		}

	}

	static public String getDateByLongTime(String longTime) {
		try {
			if (longTime != null && longTime.length() > 0) {
				Date date = new Date(Long.parseLong(longTime));
				return getDateTimeStr(date);
			}
		} catch (Exception ex) {
			return "";
		}

		return "";
	}

	static public Date parseDateTimeC(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return format.parse(s);
	}

	/**
	 * Parses text in 'HH:mm:ss' format to produce a time.
	 *
	 * @param s
	 *            the text
	 * @return Date
	 * @throws ParseException
	 */
	static public Date parseTime(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.parse(s);
	}

	static public Date parseTimeC(String s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("HH时mm分ss秒");
		return format.parse(s);
	}

	static public int yearOfDate(Date s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String d = format.format(s);
		return Integer.parseInt(d.substring(0, 4));
	}

	static public int monthOfDate(Date s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String d = format.format(s);
		return Integer.parseInt(d.substring(5, 7));
	}

	static public int dayOfDate(Date s) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String d = format.format(s);
		return Integer.parseInt(d.substring(8, 10));
	}

	static public int diffDateD(Date sd, Date ed) throws ParseException {
		return Math.round((ed.getTime() - sd.getTime()) / 86400000) + 1;
	}

	static public int diffDateM(int sym, int eym) throws ParseException {
		return (Math.round(eym / Float.valueOf("100")) - Math.round(sym / Float.valueOf("100"))) * 12 + (eym % 100 - sym % 100) + 1;
	}

	static public Date getNextMonthFirstDate(Date date) {
		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(date);
		scalendar.add(Calendar.MONTH, 1);
		scalendar.set(Calendar.DATE, 1);
		return new Date(scalendar.getTime().getTime());
	}

	static public Date subDate(Date date, int dayCount) throws ParseException {
		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(date);
		scalendar.add(Calendar.DATE, -dayCount);
		return new Date(scalendar.getTime().getTime());
	}

	/**
	 * Get first day of the month.
	 *
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @return Date first day of the month.
	 * @throws ParseException
	 */
	static public Date getFirstDay(String year, String month) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(year + "-" + month + "-1");
	}

	static public Date getFirstDay(int year, int month) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(year + "-" + month + "-1");
	}

	static public Date getLastDay(String year, String month) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(year + "-" + month + "-1");

		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(date);
		scalendar.add(Calendar.MONTH, 1);
		scalendar.add(Calendar.DATE, -1);
		date = scalendar.getTime();
		return date;
	}

	static public Date getLastDay(int year, int month) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(year + "-" + month + "-1");

		Calendar scalendar = new GregorianCalendar();
		scalendar.setTime(date);
		scalendar.add(Calendar.MONTH, 1);
		scalendar.add(Calendar.DATE, -1);
		date = scalendar.getTime();
		return date;
	}

	/**
	 * getToday get todat string with format YYYY-MM-DD from a Date object
	 *
	 * @param date
	 *            date
	 * @return String
	 */

	static public String getTodayStr() {
		Calendar calendar = Calendar.getInstance();
		return getDateStr(calendar.getTime());
	}

	static public Date getToday() {
		return new Date(System.currentTimeMillis());
	}

	static public Date getTodayYMD() {
		try {
			return parseDate(getTodayStr());
		} catch (Exception ex) {

		}

		return null;
	}

	/**
	 * 获取当前时间点，格式为yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	static public String getTodayAndTime() {
		return getDateTimeStr(new Timestamp(System.currentTimeMillis()));
	}

	/**
	 * 得到昨天的当前时间点
	 *
	 * @return
	 */
	static public String getBeforDayAndTime(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -days);
		return getDateTimeStr(calendar.getTime());
	}

	static public String getBeforDay(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -days);
		return getDateStr(calendar.getTime());
	}

	static public String getBeforDayStr(int days){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -days);
		return getDateStrCompact(calendar.getTime());
	}

	static public String getTodayC() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		return getDateStrC(calendar.getTime());
	}

	static public String getWeekC() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		return dayNames[week - 1];
	}

	static public String getTodayAndWeekC() throws ParseException {
		return getTodayC() + "&nbsp;&nbsp;&nbsp;&nbsp;" + getWeekC();
	}

	// 获取相隔天数
	static public long getDistinceDay(String beforedate, String afterdate) throws ParseException {
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
		long dayCount = 0;
		try {
			java.util.Date d1 = d.parse(beforedate);
			java.util.Date d2 = d.parse(afterdate);

			dayCount = (d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000);

		} catch (ParseException e) {
			System.out.println("Date parse error!");
			// throw e;
		}
		return dayCount;
	}

	// 获取相隔天数
	static public long getDistinceDay(Date beforedate, Date afterdate) throws ParseException {
		long dayCount = 0;

		try {
			dayCount = (afterdate.getTime() - beforedate.getTime()) / (24 * 60 * 60 * 1000);

		} catch (Exception e) {
		}
		return dayCount;
	}

	static public long getDistinceDay(java.sql.Date beforedate, java.sql.Date afterdate) throws ParseException {
		long dayCount = 0;

		try {
			dayCount = (afterdate.getTime() - beforedate.getTime()) / (24 * 60 * 60 * 1000);

		} catch (Exception e) {
		}
		return dayCount;
	}

	// 获取相隔天数
	static public long getDistinceDay(String beforedate) throws ParseException {
		return getDistinceDay(beforedate, getTodayStr());
	}

	// 获取相隔时间数
	static public long getDistinceTime(String beforeDateTime, String afterDateTime) throws ParseException {
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long timeCount = 0;
		try {
			java.util.Date d1 = d.parse(beforeDateTime);
			java.util.Date d2 = d.parse(afterDateTime);

			timeCount = (d2.getTime() - d1.getTime());

		} catch (ParseException e) {
			System.out.println("Date parse error!");
			throw e;
		}
		return timeCount;
	}

	// 获取相隔时间数
	static public long getDistinceTime(String beforeDateTime) throws ParseException {
		return getDistinceTime(beforeDateTime, new Timestamp(System.currentTimeMillis()).toString());
	}

	// 获取相隔分钟数
	static public long getDistinceMinute(String beforeDateTime, String afterDateTime) throws ParseException {
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long timeCount = 0;

		java.util.Date d1 = d.parse(beforeDateTime);
		java.util.Date d2 = d.parse(afterDateTime);
		timeCount = d2.getTime() - d1.getTime();

		return timeCount;
	}

	/**
	 * 
	 * @param date
	 *            要转换的日期
	 * @return
	 */
	public static String getFormatDateYMDEHm(String date) {
		SimpleDateFormat source = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat target = new SimpleDateFormat("yyyy年MM月dd日 E HH:m");
		Calendar c = GregorianCalendar.getInstance();
		try {
			c.setTime(source.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return target.format(c.getTime());
	}

	/**
	 * 字符串的日期格式的计算
	 * 
	 * @throws ParseException
	 */
	public static String daysBetween(String smdate, String bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		long between_days = 0;
		try {
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			between_days = (time2 - time1) / (1000 * 3600 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return String.valueOf(between_days);
	}

	/**
	 * 判断是否超过24小时
	 * 
	 * @param date1
	 * @param date2
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean judgmentDate(String date1, String date2) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start = sdf.parse(date1);
		Date end = sdf.parse(date2);
		long cha = end.getTime() - start.getTime();
		if (cha < 0) {
			return false;
		}
		double result = cha * 1.0 / (1000 * 60 * 60);
		if (result <= 24) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param date
	 *            要转换的日期
	 * @return
	 */
	public static String getFormatDateYMDEAHm(String date) {
		SimpleDateFormat source = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat target = new SimpleDateFormat("yyyy年MM月dd日EaHH:m");
		Calendar c = GregorianCalendar.getInstance();
		try {
			c.setTime(source.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return target.format(c.getTime());
	}

	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public static Timestamp getSystemTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String getmmTimeLast10(String time) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date(format.parse(time).getTime() - 10 * 60 * 1000));
	}

	/**
	 * 判断是否大于 当天的 15：30
	 */
	public static boolean judgmentDate1530(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String date1530 = date.substring(0, 8) + "153000";
		Date start = sdf.parse(date);
		Date end = sdf.parse(date1530);
		long cha = end.getTime() - start.getTime();
		if (cha <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 是否 本周最后一天 1530
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static boolean judgmentDateWeek1530(String date) throws Exception {
		if (!judgmentDate1530(date))
			return false;
		String week = getThisWeekEnd();
		if (week.equals(date.substring(0, 8)))
			return true;
		return false;
	}

	/**
	 * 是否 本周五 1530
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static boolean judgmentDateFri1530(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(date));
		// 今天是一周中的第几天
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 6)
			return true;
		return false;
	}

	/**
	 * 这个周末 日期
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getThisWeekEnd() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		// 今天是一周中的第几天
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		if (c.getFirstDayOfWeek() == Calendar.SUNDAY) {
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		// 计算一周开始的日期
		c.add(Calendar.DAY_OF_MONTH, 7 - dayOfWeek);
		return sdf.format(c.getTime());
	}
	
	public static String getThisWeekFri() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		// 今天是一周中的第几天
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return sdf.format(c.getTime());
	}

	public static String getThisMonthFri() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		// 今天是一周中的第几天
		c.set(Calendar.DAY_OF_MONTH, 1);
		return sdf.format(c.getTime());
	}
	
	/**
	 * 上周日 日期
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getLastWeekEnd() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		// 今天是一周中的第几天
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		if (c.getFirstDayOfWeek() == Calendar.SUNDAY) {
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		// 计算一周开始的日期
		c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
		return sdf.format(c.getTime());
	}

	/**
	 * 上周五 日期
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getLastWeekFri() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		// 今天是一周中的第几天
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		if (c.getFirstDayOfWeek() == Calendar.SUNDAY) {
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		// 计算一周开始的日期
		c.add(Calendar.DAY_OF_MONTH, -dayOfWeek - 2);
		return sdf.format(c.getTime());
	}

	/**
	 * 上月底日期
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getLastMonthEnd() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		// 今天是一周中的第几天
		int dayOfmonth = c.get(Calendar.DAY_OF_MONTH);
		// 计算一周开始的日期
		c.add(Calendar.DAY_OF_YEAR, -dayOfmonth);
		return sdf.format(c.getTime());
	}

	/**
	 * 是否本月最后一天 1530后
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static boolean judgmentDateMonth1530(String date) throws Exception {
		if (!judgmentDate1530(date))
			return false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// 获取Calendar
		Calendar calendar = Calendar.getInstance();
		// 设置日期为本月最大日期
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		// 打印
		String week = sdf.format(calendar.getTime());
		if (week.equals(date.substring(0, 8)))
			return true;
		return false;
	}

	/**
	 * 前一天 日期 yyyyMMdd
	 * 
	 * @param date
	 *            yyyyMMddhhmmss
	 * @return
	 * @throws Exception
	 */
	public static String getLastDate(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date parseDate = sdf.parse(date.substring(0, 8));
		return sdf.format(subDate(parseDate, 1));
	}

	public static String getNextDate(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date parseDate = sdf.parse(date.substring(0, 8));
		return sdf.format(subDate(parseDate, -1));
	}

	/**
	 * @param date
	 *            yyyyMMddhhmmss
	 * @param createTime
	 *            yyyyMMddhhmmss
	 * @return
	 * @throws ParseException
	 */
	public static long getDisLong(String date, String createTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date parseDate = sdf.parse(date);
		Date createDate = sdf.parse(createTime);
		return parseDate.getTime() - createDate.getTime();
	}
	/**
	 * 只在周末 不交易
	 * 
	 * @return
	 */
	public static boolean checkTradingdayFXEU() {
		Calendar c = Calendar.getInstance();
		// 今天是一周中的第几天
		int dayOfmonth = c.get(Calendar.DAY_OF_MONTH);
		if (dayOfmonth != 6 && dayOfmonth != 0)
			return true;
		return false;
	}

	/**
	 * 是否周末
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static boolean checkTradingday(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date parseDate = sdf.parse(date.substring(0, 8));
		Calendar c = Calendar.getInstance();
		c.setTime(parseDate);
		// 今天是一周中的第几天
		int dayOfmonth = c.get(Calendar.DAY_OF_WEEK);
		if (dayOfmonth == 5 || dayOfmonth == 6)
			return true;
		return false;
	}

	/**
	 * @return 时间加几分钟，负数为减
	 * @throws ParseException
	 */
	static public String getmmTimeAddMin(String time, int min) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = format.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// calendar.add(Calendar.WEEK_OF_YEAR, -1);
		calendar.add(Calendar.MINUTE, min);
		date = calendar.getTime();
		return format.format(date);
	}

	/**
	 * @return 时间加几天，负数为减
	 * @throws ParseException
	 */
	static public String getmmTimeAddDay(String time, int day) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = format.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// calendar.add(Calendar.WEEK_OF_YEAR, -1);
		calendar.add(Calendar.DATE, day);
		date = calendar.getTime();
		return format.format(date);
	}

	/**
	 * @return 时间加几周，负数为减
	 * @throws ParseException
	 */
	static public String getmmTimeAddWeek(String time, int week) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = format.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// calendar.add(Calendar.WEEK_OF_YEAR, -1);
		calendar.add(Calendar.WEDNESDAY, week);
		date = calendar.getTime();
		return format.format(date);
	}

	/**
	 * @return 时间加几个月，负数为减
	 * @throws ParseException
	 */
	static public String getmmTimeAddMonth(String time, int month) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = format.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// calendar.add(Calendar.WEEK_OF_YEAR, -1);
		calendar.add(Calendar.MONTH, month);
		date = calendar.getTime();
		return format.format(date);
	}

	/**
	 * @return 时间加几年，负数为减
	 * @throws ParseException
	 */
	static public String getmmTimeAddYear(String time, int year) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = format.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// calendar.add(Calendar.WEEK_OF_YEAR, -1);
		calendar.add(Calendar.YEAR, year);
		date = calendar.getTime();
		return format.format(date);
	}

	/**
	 * 获取当前系统日期
	 * 
	 * @return
	 */
	static public String getCurrSystemDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String str = format.format(new Date());
		return str;
	}

	/**
	 * 获取当前系统时间
	 * 
	 * @return
	 */
	static public String getCurrSystemTime() {
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		String str = format.format(new Date());
		return str;
	}

	/**
	 * 获取账单查询结束日期
	 * 
	 * @param args
	 * @throws Exception
	 */
	static public String getendTime(int dateType) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar instance = Calendar.getInstance();
		String endTime = "";
		if (dateType == 0) {// 任意时间
			endTime = format.format(new Date());
		}
		if (dateType == 1) {// 一周内
			instance.add(Calendar.DATE, -7);// 减7天
			endTime = format.format(instance.getTime());
		}
		if (dateType == 2) {// 一个月内
			instance.add(Calendar.MARCH, -1);
			endTime = format.format(instance.getTime());
		}
		if (dateType == 3) {// 三个月内
			instance.add(Calendar.MARCH, -3);
			endTime = format.format(instance.getTime());
		}
		if (dateType == 4) {// 六个月内
			instance.add(Calendar.MARCH, -6);
			endTime = format.format(instance.getTime());
		}
		if (dateType == 5) {// 一年内
			instance.add(Calendar.YEAR, -1);
			endTime = format.format(instance.getTime());
		}

		return endTime;
	}

	/**
	 * 
	 * @param args
	 * @throws ParseException
	 * @throws Exception
	 */
	static public Long getendDay(int dateType) throws ParseException {
		String endTime = DateUtil.getDateStr(new Date());// 结束时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar instance = Calendar.getInstance();
		Long day = 0L;
		String startTime = "";
		if (dateType == 0) {// 任意时间
			return day;
		}
		if (dateType == 1) {// 一周内
			instance.add(Calendar.DATE, -7);// 减7天
			startTime = format.format(instance.getTime());
			day = DateUtil.getDistinceDay(startTime, endTime);
			return day;
		}
		if (dateType == 2) {// 一个月内
			instance.add(Calendar.MARCH, -1);
			startTime = format.format(instance.getTime());
			day = DateUtil.getDistinceDay(startTime, endTime);
			return day;
		}
		if (dateType == 3) {// 三个月内
			instance.add(Calendar.MARCH, -3);
			startTime = format.format(instance.getTime());
			day = DateUtil.getDistinceDay(startTime, endTime);
			return day;
		}
		if (dateType == 4) {// 六个月内
			instance.add(Calendar.MARCH, -6);
			startTime = format.format(instance.getTime());
			day = DateUtil.getDistinceDay(startTime, endTime);
			return day;
		}
		if (dateType == 5) {// 一年内
			instance.add(Calendar.YEAR, -1);
			startTime = format.format(instance.getTime());
			day = DateUtil.getDistinceDay(startTime, endTime);
			return day;
		}

		return day;
	}

	public static boolean after(String timeNow, String now) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		if(sdf.parse(timeNow).after(sdf.parse(now))){
			return true;
		}
		return false;
	}
	
	public static boolean afterTime(String timeNow, String now) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		if(sdf.parse(timeNow).after(sdf.parse(now))){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取传入时间intervalMinute之前的时间
	 * @param time 时间
	 * @param intervalMinute 间隔分钟数
	 * @return
	 * @throws ParseException
	 */
	public static String beforedateByMinute(String time, int intervalMinute) throws ParseException {
		 Calendar beforeTime = Calendar.getInstance();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		 beforeTime.setTime(sdf.parse(time));
		 beforeTime.add(Calendar.MINUTE,-intervalMinute);
		return sdf.format(beforeTime.getTime());
	}
}