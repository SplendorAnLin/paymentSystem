package com.yl.boss.interfaces.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.Agent;
import com.yl.boss.api.bean.AgentCert;
import com.yl.boss.api.bean.AgentDeviceOrderBean;
import com.yl.boss.api.bean.AgentFee;
import com.yl.boss.api.bean.AgentSettle;
import com.yl.boss.api.bean.AppVersionBean;
import com.yl.boss.api.bean.DeviceTypeBean;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.boss.entity.AgentDeviceOrder;
import com.yl.boss.service.AgentCertService;
import com.yl.boss.service.AgentFeeService;
import com.yl.boss.service.AgentService;
import com.yl.boss.service.AgentSettleService;
import com.yl.boss.service.AppVersionService;
import com.yl.boss.service.DeviceService;
import com.yl.boss.valuelist.ValueListRemoteAction;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;

import net.mlw.vlh.ValueList;

/**
 * 服务商远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public class AgentInterfaceImpl implements AgentInterface{
	
	private AgentService agentService;
	private AgentFeeService agentFeeService;
	private AgentSettleService agentSettleService;
	private DeviceService deviceService;
	private ValueListRemoteAction valueListRemoteAction;
	private AgentCertService agentCertService;
	private AppVersionService appVersionService;
	
	@Override
	public List<Map<String, Object>> appGetAgentFeeIn(String agentNo){
		List<com.yl.boss.entity.AgentFee> list = agentFeeService.findByAgentNo(agentNo);
		if (list != null && list.size() > 0) {
			List<Map<String, Object>> db = new ArrayList<>();
			Map<String, Object> map;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < list.size(); i++) {
				if (!list.get(i).getProductType().name().equals("REMIT") && !list.get(i).getProductType().name().equals("HOLIDAY_REMIT")) {
					map = new HashMap<>();
					map.put("id", list.get(i).getId());
					map.put("customerNo", list.get(i).getAgentNo());
					map.put("productType", list.get(i).getProductType().name());
					map.put("feeType", list.get(i).getFeeType().name());
					map.put("fee", list.get(i).getFee());
					map.put("minFee", list.get(i).getMinFee());
					map.put("maxFee", list.get(i).getMaxFee());
					map.put("status", list.get(i).getStatus().name());
					map.put("createTime", sdf.format(list.get(i).getCreateTime()));
					if(list.get(i).getProductType().name().equals("B2C")){
						map.put("chinese", "个人网银");
						map.put("img", "");
					}
					if(list.get(i).getProductType().name().equals("B2B")){
						map.put("chinese", "企业网银");
						map.put("img", "");
					}
					if(list.get(i).getProductType().name().equals("AUTHPAY")){
						map.put("chinese", "认证支付");
						map.put("img", "");
					}
					if(list.get(i).getProductType().name().equals("WXJSAPI")){
						map.put("chinese", "微信公众号");
						map.put("img", "");
					}
					if(list.get(i).getProductType().name().equals("WXNATIVE")){
						map.put("chinese", "微信扫码");
						map.put("img", "");
					}
					if(list.get(i).getProductType().name().equals("ALIPAY")){
						map.put("chinese", "支付宝");
						map.put("img", "");
					}
					if(list.get(i).getProductType().name().equals("RECEIVE")){
						map.put("chinese", "代收");
						map.put("img", "");
					}
					db.add(map);
				} 
			}
			return db;
		}
		return null;
	}
	
	@Override
	public List<Map<String, Object>> appGetAgentFeeOut(String agentNo){
		List<com.yl.boss.entity.AgentFee> list = agentFeeService.findByAgentNo(agentNo);
		if (list != null && list.size() > 0) {
			Map<String, Object> map;
			List<Map<String, Object>> db = new ArrayList<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getProductType().name().equals("REMIT") || list.get(i).getProductType().name().equals("HOLIDAY_REMIT")) {
					map = new HashMap<>();
					map.put("id", list.get(i).getId());
					map.put("customerNo", list.get(i).getAgentNo());
					map.put("productType", list.get(i).getProductType().name());
					map.put("feeType", list.get(i).getFeeType().name());
					map.put("fee", list.get(i).getFee());
					map.put("minFee", list.get(i).getMinFee());
					map.put("maxFee", list.get(i).getMaxFee());
					map.put("status", list.get(i).getStatus().name());
					map.put("createTime", sdf.format(list.get(i).getCreateTime()));
					if(list.get(i).getProductType().name().equals("REMIT")){
						map.put("chinese","代付");
						map.put("img", "");
					}
					if(list.get(i).getProductType().name().equals("HOLIDAY_REMIT")){
						map.put("chinese","假日付");
						map.put("img", "");
					}
					db.add(map);
				} 
			}
			return db;
		}
		return null;
	}

	
	@Override
	public AgentFee getAgentFee(String agentNo, ProductType productType) {
		com.yl.boss.entity.AgentFee agFee = agentFeeService.findBy(agentNo, productType);
		if(agFee != null){
			return (AgentFee)JsonUtils.toJsonToObject(agFee, AgentFee.class);
		}
		return null;
	}

	@Override
	public AgentSettle getAgentSettle(String agentNo) {
		com.yl.boss.entity.AgentSettle agSettle = agentSettleService.findByAgentNo(agentNo);
		if(agSettle != null){
			return (AgentSettle)JsonUtils.toJsonToObject(agSettle, AgentSettle.class);
		}
		return null;
	}
	
	public AppVersionBean getAppVersion(String type,String oem) {
		com.yl.boss.entity.AppVersion appVersion=appVersionService.findByType(type, oem);
		if (appVersion!=null) {
			return (AppVersionBean)JsonUtils.toJsonToObject(appVersion, AppVersionBean.class);
		}
		return null;
	}

	@Override
	public Agent getAgent(String agentNo) {
		com.yl.boss.entity.Agent ag = agentService.findByNo(agentNo);
		if(ag != null){
			return (Agent)JsonUtils.toJsonToObject(ag, Agent.class);
		}
		return null;
	}
	
	@Override
	public List<AgentSettle> getListAgentSettle(String agentNo){
		List<com.yl.boss.entity.AgentSettle> agSettle = agentSettleService.findListByAgentNo(agentNo);
		if (agSettle!= null && agSettle.size() > 0) {
			return JsonUtils.toObject(JsonUtils.toJsonString(agSettle), new TypeReference<List<AgentSettle>>() {});
		}
		return null;
	}
	
	@Override
	public List<AgentFee> getAgentFee(String agentNo){
		List<com.yl.boss.entity.AgentFee> fees = agentFeeService.findByAgentNo(agentNo);
		if (fees != null && fees.size() > 0) {
			return JsonUtils.toObject(JsonUtils.toJsonString(fees), new TypeReference<List<AgentFee>>() {});
		}
		return null;
	}
	
	@Override
	public void createAgent(ReceiveConfigInfoBean receiveConfigInfoBean, Agent agent, AgentCert agentCert,
			AgentSettle agentSettle, List<AgentFee> agentFees, String serviceConfigBean, String oper, int cycle) {
		List<com.yl.boss.entity.AgentFee> list = JsonUtils.toObject(JsonUtils.toJsonString(agentFees), new TypeReference<List<com.yl.boss.entity.AgentFee>>() {
		});
		agentService.createAgent(receiveConfigInfoBean, JsonUtils.toJsonToObject(agent, com.yl.boss.entity.Agent.class), JsonUtils.toJsonToObject(agentCert, com.yl.boss.entity.AgentCert.class), JsonUtils.toJsonToObject(agentSettle, com.yl.boss.entity.AgentSettle.class),list, JsonUtils.toObject(serviceConfigBean, ServiceConfigBean.class), oper, cycle);
		
	}

	@Override
	public AgentDeviceOrderBean toPayAgain(String outOrderId) {
		return JsonUtils.toObject(JsonUtils.toJsonString(deviceService.findByOrderId(outOrderId)), new TypeReference<AgentDeviceOrderBean>() {});
	}

	@Override
	public void updateAgentDeviceOrder(AgentDeviceOrderBean agentDeviceOrderBean, String oper) {
		deviceService.updateDeviceOrder(JsonUtils.toJsonToObject(agentDeviceOrderBean,AgentDeviceOrder.class));
	}
	
	@Override
	public void addAgentDeviceOrder(AgentDeviceOrderBean agentDeviceOrderBean,String oper) {
		deviceService.createAgentOrder(JsonUtils.toJsonToObject(agentDeviceOrderBean,AgentDeviceOrder.class), oper);
	}
	
	public List<DeviceTypeBean> getDeviceList(){
		return JsonUtils.toObject(JsonUtils.toJsonString(deviceService.getDeviceType()), new TypeReference<List<DeviceTypeBean>>() {});
	}
	
	@Override
	public Page queryAgentDeviceOrderByAgentNo(Map<String, Object> params) {
		ValueList vl = valueListRemoteAction.getValueList("agentDeviceOrderInfo", params);
		Page page = new Page<>();
		page.setCurrentPage(vl.getValueListInfo().getPagingPage());
		page.setTotalResult(vl.getValueListInfo().getTotalNumberOfEntries());
		page.setObject(vl.getList());
		return page;
	}
	

	@Override
	public Page queryAgentDeviceByAgentNo(Map<String, Object> params) {
		ValueList vl = valueListRemoteAction.getValueList("deviceInfo", params);
		Page page = new Page<>();
		page.setCurrentPage(vl.getValueListInfo().getPagingPage());
		page.setTotalResult(vl.getValueListInfo().getTotalNumberOfEntries());
		page.setObject(vl.getList());
		return page;
	}
	
	@Override
	public Page queryAgent(String queryId, Map<String, Object> params) {
		ValueList vl = valueListRemoteAction.execute("agentInfo", params).get("agentInfo");
		Page page = new Page<>();
		page.setCurrentPage(vl.getValueListInfo().getPagingPage());
		page.setTotalResult(vl.getValueListInfo().getTotalNumberOfEntries());
		page.setObject(vl.getList());
		return page;
	}
	
	@Override
	public void updateAgent(Agent agent, List<AgentFee> feeList, ReceiveConfigInfoBean receiveConfigInfoBean,
			AgentCert agentCert, AgentSettle agentSettle, String serviceConfigBean, String oper, int cycle) {
		List<com.yl.boss.entity.AgentFee> list = JsonUtils.toObject(JsonUtils.toJsonString(feeList), new TypeReference<List<com.yl.boss.entity.AgentFee>>() {
		});
		agentService.updateAgent(JsonUtils.toJsonToObject(agent, com.yl.boss.entity.Agent.class),list,receiveConfigInfoBean,JsonUtils.toJsonToObject(agentCert, com.yl.boss.entity.AgentCert.class), JsonUtils.toJsonToObject(agentSettle, com.yl.boss.entity.AgentSettle.class), JsonUtils.toObject(serviceConfigBean, ServiceConfigBean.class), oper, cycle);
	}
	@Override
	public int yOrNoOrder(AgentDeviceOrderBean agentDeviceOrderBean) {
		return deviceService.yOrNoOrder(JsonUtils.toJsonToObject(agentDeviceOrderBean,AgentDeviceOrder.class));
	}
	@Override
	public AgentCert findByAgentNo(String agentNo) {
		return JsonUtils.toJsonToObject(agentCertService.findByAgentNo(agentNo), AgentCert.class);
	}

	@Override
	public Map<String, String> checkAgentName(String fullName, String shortName) {
		return agentService.checkAgentName(fullName, shortName);
	}

	@Override
	public int queryAgentLevelByAgentNo(String agentNo) {
		return agentService.queryAgentLevelByAgentNo(agentNo);
	}
	
	public AgentService getAgentService() {
		return agentService;
	}

	public void setAgentService(AgentService agentService) {
		this.agentService = agentService;
	}

	public AgentFeeService getAgentFeeService() {
		return agentFeeService;
	}

	public void setAgentFeeService(AgentFeeService agentFeeService) {
		this.agentFeeService = agentFeeService;
	}

	public AgentSettleService getAgentSettleService() {
		return agentSettleService;
	}

	public void setAgentSettleService(AgentSettleService agentSettleService) {
		this.agentSettleService = agentSettleService;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public ValueListRemoteAction getValueListRemoteAction() {
		return valueListRemoteAction;
	}

	public void setValueListRemoteAction(ValueListRemoteAction valueListRemoteAction) {
		this.valueListRemoteAction = valueListRemoteAction;
	}

	public AgentCertService getAgentCertService() {
		return agentCertService;
	}

	public void setAgentCertService(AgentCertService agentCertService) {
		this.agentCertService = agentCertService;
	}

	public AppVersionService getAppVersionService() {
		return appVersionService;
	}

	public void setAppVersionService(AppVersionService appVersionService) {
		this.appVersionService = appVersionService;
	}

	@Override
	public String getConfigKey() {
		return deviceService.getConfigKey();
	}

	@Override
	public Agent findByPhone(String phone) {
		return JsonUtils.toJsonToObject(agentService.findByPhone(phone), Agent.class);
	}

	@Override
	public List<String> findAllCustomerNoByAgentNo(String agentNo) {
		return agentService.findAllCustomerNoByAgentNo(agentNo);
	}
}