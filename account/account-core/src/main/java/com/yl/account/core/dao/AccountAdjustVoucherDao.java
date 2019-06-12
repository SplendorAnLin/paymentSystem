/**
 * 
 */
package com.yl.account.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.account.model.AccountAdjustVoucher;

/**
 * 账户调账数据逻辑处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public interface AccountAdjustVoucherDao {

	/**
	 * @Description 条件查询账户调账明细
	 * @param params 查询条件
	 * @return {@link AccountAdjustVoucher} 调账凭证
	 * @see 需要参考的类或方法
	 */
	List<AccountAdjustVoucher> findBy(@Param("params")Map<String, Object> params);

	/**
	 * @Description 根据以下条件查询调账凭证
	 * @param system 系统编码
	 * @param systemFlowId 系统流水
	 * @param bussinessCode 业务类型
	 * @param accountNo 账户编码
	 * @return {@link AccountAdjustVoucher }
	 * @see 需要参考的类或方法
	 */
	AccountAdjustVoucher findVoucherBy(@Param("system")String system, @Param("systemFlowId")String systemFlowId, 
			@Param("bussinessCode")String bussinessCode, @Param("accountNo")String accountNo);

	/**
	 * @Description 更新调账状态
	 * @param accountAdjustVoucher 调账凭证
	 * @param version 版本号
	 * @see 需要参考的类或方法
	 */
	void modify(@Param("accountAdjustVoucher")AccountAdjustVoucher accountAdjustVoucher, @Param("version")long version);

	/**
	 * @Description 新增调账流水
	 * @param accountAdjustVoucher
	 * @see 需要参考的类或方法
	 */
	void insert(AccountAdjustVoucher accountAdjustVoucher);

	/**
	 * @Description 更新处理状态和调账状态
	 * @param accountAdjustVoucher
	 * @see 需要参考的类或方法
	 */
	void modifyHandleStatusAndAdjustStatus(@Param("accountAdjustVoucher")AccountAdjustVoucher accountAdjustVoucher,  @Param("version")long version);

	/**
	 * @Description 根据code查询调账明细
	 * @param code
	 * @return {@link AccountAdjustVoucher}
	 * @see 需要参考的类或方法
	 */
	public AccountAdjustVoucher findByCode(@Param("code")String code);

	/**
	 * @Description 更新调账明细
	 * @param accountAdjustVoucher
	 * @see 需要参考的类或方法
	 */
	void modifyVoucher(@Param("accountAdjustVoucher")AccountAdjustVoucher accountAdjustVoucher,  @Param("version")long version);
	
	/**
	 * 根据账户编号查询调账明细
	 * @param accountNo
	 * @return
	 */
	List<AccountAdjustVoucher> findAllAdjHistory(@Param("accountNo")String accountNo,@Param("page")Page<?> page);
}
