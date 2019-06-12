/**
 * 
 */
package com.yl.account.core.facade;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountFreezeResponse;

/**
 * 账务可用余额冻结处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2014年8月18日
 * @version V1.0.0
 */
public interface AccountFreezeFacade {

	/**
	 * @Description 账户可用余额冻结
	 * @param accountBussinessInterfaceBean 业务处理Bean
	 * @return AccountFreezeResponse 冻结响应处理
	 * @see 需要参考的类或方法
	 */
	AccountFreezeResponse freeze(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

}
