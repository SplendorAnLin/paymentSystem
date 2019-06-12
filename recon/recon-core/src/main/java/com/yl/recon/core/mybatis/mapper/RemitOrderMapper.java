package com.yl.recon.core.mybatis.mapper;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.yl.recon.core.entity.order.RemitOrder;
import org.springframework.stereotype.Repository;
/**
 * @author 聚合支付有限公司
 * @since 2018年01月22
 * @version V1.0.0
 */
@Repository
public interface RemitOrderMapper {
	int insert(@Param("remitOrder") RemitOrder remitOrder);

	int insertSelective(@Param("remitOrder") RemitOrder remitOrder);

	int insertList(@Param("remitOrders") List <RemitOrder> remitOrders);

	int update(@Param("remitOrder") RemitOrder remitOrder);

	/**
	 * 分页
	 * @param reconOrderDataQueryBean
	 * @param page
	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.RemitOrder> findAllRemitOrders(@Param("reconOrderDataQueryBean")ReconOrderDataQueryBean reconOrderDataQueryBean, @Param("page") Page page);

	/**
	 * 不分页
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.RemitOrder> findRemitOrders(@Param("reconOrderDataQueryBean")ReconOrderDataQueryBean reconOrderDataQueryBean);
}
