package com.yl.risk.core.entity;

/**
 * 风险处理
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/10
 */
public class RiskProcessor extends AutoIDEntity {

    /** 响应编码 **/
    private String responseCode;

    /** 名称 **/
    private String riskProcessorName;

    /** 备注 **/
    private String remark;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getRiskName() {
        return riskProcessorName;
    }

    public void setRiskName(String riskName) {
        this.riskProcessorName = riskName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}