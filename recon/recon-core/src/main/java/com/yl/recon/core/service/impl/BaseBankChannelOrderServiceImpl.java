package com.yl.recon.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.yl.recon.core.entity.order.BaseBankChannelOrder;
import com.yl.recon.core.mybatis.mapper.BaseBankChannelOrderMapper;
import com.yl.recon.core.service.BaseBankChannelOrderService;
/**
 * @author 聚合支付有限公司
 * @since 2018年01月17
 * @version V1.0.0
 */
@Service("baseBankChannelOrderService")
public class BaseBankChannelOrderServiceImpl implements BaseBankChannelOrderService{

    @Resource
    private BaseBankChannelOrderMapper baseBankChannelOrderMapper;

    @Override
    public int insert(BaseBankChannelOrder baseBankChannelOrder){
        return baseBankChannelOrderMapper.insert(baseBankChannelOrder);
    }

    @Override
    public int insertSelective(BaseBankChannelOrder baseBankChannelOrder){
        return baseBankChannelOrderMapper.insertSelective(baseBankChannelOrder);
    }

    @Override
    public int insertList(List<BaseBankChannelOrder> baseBankChannelOrders){
        return baseBankChannelOrderMapper.insertList(baseBankChannelOrders);
    }

    @Override
    public int update(BaseBankChannelOrder baseBankChannelOrder){
        return baseBankChannelOrderMapper.update(baseBankChannelOrder);
    }

	/**
	 * 分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @param page
	 * @return
	 */
	@Override
	public List <com.yl.recon.api.core.bean.order.BaseBankChannelOrder> findAllBaseBankChannelOrder(ReconOrderDataQueryBean reconOrderDataQueryBean, Page page) {
		return baseBankChannelOrderMapper.findAllBankChannelOrder(reconOrderDataQueryBean,page);
	}

	/**
	 * 不分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	@Override
	public List <com.yl.recon.api.core.bean.order.BaseBankChannelOrder> findBaseBankChannelOrder(ReconOrderDataQueryBean reconOrderDataQueryBean) {
		return baseBankChannelOrderMapper.findBankChannelOrder(reconOrderDataQueryBean);
	}
}
