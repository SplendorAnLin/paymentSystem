package com.yl.online.gateway.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.online.gateway.dao.MerchantSalesResultNotifyOrderDao;
import com.yl.online.gateway.mapper.MerchantSalesResultNotifyOrderMapper;
import com.yl.online.gateway.utils.CodeBuilder;
import com.yl.online.model.model.MerchantSalesResultNotifyOrder;

/**
 * 商户消费结果通知订单数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
@Repository("merchantSalesResultNotifyOrderDao")
public class MerchantSalesResultNotifyOrderDaoImpl implements MerchantSalesResultNotifyOrderDao {

	@Resource
	private MerchantSalesResultNotifyOrderMapper merchantSalesResultNotifyOrderMapper;

	@Override
	public void recordFaildOrder(MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder) {
		merchantSalesResultNotifyOrder.setCode(CodeBuilder.build("MSRN", "yyyyMMdd", 6));
		merchantSalesResultNotifyOrder.setCreateTime(new Date());
		merchantSalesResultNotifyOrder.setVersion(System.currentTimeMillis());
		merchantSalesResultNotifyOrderMapper.recordFaildOrder(merchantSalesResultNotifyOrder);
	}

	@Override
	public MerchantSalesResultNotifyOrder queryByOrderCode(String code) {
		return merchantSalesResultNotifyOrderMapper.queryByOrderCode(code);
	}

	@Override
	public List<MerchantSalesResultNotifyOrder> queryBy(int maxCount, int maxNums) {
		return merchantSalesResultNotifyOrderMapper.queryBy(maxCount, maxNums);
	}

	@Override
	public void updateMerchantSalesResultNotifyOrder(MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder) {
		merchantSalesResultNotifyOrderMapper.updateMerchantSalesResultNotifyOrder(merchantSalesResultNotifyOrder);
	}

	@Override
	public void updateNotifyCount(MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder) {
		merchantSalesResultNotifyOrderMapper.updateNotifyCount(merchantSalesResultNotifyOrder);
	}

}
