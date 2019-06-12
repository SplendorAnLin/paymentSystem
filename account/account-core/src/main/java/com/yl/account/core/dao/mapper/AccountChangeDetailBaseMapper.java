/**
 *
 */
package com.yl.account.core.dao.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.account.model.AccountChangeDetail;

/**
 * 账户变更记录映射
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public interface AccountChangeDetailBaseMapper {

	/**
	 * @Description 持久化账户变更记录
	 * @param accountChangeDetail 账户变更记录实体
	 * @see 需要参考的类或方法
	 */
	void insert(AccountChangeDetail accountChangeDetail);

	/**
	 * @Description 根据系统编码、系统流水号查询账户变更明细
	 * @param systemCode 系统编码
	 * @param systemFlowId 系统流水号
	 * @param bussinessCode 业务类型码
	 * @return 账户变更明细
	 * @see 需要参考的类或方法
	 */
	AccountChangeDetail findAccountBySystemId(@Param("systemCode") String systemCode, @Param("systemFlowId") String systemFlowId,
			@Param("bussinessCode") String bussinessCode);

	/**
	 * @Description 冻结、解冻金额明细金额汇总
	 * @param dailyStart 天开始
	 * @param dailyEnd 天结束
	 * @return List<Map<String, Object>> 每日明细
	 * @see 需要参考的类或方法
	 */
	List<Map<String, Object>> findFreezeBalanceBy(@Param("dailyStart") Date dailyStart, @Param("dailyEnd") Date dailyEnd);

	/**
	 * @Description 系统流水号获取账户信息
	 * @param systemCode 系统编码
	 * @param systemFlowId 系统流水号
	 * @return AccountQueryResponse 账户查询响应信息
	 * @see 需要参考的类或方法
	 */
	AccountChangeDetail findAccountBySystemFlowId(@Param("systemCode") String systemCode, @Param("systemFlowId") String systemFlowId);

	/**
	 * @Description 分页查询账户变更明细信息
	 * @param queryParams 查询参数
	 * @param page 分页实体
	 * @return List<AccountChangeDetail> 变更明细
	 * @see 需要参考的类或方法
	 */
	List<AccountChangeDetail> findAllAccountChangeRecordsBy(@Param("queryParams") Map<String, Object> queryParams, @Param("page") Page<?> page);
    /**
     * 账务明细
     */
	List<Map<String, Object>>  queryAllAccountChangeRecordsBy(@Param("queryParams") Map<String, Object> queryParams);
}
