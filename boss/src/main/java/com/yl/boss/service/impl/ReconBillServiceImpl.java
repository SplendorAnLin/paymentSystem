package com.yl.boss.service.impl;

import java.util.List;

import com.yl.boss.dao.ReconBillDao;
import com.yl.boss.entity.InterfaceReconBillConfig;
import com.yl.boss.service.ReconBillService;

/**
 * 对账单配置信息业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class ReconBillServiceImpl implements ReconBillService {
	private ReconBillDao reconBillDao;
	
	/**
	 * 新增
	 */
	@Override
	public void create(InterfaceReconBillConfig interfaceReconBillConfig) {
		reconBillDao.save(interfaceReconBillConfig);
	}
	
	/**
	 * 根据接口名查询编号
	 */
	@Override
	public InterfaceReconBillConfig findByInterfaceName(String interfaceName) {
		//调用远程接口
		return null;
	}

	public InterfaceReconBillConfig reconBillById(long id) {
		return reconBillDao.reconBillById(id);
	}

	@Override
	public List<InterfaceReconBillConfig> reconBill() {
		// TODO Auto-generated method stub
		return reconBillDao.reconBill();
	}

	public ReconBillDao getReconBillDao() {
		return reconBillDao;
	}

	public void setReconBillDao(ReconBillDao reconBillDao) {
		this.reconBillDao = reconBillDao;
	}
	@Override
	public void reconBillUpdate(InterfaceReconBillConfig interfaceReconBillConfig) {
		InterfaceReconBillConfig interfaceReconBill = reconBillById(interfaceReconBillConfig.getId());
		interfaceReconBill.setReconBillUrl(interfaceReconBillConfig.getReconBillUrl());
		interfaceReconBill.setGenerateTime(interfaceReconBillConfig.getGenerateTime());
		interfaceReconBill.setStatus(interfaceReconBillConfig.getStatus());
		interfaceReconBill.setFilePrefix(interfaceReconBillConfig.getFilePrefix());
		reconBillDao.reconBillUpdate(interfaceReconBill);
	}

}
