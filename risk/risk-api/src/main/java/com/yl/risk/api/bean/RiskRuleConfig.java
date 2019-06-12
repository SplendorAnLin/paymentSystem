package com.yl.risk.api.bean;

import com.yl.risk.api.enums.ValidationRules;

/**
 * 风控规则配置
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/10
 */
public class RiskRuleConfig extends AutoIDEntity {

    /** 验证规则 **/
    private ValidationRules validationRules;

    /** 接口编号 **/
    private String interfaceCode;

    /** 接口实现类名称 **/
    private String interfaceImplClassName;

    /** 名称 **/
    private String name;

    /** 配置 **/
    private String validationConfig;

    public ValidationRules getValidationRules() {
        return validationRules;
    }

    public void setValidationRules(ValidationRules validationRules) {
        this.validationRules = validationRules;
    }

    public String getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }

    public String getInterfaceImplClassName() {
        return interfaceImplClassName;
    }

    public void setInterfaceImplClassName(String interfaceImplClassName) {
        this.interfaceImplClassName = interfaceImplClassName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValidationConfig() {
        return validationConfig;
    }

    public void setValidationConfig(String validationConfig) {
        this.validationConfig = validationConfig;
    }
}