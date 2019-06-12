package com.yl.boss.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.Constant;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Pos;
import com.yl.boss.service.AgentService;
import com.yl.boss.service.CustomerService;
import com.yl.boss.service.PosService;
import com.yl.boss.service.PosSynchronizationService;
import com.yl.boss.service.ShopService;
import com.yl.boss.utils.Base64Utils;
import com.yl.boss.utils.MD5;
import com.yl.dpay.hessian.service.QueryFacade;
import com.yl.pay.pos.api.interfaces.PosOrderHessianService;
import com.yl.pay.pos.api.interfaces.VlhQuery;

import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueListInfo;
import net.sf.json.JSONObject;

/**
 * POS终端控制器
 *
 * @author 聚合支付有限公司
 * @since 2017年7月10日
 * @version V1.0.0
 */
public class PosAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(PosAction.class);
	
	@Resource
	private PosService posService;
	@Resource
	private CustomerService customerService;
	@Resource
	private ShopService shopService;
	@Resource
	private VlhQuery vlhQuery;
	@Resource
	PosOrderHessianService posOrder;
	
	@Resource
	AgentService agentService;
	
	@Resource
	PosSynchronizationService posSynchronizationService;
	
	private Map<String, Object> order;
	
	private Pos pos;
	
	private String msg;
	
	/**
	 * pos请求记录查询
	 * @return
	 */
	public String posRequestQuery() {
		try {
			String queryId="requestQuery";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			Map<String, Object> returnMap = vlhQuery.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询POS订单
	 * @return
	 */
	public String posOrderQuery(){
		String queryId="orderQuery";
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		Map<String, Object> returnMap = vlhQuery.query(queryId, params);
		getHttpRequest().setAttribute(queryId,
				new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
						(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
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
	 * Pos终端信息入库
	 * @return
	 */
	public String posAdd(){
		
		try {
			if(pos != null){
				Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
				pos.setOperator(auth.getRealname());
				
				int length=pos.getBatchNo().length();
				String temp="";
				if (length<6) {
					for (int i = 0; i < 6-length; i++) {
						temp+="0";
					}
				}
				pos.setBatchNo(temp+pos.getBatchNo());//补齐6位
				
				pos.setPosSn(String.valueOf(Integer.parseInt(pos.getPosSn())));
				
				if(pos.getPosSn().length() < 8){
					while(8 - pos.getPosSn().length() != 0){
						pos.setPosSn(pos.getPosSn() + 0);
					}
				}
				
				int posSnNumber = Integer.parseInt(getHttpRequest().getParameter("posSnNumber"));
				
				msg = posService.posAllAdd(pos, posSnNumber, auth.getUsername());
			}else {
				logger.info("POS终端信息新增，请求参数不全");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 根据PosId查询单条Pos终端信息
	 * @return
	 */
	public String posById(){
		pos = posService.posById(pos.getId());
		return SUCCESS;
	}
	
	/**
	 * 修改单条Pos终端信息
	 * @return
	 */
	public String posUpdate(){
		try {
//			int length=pos.getBatchNo().length();
//			String temp="";
//			if (length<6) {
//				for (int i = 0; i < 6-length; i++) {
//					temp+="0";
//				}
//			}
//			pos.setBatchNo(temp+pos.getBatchNo());//补齐6位
			posService.posUpdate(pos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询POS订单合计
	 * @return
	 */
	public String posOrderSumAction(){
//		String agent_no = (String) retrieveParams(getHttpRequest().getParameterMap()).get("agent_no");
//		String no = null;
//		if(agent_no != null && !"".equals(agent_no)){
//			no = JsonUtils.toJsonString(agentService.findAllCustomerNoByAgentNo(agent_no)).replace("[","").replace("]","");
//		}
//		msg = JsonUtils.toJsonString(posOrder.posOrderSum("boss",no,retrieveParams(getHttpRequest().getParameterMap())));
//		return SUCCESS;
		
		
		String queryId="orderTotalQuery";
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
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
		
		msg = JsonUtils.toJsonString(returnMap);
		return SUCCESS;
		
	}
	
	/**
	 * POS批量出库
	 * @return
	 */
	public String posBatchDelivery(){
		String[] posCatiArrays = getHttpRequest().getParameter("posCatiArrays").split(",");
		if(posCatiArrays != null && posCatiArrays.length > 0){
			
			String agentNo = getHttpRequest().getParameter("agentNo");
			
			if(agentNo != null && !"".equals(agentNo)){
				
				try {
					Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
					posService.posBatchDelivery(posCatiArrays, agentNo, auth.getUsername());
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

			if(customerNo != null && !"".equals(customerNo)){

				try {
					Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
					posService.posBind(posCatiArrays, customerNo, auth.getUsername());
					msg = "true";
				} catch (Exception e) {
					msg = "false";
					logger.error("posBind failed!",e);
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
	 * POS信息同步
	 */
	public void posSynchronize(){
		
		BufferedReader br;
		StringBuilder sb = null;
		Map params = null;
		try {
			br = new BufferedReader(new InputStreamReader(getHttpRequest().getInputStream()));
			String line = null;
			sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			if (StringUtils.isBlank(sb)){
				//参数错误
				return;
			}
			
			params= JsonUtils.toJsonToObject(JSONObject.fromObject(new String(Base64Utils.decode(sb.toString()),"UTF-8")), Map.class);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		Map<String, Object> paramsData = new HashMap<String, Object>();
		
		try {
			
			if(params != null && !params.equals("")){
				
				JSONObject  jasonObject = JSONObject.fromObject(params);
				paramsData = (Map)jasonObject;
				
				
				String str = params.get("customerNo").toString() + params.get("createDate").toString();
				
				String md5String = MD5.MD5Encode(str.toString().substring(str.toString().length()/2,str.toString().length()));
				
				Boolean respResult = md5String.equals(paramsData.get("MD5"));
				
				if(respResult){
					
					try {
						
						paramsData = new HashMap<String, Object>();
						paramsData.put("responseCode", "000000");
//						paramsData.put("responseData", posService.findPosOemByCustomerNo((String) params.get("customerNo")));
						List list = posSynchronizationService.query((String) params.get("customerNo"));
//						if(list != null){
//							List<String> posCatis = new ArrayList<String>();
//							for (int i = 0; i < list.size(); i++) {
//								posCatis.add(JsonUtils.toJsonToObject(list.get(i), Object[].class)[0].toString());
//							}
//							
//							posSynchronizationService.updateStatusByPosCati(posCatis);
//							
//						}
//						
						paramsData.put("responseData", list);
						
						getHttpResponse().getWriter().write(JsonUtils.toJsonString(paramsData));

					} catch (Exception e) {
						
						paramsData = new HashMap<String, Object>();
						paramsData.put("responseCode", "999999");
						paramsData.put("responseData", "");
						
						getHttpResponse().getWriter().write(JsonUtils.toJsonString(paramsData));
						
					}
					
				}else {
					
					//MD5对比失败
					paramsData = new HashMap<String, Object>();
					paramsData.put("responseCode", "000001");
					paramsData.put("responseData", "");
					
					getHttpResponse().getWriter().write(JsonUtils.toJsonString(paramsData));
					
				}
				
			}
			
		} catch (IOException e1) {
			paramsData = new HashMap<String, Object>();
			paramsData.put("responseCode", "999999");
			paramsData.put("responseData", "");
			
			try {
				getHttpResponse().getWriter().write(JsonUtils.toJsonString(paramsData));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			e1.printStackTrace();
		}
		
		
	}
	
	public void posSynchronizeNotice(){
		
		BufferedReader br;
		StringBuilder sb = null;
		Map params = null;
		try {
			br = new BufferedReader(new InputStreamReader(getHttpRequest().getInputStream()));
			String line = null;
			sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			if (StringUtils.isBlank(sb)){
				//参数错误
				return;
			}
			
			params= JsonUtils.toJsonToObject(JSONObject.fromObject(new String(Base64Utils.decode(sb.toString()),"UTF-8")), Map.class);
			
			Map<String, Object> paramsData = new HashMap<String, Object>();
			
			if(params != null && !params.equals("")){
				
				JSONObject  jasonObject = JSONObject.fromObject(params);
				paramsData = (Map)jasonObject;
				
				
				String str = params.get("customerNo").toString() + params.get("createDate").toString();
				
				String md5String = MD5.MD5Encode(str.toString().substring(str.toString().length()/2,str.toString().length()));
				
				Boolean respResult = md5String.equals(paramsData.get("MD5"));
				
				if(respResult){
					
					List<String> posCatis = (List<String>) params.get("posCatis");
					
					posSynchronizationService.updateStatusByPosCati(params.get("customerNo").toString(), posCatis);
					
				}
				
			}
		} catch (Exception e) {
			logger.error("posSynchronizeNotice failure! ", e);
		}
		
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
	
	public Pos getPos() {
		return pos;
	}

	public void setPos(Pos pos) {
		this.pos = pos;
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
