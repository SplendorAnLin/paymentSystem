package com.yl.online.trade.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.online.model.model.SalesResultReverseOrder;
import com.yl.online.trade.dao.SalesResultReverseOrderDao;
import com.yl.online.trade.service.SalesResultReverseOrderService;

/**
 * 消费结果补单业务处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Service("salesResultReverseOrderService")
public class SalesResultReverseOrderServiceImpl implements SalesResultReverseOrderService {

	@Resource
	private SalesResultReverseOrderDao salesResultReverseOrderDao;
	
	@Override
	public void recordSalesResultReverseOrder(SalesResultReverseOrder salesResultReverseOrder) {
	    if(null == salesResultReverseOrderDao.querySalesResultReverseOrder(salesResultReverseOrder.getBusinessOrderID())){
            salesResultReverseOrderDao.createSalesResultReverseOrder(salesResultReverseOrder);
        }
	}

	@Override
	public SalesResultReverseOrder querySalesResultReverseOrder(String businessOrderID) {
		return salesResultReverseOrderDao.querySalesResultReverseOrder(businessOrderID);
	}

	@Override
	public List<SalesResultReverseOrder> querySalesResultReverseOrderBy(int maxCount, int maxNums) {
		return salesResultReverseOrderDao.querySalesResultReverseOrderBy(maxCount, maxNums);
	}

	@Override
	public void updateSalesResultReverseOrder(SalesResultReverseOrder salesResultReverseOrder) {
		salesResultReverseOrderDao.updateSalesResultReverseOrder(salesResultReverseOrder);
	}

	@Override
	public void updateSalesResultReverseOrderCount(SalesResultReverseOrder salesResultReverseOrder) {
		salesResultReverseOrderDao.updateSalesResultReverseOrderCount(salesResultReverseOrder);
	}

	@Override
	public void updateSalesResultReverse(SalesResultReverseOrder salesResultReverseOrder) {
		salesResultReverseOrderDao.updateSalesResultReverse(salesResultReverseOrder);
	}
}