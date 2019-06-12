package com.yl.recon.core.entity;

import com.yl.recon.core.enums.ReconStatus;
import com.yl.recon.core.enums.ReconType;
import lombok.Data;

import java.util.Date;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
@Data
public class ReconOrder extends BaseEntity {
	private static final long serialVersionUID = -8815970512424819031L;
	/**
	 * 对账日期
	 */
	private Date reconDate;
	/**
	 * 对账类型
	 */
	private ReconType reconType;
	/**
	 * 对账状态
	 */
	private ReconStatus reconStatus;
	/**
	 * 匹配元素A
	 */
	private String matchA;
	/**
	 * A重复笔数
	 */
	private Integer repeatNumA;
	/**
	 * A总笔数
	 */
	private Integer numsA;

	/**
	 * A总金额
	 */
	private Double amountA;

	/**
	 * A单边笔数
	 */
	private Integer failNumsA;

	/**
	 * A单边金额
	 */
	private Double failAmountA;
	/**
	 * 匹配元素B
	 */
	private String matchB;
	/**
	 * B重复笔数
	 */
	private Integer repeatNumB;
	/**
	 * B总笔数
	 */
	private Integer numsB;
	/**
	 * B总金额
	 */
	private Double amountB;
	/**
	 * B单边笔数
	 */
	private Integer failNumsB;
	/**
	 * B单边金额
	 */
	private Double failAmountB;
	/**
	 * 对账描述
	 */
	private String msg;
	/**
	 * 失败原因
	 */
	private String failureReason;
	/**
	 * 金额错误笔数
	 */
	private Integer amountErrNum;


}