/**
 * 
 */
package com.yl.account.api.dubbo;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.request.AccountHistoryQuery;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.bean.response.AccountChangeRecordsResponse;
import com.yl.account.api.bean.response.AccountFreezeDetailResponse;
import com.yl.account.api.bean.response.AccountFreezeQueryResponse;
import com.yl.account.api.bean.response.AccountHistoryQueryResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.bean.response.AccountSummaryResponse;

/**
 * 账户查询处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月22日
 * @version V1.0.0
 */
public interface AccountQueryInterface {

	/**
	 * @Description 账户余额查询-主节点
	 * @param accountBalanceQuery 账户余额查询请求
	 * @return AccountBalanceQueryResponse 账户余额查询响应
	 * @see 需要参考的类或方法
	 */
	public AccountBalanceQueryResponse _findAccountBalance(AccountBalanceQuery accountBalanceQuery);

	/**
	 * @Description 账户余额查询
	 * @param accountBalanceQuery 账户余额查询请求
	 * @return AccountBalanceQueryResponse 账户余额查询响应
	 * @see 需要参考的类或方法
	 */
	public AccountBalanceQueryResponse findAccountBalance(AccountBalanceQuery accountBalanceQuery);
	
	/**
	 * 账户查询 （app接口）
	 * @param m
	 * @return
	 */
	 Map<String , Object> queryAccountBalance(Map<String, Object> m);
	/**
	 * 帐户分类余额查询
	 * @return
	 */
	public List<Map<String, Object>> findAccTypeBalance();

	/**
	 * @Description 账户历史查询
	 * @param queryParams 账户历史查询请求
	 * @return AccountHistoryQueryResponse 账户历史查询响应
	 * @see 需要参考的类或方法
	 */
	public AccountHistoryQueryResponse findAccountHistory(Map<String, Object> queryParams, Page<?> page);
	
	/**
	 * 查询账户历史合计
	 * @param queryParams
	 * @return
	 */
	public Map<String, Object> queryAccountHistorySum(Map<String, Object> queryParams);
	
	/**
	 * 账户历史查询
	 */
	 public  List<Map<String, Object>>  queryAccountHistory(Map<String, Object> queryParams);

	/**
	 * @Description 账号查询账户信息
	 * @param accountNo 账号
	 * @return AccountQueryResponse 账户查询响应信息
	 * @see 需要参考的类或方法
	 */
	public AccountQueryResponse findAccontByAccountNo(String accountNo);

	/**
	 * @Description 查询账户信息
	 * @param accountQuery 查询条件
	 * @return AccountQueryResponse 账户查询响应信息
	 * @see 需要参考的类或方法
	 */
	public AccountQueryResponse findAccountBy(AccountQuery accountQuery);

	/**
	 * @Description 分页查询账户信息
	 * @param queryParams 查询参数
	 * @param page 分页实体
	 * @return AccountQueryResponse 账户查询响应信息
	 * @see 需要参考的类或方法
	 */
	public AccountQueryResponse findAccountByPage(Map<String, Object> queryParams, Page<?> page);

	/**
	 * @Description 查询账户汇总信息
	 * @param accountHistoryQuery 账户汇总信息查询
	 * @return AccountSummaryResponse 汇总信息响应
	 * @see 需要参考的类或方法
	 */
	public AccountSummaryResponse findAccountSummary(AccountHistoryQuery accountHistoryQuery);

	/**
	 * @Description 交易订单号获取账户信息
	 * @param systemCode 系统编码
	 * @param transOrder 交易订单号
	 * @return AccountQueryResponse 账户查询响应信息
	 * @see 需要参考的类或方法
	 */
	public AccountQueryResponse findAccountByTransOrder(String systemCode, String transOrder);

	/**
	 * @Description 系统流水号获取账户信息
	 * @param systemCode 系统编码
	 * @param systemFlowId 系统流水号
	 * @return AccountQueryResponse 账户查询响应信息
	 * @see 需要参考的类或方法
	 */
	public AccountQueryResponse findAccountBySystemFlowId(String systemCode, String systemFlowId);

	/**
	 * @Description 分页查询账务变更明细信息
	 * @param queryParams 查询参数
	 * @param page 分页实体
	 * @return AccountChangeRecordsResponse
	 * @see 需要参考的类或方法
	 */
	public AccountChangeRecordsResponse findAccountChangeRecordsBy(Map<String, Object> queryParams, Page<?> page);
	
	/**
	 * 账户明细（app接口）
	 * @param queryParams
	 * @param page
	 * @return
	 */
	
	public List<Map<String, Object>>  querycountChangeRecordsBy(Map<String, Object> queryParams);


	/**
	 * @Description 查询条件获取冻结明细信息
	 * @param queryParams 查询条件
	 * @return AccountFreezeDetailResponse 冻结明细
	 * @see 需要参考的类或方法
	 */
	public AccountFreezeDetailResponse findAccountFreezeDetailBy(Map<String, Object> queryParams);
	
	/**
	 * 根据账户编号查询冻结历史记录
	 * @param queryParams
	 * @param page
	 * @return
	 */
	public AccountFreezeQueryResponse AccountFreezeList(Map<String, Object> queryParams, Page<?> page);
	
	/**
	 * @Title:AccountQueryInterface
	 * @Description:导出账户历史信息
	 * @param queryParams
	 * @return
	 * @date 2016年10月22日 下午10:00:48
	 */
	public AccountHistoryQueryResponse findAccountHistoryExportBy(Map<String, Object> queryParams);
	
	/**
	 * 查询冻结编号是否存在
	 * @param accountNo
	 * @return
	 */
	public boolean queryFreezeNoExists(String freezeNo);
	
	/**
	 * 根据账户编号查询调账明细
	 * @param accountNo
	 * @return
	 */
	public Map<String, Object> findAllAdjHistory(String accountNo,Page<?> page);

}
