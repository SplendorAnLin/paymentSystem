package com.yl.online.trade.dao.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.enterprise.inject.ResolutionException;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.lefu.commons.utils.Page;
import com.yl.online.model.model.CustomerConfig;
import com.yl.online.trade.dao.CustomerConfigDao;
import com.yl.online.trade.dao.mapper.CustomerConfigMapper;

/**
 * 商户交易配置数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Repository("customerConfigDao")
public class CustomerConfigDaoImpl implements CustomerConfigDao {

	@Resource
	private CustomerConfigMapper customerConfigMapper;
	
	@Override
	public List<CustomerConfig> findAll(Page page, @Param("params")Map<String, Object> params) {
		return customerConfigMapper.findAll(page, params);
	}

	@Override
	public void create(CustomerConfig customerConfig) {
		customerConfigMapper.create(customerConfig);
	}

	@Override
	public CustomerConfig findById(String id) {
		return customerConfigMapper.findById(id);
	}

	@Override
	public void modifyConfig(Map<String, Object> params) {
		customerConfigMapper.modifyConfig(params);
	}


	@Override
	public CustomerConfig findByCustomerNo(String customerNo,String payType) {
		return customerConfigMapper.findByCustomerNo(customerNo,payType);
	}

	@Override
	public int queryProductTypeExistsByCustNo(String customerNo, String productType) {
		return customerConfigMapper.queryProductTypeExistsByCustNo(customerNo, productType);
	}

	@Override
	public List<CustomerConfig> queryAllByCustomerNo(String customerNo) {
		return customerConfigMapper.queryAllByCustomerNo(customerNo);
	}
}