package com.yl.rate.interfaces.beans;

import com.yl.rate.interfaces.enums.InterfaceCardType;
import com.yl.rate.interfaces.enums.InterfaceOwnerRole;
import com.yl.rate.interfaces.enums.InterfaceProductType;
import com.yl.rate.interfaces.enums.InterfaceStatus;

import java.io.Serializable;

/**
 * @author Shark
 * @Description
 * @Date 2017/12/3 17:43
 */
public class RateRequestBean implements Serializable{
    /** 所有者编号 */
    private String ownerId;
    /** 产品类型 */
    private InterfaceProductType productType;
    /** 所有者角色 */
    private InterfaceOwnerRole ownerRole;
    /** 产品编号 */
    private String productCode;
    /** 金额 */
    private Double amount;
    /**卡类型*/
    private InterfaceCardType cardType;
    /** 是否是标准商户 */
    private InterfaceStatus standard;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public InterfaceProductType getProductType() {
        return productType;
    }

    public void setProductType(InterfaceProductType productType) {
        this.productType = productType;
    }

    public InterfaceOwnerRole getOwnerRole() {
        return ownerRole;
    }

    public void setOwnerRole(InterfaceOwnerRole ownerRole) {
        this.ownerRole = ownerRole;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public InterfaceCardType getCardType() {
        return cardType;
    }

    public void setCardType(InterfaceCardType cardType) {
        this.cardType = cardType;
    }

    public InterfaceStatus getStandard() {
        return standard;
    }

    public void setStandard(InterfaceStatus standard) {
        this.standard = standard;
    }

    @Override
    public String toString() {
        return "RateRequestBean{" +
                "ownerId='" + ownerId + '\'' +
                ", productType=" + productType +
                ", ownerRole=" + ownerRole +
                ", productCode='" + productCode + '\'' +
                ", amount=" + amount +
                ", cardType=" + cardType +
                ", standard=" + standard +
                '}';
    }
}
