/**
 * 
 */
package com.yl.account.core;

/**
 * 业务系统描述转义
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
public enum HandlerResultCode {
	/** 参数校验相关 */
	PARAM_NOT_EXISTS(ExceptionMessages.PARAM_NOT_EXISTS, "参数不存在"), PARAM_ERROR(ExceptionMessages.PARAM_ERROR, "参数错误"),

	/** 业务校验相关 */
	ACCOUNT_NOT_EXISTS(ExceptionMessages.ACCOUNT_NOT_EXISTS, "账户信息不存在"), ACCOUNT_STATUS_NOT_NORMAL(ExceptionMessages.ACCOUNT_STATUS_NOT_NORMAL, "账户状态不正常"), ACCOUNT_TRANSIT_DETAI_NOT_EXISTS(
			ExceptionMessages.ACCOUNT_TRANSIT_DETAI_NOT_EXISTS, "在途资金明细不存在"), ACCOUNT_SUMMARY_NOT_EXISTS(ExceptionMessages.ACCOUNT_SUMMARY_NOT_EXISTS, "汇总信息不存在"), ACCOUNT_ALREADY_FREEZE(
			ExceptionMessages.ACCOUNT_ALREADY_FREEZE, "账户已被冻结"), ACCOUNT_STATUS_NOT_FREEZE(ExceptionMessages.ACCOUNT_STATUS_NOT_FREEZE, "账户状态非冻结"), ACCOUNT_FREEZE_DETAI_NOT_EXISTS(
			ExceptionMessages.ACCOUNT_FREEZE_DETAI_NOT_EXISTS, "冻结明细不存在"), ACCOUNT_SUMMARY_VALUEABLE_TRANSIT_NOT_ENOUGH(
			ExceptionMessages.ACCOUNT_SUMMARY_VALUEABLE_TRANSIT_NOT_ENOUGH, "汇总在途可用资金不足"), ACCOUNT_VALUEABLE_TRANSIT_NOT_ENOUGH(
			ExceptionMessages.ACCOUNT_VALUEABLE_TRANSIT_NOT_ENOUGH, "账户在途可用资金不足"), ACCOUNT_CONSULT_STAUS_NOT_PRE_FREEZE(
			ExceptionMessages.ACCOUNT_STATUS_HAS_BEEN_THAW, "账户状态已被解冻"), ACCOUNT_VALUEABLE_BALANCE_NOT_ENOUGH(
			ExceptionMessages.ACCOUNT_VALUEABLE_BALANCE_NOT_ENOUGH, "账户可用余额资金不足"), ACCOUNT_CREDIT_AMOUNT_NOT_NEGATIVE(
			ExceptionMessages.ACCOUNT_CREDIT_AMOUNT_NOT_NEGATIVE, "账户入账金额不能为负数"), ACCOUNT_EXISTS_MORE_THAN_ONE(ExceptionMessages.ACCOUNT_EXISTS_MORE_THAN_ONE,
			"账户存在多个"), MQ_MESSAGE_FORMAT_ERROR(ExceptionMessages.MQ_MESSAGE_FORMAT_ERROR, "中间件消息格式错误"), MQ_MESSAGE_CONSUMER_ERROR(
			ExceptionMessages.MQ_MESSAGE_CONSUMER_ERROR, "中间件消费错误"), ACCOUNT_STATUS_NOT_PRE_FREEZE(ExceptionMessages.ACCOUNT_STATUS_NOT_PRE_FREEZE, "账户状态非预冻结"), ACCOUNT_CONSULT_AMOUNT_GREATETHAN_PREFREEZE_AMOUNT(
			ExceptionMessages.ACCOUNT_CONSULT_AMOUNT_GREATETHAN_PREFREEZE_AMOUNT, "请款金额大于预冻可用金额"), ACCOUNT_THAW_AMOUNT_LESS_THAN_ZERO(
			ExceptionMessages.ACCOUNT_THAW_AMOUNT_LESS_THAN_ZERO, "解冻金额小于0"), ACCOUNT_FREEZE_AMOUNT_GREATETHAN_VALUEABLE_AMOUNT(
			ExceptionMessages.ACCOUNT_FREEZE_AMOUNT_GREATETHAN_VALUEABLE_AMOUNT, "冻结金额大于可用金额"), ACCOUNT_THAW_AMOUNT_GREATETHAN_TOTAL_FREEZE(
			ExceptionMessages.ACCOUNT_THAW_AMOUNT_GREATETHAN_TOTAL_FREEZE, "解冻金额大于账户总冻结金额"), ACCOUNT_BALANCE_LESS_ZERO(ExceptionMessages.ACCOUNT_BALANCE_LESS_ZERO,
			"账户总额小于0"),

	/** 数据库相关 */
	DATABASE_OPERATOR_ERROR(ExceptionMessages.DATABASE_OPERATOR_ERROR, "数据库操作异常"), DATABASE_ACCOUNT_UPDATE_FAILURE(
			ExceptionMessages.DATABASE_ACCOUNT_UPDATE_FAILURE, "数据库更新失败"),

	UNKOWN_ERROR("9999", "交易结果未知");

	/** 账户交易码 */
	private String accountCode;

	/** 业务系统响应描述 */
	private String handlerResultMsg;

	private HandlerResultCode(String accountCode, String handlerResultMsg) {
		this.accountCode = accountCode;
		this.handlerResultMsg = handlerResultMsg;
	}

	/**
	 * 根据系统内部码获取对应的商户响应码
	 * @param accountCode 交易系统内部码
	 * @return MerchantResponseCode
	 */
	public static HandlerResultCode getHandlerResultCode(String accountCode) {
		HandlerResultCode[] handlerResultCodes = HandlerResultCode.values();
		for (HandlerResultCode handlerResultCode : handlerResultCodes) {
			if (handlerResultCode.getAccountCode().equalsIgnoreCase(accountCode)) {
				return handlerResultCode;
			}
		}
		return UNKOWN_ERROR;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getHandlerResultMsg() {
		return handlerResultMsg;
	}

	public void setHandlerResultMsg(String handlerResultMsg) {
		this.handlerResultMsg = handlerResultMsg;
	}

}
