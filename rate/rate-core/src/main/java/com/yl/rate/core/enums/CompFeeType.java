package com.yl.rate.core.enums;

/**
 * 计费方式
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/27 0027
 */
public enum CompFeeType {

    /**
     * 标准计费
     */
	STANDARD,
	/**
	 * 组合计费
	 */
	COMBINATION,
	/**
	 * 包量
	 */
	PACKAGE_VOLUME,
	/**
	 * 阶梯
	 */
    LADDER,
	/**
	 * 线下费率
	 */
	POS;

}
