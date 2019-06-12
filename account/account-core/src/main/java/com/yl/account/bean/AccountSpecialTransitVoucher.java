package com.yl.account.bean;

import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.TransitDebitSeq;

/**
 * 账务在途记账请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountSpecialTransitVoucher extends SpecialTallyVoucher {

	private static final long serialVersionUID = -6079837943547558709L;

	/** 在途金额 */
	private Double transitBalance;
	/** 在途手续费金额 */
	private Double transitFeeBalance;
	/** 在途手续费资金标识 */
	private FundSymbol transitFeeFundSymbol;
	/** 在途金额出账顺序 */
	private TransitDebitSeq transitDebitSeq;

	public Double getTransitBalance() {
		return transitBalance;
	}

	public void setTransitBalance(Double transitBalance) {
		this.transitBalance = transitBalance;
	}

	public Double getTransitFeeBalance() {
		return transitFeeBalance;
	}

	public void setTransitFeeBalance(Double transitFeeBalance) {
		this.transitFeeBalance = transitFeeBalance;
	}

	public FundSymbol getTransitFeeFundSymbol() {
		return transitFeeFundSymbol;
	}

	public void setTransitFeeFundSymbol(FundSymbol transitFeeFundSymbol) {
		this.transitFeeFundSymbol = transitFeeFundSymbol;
	}

	public TransitDebitSeq getTransitDebitSeq() {
		return transitDebitSeq;
	}

	public void setTransitDebitSeq(TransitDebitSeq transitDebitSeq) {
		this.transitDebitSeq = transitDebitSeq;
	}

	@Override
	public String toString() {
		return "AccountTransitVoucher [transitBalance=" + transitBalance + ", transitFeeBalance=" + transitFeeBalance + ", transitFeeFundSymbol="
				+ transitFeeFundSymbol + ", transitDebitSeq=" + transitDebitSeq + "]";
	}

}
