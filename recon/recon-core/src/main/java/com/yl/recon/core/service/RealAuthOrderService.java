package com.yl.recon.core.service;

import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import com.yl.recon.core.entity.order.RealAuthOrder;
/**
 * @author 聚合支付有限公司
 * @since 2018年01月22
 * @version V1.0.0
 */
public interface RealAuthOrderService{

    int insert(RealAuthOrder realAuthOrder);

    int insertSelective(RealAuthOrder realAuthOrder);

    int insertList(List <RealAuthOrder> realAuthOrders);

    int update(RealAuthOrder realAuthOrder);


	/**
	 * 分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @param page
	 * @return
	 */
	List <com.yl.recon.api.core.bean.order.RealAuthOrder> findAllRealAuthOrders(ReconOrderDataQueryBean reconOrderDataQueryBean, Page page);

	/**
	 * 不分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	List <com.yl.recon.api.core.bean.order.RealAuthOrder> findRealAuthOrders(ReconOrderDataQueryBean reconOrderDataQueryBean);
}
