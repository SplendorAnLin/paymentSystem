package com.yl.dpay.core.Utils;

import java.util.UUID;

/**
 * UUID工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class UUIDUtils {
	public static String getUniqueCode(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
