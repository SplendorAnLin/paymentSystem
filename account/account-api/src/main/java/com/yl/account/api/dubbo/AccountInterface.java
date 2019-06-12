package com.yl.account.api.dubbo;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountAdjustResponse;
import com.yl.account.api.bean.response.AccountBatchConsultResponse;
import com.yl.account.api.bean.response.AccountCompositeTallyResponse;
import com.yl.account.api.bean.response.AccountConsultResponse;
import com.yl.account.api.bean.response.AccountDelayTallyResponse;
import com.yl.account.api.bean.response.AccountFreezeResponse;
import com.yl.account.api.bean.response.AccountModifyResponse;
import com.yl.account.api.bean.response.AccountOpenResponse;
import com.yl.account.api.bean.response.AccountPreFreezeResponse;
import com.yl.account.api.bean.response.AccountThawResponse;

/**
 * 账务操作处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月22日
 * @version V1.0.0
 */
public interface AccountInterface {

	/**
	 * @Description 标准复合记账
	 * @param accountBussinessInterfaceBean 复合记账请求
	 * @return AccountBatchTallyResponse 复合记账响应
	 * @see 需要参考的类或方法
	 */
	public AccountCompositeTallyResponse standardCompositeTally(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

	/**
	 * @Description 特殊复合记账
	 * @param accountBussinessInterfaceBean 复合记账请求
	 * @return AccountBatchTallyResponse 复合记账响应
	 * @see 需要参考的类或方法
	 */
	public AccountCompositeTallyResponse specialCompositeTally(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

	/**
	 * @Description 延迟记账
	 * @param accountBussinessInterfaceBean 延迟入账请求
	 * @return 延迟入账响应
	 * @see 需要参考的类或方法
	 */
	public AccountDelayTallyResponse delayTally(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

	/**
	 * @Description 资金冻结
	 * @param accountBussinessInterfaceBean 资金冻结请求
	 * @return AccountFreezeResponse 资金冻结响应
	 * @see 需要参考的类或方法
	 */
	public AccountFreezeResponse freeze(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

	/**
	 * @Description 资金预冻
	 * @param accountBussinessInterfaceBean 资金预冻请求
	 * @return AccountPreFreezeResponse 资金预冻响应
	 * @see 需要参考的类或方法
	 */
	public AccountPreFreezeResponse preFreeze(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

	/**
	 * @Description 资金解冻
	 * @param accountBussinessInterfaceBean 资金解冻请求
	 * @return AccountThawResponse 资金解冻响应
	 * @see 需要参考的类或方法
	 */
	public AccountThawResponse thaw(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

	/**
	 * @Description 请款
	 * @param accountBussinessInterfaceBean 请款请求
	 * @return AccountConsuleResponse 请款响应
	 * @see 需要参考的类或方法
	 */
	public AccountConsultResponse consult(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

	/**
	 * @Description 批量请款
	 * @param accountBussinessInterfaceBean 批量请款请求
	 * @return AccountConsuleResponse 批量请款响应
	 * @see 需要参考的类或方法
	 */
	public AccountBatchConsultResponse batchConsult(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

	/**
	 * @Description 账户开户
	 * @param accountOpen 账户开户请求
	 * @return AccountOpenResponse 账户开户响应
	 * @see 需要参考的类或方法
	 */
	public AccountOpenResponse openAccount(AccountBussinessInterfaceBean accountOpen);

	/**
	 * @Description 账户修改
	 * @param accountModify 账户修改请求
	 * @return AccountModifyResponse 账户修改响应
	 * @see 需要参考的类或方法
	 */
	public AccountModifyResponse modifyAccount(AccountBussinessInterfaceBean accountModify);

	/**
	 * 调账
	 * @param accountBussinessInterfaceBean
	 * @return
	 */
	public AccountAdjustResponse adjustAccount(AccountBussinessInterfaceBean accountBussinessInterfaceBean);
	
	/**
	 * 根据冻结编号查询账户编号
	 */
	public String queryFreezeAccount(String freezeNo);

}
