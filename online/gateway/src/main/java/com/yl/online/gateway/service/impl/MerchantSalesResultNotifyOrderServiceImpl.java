package com.yl.online.gateway.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.online.gateway.dao.MerchantSalesResultNotifyOrderDao;
import com.yl.online.gateway.service.MerchantSalesResultNotifyOrderService;
import com.yl.online.model.model.MerchantSalesResultNotifyOrder;

/**
 * 商户消费结果通知订单业务处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
@Service("merchantSalesResultNotifyOrderService")
public class MerchantSalesResultNotifyOrderServiceImpl implements MerchantSalesResultNotifyOrderService {

	@Resource
	private MerchantSalesResultNotifyOrderDao merchantSalesResultNotifyOrderDao;
	
	@Override
	public void recordFaildOrder(MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder) {
		merchantSalesResultNotifyOrderDao.recordFaildOrder(merchantSalesResultNotifyOrder);
	}

	@Override
	public MerchantSalesResultNotifyOrder queryByOrderCode(String code) {
		return merchantSalesResultNotifyOrderDao.queryByOrderCode(code);
	}

	@Override
	public List<MerchantSalesResultNotifyOrder> queryBy(int maxCount, int maxNums) {
		return merchantSalesResultNotifyOrderDao.queryBy(maxCount, maxNums);
	}

	@Override
	public void updateMerchantSalesResultNotifyOrder(MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder) {
		merchantSalesResultNotifyOrderDao.updateMerchantSalesResultNotifyOrder(merchantSalesResultNotifyOrder);
	}

	@Override
	public void updateNotifyCount(MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder) {
		merchantSalesResultNotifyOrderDao.updateNotifyCount(merchantSalesResultNotifyOrder);
	}

}
