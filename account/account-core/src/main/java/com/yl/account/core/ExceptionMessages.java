package com.yl.account.core;

/**
 * 异常码定义
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月12日
 * @version V1.0.0
 */
public final class ExceptionMessages {
	// 参数校验相关1开头
	/** 参数不存在 */
	public static final String PARAM_NOT_EXISTS = "10001";
	/** 参数错误 */
	public static final String PARAM_ERROR = "10002";

	// 业务校验相关2开头
	/** 账户信息不存在 */
	public static final String ACCOUNT_NOT_EXISTS = "20001";
	/** 账户状态不正常 */
	public static final String ACCOUNT_STATUS_NOT_NORMAL = "20002";
	/** 在途资金明细不存在 */
	public static final String ACCOUNT_TRANSIT_DETAI_NOT_EXISTS = "20003";
	/** 汇总信息不存在 */
	public static final String ACCOUNT_SUMMARY_NOT_EXISTS = "20004";
	/** 账户已被冻结 */
	public static final String ACCOUNT_ALREADY_FREEZE = "20005";
	/** 账户状态非冻结 */
	public static final String ACCOUNT_STATUS_NOT_FREEZE = "20006";
	/** 冻结明细不存在 */
	public static final String ACCOUNT_FREEZE_DETAI_NOT_EXISTS = "20007";
	/** 汇总在途可用资金不足 */
	public static final String ACCOUNT_SUMMARY_VALUEABLE_TRANSIT_NOT_ENOUGH = "20008";
	/** 账户在途可用资金不足 */
	public static final String ACCOUNT_VALUEABLE_TRANSIT_NOT_ENOUGH = "20009";
	/** 账户状态已被解冻 */
	public static final String ACCOUNT_STATUS_HAS_BEEN_THAW = "20010";
	/** 账户可用余额资金不足 */
	public static final String ACCOUNT_VALUEABLE_BALANCE_NOT_ENOUGH = "20011";
	/** 账户入账金额不能为负数 */
	public static final String ACCOUNT_CREDIT_AMOUNT_NOT_NEGATIVE = "20012";
	/** 账户存在多个 */
	public static final String ACCOUNT_EXISTS_MORE_THAN_ONE = "20013";
	/** 中间件消息格式错误 */
	public static final String MQ_MESSAGE_FORMAT_ERROR = "20014";
	/** 中间件消费错误 */
	public static final String MQ_MESSAGE_CONSUMER_ERROR = "20015";
	/** 账户状态非预冻结 */
	public static final String ACCOUNT_STATUS_NOT_PRE_FREEZE = "20016";
	/** 请款金额大于预冻金额 */
	public static final String ACCOUNT_CONSULT_AMOUNT_GREATETHAN_PREFREEZE_AMOUNT = "20017";
	/** 解冻金额小于0 */
	public static final String ACCOUNT_THAW_AMOUNT_LESS_THAN_ZERO = "20018";
	/** 冻结金额大于可用金额 */
	public static final String ACCOUNT_FREEZE_AMOUNT_GREATETHAN_VALUEABLE_AMOUNT = "20019";
	/** 解冻金额大于账户总冻结金额 */
	public static final String ACCOUNT_THAW_AMOUNT_GREATETHAN_TOTAL_FREEZE = "20020";
	/** 账户总额小于0 */
	public static final String ACCOUNT_BALANCE_LESS_ZERO = "20021";
	/** 调账状态不匹配 */
	public static final String ACCOUNT_ADJUST_STATUS_NOT_MATCH = "20022";
	/** 账户开户失败 */
	public static final String ACCOUNT_OPEN_FAILED = "20023";
	/** 业务类型不存在*/
	public static final String BUSSINESS_CODE_NOT_EXISTS="20024";

	// 数据库相关3开头
	/** 数据库操作异常 */
	public static final String DATABASE_OPERATOR_ERROR = "30001";
	/** 数据库更新失败 */
	public static final String DATABASE_ACCOUNT_UPDATE_FAILURE = "30013";
}
