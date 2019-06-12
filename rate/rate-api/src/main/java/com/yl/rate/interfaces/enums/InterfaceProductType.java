package com.yl.rate.interfaces.enums;

/**
 * 产品类型
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/27 0027
 */
public enum InterfaceProductType {
    /**
     * 个人网银
     */
    B2C("个人网银"),
    /**
     * 企业网银
     */
    B2B("企业网银"),
    /**
     * 认证支付
     */
    AUTHPAY("认证支付"),
    /**
     * 代付
     */
    REMIT("代付"),
    /**
     * 微信公众号
     */
    WXJSAPI("微信公众号"),
    /**
     * 微信扫码
     */
    WXNATIVE("微信扫码"),
    /**
     * 支付宝
     */
    ALIPAY("支付宝"),
    /**
     * 假日付
     */
    HOLIDAY_REMIT("假日付"),
    /**
     * 代收
     */
    RECEIVE("代收"),
    /**
     * 微信条码
     */
    WXMICROPAY("微信条码"),
    /**
     * 支付宝条码
     */
    ALIPAYMICROPAY("支付宝条码"),
    /**
     * POS
     */
    POS("POS"),
    /**
     * MPOS
     */
    MPOS("MPOS"),
    /**
     * 绑卡认证
     */
    BINDCARD_AUTH("绑卡认证"),
    /**
     * 身份认证
     */
    IDENTITY_AUTH("身份认证"),
    /**
     * 快捷支付
     */
    QUICKPAY("快捷支付"),
    /**
     * 支付宝服务窗
     */
    ALIPAYJSAPI("支付宝服务窗");

    private final String message;

    InterfaceProductType(String message) {
        this.message = message;
    }
}
