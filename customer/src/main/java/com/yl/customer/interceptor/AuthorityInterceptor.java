package com.yl.customer.interceptor;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.pay.common.util.PropertyUtil;
import com.yl.customer.Constant;
import com.yl.customer.entity.Authorization;
import com.yl.customer.service.OperatorLoginService;

/**
 * 权限检查拦截器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月18日
 * @version V1.0.0
 */
public class AuthorityInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1673914883471764904L;
	private static final Logger logger = Logger.getLogger(AuthorityInterceptor.class);
	private OperatorLoginService operatorLoginService;

	@SuppressWarnings({ "unchecked", "unused" })
	public String intercept(ActionInvocation invocation) throws Exception {

		ActionContext ctx = invocation.getInvocationContext();
		@SuppressWarnings("rawtypes")
		Map session = ctx.getSession();
		String actionName = ctx.getName();

		// 全局权限开关检查
		PropertyUtil propertyUtil = PropertyUtil.getInstance(Constant.PROPERTIES_SYS);
		String authFlag = propertyUtil.getProperty("system.auth.flag").toUpperCase();
		String permitList = propertyUtil.getProperty("system.auth.permitlist");
		HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
		String serviceIndex = propertyUtil.getProperty("moble.service.index");
		
		if (serviceIndex.indexOf(actionName) > -1) {
			return invocation.invoke();
		}
		
		if (permitList.indexOf(actionName + ",") > -1) {
			return invocation.invoke();
		}
		
		Authorization auth = (Authorization) session.get(Constant.SESSION_AUTH);
		
		if (auth == null) {
			return "login";
		}
		
		// 全局权限开关检查
		if (Constant.BOOLEAN_FALSE.equals(authFlag)) {
			return invocation.invoke();
		}

		// 功能权限检查
		@SuppressWarnings("rawtypes")
		Set resource = auth.getResources();
		actionName += ".action";

		if (!resource.contains(actionName)) {
			return "noPermit";
		}

		return invocation.invoke();
	}

	public void setOperatorLoginService(OperatorLoginService operatorLoginService) {
		this.operatorLoginService = operatorLoginService;
	}

}
