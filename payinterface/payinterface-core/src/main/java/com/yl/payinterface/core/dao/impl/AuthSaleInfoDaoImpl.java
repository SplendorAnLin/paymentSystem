package com.yl.payinterface.core.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.payinterface.core.bean.AuthSaleBean;
import com.yl.payinterface.core.dao.AuthSaleInfoDao;
import com.yl.payinterface.core.dao.mapper.AuthSaleInfoMapper;

/**
 * 认证持卡人信息数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
@Repository("authSaleInfoDao")
public class AuthSaleInfoDaoImpl implements AuthSaleInfoDao {

	@Resource
	private AuthSaleInfoMapper authSaleInfoMapper;
	
	@Override
	public void save(AuthSaleBean authSaleBean) {
		authSaleInfoMapper.save(authSaleBean);
	}

	@Override
	public void delete(AuthSaleBean authSaleBean) {
		authSaleInfoMapper.delete(authSaleBean);
	}

	@Override
	public AuthSaleBean findByInterfaceRequestId(String interfaceRequestId) {
		return authSaleInfoMapper.findByInterfaceRequestId(interfaceRequestId);
	}

}
