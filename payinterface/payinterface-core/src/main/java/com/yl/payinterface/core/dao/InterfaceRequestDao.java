package com.yl.payinterface.core.dao;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.model.InterfaceRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 接口请求数据处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
public interface InterfaceRequestDao {

	/**
	 * 支付接口请求记录新增
	 * @param interfaceRequest
	 */
	void create(InterfaceRequest interfaceRequest);

	/**
	 * 支付接口请求记录修改
	 * @param interfaceRequest
	 * @param newVersion
	 * @return
	 */
	void update(@Param("interfaceRequest")InterfaceRequest interfaceRequest, @Param("newVersion")Long newVersion);

	/**
	 * 根据接口请求订单号查询支付接口请求记录
	 * @param interfaceRequestID
	 * @return
	 */
	InterfaceRequest findByInterfaceRequestID(@Param("interfaceRequestID")String interfaceRequestID);

	/**
	 * 根据支付状态查询
	 * @param interfaceRequestCount 最大补单次数
	 * @param page 一批次补单量
	 * @return
	 */
	List<InterfaceRequest> queryNeedReverseOrderInterfaceRequest(@Param("interfaceRequestCount")int interfaceRequestCount, @Param("page")Page page);

	/**
	 * 更新查询接口信息
	 * @param queryInterfaceRequest 查询接口实体信息
	 */
	void updateQueryInterfaceRequest(@Param("queryInterfaceRequest")InterfaceRequest queryInterfaceRequest);
	
	/**
	 * 根据业务编号查询
	 * @param businessOrderID
	 * @return InterfaceRequest
	 */
	InterfaceRequest queryByBusinessOrderID(@Param("businessOrderID")String businessOrderID);
	
	/**
	 * 
	 * @param page
	 * @param param
	 * @return
	 */
	List<InterfaceRequest> findAllInterfaceRequest(@Param("page")Page page, @Param("params")Map<String, Object> param);

	/**
	 * 查询统计
	 * @param param
	 * @return
	 */
	Map<String, Object> queryAllSum(@Param("params")Map<String, Object> param);
	
	/**
	 * 根据日期查询接口订单所有数据（成功）
	 */
	List<InterfaceRequest> allByDate(@Param("params")Map<String, Object> param);
	
	/**
	 * 根据日期查询接口订单所有数据（成功）合计
	 */
	Map<String, Object> totalByJob(@Param("params")Map<String, Object> map);
	
	/**
	 * 根据条件查询提供方接口请求记录
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryInterfaceRequestQuery(@Param("params")Map<String, Object> map);
	/**
	 * 查询接口编号的最后一条状态请求
	 * @return
	 */
	InterfaceRequest queryOneInterfaceRequestByInterfaceCode(@Param("code")String code,@Param("status")InterfaceTradeStatus status);

	/**
	 * 根据接口编号 接口订单号 查询接口请求
	 * @param interfaceRequestID
	 * @param interfaceCode
	 * @return
	 */
	InterfaceRequest queryByInterfaceCodeAndRequestId(@Param("interfaceRequestID") String interfaceRequestID, @Param("interfaceCode") String interfaceCode);

    /**
     * 根据接口请求订单号查询
     * @param interfaceOrderID
     * @return
     */
    InterfaceRequest findByInterfaceOrderId(@Param("interfaceOrderID") String interfaceOrderID);
}