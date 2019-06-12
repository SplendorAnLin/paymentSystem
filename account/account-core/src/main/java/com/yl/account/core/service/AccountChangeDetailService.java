/**
 *
 */
package com.yl.account.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.response.AccountChangeRecordsResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.enums.AccountStatus;
import com.yl.account.enums.AccountType;
import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.UserRole;
import com.yl.account.model.AccountChangeDetail;

/**
 * 账户变更逻辑处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
public interface AccountChangeDetailService {

	/**
	 * @Description 创建账户变更记录信息
	 * @param bussinessCode 业务编码
	 * @param requestTime 请求时间
	 * @param accountNo 账号
	 * @param type 账务类型
	 * @param status 账户状态
	 * @param systemCode 系统编码
	 * @param systemFlowId 系统流水号
	 * @param userNo 用户号
	 * @param role 用户角色
	 * @param balance 账户余额
	 * @param freezeBalance 冻结金额
	 * @param transitBalance 在途金额
	 * @param symbol 资金标识
	 * @param amount 交易金额
	 * @param operator 操作人
	 * @param changeReason 变更原因
	 * @see 需要参考的类或方法
	 */
	void create(String bussinessCode, Date requestTime, String accountNo, AccountType type, AccountStatus status, String systemCode, String systemFlowId,
			String userNo, UserRole role, Double balance, Double freezeBalance, Double transitBalance, FundSymbol symbol, Double amount, String operator,
			String changeReason);

	/**
	 * @Description 根据系统编码、系统流水号查询账户变更明细
	 * @param systemCode 系统编码
	 * @param systemFlowId 系统流水号
	 * @param bussinessCode 业务类型码
	 * @return 账户变更明细
	 * @see 需要参考的类或方法
	 */
	AccountChangeDetail findAccountBySystemId(String systemCode, String systemFlowId, String bussinessCode);

	/**
	 * @Description 冻结、解冻金额明细金额汇总
	 * @param dailyStart 天开始
	 * @param dailyEnd 天结束
	 * @return List<Map<String, Object>> 每日明细
	 * @see 需要参考的类或方法
	 */
	List<Map<String, Object>> findFreezeBalanceBy(Date dailyStart, Date dailyEnd);
	
	/**
	 * @Description 系统流水号获取账户变更信息
	 * @param systemCode 系统编码
	 * @param systemFlowId 系统流水号
	 * @return AccountQueryResponse 账户查询响应信息
	 * @see 需要参考的类或方法
	 */
	AccountQueryResponse findAccountBySystemFlowId(String systemCode, String systemFlowId);

	/**
	 * @Description 分页查询账户变更明细信息
	 * @param queryParams 查询参数
	 * @param page 分页实体
	 * @return AccountChangeRecordsResponse 变更明细响应信息
	 * @see 需要参考的类或方法
	 */
	AccountChangeRecordsResponse findAccountChangeRecordsBy(Map<String, Object> queryParams, Page<?> page);
	
	List<Map<String, Object>> querycountChangeRecordsBy(Map<String, Object> queryParams);
}
