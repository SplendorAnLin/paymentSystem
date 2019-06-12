package com.yl.customer.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpSession;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import com.lefu.commons.utils.lang.JsonUtils;
import com.pay.common.util.DigestUtil;
import com.pay.common.util.Md5Util;
import com.pay.common.util.PreventAttackUtil;
import com.pay.common.util.PropertyUtil;
import com.pay.common.util.RandomCodeUtil;
import com.pay.common.util.SmsUtil;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.bean.CustomerSettle;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.customer.Constant;
import com.yl.customer.entity.Authorization;
import com.yl.customer.entity.Operator;
import com.yl.customer.entity.Role;
import com.yl.customer.enums.OperatorType;
import com.yl.customer.enums.Status;
import com.yl.customer.exception.LoginException;
import com.yl.customer.service.CustomerService;
import com.yl.customer.service.OperatorLoginService;
import com.yl.customer.service.OperatorService;
import com.yl.customer.service.RoleService;
import com.yl.customer.utils.CodeBuilder;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.sms.SmsUtils;

/**
 * 操作员管理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class OperatorAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 921392497399594456L;

	private static final Logger logger = Logger.getLogger(OperatorAction.class);
	private ValueListHandlerHelper valueListHelper;	
	private RoleService roleService;
	private CustomerService customerService;
	private CustomerInterface customerInterface;
	private OperatorService operatorService;
	private OperatorLoginService operatorLoginService;
	private Operator operator;
	private String phone;
	private String newpassword;
	private String auditPassword;
	private String msg;
	private List<Role> roleAll;
	public List<Role> getRoleAll() {
		return roleAll;
	}

	public void setRoleAll(List<Role> roleAll) {
		this.roleAll = roleAll;
	}

	// 校验审核密码
	public void checkAuditPassword() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		try {
			operator = operatorService.findByUsername(auth.getUsername());
			if (Md5Util.hmacSign(auditPassword, "DPAY").equals(operator.getComplexPassword())) {
				getHttpResponse().getWriter().write("true");
			} else {
				getHttpResponse().getWriter().write("false");
			}
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	public String  getRole(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		roleAll=roleService.findAllAvilable(auth.getCustomerno());
		if (roleAll.size()>0) {
			msg="[{\"id\": "+roleAll.get(0).getId()+",\"name\":\""+ roleAll.get(0).getName()+"\"}";
			for (int i = 1; i < roleAll.size(); i++) {
				msg+=",{\"id\": "+roleAll.get(i).getId()+",\"name\":\""+ roleAll.get(i).getName()+"\"}";
			}
		}
		msg+="]";
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String operatorQuery(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Map params= retrieveParams(getHttpRequest().getParameterMap());		//request 参数转换
		params.put("customer_no", auth.getCustomerno());
		ValueListInfo info = new ValueListInfo(params); 
		if(params.get("pagingPage")==null) {
			 info.setPagingPage(1);
		}    
	    ValueList valueList = valueListHelper.getValueList("operator", info);  
	    getHttpRequest().setAttribute("operator", valueList);
		return SUCCESS;
	}
	
	// 操作员密码修改接口
	public void updateCliPassword() {
		HttpSession session = this.getSession();
		Authorization auth_session = (Authorization) session.getAttribute(Constant.SESSION_AUTH);
		Map<String, String> reqParams = null;
		Map<String, String> resParams = null;

		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader((ServletInputStream) getHttpRequest().getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			reqParams = JsonUtils.toObject(sb.toString(), new TypeReference<Map<String, String>>() {
			});

			// check params
			if (reqParams == null) {
				resParams = new HashMap<String, String>();
				resParams.put("responseCode", "01");
				resParams.put("responseMsg", "参数错误");
				resParams.put("timestamp", String.valueOf(System.nanoTime()));
				resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams),
						resParams.get("timestamp").substring(2, 10)));

				try {
					getHttpResponse().setCharacterEncoding("UTF-8");
					getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
					getHttpResponse().getWriter().close();
				} catch (IOException e) {
					logger.error(e);
				}
				return;
			}

			// check sign
			Map<String, String> params = new HashMap<>();
			params.put("userName", reqParams.get("userName"));
			params.put("oldPassword", reqParams.get("oldPassword"));
			params.put("newPassword", reqParams.get("newPassword"));
			params.put("timestamp", reqParams.get("timestamp"));
			if (!Md5Util.hmacSign(JsonUtils.toJsonString(params), reqParams.get("timestamp").substring(2, 10))
					.equals(reqParams.get("sign"))) {
				resParams = new HashMap<String, String>();
				resParams.put("responseCode", "01");
				resParams.put("responseMsg", "签名错误");
				resParams.put("timestamp", String.valueOf(System.nanoTime()));
				resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams),
						resParams.get("timestamp").substring(2, 10)));
				try {
					getHttpResponse().setCharacterEncoding("UTF-8");
					getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
					getHttpResponse().getWriter().close();
				} catch (IOException e) {
					logger.error(e);
				}
				return;
			}

			try {
				Authorization auth = operatorLoginService.login(reqParams.get("userName"), reqParams.get("passowrd"),
						"", "");
				Customer customer = customerService.findCustByRemote(auth.getCustomerno());
				CustomerSettle customerSettle = customerService.findByCustSettleByRemote(auth.getCustomerno());
				List<CustomerFee> custFeeList = customerService.findByCustFeeByRemote(auth.getCustomerno());
				CustomerKey customerKey = customerService.findCustKeyByRemote(auth.getCustomerno(), KeyType.MD5);
				ServiceConfigBean serviceConfigBean = customerService
						.findByCustDpayConfigByRemote(auth.getCustomerno());

				resParams = new HashMap<String, String>();
				resParams.put("responseCode", "00");
				resParams.put("responseMsg", "登录成功");
				resParams.put("shortName", customer.getShortName());
				resParams.put("fullName", customer.getFullName());
				resParams.put("settleAccountName", customerSettle.getAccountName());
				resParams.put("settleAccountNo", customerSettle.getAccountNo());
				resParams.put("settleBankName", customerSettle.getOpenBankName());
				resParams.put("fees", JsonUtils.toJsonString(custFeeList));
				resParams.put("key", customerKey.getKey());
				resParams.put("minSettleAmount", String.valueOf(serviceConfigBean.getMinAmount()));
				resParams.put("maxSettleAmount", String.valueOf(serviceConfigBean.getMaxAmount()));
				resParams.put("timestamp", String.valueOf(System.nanoTime()));
				resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams),
						resParams.get("timestamp").substring(2, 10)));
				try {
					getHttpResponse().setCharacterEncoding("UTF-8");
					getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
					getHttpResponse().getWriter().close();
				} catch (IOException e) {
					logger.error(e);
				}
				return;
			} catch (LoginException e1) {
				logger.error(e1);
				resParams = new HashMap<String, String>();
				resParams.put("responseCode", "01");
				resParams.put("responseMsg", e1.getMessage());
				resParams.put("timestamp", String.valueOf(System.nanoTime()));
				resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams),
						resParams.get("timestamp").substring(2, 10)));
				try {
					getHttpResponse().setCharacterEncoding("UTF-8");
					getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
					getHttpResponse().getWriter().close();
				} catch (IOException e) {
					logger.error(e);
				}
				return;
			}

		} catch (Exception e) {
			logger.error(e);
			resParams = new HashMap<String, String>();
			resParams.put("responseCode", "01");
			resParams.put("responseMsg", "系统异常");
			resParams.put("timestamp", String.valueOf(System.nanoTime()));
			resParams.put("sign",
					Md5Util.hmacSign(JsonUtils.toJsonString(resParams), resParams.get("timestamp").substring(2, 10)));

			try {
				getHttpResponse().setCharacterEncoding("UTF-8");
				getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
				getHttpResponse().getWriter().close();
			} catch (IOException e1) {
				logger.error(e1);
			}
			return;
		}
	}

	// 操作员登录接口
	public void cliLogin() {
		Map<String, String> reqParams = null;
		Map<String, String> resParams = null;

		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader((ServletInputStream) getHttpRequest().getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			reqParams = JsonUtils.toObject(sb.toString(), new TypeReference<Map<String, String>>() {
			});

			// check params
			if (reqParams == null) {
				resParams = new HashMap<String, String>();
				resParams.put("responseCode", "01");
				resParams.put("responseMsg", "参数错误");
				resParams.put("timestamp", String.valueOf(System.nanoTime()));
				resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams),
						resParams.get("timestamp").substring(2, 10)));

				try {
					getHttpResponse().setCharacterEncoding("UTF-8");
					getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
					getHttpResponse().getWriter().close();
				} catch (IOException e) {
					logger.error(e);
				}
				return;
			}

			// check sign
			Map<String, String> params = new HashMap<>();
			params.put("userName", reqParams.get("userName"));
			params.put("password", reqParams.get("oldPassword"));
			params.put("timestamp", reqParams.get("timestamp"));
			if (!Md5Util.hmacSign(JsonUtils.toJsonString(params), reqParams.get("timestamp").substring(2, 10))
					.equals(reqParams.get("sign"))) {
				resParams = new HashMap<String, String>();
				resParams.put("responseCode", "01");
				resParams.put("responseMsg", "签名错误");
				resParams.put("timestamp", String.valueOf(System.nanoTime()));
				resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams),
						resParams.get("timestamp").substring(2, 10)));
				try {
					getHttpResponse().setCharacterEncoding("UTF-8");
					getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
					getHttpResponse().getWriter().close();
				} catch (IOException e) {
					logger.error(e);
				}
				return;
			}

			String orgPassword = reqParams.get("oldPassword");
			operator = operatorService.findByUsername(reqParams.get("userName"));

			if (DigestUtil.md5(orgPassword).equals(operator.getPassword())) {// 客户端提交的原密码正确
				operatorService.updatePassword(operator.getUsername(), reqParams.get("newPassword"));
				resParams = new HashMap<String, String>();
				resParams.put("responseCode", "00");
				resParams.put("responseMsg", "修改成功");
				resParams.put("timestamp", String.valueOf(System.nanoTime()));
				resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams),
						resParams.get("timestamp").substring(2, 10)));
				try {
					getHttpResponse().setCharacterEncoding("UTF-8");
					getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
					getHttpResponse().getWriter().close();
				} catch (IOException e) {
					logger.error(e);
				}
				return;
			} else {
				resParams = new HashMap<String, String>();
				resParams.put("responseCode", "01");
				resParams.put("responseMsg", "原密码错误");
				resParams.put("timestamp", String.valueOf(System.nanoTime()));
				resParams.put("sign", Md5Util.hmacSign(JsonUtils.toJsonString(resParams),
						resParams.get("timestamp").substring(2, 10)));
				try {
					getHttpResponse().setCharacterEncoding("UTF-8");
					getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
					getHttpResponse().getWriter().close();
				} catch (IOException e) {
					logger.error(e);
				}
				return;
			}
		} catch (Exception e) {
			logger.error(e);
			resParams = new HashMap<String, String>();
			resParams.put("responseCode", "01");
			resParams.put("responseMsg", "系统异常");
			resParams.put("timestamp", String.valueOf(System.nanoTime()));
			resParams.put("sign",
					Md5Util.hmacSign(JsonUtils.toJsonString(resParams), resParams.get("timestamp").substring(2, 10)));

			try {
				getHttpResponse().setCharacterEncoding("UTF-8");
				getHttpResponse().getWriter().write(JSONObject.fromObject(resParams).toString());
				getHttpResponse().getWriter().close();
			} catch (IOException e1) {
				logger.error(e1);
			}
			return;
		}
	}

	// 操作员密码修改
	public String passwordUpdate() {
		String orgPassword = operator.getPassword();
		operator = operatorService.findByUsername(operator.getUsername());
		if (DigestUtil.md5(orgPassword).equals(operator.getPassword())) {// 客户端提交的原密码正确
			try {
				operatorService.updatePassword(operator.getUsername(), newpassword);
				msg = "true";
			} catch (Exception e) {
				msg = "false";
			}
		} else {
			msg = "old_error";
		}
		return SUCCESS;
	}

	// 操作员审核密码修改
	public String auditPasswordUpdate() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String orgPassword = operator.getComplexPassword();
		operator = operatorService.findByUsername(operator.getUsername());
		if (DigestUtil.md5(orgPassword).equals(operator.getComplexPassword())) {// 客户端提交的原密码正确
			operatorService.updateAuditPassword(operator.getUsername(), newpassword, auth);
			return SUCCESS;
		} else {
			this.addFieldError("msg", "原密码不正确");
			throw new RuntimeException("原密码不正确!");
		}
	}

	// 操作员修改
	public String operatorUpdate() {
		try {
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			phone=customerInterface.getCustomer(auth.getCustomerno()).getPhoneNo();
			if (phone.equals(operator.getUsername())) {
				operator.setUsername(phone);
			}
			operatorService.update(operator);
		} catch (Exception e) {
			logger.info("", e);
			this.addFieldError("msg", "修改失败！");
		}
		return SUCCESS;
	}

	// 操作员
	public String operatorAdd(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Operator operatorOld = operatorService.findByUsername(operator.getUsername());
		if (operatorOld != null) {
			this.addFieldError("msg", "该手机号已注册！");
			throw new RuntimeException("operatorAdd is failed!");
		}
		operator.setCustomerNo(auth.getCustomerno());
		operator.setCreateOperator(auth.getRealname());
		operator.setTryTimes(99);
		operator.setCreateTime(new Date());
		try {
			operatorService.add(operator);
			SmsUtils.sendCustom(String.format(Constant.SMS_OPER_OPEN, operator.getUsername(),operator.getPublicPassword()),operator.getUsername());
			logger.info("create operator customerNo:" + operator.getCustomerNo() + " info:" + JsonUtils.toJsonString(operator));
			return SUCCESS;
		} catch (Exception e) {
			logger.info("", e);
			throw new RuntimeException("operatorAdd is failed!");
		}
	}
	
	public String checkOper(){
		operator =  operatorService.findByUsername(phone);
		if (operator==null) {
			msg="true";
		}else{
			msg="false";
		}
		return SUCCESS;
	}
	
	public String findByUserName() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		phone=customerInterface.getCustomer(auth.getCustomerno()).getPhoneNo();
		operator = operatorService.findByUsername(operator.getUsername());
		roleAll=roleService.findAllAvilable(auth.getCustomerno());
		return SUCCESS;
	}
	
	
	/**
	 * 忘记密码短信验证码
	 * @return
	 */
	public String forgotPasswordSMSVerificationCode(){
		String phone = getHttpRequest().getParameter("phone");
		String code = CodeBuilder.build(4);
		try {
			SmsUtils.sendCustom(String.format(Constant.SMS_CUSTOMER_FORGOTPASSWORD,phone,code), phone);
			getHttpRequest().getSession().setAttribute("code", code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 随机重置密码
	 * @return
	 */
	public String randomResetpassword(){
		String phone = getHttpRequest().getParameter("phone");
		String code = getHttpRequest().getParameter("code");
		String codex = (String) getHttpRequest().getSession().getAttribute("code");
		if(code.equals(codex)){
			Operator operator = operatorService.updatePassword(phone, CodeBuilder.buildNumber(8));
			try {
				SmsUtils.sendCustom(String.format(Constant.CUSTOMER_RESETPASSWORD,operator.getPublicPassword()), phone);
				msg = "true";
			} catch (IOException e) {
				msg = "false";
				e.printStackTrace();
			}
		}else {
			msg = "codeError";
		}
		
		getHttpRequest().getSession().removeAttribute("code");
		
		return SUCCESS;
	}
	

	public String md5() {
		List<Operator> list = operatorService.findAll();
		// Operator oper = null;

		try {

			for (int i = 0; i < list.size(); i++) {
				String s = RandomCodeUtil.genRandomCode(8);
				Operator oper = list.get(i);
				oper.setPublicPassword(s);
				oper.setPassword(DigestUtil.md5(s));
				operatorService.update(oper);
			}
		} catch (Throwable e) {
			logger.info("", e);
			throw new RuntimeException("md5 is failed!");
		}
		return null;
	}

	//原始请求参数转换
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Map retrieveParams(Map requestMap){
			Map resultMap = new HashMap();
			for(Object key : requestMap.keySet()){
				Object value = requestMap.get(key);
				if(value!=null){
					if(value.getClass().isArray()){
						int length = Array.getLength(value);
						if(length==1){
							boolean includedInSQLInjectionWhitelist = false;
							boolean includedInXSSWhitelist = false;
							if(!includedInSQLInjectionWhitelist){
								resultMap.put(key, PreventAttackUtil.filterSQLInjection(Array.get(value,0).toString()).trim());
							}
							if(!includedInXSSWhitelist){
								if(resultMap.containsKey(key)){
									resultMap.put(key, PreventAttackUtil.filterXSS(resultMap.get(key).toString()).trim());
								}else{
									resultMap.put(key, PreventAttackUtil.filterXSS(Array.get(value,0).toString()).trim());
								}
							}
							if(includedInSQLInjectionWhitelist && includedInXSSWhitelist) {
									resultMap.put(key, Array.get(value,0).toString().trim());
							}
						}
						if(length>1){
							resultMap.put(key, value);
						}
					}else{
						boolean includedInSQLInjectionWhitelist = false;
						boolean includedInXSSWhitelist = false;
						if(!includedInSQLInjectionWhitelist){
							resultMap.put(key, PreventAttackUtil.filterSQLInjection(value.toString()).trim());
						}
						if(!includedInXSSWhitelist){
							if(resultMap.containsKey(key)){
								resultMap.put(key, PreventAttackUtil.filterXSS(resultMap.get(key).toString()).trim());
							}else{
								resultMap.put(key, PreventAttackUtil.filterXSS(value.toString()).trim());
							}
						}
						if(includedInSQLInjectionWhitelist && includedInXSSWhitelist) {
							resultMap.put(key, value.toString().trim());
						}
					}
				}
			}
			return resultMap;
		}
	
	public OperatorService getOperatorService() {
		return operatorService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setOperatorService(OperatorService operatorService) {
		this.operatorService = operatorService;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAuditPassword() {
		return auditPassword;
	}

	public void setAuditPassword(String auditPassword) {
		this.auditPassword = auditPassword;
	}

	public void setOperatorLoginService(OperatorLoginService operatorLoginService) {
		this.operatorLoginService = operatorLoginService;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public CustomerInterface getCustomerInterface() {
		return customerInterface;
	}

	public void setCustomerInterface(CustomerInterface customerInterface) {
		this.customerInterface = customerInterface;
	}

	public ValueListHandlerHelper getValueListHelper() {
		return valueListHelper;
	}

	public void setValueListHelper(ValueListHandlerHelper valueListHelper) {
		this.valueListHelper = valueListHelper;
	}

	public static void main(String[] args) {
		String message = MessageFormat.format(PropertyUtil.getInstance("system").getProperty("customer.sms.open"),
				new Object[] { "爱仕达", "18701659564", "myPassword" });
		boolean result = SmsUtil.sendContentSms("18701659564", message); // 发送
		System.out.println("++++++++++++result++++++++++++++ =======" + result);
	}
}
