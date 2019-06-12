package com.yl.recon.core.service;

import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import com.yl.recon.core.entity.order.PayinterfaceOrder;

/**
 * 接口订单访问接口
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface PayinterfaceOrderService {

	/**
	 * 修改接口订单记录
	 */
	int update(PayinterfaceOrder payinterfaceOrder);

	int insert(PayinterfaceOrder payinterfaceOrder);

	int insertSelective(PayinterfaceOrder payinterfaceOrder);

	int insertList(List <PayinterfaceOrder> payinterfaceOrders);

	/**
	 * 分页查询
 	 * @param page
	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.PayinterfaceOrder> findAllPayInterfaceOrder(ReconOrderDataQueryBean reconOrderDataQueryBean, Page page);

	/**
	 * 不分页查询
 	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.PayinterfaceOrder> findPayInterfaceOrder(ReconOrderDataQueryBean reconOrderDataQueryBean);
}