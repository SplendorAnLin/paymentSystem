package com.yl.pay.pos.dao;


import com.yl.pay.pos.entity.BankInterfaceMccConfig;
import com.yl.pay.pos.entity.BankMccClassConfig;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.YesNo;

/**
 * 
 * @author haitao.liu
 *
 */
public interface BankInterfaceMccConfigDao {

	/**
	 * 根据银行接口查找是否开启mcc分类配置
	 * @param bankInterfaceCode
	 * @param isOPenConfig
	 * @return
	 */
	public BankInterfaceMccConfig findBy(String bankInterfaceCode, YesNo isOPenConfig);

	/**
	 * 根据商户银行MCC查找对应的大MCC
	 * @param bankMcc
	 * @param status
	 * @param bankInterfaceCode
	 * @return
	 */
	public BankMccClassConfig findBy(String bankInterfaceCode, String bankMcc, Status status);

	/**
	 * 根据标识位查询
	 * @param bankInterfaceCode
	 * @param bankMccCategory
	 * @param isFlag
	 * @return
	 */
	public BankMccClassConfig findBy(String bankInterfaceCode, String bankMccCategory, YesNo isFlag, Status status);
	
}
