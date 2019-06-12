package com.yl.risk.api.bean;

import java.util.Date;

/**
 * 风控订单请求记录
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/14
 */
public class RiskOrderRecord {

    /** 主键 */
    private Integer id;

    /** 创建时间 */
    private Date createTime;

    /** 订单号 */
    private String orderCode;

    /** 风控案例编号 */
    private Integer riskCaseId;

    /** 响应码编号 */
    private String responseCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getRiskCaseId() {
        return riskCaseId;
    }

    public void setRiskCaseId(Integer riskCaseId) {
        this.riskCaseId = riskCaseId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}