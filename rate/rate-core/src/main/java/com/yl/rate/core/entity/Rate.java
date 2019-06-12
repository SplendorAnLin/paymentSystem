package com.yl.rate.core.entity;

import com.yl.rate.core.enums.CompFeeType;
import com.yl.rate.core.enums.Status;
import lombok.Data;

/**
 * 费率
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/27 0027
 */
@Data
public class Rate extends BaseEntity {

    /**
     * 费率状态
     */
    private Status status;
    /**
     * 计费类型
     */
    private CompFeeType compFeeType;
}
