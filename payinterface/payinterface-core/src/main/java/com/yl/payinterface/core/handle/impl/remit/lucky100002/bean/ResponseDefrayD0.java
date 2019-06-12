package com.yl.payinterface.core.handle.impl.remit.lucky100002.bean;

/**
 * 维基D0代付返回
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/23
 */
public class ResponseDefrayD0 {

    private String responseCode;
    private String responseDesc;
    private String merchantNo;
    private String requestOrder;
    private String userNo;
    private String orderAmount;
    private String returnInfo;
    private String orderStatus;
    private String sign;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

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

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}