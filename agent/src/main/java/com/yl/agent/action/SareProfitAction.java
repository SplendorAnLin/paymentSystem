package com.yl.agent.action;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yl.agent.Constant;
import com.yl.agent.entity.Authorization;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.dpay.hessian.service.QueryFacade;

import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueListInfo;

/**
 * 分润控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月28日
 * @version V1.0.0
 */
public class SareProfitAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -6068631231063557751L;
	private ShareProfitInterface shareProfitInterface;
	private String msg;

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getSpInfo() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		params.put("agent_no", auth.getAgentNo());
		try {
			String queryId = "shareProfit";
			Map<String, Object> returnMap = shareProfitInterface.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}

	public String getSpExport() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		params.put("agent_no", auth.getAgentNo());
		try {
			String queryId = "spExport";
			Map<String, Object> returnMap = shareProfitInterface.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}

	/**
	 * 查看合计
	 * 
	 * @return
	 */
	public String shareProfitCount() {
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		params.put("agent_no", auth.getAgentNo());
		msg = shareProfitInterface.findByMapShareProfitInterfaces(params);
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

	public ShareProfitInterface getShareProfitInterface() {
		return shareProfitInterface;
	}

	public void setShareProfitInterface(ShareProfitInterface shareProfitInterface) {
		this.shareProfitInterface = shareProfitInterface;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
