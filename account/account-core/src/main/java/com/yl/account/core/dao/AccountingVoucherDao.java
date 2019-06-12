/**
 * 
 */
package com.yl.account.core.dao;

import com.yl.account.model.AccountingVoucher;

/**
 * 记账凭证数据处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public interface AccountingVoucherDao {
	/**
	 * @Description 持久化记账凭证
	 * @param accountingVoucher 记账凭证
	 * @see 需要参考的类或方法
	 */
	public void create(AccountingVoucher accountingVoucher);
}
