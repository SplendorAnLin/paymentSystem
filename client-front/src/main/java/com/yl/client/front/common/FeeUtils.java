package com.yl.client.front.common;

import com.lefu.commons.utils.lang.AmountUtils;
import com.yl.client.front.enums.AppExceptionEnum;
import com.yl.online.model.enums.FeeType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * Fee工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月5日
 * @version V1.0.0
 */
public class FeeUtils {

	public static double computeFee(double amount, double feeRate) {
		double d = AmountUtils.multiply(amount, feeRate);
		BigDecimal b = new BigDecimal(d);
		d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return d;
	}

	/**
	 * 计算商户交易费率
	 *
	 * @param amount
	 * @param feeType
	 * @param fee
	 * @return
	 */
	public static double computeFee(double amount, FeeType feeType, double fee) throws AppRuntimeException {
		double computeFee = 0d;
		switch (feeType) {
			case FIXED:
				computeFee = fee;
				break;

			case RATIO:
				computeFee = AmountUtils.multiply(amount, fee);
				break;

			default:
				throw new AppRuntimeException(AppExceptionEnum.FEE_TYPE_ERROR.getCode(), AppExceptionEnum.FEE_TYPE_ERROR.getMessage());
		}
		if (computeFee < 0) {
			throw new AppRuntimeException(AppExceptionEnum.AMOUNT_FEE_ERROR.getCode(), AppExceptionEnum.AMOUNT_FEE_ERROR.getMessage());
		}

		computeFee = new BigDecimal(computeFee).setScale(2, RoundingMode.HALF_UP).doubleValue();
		return computeFee;
	}

	/**
	 * 计算商户交易费率
	 *
	 * @param amount
	 * @param feeType
	 * @param fee
	 * @param minFee
	 * @return
	 */
	public static double computeFee(double amount, FeeType feeType, double fee, double minFee) throws AppRuntimeException {
		double computeFee = 0d;
		switch (feeType) {
			case FIXED:
				computeFee = fee;
				break;

			case RATIO:
				computeFee = AmountUtils.multiply(amount, fee);
				break;

			default:
				throw new AppRuntimeException(AppExceptionEnum.FEE_TYPE_ERROR.getCode(), AppExceptionEnum.FEE_TYPE_ERROR.getMessage());
		}
		if (computeFee < 0) {
			throw new AppRuntimeException(AppExceptionEnum.AMOUNT_FEE_ERROR.getCode(), AppExceptionEnum.AMOUNT_FEE_ERROR.getMessage());
		}

		computeFee = new BigDecimal(computeFee).setScale(2, RoundingMode.HALF_UP).doubleValue();
		if (computeFee < minFee) {
			return minFee;
		}
		return computeFee;
	}

	/**
	 * 计算商户交易费率
	 *
	 * @param amount
	 * @param feeType
	 * @param fee
	 * @param minFee
	 * @param maxFee
	 * @return
	 */
	public static double computeFee(double amount, FeeType feeType, double fee, double minFee, double maxFee) throws AppRuntimeException {
		double computeFee = 0d;
		switch (feeType) {
			case FIXED:
				computeFee = fee;
				break;

			case RATIO:
				computeFee = AmountUtils.multiply(amount, fee);
				break;

			default:
				throw new AppRuntimeException(AppExceptionEnum.FEE_TYPE_ERROR.getCode(), AppExceptionEnum.FEE_TYPE_ERROR.getMessage());
		}
		if (computeFee < 0) {
			throw new AppRuntimeException(AppExceptionEnum.AMOUNT_FEE_ERROR.getCode(), AppExceptionEnum.AMOUNT_FEE_ERROR.getMessage());
		}

		computeFee = new BigDecimal(computeFee).setScale(2, RoundingMode.HALF_UP).doubleValue();
		if (computeFee < minFee) {
			return minFee;
		}
		if (computeFee > maxFee) {
			return maxFee;
		}
		return computeFee;
	}

	public static double compRatio(double amount, double fee) {
		double remitAmount = AmountUtils.round(amount / (1 + fee),2, RoundingMode.HALF_UP);
		double feeNow = AmountUtils.round(AmountUtils.multiply(remitAmount, 0.0032), 2, RoundingMode.HALF_UP);
		if (AmountUtils.less(amount, AmountUtils.add(remitAmount, feeNow))) {
			remitAmount = AmountUtils.subtract(remitAmount, 0.01d);
		}
		return remitAmount>0?remitAmount:0;
	}

	public static double compFee(List<Map<String, Object>> fee, double amount) throws AppRuntimeException {
		if (HolidayUtils.isHoliday()) {
			for (Map<String, Object> temp : fee) {
				if ("HOLIDAY_REMIT".equals(temp.get("productType"))) {
					if ("RATIO".equals(temp.get("feeType"))){
						return compRatio(amount,Double.valueOf(temp.get("fee").toString()));
					}
					double amountV=amount-computeFee(amount, FeeType.valueOf("FIXED"), Double.valueOf(temp.get("fee").toString()));
					return amountV>0?amountV:0;
				}
			}
		} else {
			for (Map<String, Object> temp : fee) {
				if ("REMIT".equals(temp.get("productType"))) {
					if ("RATIO".equals(temp.get("feeType"))){
						return compRatio(amount,Double.valueOf(temp.get("fee").toString()));
					}
					double amountV=amount-computeFee(amount, FeeType.valueOf("FIXED"), Double.valueOf(temp.get("fee").toString()));
					return amountV>0?amountV:0;
				}
			}
		}
		return 0;
	}
}
	
	//public static void main(String[] args) {
//		System.out.println(computeFee(0.1, FeeType.RATIO, 0.001, 0.01));
//	}

