package com.yl.payinterface.core.handle.impl.wallet.lucky100001.bean;

/**
 * 京东钱包H5 下单Bean
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/10
 */
public class Lucky100001PayBean {

    /** 商户编号 */
    private String merchantNo;

    /** 请求订单号 */
    private String requestOrder;

    /** 请求用户号 */
    private String userNo;

    /** 客户端请求IP */
    private String requestIp;

    /** 订单金额 */
    private String orderAmount;

    /** 订单描述 */
    private String orderSubject;

    /** 订单商品ID */
    private String goodsNo;

    /** 异步通知地址 */
    private String notifyUrl;

    /** 回传参数 */
    private String returnInfo;

    /** 签名 */
    private String sign;

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

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderSubject() {
        return orderSubject;
    }

    public void setOrderSubject(String orderSubject) {
        this.orderSubject = orderSubject;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}