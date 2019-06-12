package com.yl.recon.core.entity.other.account;

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
public class BaseAccount implements Serializable {
 	/**
	 * 账号
	 */
	private String accountNo;
	/**
	 * 订单号
	 */
	private String orderCode;
	/**
	 * 交易金额
	 */
	private Double amount;
	/**
	 * 资金标识
	 */
	private String symbol;
	/**
	 * 交易时间
	 */
	private Date transTime;
	/**
	 * 业务码
	 */
	private String bussinessCode;
	/**
	 * 系统编码
	 */
	private SystemCode systemCode;

 }
