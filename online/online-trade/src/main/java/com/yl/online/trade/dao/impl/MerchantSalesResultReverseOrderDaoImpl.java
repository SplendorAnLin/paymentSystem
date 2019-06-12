package com.yl.online.trade.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.online.model.model.MerchantSalesResultReverseOrder;
import com.yl.online.trade.dao.MerchantSalesResultReverseOrderDao;
import com.yl.online.trade.dao.mapper.MerchantSalesResultReverseOrderMapper;
import com.yl.online.trade.utils.CodeBuilder;

/**
 * 商户消费结果补单数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Repository("merchantSalesResultReverseOrderDao")
public class MerchantSalesResultReverseOrderDaoImpl implements MerchantSalesResultReverseOrderDao {

	@Resource
	private MerchantSalesResultReverseOrderMapper merchantSalesResultReverseOrderMapper;
	@Override
	public void createReverseOrder(MerchantSalesResultReverseOrder merchantSalesResultReverseOrder) {
		merchantSalesResultReverseOrder.setCode(CodeBuilder.build("MSRR", "yyyyMMdd", 6));
		merchantSalesResultReverseOrder.setCreateTime(new Date());
		merchantSalesResultReverseOrder.setVersion(System.currentTimeMillis());
		merchantSalesResultReverseOrderMapper.createReverseOrder(merchantSalesResultReverseOrder);
	}

	@Override
	public MerchantSalesResultReverseOrder queryByOrderCode(String orderCode) {
		return merchantSalesResultReverseOrderMapper.queryByOrderCode(orderCode);
	}

	@Override
	public List<MerchantSalesResultReverseOrder> queryBy(int maxCount, int maxNums) {
		return merchantSalesResultReverseOrderMapper.queryBy(maxCount, maxNums);
	}

	@Override
	public void updateMerchantSalesResultReverseOrderCount(MerchantSalesResultReverseOrder merchantSalesResultReverseOrder) {
		merchantSalesResultReverseOrderMapper.updateMerchantSalesResultReverseOrderCount(merchantSalesResultReverseOrder);
	}

	@Override
	public void updateMerchantSalesResultReverseOrder(MerchantSalesResultReverseOrder merchantSalesResultReverseOrder) {
		merchantSalesResultReverseOrderMapper.updateMerchantSalesResultReverseOrder(merchantSalesResultReverseOrder);
	}

}
