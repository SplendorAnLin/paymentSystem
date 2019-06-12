package com.yl.rate.interfaces.beans;

import com.yl.rate.interfaces.enums.OwnerRole;
import com.yl.rate.interfaces.enums.ProductType;
import com.yl.rate.interfaces.enums.Status;

import java.util.Date;

/**
 * 费率模板
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/27 0027
 */
public class RateTemplateBean extends BaseBean  {

    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板状态
     */
    private Status status;

    /**
     * 费率编号
     */
    private String rateCode;

    /**
     * 是否是默认模板
     */
    private Status defaultRate;

    /**
     * 产品类型
     */
    private ProductType productType;
    /**
     * 角色
     */
    private OwnerRole ownerRole;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remarks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public Status getDefaultRate() {
        return defaultRate;
    }

    public void setDefaultRate(Status defaultRate) {
        this.defaultRate = defaultRate;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public OwnerRole getOwnerRole() {
        return ownerRole;
    }

    public void setOwnerRole(OwnerRole ownerRole) {
        this.ownerRole = ownerRole;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "RateTemplateBean{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", rateCode='" + rateCode + '\'' +
                ", defaultRate=" + defaultRate +
                ", productType=" + productType +
                ", ownerRole=" + ownerRole +
                ", updateTime=" + updateTime +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
