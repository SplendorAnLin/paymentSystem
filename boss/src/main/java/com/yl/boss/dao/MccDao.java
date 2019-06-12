package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.MccInfo;

/**
 * Mcc数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月13日
 * @version V1.0.0
 */
public interface MccDao {

	/**
	 * Mcc新增
	 * @param mccInfo
	 */
	void mccAdd(MccInfo mccInfo);
	
	/**
	 * 根据ID查询单条信息
	 * @return
	 */
	MccInfo mccById(Long id);
	
	/**
	 * 根据mcc查询判断当前是否存在
	 * @return
	 */
	boolean mccByMcc(String mcc);
	
	/**
	 * Mcc修改
	 * @param mccInfo
	 */
	void mccUpdate(MccInfo mccInfo);
	/**
	 * 查询待同步信息
	 * @return
	 */
	List<MccInfo> findSyncMcc();
	
	/**
	 * 查询所有MCC
	 * @return
	 */
	List<MccInfo> findAll();
}
