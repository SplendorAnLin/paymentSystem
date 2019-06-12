package com.yl.recon.core.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import com.yl.recon.core.entity.order.TradeOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
@Repository
public interface TradeOrderMapper {

	/**
	 * 插入交易订单到数据库
	 */
	int insert(@Param("tradeOrder") TradeOrder tradeOrder);


	/**
	 * 选择性插入
	 *
	 * @param tradeOrder
	 * @return
	 */
	int insertSelective(@Param("tradeOrder") TradeOrder tradeOrder);

	/**
	 * 批量插入
	 *
	 * @param tradeOrders
	 * @return
	 */
	int insertList(@Param("tradeOrders") List <TradeOrder> tradeOrders);

	/**
	 * 更新
	 *
	 * @param tradeOrder
	 * @return
	 */
	int update(@Param("tradeOrder") TradeOrder tradeOrder);

	/**
	 * 分页
	 * @param reconOrderDataQueryBean
	 * @param page
	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.TradeOrder> findAllTradeOrders(@Param("reconOrderDataQueryBean")ReconOrderDataQueryBean reconOrderDataQueryBean,@Param("page") Page page);

	/**
	 * 不分页
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.TradeOrder> findTradeOrders(@Param("reconOrderDataQueryBean")ReconOrderDataQueryBean reconOrderDataQueryBean);
}

