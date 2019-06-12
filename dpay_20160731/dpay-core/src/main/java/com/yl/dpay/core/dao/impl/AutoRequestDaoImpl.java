package com.yl.dpay.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.dpay.core.dao.AutoRequestDao;
import com.yl.dpay.core.entity.AutoRequest;
import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.mybatis.mapper.AutoRequestMapper;


/**
 * 自动发起dao实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Repository("autoRequestDao")
public class AutoRequestDaoImpl implements AutoRequestDao {
	
	@Resource
	private AutoRequestMapper autoRequestMapper;

	@Override
	public void insert(AutoRequest t) {
		autoRequestMapper.insert(t);
	}

	@Override
	public AutoRequest findById(Long id) {
		return autoRequestMapper.findById(id);
	}

	@Override
	public void update(AutoRequest t) {
		autoRequestMapper.update(t);
	}

	@Override
	public List<Map<String, String>> findAllWait() {
		return autoRequestMapper.findAllWait();
	}

	@Override
	public List<AutoRequest> findWaitByOwner(String ownerId, OwnerRole ownerRole) {
		return autoRequestMapper.findWaitByOwner(ownerId, ownerRole);
	}

}
