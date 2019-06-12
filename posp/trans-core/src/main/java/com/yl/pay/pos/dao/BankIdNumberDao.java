package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.BankIdNumber;

import java.util.List;

/**
 * Title: BankIdNumber Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface BankIdNumberDao {

	public List<BankIdNumber> findByPan(String pan, int panLength, String prefix);	
	
	public List<BankIdNumber> findbyPanLengthAndVerifyCode(int panLength, String verifyCode);
	
	public List<BankIdNumber> findAll();
}
