package com.yl.rate.core.entity;

import com.yl.rate.core.enums.ComputeMode;
import com.yl.rate.core.enums.Condition;
import lombok.Data;

import java.util.Date;

/**
 * @author Shark
 * @Description 阶梯费率历史表
 * @Date 2017/12/7 15:55
 */
@Data
public class LadderRateHistory extends BaseEntity {

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

    /**
     * 操作人
     */
    private String oper;



    public LadderRateHistory() {
        super();
    }

    public LadderRateHistory(LadderRate ladderRate, String oper) {
        this.setCode(ladderRate.getCode());
        this.setCreateTime(new Date());
        this.rateCondition = ladderRate.getRateCondition();
        this.beginCondition = ladderRate.getBeginCondition();
        this.endCondition = ladderRate.getEndCondition();
        this.rateRuleCode = ladderRate.getRateRuleCode();
        this.fee = ladderRate.getFee();
        this.computeMode = ladderRate.getComputeMode();
        this.maxFee = ladderRate.getMaxFee();
        this.minFee = ladderRate.getMinFee();
        this.oper = oper;
    }
}
