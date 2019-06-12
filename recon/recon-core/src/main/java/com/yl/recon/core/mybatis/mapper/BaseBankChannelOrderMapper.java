package com.yl.recon.core.mybatis.mapper;

 import com.lefu.commons.utils.Page;
 import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
 import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.yl.recon.core.entity.order.BaseBankChannelOrder;
 import org.springframework.stereotype.Repository;
/**
 * @author 聚合支付有限公司
 * @since 2018年01月19
 * @version V1.0.0
 */
@Repository
public interface BaseBankChannelOrderMapper {
    int insert(@Param("baseBankChannelOrder") BaseBankChannelOrder baseBankChannelOrder);

    int insertSelective(@Param("baseBankChannelOrder") BaseBankChannelOrder baseBankChannelOrder);

    int insertList(@Param("baseBankChannelOrders") List<BaseBankChannelOrder> baseBankChannelOrders);

    int update(@Param("baseBankChannelOrder") BaseBankChannelOrder baseBankChannelOrder);

	/**
	 * 分页
	 * @param reconOrderDataQueryBean
	 * @param page
	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.BaseBankChannelOrder> findAllBankChannelOrder(@Param("reconOrderDataQueryBean")ReconOrderDataQueryBean reconOrderDataQueryBean,@Param("page") Page page);

	/**
	 * 不分页
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	List<com.yl.recon.api.core.bean.order.BaseBankChannelOrder> findBankChannelOrder(@Param("reconOrderDataQueryBean")ReconOrderDataQueryBean reconOrderDataQueryBean);
}
