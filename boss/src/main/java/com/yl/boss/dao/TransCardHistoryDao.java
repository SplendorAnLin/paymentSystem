package com.yl.boss.dao;

import java.util.List;
import com.yl.boss.entity.TransCardHistory;

public interface TransCardHistoryDao {
	/**
	 * 保存卡记录
	 */
	void addTransCardHistory(TransCardHistory transCardHistory);
	
	/**
	 * 修改卡记录
	 */
	void updateTransCardHistory(TransCardHistory transCardHistory);
	
	/**
	 * 根据商户编号/卡号查询卡记录
	 */
	List<TransCardHistory> findTransCardHistoryByCustAndAcc(String customerNo, String accountNo);
	
	/**
	 * 根据接口号查询交易信息
	 */
	List<TransCardHistory> findByInterfaceRequestId(String interfaceCode);
}