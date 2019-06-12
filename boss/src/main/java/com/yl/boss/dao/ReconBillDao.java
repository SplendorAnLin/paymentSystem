package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.InterfaceReconBillConfig;

/**
 * 对账单配置信息数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface ReconBillDao {

	/**
	 * 新增
	 * @param interfaceReconBillConfig
	 */
	void save(InterfaceReconBillConfig interfaceReconBillConfig);
	
	/**
	 * 根据ID查询
	 * @return
	 */
	InterfaceReconBillConfig reconBillById(long id);
	
	/**
	 * 修改对账单配置信息
	 */
	void reconBillUpdate(InterfaceReconBillConfig interfaceReconBillConfig);
	/**
	 * 接口名称.对账单路径.生成时间
	 * @return
	 */
	List<InterfaceReconBillConfig> reconBill();
}
