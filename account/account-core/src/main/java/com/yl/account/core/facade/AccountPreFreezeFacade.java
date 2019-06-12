/**
 * 
 */
package com.yl.account.core.facade;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountPreFreezeResponse;

/**
 * 预冻处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月14日
 * @version V1.0.0
 */
public interface AccountPreFreezeFacade {
	/**
	 * @Description 预冻处理
	 * @param accountBussinessInterfaceBean
	 * @return
	 * @see 需要参考的类或方法
	 */
	AccountPreFreezeResponse preFreeze(AccountBussinessInterfaceBean accountBussinessInterfaceBean);
}
