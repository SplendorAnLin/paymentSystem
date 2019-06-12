package com.yl.rate.core.entity;

import lombok.Data;
import java.util.Date;

/**
 * 费率规则
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/27 0027
 */
@Data
public class RateRuleHistory extends BaseEntity {

    /** 费率编号 */
    private String rateCode;

    /**
     * 操作人
     */
    private String oper;

    /**
     * 历史所属ID
     */
    private Long ownerRateId;

    public RateRuleHistory() {
        super();
    }

    public RateRuleHistory(RateRule rateRule, String oper, Long ownerRateId) {
        this.ownerRateId = ownerRateId;
        this.setCode(rateRule.getCode());
        this.setCreateTime(new Date());
        this.rateCode = rateRule.getRateCode();
        this.oper = oper;
    }

    public RateRuleHistory(RateRule rateRule, String oper) {
        this.setCode(rateRule.getCode());
        this.setCreateTime(new Date());
        this.rateCode = rateRule.getRateCode();
        this.oper = oper;
    }
}