package com.yl.account.api.bean.response;

import com.yl.account.api.bean.BussinessResponse;
import com.yl.account.api.enums.Status;

/**
 * 账务延迟入账响应
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月21日
 * @version V1.0.0
 */
public class AccountDelayTallyResponse extends BussinessResponse {

	private static final long serialVersionUID = -5198151485010044334L;

	/** 账号 */
	private String accountNo;
	/** 入账状态 */
	private Status status;
	/** 原业务系统交易订单号 */
	private String transOrder;

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

	public String getTransOrder() {
		return transOrder;
	}

	public void setTransOrder(String transOrder) {
		this.transOrder = transOrder;
	}

	@Override
	public String toString() {
		return "AccountDelayCreditResponse [accountNo=" + accountNo + ", status=" + status + ", transOrder=" + transOrder + "]";
	}

}
