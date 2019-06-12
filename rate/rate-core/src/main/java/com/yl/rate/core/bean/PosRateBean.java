package com.yl.rate.core.bean;

import com.yl.rate.core.enums.CardType;
import com.yl.rate.core.enums.ComputeMode;
import com.yl.rate.core.enums.Status;
import lombok.Data;

import java.util.Date;

/**
 * @author Shark
 * @Description
 * @Date 2017/12/7 20:59
 */
@Data
public class PosRateBean {
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

    /**
     * 费率编号
     */
    private String rateCode;
}
