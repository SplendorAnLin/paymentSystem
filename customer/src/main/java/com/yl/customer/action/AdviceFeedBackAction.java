package com.yl.customer.action;

import java.util.Date;

import com.yl.customer.Constant;
import com.yl.customer.entity.AdviceFeedBack;
import com.yl.customer.entity.Authorization;
import com.yl.customer.service.AdviceFeedBackService;

/**
 * 意见反馈控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月2日
 * @version V1.0.0
 */
public class AdviceFeedBackAction extends Struts2ActionSupport{
	private AdviceFeedBackService adviceFeedBackService;
	private AdviceFeedBack adviceFeedBack;

	/**
	 * 新增反馈意见
	 * @return
	 */
	public String addAdvice(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		adviceFeedBack.setCreateTime(new Date());
		adviceFeedBack.setInitiator(auth.getRealname());
		adviceFeedBack.setPhoneNo(auth.getUsername());
		adviceFeedBackService.create(adviceFeedBack);
		return SUCCESS;
	}
	public AdviceFeedBackService getAdviceFeedBackService() {
		return adviceFeedBackService;
	}
	public void setAdviceFeedBackService(AdviceFeedBackService adviceFeedBackService) {
		this.adviceFeedBackService = adviceFeedBackService;
	}
	public AdviceFeedBack getAdviceFeedBack() {
		return adviceFeedBack;
	}
	public void setAdviceFeedBack(AdviceFeedBack adviceFeedBack) {
		this.adviceFeedBack = adviceFeedBack;
	}
}
