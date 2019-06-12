package com.yl.recon.core.entity.order;


import lombok.Data;

/**
 * 对账文件：总金额和手续费
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
@Data
public class AccTotalOrder {
	/**
	 * 总金额
	 */
	private String totalAmount;
	/**
	 * 总笔数
	 */
	private String totalNum;

}