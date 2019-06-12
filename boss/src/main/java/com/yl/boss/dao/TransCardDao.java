package com.yl.boss.dao;

import java.util.List;
import com.yl.boss.entity.TransCard;
import com.yl.boss.enums.CardAttr;

/**
 * 交易卡服务接口
 * @author AnLin
 *
 */
public interface TransCardDao {
	/**
	 * 新增交易卡
	 */
	void addTransCard(TransCard transCard);
	
	/**
	 * 修改交易卡
	 */
	void updateTransCard(TransCard transCard);
	
	/**
	 * 根据code查询卡信息
	 */
	TransCard findByCode(String code);
	
	/**
	 * 根据商户号，银行卡号查询所有卡信息
	 */
	TransCard findByCustNoAndAccNo(String custNo, String accNo, CardAttr cardAttr);
	
	/**
	 * 根据商户号，银行卡号查询所有卡信息
	 */
	List<TransCard> findByCustNo(String custNo);
	
	/**
	 * 根据商户号查询所有卡信息
	 */
	List<TransCard> findAllByCustNo(String custNo);
	
	/**
	 * 根据商户号，卡属性查询
	 */
	List<TransCard> findByCustAttr(String custNo, CardAttr cardAttr);
	
	/**
	 * 根据商户号，银行卡号查询卡信息
	 */
	TransCard findByCustAndAcc(String custNo, String accNo, CardAttr cardAttr);
}