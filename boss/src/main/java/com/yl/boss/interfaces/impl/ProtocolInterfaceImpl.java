package com.yl.boss.interfaces.impl;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.Page;
import com.yl.boss.api.interfaces.ProtocolInterface;
import com.yl.boss.service.ProtocolManagementService;
import com.yl.boss.valuelist.ValueListRemoteAction;

import net.mlw.vlh.ValueList;

/**
 * 入网协议远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public class ProtocolInterfaceImpl implements ProtocolInterface {
  
	private static final Logger logger = LoggerFactory.getLogger(ProtocolInterfaceImpl.class);
	
	private ProtocolManagementService protocolManagementService;
	private ValueListRemoteAction valueListRemoteAction;
	
	public ProtocolManagementService getProtocolManagementService() {
		return protocolManagementService;
	}

	public void setProtocolManagementService(ProtocolManagementService protocolManagementService) {
		this.protocolManagementService = protocolManagementService;
	}

	@Override
	public Map<String, Object> wapProtocol(String sort, String type) {
		return protocolManagementService.wapProtocol(sort, type);
	}
	
	@Override
	public Page getProtolList(Map<String, Object> params) {
		ValueList vl = valueListRemoteAction.execute("protocolManagements", params).get("protocolManagements");
		Page page = new Page<>();
		page.setCurrentPage(vl.getValueListInfo().getPagingPage());
		page.setTotalResult(vl.getValueListInfo().getTotalNumberOfEntries());
		page.setShowCount(vl.getValueListInfo().getPagingNumberPer());
		page.setObject(vl.getList());
		return page;
	}
     
	
	public ValueListRemoteAction getValueListRemoteAction() {
		return valueListRemoteAction;
	}

	public void setValueListRemoteAction(ValueListRemoteAction valueListRemoteAction) {
		this.valueListRemoteAction = valueListRemoteAction;
	}


	@Override
	public Map<String, Object> selectWechathelp(String sort, int id,String status) {
		
		return  protocolManagementService.selectWechathelp(sort, id,status);
	} 
	

 
}