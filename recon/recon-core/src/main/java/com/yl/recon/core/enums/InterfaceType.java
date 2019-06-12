package com.yl.recon.core.enums;

/**
 * 接口类型
 *
 * @author AnLin
 * @since 2017/6/21
 */
public enum InterfaceType {
    /**
     * 个人网银
     */
    B2C,
    /**
     * 企业网银
     */
    B2B,
    /**
     * 快捷支付
     */
    QUICKPAY,
    /**
     * 认证支付
     */
    AUTHPAY,
    /**
     * 移动支付
     */
    MOBILE,
    /**
     * 付款
     */
    REMIT,
    /**
     * 微信公众号支付
     */
    WXJSAPI,
    /**
     * 微信扫码支付
     */
    WXNATIVE,
    /**
     * 支付宝扫码支付
     */
    ALIPAY,
    /**
     * 微信条码支付
     */
    WXMICROPAY,
    /**
     * 支付宝条码支付
     */
    ALIPAYMICROPAY,
    /**
     * 代收
     */
    RECEIVE;
}
