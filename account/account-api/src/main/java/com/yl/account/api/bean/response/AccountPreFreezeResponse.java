package com.yl.account.api.bean.response;

import com.yl.account.api.bean.BussinessResponse;

/**
 * 账务预冻响应
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class AccountPreFreezeResponse extends BussinessResponse {

	private static final long serialVersionUID = -1680811385251799846L;

	/** 冻结编号 */
	private String freezeNo;
	/** 预冻完成金额 */
	private double remainBalance;

	public String getFreezeNo() {
		return freezeNo;
	}

	public void setFreezeNo(String freezeNo) {
		this.freezeNo = freezeNo;
	}

	public double getRemainBalance() {
		return remainBalance;
	}

	public void setRemainBalance(double remainBalance) {
		this.remainBalance = remainBalance;
	}

	@Override
	public String toString() {
		return "AccountPreFreezeResponse [freezeNo=" + freezeNo + ", remainBalance=" + remainBalance + "]";
	}

}
