package com.yl.payinterface.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.model.InterfaceRequestReverse;

/**
 * 补单接口信息处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
@Service("interfaceRequestReverseService")
public interface InterfaceRequestReverseService {

	/**
	 * 根据支付接口订单号查询请求接口信息
	 * @param interfaceRequestID 支付接口订单号
	 * @return InterfaceRequestReverse 接口补单信息
	 */
	public InterfaceRequestReverse queryInterfaceRequestReverse(String interfaceRequestID);

	/**
	 * 记录异常支付接口订单信息
	 * @param interfaceRequest 支付接口订单
	 */
	public void recordInterfaceRequestReverse(InterfaceRequest interfaceRequest, String payType);

	/**
	 * 批量查询补单数据
	 * @param maxCount 最大补单次数
	 * @param maxNum 一次补单数
	 * @return List<InterfaceRequestReverse> 需补单数据
	 */
	public List<InterfaceRequestReverse> queryNeedReverseOrderInterfaceRequest(int maxCount, int maxNum);

	/**
	 * 更新接口补单流水
	 * @param interfaceRequestReverse 接口补单流水实体
	 */
	public void modifyInterfaceRequestReverse(InterfaceRequestReverse interfaceRequestReverse);

	/**
	 * 更新接口补单次数
	 * @param originalInterfaceRequestReverse 待补单支付接口
	 */
	public void modifyReverseCount(InterfaceRequestReverse originalInterfaceRequestReverse);
	
	/**
	 * 
	 * @Description 条码补单
	 * @param maxCount
	 * @param maxNum
	 * @return
	 * @date 2017年6月12日
	 */
	public List<InterfaceRequestReverse> queryMicropayReverseOrderInterfaceRequest(int maxCount, int maxNum);
}
