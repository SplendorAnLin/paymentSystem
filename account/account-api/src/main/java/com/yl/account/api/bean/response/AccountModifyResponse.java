package com.yl.account.api.bean.response;

import com.yl.account.api.bean.BussinessResponse;

/**
 * 账务账户修改响应
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class AccountModifyResponse extends BussinessResponse {

	private static final long serialVersionUID = 5956638431909143297L;

	/** 账户编号 */
	private String accountNo;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Override
	public String toString() {
		return "AccountModifyResponse [accountNo=" + accountNo + "]";
	}

}
