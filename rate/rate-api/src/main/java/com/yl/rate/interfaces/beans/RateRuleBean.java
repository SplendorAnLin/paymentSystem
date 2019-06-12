package com.yl.rate.interfaces.beans;

/**
 * 费率规则
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/27 0027
 */
public class RateRuleBean extends BaseBean {

    /** 费率编号 */
    private String rateCode;

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    @Override
    public String toString() {
        return "RateRuleBean{" +
                "rateCode='" + rateCode + '\'' +
                '}';
    }

}
