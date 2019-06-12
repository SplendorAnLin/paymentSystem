package com.yl.pay.pos.util;

import java.security.MessageDigest;

/**
 * MD5工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月17日
 * @version V1.0.0
 */
public class MD5 {
	  private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
	      "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	  /**
	   * 转换字节数组为16进制字串
	   * 
	   * @param b
	   *            字节数组
	   * @return 16进制字串
	   */

	  public static String byteArrayToHexString(byte[] b) {
	    StringBuffer resultSb = new StringBuffer();
	    for (int i = 0; i < b.length; i++) {
	      resultSb.append(byteToHexString(b[i]));
	    }
	    return resultSb.toString();
	  }

	  private static String byteToHexString(byte b) {
	    int n = b;
	    if (n < 0)
	      n = 256 + n;
	    int d1 = n / 16;
	    int d2 = n % 16;
	    return hexDigits[d1] + hexDigits[d2];
	  }
	  
	  public final static String MD5Encode(String s, String charset) {
	      try {
	          byte[] btInput = s.getBytes(charset);
	          MessageDigest mdInst = MessageDigest.getInstance("MD5");
	          mdInst.update(btInput);
	          byte[] md = mdInst.digest();
	          StringBuffer sb = new StringBuffer();
	          for (int i = 0; i < md.length; i++) {
	              int val = ((int) md[i]) & 0xff;
	              if (val < 16){
	              	sb.append("0");
	              }
	              sb.append(Integer.toHexString(val));
	          }
	          return sb.toString();
	      } catch (Exception e) {
	          return null;
	      }
	  }


	  public static String MD5Encode(String origin) {
	    String resultString = null;

	    try {
	      resultString = new String(origin);
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      resultString = byteArrayToHexString(md.digest(resultString
	          .getBytes()));
	    } catch (Exception ex) {

	    }
	    return resultString;
	  }
	  
	  public static void main(String[] args) {
	    System.out.println(MD5.MD5Encode("EAEB8C1BFD31DDA00BBC6D5A9ABACF2C"+"896420157021880","utf-8"));
//		  System.out.println(MD5Encode("pay_memberid=>10014&pay_amount=>5.0&pay_orderid=>L220151220232121&pay_applydate=>2015-12-20 23:21:21&pay_type=>%E9%93%B6%E8%81%94%E6%94%AF%E4%BB%98&pay_sn=>13910178418&pay_bankcode=>13910178418&pay_status=>1&pay_notifyurl=>http://www.baidu.com&pay_callbackurl=>http://www.baidu.com&key=>WGbbgw2NK6SgyaykTvzVeKmK16KAIu"));
	  }

	}