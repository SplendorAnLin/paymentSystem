package com.yl.dpay.front.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yl.dpay.front.dao.CustomerReqInfoDao;
import com.yl.dpay.front.model.CustomerReqInfo;
import com.yl.dpay.front.service.CustomerReqInfoService;

/**
 * 商户请求信息服务接口实现
 * 
 * @author AnLin
 * @since 2016年5月24日
 * @version V1.0.0
 */
@Service("customerReqInfoService")
public class CustomerReqInfoServiceImpl implements CustomerReqInfoService {

	@Resource
	private CustomerReqInfoDao customerReqInfoDao;

	@Override
	@Transactional
	public void create(CustomerReqInfo customerReqInfo) {
		customerReqInfoDao.insert(customerReqInfo);
	}

}
