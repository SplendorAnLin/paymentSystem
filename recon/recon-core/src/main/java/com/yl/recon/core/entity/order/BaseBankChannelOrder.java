package com.yl.recon.core.entity.order;

import com.yl.recon.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 银行通道订单基类
 *
 * @author AnLin
 * @since 2017/6/21
 */
@Data
public class BaseBankChannelOrder extends BaseEntity {
	
	private static final long serialVersionUID = -8218433977382331582L;

	/**
     * 接口编码
     */
    private String interfaceInfoCode;

    /**
     * 银行订单号
     */
    private String bankOrderCode;

    /**
     * 接口订单号
     */
    private String interfaceOrderCode;

    /**
     * 订单金额
     */
    private Double amount;

    /**
	 * 手续费
	 */
	private Double fee;

    /**
     * 交易时间
     */
    private Date transTime;

    /**
     * 对账日期
     */
    private Date reconDate;

}
