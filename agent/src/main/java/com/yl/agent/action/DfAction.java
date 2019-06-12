package com.yl.agent.action;

import java.util.HashMap;
import java.util.Map;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.enums.UserRole;
import com.yl.agent.Constant;
import com.yl.agent.entity.Authorization;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.service.ServiceConfigFacade;

/**
 * 代付控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月26日
 * @version V1.0.0
 */
public class DfAction extends Struts2ActionSupport {
	private static final long serialVersionUID = 1L;
	private ServiceConfigFacade serviceConfigFacade;
	private String msg;
	
	/**
	 * 根据账户查询用户代付配置
	 * @return
	 */
	public String findDfConfigById(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		ServiceConfigBean serviceConfigBean = serviceConfigFacade.findServiceConfigById(auth.getAgentNo());
		Map<String,Object> sm = new HashMap<String,Object>();
		sm.put("maxAmount", serviceConfigBean.getMaxAmount());
		sm.put("minAmount", serviceConfigBean.getMinAmount());
		msg = JsonUtils.toJsonString(sm);
		return SUCCESS;
	}
	
	public ServiceConfigFacade getServiceConfigFacade() {
		return serviceConfigFacade;
	}
	public void setServiceConfigFacade(ServiceConfigFacade serviceConfigFacade) {
		this.serviceConfigFacade = serviceConfigFacade;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
