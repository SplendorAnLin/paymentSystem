package com.yl.agent.action;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.apache.log4j.Logger;

import com.lefu.commons.utils.lang.JsonUtils;
import com.pay.common.util.DigestUtil;
import com.pay.common.util.PreventAttackUtil;
import com.pay.common.util.PropertyUtil;
import com.pay.common.util.RandomCodeUtil;
import com.pay.common.util.SmsUtil;
import com.yl.agent.Constant;
import com.yl.agent.entity.Authorization;
import com.yl.agent.entity.Operator;
import com.yl.agent.entity.Role;
import com.yl.agent.service.OperatorService;
import com.yl.agent.service.RoleService;
import com.yl.agent.utils.CodeBuilder;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.sms.SmsUtils;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;

/**
 * 操作员管理控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月28日
 * @version V1.0.0
 */
public class OperatorAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 921392497399594456L;
	private ValueListHandlerHelper valueListHelper;	
	private RoleService roleService;
	private OperatorService operatorService;
	private AgentInterface agentInterface;
	private Operator operator;
	private String newpassword;
	private static final Logger logger = Logger.getLogger(OperatorAction.class);
	private String msg;
	private String phone;
	private List<Role> roleAll;
	public List<Role> getRoleAll() {
		return roleAll;
	}

	public void setRoleAll(List<Role> roleAll) {
		this.roleAll = roleAll;
	}

	// 操作员密码修改
	public String passwordUpdate() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String orgPassword = operator.getPassword();
		operator = operatorService.findByUsername(operator.getUsername());

		if (DigestUtil.md5(orgPassword).equals(operator.getPassword())) {// 客户端提交的原密码正确
			try {
				operatorService.updatePassword(operator.getUsername(), newpassword, auth);
				msg = "true";
			} catch (Exception e) {
				msg = "false";
			}
		} else {
			msg = "old_error";
		}
		return SUCCESS;
	}
	
	public String  getRole(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		List<Role> roleAll=roleService.findAllAvilable(auth.getAgentNo());
		if (roleAll.size()>0) {
			msg="[{\"id\": "+roleAll.get(0).getId()+",\"name\":\""+ roleAll.get(0).getName()+"\"}";
			for (int i = 1; i < roleAll.size(); i++) {
				msg+=",{\"id\": "+roleAll.get(i).getId()+",\"name\":\""+ roleAll.get(i).getName()+"\"}";
			}
		}
		msg+="]";
		return SUCCESS;
	}

	// 操作员修改
	public String operatorUpdate() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		phone=agentInterface.getAgent(auth.getAgentNo()).getPhoneNo();
		if (operator.getUsername().equals(phone)) {
			operator.setUsername(phone);
		}
		try {
			operatorService.update(operator);
		} catch (Exception e) {
			logger.info("", e);
			this.addFieldError("msg", "修改失败！");
		}
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String operatorQuery(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Map params= retrieveParams(getHttpRequest().getParameterMap());		//request 参数转换
		params.put("agent_no", auth.getAgentNo());
		ValueListInfo info = new ValueListInfo(params); 
		if(params.get("pagingPage")==null) {
			 info.setPagingPage(1);
		}    
	    ValueList valueList = valueListHelper.getValueList("operator", info);  
	    getHttpRequest().setAttribute("operator", valueList);
		return SUCCESS;
	}

	// 操作员
	public String operatorAdd() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Operator operatorOld = operatorService.findByUsername(operator.getUsername());
		if (operatorOld != null) {
			this.addFieldError("msg", "该手机号已注册！");
			throw new RuntimeException("operatorAdd is failed!");
		}
		operator.setAgentNo(auth.getAgentNo());
		operator.setCreateOperator(auth.getRealname() + "_" + auth.getUsername());
		operator.setTryTimes(99);
		operator.setCreateTime(new Date());
		try {
			operatorService.add(operator);
			SmsUtils.sendCustom(String.format(Constant.SMS_ADD_OPER, operator.getUsername(),operator.getPublicPassword()),operator.getUsername());
			logger.info("create operator customerNo:" + operator.getAgentNo() + " info:" + JsonUtils.toJsonString(operator));
			return SUCCESS;
		} catch (Exception e) {
			logger.info("", e);
			throw new RuntimeException("operatorAdd is failed!");
		}
	}
	
	/**
	 * 忘记密码短信验证码
	 * @return
	 */
	public String forgotPasswordSMSVerificationCode(){
		String phone = getHttpRequest().getParameter("phone");
		String code = CodeBuilder.build(4);
		try {
			SmsUtils.sendCustom(String.format(Constant.SMS_AGENT_FORGOTPASSWORD,phone,code), phone);
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
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			Operator operator = operatorService.updatePassword(phone, CodeBuilder.buildNumber(8), auth);
			try {
				SmsUtils.sendCustom(String.format(Constant.AGENT_RESETPASSWORD,operator.getPublicPassword()), phone);
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
		phone=agentInterface.getAgent(auth.getAgentNo()).getPhoneNo();
		operator = operatorService.findByUsername(operator.getUsername());
		roleAll=roleService.findAllAvilable(auth.getAgentNo());
		return SUCCESS;
	}

	public String md5() {
		List<Operator> list = operatorService.findAll();
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
	//原始请求参数转换
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Map retrieveParam(Map requestMap){
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

	public ValueListHandlerHelper getValueListHelper() {
			return valueListHelper;
		}

		public void setValueListHelper(ValueListHandlerHelper valueListHelper) {
			this.valueListHelper = valueListHelper;
		}

	public OperatorService getOperatorService() {
		return operatorService;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public AgentInterface getAgentInterface() {
		return agentInterface;
	}

	public void setAgentInterface(AgentInterface agentInterface) {
		this.agentInterface = agentInterface;
	}

	public static void main(String[] args) {
		String message = MessageFormat.format(PropertyUtil.getInstance("system").getProperty("customer.sms.open"),
				new Object[] { "爱仕达", "18701659564", "myPassword" });
		boolean result = SmsUtil.sendContentSms("18701659564", message); // 发送
		System.out.println("++++++++++++result++++++++++++++ =======" + result);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
