package com.yl.recon.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import com.yl.recon.core.entity.order.PayinterfaceOrder;
import com.yl.recon.core.mybatis.mapper.PayinterfaceOrderMapper;
import com.yl.recon.core.service.PayinterfaceOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 接口订单访问接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月11日
 */
@Service("payinterfaceOrderService")
public class PayinterfaceOrderServiceImpl implements PayinterfaceOrderService {

	@Resource
	private PayinterfaceOrderMapper payinterfaceOrderMapper;

	@Override
	public int insert(PayinterfaceOrder payinterfaceOrder) {
		return payinterfaceOrderMapper.insert(payinterfaceOrder);
	}

	@Override
	public int insertSelective(PayinterfaceOrder payinterfaceOrder) {
		return payinterfaceOrderMapper.insertSelective(payinterfaceOrder);
	}

	@Override
	public int insertList(List <PayinterfaceOrder> payinterfaceOrders) {
		return payinterfaceOrderMapper.insertList(payinterfaceOrders);
	}

	/**
	 * 分页查询
	 * @param reconOrderDataQueryBean
	 * @param page
	 * @return
	 */
	@Override
	public List <com.yl.recon.api.core.bean.order.PayinterfaceOrder> findAllPayInterfaceOrder(ReconOrderDataQueryBean reconOrderDataQueryBean, Page page) {
		return payinterfaceOrderMapper.findAllPayInterfaceOrder(reconOrderDataQueryBean, page);
	}

	/**
	 * 不分页查询
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	@Override
	public List<com.yl.recon.api.core.bean.order.PayinterfaceOrder> findPayInterfaceOrder(ReconOrderDataQueryBean reconOrderDataQueryBean){
		return payinterfaceOrderMapper.findPayInterfaceOrder(reconOrderDataQueryBean);
	}

	@Override
	public int update(PayinterfaceOrder payinterfaceOrder) {
		return payinterfaceOrderMapper.update(payinterfaceOrder);
	}
}