package com.yl.boss.api.enums;

/**
 * 产品类型
 *
 * @author AnLin
 * @version 创建时间：2016年8月15日
 */
public enum ProductType {

    B2C("个人网银"), B2B("企业网银"), AUTHPAY("认证支付"), REMIT("代付"), WXJSAPI("微信公众号"),
    WXNATIVE("微信扫码"), ALIPAY("支付宝"), HOLIDAY_REMIT("假日付"), RECEIVE("代收"),
    WXMICROPAY("微信条码"), ALIPAYMICROPAY("支付宝条码"), POS("POS"), MPOS("MPOS"),
    BINDCARD_AUTH("绑卡认证"), QUICKPAY("快捷支付"), ALIPAYJSAPI("支付宝服务窗"), QQH5("QQ主扫"),
    QQNATIVE("QQH5支付"), JDH5("京东H5"),UNIONPAYNATIVE("银联主扫");
    private final String message;

    ProductType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
