/**
 * 
 */
package com.yl.account.core.facade;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountBatchConsultResponse;

/**
 * 批量请款处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月14日
 * @version V1.0.0
 */
public interface AccountBatchConsultFacade {

	/**
	 * @Description 批量请款
	 * @param accountBussinessInterfaceBean 批量请款业务处理Bean
	 * @return AccountConsuleResponse 批量请款响应信息
	 * @see 需要参考的类或方法
	 */
	AccountBatchConsultResponse batchConsult(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

}
