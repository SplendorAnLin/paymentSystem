package com.yl.rate.core.entity;

import com.yl.rate.core.enums.CompFeeType;
import com.yl.rate.core.enums.Status;
import lombok.Data;
import java.util.Date;

/**
 * 费率
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/27 0027
 */
@Data
public class RateHistory extends BaseEntity {

    /**
     * 费率状态
     */
    private Status status;
    /**
     * 计费类型
     */
    private CompFeeType compFeeType;

    /**
     * 操作人
     */
    private String oper;

    /**
     * 费率历史ID
     */
    private Long ownerRateId;


    public RateHistory() {
        super();
    }

    public RateHistory(Rate rate, String oper) {
        this.setCode(rate.getCode());
        this.setCreateTime(new Date());
        this.status = rate.getStatus();
        this.compFeeType = rate.getCompFeeType();
        this.oper = oper;
    }

    public RateHistory(Rate rate, String oper, Long ownerRateId) {
        this.setCode(rate.getCode());
        this.setCreateTime(new Date());
        this.ownerRateId = ownerRateId;
        this.status = rate.getStatus();
        this.compFeeType = rate.getCompFeeType();
        this.oper = oper;
    }
}
