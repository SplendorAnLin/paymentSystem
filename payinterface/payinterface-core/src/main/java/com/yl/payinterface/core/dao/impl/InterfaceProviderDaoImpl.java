package com.yl.payinterface.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.dao.InterfaceProviderDao;
import com.yl.payinterface.core.dao.mapper.InterfaceProviderMapper;
import com.yl.payinterface.core.model.InterfaceProvider;

/**
 * 接口提供方数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
@Repository("interfaceProviderDao")
public class InterfaceProviderDaoImpl implements InterfaceProviderDao {

	@Resource
	private InterfaceProviderMapper interfaceProviderMapper;

	@Override
	public void create(InterfaceProvider interfaceProvider) {
		interfaceProviderMapper.create(interfaceProvider);
	}

	@Override
	public void update(InterfaceProvider interfaceProvider, Long newVersion) {
		interfaceProviderMapper.update(interfaceProvider, newVersion);
	}

	@Override
	public InterfaceProvider findByCode(String code) {
		return interfaceProviderMapper.findByCode(code);
	}

	@Override
	public List<InterfaceProvider> queryAllInterfaceProvider() {
		return interfaceProviderMapper.queryAllInterfaceProvider();
	}

	@Override
	public List<InterfaceProvider> findAll(Page page ,Map<String, Object> params) {
		return interfaceProviderMapper.findAll(page, params);
	}
}