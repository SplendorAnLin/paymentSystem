package com.yl.recon.core.service;

import java.util.Date;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月16
 * @desc 生成对账文件的服务
 **/
public interface CreateReconFileService {

	/**
	 * 生成dpay订单对账文件
	 */
	void createRemitOrderReconFileTask(Date date);

	/**
	 * 生成online订单对账文件
	 */
	void createOnlineOrderReconFileTask(Date date);

	/**
	 * 生成real_auth对账文件
	 */
	void createRealAuthOrderReconFileTask(Date date);

	/**
	 * 生成payinterface对账文件
	 */
	void createPayInterfaceReconFileTask(Date date);

	/**
	 * 生成account对账文件
	 */
	void createAccountReconFileTask(Date date);
}
