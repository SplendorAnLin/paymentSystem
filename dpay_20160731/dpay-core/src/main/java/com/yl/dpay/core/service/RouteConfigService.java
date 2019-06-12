package com.yl.dpay.core.service;

import java.util.List;

import com.yl.dpay.core.entity.RouteConfig;
import com.yl.dpay.core.enums.AccountType;
import com.yl.dpay.core.enums.CardType;
import com.yl.dpay.core.enums.CerType;
import com.yl.dpay.hessian.beans.RouteConfigBean;

/**
 * 代付路由配置业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface RouteConfigService {
	
	/**
	 * @param routeConfig
	 * @param oper
	 */
	public void create(RouteConfig routeConfig,String oper);
	
	/**
	 * @param code
	 * @return
	 */
	public RouteConfig findByCode(String code);
	
	/**
	 * @return
	 */
	public RouteConfig findRouteConfig();
	
	/**
	 * @param routeConfig
	 * @param oper
	 */
	public void update(RouteConfig routeConfig,String oper);
	
	/**
	 * @param bankCode
	 * @param accountType
	 * @param cardType
	 * @param cerType
	 * @return interfaceCode
	 */
	public String compRoute(String bankCode,AccountType accountType,CardType cardType,CerType cerType);
	
	/**
	 * @param bankCode
	 * @return interfaceCode
	 */
	public String compRoute(String bankCode);
	
	/**
	 * @param bankCode
	 * @param accountType
	 * @param cardType
	 * @return interfaceCode
	 */
	public String compRoute(String bankCode,AccountType accountType,CardType cardType);
	
	/**
	 * @param bankCode
	 * @param accountType
	 * @return interfaceCode
	 */
	public String compRoute(String bankCode,AccountType accountType);
	
	/**
	 * @param accountType
	 * @return interfaceCode
	 */
	public String compRoute(AccountType accountType);
	
	/**
	 * 根据条件查询所有代付路由
	 * @return
	 */
	public List<RouteConfigBean> findRouteConfigList(RouteConfigBean routeConfigBean);
	
}
