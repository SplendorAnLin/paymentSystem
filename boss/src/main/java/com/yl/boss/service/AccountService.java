package com.yl.boss.service;

import java.util.Date;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.AccountFreezeBean;
import com.yl.account.api.bean.request.AccountModify;
import com.yl.account.api.bean.response.AccountModifyResponse;
import com.yl.boss.bean.AccountHistoryQueryBean;
import com.yl.boss.bean.AccountQueryBean;

/**
 * 账户信息业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface AccountService {
	
	/**
	 * 查询账户列表
	 * @param page
	 * @param accountBean
	 * @return
	 */
	public Page queryAccount(Page page, AccountQueryBean accountBean);
	
	/**
	 * 查看账户详细
	 * @param accountNo
	 * @return
	 */
	public AccountBean queryAccountDetail(String accountNo);
	
	/**
	 * 查询账户历史
	 * @param accountHistoryQuery
	 * @param page
	 * @return
	 */
	public Page queryAccountHistory(AccountHistoryQueryBean accountHistoryQueryBean,Page page );
	
	/**
	 * 查询账户历史合计
	 * @param accountHistoryQueryBean
	 * @return
	 */
	public Map<String, Object> queryAccountHistorySum(AccountHistoryQueryBean accountHistoryQueryBean);

	/**
	 * 调账
	 * @param accountNo
	 * @param symbol
	 * @param amount
	 * @param adjustReason
	 * @param operator
	 */
	public void adjustmentAccount(String accountNo, String symbol, String amount, String adjustReason, String operator);
	
	/**
	 * 解冻
	 * @param accountNo
	 * @param freezeNo
	 * @param adjustReason
	 * @param operator
	 */
	public void thaw(String accountNo, String freezeNo, String adjustReason,String operator);
	
	/**
	 * 查询资金冻结/解冻历史
	 * @param accountFZTWQueryBean
	 * @param page
	 */
	public Page queryAccountFz(AccountFreezeBean accountFZTWQueryBean,Page page);
	
	/**
	 * 冻结
	 * @param accountNo
	 * @param symbol
	 * @param amount
	 * @param frozenReason
	 * @param operator
	 */
	
	public void accountFrozen(String accountNo, Date freezeLimit, String amount, String frozenReason, String operator);
	
	/**
	 * 修改
	 * @param accountModify
	 * @return
	 */
	public AccountModifyResponse modifyAccount(AccountModify accountModify);
	
	/**
	 * 查询操作历史
	 * @param queryParams
	 * @param page
	 * @return
	 */
	public Page queryAccountChangeRecordsBy(AccountQueryBean accountBean, Page page) ;
	
	/**
	 * 查询冻结编号是否存在
	 * @param freezeNo
	 * @return
	 */
	public boolean queryFreezeNoExists(String freezeNo);
	
	/**
	 * 根据冻结编号查询账户编号
	 */
	public String queryFreezeAccount(String freezeNo);
}