package com.yl.payinterface.core.hessian;

import com.yl.payinterface.core.bean.BindCardInfoBean;

public interface BindCardInfoHessianService {

	/**
	 * 
	 * @Description 更新绑卡记录为失败
	 * @date 2017年9月1日
	 */
	public void updateFailed(String cardNo, String interfaceInfoCode);

	/**
	 * 
	 * @Description 保存
	 * @param bindCardInfoBean
	 * @date 2017年9月1日
	 */
	public void save(BindCardInfoBean bindCardInfoBean);

	/**
	 * 
	 * @Description 更新
	 * @param bindCardInfoBean
	 * @date 2017年9月1日
	 */
	public void update(BindCardInfoBean bindCardInfoBean);

	/**
	 * 
	 * @Description 查找
	 * @param cardNo
	 * @param interfaceInfoCode
	 * @return
	 * @date 2017年9月1日
	 */
	public BindCardInfoBean find(String cardNo, String interfaceInfoCode);
	
	/**
	 * 
	 * @Description 保存
	 * @param bindCardInfoBean
	 * @date 2017年9月1日
	 */
	public void saveOrUpdate(BindCardInfoBean bindCardInfoBean);
	
	
	/**
	 * 
	 * @Description 查找有效绑卡信息
	 * @param cardNo
	 * @param interfaceInfoCode
	 * @return
	 * @date 2017年9月2日
	 */
	public BindCardInfoBean findEffective(String cardNo, String interfaceInfoCode);

}
