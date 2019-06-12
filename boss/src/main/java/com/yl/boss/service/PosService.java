package com.yl.boss.service;

import java.util.List;
import java.util.Map;

import com.yl.boss.entity.Pos;
import com.yl.boss.entity.PosPurchase;

/**
 * POS终端业务接口
 *
 * @author 聚合支付有限公司
 * @since 2017年7月10日
 * @version V1.0.0
 */
public interface PosService {
	
	/**
	 * Pos终端单条新增
	 * @param pos
	 */
	void posAdd(Pos pos);
	
	/**
	 * 根据ID查询单条Pos终端信息
	 * @param id
	 * @return 单条Pos终端信息
	 */
	Pos posById(Long id);
	
	/**
	 * 根据PosBean修改单条终端信息
	 * @param pos
	 */
	void posUpdate(Pos pos);
	
	/**
	 * 根据POS终端号判断当前是否存在
	 * @param posCati
	 * @return
	 */
	boolean posCatiBool(String posCati);
	
	/**
	 * 根据商户号返回POS列表
 	 * @param customerNo
	 * @return
	 */
	List<Pos> findPos(String customerNo);
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
	 * APP POS申请
	 * @param posPurchase
	 */
	void posPurchaseAdd(PosPurchase posPurchase);
	
	/**
	 * POS批量入库
	 * @param pos pos信息
	 * @param posSnNumber posSn条数
	 */
	String posAllAdd(Pos pos, int posSnNumber, String operator);
	
	/**
	 * 根据商户编号查询当前商户最大POS终端号
	 * @return
	 */
	String posCatiMax();
	
	/**
	 * 根据批量POS终端号、服务商编号、操作员进行批量出库操作
	 * @param posCatiArrays
	 * @param agentNo
	 * @param operator
	 */
	void posBatchDelivery(String[] posCatiArrays,String agentNo,String operator);
	
	/**
	 * 根据批量POS终端号、商户编号、操作员进行批量绑定操作
	 * @param posCatiArrays
	 * @param customerNo
	 * @param operator
	 */
	void posBind(String[] posCatiArrays,String customerNo,String operator);
	
	/**
	 * POS出库(外部)
	 * @param posCatiArrays
	 * @param agentNo
	 * @param operator
	 */
	void posBatchDeliveryOuter(String[] posCatiArrays, String agentNo, String agentChildNo, String operator);
	
	/**
	 * 根据商户编号查询POS信息(OEM)
	 * @param customerNo
	 * @return
	 */
	List findPosOemByCustomerNo(String customerNo);
}
