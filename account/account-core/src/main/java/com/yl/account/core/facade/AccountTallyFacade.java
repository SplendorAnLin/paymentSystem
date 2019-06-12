/**
 * 
 */
package com.yl.account.core.facade;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.bean.TradeVoucher;

/**
 * 账务记账处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2014年8月21日
 * @version V1.0.0
 */
public interface AccountTallyFacade {

	/**
	 * @Description 记账处理
	 * @param tradeVoucher 记账处理实体
	 * @param accountBussinessInterfaceBean 业务系统请求信息
	 * @see 需要参考的类或方法
	 */
	void tally(TradeVoucher tradeVoucher, AccountBussinessInterfaceBean accountBussinessInterfaceBean);

}
