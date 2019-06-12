package com.yl.recon.core.entity.order;

import com.yl.recon.core.entity.BaseEntity;
import com.yl.recon.core.enums.TransType;
import lombok.Data;

import java.util.Date;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月22
 * @desc 代付订单
 **/
@Data
public class RemitOrder extends BaseEntity {
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
