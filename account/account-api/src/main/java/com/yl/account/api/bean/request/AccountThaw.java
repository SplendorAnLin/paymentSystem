package com.yl.account.api.bean.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 账务解冻请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
public class AccountThaw extends TradeVoucher {

	private static final long serialVersionUID = 7751199991794980790L;

	/** 账号 */
	@NotBlank
	private String accountNo;
	/** 冻结编号 */
	@NotBlank
	private String freezeNo;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getFreezeNo() {
		return freezeNo;
	}

	public void setFreezeNo(String freezeNo) {
		this.freezeNo = freezeNo;
	}

}
