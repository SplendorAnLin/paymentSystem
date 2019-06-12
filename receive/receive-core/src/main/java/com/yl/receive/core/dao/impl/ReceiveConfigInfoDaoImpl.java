package com.yl.receive.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.receive.core.dao.ReceiveConfigInfoDao;
import com.yl.receive.core.entity.ReceiveConfigInfo;
import com.yl.receive.core.mybatis.mapper.ReceiveConfigInfoMapper;

/**
 * 代收配置数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
@Repository("receiveConfigInfoDao")
public class ReceiveConfigInfoDaoImpl implements ReceiveConfigInfoDao{
	
	@Resource
	private ReceiveConfigInfoMapper receiveConfigInfoMapper;
	
	@Override
	public ReceiveConfigInfo queryBy(String ownerId){
		return receiveConfigInfoMapper.queryBy(ownerId);
	}

	public List<ReceiveConfigInfo> findAllRecfByPage(Map<String, Object> queryParams, Page<?> page) {
		return receiveConfigInfoMapper.findAllRecfByPage(queryParams, page);
	}

	@Override
	public void insert(ReceiveConfigInfo receiveConfigInfo) {
		receiveConfigInfoMapper.insert(receiveConfigInfo);
	}

	@Override
	public ReceiveConfigInfo findById(Long id) {
		return receiveConfigInfoMapper.findById(id);
	}

	@Override
	public void update(ReceiveConfigInfo receiveConfigInfo) {
		receiveConfigInfoMapper.update(receiveConfigInfo);
	}
}
