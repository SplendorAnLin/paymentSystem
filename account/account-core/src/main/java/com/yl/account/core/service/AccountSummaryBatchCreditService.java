/**
 * 
 */
package com.yl.account.core.service;

import com.yl.account.model.AccountTransitSummary;

/**
 * 批量汇总入账处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
public interface AccountSummaryBatchCreditService {

	/**
	 * @Description 批量汇总入账-满足待入账日期
	 * @see 需要参考的类或方法
	 */
	public void batchSummaryCredit(AccountTransitSummary accountTransitSummary);

}