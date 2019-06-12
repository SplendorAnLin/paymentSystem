package com.yl.payinterface.core.bean;

import java.io.Serializable;

/**
 * 资金账户Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
public class FundAccountBean implements Serializable {

	private static final long serialVersionUID = -783176750764881998L;

	/** 银行开户名称,商户公司名称或法人姓名 */
	private String AccountName;
	/** 银行账号 */
	private String AccountNo;
	/** 银行编号 */
	private String bankCode;
	/** 开户行名称 */
	private String BankName;

	public String getAccountName() {
		return AccountName;
	}

	public void setAccountName(String accountName) {
		AccountName = accountName;
	}

	public String getAccountNo() {
		return AccountNo;
	}

	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return BankName;
	}

	public void setBankName(String bankName) {
		BankName = bankName;
	}

}
