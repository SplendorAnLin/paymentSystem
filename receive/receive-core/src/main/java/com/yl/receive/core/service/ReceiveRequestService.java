package com.yl.receive.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yl.receive.core.entity.ReceiveRequest;
import com.yl.receive.hessian.bean.ReceiveRequestBean;

/**
 * 代收信息服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public interface ReceiveRequestService {
	
	/**
	 * 查询总笔数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	public List<Map<String, String>> orderSumCount(Date orderTimeStart, Date orderTimeEnd,String owner);
	
	/**
	 * 代收成功订单笔数 金额
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	public List<Map<String, String>> orderSum(Date orderTimeStart, Date orderTimeEnd,String owner);
	
	/**
	 * 代收订单
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	public List<Map<String, String>> orderSumByDay(Date orderTimeStart, Date orderTimeEnd,String owner);

	/**
	 * 根据代收单号查询
	 * @param receiveId
	 * @return
	 */
	ReceiveRequestBean findBy(String receiveId);
	
	/**
	 * 根据所有者编码 商户订单号查询
	 * @param ownerId
	 * @param customerOrderCode
	 * @return
	 */
	ReceiveRequestBean findBy(String ownerId, String customerOrderCode);
	
	/**
	 * 定时任务 - 成功订单
	 * @param params
	 * @return
	 */
	List<ReceiveRequest> findByReconJob(Map<String, Object> params);
	
	/**
	 * 定时任务 - 成功订单统计
	 * @param params
	 * @return
	 */
	Map<String, Object> findTotalByJob(Map<String, Object> params);

	
	/**
	 * 代收对账文件
	 */
	List<ReceiveRequestBean> customerRecon(Map<String, Object> params);
	
	/**
	 * 代收队长文件头
	 */
	String customerReconHead(Map<String, Object> params);
	
	/**
	 * 代收数据总合计
	 */
	String customerReconSum(Map<String, Object> params);
}