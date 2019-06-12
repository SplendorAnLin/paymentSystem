/**
 *
 */
package com.yl.account.core.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.enums.CallType;
import com.yl.account.model.AccountDayDetail;
import com.yl.account.model.AccountRecordedDetail;

/**
 * 账务入账明细数据处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月22日
 * @version V1.0.0
 */
public interface AccountRecordedDetailDao {

	/**
	 * @Description 创建入账明细
	 * @param accountRecordedDetail 入账明细实体
	 * @see 需要参考的类或方法
	 */
	void create(AccountRecordedDetail accountRecordedDetail);

	/**
	 * @Description 账号、交易订单号、系统编码查询外账记账凭证
	 * @param accountNo 账号
	 * @param transOrder 交易订单号
	 * @param systemCode 系统编码
	 * @param bussinessCode 业务类型
	 * @param systemFlowId 系统请求流水号
	 * @return int 外账记账凭证
	 * @see 需要参考的类或方法
	 */
	int findBy(String accountNo, String transOrder, String systemCode, String bussinessCode, String systemFlowId);

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
	 * @return {@link AccountRecordedDetail} 账务入账明细
	 * @see 需要参考的类或方法
	 */
	List<AccountRecordedDetail> findAccountHistoryBy(Map<String, Object> accountHistoryQueryParams, Page<?> page);
	
	

	/**
	 * @Description 参数查询汇总信息
	 * @param queryParams 查询参数
	 * @return List<Object> 汇总信息
	 * @see 需要参考的类或方法
	 */
	List<Object> findAccountSummaryBy(Map<String, Object> accountHistoryQueryParams);

	/**
	 * @Description 系统流水号获取账户信息
	 * @param systemCode 系统编码
	 * @param transOrder 交易订单号
	 * @return {@link AccountQueryResponse} 账户查询响应信息
	 * @see 需要参考的类或方法
	 */
	List<AccountRecordedDetail> findAccountByTransOrder(String systemCode, String transOrder);

	/**
	 * @Description 参数查询账户历史
	 * @param realTime 调用方式实时-非实时
	 * @param queryParams 查询参数
	 * @return {@link AccountRecordedDetail}
	 * @see 需要参考的类或方法
	 */
	List<AccountRecordedDetail> _findAccountHistoryBy(CallType realTime, Map<String, Object> queryParams);
	
	List<Map<String, Object>>  _queryAccountHistorBy(Map<String, Object> params);
 
	/**
	 * @Descriptio 账务历史数据查询
	 * @param currentResutl 左边界
	 * @param rowNum 右边界
	 * @return List<Map<String, Object>> 账务明细信息
	 * @see 需要参考的类或方法
	 */
	List<Map<String, Object>> findAccountHistoryBy(int currentResutl, int rowNum);
	
	/**
	 * @Title:AccountRecordedDetailBaseMapper
	 * @Description:查询账户信息导出
	 * @param accountHistoryQueryParams
	 * @return
	 * @date 2016年10月22日 下午9:53:29
	 */
	List<AccountRecordedDetail> findAccountHistoryExportBy(@Param("accountHistoryQueryParams") Map<String, Object> accountHistoryQueryParams);

	/**
	 * 查询账户历史
	 * @return
	 */
	List<AccountRecordedDetail> findAccountHistory(Map<String, Object> accountHistoryQueryParams);
	
	/**
	 * 查询账户历史笔数金额
	 * @return
	 */
	Map<String, Object> findAccountHistorySum(Map<String, Object> accountHistoryQueryParams);
}