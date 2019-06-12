package com.yl.dpay.core.dao;


import com.yl.dpay.core.entity.Payee;

/**
 * 收款人配置数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface PayeeConfigDao extends BaseDao<Payee>{
	/**
	 * 删除单个
	 * @param id
	 * @return
	 */
	void delete(int id);
	/**
	 * 批量删除
	 * @param ids
	 */
	public void deleteAll(int[] ids);
}
