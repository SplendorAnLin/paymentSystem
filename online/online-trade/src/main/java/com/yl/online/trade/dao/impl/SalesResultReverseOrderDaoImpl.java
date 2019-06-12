package com.yl.online.trade.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.online.model.model.SalesResultReverseOrder;
import com.yl.online.trade.dao.SalesResultReverseOrderDao;
import com.yl.online.trade.dao.mapper.SalesResultReverseOrderMapper;
import com.yl.online.trade.utils.CodeBuilder;

/**
 * 消费结果补单数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Repository("salesResultReverseOrderDao")
public class SalesResultReverseOrderDaoImpl implements SalesResultReverseOrderDao {

	@Resource
	private SalesResultReverseOrderMapper salesResultReverseOrderMapper;
	
	@Override
	public void createSalesResultReverseOrder(SalesResultReverseOrder salesResultReverseOrder) {
		salesResultReverseOrder.setCode(CodeBuilder.build("SRRO", "yyyyMMdd", 6));
		salesResultReverseOrder.setCreateTime(new Date());
		salesResultReverseOrder.setVersion(System.currentTimeMillis());
		salesResultReverseOrderMapper.createSalesResultReverseOrder(salesResultReverseOrder);
	}

	@Override
	public SalesResultReverseOrder querySalesResultReverseOrder(String businessOrderID) {
		return salesResultReverseOrderMapper.querySalesResultReverseOrder(businessOrderID);
	}

	@Override
	public List<SalesResultReverseOrder> querySalesResultReverseOrderBy(int maxCount, int maxNums) {
		return salesResultReverseOrderMapper.querySalesResultReverseOrderBy(maxCount, maxNums);
	}

	@Override
	public void updateSalesResultReverseOrder(SalesResultReverseOrder salesResultReverseOrder) {
		salesResultReverseOrderMapper.updateSalesResultReverseOrder(salesResultReverseOrder);
	}

	@Override
	public void updateSalesResultReverseOrderCount(SalesResultReverseOrder salesResultReverseOrder) {
		salesResultReverseOrderMapper.updateSalesResultReverseOrderCount(salesResultReverseOrder);
	}

	@Override
	public void updateSalesResultReverse(SalesResultReverseOrder salesResultReverseOrder) {
		salesResultReverseOrderMapper.updateSalesResultReverse(salesResultReverseOrder);
	}
}