package com.yl.recon.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.yl.recon.core.entity.order.RemitOrder;
import com.yl.recon.core.mybatis.mapper.RemitOrderMapper;
import com.yl.recon.core.service.RemitOrderService;

@Service
public class RemitOrderServiceImpl implements RemitOrderService{

    @Resource
    private RemitOrderMapper remitOrderMapper;

    @Override
    public int insert(RemitOrder remitOrder){
        return remitOrderMapper.insert(remitOrder);
    }

    @Override
    public int insertSelective(RemitOrder remitOrder){
        return remitOrderMapper.insertSelective(remitOrder);
    }

    @Override
    public int insertList(List<RemitOrder> remitOrders){
        return remitOrderMapper.insertList(remitOrders);
    }

    @Override
    public int update(RemitOrder remitOrder){
        return remitOrderMapper.update(remitOrder);
    }


	/**
	 * 分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @param page
	 * @return
	 */
	@Override
	public List <com.yl.recon.api.core.bean.order.RemitOrder> findAllRemitOrders(ReconOrderDataQueryBean reconOrderDataQueryBean, Page page) {
		return remitOrderMapper.findAllRemitOrders(reconOrderDataQueryBean,page);
	}

	/**
	 * 不分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	@Override
	public List <com.yl.recon.api.core.bean.order.RemitOrder> findRemitOrders(ReconOrderDataQueryBean reconOrderDataQueryBean) {
		return remitOrderMapper.findRemitOrders(reconOrderDataQueryBean);
	}
}
