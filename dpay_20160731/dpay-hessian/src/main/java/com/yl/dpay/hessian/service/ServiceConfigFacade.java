package com.yl.dpay.hessian.service;

import com.yl.dpay.hessian.beans.PayeeBean;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.beans.ServiceConfigHistoryBean;

/**
 * 代付开通、设置、修改业务远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月17日
 * @version V1.0.0
 */
public interface ServiceConfigFacade {
	/**
	 * 开通
	 * 
	 * @param serviceConfigBean
	 */
	public void openDF(ServiceConfigBean serviceConfigBean,String oper);

	/**
	 * 修改
	 * 
	 * @param serviceConfigBean
	 */
	public void update(ServiceConfigBean serviceConfigBean);

	/**
	 * 查询
	 * 
	 * @param ownerId
	 * @return
	 */
	public ServiceConfigBean query(String ownerId);

	/**
	 * 重置代付复核密码
	 * 
	 * @param customerNo
	 */
	public void dfComplexPWDReset(String customerNo);
	/**
	 * 根据手机号查代付配置信息
	 * @param phone
	 * @return
	 */
	public ServiceConfigBean findByPhone(String phone);

	/**
	 * 修改代付复核密码
	 * 
	 * @param serviceConfigBean
	 */
	public void dfUpdateComplexPwd(ServiceConfigBean serviceConfigBean);
	/**
	 * 根据账户查询用户代付配置
	 * @param id
	 * @return
	 */
	public ServiceConfigBean findServiceConfigById(String ownerId);
	/**
	 * 查询私钥 
	 * 
	 * @param ownerId
	 * @return
	 */
	public String getPrivateKey(String ownerId);
	
	/**
	 * 更新密钥
	 * @param serviceConfigBean
	 */
	public void updateKeys(ServiceConfigBean serviceConfigBean);
	
	/**
	 * 根据id删除收款人信息
	 * @param id
	 * @return
	 */
	public void delete(int id);
	/**
	 * 创建收款人
	 * @param payee
	 */
	public void create(PayeeBean payee);
	/**
	 * 更新收款人
	 * @param payee
	 */
	public void updatePayeeBean(PayeeBean payee);
	/**
	 * 根据ID查询收款人
	 * @param id
	 * @return
	 */
	public PayeeBean findById(Long id);
	
	/**
	 * 根据id批量删除收款人信息
	 * @param id
	 */
	public void deleteAll(int[] ids);
	
	/***
	 * 新增用户配置历史信息
	 * @param serviceConfigHistory
	 */
	public void insert(ServiceConfigHistoryBean serviceConfigHistoryBean);

	/**
	 * agent系统商户更新代付信息
	 * @param serviceConfig
	 * @author qiujian
	 * 2016年11月8日
	 */
	public void updateServiceConfigOnlyForAgentSystem(
			ServiceConfigBean serviceConfigBean);

}
