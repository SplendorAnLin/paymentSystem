package com.yl.dpay.core.service;

import com.yl.dpay.core.entity.Payee;

/**
 * 收款人配置业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface PayeeConfigService {
	/**
	 * 创建收款人
	 * @param payee
	 */
	public void create(Payee payee);
	/**
	 * 更新收款人
	 * @param payee
	 */
	public void update(Payee payee);
	/**
	 * 查询单个
	 * @param id
	 * @return
	 */
	public Payee findById(Long id);
	/**
	 * 删除单个
	 * @param id
	 * @return
	 */
	public void delete(int id);
	/**
	 * 批量删除
	 * @param ids
	 */
	public void deleteAll(int[] ids);
}
