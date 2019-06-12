package com.yl.dpay.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.dpay.core.dao.RouteConfigDao;
import com.yl.dpay.core.dao.RouteConfigHistoryDao;
import com.yl.dpay.core.entity.RouteConfig;
import com.yl.dpay.core.entity.RouteConfigHistory;
import com.yl.dpay.core.entity.RouteInfo;
import com.yl.dpay.core.enums.AccountType;
import com.yl.dpay.core.enums.CardType;
import com.yl.dpay.core.enums.CerType;
import com.yl.dpay.core.enums.Status;
import com.yl.dpay.core.service.RouteConfigService;
import com.yl.dpay.hessian.beans.RouteConfigBean;
import com.yl.dpay.hessian.beans.RouteInfoBean;

/**
 * 代付路由配置业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Service("routeConfigService")
public class RouteConfigServiceImpl implements RouteConfigService {
	
	@Resource
	private RouteConfigDao routeConfigDao;
	@Resource
	private RouteConfigHistoryDao routeConfigHistoryDao;

	@Override
	@Transactional
	public void create(RouteConfig routeConfig,String oper) {
		routeConfig.setCreateDate(new Date());
		routeConfigDao.insert(routeConfig);
		
		RouteConfigHistory routeConfigHistory = new RouteConfigHistory(routeConfig, oper);
		routeConfigHistoryDao.insert(routeConfigHistory);
	}

	@Override
	public RouteConfig findByCode(String code) {
		return routeConfigDao.findByCode(code);
	}

	@Override
	@Transactional
	public void update(RouteConfig routeConfig,String oper) {
		RouteConfig config = routeConfigDao.findByCode(routeConfig.getCode());
		if(routeConfig != null){
			config.setRouteInfo(routeConfig.getRouteInfo());
			config.setStatus(routeConfig.getStatus());
			routeConfigDao.update(config);
			
			RouteConfigHistory routeConfigHistory = new RouteConfigHistory(config, oper);
			routeConfigHistoryDao.insert(routeConfigHistory);
		}
	}

	@Override
	public String compRoute(String bankCode, AccountType accountType,
			CardType cardType, CerType cerType) {
		String interfaceCode = null;
		List<RouteConfig> list = routeConfigDao.findBy(Status.TRUE);
		int tmpPriority = 0;
		for(RouteConfig routeConfig : list){
			if(!StringUtils.notBlank(routeConfig.getRouteInfo())){
				continue;
			}
			RouteInfo routeInfo = JsonUtils.toObject(routeConfig.getRouteInfo(), new TypeReference<RouteInfo>(){});
			if(routeInfo.getAccountTypes() == null || routeInfo.getCardTypes() == null || routeInfo.getCerTypes() == null ||
					routeInfo.getBankCodes() == null){
				continue;
			}
			if(routeInfo.getAccountTypes().contains(accountType) && routeInfo.getCardTypes().contains(cardType)
					&& routeInfo.getCerTypes().contains(cerType) && routeInfo.getBankCodes().contains(bankCode)){
				if(routeInfo.getPriority() == 0){
					return routeInfo.getRemitCode();
				}
				
				if(tmpPriority == 0 || tmpPriority > routeInfo.getPriority()){
					tmpPriority = routeInfo.getPriority();
					interfaceCode = routeInfo.getRemitCode();
					continue;
				}
			}
		}
		return interfaceCode;
	}

	@Override
	public String compRoute(String bankCode) {
		String interfaceCode = null;
		List<RouteConfig> list = routeConfigDao.findBy(Status.TRUE);
		int tmpPriority = 0;
		for(RouteConfig routeConfig : list){
			if(!StringUtils.notBlank(routeConfig.getRouteInfo())){
				continue;
			}
			RouteInfo routeInfo = JsonUtils.toObject(routeConfig.getRouteInfo(), new TypeReference<RouteInfo>(){});
			if(routeInfo.getBankCodes() == null){
				continue;
			}
			if(routeInfo.getBankCodes().contains(bankCode)){
				if(routeInfo.getPriority() == 0){
					return routeInfo.getRemitCode();
				}
				
				if(tmpPriority == 0 || tmpPriority > routeInfo.getPriority()){
					tmpPriority = routeInfo.getPriority();
					interfaceCode = routeInfo.getRemitCode();
					continue;
				}
			}
		}
		return interfaceCode;
	}

	@Override
	public String compRoute(String bankCode, AccountType accountType, CardType cardType) {
		String interfaceCode = null;
		List<RouteConfig> list = routeConfigDao.findBy(Status.TRUE);
		int tmpPriority = 0;
		for(RouteConfig routeConfig : list){
			if(!StringUtils.notBlank(routeConfig.getRouteInfo())){
				continue;
			}
			RouteInfo routeInfo = JsonUtils.toObject(routeConfig.getRouteInfo(), new TypeReference<RouteInfo>(){});
			if(routeInfo.getAccountTypes() == null || routeInfo.getBankCodes() == null){
				continue;
			}
			if(routeInfo.getAccountTypes().contains(accountType) && routeInfo.getBankCodes().contains(bankCode)
					&& routeInfo.getCardTypes().contains(cardType)){
				if(routeInfo.getPriority() == 0){
					return routeInfo.getRemitCode();
				}
				
				if(tmpPriority == 0 || tmpPriority > routeInfo.getPriority()){
					tmpPriority = routeInfo.getPriority();
					interfaceCode = routeInfo.getRemitCode();
					continue;
				}
			}
		}
		return interfaceCode;
	}

	@Override
	public String compRoute(AccountType accountType) {
		String interfaceCode = null;
		List<RouteConfig> list = routeConfigDao.findBy(Status.TRUE);
		int tmpPriority = 0;
		for(RouteConfig routeConfig : list){
			if(!StringUtils.notBlank(routeConfig.getRouteInfo())){
				continue;
			}
			RouteInfo routeInfo = JsonUtils.toObject(routeConfig.getRouteInfo(), new TypeReference<RouteInfo>(){});
			if(routeInfo.getAccountTypes() == null){
				continue;
			}
			if(routeInfo.getAccountTypes().contains(accountType)){
				if(routeInfo.getPriority() == 0){
					return routeInfo.getRemitCode();
				}
				
				if(tmpPriority == 0 || tmpPriority > routeInfo.getPriority()){
					tmpPriority = routeInfo.getPriority();
					interfaceCode = routeInfo.getRemitCode();
					continue;
				}
			}
		}
		return interfaceCode;
	}

	@Override
	public String compRoute(String bankCode, AccountType accountType) {
		String interfaceCode = null;
		List<RouteConfig> list = routeConfigDao.findBy(Status.TRUE);
		int tmpPriority = 0;
		for(RouteConfig routeConfig : list){
			if(!StringUtils.notBlank(routeConfig.getRouteInfo())){
				continue;
			}
			RouteInfo routeInfo = JsonUtils.toObject(routeConfig.getRouteInfo(), new TypeReference<RouteInfo>(){});
			if(routeInfo.getAccountTypes() == null || routeInfo.getBankCodes() == null){
				continue;
			}
			if(routeInfo.getAccountTypes().contains(accountType) && routeInfo.getBankCodes().contains(bankCode)){
				if(routeInfo.getPriority() == 0){
					return routeInfo.getRemitCode();
				}
				
				if(tmpPriority == 0 || tmpPriority > routeInfo.getPriority()){
					tmpPriority = routeInfo.getPriority();
					interfaceCode = routeInfo.getRemitCode();
					continue;
				}
			}
		}
		return interfaceCode;
	}

	@Override
	public RouteConfig findRouteConfig() {
		return routeConfigDao.findRouteConfig();
	}

	@Override
	public List<RouteConfigBean> findRouteConfigList(RouteConfigBean routeConfigBean) {
		List<RouteConfigBean> beanList = null;
		RouteConfig routeConfig = new RouteConfig();
		routeConfig.setCode(routeConfigBean.getCode());
		routeConfig.setName(routeConfigBean.getName());
		routeConfig.setStatus(Status.valueOf(String.valueOf(routeConfigBean.getStatus())));
		List<RouteConfig> list = routeConfigDao.findRouteConfigList(routeConfig);
		beanList = new ArrayList<>();
		for(RouteConfig config : list){
			RouteConfigBean bean = new RouteConfigBean();
			bean.setCode(config.getCode());
			bean.setName(config.getName());
			bean.setStatus(com.yl.dpay.hessian.enums.Status.valueOf(config.getStatus().name()));
			bean.setRouteInfo(JsonUtils.toObject(config.getRouteInfo(), new TypeReference<RouteInfoBean>(){}));
			beanList.add(bean);
		}
		return beanList;
	}

}
