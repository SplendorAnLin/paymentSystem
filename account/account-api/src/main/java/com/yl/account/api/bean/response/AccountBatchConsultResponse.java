/**
 * 
 */
package com.yl.account.api.bean.response;

import com.yl.account.api.bean.BussinessResponse;
import com.yl.account.api.enums.Status;

/**
 * 批量请款响应
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountBatchConsultResponse extends BussinessResponse {

	private static final long serialVersionUID = -7690991674460058163L;

	/** 请款状态 */
	private Status status;
	/** 可用请款金额 */
	private double remainAmount;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public double getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(double remainAmount) {
		this.remainAmount = remainAmount;
	}

	@Override
	public String toString() {
		return "AccountBatchConsultResponse [status=" + status + ", remainAmount=" + remainAmount + "]";
	}

}
