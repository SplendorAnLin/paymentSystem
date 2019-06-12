package com.yl.recon.core.service;

import com.yl.recon.api.core.bean.MyInterfaceInfoBean;
import com.yl.recon.core.entity.ReconOrder;
import com.yl.recon.core.entity.order.TotalOrder;
import com.yl.recon.core.enums.ReconExceptionType;
import com.yl.recon.core.enums.ReconFileType;

import java.util.List;
import java.util.Map;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月08
 * @desc 对账服务公共处理类
 **/
public interface CommonReconService {

	/**
	 * 组装单边金额和笔数
	 *
	 * @param reconOrder
	 * @param amount
	 * @param isAorB
	 */
	void installFailAmount(ReconOrder reconOrder, Double amount, String isAorB);


	/**
	 * 组装失败原因
	 *
	 * @param reconOrder
	 * @param reconExceptionType
	 */
	void installFailReason(ReconOrder reconOrder, ReconExceptionType reconExceptionType);


	/**
	 * 获取对账文件里面的总金额和总笔数
	 * @param params
	 * @param reconFileType
	 * @return
	 */
	TotalOrder getTotalOrdersByType(Map <String, Object> params, ReconFileType reconFileType);


	/**
	 * 查询有效的接口编号和名称
	 * @return
	 */
	List<MyInterfaceInfoBean> queryInterfaceInfo();

	/**
	 * 判断对应的通道对账文件是否存在
	 * @param code
	 * @return
	 */
	boolean checkInterfaceCode(String code);
}
