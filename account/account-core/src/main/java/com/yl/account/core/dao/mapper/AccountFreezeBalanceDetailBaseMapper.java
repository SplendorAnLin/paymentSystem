/**
 * 
 */
package com.yl.account.core.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.yl.account.model.AccountFreezeBalanceDetail;

/**
 * 账务冻结资金明细Mapper映射
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public interface AccountFreezeBalanceDetailBaseMapper {

	/**
	 * @Description 持久化冻结资金明细
	 * @param accountFreezeBalanceDetail 冻结资金明细业务实体
	 * @see 需要参考的类或方法
	 */
	void create(AccountFreezeBalanceDetail accountFreezeBalanceDetail);

	/**
	 * @Description 系统编码、系统请求流水、业务类型、请款金额做幂等
	 * @param systemCode 系统编码
	 * @param systemFlowId 系统请求流水
	 * @param bussinessCode 业务类型
	 * @param consultAmt 请款金额
	 * @return int 存在数量
	 * @see 需要参考的类或方法
	 */
	int findBy(@Param("systemCode") String systemCode, @Param("systemFlowId") String systemFlowId, @Param("bussinessCode") String bussinessCode,
			@Param("consultAmt") double consultAmt);

}
