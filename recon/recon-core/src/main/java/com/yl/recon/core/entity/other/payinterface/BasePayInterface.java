package com.yl.recon.core.entity.other.payinterface;

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
public class BasePayInterface implements Serializable {
	/**
	 * 接口编号
	 */
	private String interfaceInfoCode;
	/**
	 * 交易类型
	 */
	private String payType;
	/**
	 * 接口订单id
	 */
	private String interfaceOrderId;
	/**
	 * 接口请求id
	 */
	private String interfaceRequestId;
	/**
	 * 交易订单号
	 */
	private String tradeOrderCode;
	/**
	 * 金额
	 */
	private Double amount;
	/**
	 * 手续费/成本
	 */
	private Double fee;
	/**
	 * 完成时间
	 */
	private Date completeTime;
	/**
	 * 系统编码
	 */
	private SystemCode systemCode;

  }
