package com.yl.receive.hessian;

import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;
import com.yl.receive.hessian.enums.ReceiveConfigStatus;

/**
 * 商户代收配置远程服务
 * 
 * @author 聚合支付有限公司
 * @since 2017年1月7日
 * @version V1.0.0
 */
public interface CustomerReceiveConfigHessian {
	
	/**
	 * 根据所有者编号查询
	 * @param ownerId
	 * @return
	 */
	ReceiveConfigInfoBean findByOwnerId(String ownerId);
	
	/**
	 * 根据所有者编号 状态查询
	 * @param ownerId
	 * @param receiveConfigStatus
	 * @return
	 */
	ReceiveConfigInfoBean findByOwnerIdAndStatus(String ownerId, ReceiveConfigStatus receiveConfigStatus);

	/**
	 * 密钥修改
	 * @param receiveConfigInfoBean
	 */
	public void updateKeys(ReceiveConfigInfoBean receiveConfigInfoBean);

}
