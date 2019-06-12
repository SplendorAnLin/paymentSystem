package com.yl.recon.core.entity.other.order;

import com.yl.recon.core.enums.SystemCode;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月16
 * @desc 对账所需基础属性
 **/
@Data
public class BaseOrder implements Serializable {

	/**
	 * 订单号
	 */
	private String orderCode;
	/**
	 * 类型：业务类型
	 */
	private String type;
	/**
	 * 交易类型
	 */
	private String payType;
	/**
	 * 交易金额
	 */
	private Double amount;
	/**
	 * 手续费
	 */
	private Double fee;
	/**
	 * 成本
	 */
	private Double cost;
	/**
	 * 完成时间
	 */
	private Date finishTime;
	/**
	 * 接口编号
	 */
	private String interfaceCode;
	/**
	 * 接口请求号
	 */
	private String interfaceRequestId;
	/**
	 * 系统编码
	 */
	private SystemCode systemCode;


 }
