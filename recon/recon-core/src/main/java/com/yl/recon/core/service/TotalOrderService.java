package com.yl.recon.core.service;

import java.util.List;
import com.yl.recon.core.entity.order.TotalOrder;
/**
 * @author 聚合支付有限公司
 * @since 2018年01月11
 * @version V1.0.0
 */
public interface TotalOrderService{

    int insert(TotalOrder totalOrder);

    int insertSelective(TotalOrder totalOrder);

    int insertList(List <TotalOrder> totalOrders);

    int update(TotalOrder totalOrder);
}
