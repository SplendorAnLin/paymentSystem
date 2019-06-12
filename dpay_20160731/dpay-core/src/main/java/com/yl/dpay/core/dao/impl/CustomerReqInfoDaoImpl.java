package com.yl.dpay.core.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.dpay.core.dao.CustomerReqInfoDao;
import com.yl.dpay.core.entity.CustomerReqInfo;
import com.yl.dpay.core.mybatis.mapper.CustomerReqInfoMapper;

/**
 * 商户请求信息数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Repository("customerReqInfoDao")
public class CustomerReqInfoDaoImpl implements CustomerReqInfoDao {

	@Resource
	private CustomerReqInfoMapper customerReqInfoMapper;

	@Override
	public void insert(CustomerReqInfo customerReqInfo) {
		customerReqInfo.setCreateDate(new Date());
		customerReqInfoMapper.insert(customerReqInfo);
	}

	@Override
	public CustomerReqInfo findByCutomerOrderCode(String customerNo, String customerOrderCode, String requestType) {
		return customerReqInfoMapper.findByCutomerOrderCode(customerNo, customerOrderCode, requestType);
	}
}
