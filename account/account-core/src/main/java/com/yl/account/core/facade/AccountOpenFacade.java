/**
 * 
 */
package com.yl.account.core.facade;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountOpenResponse;

/**
 * 开户接口
 * 
 * @author 聚合支付有限公司
 * @since 2014年8月14日
 * @version V1.0.0
 */
public interface AccountOpenFacade {
	/**
	 * @Description 开户
	 * @param accountBussinessInterfaceBean 开户业务请求Bean
	 * @return AccountOpenResponse 开户响应
	 * @see 需要参考的类或方法
	 */
	AccountOpenResponse openAccount(AccountBussinessInterfaceBean accountBussinessInterfaceBean);
}
