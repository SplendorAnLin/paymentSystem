package com.yl.rate.core.entity;

import com.yl.rate.core.enums.CardType;
import com.yl.rate.core.enums.ComputeMode;
import com.yl.rate.core.enums.Status;
import lombok.Data;

import java.util.Date;

/**
 * 费率历史
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/27 0027
 */
@Data
public class PosRateHistory extends BaseEntity {

    /**
     * 是否是标准商户
     */
    private Status standard;
    /**
     * 卡类型
     */
    private CardType cardType;

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



    public PosRateHistory() {
        super();
    }

    public PosRateHistory(PosRate posRate, String oper) {
        this.setCode(posRate.getCode());
        this.setCreateTime(new Date());
        this.standard = posRate.getStandard();
        this.cardType = posRate.getCardType();
        this.rateRuleCode = posRate.getRateRuleCode();
        this.fee = posRate.getFee();
        this.computeMode = posRate.getComputeMode();
        this.maxFee = posRate.getMaxFee();
        this.minFee = posRate.getMinFee();
        this.oper = oper;
    }
}
