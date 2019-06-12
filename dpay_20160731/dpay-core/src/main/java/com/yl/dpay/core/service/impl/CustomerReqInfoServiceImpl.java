package com.yl.dpay.core.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.yl.dpay.core.dao.CustomerReqInfoDao;
import com.yl.dpay.core.entity.CustomerReqInfo;
import com.yl.dpay.core.service.CustomerReqInfoService;

/**
 * 商户请求信息服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Service("customerReqInfoService")
public class CustomerReqInfoServiceImpl implements CustomerReqInfoService {

	@Resource
	private CustomerReqInfoDao customerReqInfoDao;

	@Override
	@Transactional
	public CustomerReqInfo findByCutomerOrderCode(String customerNo, String customerOrderCode, String requestType) {
		return customerReqInfoDao.findByCutomerOrderCode(customerNo, customerOrderCode, requestType);
	}

}
