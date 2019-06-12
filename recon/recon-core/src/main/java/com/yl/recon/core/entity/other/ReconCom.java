package com.yl.recon.core.entity.other;

import lombok.Data;

import java.io.Serializable;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月16
 * @desc 对账合计行
 **/
@Data
public class ReconCom implements Serializable{
	/**
	 * 总金额
	 */
	private String totalAmount;
	/**
	 * 总手续费
	 */
	private String totalFee;
	/**
	 * 总笔数
	 */
	private String totalNum;
}
