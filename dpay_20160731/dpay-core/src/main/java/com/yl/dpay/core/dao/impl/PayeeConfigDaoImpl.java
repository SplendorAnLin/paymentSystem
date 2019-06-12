package com.yl.dpay.core.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.dpay.core.dao.PayeeConfigDao;
import com.yl.dpay.core.entity.Payee;
import com.yl.dpay.core.mybatis.mapper.PayeeConfigMapper;

/**
 * 收款人配置数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Repository("payeeConfigDao")
public class PayeeConfigDaoImpl implements PayeeConfigDao{

	@Resource
	private PayeeConfigMapper payeeConfigMapper;
	@Override
	public void insert(Payee t) {
		payeeConfigMapper.insert(t);
	}

	@Override
	public Payee findById(Long id) {
		return payeeConfigMapper.findById(id);
	}

	@Override
	public void update(Payee t) {
		payeeConfigMapper.update(t);
	}

	@Override
	public void delete(int id) {
		payeeConfigMapper.delete(id);
	}
	
	public PayeeConfigMapper getPayeeConfigMapper() {
		return payeeConfigMapper;
	}

	public void setPayeeConfigMapper(PayeeConfigMapper payeeConfigMapper) {
		this.payeeConfigMapper = payeeConfigMapper;
	}

	@Override
	public void deleteAll(int[] ids) {
		for (int i = 0; i < ids.length; i++) {
			System.out.println("输出："+ids[i]);
		}
		payeeConfigMapper.deleteAll(ids);
	}

}
