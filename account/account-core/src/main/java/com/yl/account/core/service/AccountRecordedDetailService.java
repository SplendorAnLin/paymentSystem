/**
 *
 */
package com.yl.account.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.response.AccountHistoryQueryResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.bean.response.AccountSummaryResponse;
import com.yl.account.enums.CallType;
import com.yl.account.enums.Currency;
import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.UserRole;
import com.yl.account.model.AccountDayDetail;
import com.yl.account.model.AccountRecordedDetail;

/**
 * 账务入账明细业务逻辑处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月14日
 * @version V1.0.0
 */
public interface AccountRecordedDetailService {

	/**
	 * @Description 持久化外账记账凭证
	 * @param accountNo 账号
	 * @param userNo 用户号
	 * @param userRole 角色
	 * @param systemFlowId 系统请求流水号
	 * @param bussinessCode 业务类型
	 * @param currency 币种
	 * @param transOrder 交易订单号
	 * @param transDate 交易日期
	 * @param systemCode 系统编码
	 * @param waitAccountDate 待入账日期
	 * @param amount 入账金额
	 * @param symbol 入账资金标识
	 * @param fee 手续费
	 * @param feeSymbol 手续费资金标识
	 * @param remainBalance 账户余额
	 * @see 需要参考的类或方法
	 */
	void create(String accountNo, String userNo, UserRole userRole, String systemFlowId, String bussinessCode, Currency currency, String transOrder, Date transDate,
			String systemCode, Date waitAccountDate, double amount, FundSymbol symbol, Double fee, FundSymbol feeSymbol, double remainBalance);

	/**
	 * @Description 账号、交易订单号、系统编码查询外账记账凭证
	 * @param accountNo 账号
	 * @param transOrder 交易订单号
	 * @param systemCode 系统编码
	 * @param bussinessCode 业务类型
	 * @param systemFlowId 系统请求流水号
	 * @return boolean 重复标识
	 * @see 需要参考的类或方法
	 */
	boolean findBy(String accountNo, String transOrder, String systemCode, String bussinessCode, String systemFlowId);

	/**
	 * @Description 账户每日明细汇总
	 * @param dailyStart 天开始
	 * @param dailyEnd 天结束
	 * @return {@link AccountDayDetail} 商户余额收支明细
	 * @see 需要参考的类或方法
	 */
	List<AccountDayDetail> findBySum(Date dailyStart, Date dailyEnd);
	
	/**
	 * @Description 根据参数查询账户历史信息
	 * @param accountHistoryQueryParams 查询参数
	 * @param page 分页实体
	 * @return {@link AccountHistoryQueryResponse} 账户历史信息
	 * @see 需要参考的类或方法
	 */
	AccountHistoryQueryResponse findAccountHistoryBy(Map<String, Object> accountHistoryQueryParams, Page<?> page);
	
	List<Map<String, Object>> queryAccountHistory(Map<String, Object> params);

	/**
	 * @Description 查询账户统计信息
	 * @param queryParams 查询参数
	 * @return {@link AccountSummaryResponse} 汇总信息
	 * @see 需要参考的类或方法
	 */
	AccountSummaryResponse findAccountSummaryBy(Map<String, Object> queryParams);

	/**
	 * @Description 交易订单号获取账户信息
	 * @param systemCode 系统编码
	 * @param transOrder 交易订单号
	 * @return {@link AccountQueryResponse} 账户查询响应信息
	 * @see 需要参考的类或方法
	 */
	AccountQueryResponse findAccountByTransOrder(String systemCode, String transOrder);

	/**
	 * @Dscription 参数查询账户历史
	 * @param realTime 调用方式实时-非实时
	 * @param queryParams 查询参数
	 * @return {@link AccountHistoryQueryResponse}
	 * @see 需要参考的类或方法
	 */
	AccountHistoryQueryResponse findAccountHistoryBy(CallType realTime, Map<String, Object> queryParams);

	/**
	 * @Descriptio 账务历史数据查询
	 * @param currentResutl 左边界
	 * @param rowNum 右边界
	 * @return List<Map<String, Object>> 账务明细信息
	 * @see 需要参考的类或方法
	 */
	List<Map<String, Object>> findAccountHistoryBy(int currentResutl, int rowNum);
	
	/**
	 * @Title:AccountRecordedDetailService
	 * @Description:账户查询导出
	 * @param accountHistoryQueryParams
	 * @return
	 * @date 2016年10月22日 下午9:57:58
	 */
	AccountHistoryQueryResponse findAccountHistoryExportBy(Map<String, Object> accountHistoryQueryParams);

	/**
	 * 查询账户历史
	 * @return
	 */
	List<AccountRecordedDetail> findAccountHistory(Map<String, Object> accountHistoryQueryParams);
	
	/**
	 * 查询账户历史笔数金额
	 */
	Map<String, Object> findAccountHistorySum(Map<String, Object> accountHistoryQueryParams);
}