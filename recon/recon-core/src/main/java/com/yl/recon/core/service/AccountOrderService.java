package com.yl.recon.core.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import com.yl.recon.core.entity.order.AccountOrder;

/**
 * 账户订单记录访问接口
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月11日
 */
public interface AccountOrderService {


	/**
	 * @param accountOrder
	 * @return
	 */
	int insert(AccountOrder accountOrder);

	/**
	 * @param accountOrder
	 * @return
	 */
	int insertSelective(AccountOrder accountOrder);

	/**
	 * @param accountOrders
	 * @return
	 */
	int insertList(List <AccountOrder> accountOrders);

	/**
	 * 修改账户订单记录
	 */
	int update(AccountOrder accountOrder);

	/**
	 * 分页查询
 	 * @param page
	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.AccountOrder> findAllAccountOrder(ReconOrderDataQueryBean reconOrderDataQueryBean, Page page);

	/**
	 * 不分页查询
 	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.AccountOrder> finAccountOrder(ReconOrderDataQueryBean reconOrderDataQueryBean);
}