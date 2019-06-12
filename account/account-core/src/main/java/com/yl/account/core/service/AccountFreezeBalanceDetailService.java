/**
 * 
 */
package com.yl.account.core.service;

import java.util.Date;

/**
 * 冻结资金明细业务处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
public interface AccountFreezeBalanceDetailService {

	/**
	 * @Description 记录冻结资金明细信息
	 * @param systemCode 系统编码
	 * @param accountNo 账号
	 * @param transOrder 交易订单号
	 * @param amount 金额
	 * @param freezeLimit 冻结期限
	 * @param freezeHandleType 业务类型
	 * @see 需要参考的类或方法
	 */
	void create(String systemCode, String accountNo, String transOrder, double amount, Date freezeLimit, String freezeHandleType);

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
