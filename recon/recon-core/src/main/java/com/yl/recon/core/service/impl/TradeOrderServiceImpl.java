package com.yl.recon.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import com.yl.recon.core.entity.order.TradeOrder;
import com.yl.recon.core.mybatis.mapper.TradeOrderMapper;
import com.yl.recon.core.service.TradeOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 交易订单访问接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月11日
 */
@Service("tradeOrderService")
public class TradeOrderServiceImpl implements TradeOrderService {

	@Resource
	TradeOrderMapper tradeOrderMapper;

	/**
	 * 单个插入
	 *
	 * @param tradeOrder
	 * @return
	 */
	@Override
	public int insert(TradeOrder tradeOrder) {
		return tradeOrderMapper.insert(tradeOrder);
	}

	/**
	 * 插入
	 *
	 * @param tradeOrder
	 * @return
	 */
	@Override
	public int insertSelective(TradeOrder tradeOrder) {
		return tradeOrderMapper.insertSelective(tradeOrder);
	}

	/**
	 * 批量插入
	 *
	 * @param tradeOrders
	 * @return
	 */
	@Override
	public int insertList(List <TradeOrder> tradeOrders) {
		return tradeOrderMapper.insertList(tradeOrders);
	}

	@Override
	public int update(TradeOrder tradeOrder) {
		return tradeOrderMapper.update(tradeOrder);
	}


	/**
	 * 分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @param page
	 * @return
	 */
	@Override
	public List <com.yl.recon.api.core.bean.order.TradeOrder> findAllTradeOrders(ReconOrderDataQueryBean reconOrderDataQueryBean, Page page) {
		return tradeOrderMapper.findAllTradeOrders(reconOrderDataQueryBean,page);
	}

	/**
	 * 不分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	@Override
	public List <com.yl.recon.api.core.bean.order.TradeOrder> findTradeOrders(ReconOrderDataQueryBean reconOrderDataQueryBean) {
		return tradeOrderMapper.findTradeOrders(reconOrderDataQueryBean);
	}
}