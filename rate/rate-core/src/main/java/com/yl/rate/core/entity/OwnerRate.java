package com.yl.rate.core.entity;

import com.yl.rate.core.enums.*;
import lombok.Data;

import java.util.Date;

/**
 * 所有者费率
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/27 0027
 */
@Data
public class OwnerRate extends BaseEntity {

    /**
     * 所有者角色
     */
    private OwnerRole ownerRole;
    /**
     * 所有者编号
     */
    private String ownerId;

    /**
     * 商户费率类型
     */
    private OwnerRateType ownerRateType;
    /**
     * 费率编号或者模板编号，跟商户费率类型配合使用
     */
    private String rateCode;
    /**
     * 费率状态
     */
    private Status status;

    /**
     * 费率类型(商户/代理商费率基于产品或通道)
     */
    private FeeType feeType;

    /**
     * 产品类型
     */
    private ProductType productType;

    /**
     * 产品/接口编号
     */
    private String productCode;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 最后修改时间
     */
    private Date lastUpdatedTime;
}