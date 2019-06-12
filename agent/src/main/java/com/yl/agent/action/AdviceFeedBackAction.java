package com.yl.agent.action;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yl.agent.Constant;
import com.yl.agent.entity.AdviceFeedBack;
import com.yl.agent.entity.Authorization;
import com.yl.agent.service.AdviceFeedBackService;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.dpay.hessian.service.QueryFacade;
import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueListInfo;

/**
 * 意见反馈控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月23日
 * @version V1.0.0
 */
public class AdviceFeedBackAction extends Struts2ActionSupport{
	
	private static final long serialVersionUID = 7428686379238225080L;
	private AdviceFeedBackService adviceFeedBackService;
	private AdviceFeedBack adviceFeedBack;
	private ShareProfitInterface shareProfitInterface;
	
	public String feedBackQuery(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		params.put("agent_no", auth.getAgentNo());
		try {
			String queryId = "userFeedBackQuery";
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
	
	/**
	 * 新增反馈意见
	 * @return
	 */
	public String addAdvice(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		adviceFeedBack.setCreateTime(new Date());
		adviceFeedBack.setInitiator(auth.getRealname());
		adviceFeedBack.setPhoneNo(auth.getUsername());
		adviceFeedBackService.create(adviceFeedBack);
		return SUCCESS;
	}
	
	public AdviceFeedBackService getAdviceFeedBackService() {
		return adviceFeedBackService;
	}
	
	public void setAdviceFeedBackService(AdviceFeedBackService adviceFeedBackService) {
		this.adviceFeedBackService = adviceFeedBackService;
	}
	
	public AdviceFeedBack getAdviceFeedBack() {
		return adviceFeedBack;
	}
	
	public void setAdviceFeedBack(AdviceFeedBack adviceFeedBack) {
		this.adviceFeedBack = adviceFeedBack;
	}

	public ShareProfitInterface getShareProfitInterface() {
		return shareProfitInterface;
	}

	public void setShareProfitInterface(ShareProfitInterface shareProfitInterface) {
		this.shareProfitInterface = shareProfitInterface;
	}
}