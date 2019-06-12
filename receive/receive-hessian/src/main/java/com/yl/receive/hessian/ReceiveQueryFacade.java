package com.yl.receive.hessian;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;
import com.yl.receive.hessian.bean.ReceiveRequestBean;
import com.yl.receive.hessian.bean.RouteConfigBean;

/**
 * 代收查询远程服务
 * 
 * @author 聚合支付有限公司
 * @since 2017年1月7日
 * @version V1.0.0
 */
public interface ReceiveQueryFacade {
	
	/**
	 * 查询所有订单数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	public List<Map<String,String>> orderSumCount(Date orderTimeStart, Date orderTimeEnd,String owner);
	
	/**
	 * 代收成功 笔数 金额
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param owner
	 * @return
	 */
	public List<Map<String,String>> orderSum(Date orderTimeStart, Date orderTimeEnd,String owner);
	
	/**
	 * 代收收入   
	 */
	public List<Map<String,String>> orderSumByDay(Date orderTimeStart, Date orderTimeEnd,String owner);
	
	/**
	 * 根据代收单号查询
	 * @param receiveId
	 * @return
	 */
	ReceiveRequestBean findByRec(String receiveId);
	
	/**
	 * 根据所有者编码 商户订单号查询
	 * @param ownerId
	 * @param customerOrderCode
	 * @return
	 */
	ReceiveRequestBean findBy(String ownerId, String customerOrderCode);
	/**
	 * 根据用户ID查询代收配置
	 * @param ownerId
	 * @return
	 */
	public ReceiveConfigInfoBean queryReceiveConfigBy(String ownerId);
	/**
	 * 分页查询代收配置
	 * @param queryParams 查询参数
	 * @param page 分页实体
	 * @return	ReceiveConfigInfo 代收配置信息
	 */
	public List<ReceiveConfigInfoBean> findAllRecfByPage(Map<String, Object> queryParams, Page<?> page);
	
	/**
	 * 添加代收配置
	 * @param receiveConfigInfo
	 */
	public void insertReceiveConfig(ReceiveConfigInfoBean receiveConfigInfo);
	
	/**
	 * 更新代收配置
	 */
	public void updateReceiveConfig(ReceiveConfigInfoBean receiveConfigInfo);
	
	/**
	 * 创建代付路由
	 * @param routeConfigBean
	 * @param oper
	 */
	public void createRoute(RouteConfigBean routeConfigBean,String oper);
	
	/**
	 * 更新代付路由
	 * @param routeConfigBean
	 * @param oper
	 */
	public void updateRoute(RouteConfigBean routeConfigBean,String oper);
	
	/**
	 * 查询代付路由
	 * @param code
	 * @return
	 */
	public RouteConfigBean findByCode(String code);
	
	/**
	 * 查询代付默认路由
	 * @return
	 */
	public RouteConfigBean findRouteConfig();
	
	/**
	 * 根据条件查询所有代付路由
	 * @return
	 */
	public List<RouteConfigBean> findRouteConfigList(RouteConfigBean routeConfigBean);

	/**
	 * 代收对账文件
	 */
	public List<ReceiveRequestBean> customerRecon(Map<String, Object> params);
	
	/**
	 * 代收队长文件头
	 */
	public String customerReconHead(Map<String, Object> params);
	
	/**
	 * 代收数据总合计
	 */
	public String customerReconSum(Map<String, Object> params);
}