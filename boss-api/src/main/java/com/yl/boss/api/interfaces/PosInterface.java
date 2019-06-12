package com.yl.boss.api.interfaces;

import com.yl.boss.api.bean.Pos;

import java.util.List;
import java.util.Map;

/**
 * POS终端信息信息远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2017年7月14日
 * @version V1.0.0
 */       
public interface PosInterface {
	/**
	 * 根据条件查询POS信息
	 * @param params
	 * @return
	 */
	Map<String,Object> posQuery(Map<String,Object> params);
	
	/**
	 * 查询POS分配信息
	 * @param params
	 * @return
	 */
	Map<String,Object> posAssignOuter(Map<String,Object> params);
	
	/**
	 * POS出库(外部)
	 * @param posCatiArrays
	 * @param agentNo
	 * @param operator
	 */
	void posBatchDeliveryOuter(String[] posCatiArrays, String agentNo, String agentChildNo, String operator);
	
	/**
	 * POS绑定
	 * @param posCatiArrays
	 * @param customerNo
	 * @param operator
	 */
	void posBind(String[] posCatiArrays, String customerNo, String operator);

	/**
	 * 根据pos终端号查询
	 * @param posCati
	 * @return
	 */
	Pos findPosByPosCati(String posCati);

	/**
	 * 根据sn号查询POS
	 * @param posSn
	 * @return
	 */
	Pos findPosByPosSn(String posSn);

	/**
	 * 根据商户号查询POS
	 * @param customerNo
	 * @return
	 */
	List<Pos> findPosByCust(String customerNo);
}
