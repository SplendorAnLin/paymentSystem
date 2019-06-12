package com.yl.boss.utils;

import java.util.Random;

/**
 * 生成密码工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class GengeratePassword {
	public static int getRandInt() {

		Random random = new Random();

		return random.nextInt(10);

	}

	public static StringBuffer getExchangeCode() {

		StringBuffer randomValidateCode = new StringBuffer();

		for (int j = 0; j < 8; j++) {
			int one = getRandInt();// 获得一个随机数
			if (j == 0) {
				randomValidateCode.append(one).append("#");// 添加随机数和分隔符
			} else if (j > 0) {
				String[] all = randomValidateCode.toString().split("#");// 分割成字符串数组
				// 调用是否重复方法teseEquals
				if (teseEquals(all, one, randomValidateCode) == 1) {
					randomValidateCode.append(one).append("#");// 如果不重复就添加随机数和分隔符
				} else {
					j--;// 如果重复的话将j-1
				}
			}
		}
		// 对得到的8位随机数用分隔符进行分割
		String[] result = randomValidateCode.toString().split("#");
		StringBuffer res = new StringBuffer();
		for (int r = 0; r < result.length; r++) {
			res.append(result[r]);
		}
		return res;
	}

	// 测试是否重复
	public static int teseEquals(String[] all, int one, StringBuffer randomValidateCode) {
		for (int i = 0; i < all.length; i++) {
			if (one == Integer.parseInt(all[i])) {
				return 0;// 重复就返回0
			}
		}
		return 1;// 不重复返回1
	}
}
