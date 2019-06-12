package com.yl.dpay.core.dao;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import org.apache.ibatis.annotations.Param;

import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.enums.RequestStatus;

/**
 * 代付申请数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface RequestDao extends BaseDao<Request> {
	
	/**
	 *weixin 查询结算
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findAllrequest(@Param("params") Map<String, Object> params,@Param("page")Page page);
	
	Map<String, Object> findrequestDetail(@Param("params") Map<String, Object> params);
	
	/**
	 * 代付总笔数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	public List<Map<String,String>> dfSuccessCount(@Param("orderTimeStart")String orderTimeStart, 
			@Param("orderTimeEnd")String orderTimeEnd,@Param("owner")String owner);
	
	public List<Map<String,String>> orderAllSumByDay(@Param("orderTimeStart")String orderTimeStart, 
			@Param("orderTimeEnd")String orderTimeEnd,@Param("owner")String owner);
	/**
	 * 代付
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @return
	 */
	public List<Map<String,String>> orderSumByDay(@Param("orderTimeStart")String orderTimeStart, 
			@Param("orderTimeEnd")String orderTimeEnd,@Param("owner")String owner);
	
	/**
	 * 查找代付申请
	 * @param requestNo 申请编号
	 * @return
	 */
	public Request find(@Param("requestNo") String requestNo, @Param("ownerId") String ownerId);

	/**
	 * 代付请求更新
	 * @param request
	 */
	public void update(Request request);

	/**
	 * 查找代付申请
	 * @param flowNo
	 * @return
	 */
	public Request findByFlowNo(@Param("flowNo") String flowNo);
	
	/**
	 * 查找代付申请
	 * @param requestNo
	 * @param ownerId
	 * @return
	 */
	public Request findByRequestNo(@Param("requestNo") String requestNo,@Param("ownerId")String ownerId);
	
	/**
	 * 查找代付申请
	 * @param status
	 * @return
	 */
	public List<Request> findByStatus(@Param("status")RequestStatus status);
	
	
	/**
	 * 根据通知次数查询指定条数
	 * @param count
	 * @param nums
	 * @return List<Request>
	 */
	public List<Request> findWaitNotifyRequest(@Param("count")int count, @Param("nums")int nums);
	
	/**
	 * 根据时间/金额/收款人查询
	 * @param accountName
	 * @param amount
	 * @param createDate
	 * @return
	 */
	public Request findByAccNameAmtDay(@Param("accountName")String accountName, @Param("accountNo")String accountNo, 
			@Param("amount")double amount, @Param("startDate")String startDate, @Param("endDate")String endDate);
	
	/**
	 * 根据日期查询 代付成功笔数 金额
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	public List<Map<String, String>> dfSuccess(@Param("orderTimeStart")String orderTimeStart, 
			@Param("orderTimeEnd")String orderTimeEnd,@Param("owner")List owner);
	/**
	 * APP调用接口查询提现
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> selectRequest(@Param("params")Map<String,Object> params);
	/**
	 * APP调用接口查询提现详情
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> selectRequestDetailed(@Param("params")Map<String,Object> params);
	
	/**
	 * 根据 ownerId查询总条数
	 */
	public Map<String,Object>  selectRequestsum(@Param("params")Map<String,Object> params);
	
		/**
	 * 查询成功代付订单
	 * @param params
	 * @return
	 */
	List<Request> findByParams(@Param("params")Map<String, Object> params);
	
	/**
	 * 查询成功代付订单合计数据
	 * @param params
	 * @return
	 */
	Map<String, Object> findSumCountAndFee(@Param("params")Map<String, Object> params);

	/**
	 * 定时任务 - 代付记录上送
	 * @param params
	 * @return
	 */
	public List<Request> findByCreateTime(@Param("params")Map<String, Object> params);
	
	/**
	 * 代付成功 笔数 金额 手续费
	 */
	public Map<String, Object> customerReconHead(@Param("params")Map<String, Object> params);
}