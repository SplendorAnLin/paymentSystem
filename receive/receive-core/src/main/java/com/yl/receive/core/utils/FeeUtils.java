package com.yl.receive.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.lefu.commons.utils.lang.AmountUtils;
import com.yl.payinterface.core.exception.ExceptionMessages;
import com.yl.receive.core.enums.FeeType;

/**
 * 文件工具类
 *
 * @author 聚合支付有限公司
 * @since 2016年9月17日
 * @version V1.0.0
 */
public class FeeUtils {

	public static double computeFee(double amount , double feeRate){
		double d = AmountUtils.multiply(amount, feeRate);
		BigDecimal b = new BigDecimal(d);
		d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return d;
	}

	/**
	 * 计算接口费率
	 * @param amount
	 * @param feeType
	 * @param fee
	 * @return
	 */
	public static double computeFee(double amount,FeeType feeType,double fee){
		double computeFee = 0d;
		switch (feeType) {
			case FIXED:
				computeFee = fee;
				break;

			case RATIO:
				computeFee = AmountUtils.multiply(amount, fee);
				break;

			default:
				throw new RuntimeException(ExceptionMessages.FEE_TYPE_ERROR);
		}
		if(computeFee < 0){
			throw new RuntimeException(ExceptionMessages.AMOUNT_FEE_ERROR);
		}

		computeFee = new BigDecimal(computeFee).setScale(2, RoundingMode.HALF_UP).doubleValue();
		return computeFee;
	}

	public static void main(String[] args) {
		System.out.println(computeFee(3.1, FeeType.FIXED, 2));
		System.out.println(computeFee(1, FeeType.RATIO, 0.056));
		System.out.println(computeFee(1, 0.004));
	}

}
