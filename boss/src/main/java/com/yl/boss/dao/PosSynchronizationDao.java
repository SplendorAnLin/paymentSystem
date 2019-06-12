package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.PosSynchronization;

public interface PosSynchronizationDao {

	void add(PosSynchronization posSynchronization);
	
	List query(String customerNo);
	
	PosSynchronization queryByPosCati(String posCati);
	
	void update(PosSynchronization posSynchronization);
}
