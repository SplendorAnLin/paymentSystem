/**
 * 
 */
package com.yl.account.api.bean.response;

import com.yl.account.api.bean.BussinessResponse;
import com.yl.account.api.enums.Status;

/**
 * 请款响应
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月15日
 * @version V1.0.0
 */
public class AccountConsultResponse extends BussinessResponse {

	private static final long serialVersionUID = 8310856341363217956L;

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

}
