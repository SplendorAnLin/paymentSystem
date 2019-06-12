package com.yl.pay.pos.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.lefu.commons.utils.lang.AmountUtils;
import com.yl.boss.api.enums.FeeType;
import com.yl.pay.pos.exception.ExceptionMessages;

/**
 * Fee工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月5日
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
	 * 计算商户交易费率
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
	 * @return
	 */
	public static double computeFee(double amount,FeeType feeType,double fee,double minFee){
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
	public static double computeFee(double amount,FeeType feeType,double fee,double minFee, double maxFee){
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
		System.out.println(computeFee(0.1, FeeType.RATIO, 0.001, 0.01));
	}

}
