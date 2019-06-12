package com.yl.receive.front.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.receive.front.dao.CustomerReqInfoDao;
import com.yl.receive.front.model.CustomerReqInfo;
import com.yl.receive.front.service.CustomerReqInfoService;

/**
 * 商户请求信息业务处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
@Service
public class CustomerReqInfoServiceImpl implements CustomerReqInfoService {
	
	@Resource
	private CustomerReqInfoDao customerReqInfoDao;

	@Override
	public void create(CustomerReqInfo customerReqInfo) {
		customerReqInfo.setCreateDate(new Date());
		customerReqInfoDao.insert(customerReqInfo);
	}

}
