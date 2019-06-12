package com.yl.agent.action;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.dpay.hessian.service.QueryFacade;
import com.yl.receive.hessian.ReceiveFacade;

import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueListInfo;

/**
 * 代收控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月28日
 * @version V1.0.0
 */
public class ReceiveAction  extends Struts2ActionSupport{
	private static final long serialVersionUID = 8995432988301364380L;
	private ReceiveFacade recfFacade;
	private String msg;
	
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

	public ReceiveFacade getRecfFacade() {
		return recfFacade;
	}

	public void setRecfFacade(ReceiveFacade recfFacade) {
		this.recfFacade = recfFacade;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
