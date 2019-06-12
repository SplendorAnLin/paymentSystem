package com.yl.dpay.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yl.dpay.core.entity.ServiceConfig;
import com.yl.dpay.core.enums.FireType;
import com.yl.dpay.hessian.beans.ServiceConfigBean;

/**
 * 代付配置服务数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface ServiceConfigDao extends BaseDao<ServiceConfig>{
	/**
	 *查找生效业务设置ID
	 * @param ownerId
	 * @param valid
	 * @return
	 */
	public ServiceConfig find(@Param("ownerId") String ownerId, @Param("valid") String valid);
	/**
	 *查找业务设置ID
	 * @param ownerId
	 * @return
	 */
	public ServiceConfig findByOwnerId(@Param("ownerId") String ownerId);
	/**
	 * 更新
	 * @param ServiceConfig
	 */
	public void update(ServiceConfig ServiceConfig);
	/**
	 * 根据手机号查代付配置信息
	 * @param phone
	 * @return
	 */
	public ServiceConfigBean findByPhone(String phone);
	/**
	 * 更新复核密码
	 * @param ServiceConfig
	 */
	public boolean updateComplexPwd(ServiceConfig ServiceConfig);
	
	/**
	 * 修改账户信息
	 * @param serviceConfigBean
	 */
	public void updateServiceConfig(ServiceConfigBean serviceConfigBean);
	
	/**
 	 * 查询自动发起商户
 	 * @param fireType
 	 * @param valid 是否有效 TRUE,FALSE
 	 */
 	public List<ServiceConfig> findByFireType(@Param("fireType")FireType fireType, @Param("valid")String valid);
	
}
