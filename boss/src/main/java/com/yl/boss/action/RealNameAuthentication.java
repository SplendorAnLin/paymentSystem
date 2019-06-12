package com.yl.boss.action;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.service.CustomerService;
import com.yl.boss.utils.CodeBuilder;
import com.yl.realAuth.hessian.AuthConfigHessianService;
import com.yl.realAuth.hessian.BindCardInfoHessianService;
import com.yl.realAuth.hessian.RealNameAuthOrderHessianService;
import com.yl.realAuth.hessian.RoutingTemplateHessianService;
import com.yl.realAuth.hessian.bean.AuthConfigBean;
import com.yl.realAuth.hessian.bean.BindCardInfoBean;
import com.yl.realAuth.hessian.bean.RealNameAuthOrderBean;
import com.yl.realAuth.hessian.enums.AuthBusiType;
import com.yl.realAuth.hessian.enums.AuthConfigStatus;
import com.yl.realAuth.hessian.enums.AuthOrderStatus;
import com.yl.realAuth.hessian.enums.AuthResult;

public class RealNameAuthentication extends Struts2ActionSupport {

	private static final long serialVersionUID = -5923442157911997356L;
	
	private AuthConfigBean authConfigBean;
	private List<AuthConfigBean> auth=new ArrayList<AuthConfigBean>();
	private List<BindCardInfoBean> bind=new ArrayList<BindCardInfoBean>();
	private List<RealNameAuthOrderBean> real=new ArrayList<RealNameAuthOrderBean>();
	private RealNameAuthOrderHessianService realNameAuthOrderHessianService;
	private BindCardInfoHessianService bindCardInfoHessianService;
	private Page page;
	private AuthConfigHessianService authConfigHessianService;
	private RoutingTemplateHessianService routingTemplateHessianService;
	List<Map> info;
	Map<String, Object> params = new HashMap<String, Object>();
	
	@Resource
	private CustomerService customerService;
	
