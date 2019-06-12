/**
 * 
 */
package com.yl.account.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountFreezeDetailResponse;
import com.yl.account.api.bean.response.AccountFreezeQueryResponse;
import com.yl.account.api.bean.response.AccountHistoryQueryResponse;
import com.yl.account.enums.FreezeStatus;
import com.yl.account.model.AccountFreezeDetail;

/**
 * 账户冻结明细业务逻辑处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月14日
 * @version V1.0.0
 */
public interface AccountFreezeDetailService {

	/**
	 * @Description 创建预冻结明细
	 * @param accountBussinessInterfaceBean 业务处理Bean
	 * @param accountNo 账号
	 * @param preFreezeBalance 预冻结金额
	 * @param freezeLimit 冻结期限
	 * @param valueableBalance 可冻结金额
	 * @param preFreezeComplete 预冻状态
	 * @return AccountFreezeDetail 冻结明细
	 * @see 需要参考的类或方法
	 */
	AccountFreezeDetail createPreFreezeDetail(AccountBussinessInterfaceBean accountBussinessInterfaceBean, String accountNo, double preFreezeBalance,
			Date freezeLimit, double valueableBalance, FreezeStatus preFreezeComplete);

	/**
	 * @Description 账号、冻结状态获取冻结明细信息
	 * @param accountNo 账号
	 * @param preFreezeIng 冻结状态
	 * @return List<AccountFreezeDetail> 冻结明细
	 * @see 需要参考的类或方法
	 */
	List<AccountFreezeDetail> findAccountFreezeDetailBy(String accountNo, FreezeStatus preFreezeIng);

	/**
	 * @Description 冻结金额累加发生额
	 * @param accountFreezeDetail 冻结明细
	 * @param valueableBalance 发生额
	 * @see 需要参考的类或方法
	 */
	void addFreezeBalance(AccountFreezeDetail accountFreezeDetail, double valueableBalance);

	/**
	 * @Description 预冻完成
	 * @param accountFreezeDetail 预冻明细
	 * @param remainPreFreezeBalance 剩余预冻金额
	 * @see 需要参考的类或方法
	 */
	void preFreezeComplete(AccountFreezeDetail accountFreezeDetail, double remainPreFreezeBalance);

	/**
	 * @Description 创建冻结明细
	 * @param accountBussinessInterfaceBean 业务处理Bean
	 * @param accountNo 账号
	 * @param freezeAmount 冻结金额
	 * @param freezeLimit 冻结期限
	 * @see 需要参考的类或方法
	 */
	AccountFreezeDetail createFreezeDetail(AccountBussinessInterfaceBean accountBussinessInterfaceBean, String accountNo, double freezeAmount, Date freezeLimit);

	/**
	 * @Description 冻结编号查询冻结明细
	 * @param freezeNo 冻结编号
	 * @return AccountFreezeDetail 冻结明细
	 * @see 需要参考的类或方法
	 */
	AccountFreezeDetail findAccountFreezeDetailByFreezeNo(String freezeNo);

	/**
	 * @Description 解冻完成
	 * @param accountFreezeDetail 冻结明细
	 * @see 需要参考的类或方法
	 */
	void thawComplete(AccountFreezeDetail accountFreezeDetail);

	/**
	 * @Description 请款金额增加
	 * @param accountFreezeDetail 冻结明细
	 * @see 需要参考的类或方法
	 */
	void addConsultBalance(AccountFreezeDetail accountFreezeDetail);

	/**
	 * @Description 批量查询需解冻数据
	 * @param page 分页实体
	 * @return List<AccountFreezeDetail> 冻结明细集
	 * @see 需要参考的类或方法
	 */
	List<AccountFreezeDetail> findAccountFreezeDetailsBy(Page<List<AccountFreezeDetail>> page);
	
	/**
	 * @Description 查询参数获取冻结明细信息
	 * @param queryParams 查询参数
	 * @return AccountFreezeDetailResponse 冻结明细信息
	 * @see 需要参考的类或方法
	 */
	AccountFreezeDetailResponse findAccountDetailBy(Map<String, Object> queryParams);
	
	/**
	 * 查询冻结、解冻历史
	 * @param accountHistoryQueryParams
	 * @param page
	 * @return
	 */
	AccountFreezeQueryResponse findAccountFzBy(Map<String, Object> accountHistoryQueryParams, Page<?> page);
	
	/**
	 * 查询冻结编号是否存在
	 * @param freezeNo
	 * @return
	 */
	public boolean queryFreezeNoExists(String freezeNo);

}
