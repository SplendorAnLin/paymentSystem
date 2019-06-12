package com.yl.receive.core.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.receive.core.dao.ReceiveConfigInfoHistoryDao;
import com.yl.receive.core.entity.ReceiveConfigInfoHistory;
import com.yl.receive.core.mybatis.mapper.ReceiveConfigInfoHistoryMapper;

/**
 * 代收配置历史数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
@Repository("ReceiveConfigInfoHistoryDao")
public class ReceiveConfigInfoHistoryDaoImpl implements ReceiveConfigInfoHistoryDao{
	
	@Resource
	private ReceiveConfigInfoHistoryMapper receiveConfigInfoHistoryMapper;

	@Override
	public void insert(ReceiveConfigInfoHistory receiveConfigInfoHistory) {
		receiveConfigInfoHistoryMapper.insert(receiveConfigInfoHistory);
	}

	@Override
	public ReceiveConfigInfoHistory findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ReceiveConfigInfoHistory t) {
		// TODO Auto-generated method stub
		
	}


}
