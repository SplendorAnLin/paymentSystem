package com.yl.boss.action;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.security.PreventAttackUtil;
import com.pay.common.util.DigestUtil;
import com.pay.common.util.RandomCodeUtil;
import com.pay.common.util.SmsUtil;
import com.yl.agent.api.bean.AgentOperator;
import com.yl.agent.api.interfaces.AgentFacade;
import com.yl.agent.api.interfaces.AgentOperInterface;
import com.yl.boss.Constant;
import com.yl.boss.api.utils.Page;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Operator;
import com.yl.boss.entity.Role;
import com.yl.boss.service.OperatorService;
import com.yl.boss.service.RoleService;
import com.yl.customer.api.bean.CustOperator;
import com.yl.customer.api.interfaces.CustFacade;
import com.yl.customer.api.interfaces.CustOperInterface;
import com.yl.dpay.hessian.service.QueryFacade;
import com.yl.sms.SmsUtils;

import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;
import com.pay.common.util.PropertyUtil;

/**
 * 操作员管理
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class OperatorAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 921392497399594456L;
	private ValueListHandlerHelper valueListHelper;
	private RoleService roleService;
	private OperatorService operatorService;
	private AgentOperInterface agentOperInterface;
	private CustOperInterface custOperInterface;
	private AgentFacade agentFacade;
	private CustFacade custFacade;
	private Operator operator;
	private String newpassword;
	private List<Role> roleAll;
	public List<Role> getRoleAll() {
		return roleAll;
	}

	public void setRoleAll(List<Role> roleAll) {
		this.roleAll = roleAll;
	}

	private String phone;
	private String msg;
	private Page page;
	private static final Logger logger = LoggerFactory.getLogger(OperatorAction.class);

	// 操作员密码修改
	public String passwordUpdate() {

		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String orgPassword = operator.getPassword();
		operator = operatorService.findByUsername(operator.getUsername());
		if (DigestUtil.md5(orgPassword).equals(operator.getPassword())) {// 客户端提交的原密码正确
			operatorService.updatePassword(operator.getUsername(), newpassword, auth);
			auth.setPassword(DigestUtil.md5(newpassword));
			msg = "true";
		} else {
			msg = "false";
			// this.addFieldError("msg", "other");
			// throw new RuntimeException("passwordUpdate is failed!");
		}
		return SUCCESS;
	}
	
	public String  getRole(){
		roleAll=roleService.findAllAvilable();
		if (roleAll.size()>0) {
			msg="[{\"id\": "+roleAll.get(0).getId()+",\"name\":\""+ roleAll.get(0).getName()+"\"}";
			for (int i = 1; i < roleAll.size(); i++) {
				msg+=",{\"id\": "+roleAll.get(i).getId()+",\"name\":\""+ roleAll.get(i).getName()+"\"}";
			}
		}
		msg+="]";
		return SUCCESS;
	}

	public String auditPasswordUpdate() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		String orgPassword = operator.getAuditPassword();
		operator = operatorService.findByUsername(operator.getUsername());

		if (DigestUtil.md5(orgPassword).equals(operator.getAuditPassword())) {// 客户端提交的原密码正确
			operatorService.updateAuditPassword(operator.getUsername(), newpassword, auth);
			msg = "true";
		} else {
			msg = "false";
			// this.addFieldError("msg", "原密码不正确");
			// throw new RuntimeException("passwordUpdate is failed!");
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String operatorQuery() {
		Map<String, Object> params = retrieveParam(getHttpRequest().getParameterMap()); // request 参数转换
		String sysType = params.get("sysType").toString();
		if (sysType.equals("BOSS")) {
			ValueListInfo info = new ValueListInfo(params);
			if (params.get("pagingPage") == null) {
				info.setPagingPage(1);
			}
			ValueList valueList = valueListHelper.getValueList("operator", info);
			getHttpRequest().setAttribute("operator", valueList);
		} else {
			if (sysType.equals("AGENT")) {
				Map<String, Object> returnMap = agentFacade.query("operator", params);
				getHttpRequest().setAttribute("operator",
						new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
								(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
			} else if (sysType.equals("CUST")) {
				Map<String, Object> returnMap = custFacade.query("operator", params);
				getHttpRequest().setAttribute("operator",
						new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
								(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
			}
		}
		return SUCCESS;
	}

	// 操作员密码修改
	public String operatorUpdate() {
		try {
			operatorService.update(operator);
		} catch (Exception e) {
			logger.info("", e);
			this.addFieldError("msg", "修改失败！");
		}
		return SUCCESS;
		
	}

	// 操作员
	public String operatorAdd() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Operator operatorOld = operatorService.findByUsername(operator.getUsername());
		if (operatorOld != null) {
			// this.addFieldError("msg", "该手机号已注册！");
			throw new RuntimeException("operatorAdd is failed!");
		}
		operator.setCreateOperator(auth.getRealname() + "_" + auth.getUsername());
		operator.setTryTimes(99);
		operator.setCreateTime(new Date());
		try {
			operatorService.add(operator);
			SmsUtils.sendCustom(String.format(Constant.SMS_OPER_OPEN, operator.getUsername(), operator.getPublicPassword()),
					operator.getUsername());
			return SUCCESS;
		} catch (Exception e) {
			logger.info("", e);
			throw new RuntimeException("operatorAdd is failed!");
		}
	}

	public String checkOper() {
		operator = operatorService.findByUsername(phone);
		if (operator == null) {
			msg = "true";
		} else {
			msg = "false";
		}
		return SUCCESS;
	}

	public String findByUserName() {
		if(operator.getCustomerNo() != null && !"".equals(operator.getCustomerNo())){
			if (operator.getCustomerNo().subSequence(0, 1).equals("C")) {
				CustOperator custOperator=custOperInterface.findByUserName(operator.getUsername());
				operator.setCustomerNo(custOperator.getCustomerNo());
				operator.setUsername(custOperator.getUsername());
				operator.setRealname(custOperator.getRealname());
			}else if (operator.getCustomerNo().subSequence(0, 1).equals("A")) {
				AgentOperator agentOperator=agentOperInterface.findByUserName(operator.getUsername());
				operator.setCustomerNo(agentOperator.getAgentNo());
				operator.setUsername(agentOperator.getUsername());
				operator.setRealname(agentOperator.getRealname());
			}else if (operator.getCustomerNo().subSequence(0, 1).equals("1")) {
				operator =operatorService.findByUsername(operator.getUsername());
				roleAll=roleService.findAllAvilable();
			}
		}else {
			operator =operatorService.findByUsername(operator.getUsername());
			roleAll=roleService.findAllAvilable();
		}
		return SUCCESS;
	}
	
	//用户登陆历史记录查询
	public String operatorLoginHistoryQuery(){
		Map<String, Object> params = retrieveParam(getHttpRequest().getParameterMap()); // request 参数转换
		String sysType = params.get("sysType").toString();
		if (sysType.equals("AGENT")) {
			Map<String, Object> returnMap = agentFacade.query("loginLogQuery", params);
			getHttpRequest().setAttribute("loginLogQuery",
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
			getHttpRequest().setAttribute("customer_type", "服务商");
		} else if (sysType.equals("CUST")) {
			Map<String, Object> returnMap = custFacade.query("loginLogQuery", params);
			getHttpRequest().setAttribute("loginLogQuery",
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
			getHttpRequest().setAttribute("customer_type", "商户");
		}else if (sysType.equals("BOSS")) {
			ValueListInfo info = new ValueListInfo(params);
			if (params.get("pagingPage") == null) {
				info.setPagingPage(1);
			}
			ValueList valueList = valueListHelper.getValueList("loginLogQuery", info);
			getHttpRequest().setAttribute("loginLogQuery", valueList);
		}
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

	// 原始请求参数转换
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map retrieveParam(Map requestMap) {
		Map resultMap = new HashMap();
		for (Object key : requestMap.keySet()) {
			Object value = requestMap.get(key);
			if (value != null) {
				if (value.getClass().isArray()) {
					int length = Array.getLength(value);
					if (length == 1) {
						boolean includedInSQLInjectionWhitelist = false;
						boolean includedInXSSWhitelist = false;
						if (!includedInSQLInjectionWhitelist) {
							resultMap.put(key,
									PreventAttackUtil.filterSQLInjection(Array.get(value, 0).toString()).trim());
						}
						if (!includedInXSSWhitelist) {
							if (resultMap.containsKey(key)) {
								resultMap.put(key, PreventAttackUtil.filterXSS(resultMap.get(key).toString()).trim());
							} else {
								resultMap.put(key, PreventAttackUtil.filterXSS(Array.get(value, 0).toString()).trim());
							}
						}
						if (includedInSQLInjectionWhitelist && includedInXSSWhitelist) {
							resultMap.put(key, Array.get(value, 0).toString().trim());
						}
					}
					if (length > 1) {
						resultMap.put(key, value);
					}
				} else {
					boolean includedInSQLInjectionWhitelist = false;
					boolean includedInXSSWhitelist = false;
					if (!includedInSQLInjectionWhitelist) {
						resultMap.put(key, PreventAttackUtil.filterSQLInjection(value.toString()).trim());
					}
					if (!includedInXSSWhitelist) {
						if (resultMap.containsKey(key)) {
							resultMap.put(key, PreventAttackUtil.filterXSS(resultMap.get(key).toString()).trim());
						} else {
							resultMap.put(key, PreventAttackUtil.filterXSS(value.toString()).trim());
						}
					}
					if (includedInSQLInjectionWhitelist && includedInXSSWhitelist) {
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

	public void setOperatorService(OperatorService operatorService) {
		this.operatorService = operatorService;
	}

	public Operator getOperator() {
		return operator;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public ValueListHandlerHelper getValueListHelper() {
		return valueListHelper;
	}

	public void setValueListHelper(ValueListHandlerHelper valueListHelper) {
		this.valueListHelper = valueListHelper;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	public AgentOperInterface getAgentOperInterface() {
		return agentOperInterface;
	}

	public void setAgentOperInterface(AgentOperInterface agentOperInterface) {
		this.agentOperInterface = agentOperInterface;
	}

	public CustOperInterface getCustOperInterface() {
		return custOperInterface;
	}

	public void setCustOperInterface(CustOperInterface custOperInterface) {
		this.custOperInterface = custOperInterface;
	}

	public AgentFacade getAgentFacade() {
		return agentFacade;
	}

	public void setAgentFacade(AgentFacade agentFacade) {
		this.agentFacade = agentFacade;
	}

	public CustFacade getCustFacade() {
		return custFacade;
	}

	public void setCustFacade(CustFacade custFacade) {
		this.custFacade = custFacade;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static void main(String[] args) {
		String message = MessageFormat.format(PropertyUtil.getInstance("system").getProperty("customer.sms.open"),
				new Object[] { "爱仕达", "18701659564", "myPassword" });
		boolean result = SmsUtil.sendContentSms("18701659564", message); // 发送
		System.out.println("++++++++++++result++++++++++++++ =======" + result);
	}
}
