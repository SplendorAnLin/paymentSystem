package com.yl.payinterface.core.bean;


/**
 * @author Shark
 * @Description
 * @Date 2017/12/27 15:31
 */
public class Cnaps {

    private String code;
    /**
     * 清分行号
     */
    private String clearingBankCode;
    /**
     * 提供方编号
     */
    private String providerCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClearingBankCode() {
        return clearingBankCode;
    }

    public void setClearingBankCode(String clearingBankCode) {
        this.clearingBankCode = clearingBankCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    @Override
    public String toString() {
        return "Cnaps{" +
                "code='" + code + '\'' +
                ", clearingBankCode='" + clearingBankCode + '\'' +
                ", providerCode='" + providerCode + '\'' +
                '}';
    }
}
