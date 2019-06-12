/**
 * 
 */
package com.yl.account.core.facade;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountCompositeTallyResponse;

/**
 * 复合记账处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月14日
 * @version V1.0.0
 */
public interface AccountCompositeTallyFacade {

	/**
	 * @Description 标准复合记账
	 * @param accountBussinessInterfaceBean 复合记账业务处理Bean
	 * @return AccountCompositeTallyResponse 复合记账响应
	 * @see 需要参考的类或方法
	 */
	AccountCompositeTallyResponse standardCompositeTally(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

	/**
	 * @Description 特殊复合记账
	 * @param accountBussinessInterfaceBean 复合记账业务处理Bean
	 * @return AccountCompositeTallyResponse 复合记账响应
	 * @see 需要参考的类或方法
	 */
	AccountCompositeTallyResponse specialCompositeTally(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

}
