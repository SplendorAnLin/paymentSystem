package com.yl.dpay.core.mybatis.mapper;

import com.yl.dpay.core.dao.PayeeConfigDao;

/**
 * 收款人配置Mapper
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface PayeeConfigMapper extends PayeeConfigDao{
	/**
	 * 删除
	 */
	void delete(int id);
	/**
	 * 批量删除
	 * @param ids
	 */
	public void deleteAll(int[] ids);
}
