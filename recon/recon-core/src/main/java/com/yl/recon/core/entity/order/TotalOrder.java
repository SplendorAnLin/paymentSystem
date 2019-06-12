package com.yl.recon.core.entity.order;


import com.yl.recon.core.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 对账文件：总金额和手续费
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
@Data
public class TotalOrder extends BaseEntity {
	/**
	 * 对账文件
	 */
	private String file;
	/**
	 * 对账文件类型
	 */
	private String reconFileType;
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
	/**
	 * 对账日期
	 */
	private Date reconDate;



}