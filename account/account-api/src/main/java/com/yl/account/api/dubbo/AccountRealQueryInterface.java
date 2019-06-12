/**
 * 
 */
package com.yl.account.api.dubbo;

import java.util.Map;

import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.bean.response.AccountHistoryQueryResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;

/**
 * 账户实时查询处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月22日
 * @version V1.0.0
 */
public interface AccountRealQueryInterface {

	/**
	 * @Description 账户余额查询-主节点
	 * @param accountBalanceQuery 账户余额查询请求
	 * @return {@link AccountHistoryQueryResponse} 账户余额查询响应
	 * @see 需要参考的类或方法
	 */
	public AccountBalanceQueryResponse _findAccountBalance(AccountBalanceQuery accountBalanceQuery);

	/**
	 * @Description 参数查询账户历史信息-主节点
	 * @param queryParams 查询参数
	 * @return {@link AccountHistoryQueryResponse}
	 * @see 需要参考的类或方法
	 */
	public AccountHistoryQueryResponse _findAccountHistoryBy(Map<String, Object> queryParams);

	/**
	 * @Description 参数查询账户信息-主节点
	 * @param accountQuery 查询参数
	 * @return {@link AccountQueryResponse}
	 * @see 需要参考的类或方法
	 */
	public AccountQueryResponse _findAccountBy(AccountQuery accountQuery);

	/**
	 * @Description 账户编号查询账户信息
	 * @param accountNo 账号
	 * @return {@link AccountQueryResponse}
	 * @see 需要参考的类或方法
	 */
	public AccountQueryResponse _findAccontByAccountNo(String accountNo);

}
