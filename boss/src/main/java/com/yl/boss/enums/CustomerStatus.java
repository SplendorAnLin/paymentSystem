package com.yl.boss.enums;

/**
 * 商户状态
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public enum CustomerStatus {

	/** 预开通 */
	PRE_TRUE,
	/** 等待运营审核中 */
	AUDIT,
	/**	开通 */
	TRUE,
	/** 关闭*/
	FALSE,
	/** 等待服务商审核 */
	AGENT_AUDIT,
	/** 服务商拒绝 */
	AGENT_REFUSE,
	/** 审核拒绝 */
	AUDIT_REFUSE;
}
