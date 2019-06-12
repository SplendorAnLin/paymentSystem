package com.yl.boss.dao;

import java.util.List;
import java.util.Map;

import com.yl.boss.entity.Pos;
import com.yl.boss.entity.PosPurchase;

/**
 * POS终端数据访问接口
 *
 * @author 聚合支付有限公司
 * @since 2017年7月10日
 * @version V1.0.0
 */
public interface PosDao {

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
	 * 查询待同步信息
	 * @return
	 */
	List<Pos> findSyncPos();
	
	/**
	 * 根据POS终端号判断当前是否存在
	 * @param posCati
	 * @return
	 */
	boolean posCatiBool(String posCati);
	/**
	 * 根据商户号返回小POS列表
 	 * @param customerNo
	 * @return
	 */
	List<Pos> findPos(String customerNo);
	
	/**
	 * POS 申请记录
	 */
	void posPurchaseAdd(PosPurchase posPurchase);
	
	/**
	 * 根据商户编号查询当前商户最大POS终端号
	 * @return
	 */
	String posCatiMax();
	
	/**
	 * 根据POS终端号，查询POS信息
	 * @param posCati
	 * @return
	 */
	Pos posByPosCati(String posCati);
	/**
	 * 根据sn号查询POS
	 * @param posSn
	 * @return
	 */
	Pos findPosByPosSn(String posSn);
	/**
	 * 检查posSn是否存在
	 * @param posSn
	 * @return
	 */
	List<String> posPosSnExists(String posSn);
	
	/**
	 * 根据商户编号查询POS信息(OEM)
	 * @param customerNo
	 * @return
	 */
	List findPosOemByCustomerNo(String customerNo);
	
}
