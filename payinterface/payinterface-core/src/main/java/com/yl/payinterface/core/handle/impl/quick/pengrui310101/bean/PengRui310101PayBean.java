package com.yl.payinterface.core.handle.impl.quick.pengrui310101.bean;

import net.sf.json.JSONObject;

/**
 * 全时优服 银联快捷支付Bean
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/7
 */
public class PengRui310101PayBean {

    /** 请求头 */
    private JSONObject head;

    /** 上游平台商户号 */
    private String platMerchantCode;

    /** 交易日期 */
    private String cutDate;

    /** 支付银行卡号 */
    private String payBankCardNo;

    /** 持卡人姓名 */
    private String cardHolderName;

    /** 手机号 */
    private String mobile;

    /** 身份证 */
    private String idCard;

    /** CVV2 */
    private String cvv2;

    /** 有效期 */
    private String validity;

    /** 支付密码 */
    private String pwd;

    /** 用户ID */
    private String userId;

    /** IP */
    private String ip;

    /** 交易金额 */
    private String txnAmt;

    /** 超时时间 */
    private String expireTime;

    /** 备注 */
    private String remark;

    /** 备用字段1 */
    private String extend1;

    /** 备用字段2 */
    private String extend2;

    /** 备用字段3 */
    private String extend3;

    public JSONObject getHead() {
        return head;
    }

    public void setHead(JSONObject head) {
        this.head = head;
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

    public String getPayBankCardNo() {
        return payBankCardNo;
    }

    public void setPayBankCardNo(String payBankCardNo) {
        this.payBankCardNo = payBankCardNo;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    @Override
    public String toString() {
        return "PengRui310101PayBean{" +
                "head='" + head + '\'' +
                ", platMerchantCode='" + platMerchantCode + '\'' +
                ", cutDate='" + cutDate + '\'' +
                ", payBankCardNo='" + payBankCardNo + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", idCard='" + idCard + '\'' +
                ", cvv2='" + cvv2 + '\'' +
                ", validity='" + validity + '\'' +
                ", pwd='" + pwd + '\'' +
                ", userId='" + userId + '\'' +
                ", ip='" + ip + '\'' +
                ", txnAmt='" + txnAmt + '\'' +
                ", expireTime='" + expireTime + '\'' +
                ", remark='" + remark + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                '}';
    }
}