package com.yl.payinterface.core.service;

import java.util.Map;

import com.yl.boss.api.bean.TransCardBean;
import com.yl.payinterface.core.model.InterfaceRequest;

public interface QuickPayFeeService {

	/**
	 * 返回单位为分的结算金额
	 * 
	 * @param ownerId
	 * @param amount
	 * @return
	 */
	public String getSettleAmountInCents(String ownerId, double amount);

	/**
	 * 返回单位为元的结算金额
	 * 
	 * @param ownerId
	 * @param amount
	 * @return
	 */
	public String getSettleAmount(String ownerId, double amount);

	/**
	 * 返回（总金额 - 快捷手续费）单位为元的结算金额
	 * 
	 * @param ownerId
	 *            商户ID
	 * @param amount
	 *            交易金额
	 * @return
	 */
	public Double getQuickPaySettleAmount(String ownerId, double amount);

	/**
	 * 返回付款手续费
	 * 
	 * @param ownerId
	 *            商户ID
	 * @param amount
	 *            交易金额
	 * @return
	 */
	public String getRemitFee(String ownerId, double amount);

	/**
	 * @Description 保存交易卡历史信息
	 * @param cardNo
	 * @param interfaceRequest
	 * @date 2017年9月12日
	 */
	public void saveTransCardHis(String cardNo, InterfaceRequest interfaceRequest);

	/**
	 * @Description 查询结算卡信息
	 * @param map
	 * @param ownerId
	 * @param cardNo
	 * @date 2017年9月12日
	 */
	public void getSettleInfo(Map<Object, Object> map, String ownerId, String cardNo);
	
	/**
	 * 
	 * @Description 根据交易卡获取结算卡信息
	 * @param ownerId
	 * @param cardNo
	 * @return
	 * @date 2017年12月18日
	 */
	TransCardBean getSettleInfo(String ownerId, String cardNo);

	/**
	 * @Description 根据接口请求号查询结算卡信息
	 * @param interfaceRequestId
	 * @return
	 * @date 2017年9月12日
	 */
	public TransCardBean getSettleInfoByInterfaceRequestId(String interfaceRequestId);
	
	/**
	 * 
	 * @Description 根据接口请求号查询交易卡信息
	 * @param interfaceRequestId
	 * @return
	 * @date 2017年11月4日
	 */
	public TransCardBean getTransCardInfoByInterfaceRequestId(String interfaceRequestId);

	/**
	 * 
	 * @Description 获取预冻金额
	 * @param ownerId
	 *            商户编号
	 * @param amount
	 *            交易金额
	 * @param userSettleAmount
	 *            大商户下用户提交的金额
	 * @return
	 * @date 2017年9月13日
	 */
	public Double getPreFreezeAmount(String ownerId, Double amount, String userSettleAmount);
	
	/**
	 * 
	 * @Description 获取快捷费率
	 * @param ownerId
	 * @return
	 * @date 2017年11月20日
	 */
	public String getQuickPayFee(String ownerId);
}
