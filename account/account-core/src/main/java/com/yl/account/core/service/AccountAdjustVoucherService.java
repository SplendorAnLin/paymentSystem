/**
 * 
 */
package com.yl.account.core.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.account.enums.AccountType;
import com.yl.account.enums.AdjustStatus;
import com.yl.account.enums.ExpireOperate;
import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.HandleStatus;
import com.yl.account.enums.NoticeStatus;
import com.yl.account.enums.UserRole;
import com.yl.account.model.AccountAdjustVoucher;

/**
 * 账户调账业务逻辑处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月9日
 * @version V1.0.0
 */
public interface AccountAdjustVoucherService {

	/**
	 * @Description 条件查询账户调账明细
	 * @param params 查询条件
	 * @return {@link AccountAdjustVoucher} 调账凭证
	 * @see 需要参考的类或方法
	 */
	List<AccountAdjustVoucher> findBy(Map<String, Object> params);

	/**
	 * @Description 根据以下条件查询调账凭证
	 * @param system 系统编码
	 * @param systemFlowId 系统流水
	 * @param bussinessCode 业务类型
	 * @param accountNo 账户编码
	 * @return {@link AccountAdjustVoucher }
	 * @see 需要参考的类或方法
	 */
	AccountAdjustVoucher findBy(String system, String systemFlowId, String bussinessCode, String accountNo);

	/**
	 * @Description 更新调账状态
	 * @param accountAdjustVoucher 调账凭证
	 * @see 需要参考的类或方法
	 */
	void modify(AccountAdjustVoucher accountAdjustVoucher);

	/**
	 * @Description 一句话描述方法用法
	 * @param system 系统编码
	 * @param bussinessCode 业务类型
	 * @param accountNo 账户编码
	 * @param accountType 账户类型
	 * @param userNo 用户编号
	 * @param userRole 用户角色
	 * @param status 调账状态
	 * @param fundSymbol 调账方向
	 * @param amount 调账金额
	 * @param expireTime 过期日期
	 * @param expireOperate 过期操作方式
	 * @param flowId 系统流水
	 * @param freezeNo 冻结编码
	 * @param operator 操作人
	 * @param reason 操作原因
	 * @param remark 备注
	 * @param handleStatus 处理状态
	 * @param processInstanceId 流程实例id
	 * @param transOrder 交易订单号
	 * @param noticeStatus 通知状态
	 * @see 需要参考的类或方法
	 */
	String create(String system, String bussinessCode, String accountNo, AccountType accountType, String userNo, UserRole userRole, AdjustStatus status,
			FundSymbol fundSymbol, double amount, Integer expireTime, ExpireOperate expireOperate, String flowId, String freezeNo, String operator, String reason,
			String remark, HandleStatus handleStatus, String processInstanceId, String transOrder, NoticeStatus noticeStatus);

	/**
	 * @Description 更新处理状态和调账状态
	 * @param accountAdjustVoucher
	 * @see 需要参考的类或方法
	 */
	void modifyHandleStatusAndAdjustStatus(AccountAdjustVoucher accountAdjustVoucher);

	/**
	 * @Description 根据code查询调账明细
	 * @param code
	 * @return {@link AccountAdjustVoucher}
	 * @see 需要参考的类或方法
	 */
	public AccountAdjustVoucher findByCode(String code);

	/**
	 * @Description 更新调账明细
	 * @param accountAdjustVoucher
	 * @see 需要参考的类或方法
	 */
	void modifyVoucher(AccountAdjustVoucher accountAdjustVoucher);
	
	/**
	 * 根据账户编号查询调账明细
	 * @param accountNo
	 * @return
	 */
	List<AccountAdjustVoucher> findAllAdjHistory(String accountNo,Page<?> page);

}
