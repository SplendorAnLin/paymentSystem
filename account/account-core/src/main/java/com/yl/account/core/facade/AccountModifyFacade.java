/**
 * 
 */
package com.yl.account.core.facade;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountModifyResponse;

/**
 * 账户修改处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2014年8月14日
 * @version V1.0.0
 */
public interface AccountModifyFacade {
	/**
	 * @Description 更新账户
	 * @param accountBussinessInterfaceBean 更新账户请求
	 * @return AccountModifyResponse 更新账户响应
	 * @see 需要参考的类或方法
	 */
	AccountModifyResponse modifyAccountInfo(AccountBussinessInterfaceBean accountBussinessInterfaceBean);
}
