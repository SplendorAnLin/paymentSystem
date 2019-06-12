package com.yl.dpay.front.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.dpay.front.dao.CustomerReqInfoDao;
import com.yl.dpay.front.mapper.CustomerReqInfoMapper;
import com.yl.dpay.front.model.CustomerReqInfo;

/**
 * 商户请求信息数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月24日
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
}
