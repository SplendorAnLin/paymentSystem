package com.yl.boss.service;

/**
 * 商户历史记录业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface CustomerHistoryService {
	
	/**
	 * 获取最近一次拒绝信息
	 */
	String getLastInfo(String customerNo);
	
	/**
	 * 再次提交清空上次拒绝原因
	 */
	void deleteLastInfo(String customerNo);
}