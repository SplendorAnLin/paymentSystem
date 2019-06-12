package com.yl.payinterface.core.handle.impl.quick.pengrui100101.bean;

import net.sf.json.JSONObject;

/**
 * 全时优服 H5 交易Bean
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/20
 */
public class PengRui100101PayBean {

    /** 交易头 */
    private JSONObject head;

    /** 前台通知地址URL */
    private String frontNotifyUrl;

    /** 后台通知地址URL */
    private String backNotifyUrl;

    /** 末端商户号 */
    private String platMerchantCode;

    /** 日切日期 */
    private String cutDate;

    /** 交易金额 */
    private String txnAmt;

    /** 持卡人姓名 */
    private String cardHolderName;

    /** 手机号 */
    private String mobile2;

    /** 身份证号 */
    private String idCard;

    /** 入账银行卡号 */
    private String inBankCardNo;

    /** 支付银行卡号 */
    private String payBankCardNo;

    /** 银行名称 */
    private String inBankName;

    /** 银行总行联行号 */
    private String inBankUnitNo;

    /** 到账金额 */
    private String inTxnAmt;

    /** 消费交易手续费扣率 */
    private String t0TxnRate;

    /** 单笔消费交易手续费 */
    private String t0TxnFee;

    /** 手续费 */
    private String fee;

    public String getFrontNotifyUrl() {
        return frontNotifyUrl;
    }

    public void setFrontNotifyUrl(String frontNotifyUrl) {
        this.frontNotifyUrl = frontNotifyUrl;
    }

    public String getBackNotifyUrl() {
        return backNotifyUrl;
    }

    public void setBackNotifyUrl(String backNotifyUrl) {
        this.backNotifyUrl = backNotifyUrl;
    }

    public String getPlatMerchantCode() {
        return platMerchantCode;
    }

    public void setPlatMerchantCode(String platMerchantCode) {
        this.platMerchantCode = platMerchantCode;
    }

    public String getCutDate() {
        return cutDate;
    }

    public void setCutDate(String cutDate) {
        this.cutDate = cutDate;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getInBankCardNo() {
        return inBankCardNo;
    }

    public void setInBankCardNo(String inBankCardNo) {
        this.inBankCardNo = inBankCardNo;
    }

    public String getPayBankCardNo() {
        return payBankCardNo;
    }

    public void setPayBankCardNo(String payBankCardNo) {
        this.payBankCardNo = payBankCardNo;
    }

    public String getInBankName() {
        return inBankName;
    }

    public void setInBankName(String inBankName) {
        this.inBankName = inBankName;
    }

    public String getInBankUnitNo() {
        return inBankUnitNo;
    }

    public void setInBankUnitNo(String inBankUnitNo) {
        this.inBankUnitNo = inBankUnitNo;
    }

    public String getInTxnAmt() {
        return inTxnAmt;
    }

    public void setInTxnAmt(String inTxnAmt) {
        this.inTxnAmt = inTxnAmt;
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

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public JSONObject getHead() {
        return head;
    }

    public void setHead(JSONObject head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "PengRui100101PayBean{" +
                "head=" + head +
                ", frontNotifyUrl='" + frontNotifyUrl + '\'' +
                ", backNotifyUrl='" + backNotifyUrl + '\'' +
                ", platMerchantCode='" + platMerchantCode + '\'' +
                ", cutDate='" + cutDate + '\'' +
                ", txnAmt='" + txnAmt + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", mobile2='" + mobile2 + '\'' +
                ", idCard='" + idCard + '\'' +
                ", inBankCardNo='" + inBankCardNo + '\'' +
                ", payBankCardNo='" + payBankCardNo + '\'' +
                ", inBankName='" + inBankName + '\'' +
                ", inBankUnitNo='" + inBankUnitNo + '\'' +
                ", inTxnAmt='" + inTxnAmt + '\'' +
                ", t0TxnRate='" + t0TxnRate + '\'' +
                ", t0TxnFee='" + t0TxnFee + '\'' +
                ", fee='" + fee + '\'' +
                '}';
    }
}