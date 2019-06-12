package com.yl.receive.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;

import com.yl.receive.core.entity.ReceiveRequest;
import com.yl.receive.hessian.bean.ReceiveRequestBean;

/**
 * 代收请求数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public interface ReceiveRequestDao {
	
	
	/**
	 * 查询总笔数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	public List<Map<String, String>> orderSumCount(@Param("orderTimeStart")String orderTimeStart, 
			@Param("orderTimeEnd")String orderTimeEnd,@Param("owner")String owner);
	
	/**
	 * 代收成功金额 - 笔数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	public List<Map<String,String>> orderSum(@Param("orderTimeStart")String orderTimeStart, 
			@Param("orderTimeEnd")String orderTimeEnd, @Param("owner")String owner);
	
	/**
	 * 代收
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @return
	 */
	public List<Map<String,String>> orderSumByDay(@Param("orderTimeStart")String orderTimeStart, 
			@Param("orderTimeEnd")String orderTimeEnd, @Param("owner")String owner);

	/**
	 * 
	 * @Description 更新订单
	 * @param receiveRequest
	 * @param newVersion
	 * @date 2017年1月8日
	 */
	public void update(@Param("receiveRequest") ReceiveRequest receiveRequest, @Param("newVersion") Long newVersion);

	/**
	 * 
	 * @Description 根据代收订单号查找订单
	 * @param receiveId
	 * @return
	 * @date 2017年1月8日
	 */
	public ReceiveRequest queryByReceiveId(@Param("receiveId") String receiveId);

	/**
	 * 
	 * @Description 根据商户订单号查找订单
	 * @param seqId
	 * @param ownerId
	 * @return
	 * @date 2017年1月8日
	 */
	public ReceiveRequest queryBySeqId(@Param("seqId") String seqId, @Param("ownerId") String ownerId);

	/**
	 * 
	 * @Description 保存
	 * @param receiveRequest
	 * @date 2017年1月8日
	 */
	public void save(ReceiveRequest receiveRequest);

	
	/**
	 * 定时任务 - 成功订单
	 * @param params
	 * @return
	 */
	List<ReceiveRequest> findByReconJob(@Param("params")Map<String, Object> params);
	
	/**
	 * 定时任务 - 成功订单统计
	 * @param params
	 * @return
	 */
	Map<String, Object> findTotalByJob(@Param("params")Map<String, Object> params);

	
	/**
	 * 代收对账文件
	 */
	public List<ReceiveRequest> customerRecon(@Param("params")Map<String, Object> params);
	
	/**
	 * 代收队长文件头
	 */
	public Map<String, Object> customerReconHead(@Param("params")Map<String, Object> params);
	
	/**
	 * 代收数据总合计
	 */
	public Map<String, Object> customerReconSum(@Param("params")Map<String, Object> params);
}