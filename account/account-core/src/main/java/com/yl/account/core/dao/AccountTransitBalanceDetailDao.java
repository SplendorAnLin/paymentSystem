/**
 * 
 */
package com.yl.account.core.dao;

import java.util.List;

import com.yl.account.enums.TransitDebitSeq;
import com.yl.account.model.AccountTransitBalanceDetail;

/**
 * 在途资金明细数据处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public interface AccountTransitBalanceDetailDao {

	/**
	 * @Description 记录在途资金明细
	 * @param accountTransitBalanceDetail 在途资金明细
	 * @see 需要参考的类或方法
	 */
	AccountTransitBalanceDetail insert(AccountTransitBalanceDetail accountTransitBalanceDetail);

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
	List<AccountTransitBalanceDetail> findTransitBalanceDetailBy(String accountNo, String origTransOrder, String origBussinessCode, String origBussinessCodeFee,
			TransitDebitSeq transitDebitSeq);

	/**
	 * @Description 更新账户待入账日期
	 * @param originalAccountTransitBalanceDetail 原在途资金明细
	 * @see 需要参考的类或方法
	 */
	void modifyWaitAccountDate(AccountTransitBalanceDetail originalAccountTransitBalanceDetail);
	
	/**
	 * @Description 根据账户、日期查询在途明细
	 * @param accountNo
	 * @param accountDate
	 * @return List<AccountTransitBalanceDetail>
	 */
	public List<AccountTransitBalanceDetail> findTransitBalanceDetailBy(String accountNo,String accountDate);

}
