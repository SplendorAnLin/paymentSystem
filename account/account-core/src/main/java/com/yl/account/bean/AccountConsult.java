/**
 * 
 */
package com.yl.account.bean;

import javax.validation.constraints.Min;

/**
 * 请款请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountConsult extends TradeVoucher {

	private static final long serialVersionUID = 2050146643155193690L;

	/** 冻结编号 */
	private String freezeNo;
	/** 请款金额 */
	@Min(value = 0)
	private double consultAmount;

	public String getFreezeNo() {
		return freezeNo;
	}

	public void setFreezeNo(String freezeNo) {
		this.freezeNo = freezeNo;
	}

	public double getConsultAmount() {
		return consultAmount;
	}

	public void setConsultAmount(double consultAmount) {
		this.consultAmount = consultAmount;
	}

	@Override
	public String toString() {
		return "AccountConsult [freezeNo=" + freezeNo + ", consultAmount=" + consultAmount + "]";
	}

}
