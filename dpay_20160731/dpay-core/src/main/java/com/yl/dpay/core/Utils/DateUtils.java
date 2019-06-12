package com.yl.dpay.core.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class DateUtils {

	public static int compDate(Date date1, Date date2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date1Str = sdf.format(date1);
		String date2Str = sdf.format(date2);
		return (int) (Long.valueOf(date1Str) - Long.valueOf(date2Str));
	}
	
}
