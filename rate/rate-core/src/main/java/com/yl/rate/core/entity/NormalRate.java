package com.yl.rate.core.entity;

import com.yl.rate.core.enums.ComputeMode;
import lombok.Data;

/**
 * @author Shark
 * @Description 标准费率表
 * @Date 2017/12/3 16:28
 */
@Data
public class NormalRate extends BaseEntity {

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
