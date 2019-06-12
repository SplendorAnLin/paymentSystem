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
public class AccountOrder extends BaseEntity {


	private static final long serialVersionUID = -6586069491089604711L;

	/**
	 * 账户编号
	 */
	private String accountNo;

	/**
	 * 交易订单号
	 */
	private String tradeOrderCode;


	private String transType;

	/**
	 * 订单金额
	 */
	private Double amount;

	/**
	 * 资金变动方向
	 */
	private String fundSymbol;

	/**
	 * 交易时间
	 */
	private Date transTime;

	/**
	 * 系统编码
	 */
	private String systemCode;
	/**
	 * 业务编码
	 */
	private String bussinessCode;

	/**
	 * 对账日期
	 */
	private Date reconDate;

	/**
	 * 是否是手续费
	 */
	private String isFee;


}