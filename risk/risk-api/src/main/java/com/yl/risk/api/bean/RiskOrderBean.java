package com.yl.risk.api.bean;

import com.yl.risk.api.enums.BusinessType;
import com.yl.risk.api.enums.CardType;
import com.yl.risk.api.enums.PayType;
import java.io.Serializable;
import java.util.Date;

/**
 * 风控校验订单基础信息
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/14
 */
public class RiskOrderBean implements Serializable {

    /** 订单号 */
    private String orderCode;

    /** 支付方式 */
    private PayType payType;

    /** 业务类型 */
    private BusinessType businessType;

    /** 来源*/
    private String source;

    /** 付款方 */
    private String payer;

    /** 收款方 */
    private String receiver;

    /** 订单金额 */
    private Double amount;

    /** 卡类型 */
    private CardType cardType;

    /** IP */
    private String ip;

    /** 下单时间 */
    private Date orderTime;

    /** 配置 **/
    private String validationConfig;


    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getValidationConfig() {
        return validationConfig;
    }

    public void setValidationConfig(String validationConfig) {
        this.validationConfig = validationConfig;
    }
}