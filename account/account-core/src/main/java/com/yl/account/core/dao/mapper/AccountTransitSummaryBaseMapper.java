/**
 * 
 */
package com.yl.account.core.dao.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yl.account.model.AccountTransitSummary;

/**
 * 账务非入账周期汇总数据处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public interface AccountTransitSummaryBaseMapper {

	/**
	 * @Description 账号、待入账周期获取汇总信息
	 * @param accountNo 账号
	 * @param waitAccountDate 待入账周期
	 * @return AccountTransitSummary 汇总数据
	 * @see 需要参考的类或方法
	 */
	AccountTransitSummary findTransitSummaryBy(@Param("accountNo") String accountNo, @Param("waitAccountDate") Date waitAccountDate);

	/**
	 * @Description 持久化非入账周期汇总信息
	 * @param accountTransitSummary 非入账周期汇总信息
	 * @see 需要参考的类或方法
	 */
	void insert(AccountTransitSummary accountTransitSummary);

	/**
	 * @Description 更新汇总数据
	 * @param accountTransitSummary 非入账周期汇总实体
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int addOrSubstractWaitAccountAmount(@Param("accountTransitSummary") AccountTransitSummary accountTransitSummary, @Param("newVersion") long newVersion);

	/**
	 * @Description 更新满足入账日期的待入账信息
	 * @param accountTransitSummary 汇总信息
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int modifySummaryStatus(@Param("accountTransitSummary") AccountTransitSummary accountTransitSummary, @Param("newVersion") long newVersion);

	/**
	 * @Description 账号、顺序序批量查询非入账汇总信息
	 * @param accountNo 账号
	 * @param transitDebitSeq 顺序标识
	 * @return List<AccountTransitSummary> 非入账汇总信息
	 * @see 需要参考的类或方法
	 */
	List<AccountTransitSummary> findBatchTransitSummaryBySeq(@Param("accountNo") String accountNo, @Param("transitDebitSeq") String transitDebitSeq);

	/**
	 * @Description 扣减待入账金额并修改费入账周期汇总状态
	 * @param accountTransitSummary 汇总信息
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int modifySummary(@Param("accountTransitSummary") AccountTransitSummary accountTransitSummary, @Param("newVersion") long newVersion);

	/**
	 * @Description 批量查询满足入账时间的汇总信息
	 * @param rowNum 分页区间rowNum
	 * @param rowId 分页区间rowId
	 * @param currentDate 当期日期
	 * @return List<AccountTransitSummary> 汇总信息
	 * @see 需要参考的类或方法
	 */
	List<AccountTransitSummary> findBatchTransitSummaryBy(@Param("rowNum") int rowNum, @Param("rowId") int rowId, @Param("currentDate") Date currentDate);

	/**
	 * @Description 账号、待入账周期获取汇总信息
	 * @param accountBalanceQuery 查询参数
	 * @return AccountTransitSummary 汇总数据
	 * @see 需要参考的类或方法
	 */
	AccountTransitSummary findTransitSummaryBy(@Param("accountBalanceQuery") Map<String, Object> accountBalanceQuery);

	/**
	 * @Description 账号、待入账周期获取汇总信息
	 * @param accountBalanceQuery 查询参数
	 * @return AccountTransitSummary 汇总数据
	 * @see 需要参考的类或方法
	 */
	AccountTransitSummary _findTransitSummaryBy(@Param("accountBalanceQuery") Map<String, Object> accountBalanceQuery);

}
