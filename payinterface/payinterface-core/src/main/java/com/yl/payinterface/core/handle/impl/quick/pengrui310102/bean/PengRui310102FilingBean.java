package com.yl.payinterface.core.handle.impl.quick.pengrui310102.bean;

import net.sf.json.JSONObject;

/**
 * 入网报备Bean
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/27
 */
public class PengRui310102FilingBean {

    /** 请求头 */
    private JSONObject head;

    /** TOKEN */
    private String token;

    /** 商户号 */
    private String merchantCode;

    /** 费率编码 */
    private String rateCode;

    /** 商户名称 */
    private String merName;

    /** 商户简称 */
    private String merAbbr;

    /** 身份证号 */
    private String idCardNo;

    /** 银行卡号 */
    private String bankAccountNo;

    /** 银行预留手机号 */
    private String phoneno;

    /** 持卡人姓名 */
    private String bankAccountName;

    /** 银行账户类型 */
    private String bankAccountType;

    /** 银行名称 */
    private String bankName;

    /** 支行名称 */
    private String bankSubName;

    /** 银行代码 */
    private String bankCode;

    /** 银行代号 */
    private String bankAbbr;

    /** 支行联行号 */
    private String bankChannelNo;

    /** 开户行省份 */
    private String bankProvince;

    /** 开户行城市 */
    private String bankCity;

    /** 借记卡费率 */
    private String debitRate;

    /** 借记卡封顶 */
    private String debitCapAmount;

    /** 信用卡费率 */
    private String creditRate;

    /** 信用卡封顶 */
    private String creditCapAmount;

    /** 提现费率 */
    private String withdrawDepositRate;

    /** 单笔提现手续费 */
    private String withdrawDepositSingleFee;

    /** 是否机构商户 */
    private String isOrgMerchant;

    // 修改报备必带参数
    /** 渠道商户号 */
    private String platMerchantCode;

    /** 1 交易费率变更 2 银行卡信息变更 4 提现费率变更 */
    private String changeType;

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

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getMerAbbr() {
        return merAbbr;
    }

    public void setMerAbbr(String merAbbr) {
        this.merAbbr = merAbbr;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankSubName() {
        return bankSubName;
    }

    public void setBankSubName(String bankSubName) {
        this.bankSubName = bankSubName;
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

    public String getBankChannelNo() {
        return bankChannelNo;
    }

    public void setBankChannelNo(String bankChannelNo) {
        this.bankChannelNo = bankChannelNo;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getDebitRate() {
        return debitRate;
    }

    public void setDebitRate(String debitRate) {
        this.debitRate = debitRate;
    }

    public String getDebitCapAmount() {
        return debitCapAmount;
    }

    public void setDebitCapAmount(String debitCapAmount) {
        this.debitCapAmount = debitCapAmount;
    }

    public String getCreditRate() {
        return creditRate;
    }

    public void setCreditRate(String creditRate) {
        this.creditRate = creditRate;
    }

    public String getCreditCapAmount() {
        return creditCapAmount;
    }

    public void setCreditCapAmount(String creditCapAmount) {
        this.creditCapAmount = creditCapAmount;
    }

    public String getWithdrawDepositRate() {
        return withdrawDepositRate;
    }

    public void setWithdrawDepositRate(String withdrawDepositRate) {
        this.withdrawDepositRate = withdrawDepositRate;
    }

    public String getWithdrawDepositSingleFee() {
        return withdrawDepositSingleFee;
    }

    public void setWithdrawDepositSingleFee(String withdrawDepositSingleFee) {
        this.withdrawDepositSingleFee = withdrawDepositSingleFee;
    }

    public String getIsOrgMerchant() {
        return isOrgMerchant;
    }

    public void setIsOrgMerchant(String isOrgMerchant) {
        this.isOrgMerchant = isOrgMerchant;
    }

    public String getPlatMerchantCode() {
        return platMerchantCode;
    }

    public void setPlatMerchantCode(String platMerchantCode) {
        this.platMerchantCode = platMerchantCode;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }
}
