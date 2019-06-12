package com.yl.risk.api.bean;

import java.util.Date;

/**
 * 自动基础实体
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年7月8日
 */
public abstract class AutoIDEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer optimistic;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOptimistic() {
        return optimistic;
    }

    public void setOptimistic(Integer optimistic) {
        this.optimistic = optimistic;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
