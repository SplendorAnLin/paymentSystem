/**
 * 
 */
package com.yl.account.core.service;

import java.util.Date;
import java.util.List;

import com.yl.account.enums.Currency;
import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.TransitDebitSeq;
import com.yl.account.model.AccountTransitBalanceDetail;

/**
 * 账务在途资金明细接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月17日
 * @version V1.0.0
 */
public interface AccountTransitBalanceDetailService {

	/**
	 * @Description 记录入账在途资金明细
	 * @param accountNo 账号
	 * @param currency 币种
	 * @param transOrder 交易订单号
	 * @param transDate 交易日期
	 * @param systemCode 系统编码
	 * @param amount 入账金额
	 * @param symbol 资金标识
	 * @param fee 手续费
	 * @param feeSymbol 手续费资金标识
	 * @param bussinessCode 业务类型
	 * @param waitAccountDate 带入账日期
	 * @return AccountTransitBalanceDetail 在途资金明细
	 * @see 需要参考的类或方法
	 */
	public AccountTransitBalanceDetail create(String accountNo, Currency currency, String transOrder, Date transDate, String systemCode, double amount,
			FundSymbol symbol, Double fee, FundSymbol feeSymbol, String bussinessCode, Date waitAccountDate);

	/**
	 * @Description 账号、原交易订单号查询在途资金明细
	 * @param accountNo 账号
	 * @param origTransOrder 原交易订单号
	 * @param origBussinessCode 原业务类型
	 * @param origBussinessCodeFee 原业务类型手续费
	 * @param transitDebitSeq 查询顺序
	 * @return List<AccountTransitBalanceDetail> 在途资金明细
	 * @see 需要参考的类或方法 在途资金明细
	 */
	public List<AccountTransitBalanceDetail> findTransitBalanceDetailBy(String accountNo, String origTransOrder, String origBussinessCode,
			String origBussinessCodeFee, TransitDebitSeq transitDebitSeq);

	/**
	 * @Description 更新账户待入账日期
	 * @param originalAccountTransitBalanceDetail 原在途资金明细
	 * @see 需要参考的类或方法
	 */
	public void modifyWaitAccountDate(AccountTransitBalanceDetail originalAccountTransitBalanceDetail);
	
	/**
	 * @Description 根据账户、日期查询在途明细
	 * @param accountNo
	 * @param accountDate
	 * @return List<AccountTransitBalanceDetail>
	 */
	public List<AccountTransitBalanceDetail> findTransitBalanceDetailBy(String accountNo,String accountDate);

}
