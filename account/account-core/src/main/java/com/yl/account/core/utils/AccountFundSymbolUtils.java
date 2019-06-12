/**
 * 
 */
package com.yl.account.core.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import com.lefu.commons.utils.lang.AmountUtils;
import com.yl.account.enums.FundSymbol;

/**
 * 账务资金标识工具处理类
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月12日
 * @version V1.0.0
 */
public final class AccountFundSymbolUtils {

	/**
	 * @Description 入账、出账金额及手续费判定
	 * @param amount 账务处理入账、出账金额
	 * @param symbol 入账、出账金额资金标识
	 * @param fee 手续费
	 * @param feeSymbol 手续费入账、出账资金标识
	 * @return Map<String, Object> 资金和出入账资金标识
	 * @see 需要参考的类或方法
	 */
	public static Map<String, Object> handleFund(double amount, FundSymbol symbol, Double fee, FundSymbol feeSymbol) {
		Map<String, Object> transAmountAndSymbol = new LinkedHashMap<String, Object>();
		if (symbol.equals(feeSymbol)) {
			double transAmount = AmountUtils.add(amount, fee);
			transAmountAndSymbol.put("transAmount", transAmount);
			transAmountAndSymbol.put("symbol", symbol);
		} else {
			double transAmount = AmountUtils.subtract(amount, fee);
			if (transAmount >= 0) transAmountAndSymbol.put("symbol", FundSymbol.PLUS);
			else transAmountAndSymbol.put("symbol", FundSymbol.SUBTRACT);
		}
		return transAmountAndSymbol;
	}

	/**
	 * @Description 根据资金标识转化成真实入账、出账金额
	 * @param symbol 资金标识
	 * @param amount 入账、出账金额
	 * @return 真实入账、出账金额
	 * @see 需要参考的类或方法
	 */
	public static double convertToRealAmount(FundSymbol symbol, double amount) {
		double finalAmount = amount;
		if (symbol.equals(FundSymbol.SUBTRACT)) finalAmount = -finalAmount;
		return finalAmount;
	}

	/**
	 * @Description 资金标识转化为相反方向
	 * @param symbol 原资金标识
	 * @return 转化后的资金标识
	 * @see 需要参考的类或方法
	 */
	public static FundSymbol inverseSymbol(FundSymbol symbol) {
		if (symbol.equals(FundSymbol.PLUS)) return FundSymbol.SUBTRACT;
		return FundSymbol.PLUS;
	}
}
