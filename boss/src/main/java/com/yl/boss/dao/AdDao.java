package com.yl.boss.dao;

import java.util.List;
import java.util.Map;

import com.yl.boss.entity.Ad;

/**
 * 广告管理业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface AdDao {
	/**
	 * 查询广告总条数
	 */
	int selectCount();
	/**
	 * 根据条件查询
	 * @param m 查询条件
	 * @return 广告List
	 */
	List<Ad> query(String hql);
	/**
	 * 根据adminId查询广告
	 * @param adminId
	 * @return
	 */
	List<Object> queryByadminId(String hql);
	
	/**
	 * 根据广告Bean进行新增操作
	 * @param ad 广告Bean
	 */
	void save(Ad ad);
	
	/**
	 * 根据管理员编号查询当前广告中所属管理员的最大广告序列
	 * @param adminId 管理员编号
	 * @return 广告表中，所属管理员的最大广告序列
	 */
	List<Ad> queryAdCodeByAdminId(String hql);
	
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
	List<Ad> queryById(String hql);
	/**
	 * 删除Aa
	 * @param ad
	 */
	void delete(Ad ad );
}
