package com.yl.boss.action;

import javax.annotation.Resource;

import com.yl.boss.entity.MccInfo;
import com.yl.boss.service.MccService;

/**
 * Mcc控制器
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月13日
 * @version V1.0.0
 */
public class MccAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 1L;

	@Resource
	MccService mccService;
	
	private MccInfo mccInfo;
	
	private String msg;
	
	/**
	 * Mcc新增
	 * @return
	 */
	public String mccAdd(){
		try {
			mccService.mccAdd(mccInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 根据ID查询单条信息
	 * @return
	 */
	public String mccById(){
		try {
			mccInfo = mccService.mccById(Long.parseLong(getHttpRequest().getParameter("id")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * Ajax请求，根据当前mcc查询判断当前是否存在
	 * @return
	 */
	public String mccAjaxByMcc(){
		try {
			Boolean mcc = mccService.mccByMcc(getHttpRequest().getParameter("mcc"));
			if(mcc == true){
				msg = "true";
			}else {
				msg = "false";
			}
		} catch (Exception e) {
			msg = "false";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * Mcc修改
	 * @return
	 */
	public String mccUpdate(){
		try {
			mccService.mccUpdate(mccInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public MccInfo getMccInfo() {
		return mccInfo;
	}

	public void setMccInfo(MccInfo mccInfo) {
		this.mccInfo = mccInfo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
