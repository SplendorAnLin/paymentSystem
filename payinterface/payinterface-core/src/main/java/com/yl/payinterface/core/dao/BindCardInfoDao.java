package com.yl.payinterface.core.dao;

import org.apache.ibatis.annotations.Param;

import com.yl.payinterface.core.model.BindCardInfo;

public interface BindCardInfoDao {

	/**
	 * 
	 * @Description 更新绑卡记录为失败
	 * @date 2017年9月1日
	 */
	public void updateFailed(@Param("cardNo")String cardNo, @Param("interfaceInfoCode")String interfaceInfoCode);

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
	public BindCardInfo find(@Param("cardNo")String cardNo, @Param("interfaceInfoCode")String interfaceInfoCode);
	
	/**
	 * 
	 * @Description 查找有效绑卡信息
	 * @param cardNo
	 * @param interfaceInfoCode
	 * @return
	 * @date 2017年9月2日
	 */
	public BindCardInfo findEffective(@Param("cardNo")String cardNo, @Param("interfaceInfoCode")String interfaceInfoCode);
	

}
