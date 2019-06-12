package com.yl.recon.core.mybatis.mapper;

import com.yl.recon.core.entity.other.account.BaseAccount;
import com.yl.recon.core.entity.other.order.BaseOrder;
import com.yl.recon.core.entity.other.ReconCom;
import com.yl.recon.core.entity.other.payinterface.BasePayInterface;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月16
 * @desc
 **/
@Repository
public interface CreateReconFileMapper {

	/**
	 * 查询Remit完成订单
	 *
	 * @return
	 */
	List <BaseOrder> queryCompleteRemitOrder(@Param("params") Map <String, Object> params);

	/**
	 * 查询Remit完成订单总金额、笔数、手续费
	 *
	 * @param params
	 * @return
	 */
	List <ReconCom> queryRemitSumCountAndFee(@Param("params") Map <String, Object> params);

	/**
	 * 查询online完成订单
	 *
	 * @return
	 */
	List <BaseOrder> queryCompleteOnlineOrder(@Param("params") Map <String, Object> params);

	/**
	 * 查询online完成订单总金额、笔数、手续费
	 *
	 * @param params
	 * @return
	 */
	List <ReconCom> queryOnlineSumCountAndFee(@Param("params") Map <String, Object> params);

	/**
	 * 查询RealAuth完成订单
	 *
	 * @return
	 */
	List <BaseOrder> queryCompleteRealAuthOrder(@Param("params") Map <String, Object> params);

	/**
	 * 查询RealAuth完成订单总金额、笔数、手续费
	 *
	 * @param params
	 * @return
	 */
	List <ReconCom> queryRealAuthSumCountAndFee(@Param("params") Map <String, Object> params);

	/**
	 * 查询payinterface完成订单
	 *
	 * @return
	 */
	List <BasePayInterface> queryCompletePayInterface(@Param("params") Map <String, Object> params);

	/**
	 * 查询payinterface完成订单总金额、笔数、手续费
	 *
	 * @param params
	 * @return
	 */
	List <ReconCom> queryPayInterfaceSumCountAndFee(@Param("params") Map <String, Object> params);

	/**
	 * 查询account完成订单
	 *
	 * @return
	 */
	List <BaseAccount> queryCompleteAccountOrder(@Param("params") Map <String, Object> params);

	/**
	 * 查询account完成订单总金额、笔数、手续费
	 *
	 * @param params
	 * @return
	 */
	List <ReconCom> queryAccountSumCountAndFee(@Param("params") Map <String, Object> params);
}
