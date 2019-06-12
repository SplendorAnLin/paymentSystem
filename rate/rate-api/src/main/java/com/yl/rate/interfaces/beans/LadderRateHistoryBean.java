package com.yl.rate.interfaces.beans;

import com.yl.rate.interfaces.enums.ComputeMode;
import com.yl.rate.interfaces.enums.Condition;

public class LadderRateHistoryBean extends BaseBean {
    /**
     * 阶梯费率条件
     */
    private Condition rateCondition;
    /**
     * 开始条件
     */
    private String beginCondition;
    /**
     * 结束条件
     */
    private String endCondition;

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

    /**
     * 操作人
     */
    private String oper;


    public Condition getRateCondition() {
        return rateCondition;
    }

    public void setRateCondition(Condition rateCondition) {
        this.rateCondition = rateCondition;
    }

    public String getBeginCondition() {
        return beginCondition;
    }

    public void setBeginCondition(String beginCondition) {
        this.beginCondition = beginCondition;
    }

    public String getEndCondition() {
        return endCondition;
    }

    public void setEndCondition(String endCondition) {
        this.endCondition = endCondition;
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

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    @Override
    public String toString() {
        return "LadderRateHistoryBean{" +
                "rateCondition=" + rateCondition +
                ", beginCondition='" + beginCondition + '\'' +
                ", endCondition='" + endCondition + '\'' +
                ", rateRuleCode='" + rateRuleCode + '\'' +
                ", fee=" + fee +
                ", computeMode=" + computeMode +
                ", maxFee=" + maxFee +
                ", minFee=" + minFee +
                ", oper='" + oper + '\'' +
                '}';
    }
}