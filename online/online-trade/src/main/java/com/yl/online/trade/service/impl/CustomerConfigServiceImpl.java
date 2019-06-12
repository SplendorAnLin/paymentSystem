package com.yl.online.trade.service.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.lefu.commons.utils.Page;
import com.yl.online.model.model.CustomerConfig;
import com.yl.online.trade.dao.CustomerConfigDao;
import com.yl.online.trade.service.CustomerConfigService;

/**
 * 商户交易配置处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Service("customerConfigService")
public class CustomerConfigServiceImpl implements CustomerConfigService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(TradeOrderServiceImpl.class);
	
	@Resource
	private CustomerConfigDao customerConfigDao;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Object findAll(Page page, Map<String, Object> params) {
		String resultStr = "";
		List <CustomerConfig> list = customerConfigDao.findAll(page, params);
		try {
			page.setObject(list);
			return page;
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return resultStr;
	}


	@Override
	public void create(CustomerConfig customerConfig) {
		customerConfigDao.create(customerConfig);
	}

	@Override
	public CustomerConfig findByCustomerNo(String customerNo,String payType) {
		return customerConfigDao.findByCustomerNo(customerNo, payType);
	}

	@Override
	public CustomerConfig findById(String id) {
		return customerConfigDao.findById(id);
	}

	@Override
	public void modifyConfig(CustomerConfig customerConfig) {
		Map<String, Object> params = ObjectToMap(customerConfig);
		customerConfigDao.modifyConfig(params);
	}
	
	private Map<String, Object> ObjectToMap(Object object) {
		Map<String, Object> map = new HashMap<>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(object);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}
		return map;
	}


	@Override
	public boolean queryProductTypeExistsByCustNo(String customerNo, String productType) {
		int result = customerConfigDao.queryProductTypeExistsByCustNo(customerNo, productType);
		if(result == 1){
			return true;
		}
		return false;
	}


	@Override
	public List<CustomerConfig> queryAllByCustomerNo(String customerNo) {
		return customerConfigDao.queryAllByCustomerNo(customerNo);
	}
}