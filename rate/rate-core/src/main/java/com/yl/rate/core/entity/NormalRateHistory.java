package com.yl.rate.core.entity;

import com.yl.rate.core.enums.ComputeMode;
import lombok.Data;

import java.util.Date;

/**
 * @author Shark
 * @Description 标准费率历史表
 * @Date 2017/12/3 16:28
 */
@Data
public class NormalRateHistory extends BaseEntity {

    /**
     * 费率规则表
     */
    private String rateRuleCode;

    /**
     * 费率
     */
    private Double fee;
    /**
     * 计算方式
     */
    private ComputeMode computeMode;

    /**
     * 最大费率
     */
    private Double maxFee;
    /**
     * 最小费率
     */
    private Double minFee;

    /**
     * 操作人
     */
    private String oper;



    public NormalRateHistory() {
        super();
    }

    public NormalRateHistory(NormalRate normalRate, String oper) {
        this.setCode(normalRate.getCode());
        this.setCreateTime(new Date());
        this.rateRuleCode = normalRate.getRateRuleCode();
        this.fee = normalRate.getFee();
        this.computeMode = normalRate.getComputeMode();
        this.maxFee = normalRate.getMaxFee();
        this.minFee = normalRate.getMinFee();
        this.oper = oper;
    }
}
