package com.yl.customer.action;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.interfaces.PosInterface;
import com.yl.customer.entity.Authorization;
import com.yl.customer.utils.ToolUtils;
import com.yl.dpay.hessian.service.QueryFacade;
import com.yl.pay.pos.api.interfaces.PosOrderHessianService;
import com.yl.pay.pos.api.interfaces.VlhQuery;

import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueListInfo;

public class PosAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 1L;

	@Resource
	private VlhQuery vlhQuery;
	
	@Resource
	PosOrderHessianService posOrder;
	
	@Resource
	PosInterface posInterface;
	
	private Map<String, Object> order;
	
	private String msg;
	
	/**
	 * 查询POS订单
	 * @return
	 */
	public String posOrderQuery(){
		String queryId="orderQuery";
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		Authorization auth = (Authorization) getSession().getAttribute("auth");
		params.put("customer_no", auth.getCustomerno());
		Map<String, Object> returnMap = vlhQuery.query(queryId, params);
		getHttpRequest().setAttribute(queryId,
				new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
						(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		return SUCCESS;
	}
	
	/**
	 * 订单详情
	 * @return
	 */
	public String posOrderDetail(){
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		Object currentPage=params.get("currentPage");
		try {
			if (currentPage!=null&&!"".equals(currentPage)) {
				order=posOrder.findOrderById(Long.valueOf(params.get("id").toString()), currentPage.toString());
			}else {
				order=posOrder.findOrderById(Long.valueOf(params.get("id").toString()), "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询POS订单Export
	 * @return
	 */
	public String posOrderQueryExport(){
		String queryId="posOrderQueryExport";
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		Authorization auth = (Authorization) getSession().getAttribute("auth");
		params.put("customer_no", auth.getCustomerno());
		Map<String, Object> returnMap = vlhQuery.query(queryId, params);
		getHttpRequest().setAttribute(queryId,
				new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
						(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		return SUCCESS;
	}
	
	/**
	 *	查询当前商户的Pos订单合计
	 * @return
	 */
	public String posOrderSumAction(){
//		Authorization auth = (Authorization) getSession().getAttribute("auth");
//		msg = JsonUtils.toJsonString(posOrder.posOrderSum("customer",auth.getCustomerno(),retrieveParams(getHttpRequest().getParameterMap())));
//		return SUCCESS;
		
		
		String queryId="orderTotalQuery";
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		Authorization auth = (Authorization) getSession().getAttribute("auth");
		params.put("customer_no", auth.getCustomerno());
		Map<String, Object> returnMap = vlhQuery.query(queryId, params);
		getHttpRequest().setAttribute(queryId,new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
						(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		msg = JsonUtils.toJsonString(returnMap.get(QueryFacade.VALUELIST));
		
		returnMap = new HashMap<String, Object>();
		
		String[] result = msg.replace("\"", "").replace("[","").replace("]","").replace("{","").replace("}","").split(",");
		
		for (String s : result) {
			
			if(!s.split(":")[1].equals("null")){
				returnMap.put(s.split(":")[0], s.split(":")[1]);
			}else {
				returnMap.put(s.split(":")[0], 0);
			}
			
		}
		returnMap.remove("bc");
		
		msg = JsonUtils.toJsonString(returnMap);
		return SUCCESS;
		
	}
	
	/**
	 * Pos信息查询
	 * @return
	 */
	public String posQuery(){
		Map<String,Object> params = retrieveParams(getHttpRequest().getParameterMap());
		String queryId = "posInfo";
		Authorization auth = (Authorization) getSession().getAttribute("auth");
		params.put("customer_no", auth.getCustomerno());
		Map<String, Object> posInfo = posInterface.posQuery(params);
		getHttpRequest().setAttribute(queryId,
				new DefaultListBackedValueList((List) posInfo.get(QueryFacade.VALUELIST),
						(ValueListInfo) posInfo.get(QueryFacade.VALUELIST_INFO)));
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

	public Map<String, Object> getOrder() {
		return order;
	}

	public void setOrder(Map<String, Object> order) {
		this.order = order;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
