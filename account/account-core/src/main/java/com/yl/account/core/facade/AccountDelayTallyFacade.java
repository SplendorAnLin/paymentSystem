/**
 * 
 */
package com.yl.account.core.facade;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountDelayTallyResponse;

/**
 * 延迟标记入账接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月14日
 * @version V1.0.0
 */
public interface AccountDelayTallyFacade {

	/**
	 * @Description 延迟标记入账
	 * @param accountBussinessInterfaceBean 记账处理Bean
	 * @return AccountDelayCreditResponse 延迟入账响应结果
	 * @see 需要参考的类或方法
	 */
	AccountDelayTallyResponse delayTally(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

}
