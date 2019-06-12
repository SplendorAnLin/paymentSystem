package com.yl.rate.core.entity;

import lombok.Data;

import com.yl.rate.core.enums.CardType;
import com.yl.rate.core.enums.ComputeMode;
import com.yl.rate.core.enums.Status;

/**
 * @author Shark
 * @Description 银行卡收单费率表
 * @Date 2017/12/7 16:44
 */
@Data
public class PosRate extends BaseEntity {

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
}
