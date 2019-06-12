package com.yl.dpay.core.hessian;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.dpay.core.entity.DpayConfig;
import com.yl.dpay.core.entity.RouteConfig;
import com.yl.dpay.core.enums.RemitType;
import com.yl.dpay.core.enums.Status;
import com.yl.dpay.core.service.DpayConfigService;
import com.yl.dpay.core.service.RouteConfigService;
import com.yl.dpay.hessian.beans.DpayConfigBean;
import com.yl.dpay.hessian.beans.RouteConfigBean;
import com.yl.dpay.hessian.beans.RouteInfoBean;
import com.yl.dpay.hessian.service.DpayConfigFacade;

/**
 * 代付配置远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月17日
 * @version V1.0.0
 */
@Service("dpayConfigFacade")
public class DpayConfigFacadeImpl implements DpayConfigFacade {
	
	@Resource
	private DpayConfigService dpayConfigService;
	@Resource
	private RouteConfigService routeConfigService;

	@Override
	public void config(DpayConfigBean dpayConfigBean) {
		DpayConfig dpayConfig = new DpayConfig();
		dpayConfig.setId(dpayConfigBean.getId());
		dpayConfig.setAuditAmount(dpayConfigBean.getAuditAmount());
		dpayConfig.setEndTime(dpayConfigBean.getEndTime());
		dpayConfig.setHolidayStatus(Status.valueOf(String.valueOf(dpayConfigBean.getHolidayStatus())));
		dpayConfig.setMaxAmount(dpayConfigBean.getMaxAmount());
		dpayConfig.setMinAmount(dpayConfigBean.getMinAmount());
		dpayConfig.setRemitType(RemitType.valueOf(String.valueOf(dpayConfigBean.getRemitType())));
		dpayConfig.setReRemitFlag(Status.valueOf(String.valueOf(dpayConfigBean.getReRemitFlag())));
		dpayConfig.setStartTime(dpayConfigBean.getStartTime());
		dpayConfig.setStatus(Status.valueOf(String.valueOf(dpayConfigBean.getStatus())));
		dpayConfigService.update(dpayConfig, dpayConfigBean.getOper());
	}

	@Override
	public DpayConfigBean findDfConfig() {
		DpayConfig dpayConfig = dpayConfigService.findDpayConfig();
		if(dpayConfig != null){
			DpayConfigBean bean = new DpayConfigBean();
			bean.setId(dpayConfig.getId());
			bean.setCreateDate(dpayConfig.getCreateDate());
			bean.setAuditAmount(dpayConfig.getAuditAmount());
			bean.setEndTime(dpayConfig.getEndTime());
			bean.setHolidayStatus(com.yl.dpay.hessian.enums.Status.valueOf(String.valueOf(dpayConfig.getHolidayStatus())));
			bean.setMaxAmount(dpayConfig.getMaxAmount());
			bean.setMinAmount(dpayConfig.getMinAmount());
			bean.setRemitType(com.yl.dpay.hessian.enums.RemitType.valueOf(String.valueOf(dpayConfig.getRemitType())));
			bean.setReRemitFlag(com.yl.dpay.hessian.enums.Status.valueOf(String.valueOf(dpayConfig.getReRemitFlag())));
			bean.setStartTime(dpayConfig.getStartTime());
			bean.setStatus(com.yl.dpay.hessian.enums.Status.valueOf(String.valueOf(dpayConfig.getStatus())));
			return bean;
		}
		return null;
	}
	
	@Override
	public void createRoute(RouteConfigBean routeConfigBean,String oper) {
		RouteConfig routeConfig = new RouteConfig();
		routeConfig.setCode(routeConfigBean.getCode());
		routeConfig.setName(routeConfigBean.getName());
		routeConfig.setRouteInfo(JsonUtils.toJsonString(routeConfigBean.getRouteInfo()));
		routeConfig.setStatus(Status.valueOf(String.valueOf(routeConfigBean.getStatus())));
		routeConfigService.create(routeConfig, oper);
	}

	@Override
	public void updateRoute(RouteConfigBean routeConfigBean,String oper) {
		RouteConfig routeConfig = new RouteConfig();
		routeConfig.setCode(routeConfigBean.getCode());
		routeConfig.setName(routeConfigBean.getName());
		routeConfig.setRouteInfo(JsonUtils.toJsonString(routeConfigBean.getRouteInfo()));
		routeConfig.setStatus(Status.valueOf(String.valueOf(routeConfigBean.getStatus())));
		routeConfigService.update(routeConfig, oper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RouteConfigBean findByCode(String code) {
		RouteConfigBean routeConfigBean = null;
		RouteConfig routeConfig = routeConfigService.findByCode(code);
		if(routeConfig != null){
			routeConfigBean = new RouteConfigBean();
			routeConfigBean.setCode(code);
			routeConfigBean.setName(routeConfig.getName());
			routeConfigBean.setStatus(com.yl.dpay.hessian.enums.Status.valueOf(String.valueOf(routeConfig.getStatus())));
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
			routeConfigBean.setStatus(com.yl.dpay.hessian.enums.Status.valueOf(String.valueOf(routeConfig.getStatus())));
			routeConfigBean.setRouteInfo(JsonUtils.toObject(routeConfig.getRouteInfo(), new org.codehaus.jackson.type.TypeReference<RouteInfoBean>(){}));
		}
		return routeConfigBean;
	}

	@Override
	public List<RouteConfigBean> findRouteConfigList(RouteConfigBean routeConfigBean) {
		return (List<RouteConfigBean>) routeConfigService.findRouteConfigList(routeConfigBean);
	}

}
