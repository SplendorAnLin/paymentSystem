package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.BankInterfaceSwitchConfig;

import java.util.List;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface BankInterfaceSwitchConfigDao {
	
	public BankInterfaceSwitchConfig create(BankInterfaceSwitchConfig bankInterfaceSwitchConfig);
	
	public BankInterfaceSwitchConfig findById(Long id);
	
	public BankInterfaceSwitchConfig update(BankInterfaceSwitchConfig bankInterfaceSwitchConfig);
	
	public List<BankInterfaceSwitchConfig> findByInterfaceCodeAndRespCode(String interfaceCode, String respCode);
	
}
