/**
 * 
 */
package com.yl.account.core.dao;

import com.yl.account.model.AccountFreezeBalanceDetail;

/**
 * 冻结资金明细数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月22日
 * @version V1.0.0
 */
public interface AccountFreezeBalanceDetailDao {

	/**
	 * @Description 记录冻结资金明细信息
	 * @param accountFreezeBalanceDetail 冻结资金明细信息
	 * @see 需要参考的类或方法
	 */
	void create(AccountFreezeBalanceDetail accountFreezeBalanceDetail);

	/**
	 * @Description 系统编码、系统请求流水、业务类型、请款金额做幂等
	 * @param systemCode 系统编码
	 * @param systemFlowId 系统请求流水
	 * @param bussinessCode 业务类型
	 * @param consultAmt 请款金额
	 * @see 需要参考的类或方法
	 */
	boolean findBy(String systemCode, String systemFlowId, String bussinessCode, double consultAmt);

}
