package com.yl.receive.core.remote.hessian.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.receive.core.entity.ReceiveConfigInfo;
import com.yl.receive.core.entity.ReceiveConfigInfoHistory;
import com.yl.receive.core.entity.RouteConfig;
import com.yl.receive.core.enums.Status;
import com.yl.receive.core.service.ReceiveConfigInfoService;
import com.yl.receive.core.service.ReceiveRequestService;
import com.yl.receive.core.service.RouteConfigService;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;
import com.yl.receive.hessian.bean.ReceiveRequestBean;
import com.yl.receive.hessian.bean.RouteConfigBean;
import com.yl.receive.hessian.bean.RouteInfoBean;
import com.yl.receive.hessian.enums.ReceiveConfigStatus;

/**
 * 代收查询远程服务实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年1月7日
 * @version V1.0.0
 */
@Service("receiveQueryFacade")
public class ReceiveQueryFacadeImpl implements ReceiveQueryFacade {
	@Resource
	ReceiveConfigInfoService receiveConfigInfoService;
	@Resource
	private ReceiveRequestService receiveRequestService;

	@Resource
	private RouteConfigService routeConfigService;

	
	@Override
	public List<Map<String, String>> orderSumByDay(Date orderTimeStart, Date orderTimeEnd,String owner) {
		return receiveRequestService.orderSumByDay(orderTimeStart, orderTimeEnd,owner);
	}

	@Override
	public ReceiveRequestBean findByRec(String receiveId) {
		return receiveRequestService.findBy(receiveId);
	}

	@Override
	public ReceiveRequestBean findBy(String ownerId, String customerOrderCode) {
		return receiveRequestService.findBy(ownerId, customerOrderCode);
	}

	@Override
	public ReceiveConfigInfoBean queryReceiveConfigBy(String ownerId) {
		ReceiveConfigInfo receiveConfigInfo=receiveConfigInfoService.queryBy(ownerId);
		if (receiveConfigInfo!=null) {
			ReceiveConfigInfoBean receiveConfigInfoBean=new ReceiveConfigInfoBean();
			receiveConfigInfoBean.setOwnerId(receiveConfigInfo.getOwnerId());
			receiveConfigInfoBean.setCreateTime(receiveConfigInfo.getCreateTime());
			receiveConfigInfoBean.setCustIp(receiveConfigInfo.getCustIp());
			receiveConfigInfoBean.setDailyMaxAmount(receiveConfigInfo.getDailyMaxAmount());
			receiveConfigInfoBean.setDomain(receiveConfigInfo.getDomain());
			receiveConfigInfoBean.setIsCheckContract(receiveConfigInfo.getIsCheckContract());
			receiveConfigInfoBean.setPrivateCer(receiveConfigInfo.getPrivateCer());
			receiveConfigInfoBean.setPublicCer(receiveConfigInfo.getPublicCer());
			receiveConfigInfoBean.setRemark(receiveConfigInfo.getRemark());
			receiveConfigInfoBean.setSingleBatchMaxNum(receiveConfigInfo.getSingleBatchMaxNum());
			receiveConfigInfoBean.setSingleMaxAmount(Double.valueOf(receiveConfigInfo.getSingleMaxAmount()));
			receiveConfigInfoBean.setStatus(ReceiveConfigStatus.valueOf(receiveConfigInfo.getStatus().toString()));
			receiveConfigInfoBean.setVersion(receiveConfigInfo.getVersion());
			return receiveConfigInfoBean;
		}
		return null;
	}
	
