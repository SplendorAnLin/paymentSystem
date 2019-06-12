/**
 * 
 */
package com.yl.account.api.bean.response;

import com.yl.account.api.bean.BussinessResponse;
import com.yl.account.api.enums.Status;

/**
 * 复合记账响应
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月14日
 * @version V1.0.0
 */
public class AccountCompositeTallyResponse extends BussinessResponse {

	private static final long serialVersionUID = 1441704334457290978L;

	/** 出账状态 */
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AccountCompositeTallyResponse [status=" + status + "]";
	}

}
