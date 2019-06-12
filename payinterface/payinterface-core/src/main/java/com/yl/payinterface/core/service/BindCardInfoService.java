package com.yl.payinterface.core.service;

import com.yl.payinterface.core.model.BindCardInfo;

public interface BindCardInfoService {
	
	/**
	 * 
	 * @Description 更新绑卡记录为失败
	 * @date 2017年9月1日
	 */
	public void updateFailed(String cardNo, String interfaceInfoCode);

	/**
	 * 
	 * @Description 保存
	 * @param bindCardInfo
	 * @date 2017年9月1日
	 */
	public void save(BindCardInfo bindCardInfo);

	/**
	 * 
	 * @Description 更新
	 * @param bindCardInfo
	 * @date 2017年9月1日
	 */
	public void update(BindCardInfo bindCardInfo);
	
	/**
	 * 
	 * @Description 查找
	 * @param cardNo
	 * @param interfaceInfoCode
	 * @return
	 * @date 2017年9月1日
	 */
	public BindCardInfo find(String cardNo, String interfaceInfoCode);
	
	/**
	 * 
	 * @Description 保存
	 * @param bindCardInfo
	 * @date 2017年9月1日
	 */
	public void saveOrUpdate(BindCardInfo bindCardInfo);
	
	
	/**
	 * 
	 * @Description 查找有效绑卡信息
	 * @param cardNo
	 * @param interfaceInfoCode
	 * @return
	 * @date 2017年9月2日
	 */
	public BindCardInfo findEffective(String cardNo, String interfaceInfoCode);
}
