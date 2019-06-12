package com.yl.account.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.request.AccountOpen;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.enums.AccountStatus;
import com.yl.account.enums.AccountType;
import com.yl.account.enums.CallType;
import com.yl.account.enums.UserRole;
import com.yl.account.model.Account;

/**
 * 账务管理业务逻辑处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
public interface AccountService {

	/**
	 * @Description 开户
	 * @param accountOpenRequest 开户请求
	 * @param accountNo 账号
	 * @see 需要参考的类或方法
	 */
	void openAccount(AccountOpen accountOpenRequest, String accountNo);

	/**
	 * @Description 更新账户
	 * @param accountModifyRequest 更新账户请求
	 * @see 需要参考的类或方法
	 */
	void modifyAccountInfo(Account account, AccountStatus status);

	/**
	 * @Description 更新账户余额
	 * @param account 账户信息
	 * @param amount 操作金额
	 * @see 需要参考的类或方法
	 */
	void addValueableAndTransitBalance(Account account, double amount);

	/**
	 * @Description 根据账号查询账户信息
	 * @param accountNo 账号
	 * @return
	 * @see 需要参考的类或方法
	 */
	Account findAccountByCode(String accountNo);

	/**
	 * @Description 账户冻结金额增加发生额
	 * @param account 账户实体
	 * @param valueableBalance 可用余额
	 * @see 需要参考的类或方法
	 */
	void addFreezeBalance(Account account, double valueableBalance);
	
	/**
	 * 帐户分类余额查询
	 * @return
	 */
	List<Map<String, Object>> findAccTypeBalance();
	
	/**
	 * @Description 账户可用余额增加或减少发生额
	 * @param account 账户实体
	 * @param amount 操作金额
	 * @param accountCredit 入账信息
	 * @see 需要参考的类或方法
	 */
	void addOrSubtractValueableBalance(Account account, double amount);
	
	/**
	 * @Description 扣减账户在途余额
	 * @param account 账户实体
	 * @see 需要参考的类或方法
	 */
	void subtractTransit(Account account);

	/**
	 * @Description 扣减账户冻结金额
	 * @param freezeAccount 账户实体
	 * @param freezeBalance 扣减冻结发生额
	 * @see 需要参考的类或方法
	 */
	void subtractFreeze(Account freezeAccount, double freezeBalance);

	/**
	 * @Description 账户余额、冻结扣除请款金额
	 * @param account 账号
	 * @param consultAmount 请款金额
	 * @see 需要参考的类或方法
	 */
	void subtractValueableAndFreezeBalance(Account account, double consultAmount);

	/**
	 * @Description 账户扣除可用、在途金额
	 * @param account 账户实体
	 * @param valueableAndTransitBalance 可用、在途金额扣除额
	 * @see 需要参考的类或方法
	 */
	void subtractValueableAndTransitBalance(Account account, Double valueableAndTransitBalance);

	/**
	 * @Description 用户号、用户角色、账户类型获取账户信息
	 * @param userNo 用户号
	 * @param userRole 用户角色
	 * @param accountType 账户类型
	 * @return {@link Account} 账户信息
	 * @see 需要参考的类或方法
	 */
	Account findAccountBy(String userNo, UserRole userRole, AccountType accountType);

	/**
	 * @Description 统计当前账户余额
	 * @return List<Map<String, Double>> 账户总额
	 * @see 需要参考的类或方法
	 */
	List<Map<String, Double>> findAccountBalanceBySum();
	
	/**
	 * @Description 根据条件查询账户余额
	 * @param accountBalanceQuery 余额查询请求
	 * @return AccountBalanceQueryResponse 余额查询响应
	 * @see 需要参考的类或方法
	 */
	AccountBalanceQueryResponse findAccountBalanceBy(CallType callType, Map<String, Object> accountBalanceQuery);

	/**
	 * @Description 账号查询账户信息
	 * @param accountNo 账号
	 * @return accountQueryResponse 账户响应信息
	 * @see 需要参考的类或方法
	 */
	AccountQueryResponse findAccountByAccountNo(CallType callType, String accountNo);

	/**
	 * @Description 查询参数获取账户信息
	 * @param queryParams 查询参数
	 * @return AccountQueryResponse 账户响应信息
	 * @see 需要参考的类或方法
	 */
	AccountQueryResponse findAccountBy(CallType realTime, Map<String, Object> queryParams);

	/**
	 * @Description 分页查询账户信息
	 * @param queryParams 查询参数
	 * @param page 分页实体
	 * @return AccountQueryResponse 账户信息
	 * @see 需要参考的类或方法
	 */
	AccountQueryResponse findAccountByPage(Map<String, Object> queryParams, Page<?> page);
	
	/**
	 * @Description 查询待转可用金额的账户
	 * @param operDate 操作时间
	 * @param nums 条数
	 * @return
	 */
	List<Account> findWaitAbleAccounts(int nums,String operDate);
	
	/**
	 * @Description 在途金额待转可用金额
	 * @param accountNo
	 * @param amount
	 * @param ableDate
	 * @return
	 * @see 需要参考的类或方法
	 */
	void updateAbleBalance(String accountNo,double amount,Date ableDate);

}
