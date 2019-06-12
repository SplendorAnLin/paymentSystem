package com.yl.rate.core.entity;

import com.yl.rate.core.enums.OwnerRole;
import com.yl.rate.core.enums.ProductType;
import com.yl.rate.core.enums.Status;
import lombok.Data;

import java.util.Date;

/**
 * 费率模板历史
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/27 0027
 */
@Data
public class RateTemplateHistory extends BaseEntity {

    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板状态
     */
    private Status status;

    /**
     * 费率编号
     */
    private String rateCode;

    /**
     * 是否是默认模板
     */
    private Status defaultRate;

    /**
     * 产品类型
     */
    private ProductType productType;
    /**
     * 角色
     */
    private OwnerRole ownerRole;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 操作人
     */
    private String oper;

    public RateTemplateHistory() {
        super();
    }

    public RateTemplateHistory(RateTemplate rateTemplate, String oper) {
        this.setCode(rateTemplate.getCode());
        this.setCreateTime(new Date());
        this.name = rateTemplate.getName();
        this.status = rateTemplate.getStatus();
        this.rateCode = rateTemplate.getRateCode();
        this.defaultRate = rateTemplate.getDefaultRate();
        this.productType = rateTemplate.getProductType();
        this.ownerRole = rateTemplate.getOwnerRole();
        this.updateTime = rateTemplate.getUpdateTime();
        this.remarks = rateTemplate.getRemarks();
        this.oper = oper;
    }

}
