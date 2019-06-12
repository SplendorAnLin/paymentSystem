package com.yl.payinterface.core.handle.impl.quick.pengrui310103.bean;

import net.sf.json.JSONObject;

/**
 * 全时优服 消费短信Bean
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/2/28
 */
public class PengRui310103SmsBean {

    /** 请求头 */
    private JSONObject head;

    /** 交易日期 */
    private String cutDate;

    /** 通知地址 */
    private String notifyUrl;

    /** 交易金额 */
    private String txnAmt;

    /** 消费交易手续费扣率 */
    private String t0TxnRate;

    /** 单笔消费交易手续费 */
    private String t0TxnFee;

    /** 持卡人姓名 */
    private String cardHolderName;

    /** 手机号 */
    private String mobile;

    /** 身份证号 */
    private String idCard;

    /** cvv2 */
    private String cvv2;

    /** 有效期 MMYY */
    private String validity;

    /** 支付银行卡号 */
    private String payBankCardNo;

    /** 入账银行卡号 */
    private String inBankCardNo;

    /** 支付银行代码 */
    private String payBankCode;

    /** 入账银行代码 */
    private String inBankCode;

    /** 商品名称 */
    private String remark;

    public JSONObject getHead() {
        return head;
    }

    public void setHead(JSONObject head) {
        this.head = head;
    }

    public String getCutDate() {
        return cutDate;
    }

    public void setCutDate(String cutDate) {
        this.cutDate = cutDate;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getT0TxnRate() {
        return t0TxnRate;
    }

    public void setT0TxnRate(String t0TxnRate) {
        this.t0TxnRate = t0TxnRate;
    }

    public String getT0TxnFee() {
        return t0TxnFee;
    }

    public void setT0TxnFee(String t0TxnFee) {
        this.t0TxnFee = t0TxnFee;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getPayBankCardNo() {
        return payBankCardNo;
    }

    public void setPayBankCardNo(String payBankCardNo) {
        this.payBankCardNo = payBankCardNo;
    }

    public String getInBankCardNo() {
        return inBankCardNo;
    }

    public void setInBankCardNo(String inBankCardNo) {
        this.inBankCardNo = inBankCardNo;
    }

    public String getPayBankCode() {
        return payBankCode;
    }

    public void setPayBankCode(String payBankCode) {
        this.payBankCode = payBankCode;
    }

    public String getInBankCode() {
        return inBankCode;
    }

    public void setInBankCode(String inBankCode) {
        this.inBankCode = inBankCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}