	@Override
	public List<ReceiveConfigInfoBean> findAllRecfByPage(Map<String, Object> queryParams, Page<?> page) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 添加代收历史信息
	 * @param receiveConfigInfoHistory
	 */
	public void insertHistory(ReceiveConfigInfo receiveConfigInfo){
		ReceiveConfigInfoHistory receiveConfigInfoHistory=new ReceiveConfigInfoHistory();
		receiveConfigInfoHistory.setOwnerId(receiveConfigInfo.getOwnerId());
		receiveConfigInfoHistory.setCreateTime(new Date());
		receiveConfigInfoHistory.setCustIp(receiveConfigInfo.getCustIp());
		receiveConfigInfoHistory.setDailyMaxAmount(String.valueOf(receiveConfigInfo.getDailyMaxAmount()));
		receiveConfigInfoHistory.setDomain(receiveConfigInfo.getDomain());
		receiveConfigInfoHistory.setIsCheckContract(receiveConfigInfo.getIsCheckContract());
		receiveConfigInfoHistory.setPrivateCer(receiveConfigInfo.getPrivateCer());
		receiveConfigInfoHistory.setPublicCer(receiveConfigInfo.getPublicCer());
		receiveConfigInfoHistory.setRemark(receiveConfigInfo.getRemark());
		receiveConfigInfoHistory.setSingleBatchMaxNum(String.valueOf(receiveConfigInfo.getSingleBatchMaxNum()));
		receiveConfigInfoHistory.setSingleMaxAmount(String.valueOf(receiveConfigInfo.getSingleMaxAmount()));
		receiveConfigInfoHistory.setStatus(com.yl.receive.core.enums.ReceiveConfigStatus.valueOf(receiveConfigInfo.getStatus().toString()));
		receiveConfigInfoHistory.setVersion(receiveConfigInfo.getVersion());
		receiveConfigInfoService.insertHistory(receiveConfigInfoHistory);
	}
	
	@Override
	public void insertReceiveConfig(ReceiveConfigInfoBean receiveConfigInfoBean) {
		ReceiveConfigInfo receiveConfigInfo=new ReceiveConfigInfo();
		if (receiveConfigInfoBean!=null) {
			receiveConfigInfo.setOwnerId(receiveConfigInfoBean.getOwnerId());
			receiveConfigInfo.setCreateTime(receiveConfigInfoBean.getCreateTime());
			receiveConfigInfo.setCustIp(receiveConfigInfoBean.getCustIp());
			receiveConfigInfo.setDailyMaxAmount(receiveConfigInfoBean.getDailyMaxAmount());
			receiveConfigInfo.setDomain(receiveConfigInfoBean.getDomain());
			receiveConfigInfo.setIsCheckContract(receiveConfigInfoBean.getIsCheckContract());
			receiveConfigInfo.setPrivateCer(receiveConfigInfoBean.getPrivateCer());
			receiveConfigInfo.setPublicCer(receiveConfigInfoBean.getPublicCer());
			receiveConfigInfo.setRemark(receiveConfigInfoBean.getRemark());
			receiveConfigInfo.setSingleBatchMaxNum(receiveConfigInfoBean.getSingleBatchMaxNum());
			receiveConfigInfo.setSingleMaxAmount(receiveConfigInfoBean.getSingleMaxAmount());
			receiveConfigInfo.setStatus(com.yl.receive.core.enums.ReceiveConfigStatus.valueOf(receiveConfigInfoBean.getStatus().toString()));
			receiveConfigInfo.setVersion(receiveConfigInfoBean.getVersion());
			receiveConfigInfoService.insert(receiveConfigInfo);
			insertHistory(receiveConfigInfo);
		}
	}

	@Override
	public void updateReceiveConfig(ReceiveConfigInfoBean receiveConfigInfoBean) {
		ReceiveConfigInfo receiveConfigInfo=new ReceiveConfigInfo();
		if (receiveConfigInfoBean!=null) {
			receiveConfigInfo.setOwnerId(receiveConfigInfoBean.getOwnerId());
			receiveConfigInfo.setCreateTime(receiveConfigInfoBean.getCreateTime());
			receiveConfigInfo.setCustIp(receiveConfigInfoBean.getCustIp());
			receiveConfigInfo.setDailyMaxAmount(receiveConfigInfoBean.getDailyMaxAmount());
			receiveConfigInfo.setDomain(receiveConfigInfoBean.getDomain());
			receiveConfigInfo.setIsCheckContract(receiveConfigInfoBean.getIsCheckContract());
			receiveConfigInfo.setPrivateCer(receiveConfigInfoBean.getPrivateCer());
			receiveConfigInfo.setPublicCer(receiveConfigInfoBean.getPublicCer());
			receiveConfigInfo.setRemark(receiveConfigInfoBean.getRemark());
			receiveConfigInfo.setSingleBatchMaxNum(receiveConfigInfoBean.getSingleBatchMaxNum());
			receiveConfigInfo.setSingleMaxAmount(receiveConfigInfoBean.getSingleMaxAmount());
			receiveConfigInfo.setStatus(com.yl.receive.core.enums.ReceiveConfigStatus.valueOf(receiveConfigInfoBean.getStatus().toString()));
			receiveConfigInfo.setVersion(receiveConfigInfoBean.getVersion());
		}
		receiveConfigInfoService.update(receiveConfigInfo);
		insertHistory(receiveConfigInfo);
	}
	
