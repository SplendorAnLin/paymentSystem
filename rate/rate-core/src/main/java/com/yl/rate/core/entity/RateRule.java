package com.yl.rate.core.entity;

import lombok.Data;

/**
 * 费率规则
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/27 0027
 */
@Data
public class RateRule extends BaseEntity{

    /** 费率编号 */
    private String rateCode;

}
