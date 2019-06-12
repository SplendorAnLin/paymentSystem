package com.yl.account.core.dao;

import java.util.List;

import com.yl.account.model.AccountDayDetail;

/**
 * 商户余额收支明细表DAO接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public interface AccountDayDetailDao {

	/**
	 * @Description 批量新增收支明细
	 * @param accountDayDetails 收支明细信息
	 * @see 需要参考的类或方法
	 */
	public void insert(List<AccountDayDetail> listAccountDayDetail);

}
