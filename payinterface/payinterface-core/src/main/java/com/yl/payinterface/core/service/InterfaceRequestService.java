package com.yl.payinterface.core.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.bean.AuthRequestBean;
import com.yl.payinterface.core.bean.InternetbankSalesTradeBean;
import com.yl.payinterface.core.bean.ReceiveTradeBean;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * 接口请求服务
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public interface InterfaceRequestService {

	/**
	 * 支付接口请求记录修改
	 * @param interfaceRequest
	 * @throws BusinessException
	 */
	void modify(InterfaceRequest interfaceRequest);

	/**
	 * 根据支付编号查询
	 * @param interfaceRequestID
	 * @return
	 */
	InterfaceRequest queryByInterfaceRequestID(String interfaceRequestID);

	/**
	 * 根据支付状态查询
	 * @param interfaceRequestCount 最大补单次数
	 * @param page 一批次补单量
	 * @return
	 */
	List<InterfaceRequest> queryNeedReverseOrderInterfaceRequest(int interfaceRequestCount, Page page);

	/**
	 * 接口请求处理
	 * @param internetbankSalesTradeBean 网银业务实体
	 * @return InterfaceRequest
	 * @throws BusinessException
	 */
	InterfaceRequest save(InternetbankSalesTradeBean internetbankSalesTradeBean) throws BusinessException;

	/**
	 * 更新查询接口信息
	 * @param queryInterfaceRequest 查询接口实体信息
	 * @param resultMap 查询结果参数
	 */
	void modifyQueryInterfaceRequest(InterfaceRequest queryInterfaceRequest, Map<String, Object> resultMap);

	/**
	 * 接口请求处理
	 * @param receiveTradeBean 代收业务实体
	 * @return InterfaceRequest
	 * @throws BusinessException
	 */
	InterfaceRequest save(ReceiveTradeBean receiveTradeBean) throws BusinessException;

	/**
	 * @Description 创建接口请求
	 * @param interfaceRequest
	 */
	void save(InterfaceRequest interfaceRequest);
	
	/**
	 * 根据业务编号查询
	 * @param businessOrderID
	 * @return InterfaceRequest
	 */
	InterfaceRequest queryByBusinessOrderID(String businessOrderID);
	
	/**
	 * 查询interfaceRequest
	 * @param page
	 * @param map
	 * @return
	 */
	Page findAllInterfaceRequest(Page page, Map<String, Object> map);
	
	/**
	 * 统计
	 * @param map
	 * @return
	 */
	Map<String, Object> queryAllSum(Map<String, Object> map);
	/**
	 * 
	 * @Description TODO
	 * @param authRequestBean
	 * @return
	 * @throws BusinessException
	 * @date 2017年8月11日
	 */
	public InterfaceRequest save(AuthRequestBean authRequestBean) throws BusinessException;
	/**
	 * 根据日期查询接口订单所有数据（成功）
	 */
	List<InterfaceRequest> allByDate(Map<String, Object> map);
	
	/**
	 * 根据日期查询接口订单所有数据（成功）合计
	 */
	Map<String, Object> totalByJob(Map<String, Object> map);
	
	/**
	 * 根据条件查询提供方接口请求记录
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryInterfaceRequestQuery(Map<String, Object> map);
	/**
	 * 查询接口编号的最后一条状态请求
	 * @return
	 */
	public InterfaceRequest queryOneInterfaceRequestByInterfaceCode(String code, InterfaceTradeStatus status);

	/**
	 * 根据接口编号 接口订单号查询接口请求
	 * @param interfaceRequestID
	 * @param interfaceCode
	 * @return
	 */
	public InterfaceRequest queryByInterfaceRequestID(String interfaceRequestID, String interfaceCode);

    /**
     * 根据接口请求订单号查询
     * @param interfaceOrderId
     * @return
     */
    InterfaceRequest findByInterfaceOrderId(String interfaceOrderId);
}