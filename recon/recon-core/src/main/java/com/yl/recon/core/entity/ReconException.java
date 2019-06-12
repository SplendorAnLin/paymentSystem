package com.yl.recon.core.entity;

import com.yl.recon.core.enums.HandleStatus;
import com.yl.recon.core.enums.ReconExceptionType;
import com.yl.recon.core.enums.ReconType;
import lombok.Data;

import java.util.Date;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
@Data
public class ReconException extends BaseEntity {

	private static final long serialVersionUID = -5428894852576290276L;

	/**
	 * 对账单编号
	 */
	private Long reconOrderId;
	/**
	 * 对账日期
	 */
 	private Date reconDate;
	/**
	 * 对账类型
	 */
	private ReconType reconType;
	/**
	 * 金额
	 */
	private double amount;
	/**
	 * 交易订单/账户订单
	 */
	private String tradeOrderCode;
	/**
	 * 接口订单/银行通道订单
	 */
	private String interfaceOrderCode;
	/**
	 * 对账异常类型
	 */
	private ReconExceptionType reconExceptionType;
	/**
	 * 处理状态
	 */
	private HandleStatus handleStatus;
	/**
	 * 处理备注
	 */
	private String handleRemark;
	/**
	 * 操作员
	 */
	private String oper;


}