	private String msg;
	
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
	 * 加载实名认证开通
	 * @return
	 */
	public String findAuthConfig() {
		int currentPage = this.getHttpRequest().getParameter("currentPage") == null ? 1 : Integer.parseInt(this.getHttpRequest().getParameter("currentPage"));
		if (page == null) {
			page = new Page();
		}
		page.setCurrentPage(currentPage);
		try {
			page = authConfigHessianService.findAllAuthConfigBean(retrieveParams(getHttpRequest().getParameterMap()), page);
		} catch (Exception e) {
			logger.error("动态查询实名认证开通失败，错误原因{}",e);
		}
		return SUCCESS;
	}
	/**
	 * 添加实名认证开通
	 * @return
	 */
	public String addAuthConfig() {
		try {
			String code = null;
			do {
				code = CodeBuilder.buildNumber(8);
			} while (authConfigHessianService.queryAuthConfigBoolByCode(code));
			authConfigBean.setCode(code);
			authConfigBean.setCreateTime(new Date());
			authConfigBean.setStatus(AuthConfigStatus.TRUE);
			authConfigBean.setLastUpdateTime(new Date());
			authConfigHessianService.createAuthConfig(authConfigBean);
		} catch (Exception e) {
			logger.error("添加实名认证开通失败，错误原因{}",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据商户编号和业务类型查询实名认证信息
	 * @return
	 */
	public String findAuthConfigBeanByCustomerNoAndBusiType(){
		info=authConfigHessianService.findRoutingTemplate();
		authConfigBean = authConfigHessianService.findAuthConfigBeanByCustomerNoAndBusiType(authConfigBean.getCustomerNo(), authConfigBean.getBusiType().name());
		info = routingTemplateHessianService.findAllTemplate();
		return SUCCESS;
	}

	/**
	 * 修改实名认证开通
	 * @return
	 */
	public String updateAuthConfig() {
		try {
			authConfigHessianService.updateAuthConfig(authConfigBean);
		} catch (Exception e) {
			logger.error("添加实名认证开通失败，错误原因{}",e);
		}
		return SUCCESS;
	}

	/***
	 * 动态查询绑定卡信息
	 * 
	 * @return
	 */
	public String dynamicBindCarInfo() {
		int currentPage = this.getHttpRequest().getParameter("currentPage") == null ? 1 : Integer.parseInt(this.getHttpRequest().getParameter("currentPage"));
		if (page == null) {
			page = new Page();
		}
		page.setCurrentPage(currentPage);
		try {
			page=bindCardInfoHessianService.findAlldynamicBindCardInfoBean(retrieveParams(getHttpRequest().getParameterMap()),page);
		} catch (Exception e) {
			logger.error("添加实名认证开通失败，错误原因{}",e);
		}
		
		return SUCCESS;
	}

	/**
	 * 动态查询实名认证订单页面
	 * 
	 * @return
	 */
	public String dynamicRealNameAuthOrder() {
		int currentPage = this.getHttpRequest().getParameter("currentPage") == null ? 1 : Integer.parseInt(this.getHttpRequest().getParameter("currentPage"));
		if (page == null) {
			page = new Page();
		}
		page.setCurrentPage(currentPage);
		try {
			page=realNameAuthOrderHessianService.findAlldynamicRealNameAuthOrder(retrieveParams(getHttpRequest().getParameterMap()),page);
		} catch (Exception e) {
			logger.error("添加实名认证开通失败，错误原因{}",e);
		}
		
		return SUCCESS;
	}
	/**
	 * 查询所有路由模版
	 * @return
	 */
	public String findAllTemplate(){
		info=authConfigHessianService.findRoutingTemplate();
		return SUCCESS;
	}

	public String authQueryCustomerNoBool(){
		Map<String,Object> result = new HashMap<>();
		String fullName = customerService.findShortNameByCustomerNo(authConfigBean.getCustomerNo());
		if(fullName != null && !"".equals(fullName)){
			result.put("fullName", fullName);
			String status = authConfigHessianService.queryAuthConfigByCustomerNoAndBusiType(authConfigBean.getCustomerNo(),authConfigBean.getBusiType().name());
			if(status != null && !"".equals(status)){
				result.put("status", status);
			}else {
				result.put("status", "");
			}
			msg = JsonUtils.toJsonString(result);
		}else {
			msg = "false";
		}
		
		return SUCCESS;
	}


	public AuthConfigHessianService getAuthConfigHessianService() {
		return authConfigHessianService;
	}

	public void setAuthConfigHessianService(AuthConfigHessianService authConfigHessianService) {
		this.authConfigHessianService = authConfigHessianService;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public RealNameAuthOrderHessianService getRealNameAuthOrderHessianService() {
		return realNameAuthOrderHessianService;
	}

	public void setRealNameAuthOrderHessianService(RealNameAuthOrderHessianService realNameAuthOrderHessianService) {
		this.realNameAuthOrderHessianService = realNameAuthOrderHessianService;
	}

	public BindCardInfoHessianService getBindCardInfoHessianService() {
		return bindCardInfoHessianService;
	}

	public void setBindCardInfoHessianService(BindCardInfoHessianService bindCardInfoHessianService) {
		this.bindCardInfoHessianService = bindCardInfoHessianService;
	}

	public List<AuthConfigBean> getAuth() {
		return auth;
	}
	public void setAuth(List<AuthConfigBean> auth) {
		this.auth = auth;
	}
	public List<BindCardInfoBean> getBind() {
		return bind;
	}
	public void setBind(List<BindCardInfoBean> bind) {
		this.bind = bind;
	}
	public List<RealNameAuthOrderBean> getReal() {
		return real;
	}
	public void setReal(List<RealNameAuthOrderBean> real) {
		this.real = real;
	}

	public AuthConfigBean getAuthConfigBean() {
		return authConfigBean;
	}

	public void setAuthConfigBean(AuthConfigBean authConfigBean) {
		this.authConfigBean = authConfigBean;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Map> getInfo() {
		return info;
	}

	public void setInfo(List<Map> info) {
		this.info = info;
	}

	public RoutingTemplateHessianService getRoutingTemplateHessianService() {
		return routingTemplateHessianService;
	}

	public void setRoutingTemplateHessianService(RoutingTemplateHessianService routingTemplateHessianService) {
		this.routingTemplateHessianService = routingTemplateHessianService;
	}
}