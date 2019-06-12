package com.yl.boss.service;

import com.yl.boss.entity.Ad;

/**
 * 广告管理业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface AdService {
	/**
	 * 查询广告条数
	 */
	int selectCount();
	
	/**
	 * 根据广告Bean进行新增操作
	 * @param ad 广告Bean
	 */
	void save(Ad ad);
	
	/**
	 * 根据广告Bean进行修改操作
	 * @param ad 广告Bean
	 */
	void update(Ad ad);
	
	/**
	 * 根据广告编号和管理员编号进行单条查询
	 * @param id 广告编号
	 * @param adminId 管理员编号
	 * @return 广告Bean
	 */
	Ad queryById(int id,String adminId);

}
