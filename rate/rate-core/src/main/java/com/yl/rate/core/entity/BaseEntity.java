package com.yl.rate.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年5月12日
 */
@Data
public abstract class BaseEntity implements Serializable{

    /**
     * id
     */
    private Long id;
    /** 编号 */
    private String code;
    /**
     * 版本号
     */
    private int version;
    /**
     * 创建时间
     */
    private Date createTime;

}
