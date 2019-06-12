package com.yl.rate.interfaces.beans;

import com.yl.rate.interfaces.enums.*;
import java.util.Date;

/**
 * 所有者费率配置
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/12
 */
public class OwnerRateBean extends BaseBean {

    /**
     * 所有者角色
     */
    private OwnerRole ownerRole;

    /**
     * 所有者编号
     */
    private String ownerId;

    /**
     * 商户费率类型
     */
    private OwnerRateType ownerRateType;

    /**
     * 费率编号或者模板编号，跟商户费率类型配合使用
     */
    private String rateCode;

    /**
     * 费率状态
     */
    private Status status;

    /**
     * 费率类型(商户/代理商费率基于产品或通道)
     */
    private FeeType feeType;

    /**
     * 产品类型
     */
    private ProductType productType;

    /**
     * 产品/接口编号
     */
    private String productCode;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 最后修改时间
     */
    private Date lastUpdatedTime;

    public OwnerRole getOwnerRole() {
        return ownerRole;
    }

    public void setOwnerRole(OwnerRole ownerRole) {
        this.ownerRole = ownerRole;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public OwnerRateType getOwnerRateType() {
        return ownerRateType;
    }

    public void setOwnerRateType(OwnerRateType ownerRateType) {
        this.ownerRateType = ownerRateType;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    @Override
    public String toString() {
        return "OwnerRateBean{" +
                "ownerRole=" + ownerRole +
                ", ownerId='" + ownerId + '\'' +
                ", ownerRateType=" + ownerRateType +
                ", rateCode='" + rateCode + '\'' +
                ", status=" + status +
                ", feeType=" + feeType +
                ", productType=" + productType +
                ", productCode='" + productCode + '\'' +
                ", remarks='" + remarks + '\'' +
                ", lastUpdatedTime=" + lastUpdatedTime +
                '}';
    }
}