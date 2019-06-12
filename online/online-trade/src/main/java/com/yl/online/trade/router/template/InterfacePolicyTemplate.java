package com.yl.online.trade.router.template;

import com.yl.online.model.bean.InterfacePolicyProfile;

/**
 * 模板（统一处理）
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
public abstract class InterfacePolicyTemplate {
	
	/**
	 * 路由选择具体接口信息
	 * @param interfacePolicyProfile 接口策略配置 
	 * @param amount 交易金额
	 * @return
	 */
	public abstract String handleSpecifiedRouter(InterfacePolicyProfile interfacePolicyProfile, Double amount);
	
	/**
	 * 模板方法（栈帧入口）
	 * @param interfacePolicyProfile 接口策略配置 
	 * @param amount 交易金额
	 * @return
	 */
	public final String route(InterfacePolicyProfile interfacePolicyProfile, Double amount) {
		return this.handleSpecifiedRouter(interfacePolicyProfile, amount);
	}
}
