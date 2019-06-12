package com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.response;

import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.IBody;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class AccountBalanceQueryResponseBody implements IBody {
	private String responseCode;//响应 码
	private String responseMsg;//响应信息
	
	private String mchtId;
	private String accountBalance;//账户余额
	private String creditAmount;  //授信额度
	public String getMchtId() {
		return mchtId;
	}
	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}
	public String getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
}