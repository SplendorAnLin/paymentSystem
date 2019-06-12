package com.yl.recon.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import com.yl.recon.core.entity.order.AccountOrder;
import com.yl.recon.core.mybatis.mapper.AccountOrderMapper;
import com.yl.recon.core.service.AccountOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 账户订单记录访问接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月11日
 */
@Service("accountOrderService")
public class AccountOrderServiceImpl implements AccountOrderService {


	@Resource
	AccountOrderMapper accountOrderMapper;


	@Override
	public int insert(AccountOrder accountOrder) {
		return accountOrderMapper.insert(accountOrder);
	}

	@Override
	public int insertSelective(AccountOrder accountOrder) {
		return accountOrderMapper.insertSelective(accountOrder);
	}

	@Override
	public int insertList(List <AccountOrder> accountOrders) {
		return accountOrderMapper.insertList(accountOrders);
	}

	@Override
	public int update(AccountOrder accountOrder) {
		return accountOrderMapper.update(accountOrder);
	}

	/**
	 * 分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @param page
	 * @return
	 */
	@Override
	public List <com.yl.recon.api.core.bean.order.AccountOrder> findAllAccountOrder(ReconOrderDataQueryBean reconOrderDataQueryBean, Page page) {
		return accountOrderMapper.findAllAccountOrder(reconOrderDataQueryBean,page);
	}

	/**
	 * 不分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	@Override
	public List <com.yl.recon.api.core.bean.order.AccountOrder> finAccountOrder(ReconOrderDataQueryBean reconOrderDataQueryBean) {
		return accountOrderMapper.finAccountOrder(reconOrderDataQueryBean);
	}
}