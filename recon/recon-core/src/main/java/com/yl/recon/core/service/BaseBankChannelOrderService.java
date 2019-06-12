package com.yl.recon.core.service;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import com.yl.recon.core.entity.order.BaseBankChannelOrder;

import java.util.List;
/**
 * @author 聚合支付有限公司
 * @since 2018年01月17
 * @version V1.0.0
 */
public interface BaseBankChannelOrderService {

    int insert(BaseBankChannelOrder baseBankChannelOrder);

    int insertSelective(BaseBankChannelOrder baseBankChannelOrder);

    int insertList(List <BaseBankChannelOrder> baseBankChannelOrders);

    int update(BaseBankChannelOrder baseBankChannelOrder);


	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.BaseBankChannelOrder> findAllBaseBankChannelOrder(ReconOrderDataQueryBean reconOrderDataQueryBean, Page page);

	/**
	 * 不分页查询
	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.BaseBankChannelOrder> findBaseBankChannelOrder(ReconOrderDataQueryBean reconOrderDataQueryBean);
}
