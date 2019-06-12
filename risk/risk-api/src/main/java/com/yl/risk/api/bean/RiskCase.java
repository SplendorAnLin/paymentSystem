package com.yl.risk.api.bean;

import com.yl.risk.api.enums.BusinessType;
import com.yl.risk.api.enums.PayType;
import com.yl.risk.api.enums.Status;

/**
 * 风控案例
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/10
 */
public class RiskCase extends AutoIDEntity {

    /** 业务类型 **/
    private BusinessType businessType;

    /** 来源 **/
    private String source;

    /** 支付方式 **/
    private PayType payType;

    /** 规则编号 */
    private Integer ruleId;

    /** 风险处理编号 **/
    private Integer riskProcessorId;

    /** 状态 **/
    private Status status;

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

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getRiskProcessorId() {
        return riskProcessorId;
    }

    public void setRiskProcessorId(Integer riskProcessorId) {
        this.riskProcessorId = riskProcessorId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}