package com.yl.payinterface.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.dao.InterfaceProviderHistoryDao;
import com.yl.payinterface.core.dao.mapper.InterfaceProviderHistoryMapper;
import com.yl.payinterface.core.model.InterfaceProviderHistory;
import com.yl.payinterface.core.utils.CodeBuilder;

/**
 * 接口提供方历史数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
@Repository("interfaceProviderHistoryDao")
public class InterfaceProviderHistoryDaoImpl implements InterfaceProviderHistoryDao {

	@Resource
	private InterfaceProviderHistoryMapper interfaceProviderHistoryMapper;

	@Override
	public void create(InterfaceProviderHistory providerHistory) {
		providerHistory.setCode(CodeBuilder.build("PIPH", "yyyyMMdd", 6));
		interfaceProviderHistoryMapper.create(providerHistory);
	}

	@Override
	public List<InterfaceProviderHistory> findByInterfaceProviderCode(
			String interfaceProviderCode) {
		return interfaceProviderHistoryMapper.findByInterfaceProviderCode(interfaceProviderCode);
	}

	@Override
	public List<InterfaceProviderHistory> findAllHistoryPageAll(Page page, Map<String, Object> params) {
		return interfaceProviderHistoryMapper.findAllHistoryPageAll(page, params);
	}
}