package com.yl.payinterface.core.handle.impl.wallet.lucky100001.bean;

/**
 * 维基支付查询Bean
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/12
 */
public class Lucky100001QueryBean {

    /** 商户编号 */
    private String merchantNo;

    /** 请求订单号 */
    private String requestOrder;

    /** 签名摘要 */
    private String sign;

    /** 交易类型 */
    private String tradeType;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getRequestOrder() {
        return requestOrder;
    }

    public void setRequestOrder(String requestOrder) {
        this.requestOrder = requestOrder;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}