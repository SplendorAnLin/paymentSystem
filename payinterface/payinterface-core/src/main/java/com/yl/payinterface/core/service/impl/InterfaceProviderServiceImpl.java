package com.yl.payinterface.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.service.InterfaceProviderService;
import com.yl.payinterface.core.dao.InterfaceProviderDao;
import com.yl.payinterface.core.dao.InterfaceProviderHistoryDao;
import com.yl.payinterface.core.model.InterfaceProvider;
import com.yl.payinterface.core.model.InterfaceProviderHistory;

/**
 * 接口提供方服务
 * @author xiaojie.zhang
 * @since 2013年8月27日
 */

/**
 * 接口提供方服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
@Service("interfaceProviderService")
public class InterfaceProviderServiceImpl implements InterfaceProviderService {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceProviderServiceImpl.class);
	@Resource
	private InterfaceProviderDao interfaceProviderDao;

	@Resource
	private InterfaceProviderHistoryDao interfaceProviderHistoryDao;

	/*public List<InterfaceProviderHistory> queryHistoryAll(Page page, Map<String, Object> map) {
		List<InterfaceProviderHistory> list = interfaceProviderHistoryDao.findAllByInterfaceProviderCode(page, map);
		List<com.payinterface.core.remote.bean.InterfaceProviderHistory> ListDb = new ArrayList<>();
		try {
			if(list != null){
				for(InterfaceProviderHistory inter : list){
					ListDb.add(JsonUtils.toObject(JsonUtils.toJsonString(inter), new TypeReference<com.payinterface.core.remote.bean.InterfaceProviderHistory>(){}));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		page.setObject(ListDb);
		return page;
	}*/
	
	@Override
	public List<com.yl.payinterface.core.bean.InterfaceProviderHistory> queryHistoryAll(String code) {
		List<InterfaceProviderHistory> lists = interfaceProviderHistoryDao.findByInterfaceProviderCode(code);
		List<com.yl.payinterface.core.bean.InterfaceProviderHistory> ListDb =new ArrayList<>();
		try {
			if(lists != null){
				for(InterfaceProviderHistory inter : lists){
					ListDb.add(JsonUtils.toObject(JsonUtils.toJsonString(inter), new TypeReference<com.yl.payinterface.core.bean.InterfaceProviderHistory>(){}));
				}
				return ListDb;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Page queryAll(Page page , Map<String, Object> map) {
		List<InterfaceProvider> list = interfaceProviderDao.findAll(page, map);
		List<com.yl.payinterface.core.bean.InterfaceProviderQueryBean> ListDb = new ArrayList<>();
		try {
			if(list != null){
				for(InterfaceProvider inter : list){
					ListDb.add(JsonUtils.toObject(JsonUtils.toJsonString(inter), new TypeReference<com.yl.payinterface.core.bean.InterfaceProviderQueryBean>(){}));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		page.setObject(ListDb);
		return page;
	}
	
	@Override
	public void save(InterfaceProvider interfaceProvider, String source, String operator) {
		try {
			Date currentDate = new Date();
			if (interfaceProvider == null) throw new BusinessException("InterfaceProviderServiceImpl save interfaceProvider is null");
			interfaceProvider.setCreateTime(currentDate);
			interfaceProvider.setLastModifyTime(currentDate);
			interfaceProvider.setVersion(System.currentTimeMillis());
			interfaceProviderDao.create(interfaceProvider);

			// 接口提供方新增记录
			InterfaceProviderHistory providerHistory = new InterfaceProviderHistory(interfaceProvider, source, operator);
			providerHistory.setCreateTime(currentDate);
			providerHistory.setVersion(System.currentTimeMillis());
			interfaceProviderHistoryDao.create(providerHistory);
		} catch (Exception e) {
			logger.error("", e);
		}

	}

	@Override
	public void modify(InterfaceProvider interfaceProvider, String source, String operator) {
		try {
			Date currentDate = new Date();
			if (interfaceProvider == null) throw new RuntimeException("InterfaceProviderServiceImpl modify interfaceProvider is null");
			InterfaceProvider interfacePro = interfaceProviderDao.findByCode(interfaceProvider.getCode());
			if (interfacePro == null) {
				throw new RuntimeException("InterfaceProviderServiceImpl modify interfacePro is null");
			}
			interfacePro.setShortName(interfaceProvider.getShortName());
			interfacePro.setFullName(interfaceProvider.getFullName());
			interfacePro.setLastModifyTime(currentDate);
			interfaceProviderDao.update(interfacePro, System.currentTimeMillis());

			// 接口提供方变更记录
			InterfaceProviderHistory providerHistory = new InterfaceProviderHistory(interfacePro, source, operator);
			providerHistory.setCreateTime(currentDate);
			providerHistory.setVersion(System.currentTimeMillis());
			interfaceProviderHistoryDao.create(providerHistory);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public InterfaceProvider queryByCode(String code) {
		if (StringUtils.isBlank(code)) throw new RuntimeException("InterfaceProviderServiceImpl queryByCode code is null");
		return interfaceProviderDao.findByCode(code);
	}

	public List<InterfaceProvider> queryAllInterfaceProvider() {
		return interfaceProviderDao.queryAllInterfaceProvider();
	}

	@Override
	public Page queryHistoryPageAll(Page page, Map<String, Object> params) {
		List<InterfaceProviderHistory> list = interfaceProviderHistoryDao.findAllHistoryPageAll(page, params);
		page.setObject(list);
		return page;
	}
}