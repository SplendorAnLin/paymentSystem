package com.yl.risk.core.entity;

import com.yl.risk.api.enums.BusinessType;
import com.yl.risk.core.enums.PayType;
import com.yl.risk.core.enums.Status;
import com.yl.risk.core.enums.YesNo;

/**
 * 商户独立风控配置
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/22
 */
public class RiskCustomerConfig extends AutoIDEntity {

    /** 商户编号 */
    private String customerNo;

    /** 支付方式 */
    private PayType payType;

    /** 业务类型 */
    private BusinessType businessType;

    /** 来源*/
    private String source;

    /** 借贷比例 */
    private String orderGearing;

    /** 借贷验证差额 */
    private Double amount;

    /** 是否验证IP */
    private YesNo yesNo;

    /** 状态 */
    private Status status;


    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
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

    public String getOrderGearing() {
        return orderGearing;
    }

    public void setOrderGearing(String orderGearing) {
        this.orderGearing = orderGearing;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public YesNo getYesNo() {
        return yesNo;
    }

    public void setYesNo(YesNo yesNo) {
        this.yesNo = yesNo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}