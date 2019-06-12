package com.yl.rate.interfaces.beans;

import com.yl.rate.interfaces.enums.CompFeeType;
import com.yl.rate.interfaces.enums.Status;

/**
 * 费率
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/27 0027
 */
public class RateBean extends BaseBean {

    /**
     * 费率状态
     */
    private Status status;
    /**
     * 计费类型
     */
    private CompFeeType compFeeType;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CompFeeType getCompFeeType() {
        return compFeeType;
    }

    public void setCompFeeType(CompFeeType compFeeType) {
        this.compFeeType = compFeeType;
    }

    @Override
    public String toString() {
        return "RateBean{" +
                "status=" + status +
                ", compFeeType=" + compFeeType +
                '}';
    }
}
