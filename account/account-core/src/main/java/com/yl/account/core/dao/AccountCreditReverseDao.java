/**
 * 
 */
package com.yl.account.core.dao;

import org.apache.ibatis.annotations.Param;

import com.yl.account.model.AccountCreditReverse;

/**
 * 账务补单数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月22日
 * @version V1.0.0
 */
public interface AccountCreditReverseDao {

	/**
	 * @Description 持久化账务补单信息
	 * @param accountCreditReverse 账务补单信息
	 * @see 需要参考的类或方法
	 */
	void create(AccountCreditReverse accountCreditReverse);

	/**
	 * @Description 系统编码、系统流水、业务编码查询账务补单信息
	 * @param systemCode 系统编码
	 * @param systemFlowId 系统流水
	 * @param bussinessCode 业务编码
	 * @return 账务补单信息
	 * @see 需要参考的类或方法
	 */
	AccountCreditReverse queryBy(@Param("systemCode")String systemCode, @Param("systemFlowId")String systemFlowId, 
			@Param("bussinessCode")String bussinessCode);

}
