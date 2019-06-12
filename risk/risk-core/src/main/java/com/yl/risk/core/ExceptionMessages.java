package com.yl.risk.core;

/**
 * 异常码定义
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/15
 */
public class ExceptionMessages {

    // 参数校验相关00开头

    /** 参数不存在 */
    public static final String PARAM_NOT_EXISTS = "0001";


    // 业务校验相关01开头

    /** 收款方不存在 */
    public static final String RECEIVER_NOT_EXISTS = "0100";

    /** 收款方状态错误 */
    public static final String RECEIVER_STATUS_ERROR = "0101";

    // 商户交易配置校验02开头

    /** 商户交易不在可用时间段 */
    public static final String CUSTOMER_CONFIG_DATA = "0201";

    /** 商户交易单笔金额超过上限 */
    public static final String CUSTOMER_CONFIG_MAX = "0202";

    /** 商户交易单笔金额不足 */
    public static final String CUSTOMER_CONFIG_MIN = "0203";

    /** 商户当日交易金额超过上限 */
    public static final String CUSTOMER_CONFIG_DAY_MAX = "0204";

    /** 商户交易金额非法*/
    public static final String CUSTOMER_CONFIG_AMOUNT = "0205";

    /** 商户借贷比例超限 */
    public static final String CUSTOMER_EXCEEDED_DEBIT_CREDIT_PROPORTION = "0602";

    /** 商户跨区域操作 */
    public static final String CUSTOMER_CROSS_REGIONAL = "0604";

    /** 风控校验订单基础信息不能为空 */
    public static final String RISK_ORDER_BEAN_IS_NULL = "0605";

    /** 未匹配到风控案例 */
    public static final String RISK_CASE_IS_NULL = "0606";

    /** 未匹配到风控规则配置 */
    public static final String RISK_RULE_CONFIG_IS_NULL = "0607";

    /** 请求参数错误 */
    public static final String TRADE_PARAMS_ERROR = "0608";

    /** 响应参数为空 */
    public static final String RESPONSE_MESSAGE_IS_NULL = "0609";

    /** 相应处理脚本为空 */
    public static final String PROCESS_SCRIPT_IS_NULL = "0610";

    /** 未匹配到风险处理 */
    public static final String RISK_PROCESSOR_IS_NULL = "0611";

    // 系统级别相关99开头

    /** 结果不止一个 */
    public static final String RESULT_MORE_THAN_ONE = "1000";

    /** 错误信息未知 */
    public static final String UNKOWN_ERROR = "9999";

    /** 验证通过 */
    public static final String PASS = "0000";
}