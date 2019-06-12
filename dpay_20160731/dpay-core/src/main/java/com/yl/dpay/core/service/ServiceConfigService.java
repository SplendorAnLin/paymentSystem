package com.yl.dpay.core.service;

import java.util.List;

import com.yl.dpay.core.entity.ServiceConfig;
import com.yl.dpay.core.enums.FireType;
import com.yl.dpay.hessian.beans.ServiceConfigBean;

/**
 * 代付配置服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface ServiceConfigService {
	
	/**
	 * 业务开通或者更新
	 * @param ownerParam
	 * @param param
	 * @param rate 
	 */
	public void create(ServiceConfig serviceConf);
	/**
	 * 查找业务配置
	 * @param ownerId
	 * @param valid
	 * @return
	 */
	public ServiceConfig find(String ownerId, String valid);
	/**
	 * 根据手机号查代付配置信息
	 * @param phone
	 * @return
	 */
	public ServiceConfigBean findByPhone(String phone);
	/**
	 * 查找业务配置
	 * @param ownerId
	 * @return
	 */
	public ServiceConfig find(String ownerId);
	/**
	 * 重置代付复核密码
	 * @param customerNo
	 * 
	 */
	public void updateDfComplexPWDReset(String customerNo);
	/**
	 * 更新代付配置
	 * @param serviceConf
	 */
	public void update(ServiceConfig serviceConf);
	/**
	 * 更新代付复核密码
	 * @param serviceConf
	 */
	public void updatePassword(ServiceConfig serviceConf);
	/**
	 * 更新代付密钥
	 * @param customerNo
	 * @param privateKey
	 * @param publicKey
	 */
	public void updateKeys(String customerNo,String privateKey,String publicKey);
	
 	/**
 	 * @param serviceConf
 	 */
 	public void updateServiceConfigOnlyForAgentSystem(ServiceConfig serviceConf);
 	
 	/**
 	 * 查询自动发起商户
 	 * @param fireType
 	 * @param valid 是否有效 TRUE,FALSE
 	 */
 	public List<ServiceConfig> findByFireType(FireType fireType, String valid);
	
}
