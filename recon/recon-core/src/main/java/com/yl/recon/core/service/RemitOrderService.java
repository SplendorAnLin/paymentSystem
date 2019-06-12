package com.yl.recon.core.service;

import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import com.yl.recon.core.entity.order.RemitOrder;
public interface RemitOrderService{

    int insert(RemitOrder remitOrder);

    int insertSelective(RemitOrder remitOrder);

    int insertList(List <RemitOrder> remitOrders);

    int update(RemitOrder remitOrder);

	/**
	 * 分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @param page
	 * @return
	 */
	List <com.yl.recon.api.core.bean.order.RemitOrder> findAllRemitOrders(ReconOrderDataQueryBean reconOrderDataQueryBean, Page page);

	/**
	 * 不分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	List <com.yl.recon.api.core.bean.order.RemitOrder> findRemitOrders(ReconOrderDataQueryBean reconOrderDataQueryBean);

 }
