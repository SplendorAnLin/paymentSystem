package com.yl.payinterface.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.lefu.commons.utils.lang.AmountUtils;
import com.yl.payinterface.core.enums.FeeType;
import com.yl.payinterface.core.exception.ExceptionMessages;

/**
 * Fee工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
public class FeeUtils {

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

	/**
	 * 计算商户交易费率
	 * @param amount
	 * @param feeType
	 * @param fee
	 * @param minFee
	 * @param maxFee
	 * @return
	 */
	public static double computeFee(double amount, FeeType feeType, double fee, double minFee, double maxFee){
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
		if(computeFee < minFee){
			return minFee;
		}
		if(computeFee > maxFee){
			return maxFee;
		}
		return computeFee;
	}
	
	public static void main(String[] args) {
		System.out.println(computeFee(3.1, FeeType.FIXED, 2));
		System.out.println(computeFee(1, FeeType.RATIO, 0.056));
	}
}
