/**
 * 
 */
package com.yl.account.api.bean.response;

import com.yl.account.api.bean.BussinessResponse;

/**
 * 外账记账信息汇总
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月11日
 * @version V1.0.0
 */
public class AccountSummaryResponse extends BussinessResponse {

	private static final long serialVersionUID = -8423465049230978995L;

	/** 增加总金额 */
	private double subtractTotalBalance;
	/** 减少总笔数 */
	private int subtractTotalCount;
	/** 增加总金额 */
	private double raiseTotalBalance;
	/** 增加总笔数 */
	private int raiseTotalCount;

	public double getSubtractTotalBalance() {
		return subtractTotalBalance;
	}

	public void setSubtractTotalBalance(double subtractTotalBalance) {
		this.subtractTotalBalance = subtractTotalBalance;
	}

	public int getSubtractTotalCount() {
		return subtractTotalCount;
	}

	public void setSubtractTotalCount(int subtractTotalCount) {
		this.subtractTotalCount = subtractTotalCount;
	}

	public double getRaiseTotalBalance() {
		return raiseTotalBalance;
	}

	public void setRaiseTotalBalance(double raiseTotalBalance) {
		this.raiseTotalBalance = raiseTotalBalance;
	}

	public int getRaiseTotalCount() {
		return raiseTotalCount;
	}

	public void setRaiseTotalCount(int raiseTotalCount) {
		this.raiseTotalCount = raiseTotalCount;
	}

	@Override
	public String toString() {
		return "AccountSummaryResponse [subtractTotalBalance=" + subtractTotalBalance + ", subtractTotalCount=" + subtractTotalCount + ", raiseTotalBalance="
				+ raiseTotalBalance + ", raiseTotalCount=" + raiseTotalCount + "]";
	}

}
