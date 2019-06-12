package com.yl.recon.core.mybatis.mapper;

import com.yl.recon.api.core.bean.MyInterfaceInfoBean;
import com.yl.recon.core.entity.order.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月06
 * @desc 对账通用Mapper
 **/
@Repository
public interface CommonReconMapper {


	/**
	 * 接口与交易：交易单边
	 */
	List <TradeOrder> findInterfaceAndOrderTradeSingleErr(@Param("params") Map <String, Object> params);

	/**
	 * 接口与代付：代付单边
	 */
	List <RemitOrder> findInterfaceAndOrderRemitSingleErr(@Param("params") Map <String, Object> params);

	/**
	 * 接口与订单：实名认证单边
	 */
	List <RealAuthOrder> findInterfaceAndOrderAuthOrderSingleErr(@Param("params") Map <String, Object> params);

	/**
	 * 接口与交易：接口单边
	 *
	 * @param params
	 * @return
	 */
	List <PayinterfaceOrder> findInterfaceAndOrderInterfaceSingleErr(@Param("params") Map <String, Object> params);

	/**
	 * 接口与交易：金额错误
	 *
	 * @param params
	 * @return
	 */
	List <PayinterfaceOrder> findInterfaceAndOrderAmountErr(@Param("params") Map <String, Object> params);

	/**
	 * 账户与交易：交易单边
	 */
	List <TradeOrder> findAccAndOrderTradeSingleErr(@Param("params") Map <String, Object> params);

	/**
	 * 账户与代付：代付单边
	 */
	List <RemitOrder> findAccAndRemitSingleErr(@Param("params") Map <String, Object> params);

	/**
	 * 账户与实名认证：实名认证单边
	 */
	List <RealAuthOrder> findAccAndOrderRealAuthOrderSingleErr(@Param("params") Map <String, Object> params);

	/**
	 * 账户与订单：账户单边
	 *
	 * @param params
	 * @return
	 */
	List <AccountOrder> findAccAndOrderAccSingleErr(@Param("params") Map <String, Object> params);

	/**
	 * 账户与交易：金额错误
	 *
	 * @param params
	 * @return
	 */
	List <TradeOrder> findAccAndOrderAmountErr(@Param("params") Map <String, Object> params);

	/**
	 * 账户与代付：金额错误
	 *
	 * @param params
	 * @return
	 */
	List <RemitOrder> findAccAndRemitAmountErr(@Param("params") Map <String, Object> params);

	/**
	 * 账户与实名认证：金额错误
	 *
	 * @param params
	 * @return
	 */
	List <RealAuthOrder> findAccAndOrderRealAuthOrderAmountErr(@Param("params") Map <String, Object> params);


	/**
	 * 账户与交易：账户总笔数和总金额
	 *
	 * @param params
	 * @return
	 */
	List <AccTotalOrder> findAccountTotal(@Param("params") Map <String, Object> params);


	/**
	 * 接口与通道：通道单边
	 *
	 * @param params
	 * @return
	 */
	List <BaseBankChannelOrder> findInterfaceAndBankBankSingleErr(@Param("params") Map <String, Object> params);

	/**
	 * 接口与通道：接口单边
	 *
	 * @param params
	 * @return
	 */
	List <PayinterfaceOrder> findInterfaceAndBankInterfaceSingleErr(@Param("params") Map <String, Object> params);


	/**
	 * 接口与通道：金额错误
	 *
	 * @param params
	 * @return
	 */
	List <PayinterfaceOrder> findInterfaceAndBankAmountErr(@Param("params") Map <String, Object> params);

	/**
	 * 查询有效的接口编号和名称
	 *
	 * @return
	 */
	List <MyInterfaceInfoBean> queryInterfaceInfo();

	/**
	 * 判断对应的通道对账文件是否存在
	 *
	 * @param code
	 */
	int checkInterfaceCode(String code);


}
