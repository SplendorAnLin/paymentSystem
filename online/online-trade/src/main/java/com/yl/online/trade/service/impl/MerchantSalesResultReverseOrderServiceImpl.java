package com.yl.online.trade.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.online.model.model.MerchantSalesResultReverseOrder;
import com.yl.online.trade.dao.MerchantSalesResultReverseOrderDao;
import com.yl.online.trade.service.MerchantSalesResultReverseOrderService;

/**
 * 商户消费结果补单业务处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Service("merchantSalesResultReverseOrderService")
public class MerchantSalesResultReverseOrderServiceImpl implements MerchantSalesResultReverseOrderService {

	@Resource
	private MerchantSalesResultReverseOrderDao merchantSalesResultReverseOrderDao;
	
	@Override
	public void createMerchantSalesResultReverseOrder(MerchantSalesResultReverseOrder merchantSalesResultReverseOrder) {
		merchantSalesResultReverseOrderDao.createReverseOrder(merchantSalesResultReverseOrder);
	}

	@Override
	public MerchantSalesResultReverseOrder queryBy(String orderCode) {
		return merchantSalesResultReverseOrderDao.queryByOrderCode(orderCode);
	}

	@Override
	public List<MerchantSalesResultReverseOrder> queryBy(int maxCount, int maxNums) {
		return merchantSalesResultReverseOrderDao.queryBy(maxCount, maxNums);
	}
	
	@Override
	public void updateMerchantSalesResultReverseOrderCount(MerchantSalesResultReverseOrder merchantSalesResultReverseOrder) {
		merchantSalesResultReverseOrderDao.updateMerchantSalesResultReverseOrderCount(merchantSalesResultReverseOrder);
	}

	@Override
	public void updateMerchantSalesResultReverseOrder(MerchantSalesResultReverseOrder merchantSalesResultReverseOrder) {
		merchantSalesResultReverseOrderDao.updateMerchantSalesResultReverseOrder(merchantSalesResultReverseOrder);	
	}

}
