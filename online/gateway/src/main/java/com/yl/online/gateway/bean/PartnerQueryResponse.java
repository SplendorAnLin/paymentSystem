package com.yl.online.gateway.bean;

import java.io.Serializable;

/**
 * 合作方查询响应信息
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月4日
 */
public class PartnerQueryResponse implements Serializable {
    private static final long serialVersionUID = 5813839706537834364L;
    /**
     * 通信处理结果码
     */
    private String responseCode;
    /**
     * 通信处理结果描述
     */
    private String responseMsg;
    /**
     * 合作方编码
     */
    private String partner;
    /**
     * 交易订单状态
     */
    private String partnerOrderStatus;
    /**
     * 实付金额
     */
    private String amount;
    /**
     * 手续费
     */
    private String fee;
    /**
     * 支付系统订单号
     */
    private String orderCode;
    /**
     * 商户订单号
     */
    private String outOrderId;
    /**
     * 下单时间
     */
    private String orderTime;
    /**
     * 支付时间
     */
    private String payTime;
    /**
     * 签名方式
     */
    private String signType;
    /**
     * 合作方签名串
     */
    private String sign;
    /**
     * 交易结果编码
     */
    private String resultCode;
    /**
     * 交易结果描述
     */
    private String resultMsg;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPartnerOrderStatus() {
        return partnerOrderStatus;
    }

    public void setPartnerOrderStatus(String partnerOrderStatus) {
        this.partnerOrderStatus = partnerOrderStatus;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
