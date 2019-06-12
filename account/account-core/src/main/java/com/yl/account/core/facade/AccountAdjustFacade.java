package com.yl.account.core.facade;

import java.util.HashMap;
import java.util.Map;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountAdjustResponse;

/**
 * 账户调账
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月14日
 * @version V1.0.0
 */
public interface AccountAdjustFacade {

	/**
	 * 调账
	 * @param accountBussinessInterfaceBean
	 * @return AccountAdjustResponse
	 */
	public AccountAdjustResponse adjust(AccountBussinessInterfaceBean accountBussinessInterfaceBean);

	/**
	 * 更新处理状态和调账状态
	 * @param paramMap
	 */
	void modifyHandleStatusAndAdjustStatus(HashMap<String, Object> paramMap);

	/**
	 * 更新调账明细
	 * @param paramMap
	 */
	public void modifyVoucher(Map<String, Object> paramMap);

}
