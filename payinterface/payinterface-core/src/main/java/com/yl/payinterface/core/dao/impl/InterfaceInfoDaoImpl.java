package com.yl.payinterface.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.dao.InterfaceInfoDao;
import com.yl.payinterface.core.dao.mapper.InterfaceInfoMapper;
import com.yl.payinterface.core.enums.InterfaceTypeDubbo;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceProvider;

/**
 * 接口信息数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
@Repository("interfaceInfoDao")
public class InterfaceInfoDaoImpl implements InterfaceInfoDao {
	
	@Resource
	private InterfaceInfoMapper interfaceInfoMapper;

	@Override
	public void create(InterfaceInfo interfaceInfo) {
		interfaceInfoMapper.create(interfaceInfo);
	}

	@Override
	public InterfaceInfo findByName(String name) {
		return interfaceInfoMapper.findByName(name);
	}
	
	@Override
	public InterfaceInfo findByCode(String code) {
		return interfaceInfoMapper.findByCode(code);
	}
	
	@Override
	public InterfaceInfo findByCodeToShow(String code) {
		return interfaceInfoMapper.findByCodeToShow(code);
	}

	@Override
	public void update(InterfaceInfo interfaceInfo, Long newVersion) {
		interfaceInfoMapper.update(interfaceInfo, newVersion);
	}

	@Override
	public List<InterfaceInfo> findInterfaceInfo() {
		return interfaceInfoMapper.findInterfaceInfo();
	}

	@Override
	public List<InterfaceInfo> findAllEnable(String codes) {
		return interfaceInfoMapper.findAllEnable(codes);
	}

	@Override
	public List<InterfaceProvider> findInterfaceProviderBy(String interfaceType, String cardType) {
		return interfaceInfoMapper.findInterfaceProviderBy(interfaceType, cardType);
	}

	@Override
	public List<InterfaceInfo> findInterfaceInfoBySupProviderCode(String interfaceType, String providerCode, String cardType) {
		return interfaceInfoMapper.findInterfaceInfoBySupProviderCode(interfaceType, providerCode, cardType);
	}

	@Override
	public List<InterfaceProvider> findInterfaceProviderBy(Map<String, Object> queryParams) {
		return interfaceInfoMapper.findInterfaceProviderBy(queryParams);
	}

	@Override
	public List<InterfaceInfo> queryAllEnableByInterfaceType(String interfaceTypes) {
		return interfaceInfoMapper.queryAllEnableByInterfaceType(interfaceTypes);
	}

	@Override
	public List<InterfaceInfo> findValidateInterfaceInfo(InterfaceTypeDubbo type) {
		return interfaceInfoMapper.findValidateInterfaceInfo(type);
	}
	
	@Override
	public List<InterfaceInfo> findAll(Page page, Map<String, Object> params) {
		return interfaceInfoMapper.findAll(page, params);
	}

}
