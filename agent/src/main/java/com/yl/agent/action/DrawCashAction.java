package com.yl.agent.action;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueListInfo;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.pay.common.util.Md5Util;
import com.yl.agent.Constant;
import com.yl.agent.entity.Authorization;
import com.yl.dpay.hessian.beans.ResponseBean;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.service.DpayFacade;
import com.yl.dpay.hessian.service.QueryFacade;
import com.yl.dpay.hessian.service.ServiceConfigFacade;
import com.yl.sms.SmsUtils;

/**
 * 代付提现控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月26日
 * @version V1.0.0
 */
public class DrawCashAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -3201238991638228922L;
	private Double amount;
	private DpayFacade dpayFacade;
	private ServiceConfigFacade serviceConfigFacade;
	private QueryFacade queryFacade;
	private String oldPassword;
	private ServiceConfigBean serviceConfigBean;
	private String sumInfo;
	private String newPassword;

	/**
	 * 提现
	 */
	public void drawCash() {
		Authorization auth = (Authorization) getSession().getAttribute("auth");
		try {
			if (amount != null && amount > 0) {
				ResponseBean bean = dpayFacade.drawCashShareProfit(auth.getAgentNo(),
						amount);
				if (bean != null) {
					if(bean.getRequestStatus() != null){
						write(bean.getRequestStatus()+"_"+bean.getResponseMsg());
					}else{
						write(bean.getResponseMsg());
					}
				}else{
					write("FAILED");
				}
			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
			if(e.getMessage() == null){
				write("FAILED" + "_系统异常");
			}else{
				write("FAILED" + "_" + e.getMessage());
			}
		}
	}

	// 校验手机验证码
	public void checkVerifyCodeEqual() {
		try {
			String verifyCode = getHttpRequest().getParameter("verifyCode");
			String sessionCode = (String) getSessionMap().get("verifyCode");
			if (sessionCode != null && verifyCode != null
					&& sessionCode.equals(verifyCode)) {
				// 通知前提输入信息
				write("1");
			} else {
				write("0");
			}
		} catch (Exception e) {
			write("0");
		}
	}

	// 校验审核密码，判断当前新密码和旧密码是否相同，相同返回true，反之为false
	public void verifyPassword() {
		Authorization auth = (Authorization) getSession().getAttribute(
				Constant.SESSION_AUTH);
		if (StringUtils.isBlank(newPassword)) {
			write("false");
		}
		String npwd = Md5Util.hmacSign(newPassword, "DPAY");
		serviceConfigBean = serviceConfigFacade.query(auth.getAgentNo());
		if (serviceConfigBean != null) {
			if (serviceConfigBean.getComplexPassword().equals(npwd)) {
				write("true");
			}
		}
		write("false");
	}

	// 校验手机号验证码和审核密码
	public void applyVerifyWithVerifyCode() {
		String verifyCode = getHttpRequest().getParameter("verifyCode");
		String sessionCode = (String) getSessionMap().get("verifyCode");
		Authorization auth = (Authorization) getSession().getAttribute(
				Constant.SESSION_AUTH);
		if (sessionCode != null && verifyCode != null
				&& sessionCode.equals(verifyCode)) {
			ServiceConfigBean serviceConfigBean = serviceConfigFacade
					.query(auth.getAgentNo());
			if (serviceConfigBean == null) {
				write("false");
			}
			if (serviceConfigBean.getComplexPassword().equals(Md5Util.hmacSign(oldPassword, "DPAY"))) {
				write("true");
			}
			write("false");

		} else {
			write("false");
		}
	}

	// 发送验证码
	@SuppressWarnings("unchecked")
	public void sendVerifyCode() {
		String type = getHttpRequest().getParameter("type");
		Random random = new Random();
		// 生成验证码6位随机数字
		String verifyCode = "" + random.nextInt(10) + random.nextInt(10)
				+ random.nextInt(10) + random.nextInt(10) + random.nextInt(10)
				+ random.nextInt(10);
		// 放到session中
		getSessionMap().put("verifyCode", verifyCode);
		// 发送手机验证码
		Authorization auth = (Authorization) getSession().getAttribute(
				Constant.SESSION_AUTH);

		try {
			String smsStr = "";
			if ("0".equals(type)) {
				smsStr = String.format(Constant.SMS_AUDIT_TYPE0, verifyCode);
			} else if ("1".equals(type)) {
				smsStr = String.format(Constant.SMS_OPEN_TYPE1, verifyCode);
			} else if ("2".equals(type)) {
				smsStr = String.format(Constant.SMS_CLOSE_TYPE2, verifyCode);
			}
			SmsUtils.sendCustom(smsStr, auth.getUsername());
			// 通知前提输入信息
			write("true");
		} catch (Exception e) {
			write("false");
		}

	}

	public void getComplexName() throws IOException {
		Authorization auth = (Authorization) getSession().getAttribute(
				Constant.SESSION_AUTH);
		serviceConfigBean = serviceConfigFacade.query(auth.getAgentNo());
		if (serviceConfigBean != null) {
			try {
				this.getHttpResponse().getWriter()
						.write(serviceConfigBean.getPhone());
			} catch (IOException e) {
				this.getHttpResponse().getWriter().write("null");
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 代付统计
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getDPayCount() {
		try {
			Authorization auth = (Authorization) getSession().getAttribute(
					"auth");
			String queryId = "dpayRequestCount";
			Map<String, String[]> requestMap = getHttpRequest()
					.getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("ownerId", auth.getAgentNo());
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			List valueList = (List) returnMap.get(QueryFacade.VALUELIST);
			Map<String, Object> map = (Map<String, Object>) valueList.get(0);
//			if (map != null && map.get("flow_no") != null
//					&& map.get("amount") != null) {
//				getHttpResponse().getWriter().write(
//						map.get("flow_no") + "," + map.get("amount") + ","
//								+ map.get("fee"));
//			} else {
//				getHttpResponse().getWriter().write("null");
//			}
			
			sumInfo = JsonUtils.toJsonString(map);
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}

	/**
	 * 代付查询
	 */
	@SuppressWarnings("rawtypes")
	public String dfQuery() {
		try {
			Authorization auth = (Authorization) getSession().getAttribute(
					"auth");
			String queryId = "dpayRequest";
			Map<String, String[]> requestMap = getHttpRequest()
					.getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("ownerId", auth.getAgentNo());
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(
					queryId,
					new DefaultListBackedValueList((List) returnMap
							.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap
									.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}

	/**
	 * 代付查询
	 */
	@SuppressWarnings("rawtypes")
	public String dfAuditQuery() {
		try {
			Authorization auth = (Authorization) getSession().getAttribute(
					"auth");
			String queryId = "dpayAuditRequest";
			Map<String, String[]> requestMap = getHttpRequest()
					.getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			String status = (String) params.get("status");

			Map<String, Object> statusMap = convertDfStatus(status);
			params.putAll(statusMap);
			params.put("ownerId", auth.getAgentNo());
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(
					queryId,
					new DefaultListBackedValueList((List) returnMap
							.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap
									.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}

	/**
	 * 代付导出
	 */
	@SuppressWarnings("rawtypes")
	public String dfExport() {

		try {
			Authorization auth = (Authorization) getSession().getAttribute(
					"auth");
			String queryId = "dpayRequestExport";
			Map<String, String[]> requestMap = getHttpRequest()
					.getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			String status = (String) params.get("status");
			Map<String, Object> statusMap = convertDfStatus(status);
			params.putAll(statusMap);
			params.put("ownerId", auth.getAgentNo());
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(
					queryId,
					new DefaultListBackedValueList((List) returnMap
							.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap
									.get(QueryFacade.VALUELIST_INFO)));
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
	 * 显示状态-数据库状态的转换 AUDIT_WAIT 待审核 AUDIT_REFUSE 审核拒绝 HANDLING 处理中 SUCCESS 付款成功
	 * FAILED 付款失败 REFUND_WAIT 待退款 REFUNDED 已退款（通过与拒绝）
	 * 
	 * @param status
	 *            显示状态
	 * @return
	 */
	private Map<String, Object> convertDfStatus(String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		if ("AUDIT_WAIT".equals(status)) {
			map.put("df.auditStatus", "WAIT");
		} else if ("AUDIT_REFUSE".equals(status)) {
			map.put("df.auditStatus", "REFUSE");
		} else if ("HANDLING".equals(status)) {
			map.put("df.handling", "HANDLING");
		} else if ("SUCCEED".equals(status)) {
			map.put("dfRecord.handleStatus", "SUCCEED");
		} else if ("FAILED".equals(status)) {
			map.put("dfRecord.handleStatus", "FAILED");
		} else if ("REFUND_WAIT".equals(status)) {
			map.put("tk.auditStatus", "WAIT");
		} else if ("REFUNDED".equals(status)) {
			map.put("tk.refunded", "REFUNDED");
		}
		return map;
	}
	
	public String findByCustomerNo(){
		Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
		serviceConfigBean = serviceConfigFacade.query(auth.getAgentNo());
		if(serviceConfigBean==null){
			return NONE;
		}
		String phone =serviceConfigBean.getPhone();
		serviceConfigBean.setPhone(generateSecretPhone(phone));
		return SUCCESS;
	}
	
	// 隐藏手机号中间四位
	private String generateSecretPhone(String phone){
		String secretPhone="";
		if(phone!=null&&phone.length()==11){
			secretPhone = phone
					.substring(0, phone.length() - (phone.substring(3)).length()) + "****" + phone.substring(7);

		}
		return secretPhone;
	}
	
	public String updateComplexPwd(){
		Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
		try {
			serviceConfigBean.setOwnerId(auth.getAgentNo());
			serviceConfigFacade.dfUpdateComplexPwd(serviceConfigBean);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "fail";
		}
		serviceConfigBean = serviceConfigFacade.query(auth.getAgentNo());
		String phone =serviceConfigBean.getPhone();
		serviceConfigBean.setPhone(generateSecretPhone(phone));
		return SUCCESS;
	}
	
	public void applyVerify(){
		Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
		write(findPWDApplyVerify(auth.getAgentNo(),oldPassword));
	}
	
	public String findPWDApplyVerify(String customerNo, String password) {
		ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(customerNo);
		if(serviceConfigBean==null){
			throw new RuntimeException("findPWDApplyVerify serviceConfigBean is null!");
		}
		if(serviceConfigBean.getComplexPassword().equals(Md5Util.hmacSign(password, "DPAY"))){
			return "true";
		}
		return "false";
	}
	
	public void isUsePhoneCheck(){
		try{
			Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
			ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(auth.getAgentNo());
			if(Boolean.valueOf(serviceConfigBean.getUsePhoneCheck())){
				write("1");
			}else{
				write("0");
			}
		}catch(Exception e){
			write("0");
		}

	}
	
	public void updateUsePhoneCheck(){
		try{
			boolean flag =Boolean.parseBoolean(getHttpRequest().getParameter("flag"));
			String verifyCode =getHttpRequest().getParameter("verifyCode");
			String sessionCode =(String)getSessionMap().get("verifyCode");
			if(sessionCode!=null&&verifyCode!=null&&sessionCode.equals(verifyCode)){
				Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
				logger.info("更新开通短信验证功能，AgentNo：" + auth.getAgentNo() + "flag:" + flag + " ROLE ：AGENT");
				ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(auth.getAgentNo());
				serviceConfigBean.setUsePhoneCheck(String.valueOf(flag).toUpperCase());
				serviceConfigFacade.update(serviceConfigBean);
				// 通知前提输入信息
				write("1");
			}else{
				write("2");
			}
		}catch(Exception e){
			write("0");
		}
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setDpayFacade(DpayFacade dpayFacade) {
		this.dpayFacade = dpayFacade;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setServiceConfigFacade(ServiceConfigFacade serviceConfigFacade) {
		this.serviceConfigFacade = serviceConfigFacade;
	}

	public void setQueryFacade(QueryFacade queryFacade) {
		this.queryFacade = queryFacade;
	}

	public ServiceConfigBean getServiceConfigBean() {
		return serviceConfigBean;
	}

	public void setServiceConfigBean(ServiceConfigBean serviceConfigBean) {
		this.serviceConfigBean = serviceConfigBean;
	}

	public String getSumInfo() {
		return sumInfo;
	}

	public void setSumInfo(String sumInfo) {
		this.sumInfo = sumInfo;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
