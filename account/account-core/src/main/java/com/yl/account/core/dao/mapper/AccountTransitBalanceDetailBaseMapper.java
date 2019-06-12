/**
 * 
 */
package com.yl.account.core.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yl.account.model.AccountTransitBalanceDetail;

/**
 * 在途资金明细处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月25日
 * @version V1.0.0
 */
public interface AccountTransitBalanceDetailBaseMapper {

	/**
	 * @Description 持久化在途资金明细
	 * @param accountTransitBalanceDetail 在途资金明细
	 * @see 需要参考的类或方法
	 */
	void insert(AccountTransitBalanceDetail accountTransitBalanceDetail);

	/**
	 * @Description 账号、原交易订单号查询在途资金明细
	 * @param accountNo 账号
	 * @param origTransOrder 原交易订单号
	 * @param origBussinessCode 原业务类型
	 * @param origBussinessCodeFee 原业务类型手续费
	 * @param transitDebitSeq 查询顺序
	 * @return AccountTransitBalanceDetail 在途资金明细
	 * @see 需要参考的类或方法 在途资金明细
	 */
	List<AccountTransitBalanceDetail> findTransitBalanceDetailBy(@Param("accountNo") String accountNo, @Param("origTransOrder") String origTransOrder,
			@Param("origBussinessCode") String origBussinessCode, @Param("origBussinessCodeFee") String origBussinessCodeFee,
			@Param("transitDebitSeq") String transitDebitSeq);

	/**
	 * @Description 更新在途资金明细
	 * @param originalAccountTransitBalanceDetail 原在途资金明细
	 * @param newVersion 新版本号
	 * @return int 更新结果
	 * @see 需要参考的类或方法
	 */
	int modifyWaitAccountDate(@Param("originalAccountTransitBalanceDetail") AccountTransitBalanceDetail originalAccountTransitBalanceDetail,
			@Param("newVersion") long newVersion);
	
	/**
	 * @Description 根据账户、日期查询在途明细
	 * @param accountNo
	 * @param accountDate
	 * @return List<AccountTransitBalanceDetail>
	 */
	public List<AccountTransitBalanceDetail> findTransitBalanceDetailForAbleBy(@Param("accountNo")String accountNo,@Param("accountDate")String accountDate);

}
