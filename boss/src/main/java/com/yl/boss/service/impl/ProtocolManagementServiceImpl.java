package com.yl.boss.service.impl;

import java.util.Date;
import java.util.Map;

import com.yl.boss.dao.ProtocolManagementDao;
import com.yl.boss.entity.ProtocolManagement;
import com.yl.boss.service.ProtocolManagementService;

/**
 * 协议管理业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class ProtocolManagementServiceImpl implements ProtocolManagementService {

	private ProtocolManagementDao protocolManagementDao;	
	public ProtocolManagementDao getProtocolManagementDao() {
		return protocolManagementDao;
	}

	public void setProtocolManagementDao(ProtocolManagementDao protocolManagementDao) {
		this.protocolManagementDao = protocolManagementDao;
	}
	@Override
	public ProtocolManagement getprotolById(Long id) {
		return protocolManagementDao.getprotolById(id);
	}

	@Override
	public void updateProtol(ProtocolManagement pro) {
		ProtocolManagement proNow=protocolManagementDao.getprotolById(pro.getId());
		proNow.setUpdateTime(new Date());
		proNow.setTitle(pro.getTitle());
		proNow.setContent(pro.getContent());
		proNow.setStatus(pro.getStatus());
		proNow.setSort(pro.getSort());
		proNow.setType(pro.getType());
		proNow.setOperator(pro.getOperator());
		protocolManagementDao.updateProtol(proNow);
	}

	@Override
	public void insert(ProtocolManagement pro) {
		protocolManagementDao.insert(pro);
		
	}

	@Override
	public ProtocolManagement getprotolByTitle(String title) {
		return protocolManagementDao.getprotolByTitle(title);
	}

	@Override
	public Map<String, Object> wapProtocol(String sort, String type) {
		return protocolManagementDao.wapProtocol(sort, type);
	}



	@Override
	public Map<String, Object> selectWechathelp(String sort, int id,String status) {
		
		return protocolManagementDao.selectWechathelp(sort, id,status);
	}

	

	
}