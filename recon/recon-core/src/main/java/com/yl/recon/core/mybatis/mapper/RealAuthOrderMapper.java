package com.yl.recon.core.mybatis.mapper;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.yl.recon.core.entity.order.RealAuthOrder;
import org.springframework.stereotype.Repository;
/**
 * @author 聚合支付有限公司
 * @since 2018年01月22
 * @version V1.0.0
 */
@Repository
public interface RealAuthOrderMapper {
	int insert(@Param("realAuthOrder") RealAuthOrder realAuthOrder);

	int insertSelective(@Param("realAuthOrder") RealAuthOrder realAuthOrder);

	int insertList(@Param("realAuthOrders") List <RealAuthOrder> realAuthOrders);

	int update(@Param("realAuthOrder") RealAuthOrder realAuthOrder);

	/**
	 * 分页
	 * @param reconOrderDataQueryBean
	 * @param page
	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.RealAuthOrder> findAllRealAuthOrders(@Param("reconOrderDataQueryBean")ReconOrderDataQueryBean reconOrderDataQueryBean, @Param("page") Page page);

	/**
	 * 不分页
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.RealAuthOrder> findRealAuthOrders(@Param("reconOrderDataQueryBean")ReconOrderDataQueryBean reconOrderDataQueryBean);
}
