package com.yl.online.trade.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.lefu.commons.utils.Page;
import com.yl.online.model.model.CustomerConfig;
import com.yl.online.model.model.CustomerConfigHistory;
import com.yl.online.trade.dao.CustomerConfigHistoryDao;
import com.yl.online.trade.dao.mapper.CustomerConfigHistoryMapper;
import com.yl.online.trade.service.CustomerConfigHistoryService;

/**
 * 商户交易配置历史数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Repository("customerConfigHistoryDao")
public class CustomerConfigHistoryDaoImpl implements CustomerConfigHistoryDao{

	@Resource
	private CustomerConfigHistoryMapper customerConfigHistoryMapper;
	
	@Override
	public List<CustomerConfigHistory> findAll(Page page,@Param("params")Map<String, Object> params) {
		return customerConfigHistoryMapper.findAll(page, params);
	}

	@Override
	public void create(CustomerConfigHistory customerConfigHistory) {
		customerConfigHistoryMapper.create(customerConfigHistory);
	}
}