package com.yl.payinterface.core.remote.hessian.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.service.InterfaceProviderService;
import com.yl.payinterface.core.bean.InterfaceProviderHistory;
import com.yl.payinterface.core.bean.InterfaceProviderQueryBean;
import com.yl.payinterface.core.hessian.InterfaceProviderHessianService;
import com.yl.payinterface.core.model.InterfaceProvider;

/**
 * 接口提供方Bean远程接口服务实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
@Service("interfaceProviderHessianService")
public class InterfaceProviderHessianServiceImpl implements InterfaceProviderHessianService {

	@Resource
	private InterfaceProviderService interfaceProviderService;
	
	@Override
	public List<InterfaceProviderHistory> queryHistoryAll(String code) {
		return interfaceProviderService.queryHistoryAll(code);
	}
	
	@Override
	public Page queryAll(Page page ,Map<String, Object> map) {
		page = interfaceProviderService.queryAll(page , map);
		return page;
	}

	@Override
	public List<InterfaceProviderQueryBean> findAllShortName() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("null")
	@Override
	public InterfaceProviderQueryBean queryByCode(String code) {
		InterfaceProvider query = interfaceProviderService.queryByCode(code);
		InterfaceProviderQueryBean queryDb = new InterfaceProviderQueryBean();
		queryDb.setCode(query.getCode());
		queryDb.setCreateTime(query.getCreateTime());
		queryDb.setFullName(query.getFullName());
		queryDb.setId(query.getId());
		queryDb.setLastModifyTime(query.getLastModifyTime());
		queryDb.setShortName(query.getShortName());
		queryDb.setVersion(query.getVersion());
		return queryDb;
	}

	@Override
	public List<InterfaceProviderQueryBean> findAllProvider() {
		List<InterfaceProvider> list = interfaceProviderService.queryAllInterfaceProvider();
		List<InterfaceProviderQueryBean> beans = new ArrayList<InterfaceProviderQueryBean>();
		for (InterfaceProvider interfaceProvider : list) {
			InterfaceProviderQueryBean bean = new InterfaceProviderQueryBean();
			bean.setCode(interfaceProvider.getCode());
			bean.setId(interfaceProvider.getId());
			bean.setFullName(interfaceProvider.getFullName());
			bean.setShortName(interfaceProvider.getShortName());
			beans.add(bean);
		}
		return beans;
	}

	@Override
	public Page queryHistoryPageAll(Page<?> page, Map<String, Object> params) {
		return interfaceProviderService.queryHistoryPageAll(page, params);
	}
}