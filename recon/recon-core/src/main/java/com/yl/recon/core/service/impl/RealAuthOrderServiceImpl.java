package com.yl.recon.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.yl.recon.core.entity.order.RealAuthOrder;
import com.yl.recon.core.mybatis.mapper.RealAuthOrderMapper;
import com.yl.recon.core.service.RealAuthOrderService;
/**
 * @author 聚合支付有限公司
 * @since 2018年01月22
 * @version V1.0.0
 */
@Service
public class RealAuthOrderServiceImpl implements RealAuthOrderService{

    @Resource
    private RealAuthOrderMapper realAuthOrderMapper;

    @Override
    public int insert(RealAuthOrder realAuthOrder){
        return realAuthOrderMapper.insert(realAuthOrder);
    }

    @Override
    public int insertSelective(RealAuthOrder realAuthOrder){
        return realAuthOrderMapper.insertSelective(realAuthOrder);
    }

    @Override
    public int insertList(List<RealAuthOrder> realAuthOrders){
        return realAuthOrderMapper.insertList(realAuthOrders);
    }

    @Override
    public int update(RealAuthOrder realAuthOrder){
        return realAuthOrderMapper.update(realAuthOrder);
    }

	/**
	 * 分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @param page
	 * @return
	 */
	@Override
	public List <com.yl.recon.api.core.bean.order.RealAuthOrder> findAllRealAuthOrders(ReconOrderDataQueryBean reconOrderDataQueryBean, Page page) {
		return realAuthOrderMapper.findAllRealAuthOrders(reconOrderDataQueryBean,page);
	}

	/**
	 * 不分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	@Override
	public List <com.yl.recon.api.core.bean.order.RealAuthOrder> findRealAuthOrders(ReconOrderDataQueryBean reconOrderDataQueryBean) {
		return realAuthOrderMapper.findRealAuthOrders(reconOrderDataQueryBean);
	}
}
