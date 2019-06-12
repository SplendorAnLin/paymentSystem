/**
 * 
 */
package com.yl.account.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yl.account.enums.CallType;
import com.yl.account.enums.TransitDebitSeq;
import com.yl.account.model.AccountTransitBalanceDetail;
import com.yl.account.model.AccountTransitSummary;

/**
 * 账务非入账周期汇总业务处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
public interface AccountTransitSummaryService {

	/**
	 * @Description 账号、账户类型、待入账周期获取非入账周期汇总记录
	 * @param accountNo 账号
	 * @param waitAccountDate 待入账周期
	 * @return AccountTransitSummary 非入账周期汇总记录
	 * @see 需要参考的类或方法
	 */
	AccountTransitSummary findTransitSummaryBy(String accountNo, Date waitAccountDate);

	/**
	 * @Description 创建非入账周期汇总信息
	 * @param accountTransitBalanceDetail 在途资金明细
	 * @see 需要参考的类或方法
	 */
	void create(AccountTransitBalanceDetail accountTransitBalanceDetail);

	/**
	 * @Description 待入账金额增加或减少
	 * @param accountTransitSummary 汇总数据
	 * @see 需要参考的类或方法
	 */
	void addOrSubstractWaitAccountAmount(AccountTransitSummary accountTransitSummary);

	/**
	 * @Description 更新满足入账日期的待入账信息
	 * @param accountTransitSummary 汇总信息
	 * @see 需要参考的类或方法
	 */
	void modifySummaryStatus(AccountTransitSummary accountTransitSummary);

	/**
	 * @Description 账号、顺序序批量查询非入账汇总信息
	 * @param accountNo 账号
	 * @param transitDebitSeq 顺序标识
	 * @return List<AccountTransitSummary> 非入账汇总信息
	 * @see 需要参考的类或方法
	 */
	List<AccountTransitSummary> findBatchTransitSummaryBySeq(String accountNo, TransitDebitSeq transitDebitSeq);

	/**
	 * @Description 扣减待入账金额并修改费入账周期汇总状态
	 * @param accountTransitSummary 非入账周期汇总信息
	 * @see 需要参考的类或方法
	 */
	void modifySummary(AccountTransitSummary accountTransitSummary);

	/**
	 * @Description 批量查询满足入账时间的汇总信息
	 * @param rowNum 分页区间rowNum
	 * @param rowId 分页区间rowId
	 * @return List<AccountTransitSummary> 汇总信息
	 * @see 需要参考的类或方法
	 */
	List<AccountTransitSummary> findBatchTransitSummaryBy(int rowNum, int rowId);
	
	/**
	 * @Description 账号、账户类型、待入账周期获取非入账周期汇总记录
	 * @param callType 调用方式
	 * @param accountBalanceQuery 查询参数
	 * @return AccountTransitSummary 非入账周期汇总记录
	 * @see 需要参考的类或方法
	 */
	AccountTransitSummary findTransitSummaryBy(CallType callType, Map<String, Object> accountBalanceQuery);

}