	@Override
	public void createRoute(RouteConfigBean routeConfigBean, String oper) {
		RouteConfig routeConfig = new RouteConfig();
		routeConfig.setCode(routeConfigBean.getCode());
		routeConfig.setName(routeConfigBean.getName());
		routeConfig.setRouteInfo(JsonUtils.toJsonString(routeConfigBean.getRouteInfo()));
		routeConfig.setStatus(Status.valueOf(String.valueOf(routeConfigBean.getStatus())));
		routeConfigService.create(routeConfig, oper);
	}

	@Override
	public void updateRoute(RouteConfigBean routeConfigBean, String oper) {
		RouteConfig routeConfig = new RouteConfig();
		routeConfig.setCode(routeConfigBean.getCode());
		routeConfig.setName(routeConfigBean.getName());
		routeConfig.setRouteInfo(JsonUtils.toJsonString(routeConfigBean.getRouteInfo()));
		routeConfig.setStatus(Status.valueOf(String.valueOf(routeConfigBean.getStatus())));
		routeConfigService.update(routeConfig, oper);
	}

	@Override
	public RouteConfigBean findByCode(String code) {
		RouteConfigBean routeConfigBean = null;
		RouteConfig routeConfig = routeConfigService.findByCode(code);
		if(routeConfig != null){
			routeConfigBean = new RouteConfigBean();
			routeConfigBean.setCode(code);
			routeConfigBean.setName(routeConfig.getName());
			routeConfigBean.setStatus(com.yl.receive.hessian.enums.Status.valueOf(String.valueOf(routeConfig.getStatus())));
			routeConfigBean.setRouteInfo(JsonUtils.toObject(routeConfig.getRouteInfo(), new org.codehaus.jackson.type.TypeReference<RouteInfoBean>(){}));
		}
		return routeConfigBean;
	}

	@Override
	public RouteConfigBean findRouteConfig() {
		RouteConfigBean routeConfigBean = null;
		RouteConfig routeConfig = routeConfigService.findRouteConfig();
		if(routeConfig != null){
			routeConfigBean = new RouteConfigBean();
			routeConfigBean.setCode(routeConfig.getCode());
			routeConfigBean.setName(routeConfig.getName());
			routeConfigBean.setStatus(com.yl.receive.hessian.enums.Status.valueOf(String.valueOf(routeConfig.getStatus())));
			routeConfigBean.setRouteInfo(JsonUtils.toObject(routeConfig.getRouteInfo(), new org.codehaus.jackson.type.TypeReference<RouteInfoBean>(){}));
		}
		return routeConfigBean;
	}

	@Override
	public List<RouteConfigBean> findRouteConfigList(RouteConfigBean routeConfigBean) {
		return (List<RouteConfigBean>) routeConfigService.findRouteConfigList(routeConfigBean);
	}

	@Override
	public List<Map<String, String>> orderSum(Date orderTimeStart, Date orderTimeEnd, String owner) {
		return receiveRequestService.orderSum(orderTimeStart, orderTimeEnd, owner);
	}

	@Override
	public List<Map<String, String>> orderSumCount(Date orderTimeStart, Date orderTimeEnd, String owner) {
		return receiveRequestService.orderSumCount(orderTimeStart, orderTimeEnd, owner);
	}

	@Override
	public List<ReceiveRequestBean> customerRecon(Map<String, Object> params) {
		return receiveRequestService.customerRecon(params);
	}

	@Override
	public String customerReconHead(Map<String, Object> params) {
		return receiveRequestService.customerReconHead(params);
	}

	@Override
	public String customerReconSum(Map<String, Object> params) {
		return receiveRequestService.customerReconSum(params);
	}
}