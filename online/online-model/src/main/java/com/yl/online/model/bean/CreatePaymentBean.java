package com.yl.online.model.bean;

import com.yl.online.model.enums.BusinessType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建支付记录Bean
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年7月13日
 */
public class CreatePaymentBean implements Serializable {
    private static final long serialVersionUID = 3228802686588346452L;
    /**
     * 业务类型
     */
    @NotNull
    private BusinessType businessType;
    /**
     * 订单编号
     */
    @NotNull
    private String tradeOrderCode;
    /**
     * 接口编号
     */
    @NotNull
    private String interfaceCode;
    /**
     * 客户端IP
     */
    private String clientIP;

    // 移动认证支付信息
    /**
     * 银行卡号
     */
    private String bankCardNo;
    /**
     * 有效期
     */
    private String validity;
    /**
     * 安全码
     */
    private String cvv;
    /**
     * 姓名
     */
    private String payerName;
    /**
     * 身份证号
     */
    private String certNo;
    /**
     * 绑定手机号
     */
    private String payerMobNo;
    /**
     * 短信验证码
     */
    private String verifycode;
    /**
     * 授权编号
     */
    private String authCode;
    /**
     * 网银取货地址
     **/
    private String pageNotifyUrl;
    /**
     * 结算金额
     **/
    private Double settleAmount;
    /**
     * 交易手续费
     */
    private Double quickPayFee;
    /**
     * 结算手续费
     */
    private Double remitFee;
    /**
     * 结算费用类型 FEE，AMOUNT
     */
    private String settleType;
    /**
     * 结算卡账号
     **/
    private String settleCardNo;

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    public String getTradeOrderCode() {
        return tradeOrderCode;
    }

    public void setTradeOrderCode(String tradeOrderCode) {
        this.tradeOrderCode = tradeOrderCode;
    }

    public String getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getPayerMobNo() {
        return payerMobNo;
    }

    public void setPayerMobNo(String payerMobNo) {
        this.payerMobNo = payerMobNo;
    }

    public String getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getPageNotifyUrl() {
        return pageNotifyUrl;
    }

    public void setPageNotifyUrl(String pageNotifyUrl) {
        this.pageNotifyUrl = pageNotifyUrl;
    }

    public Double getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(Double settleAmount) {
        this.settleAmount = settleAmount;
    }

    public Double getQuickPayFee() {
        return quickPayFee;
    }

    public void setQuickPayFee(Double quickPayFee) {
        this.quickPayFee = quickPayFee;
    }

    public Double getRemitFee() {
        return remitFee;
    }

    public void setRemitFee(Double remitFee) {
        this.remitFee = remitFee;
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public String getSettleCardNo() {
        return settleCardNo;
    }

    public void setSettleCardNo(String settleCardNo) {
        this.settleCardNo = settleCardNo;
    }

    @Override
    public String toString() {
        return "CreatePaymentBean{" +
                "businessType=" + businessType +
                ", tradeOrderCode='" + tradeOrderCode + '\'' +
                ", interfaceCode='" + interfaceCode + '\'' +
                ", clientIP='" + clientIP + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", validity='" + validity + '\'' +
                ", cvv='" + cvv + '\'' +
                ", payerName='" + payerName + '\'' +
                ", certNo='" + certNo + '\'' +
                ", payerMobNo='" + payerMobNo + '\'' +
                ", verifycode='" + verifycode + '\'' +
                ", authCode='" + authCode + '\'' +
                ", pageNotifyUrl='" + pageNotifyUrl + '\'' +
                ", settleAmount=" + settleAmount +
                ", quickPayFee=" + quickPayFee +
                ", remitFee=" + remitFee +
                ", settleType='" + settleType + '\'' +
                ", settleCardNo='" + settleCardNo + '\'' +
                '}';
    }
}
