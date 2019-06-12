package com.yl.customer.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

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
