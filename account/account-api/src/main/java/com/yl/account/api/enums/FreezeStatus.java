package com.yl.account.api.enums;

/**
 * 冻结状态
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月23日
 * @version V1.0.0
 */
public enum FreezeStatus {
	/** 冻结 */
	FREEZE,
	/** 解冻 */
	UNFREEZE,
	/** 预冻处理中 */
	PRE_FREEZE_ING,
	/** 预冻完成 */
	PRE_FREEZE_COMPLETE;
}
