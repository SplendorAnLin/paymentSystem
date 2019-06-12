package com.yl.customer.action;

import java.io.IOException;
import java.util.Random;

import com.pay.common.util.Md5Util;
import com.yl.customer.Constant;
import com.yl.customer.entity.Authorization;
import com.yl.customer.service.CustomerService;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.service.ServiceConfigFacade;
import com.yl.sms.SmsUtils;

/**
 * 代付审核密码控制器
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public class DPayComplexPWDAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 2698434775103370756L;
	private String oldPassword;
	private String newPassword;
	private String customerNo;
	private ServiceConfigFacade serviceConfigFacade;
	private ServiceConfigBean serviceConfigBean;
	private CustomerService customerService;

	/**
	 * 资料下载
	 */
	public String dfFileDownload(){
//		try {
//			Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
//			Customer customer = customerService.findByCustomerNo(auth.getCustomerno());
//			if(customer!=null){
//				getHttpRequest().setAttribute("keymail", customer.getEmail());
//				getHttpRequest().setAttribute("ordermail", customer.getEmail());
//				String phone =customer.getPhoneNo();
//				getHttpRequest().setAttribute("phone", generateSecretPhone(phone));
//			}else{
//				getHttpRequest().setAttribute("keymail", "");
//				getHttpRequest().setAttribute("ordermail", "");
//				getHttpRequest().setAttribute("phone", "");
//			}
//		} catch (Exception e) {
//			logger.error("系统异常:",e);
//		}
		return SUCCESS;
	}
	public CustomerService getCustomerService() {
		return customerService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	public String update(){
		Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
		try {
			serviceConfigBean.setOwnerId(auth.getCustomerno());
			serviceConfigFacade.dfUpdateComplexPwd(serviceConfigBean);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "fail";
		}
		serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
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
	public String findByCustomerNo(){
		Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
		serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
		if(serviceConfigBean==null){
			return NONE;
		}
		String phone =serviceConfigBean.getPhone();
		serviceConfigBean.setPhone(generateSecretPhone(phone));
		return SUCCESS;
	}

	public void getComplexName() throws IOException{
		Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
		serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
		if(serviceConfigBean!= null){
			try {
				this.getHttpResponse().getWriter().write(serviceConfigBean.getPhone());
			} catch (IOException e) {
				this.getHttpResponse().getWriter().write("null");
				logger.error(e.getMessage(), e);
			}
		}
	}

	public void verifyPassword(){
		write(verifyPassword(oldPassword, newPassword));
	}

	public void applyVerify(){
		if(customerNo == null || customerNo.equals("")){
			Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
			write(findPWDApplyVerify(auth.getCustomerno(),oldPassword));
		}else{
			write(findPWDApplyVerify(customerNo,oldPassword));
		}
	}
	public void applyVerifyWithVerifyCode(){
		String  verifyCode =getHttpRequest().getParameter("verifyCode");
		if(customerNo == null || customerNo.equals("")){
			Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
			write(findPWDApplyVerify(auth.getCustomerno(),oldPassword,verifyCode));
		}else{
			write(findPWDApplyVerify(customerNo,oldPassword,verifyCode));
		}
	}

	public String verifyPassword(String oldPassword, String newPassword) {
		if(oldPassword==null || newPassword==null){
			throw new RuntimeException("DPayComplexPWDServiceImpl verifyPassword param is error!");
		}
		String opwd = Md5Util.hmacSign(oldPassword, "DPAY");
		String npwd = Md5Util.hmacSign(newPassword, "DPAY");
		if(opwd.equals(npwd)){
			return "true";
		}
		return "false";
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

	// 手机号 和验证码都相等
	public String findPWDApplyVerify(String customerNo, String password,String verifyCode) {
		try{

			String sessionCode =(String)getSessionMap().get("verifyCode");
			if(sessionCode!=null&&verifyCode!=null&&sessionCode.equals(verifyCode)){
				ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(customerNo);
				if(serviceConfigBean==null){
					throw new RuntimeException("findPWDApplyVerify serviceConfigBean is null!");
				}
				if(serviceConfigBean.getComplexPassword().equals(Md5Util.hmacSign(password, "DPAY"))){
					return "true";
				}
				return "false";

			}else{
				return "false";
			}
		}catch(Exception e){
			return "false";
		}
	}

	// 发送验证码
	@SuppressWarnings("unchecked")
	public void sendVerifyCode(){
		try{
			String type=getHttpRequest().getParameter("type");
			Random random =new Random();
			// 生成验证码6位随机数字
			String verifyCode=""+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10);
			// 放到session中
			getSessionMap().put("verifyCode", verifyCode);
			// 发送手机验证码
			Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
			ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());

			String smsStr="";
			if("0".equals(type)){
				smsStr=String.format(Constant.SMS_AUDIT_TYPE0, verifyCode);
			}else if("1".equals(type)){
				smsStr=String.format(Constant.SMS_OPEN_TYPE1, verifyCode);
			}else if("2".equals(type)){
				smsStr=String.format(Constant.SMS_CLOSE_TYPE2, verifyCode);
			}else if("3".equals(type)){
				smsStr=String.format(Constant.SMS_OPEN_TYPE3, verifyCode);//改成3
			}
			SmsUtils.sendCustom(smsStr, serviceConfigBean.getPhone());
			// 通知前提输入信息
			write("true");
		}catch(Exception e){
			write("false");
		}

	}
	public void updateUsePhoneCheck(){
		try{
			boolean flag =Boolean.parseBoolean(getHttpRequest().getParameter("flag"));
			String verifyCode =getHttpRequest().getParameter("verifyCode");
			String sessionCode =(String)getSessionMap().get("verifyCode");
			if(sessionCode!=null&&verifyCode!=null&&sessionCode.equals(verifyCode)){
				Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
				logger.info("更新开通短信复核功能，customerNo：" + auth.getCustomerno() + "flag:" + flag + " ROLE ：CUSTOMER");
				ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
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
	/**
	 * 自动审核
	 */
	public void updateAutoAudit(){
		try{
			boolean flag =Boolean.parseBoolean(getHttpRequest().getParameter("flag"));
			String verifyCode =getHttpRequest().getParameter("verifyCode");
			if (verifyCode!=null&&!verifyCode.equals("")) {
				String sessionCode =(String)getSessionMap().get("verifyCode");
				if(sessionCode!=null&&verifyCode!=null&&sessionCode.equals(verifyCode)){
					Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
					logger.info("更新代付审核状态，customerNo：" + auth.getCustomerno() + " flag:" + flag + " ROLE ：CUSTOMER");
					ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
					serviceConfigBean.setManualAudit(String.valueOf(flag).toUpperCase());
					serviceConfigFacade.update(serviceConfigBean);
					// 通知前提输入信息
					write("1");
				}else{
					write("2");
				}
			}else {
				Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
				logger.info("更新代付审核状态，customerNo：" + auth.getCustomerno() + " flag:" + flag + " ROLE ：CUSTOMER");
				ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
				serviceConfigBean.setManualAudit(String.valueOf(flag).toUpperCase());
				serviceConfigFacade.update(serviceConfigBean);
				SmsUtils.sendCustom(Constant.SMS_CLOSE_TYPE4, serviceConfigBean.getPhone());
				// 通知前提输入信息
				write("1");
			}
		}catch(Exception e){
			write("0");
		}
	}
	
	public void isUsePhoneCheck(){
		try{
			Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
			ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
			if(Boolean.valueOf(serviceConfigBean.getUsePhoneCheck())){
				write("1");
			}else{
				write("0");
			}
		}catch(Exception e){
			write("0");
		}

	}

	public void checkVerifyCodeEqual(){
		try{
			String verifyCode =getHttpRequest().getParameter("verifyCode");
			String sessionCode =(String)getSessionMap().get("verifyCode");
			if(sessionCode!=null&&verifyCode!=null&&sessionCode.equals(verifyCode)){
				// 通知前提输入信息
				write("1");
			}else{
				write("0");
			}
		}catch(Exception e){
			write("0");
		}
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public void setServiceConfigFacade(ServiceConfigFacade serviceConfigFacade) {
		this.serviceConfigFacade = serviceConfigFacade;
	}

	public void setServiceConfigBean(ServiceConfigBean serviceConfigBean) {
		this.serviceConfigBean = serviceConfigBean;
	}

	public ServiceConfigBean getServiceConfigBean() {
		return serviceConfigBean;
	}
}
