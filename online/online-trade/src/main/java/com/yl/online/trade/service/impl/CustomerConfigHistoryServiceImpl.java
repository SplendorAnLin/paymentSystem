package com.yl.online.trade.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.CustomerConfigHistory;
import com.yl.online.trade.dao.CustomerConfigHistoryDao;
import com.yl.online.trade.service.CustomerConfigHistoryService;

/**
 * 商户交易配置历史服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */

@Service("customerConfigHistoryService")
public class CustomerConfigHistoryServiceImpl implements CustomerConfigHistoryService{
	
	private static final Logger logger = LoggerFactory
			.getLogger(TradeOrderServiceImpl.class);

	@Resource
	private CustomerConfigHistoryDao customerConfigHistoryDao;
	
	@Override
	public Object findAll(Page page, Map<String, Object> params) {
		String resultStr = "";
		List <CustomerConfigHistory> list = customerConfigHistoryDao.findAll(page, params);
		try {
			page.setObject(list);
			return page;
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return resultStr;
	}

	@Override
	public void create(CustomerConfigHistory customerConfigHistory) {
		customerConfigHistoryDao.create(customerConfigHistory);
	}
}