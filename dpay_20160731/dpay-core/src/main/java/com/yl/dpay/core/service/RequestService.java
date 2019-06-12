package com.yl.dpay.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.enums.RequestStatus;

/**
 * 代付信息业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface RequestService {
	/**
	 * weixin结算查询
	 * @param bean
	 * @return
	 */
	public List<Map<String, Object>> findrequest(Map<String, Object> params, Page page);
	
	public Map<String, Object> findrequestDetail(Map<String, Object> params);
	
	/**
	 * 代付总笔数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	public List<Map<String, String>> dfSuccessCount(Date orderTimeStart, Date orderTimeEnd, String owner);
	
	/**
	 * 查询代付成功笔数 金额
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	public List<Map<String, String>> dfSuccess(Date orderTimeStart, Date orderTimeEnd, List owner);
	
	/**
	 * 代付查询
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @return
	 */
	public String orderSumByDay(Date orderTimeStart, Date orderTimeEnd, String owner, int day);

	/**
	 * 单笔申请
	 * @param request
	 * @return
	 */
	public void create(Request request);

	/**
	 * 更新审核状态
	 * @param request
	 */
	public Request updateAuditStatus(Request request);
	
	/**
	 * 更新状态
	 * @param request
	 */
	public Request updateStatus(Request request);

	/**
	 * 根据流水号查询代付请求
	 * @param flowNo
	 * @return
	 */
	public Request findByFlowNo(String flowNo);
	
	/**
	 * 根据商户请求号查询代付请求
	 * @param requestNo
	 * @param ownerId
	 * @return
	 */
	public Request findByRequestNo(String requestNo,String ownerId);
	
	/**
	 * 根据商户请求号查询代付请求
	 * @param status
	 * @return
	 */
	public List<Request> findByStatus(RequestStatus status);
	
	/**
	 * 修改订单信息
	 * @param request
	 */
	public void update(Request request);
	
	/**
	 * 更新通知信息
	 * @param request
	 */
	public void updateNotify(Request request);
	
	/**
	 * 查询待通知的代付订单
	 * @param count
	 * @param nums
	 * @return List<Request>
	 */
	public List<Request> findWaitNotifyRequest(int count, int nums);
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
	 * 根据ownerId查询结算订单总条数
	 */
	public Map<String, Object> selectRequestsum(Map<String, Object> params);

	/**
	 * 查询成功代付订单
	 * @param params
	 * @return
	 */
	List<Request> findByParams(Map<String, Object> params);

	/**
	 * 定时任务 - 代付记录上送
	 */
	public List<Request> findByCreateTime(Map<String, Object> params);

	/**
	 * 查询成功代付订单合计数据
	 * @param params
	 * @return
	 */
	Map<String, Object> findSumCountAndFee(Map<String, Object> params);

	/**
	 * 代付成功 笔数 金额 手续费
	 */
	public Map<String, Object> customerReconHead(Map<String, Object> params);
	
	/**
	 * 直接插入代付记录（慎用）
	 * @param request
	 */
	public void insert(Request request);

}