package com.yl.realAuth.hessian;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.hessian.bean.AuthOrderQueryBean;
import com.yl.realAuth.hessian.bean.AuthResponseResult;
import com.yl.realAuth.hessian.bean.CreateOrderBean;
import com.yl.realAuth.model.RealNameAuthOrder;

/**
 * 实名认证交易Hessian服务
 * @author Shark
 * @since 2015年6月3日
 */
public interface AuthTradeHessianService {
	/**
	 * 实名认证订单查询
	 * @param page 分页
	 * @param authOrderQueryBean 查询实体
	 * @return
	 */
	public Page authOrderQuery(Page page, AuthOrderQueryBean authOrderQueryBean);

	/**
	 * 实名认证订单合计
	 * @param page
	 * @param authOrderQueryBean
	 * @return
	 */
	public String authOrderTotal(AuthOrderQueryBean authOrderQueryBean);

	/**
	 * 根据订单号查询实名认证实体信息
	 * @param orderCode 订单号
	 * @return 请求实体
	 */
	public RealNameAuthOrder queryAuthOrderByCode(String orderCode);

	/**
	 * 实名认证订单信息导出
	 * @param params 查询参数
	 * @return
	 */
	public String authOrderExport(AuthOrderQueryBean authOrderQueryBean);

	/**
	 * 创建交易订单
	 * @param createOrderBean 创建交易订单参数Bean
	 * @return 订单号
	 */
	AuthResponseResult createOrder(CreateOrderBean createOrderBean);
}
