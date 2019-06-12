package com.yl.customer.action;

import java.util.Date;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yl.customer.Constant;
import com.yl.customer.entity.Authorization;
import com.yl.dpay.hessian.beans.PayeeBean;
import com.yl.dpay.hessian.service.QueryFacade;
import com.yl.dpay.hessian.service.ServiceConfigFacade;

import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueListInfo;

/**
 * 收款人控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class PayeeAction extends Struts2ActionSupport{
	private static final long serialVersionUID = -1133382486234784057L;
	private PayeeBean payee;
	private ServiceConfigFacade serviceConfigFacade;
	private QueryFacade queryFacade;
	private String msg;
	
		
	public String getPayeeById(){
		String sid=getHttpRequest().getParameter("id");
		if (sid!=null&&!sid.equals("")) {
			payee=serviceConfigFacade.findById(Long.parseLong(sid));
		}
		return SUCCESS;
	}
	
	
	public String payeeAdd(){
		payee.setCreateDate(new Date());
		Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
		String customerNo = auth.getCustomerno();
		payee.setOwnerId(customerNo);
		serviceConfigFacade.create(payee);
		return SUCCESS;
	}
	
	public String payeeUpdate() {
		serviceConfigFacade.updatePayeeBean(payee);
		return SUCCESS;
	}
	public PayeeBean getPayee() {
		return payee;
	}

	public void setPayee(PayeeBean payee) {
		this.payee = payee;
	}
	
	/**
	 * 查询收款人信息
	 * @return
	 */
	public String dfQueryPayee(){
		try {
			String queryId = "dpayQueryRecipientResult";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
			String customerNo = auth.getCustomerno();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("customer_no", customerNo);
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
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
	 * 根据id删除收款人信息
	 * @return
	 */
	public String deleteByPayeeId(){
		try {
			serviceConfigFacade.delete(Integer.parseInt(getHttpRequest().getParameter("id").toString()));
			msg = "true";
		} catch (Exception e) {
			msg = "false";
		}
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * @return
	 */
	public String deleteAllByPayeeId(){
		try {
			String idsUnicode = getHttpRequest().getParameter("ids");
			String[] s = idsUnicode.split(",");
			int[] ids = new int[s.length];
			for (int i = 0; i < s.length; i++) {
				ids[i] = Integer.parseInt(s[i]);
			}
			serviceConfigFacade.deleteAll(ids);
			msg = "true";
		} catch (Exception e) {
			msg = "false";
		}
		return SUCCESS;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ServiceConfigFacade getServiceConfigFacade() {
		return serviceConfigFacade;
	}

	public void setServiceConfigFacade(ServiceConfigFacade serviceConfigFacade) {
		this.serviceConfigFacade = serviceConfigFacade;
	}

	public QueryFacade getQueryFacade() {
		return queryFacade;
	}

	public void setQueryFacade(QueryFacade queryFacade) {
		this.queryFacade = queryFacade;
	}
}
