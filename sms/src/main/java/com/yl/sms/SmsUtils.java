package com.yl.sms;

import java.io.IOException;

/**
 * 短信工具类
 * 
 * @author AnLin
 * @since 2016年8月29日
 * @version V1.0.0
 */
public class SmsUtils {
	
	private static final String verifyCode = "【央联支付】您的验证码是:[%s]";
	private static final String exMsg = "【央联支付】系统异常:[%s],流水号:[%s],异常信息:[%s]";
	private static final String regist = "";
	private static final String common = "";

	
	/**
	 * 自定义消息
	 * @param msg
	 * @param phone
	 * @throws IOException
	 */
	public static void sendCustom(String msg, String phone) throws IOException{
		try {
			Sms.qdSendSas(msg, phone);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}