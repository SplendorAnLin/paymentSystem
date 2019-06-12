package com.yl.customer.action;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yl.customer.entity.Authorization;
import com.yl.dpay.hessian.service.QueryFacade;
import com.yl.receive.hessian.NameListQuery;
import com.yl.receive.hessian.bean.NameList;

import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueListInfo;

/**
 * 白名单管理控制器 
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月4日
 * @version V1.0.0
 */
public class NameListAction extends Struts2ActionSupport{
	private static final long serialVersionUID = 2907533337488730434L;
	private NameListQuery nameListQuery;
	private NameList nameList;
	
	
	/**
	 * 导出白名单
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String Export(){
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			String queryId = "nameListRequestExport";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("ownerId", auth.getCustomerno());
			Map<String, Object> returnMap = nameListQuery.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询白名单所有信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String queryNameList(){
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			String queryId = "nameListRequest";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("ownerId", auth.getCustomerno());
			Map<String, Object> returnMap = nameListQuery.query(queryId, params);
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


	public NameListQuery getNameListQuery() {
		return nameListQuery;
	}

	public void setNameListQuery(NameListQuery nameListQuery) {
		this.nameListQuery = nameListQuery;
	}

	public NameList getNameList() {
		return nameList;
	}

	public void setNameList(NameList nameList) {
		this.nameList = nameList;
	}
}