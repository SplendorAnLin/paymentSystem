package com.yl.recon.core.entity.order;

import java.util.Date;

import com.yl.recon.core.entity.BaseEntity;
import lombok.Data;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
@Data
public class PayinterfaceOrder extends BaseEntity {

	private static final long serialVersionUID = -353294417093420166L;

	/**
	 * 接口编码
	 */
	private String interfaceCode;

	/**
	 * 接口类型
	 */
	private String interfaceType;

	/**
	 * 接口订单号
	 */
	private String interfaceOrderCode;

	/**
	 * 银行通道订单号
	 */
	private String bankChannelCode;

	/**
	 * 交易订单号
	 */
	private String tradeOrderCode;
	/**
	 * 订单金额
	 */
	private Double amount;
	/**
	 * 手续费
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