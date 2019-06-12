package com.yl.receive.front.service;

import com.yl.receive.front.model.QueryResponse;
import com.yl.receive.front.model.ReceiveRequest;
import com.yl.receive.front.model.TradeResponse;

/**
 * 代收请求业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
public interface ReceiveRequestService {

	/**
	 * 代收发起（接口）
	 * @param receiveRequest 代收请求Bean
	 * @return TradeResponse 代收请求结果Bean
	 */
	public TradeResponse singleRequest(ReceiveRequest receiveRequest);

	/**
	 * 代收查询（接口）
	 * @param customerNo 商户编号
	 * @param customerOrderCode 商户订单号
	 * @return QueryResponse 代收查询结果Bean
	 */
	public QueryResponse singleQuery(String customerNo, String customerOrderCode);

}
