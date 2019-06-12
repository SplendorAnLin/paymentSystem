package com.yl.payinterface.core.handle.impl.b2c.shand100001.utils;

import java.util.Random;

/**
 *
 * @ClassName ：RandomStringGenerator
 * @author : pxl
 * @Date : 2018-4-25 下午3:50:48
 * @version 2.0.0
 *
 */
public class RandomStringGenerator {

	public static String getRandomStringByLength(int length)
	  {
	    String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    Random random = new Random();
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < length; i++) {
	      int number = random.nextInt(base.length());
	      sb.append(base.charAt(number));
	    }
	    return sb.toString();
	  }
}
