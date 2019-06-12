package com.yl.payinterface.core.hessian;

import java.util.List;
import java.util.Map;
import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;

/**
 * 提供方接口请求记录远程服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public interface InterfaceRequestHessianService {

	/**
	 * 分页查询支付接口请求记录
	 * @param page
	 * @param request
	 * @param params
	 * @return
	 */
	Page queryAll(Page page, Map<String, Object> map);

	/**
	 * @Description 根据条件查询合计
	 * @param request
	 * @param params
	 * @return
	 * @see 需要参考的类或方法
	 */
	String queryAllSum(Map<String, Object> map);

	/**
	 * 根据接口请求号查询接口请求记录
	 * @param code
	 * @return
	 */
	InterfaceRequestQueryBean findRequestByInterfaceReqId(String interfaceReqId);

	/**
	 * 根据条件查询接口请求记录
	 * @param interfaceRequestBean
	 * @return
	 */
	// MapReduceResults<InterfaceRequestQueryBean> findRequestBy(InterfaceRequestBean interfaceRequestBean);

	/**
	 * 根据提供方编码批量查询提供方简称
	 * @return
	 */
	String findProviderNameByProvideCode(String providerCode);

	/**
	 * 查询所有接口编号
	 * @return
	 */
	List<String> findAllInterfaceInfoCode();

	/**
	 * 根据支付接口编号批量查询支付接口简称
	 * @param interfaceInfoCode
	 * @return
	 */
	String findInterfaceNameByInterfaceCode(String interfaceInfoCode);

	/**
	 * @Description 根据ID查询
	 * @param id
	 * @return
	 * @see 需要参考的类或方法
	 */
	InterfaceRequestQueryBean findById(String id);
	
	/**
	 * 根据条件查询支付接口请求记录
	 * @param page
	 * @param request
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryInterfaceRequestQuery(Map<String, Object> map);

    /**
     * 根据接口请求订单号查询
     * @param interfaceOrderId
     * @return
     */
    InterfaceRequestQueryBean findByInterfaceOrderId(String interfaceOrderId);
}