package com.yl.dpay.hessian.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.dpay.hessian.beans.RequestBean;
import com.yl.dpay.hessian.beans.ResponseBean;

/**
 * 代付远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月17日
 * @version V1.0.0
 */
public interface DpayFacade {
	
	/**
	 * 单笔代付申请
	 * @param requestBean
	 */
	public ResponseBean singleRequest(RequestBean requestBean);

	/**
	 * 单笔发起申请,接口使用
	 * @param ownerParam
	 * @param param
	 */
	public ResponseBean interfaceSingleRequest(RequestBean requestBean);

	/**
	 * 代付复核
	 * @param flowNo
	 * @param auditStatus
	 * @param operator
	 */
	public ResponseBean audit(String flowNo, String auditStatus, String operator);
	
	/**
	 * 提现
	 * @param customerNo
	 * @param amount
	 * @return
	 */
	public ResponseBean drawCash(String customerNo, double amount);
	
	/**
	 * 分润提现
	 * @param customerNo
	 * @param agentNo
	 * @return
	 */
	public ResponseBean drawCashShareProfit(String agentNo, double amount);
	
	/**
	 * 付款审核
	 * @param flowNo
	 * @param auditStatus
	 * @param operator
	 */
	public ResponseBean remitAudit(String flowNo, String auditStatus, String operator);
	
	/**
	 * APP提现接口
	 * @param customerNo
	 * @param amount
	 * @return
	 */
	public Map<String, Object> appDrawCash(String customerNo, double amount);

	/**
	 * APP调用接口查询提现
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectRequest(Map<String,Object> params);

	/**
	 * APP调用接口查询提现详情
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectRequestDetailed(Map<String, Object> params);
	
	
	/**
	 *根据id查询结算 总条数
	 */
	public Map<String, Object> selectRequestsum(Map<String, Object> params);
	/**
	 * weixin 结算
	 */
	public Page findrequest(Map<String, Object> params,Page page);
	public Map<String, Object> findrequestDetail(Map<String, Object> params);
}
