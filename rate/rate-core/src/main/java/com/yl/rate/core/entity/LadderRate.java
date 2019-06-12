package com.yl.rate.core.entity;

import lombok.Data;

import com.yl.rate.core.enums.ComputeMode;
import com.yl.rate.core.enums.Condition;

/**
 * @author Shark
 * @Description 阶梯费率表
 * @Date 2017/12/7 15:55
 */
@Data
public class LadderRate extends BaseEntity {

    /**
     * 阶梯费率条件
     */
    private Condition rateCondition;
    /**
     * 开始条件
     */
    private String beginCondition;
    /**
     * 结束条件
     */
    private String endCondition;

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
}
