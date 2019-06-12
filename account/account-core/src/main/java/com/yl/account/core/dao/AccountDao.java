/**
 *
 */
package com.yl.account.core.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.account.enums.AccountType;
import com.yl.account.enums.CallType;
import com.yl.account.enums.UserRole;
import com.yl.account.model.Account;

/**
 * 账务管理业务逻辑处理口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public interface AccountDao {

	/**
	 * @Description 创建账户
	 * @param account 账户信息
	 * @see 需要参考的类或方法
	 */
	void create(Account account);

	/**
	 * @Description 根据账号查询账户信息
	 * @param accountNo 账号
	 * @return Account 账户信息
	 * @see 需要参考的类或方法
	 */
	Account findAccountByCode(String accountNo);

	/**
	 * @Description 更新账户信息
	 * @param account 账户信息
	 * @see 需要参考的类或方法
	 */
	void modifyAccountInfo(Account account);

	/**
	 * @Description 更新账户余额
	 * @param account 账户信息
	 * @see 需要参考的类或方法
	 */
	void addValueableAndTransitBalance(Account account);

	/**
	 * @Description 增加或扣减冻结金额
	 * @param account 账户实体
	 * @see 需要参考的类或方法
	 */
	void addOrSubtractFreezeBalance(Account account);
	
	/**
	 * @Description 增加可用余额
	 * @param account 账户实体
	 * @see 需要参考的类或方法
	 */
	void addValueableBalance(Account account);

	/**
	 * @Description 扣减账户在途余额
	 * @param account 账户实体
	 * @see 需要参考的类或方法
	 */
	void subtractTransit(Account account);

	/**
	 * @Description 账户余额、冻结扣除请款金额
	 * @param account 账户信息
	 * @see 需要参考的类或方法
	 */
	void subtractValueableAndFreezeBalance(Account account);

	/**
	 * @Description 账户扣除可用、在途金额
	 * @param account 账户信息
	 * @see 需要参考的类或方法
	 */
	void subtractValueableAndTransitBalance(Account account);

	/**
	 * @Description 用户号、用户角色、账户类型获取账户信息
	 * @param userNo 用户号
	 * @param userRole 用户角色
	 * @param accountType 账户类型
	 * @return Account 账户信息
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
	 * @Description 查询账户信息-从节点
	 * @param callType 调用方式
	 * @param queryParams 账户余额查询
	 * @return Account 账户信息
	 * @see 需要参考的类或方法
	 */
	List<Account> findAccountBy(CallType callType, Map<String, Object> queryParams);

	/**
	 * @Description 分页查询账户信息
	 * @param queryParams 查询参数
	 * @param page 分页实体
	 * @return AccountQueryResponse 账户信息
	 * @see 需要参考的类或方法
	 */
	List<Account> findAccountByPage(Map<String, Object> queryParams, Page<?> page);
	
	/**
	 * 分类查询总余额
	 * @return
	 */
	List<Map<String, Object>> findAccTypeBalance();
	
	/**
	 * @Description 查询待转可用金额的账户
	 * @param nums 查询条数
	 * @param operDate 操作时间
	 * @return List<Account> 账户信息集合
	 * @see 需要参考的类或方法
	 */
	List<Account> findWaitAbleAccounts(int nums,String operDate);
	
	/**
	 * @Description 更新待转可用金额的账户
	 * @param account 账户信息
	 * @param ableDate 转可用日期
	 * @return
	 */
	void updateAbleAmount(Account account, Date ableDate);
	
}
