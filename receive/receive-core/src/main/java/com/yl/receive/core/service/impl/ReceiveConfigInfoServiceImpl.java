package com.yl.receive.core.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.receive.core.dao.ReceiveConfigInfoDao;
import com.yl.receive.core.dao.ReceiveConfigInfoHistoryDao;
import com.yl.receive.core.entity.ReceiveConfigInfo;
import com.yl.receive.core.entity.ReceiveConfigInfoHistory;
import com.yl.receive.core.service.ReceiveConfigInfoService;

/**
 * 代收配置服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年1月4日
 * @version V1.0.0
 */
@Service("receiveConfigInfoService")
public class ReceiveConfigInfoServiceImpl implements ReceiveConfigInfoService {
	
	@Resource
	private ReceiveConfigInfoDao receiveConfigInfoDao;
	@Resource
	private ReceiveConfigInfoHistoryDao receiveConfigInfoHistoryDao;
	
	@Override
	public void updateKeys(String customerNo, String privateKey, String publicKey) {
		ReceiveConfigInfo info = receiveConfigInfoDao.queryBy(customerNo);
		if(info != null){
			info.setPrivateCer(privateKey);
			info.setPublicCer(publicKey);
			receiveConfigInfoDao.update(info);
		}
	}
	
	@Override
	public ReceiveConfigInfo queryBy(String ownerId) {
		return receiveConfigInfoDao.queryBy(ownerId);
	}

	@Override
	public List<ReceiveConfigInfo> findAllRecfByPage(Map<String, Object> queryParams, Page<?> page) {
		return receiveConfigInfoDao.findAllRecfByPage(queryParams, page);
	}

	@Override
	public void insert(ReceiveConfigInfo receiveConfigInfo) {
		receiveConfigInfoDao.insert(receiveConfigInfo);
	}

	@Override
	public void update(ReceiveConfigInfo receiveConfigInfo) {
		receiveConfigInfoDao.update(receiveConfigInfo);
	}

	@Override
	public void insertHistory(ReceiveConfigInfoHistory receiveConfigInfoHistory) {
		receiveConfigInfoHistoryDao.insert(receiveConfigInfoHistory);
	}
	
	
}
