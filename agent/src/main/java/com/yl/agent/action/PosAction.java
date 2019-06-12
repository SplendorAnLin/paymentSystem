package com.yl.agent.action;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.agent.Constant;
import com.yl.agent.entity.Authorization;
import com.yl.agent.utils.ToolUtils;
import com.yl.boss.api.bean.Agent;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.PosInterface;
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
	
	private Map<String, Object> order;
	
	private String msg;
	
	@Resource
	private AgentInterface agentInterface;
	
	@Resource
	private PosInterface posInterface;
	
	@Resource
	private CustomerInterface customerInterface;
	
	private Page page;
	
	/**
	 * 查询POS订单
	 * @return
	 */
	public String posOrderQuery(){
		String queryId="orderQuery";
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		params.put("agent_no", auth.getAgentNo());
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
	 *	查询当前代理商旗下所有商户的Pos订单合计
	 * @return
	 */
	public String posOrderSumAction(){
//		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
//		List<String> list = agentInterface.findAllCustomerNoByAgentNo(auth.getAgentNo());
//		msg = JsonUtils.toJsonString(posOrder.posOrderSum("agent",JsonUtils.toJsonString(list).replace("[","").replace("]",""),retrieveParams(getHttpRequest().getParameterMap())));
//		return SUCCESS;
		
		String queryId="orderTotalQuery";
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		params.put("agent_no", auth.getAgentNo());
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
	 * 查询POS订单Export
	 * @return
	 */
	public String posOrderQueryExport(){
		String queryId="posOrderQueryExport";
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		params.put("agent_no", auth.getAgentNo());
		Map<String, Object> returnMap = vlhQuery.query(queryId, params);
		getHttpRequest().setAttribute(queryId,
				new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
						(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		return SUCCESS;
	}
	
	/**
	 * pos查询
	 * @return
	 */
	public String posQuery(){
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		String queryId = "posInfo";
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Agent agent = agentInterface.getAgent(auth.getAgentNo());
		String agent_no_left = null;
		if(agent.getAgentLevel() == 2){
			agent_no_left = agent.getParenId() + "," + agent.getAgentNo();
		}else if(agent.getAgentLevel() == 3){
			Agent agentParen = agentInterface.getAgent(agent.getParenId());
			agent_no_left = agentParen.getParenId() + "," + agentParen.getAgentNo() + "," + agent.getAgentNo();
		}else {
			agent_no_left = agent.getAgentNo();
		}
		if(params.get("agent_no") != null && !params.get("agent_no").equals("")){
			params.put("agent_no", agent.getAgentNo() + "," + params.put("agent_no", "agent_no"));
		}else {
			params.remove("agent_no");
		}
		params.put("agent_no_left", agent_no_left);
		Map<String, Object> posInfo = posInterface.posQuery(params);
		getHttpRequest().setAttribute(queryId,
				new DefaultListBackedValueList((List) posInfo.get(QueryFacade.VALUELIST),
						(ValueListInfo) posInfo.get(QueryFacade.VALUELIST_INFO)));
		return SUCCESS;
	}
	
	/**
	 * 查询POS分配
	 * @return
	 */
	public String posAssignOuter(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Agent agent=agentInterface.getAgent(auth.getAgentNo());
		getSession().setAttribute("agentLevel", agent.getAgentLevel());
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		String queryId = "posAssignOuter";
		if(agent.getAgentLevel() == 1){
			params.put("agent_no", auth.getAgentNo());
		}else if(agent.getAgentLevel() == 2){
			params.put("agent_no", agent.getParenId() + "," + auth.getAgentNo());
		}else {
			Agent agentParen = agentInterface.getAgent(agent.getParenId());
			params.put("agent_no", agentParen.getParenId() + "," + agentParen.getAgentNo() + "," + auth.getAgentNo());
		}
		Map<String, Object> posAssignOuter = posInterface.posAssignOuter(params);
		getHttpRequest().setAttribute(queryId,
				new DefaultListBackedValueList((List) posAssignOuter.get(QueryFacade.VALUELIST),
						(ValueListInfo) posAssignOuter.get(QueryFacade.VALUELIST_INFO)));
		return SUCCESS;
	}
	
	/**
	 * POS批量出库
	 * @return
	 */
	public String posBatchDelivery(){
		String[] posCatiArrays = getHttpRequest().getParameter("posCatiArrays").split(",");
		if(posCatiArrays != null && posCatiArrays.length > 0){
			
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			
			String agentNo = auth.getAgentNo();
			
			String agentChildNo = getHttpRequest().getParameter("agentNo");
			
			if(agentChildNo != null && !"".equals(agentChildNo)){
				
				try {
					posInterface.posBatchDeliveryOuter(posCatiArrays, agentNo, agentChildNo, auth.getUsername());
					msg = "true";
				} catch (Exception e) {
					msg = "false";
					logger.error("posBatchDelivery failed!",e);
				}
				
			}else {
				msg = "agentNo null";
			}
			
		}else {
			msg = "posCatiArrays null";
		}
		return SUCCESS;
	}
	
	/**
	 * POS绑定
	 * @return
	 */
	public String posBind(){
		String[] posCatiArrays = getHttpRequest().getParameter("posCatiArrays").split(",");
		if(posCatiArrays != null && posCatiArrays.length > 0){

			String customerNo = getHttpRequest().getParameter("customerNo");

			Customer customer = customerInterface.getCustomer(customerNo);
			
			if(customerNo != null && !"".equals(customerNo) && customer.getCustomerNo().equals(customerNo)){

				try {
					Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
					posInterface.posBind(posCatiArrays, customerNo, auth.getUsername());
					msg = "true";
				} catch (Exception e) {
					msg = "false";
					logger.error("posBind failed!",e);
				}

			}else {
				msg = "false";
			}

		}else {
			msg = "false";
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

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
