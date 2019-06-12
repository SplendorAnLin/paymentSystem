package com.yl.recon.core.entity.order;

import java.util.Date;

import com.yl.recon.core.entity.BaseEntity;
import com.yl.recon.core.enums.TransType;
import lombok.Data;

/**
 * 交易订单
 *
 * @author AnLin
 * @since 2017/6/21
 */
@Data
public class TradeOrder extends BaseEntity {

	/**
	 * 交易订单号
	 */
	private String tradeOrderCode;
	/**
	 * 交易类型
	 */
	private TransType transType;
	/**
	 * 支付类型
	 */
	private String payType;
	/**
	 * 接口类型
	 */
	private String interfaceType;
	/**
	 * 接口编号
	 */
	private String interfaceCode;
	/**
	 * 接口订单号
	 */
	private String interfaceOrderCode;
	/**
	 * 订单金额
	 */
	private Double amount;
	/**
	 * 订单手续费
	 */
	private Double fee;
	/**
	 * 交易完成时间
	 */
	private Date transTime;
	/**
	 * 对账日期
	 */
	private Date reconDate;
	/**
	 * 金额错误备注
	 */
	private String remark;


}
