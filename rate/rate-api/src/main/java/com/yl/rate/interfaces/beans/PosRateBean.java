package com.yl.rate.interfaces.beans;

import com.yl.rate.interfaces.enums.CardType;
import com.yl.rate.interfaces.enums.ComputeMode;
import com.yl.rate.interfaces.enums.Status;

/**
 * @author Shark
 * @Description 银行卡收单费率表
 * @Date 2017/12/7 16:44
 */
public class PosRateBean extends BaseBean {

    /**
     * 是否是标准商户
     */
    private Status standard;
    /**
     * 卡类型
     */
    private CardType cardType;

    /**
     * 费率规则表
     */
    private String rateRuleCode;

    /**
     * 费率
     */
    private Double fee;
    /**
     * 计算方式
     */
    private ComputeMode computeMode;

    /**
     * 最大费率
     */
    private Double maxFee;
    /**
     * 最小费率
     */
    private Double minFee;

    public Status getStandard() {
        return standard;
    }

    public void setStandard(Status standard) {
        this.standard = standard;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getRateRuleCode() {
        return rateRuleCode;
    }

    public void setRateRuleCode(String rateRuleCode) {
        this.rateRuleCode = rateRuleCode;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public ComputeMode getComputeMode() {
        return computeMode;
    }

    public void setComputeMode(ComputeMode computeMode) {
        this.computeMode = computeMode;
    }

    public Double getMaxFee() {
        return maxFee;
    }

    public void setMaxFee(Double maxFee) {
        this.maxFee = maxFee;
    }

    public Double getMinFee() {
        return minFee;
    }

    public void setMinFee(Double minFee) {
        this.minFee = minFee;
    }

    @Override
    public String toString() {
        return "PosRateBean{" +
                "standard=" + standard +
                ", cardType=" + cardType +
                ", rateRuleCode='" + rateRuleCode + '\'' +
                ", fee=" + fee +
                ", computeMode=" + computeMode +
                ", maxFee=" + maxFee +
                ", minFee=" + minFee +
                '}';
    }
}
