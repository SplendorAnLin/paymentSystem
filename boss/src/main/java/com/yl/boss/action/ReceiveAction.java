package com.yl.boss.action;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lefu.commons.cache.bean.DictionaryType;
import com.lefu.commons.cache.tags.DictionaryUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.receive.hessian.ReceiveFacade;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;
import com.yl.receive.hessian.bean.RouteConfigBean;
import com.yl.receive.hessian.bean.RouteInfoBean;
import com.yl.boss.Constant;
import com.yl.boss.entity.Authorization;
import com.yl.boss.utils.RSAUtils;
import com.yl.dpay.hessian.service.QueryFacade;

import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueListInfo;

/**
 * 代收控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class ReceiveAction  extends Struts2ActionSupport{
	private static final long serialVersionUID = 8995432988301364380L;
	private ReceiveFacade recfFacade;
	private ReceiveConfigInfoBean receiveConfigInfoBean;
	private ReceiveQueryFacade receiveQueryFacade;
	private ReceiveConfigInfoBean receiveConfigInfo;
	private String ownerId;
	private String msg;
	private RouteConfigBean routeConfigBean;
	private RouteInfoBean routeInfoBean;
	private List<RouteConfigBean> routeConfigBeanList;
	private Map<String,Object> supportPayList;

	
	public ReceiveConfigInfoBean getReceiveConfigInfo() {
		return receiveConfigInfo;
	}

	public void setReceiveConfigInfo(ReceiveConfigInfoBean receiveConfigInfo) {
		this.receiveConfigInfo = receiveConfigInfo;
	}

	/**
	 * 新增
	 */
	public String serviceConfigAdd(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		try {
			Map<String, Object> keyMap = RSAUtils.genKeyPair();
			receiveConfigInfoBean.setPrivateCer(RSAUtils.getPrivateKey(keyMap));
			receiveConfigInfoBean.setPublicCer(RSAUtils.getPublicKey(keyMap));
			receiveConfigInfoBean.setCreateTime(new Date());
			receiveConfigInfoBean.setOper(auth.getUsername());
			receiveQueryFacade.insertReceiveConfig(receiveConfigInfoBean);
			msg="true";
		} catch (Exception e) {
			logger.info("帐号:"+receiveConfigInfoBean.getOwnerId()+"创建代收信息失败，异常信息{}", e);
			msg="false";
		}
		return SUCCESS;
	}
	
	public String upReceiveConfig(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		ReceiveConfigInfoBean receiveConfigInfo=receiveQueryFacade.queryReceiveConfigBy(receiveConfigInfoBean.getOwnerId());
		if (receiveConfigInfo!=null) {
			receiveConfigInfo.setCustIp(receiveConfigInfoBean.getCustIp());
			receiveConfigInfo.setDomain(receiveConfigInfoBean.getDomain());
			receiveConfigInfo.setSingleBatchMaxNum(receiveConfigInfoBean.getSingleBatchMaxNum());
			receiveConfigInfo.setSingleMaxAmount(receiveConfigInfoBean.getSingleMaxAmount());
			receiveConfigInfo.setDailyMaxAmount(receiveConfigInfoBean.getDailyMaxAmount());
			receiveConfigInfo.setStatus(receiveConfigInfoBean.getStatus());
			receiveConfigInfo.setOper(auth.getUsername());
			receiveConfigInfo.setIsCheckContract(receiveConfigInfoBean.getIsCheckContract());
			receiveQueryFacade.updateReceiveConfig(receiveConfigInfo);
			msg="true";
		}else {
			msg="false";
		}
		return SUCCESS;
	}
	
	/**
	 * 用户代收配置查询
	 * @return
	 */
	public String receiveConfigFindById(){
		try {
			receiveConfigInfoBean=receiveQueryFacade.queryReceiveConfigBy(ownerId);
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 判断当前用户代收配置是否存在
	 * @return
	 */
	public String receiveConfigExists(){
		try {
			receiveConfigInfoBean=receiveQueryFacade.queryReceiveConfigBy(ownerId);
			if(receiveConfigInfoBean != null){
				msg = "true";
			}else {
				msg = "false";
			}
		} catch (Exception e) {
			msg ="true";
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据当前编号查询是否存在该用户
	 * @return
	 */
	public String receiveConfigFindByIdBool(){
		try {
			routeConfigBean = receiveQueryFacade.findByCode(routeConfigBean.getCode());
			if(routeConfigBean != null){
				msg = "true";
			}else {
				msg = "false";
			}
		} catch (Exception e) {
			msg = "true";
		}
		return SUCCESS;
	}
	
	/**
	 * 用户代收配置查询
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String receiveConfigQuery(){
		try {
			String queryId = "receiveConfigQuery";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = recfFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}

	/**
	 * 代收订单查询
	 * @return
	 */
	public String receiveOrderQuery(){
		try {
			String queryId = "receiveOrderQuery";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = recfFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 代收订单合计查询
	 * @return
	 */
	public String receiveOrderSum(){
		try {
			String queryId = "receiveOrderSum";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = recfFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
			if(returnMap != null){
				msg = JsonUtils.toJsonString(returnMap.get("valueList"));
			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 代收订单Export
	 * @return
	 */
	public String receiveOrderExport(){
		try {
			String queryId = "receiveOrderExport";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = recfFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String receiveConfigHistoryQuery(){
		try {
			String queryId = "receiveConfigHistoryQuery";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = recfFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}

	/**
	 * 参数转换
	 */
	private Map<String, Object> retrieveParams(Map<String, String[]> requestMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (String key : requestMap.keySet()) {
			String[] value = requestMap.get(key);
			if (value != null) {
				resultMap.put(key, Array.get(value, 0).toString().trim());
			}
		}
		return resultMap;
	}

	// 代收配置新增
	public String receiveConfigAdd() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		try {
			receiveConfigInfo.setCreateTime(new Date());
			receiveConfigInfo.setOper(auth.getUsername());
			receiveQueryFacade.insertReceiveConfig(receiveConfigInfo);
			//receiveQueryFacade.updateReceiveConfig(receiveConfigInfo);
			msg="ture";
		} catch (Exception e) {
			logger.error("系统异常:", e);
			msg="false";
		}
		return SUCCESS;
	}
	
	/**
	 * 代收路由查询
	 * @return
	 */
	public String receiveRouteQuery(){
		try {
			String queryId = "routeConfig";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = recfFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 代收路由新增
	 * @return
	 */
	public String receiveRouteAdd(){
		try {
			Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
			receiveQueryFacade.createRoute(routeConfigBean, auth.getRealname());
			getHttpRequest().setAttribute("success", "true");
		} catch (Exception e) {
			getHttpRequest().setAttribute("success", "false");
		}
		return SUCCESS;
	}
	
	/**
	 * 根据编号查询单条路由信息
	 * @return
	 */
	public String receiveRouteQueryByCode(){
		routeConfigBean = receiveQueryFacade.findByCode(routeConfigBean.getCode());
		try {
			queryDictionaryRangedList();
		} catch (Exception e) {
			
		}
		if(getHttpRequest().getParameter("operate").equals("update")){
			return "update";
		}else{
			return "detail";
		}
	}
	
	/**
	 * 代收路由修改
	 * @return
	 */
	public String receiveRouteUpdate(){
		try {
			Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
			receiveQueryFacade.updateRoute(routeConfigBean, auth.getRealname());
			getHttpRequest().setAttribute("success", "true");
		} catch (Exception e) {
			getHttpRequest().setAttribute("success", "false");
		}
		return SUCCESS;
	}
	
	/**
	 * 查询所有支持支付类型
	 * @return
	 */
	public String receiveRouteQueryDictionary(){
		queryDictionaryRangedList();
		return SUCCESS;
	}
	
	/**
	 * 查询所有支持支付类型
	 * @return
	 */
	public void queryDictionaryRangedList(){
		com.lefu.commons.cache.bean.DictionaryType dictionaryTypeRanged = new DictionaryType();
		//获取所有支持银行类型
		dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + Constant.INTERFACE_PROVIDER);
		List<com.lefu.commons.cache.bean.Dictionary> bankCodeList = dictionaryTypeRanged.getDictionaries();
		
		//获取所有支持账户类型
		dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + Constant.ACCOUNT_TYPE);
		List<com.lefu.commons.cache.bean.Dictionary> accountTypeList = dictionaryTypeRanged.getDictionaries();
		
		//获取所有支持卡类型
		dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + "RECEIVE_CARD_TYPE");
		List<com.lefu.commons.cache.bean.Dictionary> cardTypeList = dictionaryTypeRanged.getDictionaries();
		
		//认证类型
		dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + Constant.CER_TYPE);
		List<com.lefu.commons.cache.bean.Dictionary> cerTypeList = dictionaryTypeRanged.getDictionaries();
		
		supportPayList = new HashMap<String,Object>();
		supportPayList.put("bankCodeList",bankCodeList);
		supportPayList.put("accountTypeList", accountTypeList);
		supportPayList.put("cardTypeList", cardTypeList);
		supportPayList.put("cerTypeList", cerTypeList);
	}
	
	
	
	

	public ReceiveFacade getRecfFacade() {
		return recfFacade;
	}

	public void setRecfFacade(ReceiveFacade recfFacade) {
		this.recfFacade = recfFacade;
	}

	public ReceiveConfigInfoBean getReceiveConfigInfoBean() {
		return receiveConfigInfoBean;
	}

	public void setReceiveConfigInfoBean(ReceiveConfigInfoBean receiveConfigInfoBean) {
		this.receiveConfigInfoBean = receiveConfigInfoBean;
	}

	public ReceiveQueryFacade getReceiveQueryFacade() {
		return receiveQueryFacade;
	}

	public void setReceiveQueryFacade(ReceiveQueryFacade receiveQueryFacade) {
		this.receiveQueryFacade = receiveQueryFacade;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public RouteConfigBean getRouteConfigBean() {
		return routeConfigBean;
	}

	public void setRouteConfigBean(RouteConfigBean routeConfigBean) {
		this.routeConfigBean = routeConfigBean;
	}

	public RouteInfoBean getRouteInfoBean() {
		return routeInfoBean;
	}

	public void setRouteInfoBean(RouteInfoBean routeInfoBean) {
		this.routeInfoBean = routeInfoBean;
	}

	public List<RouteConfigBean> getRouteConfigBeanList() {
		return routeConfigBeanList;
	}

	public void setRouteConfigBeanList(List<RouteConfigBean> routeConfigBeanList) {
		this.routeConfigBeanList = routeConfigBeanList;
	}

	public Map<String, Object> getSupportPayList() {
		return supportPayList;
	}

	public void setSupportPayList(Map<String, Object> supportPayList) {
		this.supportPayList = supportPayList;
	}
}
