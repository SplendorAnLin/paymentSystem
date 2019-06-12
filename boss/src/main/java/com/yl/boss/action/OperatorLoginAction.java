package com.yl.boss.action;

import javax.servlet.http.HttpSession;

import com.lefu.commons.utils.web.InetUtils;
import com.octo.captcha.service.CaptchaServiceException;
import com.pay.common.util.StringUtil;
import com.yl.boss.Constant;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Role;
import com.yl.boss.exception.LoginException;
import com.yl.boss.service.OperatorLoginService;
import com.yl.boss.service.RoleService;
import com.yl.boss.utils.JCaptchaServiceSingleton;

/**
 * 操作员登录
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class OperatorLoginAction extends Struts2ActionSupport {
	private static final long serialVersionUID = 8482843308815814070L;
	private OperatorLoginService operatorLoginService;
	private Authorization auth;
	private String errorMsg;
	private String checkCode;
	private RoleService roleService;

	// 登录
	public String login() {

		HttpSession session = this.getSession();
		Authorization auth_session = (Authorization) session.getAttribute(Constant.SESSION_AUTH);
		if(auth_session != null){
			auth = auth_session;
			try {
				auth.setIpAddress(InetUtils.getRealIpAddr(this.getHttpRequest()));
				auth.setSessionId(session.getId());
				auth = operatorLoginService.login(auth.getUsername(), auth.getPassword(), auth.getIpAddress(), session.getId());
			} catch (LoginException e) {
				errorMsg = e.getMessage();
				return INPUT;
			}
			getSession().setAttribute(Constant.SESSION_AUTH, auth);
			return SUCCESS;
		}
		
		if(auth == null || StringUtil.isNull(auth.getUsername()) || StringUtil.isNull(auth.getPassword())){
			errorMsg = "请输入用户名密码！";
			return INPUT;
		}
		
		String sessID = session.getId(); 
		try {
			Boolean isResponseCorrect = JCaptchaServiceSingleton.getInstance().validateResponseForID(sessID, checkCode); 
			if(!isResponseCorrect){
				errorMsg = "验证码错误，请重新输入验证码！";
			    return INPUT;
			}
		} catch (CaptchaServiceException e1) {
			return INPUT;
		}
		
		
		try {
			auth = operatorLoginService.login(auth.getUsername(), auth.getPassword(), InetUtils.getRealIpAddr(this.getHttpRequest()), session.getId());
			auth.setSessionId(session.getId());
			auth.setIpAddress(this.getHttpRequest().getRemoteAddr());
		} catch (LoginException e) {
			errorMsg = e.getMessage();
			return INPUT;
		}
		getSession().setAttribute(Constant.SESSION_AUTH, auth);

		// 当前商户拥有的产品
		
//		List<ProductList> productList = productCustomerExcludeService.findCurrentCustomerProduct(auth.getCustomerno());
//		getHttpRequest().setAttribute("productList", productList);

		return SUCCESS;
	}

	public void setOperatorLoginService(OperatorLoginService operatorLoginService) {
		this.operatorLoginService = operatorLoginService;
	}

	public Authorization getAuth() {
		return auth;
	}

	public void setAuth(Authorization auth) {
		this.auth = auth;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	
}
