package com.yl.realAuth.core;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class InnerResponseConvertHandle {
	private static Map<String, String> innerCode = new LinkedHashMap<String, String>();
	static {
		/** 账务返回码 */
		innerCode.put("10001", "S0013");// 参数不存在
		innerCode.put("10002", "S0013");// 参数错误
		innerCode.put("20005", "S0010");// 账户已被冻结
		innerCode.put("20006", "S0010");// 账户状态非冻结
		innerCode.put("20007", "S0010");// 冻结明细不存在
		innerCode.put("20008", "S0011");// 汇总在途可用资金不足
		innerCode.put("20009", "S0011");// 账户在途可用资金不足
		innerCode.put("20010", "S0010");// 账户状态已被解冻
		innerCode.put("20011", "S0011");// 账户可用余额资金不足
		innerCode.put("20012", "S0013");// 账户入账金额不能为负数
		innerCode.put("20013", "S0010");// 账户存在多个
		innerCode.put("20014", "S0013");// 中间件消息格式错误
		innerCode.put("20015", "S0013");// 中间件消费错误
		innerCode.put("20016", "S0010");// 账户状态非预冻结
		innerCode.put("20017", "S0013");// 请款金额大于预冻金额
		innerCode.put("20018", "S0011");// 解冻金额小于0
		innerCode.put("20019", "S0011");// 冻结金额大于可用金额
		innerCode.put("20020", "S0011");// 解冻金额大于账户总冻结金额
		innerCode.put("20021", "S0011");// 账户总额小于0
		innerCode.put("20022", "S0013");// 调账状态不匹配
		innerCode.put("20023", "S0013");// 账户开户失败
		innerCode.put("20024", "S0013");// 业务类型不存在
		// add by Shark 2016年5月17日 13:55:43
		innerCode.put("20029", "S0013");// 交易接口不支持该业务类型
		innerCode.put("20030", "S0013");// 账务资金信息不存在
		innerCode.put("20031", "S0013");// 账务禁止跨主体交易

		innerCode.put("30001", "S0013");// 数据库操作异常
		innerCode.put("30013", "S0013");// 数据库更新失败

		/** 计费返回码 */
		innerCode.put("0101", "S0012");// 费率不存在

		/** 支付接口返回码 */
		innerCode.put("I0001", "S0015");// 认证正确
		innerCode.put("I0002", "S0016");// 认证错误
		innerCode.put("I0003", "S0017");// 受理成功
		innerCode.put("I0004", "S0021");// 银行卡信息有误
		innerCode.put("I0008", "S0013");// 订单已存在
		innerCode.put("I0009", "S0013");// 订单未存在
		innerCode.put("I0012", "S0022");// 通道不支持该笔交易
		innerCode.put("I0014", "S0013");// 乐富在通道账户余额不足
		innerCode.put("I0015", "S0013");// 乐富在通道状态异常
		innerCode.put("I0016", "S0013");// 参数格式及验签错误
		innerCode.put("I0017", "S0013");// 通道系统错误
		innerCode.put("I0018", "S0021");// 暂不支持该银行卡
	}

	public static String getInnerResponseCode(String outCode) {

		if (StringUtils.isNotBlank(innerCode.get(outCode))) {
			return innerCode.get(outCode);
		} else {
			return "S0013";
		}
	}

}
