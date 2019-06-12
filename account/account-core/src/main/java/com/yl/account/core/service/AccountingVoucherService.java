/**
 * 
 */
package com.yl.account.core.service;

import com.yl.account.model.AccountingVoucher;

/**
 * 账务记账凭证处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
public interface AccountingVoucherService {

	/**
	 * @Description 记录记账凭证
	 * @param accountingVoucher 记账凭证实体
	 * @see 需要参考的类或方法
	 */
	void recordAccountingVoucher(AccountingVoucher accountingVoucher);

}
