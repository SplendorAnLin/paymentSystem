package com.yl.boss.service;

import java.util.List;

public interface PosSynchronizationService {

	List query(String customerNo);
	
	void updateStatusByPosCati(String customerNo, List<String> posCatis);
	
}
