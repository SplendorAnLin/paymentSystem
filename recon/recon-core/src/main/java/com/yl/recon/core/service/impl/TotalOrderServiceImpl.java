package com.yl.recon.core.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.yl.recon.core.entity.order.TotalOrder;
import com.yl.recon.core.mybatis.mapper.TotalOrderMapper;
import com.yl.recon.core.service.TotalOrderService;

@Service
public class TotalOrderServiceImpl implements TotalOrderService{

    @Resource
    private TotalOrderMapper totalOrderMapper;

    @Override
    public int insert(TotalOrder totalOrder){
        return totalOrderMapper.insert(totalOrder);
    }

    @Override
    public int insertSelective(TotalOrder totalOrder){
        return totalOrderMapper.insertSelective(totalOrder);
    }

    @Override
    public int insertList(List<TotalOrder> totalOrders){
        return totalOrderMapper.insertList(totalOrders);
    }

    @Override
    public int update(TotalOrder totalOrder){
        return totalOrderMapper.update(totalOrder);
    }
}
