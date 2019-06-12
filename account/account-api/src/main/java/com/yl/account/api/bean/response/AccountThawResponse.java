package com.yl.account.api.bean.response;

import com.yl.account.api.bean.BussinessResponse;
import com.yl.account.api.enums.Status;

/**
 * 账务解冻响应
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月11日
 * @version V1.0.0
 */
public class AccountThawResponse extends BussinessResponse {

	private static final long serialVersionUID = 8303573823176458422L;

	/** 账号 */
	private String accountNo;
	/** 解冻结果 */
	private Status status;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AccountThawResponse [accountNo=" + accountNo + "]";
	}

}
