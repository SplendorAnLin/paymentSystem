package com.yl.risk.api.enums;

/**
 * 支付方式
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年8月11日
 */
public enum PayType {

    B2C("个人网银"), B2B("企业网银"), AUTHPAY("认证支付"), REMIT("代付"), WXJSAPI("微信公众号"),
    WXNATIVE("微信扫码"), ALIPAY("支付宝"), HOLIDAY_REMIT("假日付"), RECEIVE("代收"),
    WXMICROPAY("微信条码"), ALIPAYMICROPAY("支付宝条码"), POS("POS"), MPOS("MPOS"),
    REAL_AUTH("绑卡认证"), QUICKPAY("快捷支付"), ALIPAYJSAPI("支付宝服务窗"), OTHER("其他");
    private final String message;

    PayType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}