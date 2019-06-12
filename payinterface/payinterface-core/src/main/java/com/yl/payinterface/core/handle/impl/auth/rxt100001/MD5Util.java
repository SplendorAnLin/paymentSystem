package com.yl.payinterface.core.handle.impl.auth.rxt100001;

import java.security.MessageDigest;

/**
 * MD5工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月16日
 * @version V1.0.0
 */
public class MD5Util {

	/**
	 * 功能描述：返回MD5串
	 * @developer：jun.liu
	 * @date：2016年7月4日 下午3:40:15
	 * @param src 请求原文
	 * @return String MD5密串
	 */
	public static String md5(String src) {
		String resultStr = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(src.getBytes("UTF-8"));
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0){
					i += 256;
				}
				if (i < 16){
					buf.append("0");
				}
				buf.append(Integer.toHexString(i)).toString();
			}
			resultStr = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultStr;
	}
}
