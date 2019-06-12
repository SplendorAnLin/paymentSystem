package com.yl.payinterface.core.dao;

import org.apache.ibatis.annotations.Param;

import com.yl.payinterface.core.bean.AuthSaleBean;

/**
 * 认证持卡人信息数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
public interface AuthSaleInfoDao {
	
	void save(AuthSaleBean authSaleBean);
	
	void delete(AuthSaleBean authSaleBean);
	
	AuthSaleBean findByInterfaceRequestId(@Param("interfaceRequestId")String interfaceRequestId);

}
