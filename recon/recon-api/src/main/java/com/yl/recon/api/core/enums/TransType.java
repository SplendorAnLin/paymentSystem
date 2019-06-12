package com.yl.recon.api.core.enums;

/**
 * 交易类型
 *
 * @author AnLin
 * @since 2017/6/21
 */
public enum TransType {

    /**
     * 在线支付
     */
    OLPAY,
    /**
     * 银行卡线下收单
     */
    OFPAY,
    /**
     * 代付
     */
    DPAY,
    /**
     * 代收
     */
    RECEIVE,
	/**
	 * 实名认证
	 */
	REALAUTH;
}
