package com.yl.boss.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 新增时间
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class AddDate {
	public static Date addOneDay(Date time) {
		if (time!=null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(time);
			cal.add(Calendar.DATE, 1);
			cal.add(Calendar.SECOND, -1);
			return cal.getTime();
		}
		return null;
	}
}
