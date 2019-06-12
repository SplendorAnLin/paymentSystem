package com.yl.payinterface.core.handle.impl.quick.pengrui310102.bean;

import net.sf.json.JSONObject;

/**
 * 全时优服 开卡Bean
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/28
 */
public class PengRui310102OpenCardBean {

    /** 请求头 */
    private JSONObject head;

    /** TOKEN */
    private String token;

    /** 短信订单号 */
    private String smsOrderId;

    /** 末端商户号 */
    private String platMerchantCode;

    /** 费率编码 */
    private String rateCode;

    /** 银行卡户名 */
    private String accountName;

    /** 银行卡卡号 */
    private String cardNo;

    /** 银行卡类型 C 信用卡 D 借记卡 */
    private String cardType;

    /** certType ID身份证 */
    private String certType;

    /** 身份证号 */
    private String certNo;

    /** 银行预留手机号 */
    private String phoneno;

    /** cvn2 */
    private String cvn2;

    /** 有效期 MMYY */
    private String expired;

    /** 前台通知地址 */
    private String pageReturnUrl;

    /** 后台通知地址 */
    private String offlineNotifyUrl;

    /** 银行代码 */
    private String bankCode;

    /** 银行代号 */
    private String bankAbbr;

    public JSONObject getHead() {
        return head;
    }

    public void setHead(JSONObject head) {
        this.head = head;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSmsOrderId() {
        return smsOrderId;
    }

    public void setSmsOrderId(String smsOrderId) {
        this.smsOrderId = smsOrderId;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public String getPlatMerchantCode() {
        return platMerchantCode;
    }

    public void setPlatMerchantCode(String platMerchantCode) {
        this.platMerchantCode = platMerchantCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getCvn2() {
        return cvn2;
    }

    public void setCvn2(String cvn2) {
        this.cvn2 = cvn2;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getPageReturnUrl() {
        return pageReturnUrl;
    }

    public void setPageReturnUrl(String pageReturnUrl) {
        this.pageReturnUrl = pageReturnUrl;
    }

    public String getOfflineNotifyUrl() {
        return offlineNotifyUrl;
    }

    public void setOfflineNotifyUrl(String offlineNotifyUrl) {
        this.offlineNotifyUrl = offlineNotifyUrl;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAbbr() {
        return bankAbbr;
    }

    public void setBankAbbr(String bankAbbr) {
        this.bankAbbr = bankAbbr;
    }
}