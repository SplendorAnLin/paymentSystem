package com.yl.online.gateway.utils;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 日期计算工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月17日
 * @version V1.0.0
 */
public class DateCompUtils extends DateUtils{
	
	private static DateCompUtils compUtils = null;
	
	private static DateCompUtils instance(){
		if(compUtils == null){
			return new DateCompUtils();
		}
		return compUtils;
	}
	
	@SuppressWarnings("static-access")
	public static Date addDate(Date date, String dateStr){
		if(dateStr.lastIndexOf("D")>-1||dateStr.lastIndexOf("d")>-1){
			return instance().addDays(date, Integer.parseInt(dateStr.substring(0, dateStr.length()-1)));
		}
		if(dateStr.lastIndexOf("H")>-1||dateStr.lastIndexOf("h")>-1){
			return instance().addHours(date, Integer.parseInt(dateStr.substring(0, dateStr.length()-1)));
		}
		if(dateStr.lastIndexOf("M")>-1||dateStr.lastIndexOf("m")>-1){
			return instance().addMinutes(date, Integer.parseInt(dateStr.substring(0, dateStr.length()-1)));
		}
		return new Date();
	}

	public static void main(String[] args) {
		System.out.println(DateCompUtils.addDate(new Date(), "10m"));
	}
}
