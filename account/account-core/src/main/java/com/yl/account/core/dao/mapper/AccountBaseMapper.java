/**
 *
 */
package com.yl.account.core.dao.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.account.enums.AccountType;
import com.yl.account.enums.UserRole;
import com.yl.account.model.Account;

/**
 * 账户管理处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public interface AccountBaseMapper {

	/**
	 * @Description 持久化账户
	 * @param account 账户实体
	 * @see 需要参考的类或方法
	 */
	void insert(Account account);

	/**
	 * @Description 账号获取账户信息
	 * @param accountNo 账号
	 * @return Account 账户信息
	 * @see 需要参考的类或方法
	 */
	Account findAccountByCode(@Param("accountNo") String accountNo);

	/**
	 * @Description 修改账户信息
	 * @param account 账户实体
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int modifyAccountInfo(@Param("account") Account account, @Param("newVersion") long newVersion);

	/**
	 * @Description 更新账户余额
	 * @param account 账户实体
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int addValueableAndTransitBalance(@Param("account") Account account, @Param("newVersion") long newVersion);

	/**
	 * @Description 账户冻结金额增加
	 * @param account
	 * @param currentTimeMillis
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int addFreezeBalance(@Param("account") Account account, @Param("newVersion") long newVersion);

	/**
	 * @Description 账户可用余额增加
	 * @param account 账户实体
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int addValueableBalance(@Param("account") Account account, @Param("newVersion") long newVersion);

	/**
	 * @Description 扣减账户在途余额
	 * @param account 账户实体
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int subtractTransit(@Param("account") Account account, @Param("newVersion") long newVersion);

	/**
	 * @Description 账户余额、冻结扣除请款金额
	 * @param account 账户信息
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int subtractValueableAndFreezeBalance(@Param("account") Account account, @Param("newVersion") long newVersion);

	/**
	 * @Description 账户扣除可用、在途金额
	 * @param account 账户信息
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int subtractValueableAndTransitBalance(@Param("account") Account account, @Param("newVersion") long newVersion);

	/**
	 * @Description 用户号、用户角色、账户类型获取账户信息
	 * @param userNo 用户号
	 * @param userRole 用户角色
	 * @param accountType 账户类型
	 * @return List<Account> 账户信息
	 * @see 需要参考的类或方法
	 */
	List<Account> findAccountBy(@Param("userNo") String userNo, @Param("userRole") UserRole userRole, @Param("accountType") AccountType accountType);

	/**
	 * @Description 统计当前账户余额
	 * @return List<Map<String, Double>> 账户总额
	 * @see 需要参考的类或方法
	 */
	List<Map<String, Double>> findAccountBalanceBySum();
	
	/**
	 * @Description 分页查询账户信息
	 * @param queryParams 查询参数
	 * @param page 分页实体
	 * @return List<Account> 账户信息
	 * @see 需要参考的类或方法
	 */
	List<Account> findAllAccountByPage(@Param("queryParams") Map<String, Object> queryParams, @Param("page") Page<?> page);

	/**
	 * @Description 账号获取账户信息
	 * @param accountBalanceQuery 账户查询参数
	 * @return {@link Account} 账户信息
	 * @see 需要参考的类或方法
	 */
	List<Account> _findAccountBy(@Param("queryParams") Map<String, Object> queryParams);
	
	/**
	 * @Description 查询待转可用金额的账户
	 * @param nums 条数
	 * @param operDate 操作时间
	 * @return
	 */
	List<Account> findWaitAbleAccounts(@Param("nums")int nums,@Param("operDate")String operDate);
	
	/**
	 * @Description 更新待转可用金额的账户
	 * @param account 账户信息
	 * @param ableDate 转可用日期
	 * @return
	 */
	void updateAbleAmount(@Param("account")Account account, @Param("ableDate")Date ableDate,@Param("newVersion")long newVersion);
	
	/**
	 * 分类查询待转可用金额的账户
	 * @return
	 */
	List<Map<String, Object>> findAccTypeBalance();
	
}
