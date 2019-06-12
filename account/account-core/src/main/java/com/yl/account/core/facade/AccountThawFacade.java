/**
 * 
 */
package com.yl.account.core.facade;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountThawResponse;

/**
 * 账务解冻处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月15日
 * @version V1.0.0
 */
public interface AccountThawFacade {

	/**
	 * @Description 解冻
	 * @param accountBussinessInterfaceBean 业务处理Bean
	 * @return AccountThawResponse 解冻响应信息
	 * @see 需要参考的类或方法
	 */
	AccountThawResponse thaw(AccountBussinessInterfaceBean accountBussinessInterfaceBean);
	
	/**
	 * 根据冻结编号查询账户编号
	 */
	public String queryFreezeAccount(String freezeNo);

}
