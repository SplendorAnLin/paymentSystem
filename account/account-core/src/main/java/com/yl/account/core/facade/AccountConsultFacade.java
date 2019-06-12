/**
 * 
 */
package com.yl.account.core.facade;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountConsultResponse;

/**
 * 请款处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public interface AccountConsultFacade {

	/**
	 * @Description 请款
	 * @param accountBussinessInterfaceBean 请款业务处理Bean
	 * @return AccountConsuleResponse 请款响应信息
	 * @see 需要参考的类或方法
	 */
	AccountConsultResponse consult(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

}
