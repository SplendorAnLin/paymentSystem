package com.yl.payinterface.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.payinterface.core.service.AuthSaleInfoService;
import com.yl.payinterface.core.bean.AuthSaleBean;
import com.yl.payinterface.core.dao.AuthSaleInfoDao;

/**
 * 认证持卡人信息服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
@Service("suthSaleInfoService")
public class AuthSaleInfoServiceImpl implements AuthSaleInfoService{

	@Resource
	private AuthSaleInfoDao authSaleInfoDao;
	
	@Override
	public void save(AuthSaleBean authSaleBean) {
		AuthSaleBean bean = authSaleInfoDao.findByInterfaceRequestId(authSaleBean.getMerOrderId());
		if(bean == null){
			authSaleInfoDao.save(authSaleBean);
		}else{
			authSaleInfoDao.delete(bean);
			authSaleInfoDao.save(authSaleBean);
		}
		
	}

	@Override
	public AuthSaleBean findByInterfaceRequestId(String interfaceRequestId) {
		return authSaleInfoDao.findByInterfaceRequestId(interfaceRequestId);
	}

}
