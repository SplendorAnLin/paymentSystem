package com.yl.boss.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	/**
	 * 字符串转为日期
	 * 
	 * @param str
	 *            字符串日期
	 * @param parsePatterns
	 *            转化格式
	 * @return 日期
	 */
	public static Date parseDate(final String str, final String parsePatterns) {
		try {
			return parseDate(str, null, parsePatterns);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 转换时间为指定格式
	 * @return
	 */
	public static String parseStr(Date date,String format){
		SimpleDateFormat dateFormater = new SimpleDateFormat(format);
		return dateFormater.format(date);
	} 

	/**
	 * 获取一天的最小时间
	 * 
	 * @param date
	 *            指定日期
	 * @return 返回指定日期的最小时间
	 */
	public static Date getMinTime(Date date) {
		return truncate(date, Calendar.DATE);
	}

	/**
	 * 获取一天的最大时间
	 * 
	 * @param date
	 *            指定日期
	 * @return 返回指定日期的最大时间
	 */
	public static Date getMaxTime(Date date) {
		return addMilliseconds(addDays(truncate(date, Calendar.DATE), 1), -1);
	}
}
