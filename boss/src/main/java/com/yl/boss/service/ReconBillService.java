package com.yl.boss.service;

import java.util.List;

import com.yl.boss.entity.InterfaceReconBillConfig;

/**
 * 对账单配置信息业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface ReconBillService {
	
	/**
	 * 新增
	 * @param interfaceReconBillConfig
	 */
	void create(InterfaceReconBillConfig interfaceReconBillConfig);
	
	/**
	 * 接口名字查接口编号
	 * @param interfaceName
	 * @return
	 */
	InterfaceReconBillConfig findByInterfaceName(String interfaceName);
	
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
