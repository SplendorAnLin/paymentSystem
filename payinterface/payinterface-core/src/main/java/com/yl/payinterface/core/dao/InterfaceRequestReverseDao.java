package com.yl.payinterface.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yl.payinterface.core.model.InterfaceRequestReverse;

/**
 * 支付接口补单初始化数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
@Repository("interfaceRequestReverseDao")
public interface InterfaceRequestReverseDao {

	/**
	 * 根据支付接口订单号查询请求接口信息
	 * @param interfaceRequestID 支付接口订单号
	 * @return InterfaceRequestReverse 接口补单信息
	 */
	public InterfaceRequestReverse queryInterfaceRequestReverse(@Param("interfaceRequestID")String interfaceRequestID);

	/**
	 * 记录异常支付接口订单信息
	 * @param interfaceRequest 支付接口订单
	 */
	public void save(InterfaceRequestReverse reverseInterfaceRequest);

	/**
	 * 批量查询补单数据
	 * @param maxCount 最大补单次数
	 * @param maxNum 一次补单数
	 * @return List<InterfaceRequestReverse> 需补单数据
	 */
	public List<InterfaceRequestReverse> queryNeedReverseOrderInterfaceRequest(@Param("maxCount")int maxCount, @Param("maxNum")int maxNum);

	/**
	 * 更新接口补单流水
	 * @param interfaceRequestReverse 接口补单流水实体
	 */
	public void modifyInterfaceRequestReverse(@Param("interfaceRequestReverse")InterfaceRequestReverse interfaceRequestReverse);

	/**
	 * 更新接口补单次数
	 * @param originalInterfaceRequestReverse 待补单支付接口
	 */
	public void modifyReverseCount(@Param("originalInterfaceRequestReverse")InterfaceRequestReverse originalInterfaceRequestReverse);
	
	/**
	 * 
	 * @Description 查询条码要补单的记录
	 * @param maxCount
	 * @param maxNum
	 * @return
	 * @date 2017年6月12日
	 */
	public List<InterfaceRequestReverse> queryMicropayReverseOrderInterfaceRequest(@Param("maxCount")int maxCount, @Param("maxNum")int maxNum);

}
