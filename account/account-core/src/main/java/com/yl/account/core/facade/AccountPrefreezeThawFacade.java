/**
 * 
 */
package com.yl.account.core.facade;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;

/**
 * 预冻解冻处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2014年9月11日
 * @version V1.0.0
 */
public interface AccountPrefreezeThawFacade {
	/**
	 * @Description 解冻
	 * @param accountBussinessInterfaceBean 业务处理Bean
	 * @see 需要参考的类或方法
	 */
	void thaw(AccountBussinessInterfaceBean accountBussinessInterfaceBean);
}
